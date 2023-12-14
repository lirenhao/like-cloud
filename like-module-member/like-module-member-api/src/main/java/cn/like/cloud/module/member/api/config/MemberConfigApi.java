package cn.like.cloud.module.member.api.config;

import cn.like.cloud.framework.common.pojo.CommonResult;
import cn.like.cloud.module.member.api.config.dto.MemberConfigRespDTO;
import cn.like.cloud.module.member.api.config.fallback.MemberConfigApiFallback;
import cn.like.cloud.module.member.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = ApiConstants.NAME, fallbackFactory = MemberConfigApiFallback.class)
@Tag(name = "RPC 服务 - 用户配置")
public interface MemberConfigApi {

    String PREFIX = ApiConstants.PREFIX + "/config";

    @GetMapping(PREFIX + "/get")
    @Operation(summary = "获得用户配置")
    CommonResult<MemberConfigRespDTO> getConfig();

}
