package cn.gyyx.action.bll.wd161;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.wd161.Wd161ActiveBean;
import cn.gyyx.action.beans.wd161.Wd161CommentsBean;
import cn.gyyx.action.beans.wd161.Wd161RoleAccountBean;
import cn.gyyx.action.beans.wd161.Wd161VoteBean;
import cn.gyyx.action.bll.jswswxsign.DateTools;
import cn.gyyx.action.dao.wd161.Wd161Dao;
import cn.gyyx.action.dao.wdninestory.QualificationDAO;

public class Wd161Bll {

	private Wd161Dao wd161Dao = new Wd161Dao();
	private QualificationDAO  qualificationDao= new QualificationDAO();
	//添加绑定信息
	public Boolean addRoleAccount(Wd161RoleAccountBean accountBean,SqlSession session) {
		return wd161Dao.addRoleAccount(accountBean,session) >0;
	}

	//查看绑定信息
	public Wd161RoleAccountBean getUserInfo(int userId) {
		return wd161Dao.getUserInfo(userId);
	}
	
	//保存上传的图片地址信息
	public Boolean savePicUrl(int userId, String picUrl) {
		 return wd161Dao.savePicUrl(userId,picUrl) >0;
	}
	
	//发布评论
	public Boolean publishComments(String nickName, String comments,Date date) {
		return wd161Dao.publishComments(nickName,comments,date) >0;
	}
	//查看投票情况
	public List getVoteList(Integer userId) {
		return wd161Dao.getVoteList(userId);
	}
	//获取评论列表
	public List  getCommentsList() {
		
		return wd161Dao.getCommentsList();
	}
	//map工具
	public Map<String, String> covertBeanToMap(Wd161CommentsBean b) {
		Map<String,String> map = new HashMap<>();
		map.put("comments", b.getComments());
		map.put("nickName", b.getNickName());
		return map;
	}
	//按最热获取作品列表
	public List getWorkListByHot(int pageSize,int pageNo) {
		
		return wd161Dao.getWorkListByHot(pageSize,pageNo);
	}
	//按最新获取作品列表
	public List<Wd161RoleAccountBean> getWorkListByDate(int pageSize,int pageNo) {
		return wd161Dao.getWorkListByDate(pageSize,pageNo);
	}
	//按角色名获取作品列表
	public List<Wd161RoleAccountBean> getWorkListByRole(String type,int pageSize,int pageNo) {
		return wd161Dao.getWorkListByRole(type,pageSize,pageNo);
	}
	//获取作品列表总数
	public int getWorkCount() {
		return wd161Dao.getWorkCount();
	}
	//获取top10
	public List<Wd161RoleAccountBean> getTopTen() {
		return wd161Dao.getTopTen();
	}
	//获取作品列表总数-通过搜索
	public int getWorkCountBySearch(String roleName) {
		return wd161Dao.getWorkCountBySearch(roleName);
	}
	//查看对所选作品- 已投票数
	public int getcastedVotes(Integer userId, String roleName, String roleCode, String serverCode, String serverName) {
		return wd161Dao.getcastedVotes(userId,roleName,roleCode,serverCode,serverName);
	}
	//获取活跃度信息
	public Wd161ActiveBean getActiveInfo(String userName,String serverName,String gid,Integer scoreDate) {
		return wd161Dao.getActiveInfo(userName,serverName,gid,scoreDate);
	}
	//投票成功-更新
	public int updateRoleAccountBean(Wd161RoleAccountBean accountBean,SqlSession session) {
		return wd161Dao.updateRoleAccountBean(accountBean,session);
		
	}
	//投票成功-插入
	public int insertVoteBean(Wd161VoteBean voteBean,SqlSession session) {
		return wd161Dao.insertVoteBean(voteBean,session);
		
	}
	//投票成功-更新
	public int updateActiveBean(Wd161ActiveBean activeBean,SqlSession session) {
		return wd161Dao.updateActiveBean(activeBean,session);
		
	}
	//通过区服角色信息获取玩家绑定信息
	public Wd161RoleAccountBean getUserInfoByServer(String serverCode, String serverName, String roleName,String gid) {
		return wd161Dao.getUserInfoByServer(serverCode,serverName,roleName,gid);
	}
	//增加一次抽奖机会
	public void addLotteryTime(int actionCode, int userCode,SqlSession session) {
		qualificationDao.addOneLotteryTime(userCode, actionCode, session);
		
	}
	//查看投票情况，每5票可以增加一次抽奖机会
	public List getVoteList4Lottery(Integer userId, SqlSession sqlSession) {
		return wd161Dao.getVoteList4Lottery(userId,sqlSession);
	}
	//通过账号获取玩家绑定信息
	public Wd161RoleAccountBean getUserInfoByAccount(String account, int serverCode, String serverName) {
		return wd161Dao.getUserInfoByAccount(account,serverCode,serverName);
	}
	//重新上传，更新角色表信息
	public Boolean reloadPicUrl(Integer userId) {
		 return wd161Dao.reloadPicUrl(userId) >0;
	}
	
	//增加一个抽奖机会数据次数为0
	public Boolean addOneLotteryData(int userCode,int actionCode, SqlSession session ) {
		 return qualificationDao.addOneLotteryData(userCode,actionCode, session) >0;
	}
	/**********************************************************************************/
	/***********************************OA后台*****************************************/
	/**********************************************************************************/
	
	//作品总数-分页
	public int getPicCount(String beginTime, String endTime, String state) {
		return wd161Dao.getPicCount(beginTime,endTime,state);
	}
	//作品列表-分页
	public List<Wd161RoleAccountBean> getPicList(String beginTime, String endTime, String state, int pageSize,
			int pageNo) {
		return wd161Dao.getPicList(beginTime,endTime,state,pageSize,pageNo);
	}
	//审核作品 
	public Boolean checkPics(String userName, String state, Integer staffCode, String realName,SqlSession session) {
		return wd161Dao.checkPics(userName,state,staffCode,realName,session)>0;
	}
	public Boolean addOneLotteryTime(int userCode, int actionCode,SqlSession session ) {
		return qualificationDao.addLotteryTime(userCode,actionCode,session)>0;
	}
	//评论总数-分页
	public int getCommentsCount(String beginTime, String endTime, String state) {
		return wd161Dao.getCommentsCount(beginTime,endTime,state);
	}
	//评论列表-分页
	public List<Wd161CommentsBean> commentsList(String beginTime, String endTime, String state, int pageSize,
			int pageNo) {
		return wd161Dao.commentsList(beginTime,endTime,state,pageSize,pageNo);
	}
	//审核评论
	public Boolean checkComments(int  code, String state, Integer staffCode, String realName) {
		return wd161Dao.checkComments(code,state,staffCode,realName)>0;
	}
	//批量审核作品
	public Boolean batchCheckPics(Integer userId, String state, Integer staffCode, String realName,
			SqlSession session) {
		return wd161Dao.batchCheckPics(userId,state,staffCode,realName,session)>0;
	}
	//通过code查询评论-session
	public Wd161CommentsBean getCommentsByCode(Integer code,SqlSession session) {
		return wd161Dao.getCommentsByCode(code,session);
	}
	//批量审核评论
	public Boolean batchCheckComments(Integer code, String state, Integer staffCode, String realName,
			SqlSession session) {
		return wd161Dao.batchCheckComments(code,state,staffCode,realName,session)>0;
	}
	//通过code查询评论
	public Wd161CommentsBean getCommentsByCode(int code) {
		return wd161Dao.getCommentsByCode(code);
	}
	//通过userId查询用户信息-session
	public Wd161RoleAccountBean getUserInfoSession(Integer userId, SqlSession session) {
		return wd161Dao.getUserInfoSession(userId,session);
	}
}
