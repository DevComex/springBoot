/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年5月18日上午10:45:03
 * 版本号：v1.0
 * 本类主要用途叙述：官方新手卡
 */

package cn.gyyx.action.dao.giftbaginterface;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.giftinterface.OfficialGiftBagBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class OfficialGiftBagDao {
	/**
	 * 
	 * @Title: getSession
	 * @Description: TODO 获取session
	 * @return SqlSession
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	public List<OfficialGiftBagBean> getOfficialGiftBagBeans(Date begin,
			Date end,String serverName) {
		try (SqlSession sqlSession = getSession()) {
			IOfficialGiftBag iOfficialGiftBag = sqlSession
					.getMapper(IOfficialGiftBag.class);
			return iOfficialGiftBag.getOfficialGiftBagBean(begin, end,serverName);
		}
	}

}
