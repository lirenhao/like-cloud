package cn.iocoder.yudao.framework.pay.core.client.impl.wx;

import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 每个商户号只能创建一个 RSAAutoCertificateConfig。同一个商户号构造多个实例，会抛出 IllegalStateException 异常。
 */
@Slf4j
public class WxRSAAutoConfig {

    private static final ConcurrentMap<String, RSAAutoCertificateConfig> configs = new ConcurrentHashMap<>();

    public static RSAAutoCertificateConfig getAutoCertificateConfig(String mchId) {
        RSAAutoCertificateConfig config = configs.get(mchId);
        if (config == null) {
            log.error("[getAutoCertificateConfig][商户号({}) 找不到RSAAutoCertificateConfig]", mchId);
        }
        return config;
    }

    public static void createOrUpdatePayClient(WxPayClientConfig clientConfig) {
        RSAAutoCertificateConfig config = configs.getOrDefault(clientConfig.getMchId(), null);
        if (config == null) {
            config = new RSAAutoCertificateConfig.Builder()
                    .merchantId(clientConfig.getMchId())
                    .privateKeyFromPath(clientConfig.getPrivateKeyPath())
                    .merchantSerialNumber(clientConfig.getMchSerialNo())
                    .apiV3Key(clientConfig.getApiV3Key())
                    .build();
        } else {
            // TODO 重载config，现在的SDK无法做到参数变更后重载config，保留后续SDK实现后修改
        }
        configs.put(clientConfig.getMchId(), config);
    }
}
