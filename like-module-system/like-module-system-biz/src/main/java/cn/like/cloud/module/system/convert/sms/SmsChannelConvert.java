package cn.like.cloud.module.system.convert.sms;

import cn.like.cloud.framework.common.pojo.PageResult;
import cn.like.cloud.framework.sms.core.property.SmsChannelProperties;
import cn.like.cloud.module.system.controller.admin.sms.vo.channel.SmsChannelCreateReqVO;
import cn.like.cloud.module.system.controller.admin.sms.vo.channel.SmsChannelRespVO;
import cn.like.cloud.module.system.controller.admin.sms.vo.channel.SmsChannelSimpleRespVO;
import cn.like.cloud.module.system.controller.admin.sms.vo.channel.SmsChannelUpdateReqVO;
import cn.like.cloud.module.system.dal.dataobject.sms.SmsChannelDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 短信渠道 Convert
 */
@Mapper
public interface SmsChannelConvert {

    SmsChannelConvert INSTANCE = Mappers.getMapper(SmsChannelConvert.class);

    SmsChannelDO convert(SmsChannelCreateReqVO bean);

    SmsChannelDO convert(SmsChannelUpdateReqVO bean);

    SmsChannelRespVO convert(SmsChannelDO bean);

    List<SmsChannelRespVO> convertList(List<SmsChannelDO> list);

    PageResult<SmsChannelRespVO> convertPage(PageResult<SmsChannelDO> page);

    List<SmsChannelSimpleRespVO> convertList03(List<SmsChannelDO> list);

    SmsChannelProperties convert02(SmsChannelDO channel);

}
