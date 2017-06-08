package cn.gyyx.action.dao.wd161;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.gyyx.action.beans.wd161.Wd161ActiveBean;
import cn.gyyx.action.beans.wd161.Wd161CommentsBean;
import cn.gyyx.action.beans.wd161.Wd161RoleAccountBean;
import cn.gyyx.action.beans.wd161.Wd161VoteBean;
import cn.gyyx.action.dao.MyBatisConnectionFactory;

public class Wd161Dao {

	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	//查看绑定信息
	public Wd161RoleAccountBean getUserInfo(int userId) {
		try (SqlSession session = getSession()) {
			IWd161Mapper accountMapper = session
					.getMapper(IWd161Mapper.class);
			return accountMapper.getUserInfo(userId);
		}
	}
	//添加绑定信息
	public int addRoleAccount(Wd161RoleAccountBean accountBean,SqlSession session) {
		
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
			 int count = accountMapper.addRoleAccount(accountBean);
			return count;
		
		
	}
	//保存上传的图片地址信息
	public int savePicUrl(int userId, String picUrl) {
		try (SqlSession session = getSession()) {
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
		 int count = accountMapper.savePicUrl(userId,picUrl);
			session.commit();
			return count;
		}
	}
	//保存评论信息
	public int publishComments(String nickName, String comments,Date date) {
		try (SqlSession session = getSession()) {
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
		 int count = accountMapper.publishComments(nickName,comments,date);
			session.commit();
			return count;
		}
	}
	//查询投票记录
	public List getVoteList(Integer userCode) {
		try (SqlSession session = getSession()) {
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
			List list = accountMapper.getVoteList(userCode);
			session.commit();
			return list;
		}
	}
	
	//获取评论列表
	public List getCommentsList() {
		try (SqlSession session = getSession()) {
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
			List list =accountMapper.getCommentsList();
			session.commit();
			return list;
		}
	}
	
	//按最热获取作品列表
	public List getWorkListByHot(int pageSize,int pageNo) {
		try (SqlSession session = getSession()) {
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
			List list =accountMapper.getWorkListByHot(pageSize,pageNo);
			session.commit();
			return list;
		}
	}
	
	//按最新获取作品列表
	public List<Wd161RoleAccountBean> getWorkListByDate(int pageSize,int pageNo) {
		try (SqlSession session = getSession()) {
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
			List list =accountMapper.getWorkListByDate(pageSize,pageNo);
			session.commit();
			return list;
		}
	}
	
	//按角色获取作品列表
	public List<Wd161RoleAccountBean> getWorkListByRole(String type,int pageSize,int pageNo) {
		try (SqlSession session = getSession()) {
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
			List list =accountMapper.getWorkListByRole(type,pageSize,pageNo);
			session.commit();
			return list;
		}
	}
	
	//获取作品列表总数
	public int getWorkCount() {
		try (SqlSession session = getSession()) {
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
			int count =accountMapper.getWorkCount();
			session.commit();
			return count;
		}
	}
	
	//获取top10
	public List<Wd161RoleAccountBean> getTopTen() {
		try (SqlSession session = getSession()) {
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
			List list =accountMapper.getTopTen();
			session.commit();
			return list;
		}
	}
	
	//获取作品列表总数-搜索
	public int getWorkCountBySearch(String roleName) {
		try (SqlSession session = getSession()) {
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
			int count =accountMapper.getWorkCountBySearch(roleName);
			session.commit();
			return count;
		}
	}
	
	//查看对所选作品- 已投票数
	public int getcastedVotes(Integer userId, String roleName, String roleCode, String serverCode,
			String serverName) {
		try (SqlSession session = getSession()) {
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
			int count =accountMapper.getcastedVotes(userId,roleName,roleCode,serverCode,serverName);
			session.commit();
			return count;
		}
	}
	
	//获取活跃度信息
	public Wd161ActiveBean getActiveInfo(String userName,String serverName,String gid ,Integer scoreDate) {
		try (SqlSession session = getSession()) {
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
			Wd161ActiveBean bean =accountMapper.getActiveInfo(userName,serverName,gid,scoreDate);
			session.commit();
			return bean;
		}
	}
	
	//投票成功-更新角色表
	public int updateRoleAccountBean(Wd161RoleAccountBean accountBean,SqlSession session) {
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
			int count =accountMapper.updateRoleAccountBean(accountBean);
			return count;
	}
	
	//投票成功-插入投票记录表
	public int insertVoteBean(Wd161VoteBean voteBean,SqlSession session) {
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
			int count =accountMapper.insertVoteBean(voteBean);
			return count;
	}
	
	//投票成功-更新活跃度表
	public int updateActiveBean(Wd161ActiveBean activeBean,SqlSession session) {
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
			int count =accountMapper.updateActiveBean(activeBean);
			return count;
		
	}
	//通过serverCode获取用户信息
	public Wd161RoleAccountBean getUserInfoByServer(String serverCode, String serverName, String roleName,String gid) {
		try (SqlSession session = getSession()) {
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
			Wd161RoleAccountBean roleBean =accountMapper.getUserInfoByServer(serverCode,serverName,roleName,gid);
			session.commit();
			return roleBean;
		}
	}
	//获取投票记录，每5票有一次抽奖机会
	public List getVoteList4Lottery(Integer userId, SqlSession sqlSession) {
		IWd161Mapper accountMapper = sqlSession.getMapper(IWd161Mapper.class);
		List list =accountMapper.getVoteList4Lottery(userId);
		return list;
	}
	//获取用户信息
	public Wd161RoleAccountBean getUserInfoByAccount(String account, int serverCode, String serverName) {
		try (SqlSession session = getSession()) {
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
			Wd161RoleAccountBean roleBean =accountMapper.getUserInfoByAccount(account,serverCode,serverName);
			session.commit();
			return roleBean;
		}
	}
	//重新上传，重置pic及审核数据
	public int reloadPicUrl(Integer userId) {
		try (SqlSession session = getSession()) {
			IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
		 int count = accountMapper.reloadPicUrl(userId);
			session.commit();
			return count;
		}
	}
	/***********************************OA后台*****************************************/
	//pic总数
	public int getPicCount(String beginTime, String endTime, String state) {
		try (SqlSession session = getSession()) {
			IWd161Mapper oaMapper = session.getMapper(IWd161Mapper.class);
			int count =oaMapper.getPicCount(beginTime,endTime,state);
			session.commit();
			return count;
		}
	}
	//pic列表
	public List<Wd161RoleAccountBean> getPicList(String beginTime, String endTime, String state, int pageSize,
			int pageNo) {
		try (SqlSession session = getSession()) {
			IWd161Mapper oaMapper = session.getMapper(IWd161Mapper.class);
			List list = oaMapper.getPicList(beginTime,endTime,state,pageSize,pageNo);
			session.commit();
			return list;
		}
	}
	//审核pic
	public int checkPics(String userName, String state, Integer staffCode, String realName,SqlSession session) {
			IWd161Mapper oaMapper = session.getMapper(IWd161Mapper.class);
			int count = oaMapper.checkPics(userName,state,staffCode,realName);
			return count;
		
	}
	//评论总数
	public int getCommentsCount(String beginTime, String endTime, String state) {
		try (SqlSession session = getSession()) {
			IWd161Mapper oaMapper = session.getMapper(IWd161Mapper.class);
			int count =oaMapper.getCommentsCount(beginTime,endTime,state);
			session.commit();
			return count;
		}
	}
	//评论列表
	public List<Wd161CommentsBean> commentsList(String beginTime, String endTime, String state, int pageSize,
			int pageNo) {
		try (SqlSession session = getSession()) {
			IWd161Mapper oaMapper = session.getMapper(IWd161Mapper.class);
			List list = oaMapper.commentsList(beginTime,endTime,state,pageSize,pageNo);
			session.commit();
			return list;
		}
	}
	//审核评论
	public int checkComments(int code, String state, Integer staffCode, String realName) {
		try (SqlSession session = getSession()) {
			IWd161Mapper oaMapper = session.getMapper(IWd161Mapper.class);
			int count = oaMapper.checkComments(code,state,staffCode,realName);
			session.commit();
			return count;
		}
	}
	//批量审核pic
	public int batchCheckPics(Integer userId, String state, Integer staffCode, String realName, SqlSession session) {
		IWd161Mapper oaMapper = session.getMapper(IWd161Mapper.class);
		int count = oaMapper.batchCheckPics(userId,state,staffCode,realName);
		return count;
	}
	//批量审核前，先查询
	public Wd161CommentsBean getCommentsByCode(Integer code,SqlSession session) {
		IWd161Mapper oaMapper = session.getMapper(IWd161Mapper.class);
		Wd161CommentsBean bean = oaMapper.getCommentsByCode(code);
		return bean;
	}
	//批量审核comments
	public int batchCheckComments(Integer code, String state, Integer staffCode, String realName, SqlSession session) {
		IWd161Mapper oaMapper = session.getMapper(IWd161Mapper.class);
		int count = oaMapper.batchCheckComments(code,state,staffCode,realName);
		return count;
	}
	//审核前，先查询
	public Wd161CommentsBean getCommentsByCode(int code) {
		try (SqlSession session = getSession()) {
			IWd161Mapper oaMapper = session.getMapper(IWd161Mapper.class);
			Wd161CommentsBean bean = oaMapper.getCommentsByCode(code);
			session.commit();
			return bean;
		}
	}
	//获取用户信息-session
	public Wd161RoleAccountBean getUserInfoSession(Integer userId, SqlSession session) {
		IWd161Mapper accountMapper = session.getMapper(IWd161Mapper.class);
		return accountMapper.getUserInfo(userId);
	}
	
	
	
}
