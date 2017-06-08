/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：问道十周年粉丝榜
 * @作者：chenpeilan
 * @联系方式：chenpeilan@gyyx.cn
 * @创建时间： 2016年3月29日
 * @版本号：
 * @本类主要用途描述：问道十周年粉丝榜活动评论相关DAO
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.wdtenyearfans;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.action.beans.wdtenyearfans.PageBean;
import cn.gyyx.action.beans.wdtenyearfans.WdCommentsBean;

import org.apache.ibatis.session.SqlSession;

public class WdCommentsDAO {
	SqlSessionFactory factory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory(); 
	public void insertWdCommentsBean(WdCommentsBean wdCommentsBean) {
		try(SqlSession session = factory.openSession()) {
			IWdCommentsBean mapper = session.getMapper(IWdCommentsBean.class);
			mapper.insertWdCommentsBean(wdCommentsBean);
			session.commit();
		}
	}
	public WdCommentsBean selectWdCommentsBeanByCode(int code) {
		try(SqlSession session = factory.openSession()) {
			IWdCommentsBean mapper = session.getMapper(IWdCommentsBean.class);
			return mapper.selectWdCommentsBeanByCode(code);
		}
	}
	public void updateWdCommentsBean(WdCommentsBean wdCommentsBean) {
		try(SqlSession session = factory.openSession()) {
			IWdCommentsBean mapper = session.getMapper(IWdCommentsBean.class);
			mapper.updateWdCommentsBean(wdCommentsBean);
			session.commit();
		}
	}
	public List<WdCommentsBean> selectWdCommentsBeanList(PageBean pageBean){
		try(SqlSession session = factory.openSession()) {
			IWdCommentsBean mapper = session.getMapper(IWdCommentsBean.class);
			return mapper.selectWdCommentsBeanList(pageBean);
		}
	}
	public List<WdCommentsBean> selectDeleteWdCommentsBeanList(PageBean pageBean){
		try(SqlSession session = factory.openSession()) {
			IWdCommentsBean mapper = session.getMapper(IWdCommentsBean.class);
			return mapper.selectDeleteWdCommentsBeanList(pageBean);
		}
	}
	public void updateFlag(WdCommentsBean bean) {
		try(SqlSession session = factory.openSession()) {
			IWdCommentsBean mapper = session.getMapper(IWdCommentsBean.class);
			mapper.updateFlag(bean);
			session.commit();
		}
	}
	public int selectWdCommentsCount(PageBean pageBean){
		try(SqlSession session = factory.openSession()) {
			IWdCommentsBean mapper = session.getMapper(IWdCommentsBean.class);
			return mapper.selectWdCommentsCount(pageBean);
		}
	}
	public int selectDeleteWdCommentsCount(PageBean pageBean){
		try(SqlSession session = factory.openSession()) {
			IWdCommentsBean mapper = session.getMapper(IWdCommentsBean.class);
			return mapper.selectDeleteWdCommentsCount(pageBean);
		}
	}
	public List<WdCommentsBean> selectWdCommentsBean(int nominationCode) {
		try(SqlSession session = factory.openSession()) {
			IWdCommentsBean mapper = session.getMapper(IWdCommentsBean.class);
			return mapper.selectWdCommentsBean(nominationCode);
		}
	}
}