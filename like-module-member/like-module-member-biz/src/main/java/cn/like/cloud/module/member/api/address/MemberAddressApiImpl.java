package cn.like.cloud.module.member.api.address;

import cn.like.cloud.framework.common.pojo.CommonResult;
import cn.like.cloud.module.member.api.address.dto.MemberAddressRespDTO;
import cn.like.cloud.module.member.convert.address.AddressConvert;
import cn.like.cloud.module.member.service.address.AddressService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.like.cloud.framework.common.pojo.CommonResult.success;

/**
 * 用户收件地址 API 实现类
 */
@RestController // 提供 RestFull API 接口，给 Feign 调用
@Validated
public class MemberAddressApiImpl implements MemberAddressApi {

    @Resource
    private AddressService addressService;

    @Override
    public CommonResult<MemberAddressRespDTO> getAddress(Long id, Long userId) {
        return success(AddressConvert.INSTANCE.convert02(addressService.getAddress(userId, id)));
    }

    @Override
    public CommonResult<MemberAddressRespDTO> getDefaultAddress(Long userId) {
        return success(AddressConvert.INSTANCE.convert02(addressService.getDefaultUserAddress(userId)));
    }

}
