package cn.iocoder.yudao.framework.pay.core.client.impl.aliv3;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.iocoder.yudao.framework.pay.core.client.AbstractPayCodeMapping;
import cn.iocoder.yudao.framework.pay.core.client.PayCommonResult;
import cn.iocoder.yudao.framework.pay.core.client.dto.notify.PayNotifyReqDTO;
import cn.iocoder.yudao.framework.pay.core.client.dto.notify.PayOrderNotifyRespDTO;
import cn.iocoder.yudao.framework.pay.core.client.dto.notify.PayRefundNotifyRespDTO;
import cn.iocoder.yudao.framework.pay.core.client.dto.refund.PayRefundUnifiedReqDTO;
import cn.iocoder.yudao.framework.pay.core.client.dto.refund.PayRefundUnifiedRespDTO;
import cn.iocoder.yudao.framework.pay.core.client.impl.AbstractPayClient;
import cn.iocoder.yudao.framework.pay.core.client.impl.alipay.AlipayPayClientConfig;
import cn.iocoder.yudao.framework.pay.core.enums.PayNotifyRefundStatusEnum;
import com.alipay.v3.ApiClient;
import com.alipay.v3.ApiException;
import com.alipay.v3.api.AlipayTradeApi;
import com.alipay.v3.model.AlipayTradeRefundModel;
import com.alipay.v3.model.AlipayTradeRefundResponseModel;
import com.alipay.v3.util.AlipaySignature;
import com.alipay.v3.util.model.AlipayConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.util.json.JsonUtils.toJsonString;

/**
 * 支付宝抽象类，实现支付宝统一的接口。如退款
 * V3
 */
@Slf4j
public abstract class AbstractAlipayClient extends AbstractPayClient<AlipayPayClientConfig> {

    protected ApiClient client;

    public AbstractAlipayClient(Long channelId, String channelCode,
                                AlipayPayClientConfig config, AbstractPayCodeMapping codeMapping) {
        super(channelId, channelCode, config, codeMapping);
    }

    /**
     * 支付宝统一回调参数  str 转 map
     *
     * @param s 支付宝支付通知回调参数
     * @return map 支付宝集合
     */
    public static Map<String, String> strToMap(String s) {
        // TODO @zxy：这个可以使用 hutool 的 HttpUtil decodeParams 方法么？
        Map<String, String> stringStringMap = new HashMap<>();
        // 调整时间格式
        String s3 = s.replaceAll("%3A", ":");
        // 获取 map
        String s4 = s3.replace("+", " ");
        String[] split = s4.split("&");
        for (String s1 : split) {
            String[] split1 = s1.split("=");
            stringStringMap.put(split1[0], split1[1]);
        }
        return stringStringMap;
    }

    @Override
    @SneakyThrows
    protected void doInit() {
        AlipayConfig alipayConfig = new AlipayConfig();
        BeanUtil.copyProperties(config, alipayConfig, false);
        this.client = new ApiClient();
        this.client.setAlipayConfig(alipayConfig);
    }

    /**
     * 从支付宝通知返回参数中解析 PayOrderNotifyRespDTO, 通知具体参数参考
     * //https://opendocs.alipay.com/open/203/105286
     *
     * @param data 通知结果
     * @return 解析结果 PayOrderNotifyRespDTO
     * @throws Exception 解析失败，抛出异常
     */
    @Override
    public PayOrderNotifyRespDTO parseOrderNotify(PayNotifyReqDTO data) throws Exception {
        Map<String, String> params = strToMap(data.getBody());

        return PayOrderNotifyRespDTO.builder().orderExtensionNo(params.get("out_trade_no"))
                .channelOrderNo(params.get("trade_no")).channelUserId(params.get("seller_id"))
                .tradeStatus(params.get("trade_status"))
                .successTime(LocalDateTimeUtil.parse(params.get("notify_time"), "yyyy-MM-dd HH:mm:ss"))
                .data(data.getBody()).build();
    }

    @Override
    public PayRefundNotifyRespDTO parseRefundNotify(PayNotifyReqDTO notifyData) {
        Map<String, String> params = strToMap(notifyData.getBody());
        PayRefundNotifyRespDTO notifyDTO = PayRefundNotifyRespDTO.builder().channelOrderNo(params.get("trade_no"))
                .tradeNo(params.get("out_trade_no"))
                .reqNo(params.get("out_biz_no"))
                .status(PayNotifyRefundStatusEnum.SUCCESS)
                .refundSuccessTime(LocalDateTimeUtil.parse(params.get("gmt_refund"), "yyyy-MM-dd HH:mm:ss"))
                .build();
        return notifyDTO;
    }

    @Override
    public boolean isRefundNotify(PayNotifyReqDTO notifyData) {
        return notifyData.getParams().containsKey("refund_fee");
    }

    @Override
    public boolean verifyNotifyData(PayNotifyReqDTO notifyData) {
        boolean verifyResult = false;
        try {
            verifyResult = AlipaySignature.rsaCheckV1(notifyData.getParams(), config.getAlipayPublicKey(), StandardCharsets.UTF_8.name(), "RSA2");
        } catch (ApiException e) {
            log.error("[AlipayClient verifyNotifyData][(notify param is :{}) 验证失败]", toJsonString(notifyData.getParams()), e);
        }
        return verifyResult;
    }

    /**
     * 支付宝统一的退款接口 alipay.trade.refund
     *
     * @param reqDTO 退款请求 request DTO
     * @return 退款请求 Response
     */
    @Override
    protected PayCommonResult<PayRefundUnifiedRespDTO> doUnifiedRefund(PayRefundUnifiedReqDTO reqDTO) {
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setTradeNo(reqDTO.getChannelOrderNo());
        model.setOutTradeNo(reqDTO.getPayTradeNo());
        model.setOutRequestNo(reqDTO.getMerchantRefundId());
        model.setRefundAmount(calculateAmount(reqDTO.getAmount()).toString());
        model.setRefundReason(reqDTO.getReason());
        AlipayTradeApi api = new AlipayTradeApi(client);
        try {
            AlipayTradeRefundResponseModel response = api.refund(model);
            log.info("[doUnifiedRefund][response({}) 发起退款 渠道返回", toJsonString(response));
            //退款导致触发的异步通知是发送到支付接口中设置的notify_url
            //支付宝不返回退款单号，设置为空
            PayRefundUnifiedRespDTO respDTO = new PayRefundUnifiedRespDTO();
            respDTO.setChannelRefundId("");
            return PayCommonResult.build("10000", "", respDTO, codeMapping);
        } catch (ApiException e) {
            log.error("[doUnifiedRefund][request({}) 发起退款失败,网络读超时，退款状态未知]", toJsonString(reqDTO), e);
            return PayCommonResult.build(String.valueOf(e.getCode()), e.getMessage(), null, codeMapping);
        }
    }

}
