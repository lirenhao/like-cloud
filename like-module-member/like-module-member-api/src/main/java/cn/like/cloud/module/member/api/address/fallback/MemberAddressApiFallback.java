package cn.like.cloud.module.member.api.address.fallback;

import cn.like.cloud.framework.common.pojo.CommonResult;
import cn.like.cloud.module.member.api.address.MemberAddressApi;
import cn.like.cloud.module.member.api.address.dto.MemberAddressRespDTO;
import cn.like.cloud.module.member.enums.ErrorCodeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MemberAddressApiFallback implements FallbackFactory<MemberAddressApi> {

    @Override
    public MemberAddressApi create(Throwable throwable) {
        return new MemberAddressApi() {

            @Override
            public CommonResult<MemberAddressRespDTO> getAddress(Long id, Long userId) {
                log.error("获得用户收件地址出现错误:{}-{}", id, userId, throwable);
                return CommonResult.error(ErrorCodeConstants.ADDRESS_NOT_EXISTS);
            }

            @Override
            public CommonResult<MemberAddressRespDTO> getDefaultAddress(Long userId) {
                log.error("获得用户默认收件地址出现错误:{}", userId, throwable);
                return CommonResult.error(ErrorCodeConstants.ADDRESS_NOT_EXISTS);
            }
        };
    }
}
