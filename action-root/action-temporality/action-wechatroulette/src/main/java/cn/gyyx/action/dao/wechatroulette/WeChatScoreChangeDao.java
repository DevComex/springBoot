/**
  * -------------------------------------------------------------------------
  * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
  * @版权所有：北京光宇在线科技有限责任公司
  * @项目名称：action-wechatroulette
  * @作者：caishuai
  * @联系方式：caishuai@gyyx.cn
  * @创建时间：2017年3月21日 下午5:55:56
  * @版本号：0.0.1
  *-------------------------------------------------------------------------
  */
package cn.gyyx.action.dao.wechatroulette;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.common.ActionWXjifenChangeLogBean;
import cn.gyyx.action.dao.MyBatisBaseDAO;

/**
 * <p>
 * 积分变更日志记录数据库操作
 * </p>
 * 
 * @author caishuai
 * @since 0.0.1
 */
public class WeChatScoreChangeDao extends MyBatisBaseDAO {
    /**
     * 
     * <p>
     * 新增一条日志，by session
     * </p>
     *
     * @action caishuai 2017年3月22日 上午9:48:51 描述
     *
     * @param jfchangeBean
     *            积分改变操作日志记录实体类
     * @param sqlSession
     *            void
     */
    public void insertReduceJfLog(ActionWXjifenChangeLogBean jfchangeBean,
            SqlSession sqlSession) {
        WeChatScoreChangeMapper mapper = sqlSession
                .getMapper(WeChatScoreChangeMapper.class);
        mapper.insertReduceJfLog(jfchangeBean);
    }

}
