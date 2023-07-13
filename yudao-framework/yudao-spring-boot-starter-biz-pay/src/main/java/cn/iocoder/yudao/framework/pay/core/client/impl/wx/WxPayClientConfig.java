package cn.iocoder.yudao.framework.pay.core.client.impl.wx;

import cn.iocoder.yudao.framework.pay.core.client.PayClientConfig;
import lombok.Data;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * 微信支付的 PayClientConfig 实现类
 * 属性主要来自 {@link com.github.binarywang.wxpay.config.WxPayConfig} 的必要属性
 * https://pay.weixin.qq.com/wiki/doc/apiv3/wechatpay/wechatpay-1.shtml
 */
@Data
public class WxPayClientConfig implements PayClientConfig {

    /**
     * 公众号或者小程序的 appId
     */
    @NotBlank(message = "APP ID 不能为空", groups = V3.class)
    private String appId;

    /**
     * 商户号
     */
    @NotBlank(message = "商户号 不能为空", groups = V3.class)
    private String mchId;

    /**
     * 商户API证书序列号
     */
    @NotBlank(message = "证书序列号 不能为空", groups = V3.class)
    private String mchSerialNo;

    /**
     * 商户私钥文件路径
     */
    @NotBlank(message = "私钥文件路径 不能为空", groups = V3.class)
    private String privateKeyPath;

    /**
     * APIv3密钥
     */
    @NotBlank(message = "apiV3 密钥值 不能为空", groups = V3.class)
    private String apiV3Key;

    /**
     * 分组校验 v3版本
     */
    public interface V3 {
    }

    @Override
    public Set<ConstraintViolation<PayClientConfig>> verifyParam(Validator validator) {
        return validator.validate(this, V3.class);
    }
}
