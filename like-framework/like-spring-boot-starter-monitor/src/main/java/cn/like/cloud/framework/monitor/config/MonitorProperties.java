package cn.like.cloud.framework.monitor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Monitor配置类
 */
@ConfigurationProperties("like.monitor")
@Data
public class MonitorProperties {

}
