package cn.iocoder.yudao.module.pay.api.refund;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.pay.api.refund.dto.PayRefundCreateReqDTO;
import cn.iocoder.yudao.module.pay.api.refund.dto.PayRefundRespDTO;
import cn.iocoder.yudao.module.pay.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * 退款单 API 接口
 *
 * @author 芋道源码
 */
@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC 支付 - 退款")
public interface PayRefundApi {

    String PREFIX = ApiConstants.PREFIX + "/refund";

    /**
     * 创建退款单
     *
     * @param reqDTO 创建请求
     * @return 退款单编号
     */
    @PostMapping(PREFIX + "/create")
    @Operation(summary = "创建退款单")
    CommonResult<Long> createPayRefund(@Valid @RequestBody PayRefundCreateReqDTO reqDTO);

    /**
     * 获得退款单
     *
     * @param id 退款单编号
     * @return 退款单
     */
    @GetMapping(PREFIX + "/get")
    @Operation(summary = "获得退款单")
    @Parameter(name = "id", description = "退款单编号", example = "1024", required = true)
    CommonResult<PayRefundRespDTO> getPayRefund(@RequestParam("id") Long id);
}
