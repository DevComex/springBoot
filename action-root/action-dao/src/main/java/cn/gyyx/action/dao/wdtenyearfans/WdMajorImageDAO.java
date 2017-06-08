/*-------------------------------------------------------------------------
* 版权所有：北京光宇在线科技有限责任公司
* 作者：chenpeilan
* 联系方式：chenpeilan@gyyx.cn
* 创建时间： 2016年3月31日
* 版本号：
* 本类主要用途描述：问道十周年粉丝榜活动职业获取图片相关DAO
-------------------------------------------------------------------------*/
package cn.gyyx.action.dao.wdtenyearfans;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wdtenyearfans.WdMajorImageBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

/**
 * @ClassName: WdMajorImageDAO
 * @Description: TODO 问道十周年粉丝榜活动职业获取图片相关DAO
 * @author chenpeilan chenpeilan@gyyx.cn
 * @date 2016年3月31日 下午4:08:36
 *
 */
public class WdMajorImageDAO {
	SqlSessionFactory factory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory(); 
	
	public WdMajorImageBean selectImageByMajor(int type,int major,int sex) {
		try(SqlSession session = factory.openSession()) {
			IWdMajorImageBean  mapper = session.getMapper(IWdMajorImageBean.class);
			return mapper.selectImageByMajor(type, major, sex);
		}
	}
}
