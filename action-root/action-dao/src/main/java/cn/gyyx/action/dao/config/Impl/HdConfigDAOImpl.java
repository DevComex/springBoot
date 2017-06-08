package cn.gyyx.action.dao.config.Impl;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.config.po.ActionConfigPO;
import cn.gyyx.action.dao.MyBatisBaseDAO;
import cn.gyyx.action.dao.config.IHdConfigDAO;
import cn.gyyx.action.dao.config.mapper.IHdConfigMapper;

public class HdConfigDAOImpl extends MyBatisBaseDAO implements IHdConfigDAO {

	@Override
    public ActionConfigPO getData(int activityId) {
        ActionConfigPO result = null;

        try(SqlSession session = this.getSession(true)) {
            IHdConfigMapper mapper = session.getMapper(IHdConfigMapper.class);

            result = mapper.getData(activityId);
        } catch (Exception e) {
            logger.error("HdConfigDAOImpl.getData => activityId=" + activityId,
                e);
        }

        return result;
    }
}
