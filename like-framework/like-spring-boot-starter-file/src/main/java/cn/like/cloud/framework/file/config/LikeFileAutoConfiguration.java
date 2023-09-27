package cn.like.cloud.framework.file.config;

import cn.like.cloud.framework.file.core.client.FileClientFactory;
import cn.like.cloud.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 文件配置类
 */
@AutoConfiguration
public class LikeFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
