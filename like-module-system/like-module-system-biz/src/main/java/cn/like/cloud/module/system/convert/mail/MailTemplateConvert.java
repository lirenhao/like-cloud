package cn.like.cloud.module.system.convert.mail;

import cn.like.cloud.framework.common.pojo.PageResult;
import cn.like.cloud.module.system.controller.admin.mail.vo.template.MailTemplateCreateReqVO;
import cn.like.cloud.module.system.controller.admin.mail.vo.template.MailTemplateRespVO;
import cn.like.cloud.module.system.controller.admin.mail.vo.template.MailTemplateSimpleRespVO;
import cn.like.cloud.module.system.controller.admin.mail.vo.template.MailTemplateUpdateReqVO;
import cn.like.cloud.module.system.dal.dataobject.mail.MailTemplateDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MailTemplateConvert {

    MailTemplateConvert INSTANCE = Mappers.getMapper(MailTemplateConvert.class);

    MailTemplateDO convert(MailTemplateUpdateReqVO bean);

    MailTemplateDO convert(MailTemplateCreateReqVO bean);

    MailTemplateRespVO convert(MailTemplateDO bean);

    PageResult<MailTemplateRespVO> convertPage(PageResult<MailTemplateDO> pageResult);

    List<MailTemplateSimpleRespVO> convertList02(List<MailTemplateDO> list);

}
