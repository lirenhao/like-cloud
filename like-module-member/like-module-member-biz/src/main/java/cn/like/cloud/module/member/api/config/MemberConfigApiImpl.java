package cn.like.cloud.module.member.api.config;

import cn.like.cloud.framework.common.pojo.CommonResult;
import cn.like.cloud.module.member.api.config.dto.MemberConfigRespDTO;
import cn.like.cloud.module.member.convert.config.MemberConfigConvert;
import cn.like.cloud.module.member.service.config.MemberConfigService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.like.cloud.framework.common.pojo.CommonResult.success;

/**
 * 用户配置 API 实现类
 */
@RestController // 提供 RestFull API 接口，给 Feign 调用
@Validated
public class MemberConfigApiImpl implements MemberConfigApi {

    @Resource
    private MemberConfigService memberConfigService;

    @Override
    public CommonResult<MemberConfigRespDTO> getConfig() {
        return success(MemberConfigConvert.INSTANCE.convert01(memberConfigService.getConfig()));
    }

}
