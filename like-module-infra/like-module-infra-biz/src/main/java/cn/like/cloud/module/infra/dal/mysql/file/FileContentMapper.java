package cn.like.cloud.module.infra.dal.mysql.file;

import cn.like.cloud.framework.mybatis.core.mapper.BaseMapperX;
import cn.like.cloud.module.infra.dal.dataobject.file.FileContentDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileContentMapper extends BaseMapperX<FileContentDO> {
}
