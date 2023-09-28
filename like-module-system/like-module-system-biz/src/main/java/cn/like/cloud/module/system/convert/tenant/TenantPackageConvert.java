package cn.like.cloud.module.system.convert.tenant;

import cn.like.cloud.framework.common.pojo.PageResult;
import cn.like.cloud.module.system.controller.admin.tenant.vo.packages.TenantPackageCreateReqVO;
import cn.like.cloud.module.system.controller.admin.tenant.vo.packages.TenantPackageRespVO;
import cn.like.cloud.module.system.controller.admin.tenant.vo.packages.TenantPackageSimpleRespVO;
import cn.like.cloud.module.system.controller.admin.tenant.vo.packages.TenantPackageUpdateReqVO;
import cn.like.cloud.module.system.dal.dataobject.tenant.TenantPackageDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 租户套餐 Convert
 */
@Mapper
public interface TenantPackageConvert {

    TenantPackageConvert INSTANCE = Mappers.getMapper(TenantPackageConvert.class);

    TenantPackageDO convert(TenantPackageCreateReqVO bean);

    TenantPackageDO convert(TenantPackageUpdateReqVO bean);

    TenantPackageRespVO convert(TenantPackageDO bean);

    List<TenantPackageRespVO> convertList(List<TenantPackageDO> list);

    PageResult<TenantPackageRespVO> convertPage(PageResult<TenantPackageDO> page);

    List<TenantPackageSimpleRespVO> convertList02(List<TenantPackageDO> list);

}
