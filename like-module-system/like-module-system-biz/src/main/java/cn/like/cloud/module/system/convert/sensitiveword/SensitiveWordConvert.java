package cn.like.cloud.module.system.convert.sensitiveword;

import cn.like.cloud.framework.common.pojo.PageResult;
import cn.like.cloud.module.system.controller.admin.sensitiveword.vo.SensitiveWordCreateReqVO;
import cn.like.cloud.module.system.controller.admin.sensitiveword.vo.SensitiveWordExcelVO;
import cn.like.cloud.module.system.controller.admin.sensitiveword.vo.SensitiveWordRespVO;
import cn.like.cloud.module.system.controller.admin.sensitiveword.vo.SensitiveWordUpdateReqVO;
import cn.like.cloud.module.system.dal.dataobject.sensitiveword.SensitiveWordDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 敏感词 Convert
 */
@Mapper
public interface SensitiveWordConvert {

    SensitiveWordConvert INSTANCE = Mappers.getMapper(SensitiveWordConvert.class);

    SensitiveWordDO convert(SensitiveWordCreateReqVO bean);

    SensitiveWordDO convert(SensitiveWordUpdateReqVO bean);

    SensitiveWordRespVO convert(SensitiveWordDO bean);

    List<SensitiveWordRespVO> convertList(List<SensitiveWordDO> list);

    PageResult<SensitiveWordRespVO> convertPage(PageResult<SensitiveWordDO> page);

    List<SensitiveWordExcelVO> convertList02(List<SensitiveWordDO> list);

}
