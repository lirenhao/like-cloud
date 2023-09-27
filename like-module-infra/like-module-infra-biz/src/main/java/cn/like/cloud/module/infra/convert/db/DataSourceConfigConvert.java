package cn.like.cloud.module.infra.convert.db;

import cn.like.cloud.module.infra.controller.admin.db.vo.DataSourceConfigCreateReqVO;
import cn.like.cloud.module.infra.controller.admin.db.vo.DataSourceConfigRespVO;
import cn.like.cloud.module.infra.controller.admin.db.vo.DataSourceConfigUpdateReqVO;
import cn.like.cloud.module.infra.dal.dataobject.db.DataSourceConfigDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 数据源配置 Convert
 */
@Mapper
public interface DataSourceConfigConvert {

    DataSourceConfigConvert INSTANCE = Mappers.getMapper(DataSourceConfigConvert.class);

    DataSourceConfigDO convert(DataSourceConfigCreateReqVO bean);

    DataSourceConfigDO convert(DataSourceConfigUpdateReqVO bean);

    DataSourceConfigRespVO convert(DataSourceConfigDO bean);

    List<DataSourceConfigRespVO> convertList(List<DataSourceConfigDO> list);

}
