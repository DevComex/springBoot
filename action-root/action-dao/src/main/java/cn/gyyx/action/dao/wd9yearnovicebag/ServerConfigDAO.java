/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：周忠凯 王雷
 * 联系方式：zhouzhongkai@gyyx.cn wanglei@gyyx.cn 
 * 创建时间：2015年03月25日 下午4:57:58 
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 
-------------------------------------------------------------------------*/
package cn.gyyx.action.dao.wd9yearnovicebag;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import cn.gyyx.action.beans.wd9yearnovicebag.ActionServerConfigBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ServerConfigDAO {

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ServerConfigDAO.class);
	IServerConfigMapper iServerConfigMapper;

	/**
	 * @Title: setReceiveLog
	 * @Author:zhouzhongkai wanglei
	 * @date 2015年03月27日 下午7:12:45
	 * @Description: TODO 根据活动id查询区组
	 * @param param
	 * 
	 */
	public List<String> selectServerConfigInfo(Integer activityId) {
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		List<String> resultList = null;
		try {
			iServerConfigMapper = session.getMapper(IServerConfigMapper.class);
			resultList = iServerConfigMapper.selectServerConfigInfo(activityId);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return resultList;

	}

	/**
	 * @Title: setReceiveLog
	 * @Author: zhouzhongkai wanglei
	 * @date 2015年03月27日 下午7:12:45
	 * @Description: TODO 根据区组查询服务期信息
	 * @param param
	 * 
	 */
	public List<ActionServerConfigBean> selectServerConfigInfoBy(
			ActionServerConfigBean actionServerConfigBean) {
		SqlSession session = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory().openSession();
		List<ActionServerConfigBean> resultList = null;
		try {
			iServerConfigMapper = session.getMapper(IServerConfigMapper.class);
			resultList = iServerConfigMapper
					.selectServerConfigInfoBy(actionServerConfigBean);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return resultList;

	}
}
