package cn.like.cloud.module.infra.convert.file;

import cn.like.cloud.framework.common.pojo.PageResult;
import cn.like.cloud.module.infra.controller.admin.file.vo.file.FileRespVO;
import cn.like.cloud.module.infra.dal.dataobject.file.FileDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileConvert {

    FileConvert INSTANCE = Mappers.getMapper(FileConvert.class);

    FileRespVO convert(FileDO bean);

    PageResult<FileRespVO> convertPage(PageResult<FileDO> page);

}
