package cn.like.cloud.module.system.convert.notify;

import cn.like.cloud.framework.common.pojo.PageResult;
import cn.like.cloud.module.system.controller.admin.notify.vo.template.NotifyTemplateCreateReqVO;
import cn.like.cloud.module.system.controller.admin.notify.vo.template.NotifyTemplateRespVO;
import cn.like.cloud.module.system.controller.admin.notify.vo.template.NotifyTemplateUpdateReqVO;
import cn.like.cloud.module.system.dal.dataobject.notify.NotifyTemplateDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 站内信模版 Convert
 */
@Mapper
public interface NotifyTemplateConvert {

    NotifyTemplateConvert INSTANCE = Mappers.getMapper(NotifyTemplateConvert.class);

    NotifyTemplateDO convert(NotifyTemplateCreateReqVO bean);

    NotifyTemplateDO convert(NotifyTemplateUpdateReqVO bean);

    NotifyTemplateRespVO convert(NotifyTemplateDO bean);

    List<NotifyTemplateRespVO> convertList(List<NotifyTemplateDO> list);

    PageResult<NotifyTemplateRespVO> convertPage(PageResult<NotifyTemplateDO> page);

}
