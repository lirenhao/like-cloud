package cn.like.cloud.module.member.dal.mysql.level;

import cn.like.cloud.framework.common.pojo.PageResult;
import cn.like.cloud.framework.mybatis.core.mapper.BaseMapperX;
import cn.like.cloud.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.like.cloud.module.member.controller.admin.level.vo.record.MemberLevelRecordPageReqVO;
import cn.like.cloud.module.member.dal.dataobject.level.MemberLevelRecordDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员等级记录 Mapper
 *
 * @author owen
 */
@Mapper
public interface MemberLevelRecordMapper extends BaseMapperX<MemberLevelRecordDO> {

    default PageResult<MemberLevelRecordDO> selectPage(MemberLevelRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MemberLevelRecordDO>()
                .eqIfPresent(MemberLevelRecordDO::getUserId, reqVO.getUserId())
                .eqIfPresent(MemberLevelRecordDO::getLevelId, reqVO.getLevelId())
                .betweenIfPresent(MemberLevelRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MemberLevelRecordDO::getId));
    }

}
