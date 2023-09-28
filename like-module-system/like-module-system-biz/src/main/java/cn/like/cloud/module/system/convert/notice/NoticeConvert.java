package cn.like.cloud.module.system.convert.notice;

import cn.like.cloud.framework.common.pojo.PageResult;
import cn.like.cloud.module.system.controller.admin.notice.vo.NoticeCreateReqVO;
import cn.like.cloud.module.system.controller.admin.notice.vo.NoticeRespVO;
import cn.like.cloud.module.system.controller.admin.notice.vo.NoticeUpdateReqVO;
import cn.like.cloud.module.system.dal.dataobject.notice.NoticeDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoticeConvert {

    NoticeConvert INSTANCE = Mappers.getMapper(NoticeConvert.class);

    PageResult<NoticeRespVO> convertPage(PageResult<NoticeDO> page);

    NoticeRespVO convert(NoticeDO bean);

    NoticeDO convert(NoticeUpdateReqVO bean);

    NoticeDO convert(NoticeCreateReqVO bean);

}
