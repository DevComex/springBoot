/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年5月17日下午4:57:46
 * 版本号：v1.0
 * 本类主要用途叙述：媒体礼包的数据库
 */

package cn.gyyx.action.dao.giftbaginterface;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.giftinterface.MediaGiftBagBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class MediaGiftDao {
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

	public List<MediaGiftBagBean> getMediaGiftBagBeans(Date begin, Date end,
			int serverid) {
		try (SqlSession sqlSession = getSession()) {
			IMediaGift iMediaGift = sqlSession.getMapper(IMediaGift.class);
			return iMediaGift.getMediaGiftBagBeans(begin, end, serverid);
		}
	}

}
