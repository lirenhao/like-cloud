package cn.like.cloud.module.system.dal.mysql.mail;

import cn.like.cloud.framework.common.pojo.PageResult;
import cn.like.cloud.framework.mybatis.core.mapper.BaseMapperX;
import cn.like.cloud.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.like.cloud.module.system.controller.admin.mail.vo.account.MailAccountPageReqVO;
import cn.like.cloud.module.system.dal.dataobject.mail.MailAccountDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MailAccountMapper extends BaseMapperX<MailAccountDO> {

    default PageResult<MailAccountDO> selectPage(MailAccountPageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<MailAccountDO>()
                .likeIfPresent(MailAccountDO::getMail, pageReqVO.getMail())
                .likeIfPresent(MailAccountDO::getUsername, pageReqVO.getUsername()));
    }

}
