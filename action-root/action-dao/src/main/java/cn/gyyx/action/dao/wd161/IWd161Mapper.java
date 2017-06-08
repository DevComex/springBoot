package cn.gyyx.action.dao.wd161;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import cn.gyyx.action.beans.wd161.Wd161ActiveBean;
import cn.gyyx.action.beans.wd161.Wd161CommentsBean;
import cn.gyyx.action.beans.wd161.Wd161RoleAccountBean;
import cn.gyyx.action.beans.wd161.Wd161VoteBean;

public interface IWd161Mapper {

	//查询用户绑定信息
	Wd161RoleAccountBean getUserInfo(@Param("userCode") int userCode);
	
	//添加用户绑定角色信息
	int addRoleAccount(Wd161RoleAccountBean accountBean);
	
	//保存上传的图片地址信息
	int savePicUrl(@Param("userCode") int userCode, @Param("picUrl") String picUrl);
	
	//发布评论
	int publishComments(@Param("nickName") String nickName, @Param("comments") String comments,@Param("creatTime") Date createTime);

	//查询投票记录
	List getVoteList(@Param("userCode") Integer userCode);

	//获取评论列表
	List getCommentsList();
	//按最热获取作品列表
	List getWorkListByHot(@Param("pageSize")Integer pageSize,@Param("pageNo") Integer pageNo);
	//按最新获取作品列表
	List getWorkListByDate(@Param("pageSize")Integer pageSize,@Param("pageNo") Integer pageNo);
	//按角色获取作品列表
	List getWorkListByRole(@Param("roleName")String roleName,@Param("pageSize")Integer pageSize,@Param("pageNo") Integer pageNo);
	//获取作品列表总数
	Integer getWorkCount();
	//获取top10
	List getTopTen();
	//获取作品列表总数-搜索
	Integer getWorkCountBySearch(@Param("roleName")String roleName);
	//获取活跃度信息
	Wd161ActiveBean getActiveInfo(@Param("userName") String userName,@Param("serverName")String serverName,
			@Param("gid")String gid ,@Param("scoreDate")Integer scoreDate);
	//查看对所选作品- 已投票数
	int getcastedVotes(@Param("userCode") Integer userCode,
			@Param("roleName")String roleName, 
			@Param("roleCode")String roleCode, 
			@Param("serverCode")String serverCode,
			@Param("serverName")String serverName);

	//投票成功-更新表
	int updateRoleAccountBean(Wd161RoleAccountBean accountBean);
	//投票成功-更新表
	int insertVoteBean(Wd161VoteBean voteBean);
	//投票成功-更新表
	int updateActiveBean(Wd161ActiveBean activeBean);
	//按server查询用户信息
	Wd161RoleAccountBean getUserInfoByServer(@Param("serverCode")String serverCode, @Param("serverName")String serverName, 
			@Param("roleName")String roleName,@Param("gid")String gid);

	//查询投票记录
	List getVoteList4Lottery(@Param("userCode") Integer userCode);
	//按account查询用户信息
	Wd161RoleAccountBean getUserInfoByAccount(@Param("userName")String userName, @Param("serverCode")int serverCode, @Param("serverName")String serverName);
	//更新用户状态到未上传
	int reloadPicUrl(@Param("userCode") int userCode);

	/***********************************OA后台*****************************************/
	
	//分页-获取过滤的总数
	int getPicCount(@Param("beginTime")String beginTime, @Param("endTime")String endTime, @Param("auditStatus") String auditStatus);
	//分页-获取过滤的数据列表
	List getPicList(@Param("beginTime")String beginTime, @Param("endTime")String endTime, @Param("auditStatus") String auditStatus,
			@Param("pageSize")int pageSize,@Param("pageNo")int pageNo);
	//审核pic
	int checkPics(@Param("userName")String userName, @Param("auditStatus")String auditStatus,
				@Param("staffCode")Integer staffCode, @Param("realName")String realName);
	//分页-评论列表
	List commentsList(@Param("beginTime")String beginTime, @Param("endTime")String endTime, @Param("auditStatus") String auditStatus,
			@Param("pageSize")int pageSize,@Param("pageNo")int pageNo);
	//分页-评论总数
	int getCommentsCount(@Param("beginTime")String beginTime, @Param("endTime")String endTime, @Param("auditStatus") String auditStatus);
	//审核评论 
	int checkComments(@Param("code")int code, @Param("auditStatus")String auditStatus,
			@Param("staffCode")Integer staffCode, @Param("realName")String realName);
	//pic批量审核
	int batchCheckPics(@Param("userId")Integer userId, @Param("auditStatus")String auditStatus,
				@Param("staffCode")Integer staffCode, @Param("realName")String realName);
	//comments批量审核
	int batchCheckComments(@Param("code")Integer code, @Param("auditStatus")String auditStatus,
			@Param("staffCode")Integer staffCode, @Param("realName")String realName);
	//批量审核前先查询
	Wd161CommentsBean getCommentsByCode(@Param("code")Integer code);


}
