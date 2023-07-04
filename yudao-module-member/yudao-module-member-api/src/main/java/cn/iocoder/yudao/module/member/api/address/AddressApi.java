package cn.iocoder.yudao.module.member.api.address;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.member.api.address.dto.AddressRespDTO;
import cn.iocoder.yudao.module.member.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC 会员 - 地址")
public interface AddressApi {

    String PREFIX = ApiConstants.PREFIX + "/address";

    /**
     * 获得用户收件地址
     *
     * @param id 收件地址编号
     * @param userId 用户编号
     * @return 用户收件地址
     */
    @GetMapping(PREFIX + "/get")
    @Operation(summary = "获得收件地址信息")
    @Parameter(name = "id", description = "收件地址编号", example = "1024", required = true)
    @Parameter(name = "userId", description = "用户编号", example = "1", required = true)
    CommonResult<AddressRespDTO> getAddress(@RequestParam("id") Long id, @RequestParam("userId") Long userId);
}
