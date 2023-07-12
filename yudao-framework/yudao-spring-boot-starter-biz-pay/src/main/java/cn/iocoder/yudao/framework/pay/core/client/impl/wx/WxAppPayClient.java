package cn.iocoder.yudao.framework.pay.core.client.impl.wx;

import cn.hutool.core.date.DateUtil;
import cn.iocoder.yudao.framework.pay.core.client.PayCommonResult;
import cn.iocoder.yudao.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import cn.iocoder.yudao.framework.pay.core.enums.PayChannelEnum;
import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.service.payments.app.AppServiceExtension;
import com.wechat.pay.java.service.payments.app.model.Amount;
import com.wechat.pay.java.service.payments.app.model.PrepayRequest;
import com.wechat.pay.java.service.payments.app.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.payments.app.model.SceneInfo;
import lombok.extern.slf4j.Slf4j;

import static cn.iocoder.yudao.framework.common.util.json.JsonUtils.toJsonString;
import static cn.iocoder.yudao.framework.pay.core.client.impl.wx.WxPayCodeMapping.CODE_SUCCESS;
import static cn.iocoder.yudao.framework.pay.core.client.impl.wx.WxPayCodeMapping.MESSAGE_SUCCESS;

/**
 * 微信 App 支付
 */
@Slf4j
public class WxAppPayClient extends AbstractWxPayClient {

    private AppServiceExtension client;

    public WxAppPayClient(Long channelId, WxPayClientConfig config) {
        super(channelId, PayChannelEnum.WX_APP.getCode(), config, new WxPayCodeMapping());
    }

    @Override
    protected void doInit() {
        this.client = new AppServiceExtension.Builder().httpClient(this.httpClient).build();
    }

    @Override
    public PayCommonResult<PrepayWithRequestPaymentResponse> doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) {
        // 这里原生的返回的是支付的 url 所以直接使用string接收
        Amount amount = new Amount();
        amount.setTotal(reqDTO.getAmount().intValue());

        SceneInfo sceneInfo = new SceneInfo();
        sceneInfo.setPayerClientIp(reqDTO.getUserIp());

        PrepayRequest request = new PrepayRequest();
        request.setAppid(this.config.getAppId());
        request.setMchid(this.config.getMchId());
        request.setDescription(reqDTO.getBody());
        request.setOutTradeNo(reqDTO.getMerchantOrderId());
        request.setTimeExpire(DateUtil.format(reqDTO.getExpireTime(), "yyyy-MM-dd'T'HH:mm:ssXXX"));
        request.setAmount(amount); // 单位分
        request.setSceneInfo(sceneInfo);
        request.setNotifyUrl(reqDTO.getNotifyUrl());
        try {
            PrepayWithRequestPaymentResponse response = client.prepayWithRequestPayment(request);
            return PayCommonResult.build(CODE_SUCCESS, MESSAGE_SUCCESS, response, codeMapping);
        } catch (HttpException e) {
            log.error("[doUnifiedOrder][request({}) 发送HTTP请求失败，退款状态未知]", toJsonString(reqDTO), e);
            return PayCommonResult.error(e);
        } catch (ValidationException e) {
            log.error("[doUnifiedOrder][request({}) 发送HTTP请求成功，验证微信支付返回签名失败，退款状态未知]", toJsonString(reqDTO), e);
            return PayCommonResult.error(e);
        } catch (ServiceException e) {
            log.error("[doUnifiedOrder][request({}) 发送HTTP请求成功，服务返回异常，退款状态未知]", toJsonString(reqDTO), e);
            return PayCommonResult.error(e);
        } catch (MalformedMessageException e) {
            log.error("[doUnifiedOrder][request({}) 服务返回成功，content-type不为application/json、解析返回体失败，退款状态未知]", toJsonString(reqDTO), e);
            return PayCommonResult.error(e);
        }
    }
}
