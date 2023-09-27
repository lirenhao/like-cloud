package cn.like.cloud.framework.idempotent.config;

import cn.like.cloud.framework.idempotent.core.aop.IdempotentAspect;
import cn.like.cloud.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import cn.like.cloud.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import cn.like.cloud.framework.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import cn.like.cloud.framework.idempotent.core.redis.IdempotentRedisDAO;
import cn.like.cloud.framework.redis.config.LikeRedisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@AutoConfiguration(after = LikeRedisAutoConfiguration.class)
public class LikeIdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
