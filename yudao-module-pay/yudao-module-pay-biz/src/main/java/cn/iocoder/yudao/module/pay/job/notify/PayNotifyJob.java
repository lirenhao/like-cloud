package cn.iocoder.yudao.module.pay.job.notify;

import cn.iocoder.yudao.framework.tenant.core.job.TenantJob;
import cn.iocoder.yudao.module.pay.service.notify.PayNotifyService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 支付通知 Job
 * 通过不断扫描待通知的 PayNotifyTaskDO 记录，回调业务线的回调接口
 *
 * @author 芋道源码
 */
@Component
@TenantJob // 多租户
@Slf4j
public class PayNotifyJob {

    @Resource
    private PayNotifyService payNotifyCoreService;

    @XxlJob("payNotifyJob")
    public String execute(String param) throws Exception {
        int notifyCount = payNotifyCoreService.executeNotify();
        return String.format("执行支付通知 %s 个", notifyCount);
    }

}
