package cn.like.cloud.module.system.api.notify.notify;

import cn.like.cloud.framework.common.pojo.CommonResult;
import cn.like.cloud.module.system.api.notify.NotifyMessageSendApi;
import cn.like.cloud.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import cn.like.cloud.module.system.service.notify.NotifySendService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.like.cloud.framework.common.pojo.CommonResult.success;

@RestController // 提供 RestFull API 接口，给 Feign 调用
@Validated
public class NotifyMessageSendApiImpl implements NotifyMessageSendApi {

    @Resource
    private NotifySendService notifySendService;

    @Override
    public CommonResult<Long> sendSingleMessageToAdmin(NotifySendSingleToUserReqDTO reqDTO) {
        return success(notifySendService.sendSingleNotifyToAdmin(reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams()));
    }

    @Override
    public CommonResult<Long> sendSingleMessageToMember(NotifySendSingleToUserReqDTO reqDTO) {
        return success(notifySendService.sendSingleNotifyToMember(reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams()));
    }

}
