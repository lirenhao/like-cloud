package cn.like.cloud.module.system.convert.mail;

import cn.like.cloud.framework.common.pojo.PageResult;
import cn.like.cloud.module.system.controller.admin.mail.vo.log.MailLogRespVO;
import cn.like.cloud.module.system.dal.dataobject.mail.MailLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MailLogConvert {

    MailLogConvert INSTANCE = Mappers.getMapper(MailLogConvert.class);

    PageResult<MailLogRespVO> convertPage(PageResult<MailLogDO> pageResult);

    MailLogRespVO convert(MailLogDO bean);

}
