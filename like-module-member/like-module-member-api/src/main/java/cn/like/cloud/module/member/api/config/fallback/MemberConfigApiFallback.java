package cn.like.cloud.module.member.api.config.fallback;

import cn.like.cloud.framework.common.pojo.CommonResult;
import cn.like.cloud.module.member.api.address.dto.MemberAddressRespDTO;
import cn.like.cloud.module.member.api.config.MemberConfigApi;
import cn.like.cloud.module.member.api.config.dto.MemberConfigRespDTO;
import cn.like.cloud.module.member.enums.ErrorCodeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MemberConfigApiFallback implements FallbackFactory<MemberConfigApi> {

    @Override
    public MemberConfigApi create(Throwable throwable) {
        return new MemberConfigApi() {

            @Override
            public CommonResult<MemberConfigRespDTO> getConfig() {
                log.error("获得用户配置出现错误", throwable);
                return null;
            }
        };
    }
}
