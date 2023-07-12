package cn.iocoder.yudao.framework.pay.core.client.impl.wx;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Assert;
import cn.iocoder.yudao.framework.pay.core.client.AbstractPayCodeMapping;
import cn.iocoder.yudao.framework.pay.core.client.PayCommonResult;
import cn.iocoder.yudao.framework.pay.core.client.dto.notify.PayNotifyReqDTO;
import cn.iocoder.yudao.framework.pay.core.client.dto.notify.PayOrderNotifyRespDTO;
import cn.iocoder.yudao.framework.pay.core.client.dto.notify.PayRefundNotifyRespDTO;
import cn.iocoder.yudao.framework.pay.core.client.dto.refund.PayRefundUnifiedReqDTO;
import cn.iocoder.yudao.framework.pay.core.client.dto.refund.PayRefundUnifiedRespDTO;
import cn.iocoder.yudao.framework.pay.core.client.impl.AbstractPayClient;
import cn.iocoder.yudao.framework.pay.core.enums.PayNotifyRefundStatusEnum;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.http.DefaultHttpClientBuilder;
import com.wechat.pay.java.core.http.HttpClient;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static cn.iocoder.yudao.framework.common.util.json.JsonUtils.toJsonString;

/**
 * 微信支付抽象类，实现微信统一的接口。如退款、统一下单
 * 微信支付只支持v3
 */
@Slf4j
public abstract class AbstractWxPayClient extends AbstractPayClient<WxPayClientConfig> {

    protected HttpClient httpClient;
    private NotificationParser notificationParser;
    private RefundService refundService;

    public AbstractWxPayClient(Long channelId, String channelCode, WxPayClientConfig config, AbstractPayCodeMapping codeMapping) {
        super(channelId, channelCode, config, codeMapping);
    }

    /**
     * 每个商户号只能创建一个 RSAAutoCertificateConfig。同一个商户号构造多个实例，会抛出 IllegalStateException 异常。
     */
    @Override
    @SneakyThrows
    protected void doInit() {
        RSAAutoCertificateConfig payConfig = new RSAAutoCertificateConfig.Builder().
                merchantId(config.getMchId()).
                privateKeyFromPath(config.getPrivateKeyPath())
                .merchantSerialNumber(config.getMchSerialNo())
                .apiV3Key(config.getApiV3Key())
                .build();
        this.httpClient = new DefaultHttpClientBuilder().config(payConfig).build();
        this.notificationParser = new NotificationParser(payConfig);
        this.refundService = new RefundService.Builder().config(payConfig).build();
    }

    /**
     * 微信支付回调的处理方式
     *
     * @param data 通知结果
     * @return 支付回调对象
     * @throws WxPayException 微信异常类
     */
    @Override
    public PayOrderNotifyRespDTO parseOrderNotify(PayNotifyReqDTO data) throws Exception {
        log.info("[parseOrderNotify][微信支付回调data数据:{}]", data.getBody());
        RequestParam param = new RequestParam.Builder()
                .serialNumber(data.getParams().get("Wechatpay-Serial"))
                .nonce(data.getParams().get("Wechatpay-Nonce"))
                .signature(data.getParams().get("Wechatpay-Signature"))
                .timestamp(data.getParams().get("Wechatpay-Timestamp"))
                .signType(data.getParams().get("Wechatpay-Signature-Type"))
                .body(data.getBody())
                .build();
        Transaction result = notificationParser.parse(param, Transaction.class);
        // 转换结果
        Assert.isTrue(Transaction.TradeStateEnum.SUCCESS.name().equals(result.getTradeState().name()), "支付结果非 SUCCESS");
        return PayOrderNotifyRespDTO
                .builder()
                .orderExtensionNo(result.getOutTradeNo())
                .channelOrderNo(result.getTransactionId())
                .channelUserId(result.getPayer().getOpenid())
                .successTime(LocalDateTimeUtil.parse(result.getSuccessTime(), "yyyy-MM-dd'T'HH:mm:ssXXX"))
                .build();
    }

    @Override
    public PayRefundNotifyRespDTO parseRefundNotify(PayNotifyReqDTO data) {
        log.info("[parseOrderNotify][微信支付回调data数据:{}]", data.getBody());
        RequestParam param = new RequestParam.Builder()
                .serialNumber(data.getParams().get("Wechatpay-Serial"))
                .nonce(data.getParams().get("Wechatpay-Nonce"))
                .signature(data.getParams().get("Wechatpay-Signature"))
                .timestamp(data.getParams().get("Wechatpay-Timestamp"))
                .signType(data.getParams().get("Wechatpay-Signature-Type"))
                .body(data.getBody())
                .build();
        RefundNotification result = notificationParser.parse(param, RefundNotification.class);
        // 转换结果
        Assert.isTrue(Status.SUCCESS.name().equals(result.getRefundStatus().name()), "支付结果非 SUCCESS");
        return PayRefundNotifyRespDTO
                .builder()
                .channelOrderNo(result.getTransactionId())
                .tradeNo(result.getOutTradeNo())
                .reqNo(result.getOutRefundNo())
                .status(PayNotifyRefundStatusEnum.SUCCESS)
                .refundSuccessTime(LocalDateTimeUtil.parse(result.getSuccessTime(), "yyyy-MM-dd'T'HH:mm:ssXXX")).build();
    }

    @Override
    public boolean isRefundNotify(PayNotifyReqDTO notifyData) {
        return false;
    }

    @Override
    public boolean verifyNotifyData(PayNotifyReqDTO notifyData) {
        boolean verifyResult = false;
        // TODO 如何验证
        return true;
    }

    @Override
    protected PayCommonResult<PayRefundUnifiedRespDTO> doUnifiedRefund(PayRefundUnifiedReqDTO reqDTO) {
        AmountReq amount = new AmountReq();
        amount.setTotal(reqDTO.getAmount());
        amount.setRefund(reqDTO.getAmount());
        CreateRequest request = new CreateRequest();
        request.setTransactionId(reqDTO.getChannelOrderNo());
        request.setOutTradeNo(reqDTO.getPayTradeNo());
        request.setOutRefundNo(reqDTO.getMerchantRefundId());
        request.setReason(reqDTO.getReason());
        request.setAmount(amount);
        try {
            Refund refund = refundService.create(request);
            log.info("[doUnifiedRefund][response({}) 发起退款 渠道返回", toJsonString(refund));
            if (Status.SUCCESS.name().equals(refund.getStatus().name())){
                PayRefundUnifiedRespDTO respDTO = new PayRefundUnifiedRespDTO();
                respDTO.setChannelRefundId(refund.getRefundId());
                return PayCommonResult.build(refund.getStatus().name(), "", respDTO, codeMapping);
            }
            // 失败。需要抛出异常
            return PayCommonResult.build(refund.getStatus().name(), "", null, codeMapping);
        } catch (HttpException e) {
            log.error("[doUnifiedRefund][request({}) 发送HTTP请求失败，退款状态未知]", toJsonString(reqDTO), e);
            return PayCommonResult.error(e);
        } catch (ValidationException e) {
            log.error("[doUnifiedRefund][request({}) 发送HTTP请求成功，验证微信支付返回签名失败，退款状态未知]", toJsonString(reqDTO), e);
            return PayCommonResult.error(e);
        } catch (ServiceException e) {
            log.error("[doUnifiedRefund][request({}) 发送HTTP请求成功，服务返回异常，退款状态未知]", toJsonString(reqDTO), e);
            return PayCommonResult.error(e);
        } catch (MalformedMessageException e) {
            log.error("[doUnifiedRefund][request({}) 服务返回成功，content-type不为application/json、解析返回体失败，退款状态未知]", toJsonString(reqDTO), e);
            return PayCommonResult.error(e);
        }
    }
}
