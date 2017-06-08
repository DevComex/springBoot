/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：wd weChat scratchCard
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2015年12月25日
 * @版本号：
 * @本类主要用途描述：获取当前用户当天获奖记录
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wechat;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * @ClassName: WeiXinSendpresentDAO
 * @Description: TODO 获取当前用户当天获奖记录
 * @author chenpeilan chenpeilan@gyyx.cn
 * @date 2015年12月25日 下午4:14:49
 *
 */
public class WeiXinSendpresentDAO {
	
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(WeiXinSendpresentDAO.class);
	
	/**
	 * 
	* @Title: getSession
	* @Description: TODO 获取session
	* @param @return    
	* @return SqlSession    
	* @throws
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	
	/**
	 * 
	* @Title: getUserInfoByOpenIdAndDate
	* @Description: TODO 获取当前用户当天获奖记录
	* @param @param account
	* @param @param time
	* @param @return    
	* @return List<UserInfoBean>    
	* @throws
	 */
	public List<UserInfoBean> getUserInfoByOpenIdAndDate(String account, String time) {
		List<UserInfoBean> list = null;
		SqlSession session = getSession();
		try {
			IWeiXinSendpresent sendpresentMapper = session
					.getMapper(IWeiXinSendpresent.class);
			list = sendpresentMapper.getUserInfoByOpenIdAndDate(account, time);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return list;
	}
	
	/**
	 * 
	* @Title: getUserCount
	* @Description: TODO 获取用户参与量（100%中奖）
	* @param @param actionCode
	* @param @return    
	* @return Integer    
	* @throws
	 */
	public Integer getUserCount(Integer actionCode) {
		SqlSession session = getSession();
		Integer count = -1;
		try {
			IWeiXinSendpresent sendpresentMapper = session
					.getMapper(IWeiXinSendpresent.class);
			count = sendpresentMapper.getUserCount(actionCode);
		} catch (Exception e) {
			logger.warn(e.toString());
		} finally {
			session.close();
		}
		return count;
	}
}
