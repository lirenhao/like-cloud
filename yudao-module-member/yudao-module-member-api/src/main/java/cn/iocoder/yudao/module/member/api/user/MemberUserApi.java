package cn.iocoder.yudao.module.member.api.user;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.member.api.user.dto.MemberUserRespDTO;
import cn.iocoder.yudao.module.member.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertMap;

@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC 会员 - 用户")
public interface MemberUserApi {

    String PREFIX = ApiConstants.PREFIX + "/user";

    /**
     * 获得会员用户信息
     *
     * @param id 用户编号
     * @return 用户信息
     */
    @GetMapping(PREFIX + "/get")
    @Operation(summary = "获得会员用户信息")
    @Parameter(name = "id", description = "用户编号", example = "1", required = true)
    CommonResult<MemberUserRespDTO> getUser(@RequestParam("id") Long id);

    /**
     * 获得会员用户信息们
     *
     * @param ids 用户编号的数组
     * @return 用户信息们
     */
    @GetMapping(PREFIX + "/list")
    @Operation(summary = "通过用户 ID 查询会员用户们")
    @Parameter(name = "ids", description = "用户编号的数组", example = "1,2", required = true)
    CommonResult<List<MemberUserRespDTO>> getUsers(@RequestParam("id") Collection<Long> ids);

    /**
     * 获得会员用户 Map
     *
     * @param ids 用户编号的数组
     * @return 会员用户 Map
     */
    default Map<Long, MemberUserRespDTO> getUserMap(Collection<Long> ids) {
        return convertMap(getUsers(ids).getCheckedData(), MemberUserRespDTO::getId);
    }

    /**
     * 基于用户昵称，模糊匹配用户列表
     *
     * @param nickname 用户昵称，模糊匹配
     * @return 用户信息的列表
     */
    @GetMapping(PREFIX + "/list-by-nickname")
    @Operation(summary = "通过用户昵称模糊匹配用户列表")
    @Parameter(name = "ids", description = "用户昵称，模糊匹配", example = "nickname", required = true)
    CommonResult<List<MemberUserRespDTO>> getUserListByNickname(@RequestParam("nickname") String nickname);

    /**
     * 基于手机号，精准匹配用户
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    @GetMapping(PREFIX + "/get-by-mobile")
    @Operation(summary = "获得会员用户信息")
    @Parameter(name = "mobile", description = "手机号", example = "18300000001", required = true)
    CommonResult<MemberUserRespDTO> getUserByMobile(@RequestParam("mobile") String mobile);
}
