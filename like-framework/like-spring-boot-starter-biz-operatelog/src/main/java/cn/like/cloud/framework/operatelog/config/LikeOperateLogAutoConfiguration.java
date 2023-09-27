package cn.like.cloud.framework.operatelog.config;

import cn.like.cloud.framework.operatelog.core.aop.OperateLogAspect;
import cn.like.cloud.framework.operatelog.core.service.OperateLogFrameworkService;
import cn.like.cloud.framework.operatelog.core.service.OperateLogFrameworkServiceImpl;
import cn.like.cloud.module.system.api.logger.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class LikeOperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogFrameworkService operateLogFrameworkService(OperateLogApi operateLogApi) {
        return new OperateLogFrameworkServiceImpl(operateLogApi);
    }

}
