/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2015年3月20日下午12:45:56
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */



package cn.gyyx.action.dao.lottery;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.lottery.OrderAndPrizeBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class PrizeAndOrderDao implements IPrizeAndOrder {
	/**
	 * 获取session，
	 * 
	 * @throws IOException
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	@Override
	/**
	 * 得到奖品名次对应信息的dao
	 */
	public List<OrderAndPrizeBean> getPrizeInfo(int actionCode) {
		// TODO Auto-generated method stub
		try (SqlSession sqlSession = getSession()) {
			IPrizeAndOrder iPrizeAndOrder = sqlSession.getMapper(IPrizeAndOrder.class);
			return iPrizeAndOrder.getPrizeInfo(actionCode);
		}
	}
	/**
	 * 插入奖品配置
	 * */
	public void addActionConfig(OrderAndPrizeBean o){
		SqlSession sqlSession = getSession();
		try{
			IPrizeAndOrder dao = sqlSession.getMapper(IPrizeAndOrder.class);
			dao.addActionConfig(o);
		}finally{
			sqlSession.commit();
			sqlSession.close();
		}
	}
}
