package cn.like.cloud.framework.errorcode.config;

import cn.like.cloud.module.system.api.errorcode.ErrorCodeApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 错误码用到 Feign 的配置项
 */
@AutoConfiguration
@EnableFeignClients(clients = ErrorCodeApi.class) // 主要是引入相关的 API 服务
public class LikeErrorCodeRpcAutoConfiguration {
}
