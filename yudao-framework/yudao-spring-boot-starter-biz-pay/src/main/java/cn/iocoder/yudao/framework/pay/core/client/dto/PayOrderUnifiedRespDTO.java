package cn.iocoder.yudao.framework.pay.core.client.dto;

import lombok.Data;

/**
 * 统一下单 Response DTO
 *
 * @author 芋道源码
 */
@Data
public class PayOrderUnifiedRespDTO {

    /**
     * 展示模式
     */
    private String displayMode;
    /**
     * 展示内容
     */
    private String displayContent;

}
