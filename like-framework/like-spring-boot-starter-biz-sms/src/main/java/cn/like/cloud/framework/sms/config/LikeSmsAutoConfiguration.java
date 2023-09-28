package cn.like.cloud.framework.sms.config;

import cn.like.cloud.framework.sms.core.client.SmsClientFactory;
import cn.like.cloud.framework.sms.core.client.impl.SmsClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 短信配置类
 */
@AutoConfiguration
public class LikeSmsAutoConfiguration {

    @Bean
    public SmsClientFactory smsClientFactory() {
        return new SmsClientFactoryImpl();
    }

}
