package cn.like.cloud.module.system.convert.logger;

import cn.like.cloud.framework.common.pojo.PageResult;
import cn.like.cloud.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.like.cloud.module.system.controller.admin.logger.vo.loginlog.LoginLogExcelVO;
import cn.like.cloud.module.system.controller.admin.logger.vo.loginlog.LoginLogRespVO;
import cn.like.cloud.module.system.dal.dataobject.logger.LoginLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LoginLogConvert {

    LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);

    PageResult<LoginLogRespVO> convertPage(PageResult<LoginLogDO> page);

    List<LoginLogExcelVO> convertList(List<LoginLogDO> list);

    LoginLogDO convert(LoginLogCreateReqDTO bean);

}
