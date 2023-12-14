package cn.like.cloud.module.member.controller.app.point;

import cn.like.cloud.framework.common.pojo.CommonResult;
import cn.like.cloud.framework.common.pojo.PageParam;
import cn.like.cloud.framework.common.pojo.PageResult;
import cn.like.cloud.framework.security.core.annotations.PreAuthenticated;
import cn.like.cloud.module.member.controller.app.point.vo.AppMemberPointRecordRespVO;
import cn.like.cloud.module.member.convert.point.MemberPointRecordConvert;
import cn.like.cloud.module.member.dal.dataobject.point.MemberPointRecordDO;
import cn.like.cloud.module.member.service.point.MemberPointRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.like.cloud.framework.common.pojo.CommonResult.success;
import static cn.like.cloud.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 App - 签到记录")
@RestController
@RequestMapping("/member/point/record")
@Validated
public class AppMemberPointRecordController {

    @Resource
    private MemberPointRecordService pointRecordService;

    @GetMapping("/page")
    @Operation(summary = "获得用户积分记录分页")
    @PreAuthenticated
    public CommonResult<PageResult<AppMemberPointRecordRespVO>> getPointRecordPage(@Valid PageParam pageVO) {
        PageResult<MemberPointRecordDO> pageResult = pointRecordService.getPointRecordPage(getLoginUserId(), pageVO);
        return success(MemberPointRecordConvert.INSTANCE.convertPage02(pageResult));
    }

}
