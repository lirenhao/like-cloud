package cn.like.cloud.module.infra.dal.mysql.db;

import cn.like.cloud.framework.mybatis.core.mapper.BaseMapperX;
import cn.like.cloud.module.infra.dal.dataobject.db.DataSourceConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapperX<DataSourceConfigDO> {
}
