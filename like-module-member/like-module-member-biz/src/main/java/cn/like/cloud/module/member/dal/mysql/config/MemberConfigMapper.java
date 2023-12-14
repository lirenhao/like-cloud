package cn.like.cloud.module.member.dal.mysql.config;

import cn.like.cloud.framework.mybatis.core.mapper.BaseMapperX;
import cn.like.cloud.module.member.dal.dataobject.config.MemberConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分设置 Mapper
 *
 * @author QingX
 */
@Mapper
public interface MemberConfigMapper extends BaseMapperX<MemberConfigDO> {
}
