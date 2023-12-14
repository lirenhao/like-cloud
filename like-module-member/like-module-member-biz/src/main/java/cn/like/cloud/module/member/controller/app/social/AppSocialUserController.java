package cn.like.cloud.module.member.controller.app.social;

import cn.like.cloud.framework.common.enums.UserTypeEnum;
import cn.like.cloud.framework.common.pojo.CommonResult;
import cn.like.cloud.framework.security.core.annotations.PreAuthenticated;
import cn.like.cloud.module.member.controller.app.social.vo.AppSocialUserBindReqVO;
import cn.like.cloud.module.member.controller.app.social.vo.AppSocialUserUnbindReqVO;
import cn.like.cloud.module.member.convert.social.SocialUserConvert;
import cn.like.cloud.module.system.api.social.SocialUserApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.like.cloud.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 App - 社交用户")
@RestController
@RequestMapping("/system/social-user")
@Validated
public class AppSocialUserController {

    @Resource
    private SocialUserApi socialUserApi;

    @PostMapping("/bind")
    @Operation(summary = "社交绑定，使用 code 授权码")
    public CommonResult<Boolean> socialBind(@RequestBody @Valid AppSocialUserBindReqVO reqVO) {
        socialUserApi.bindSocialUser(SocialUserConvert.INSTANCE.convert(getLoginUserId(), UserTypeEnum.MEMBER.getValue(), reqVO));
        return CommonResult.success(true);
    }

    @DeleteMapping("/unbind")
    @Operation(summary = "取消社交绑定")
    @PreAuthenticated
    public CommonResult<Boolean> socialUnbind(@RequestBody AppSocialUserUnbindReqVO reqVO) {
        socialUserApi.unbindSocialUser(SocialUserConvert.INSTANCE.convert(getLoginUserId(), UserTypeEnum.MEMBER.getValue(), reqVO));
        return CommonResult.success(true);
    }

}
