package cn.gyyx.action.service.wd161;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.challenger.OperationLogBean;
import cn.gyyx.action.beans.common.ActionConfigBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.beans.wd10yearcoser.ResourceBean;
import cn.gyyx.action.beans.wd161.Wd161CommentsBean;
import cn.gyyx.action.beans.wd161.Wd161RoleAccountBean;
import cn.gyyx.action.bll.wd161.Wd161Bll;
import cn.gyyx.action.bll.wdninestory.QualificationBLL;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

/**
 * 版        权：光宇游戏
 * 作        者: lizhihai 
 * 创建时间：下午10:32:49
 * 描        述：问道1.61OA后台业务
 */
public class Wd161OAService {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(Wd161OAService.class);
	private int actionCode = 413;
	public Wd161Bll wd161Bll = new Wd161Bll();
	public static String MEM_KEY_PREFIX = "wd161oa_";
	private QualificationBLL qualificationBLL = new QualificationBLL();
	/*
	 * 审核pic
	 */
	public ResultBean<String> checkPics(String userName,int userCode, String state, Integer staffCode, String realName) {
		ResultBean<String> result = hdDate();
		if (!result.getIsSuccess()) {
			return result;
		}
		Wd161RoleAccountBean roleBean = wd161Bll.getUserInfo(userCode);
		if(!roleBean.getAuditStatus().equals("oncheck")){
			return new ResultBean<>(false,"已审核过了，不能重复审核",null);
		}
		//如果是审核通过，需要加一次抽奖次数
		SqlSession session = getSession();
		try{
			Boolean flag=false;
			Boolean flag1=false;
			//更改审核状态
			flag1 = wd161Bll.checkPics(userName,state,staffCode,realName,session);
			//审核通过,添加一次抽奖次数
			if(state.equals("pass")){
				flag=	wd161Bll.addOneLotteryTime(userCode, actionCode, session);
				if(flag&&flag1){
					result.setProperties(true, "审核成功", "");
					session.commit(true);
					return result;
				}else{
					result.setProperties(false, "审核失败", "");
					session.rollback();
					return result;
				}
			//审核不通过,不需要添加抽奖次数
			}else{
				if(flag1){
					result.setProperties(true, "审核成功", "");
					session.commit(true);
					return result;
				}else{
					result.setProperties(false, "审核失败", "");
					session.rollback();
					return result;
				}
			}
		}catch(Exception e){
			logger.error("批量审核-评论",e);
			session.rollback();
			result.setProperties(false, "审核失败", "");
			return result;
		}
	}

	/*
	 *  批量审核pic
	 */
	public ResultBean<String> batchCheckPics(String codesStr, String state, Integer staffCode, String realName) {
		ResultBean<String> result = hdDate();
		if (!result.getIsSuccess()) {
			return result;
		}
		if(state == null || ((!"pass".equals(state))&& (!"unpass".equals(state)))){
			result.setProperties(false, "状态不正确", null);
			return result;
		}
		if(codesStr == null || codesStr.split(",").length == 0){
			result.setProperties(false, "参数不正确", null);
			return null;
		}
		SqlSession session = getSession();
		try (DistributedLock lock = new DistributedLock(
				MEM_KEY_PREFIX + "batchCheckPics")) {
			lock.weakLock(30, 11);
			List<Integer> ids = new ArrayList<Integer>();
			
			for(String id : codesStr.split(",")){
				ids.add(Integer.parseInt(id));
			}
			for(Integer userId : ids ){
				Wd161RoleAccountBean roleBean= wd161Bll.getUserInfoSession(userId,session);
				//只有审核中，才执行批量审核操作 
				if(roleBean.getAuditStatus().equals("oncheck")){ 
					//执行审核
					Boolean flag = wd161Bll.batchCheckPics(userId,state,staffCode,realName,session);
					//如果是审核通过，需要添加一次抽奖次数
					if(state.equals("pass")){
						wd161Bll.addOneLotteryTime(userId, actionCode, session);
					}
				}
				continue;
			}
			session.commit(true);
			return new ResultBean<>(true, "操作成功", "");
		} catch (Exception e) {
			if(session != null) session.rollback();
			logger.error("topOperator error" ,e);
			return new ResultBean<>(false, "操作失败", "");
		}finally{
			if(session != null) session.close();
		}
	}

	//按搜索条件过滤作品
	public ResultBeanWithPage<List<Wd161RoleAccountBean>> picList( String beginTime, String endTime, String state,int pageSize, int pageNo) {
		ResultBeanWithPage<List<Wd161RoleAccountBean>> result = new ResultBeanWithPage<>();
		List<Wd161RoleAccountBean> list = new ArrayList();
		int count = 0;
		//总数
		count = wd161Bll.getPicCount(beginTime,endTime,state);
		list = wd161Bll.getPicList(beginTime, endTime,state,pageSize, pageNo);
		if (list != null && list.size() > 0) {
			for (Wd161RoleAccountBean bean : list) {
				String picUrlAll =  bean.getPicUrl().replace("-small", "");
				bean.setPicUrl(picUrlAll);
			}
			result.setProperties(true, "查询成功", list, count);
		}
		result.setProperties(true, "查询成功", list, count);
		return result;
	}
	/**
	 * 获取一个session
	 */
	private SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}
	//按搜索条件过滤评论
	public ResultBeanWithPage<List<Wd161CommentsBean>> commentsList(String beginTime, String endTime, String state,
			int pageSize, int pageNo) {
		ResultBeanWithPage<List<Wd161CommentsBean>> result = new ResultBeanWithPage<>();
		List<Wd161CommentsBean> list = new ArrayList();
		int count = 0;
		//总数
		count = wd161Bll.getCommentsCount(beginTime,endTime,state);
		list = wd161Bll.commentsList(beginTime, endTime,state,pageSize, pageNo);
		result.setProperties(true, "查询成功", list, count);
		return result;
	}
	/*
	 *  审核评论
	 */
	public ResultBean<String> checkComments( int code, String state, Integer staffCode,
			String realName) {
		ResultBean<String> result = hdDate();
		if (!result.getIsSuccess()) {
			return result;
		}
		SqlSession session = getSession();
		Wd161CommentsBean commentsBean = wd161Bll.getCommentsByCode(code);
		if(!commentsBean.getAuditStatus().equals("oncheck")){
			return new ResultBean<>(false,"已审核过了，不能重复审核",null);
		}
		Boolean flag = wd161Bll.checkComments(code,state,staffCode,realName);
		if(flag){
			result.setProperties(true, "审核成功", null);
		}
		else{
			result.setProperties(false, "审核失败", null);
		}
		
		return result;
		
	}
	/*
	 *  批量审核评论
	 */
	public ResultBean<String> batchCheckComments(String codesStr, String state, Integer staffCode, String realName) {
		ResultBean<String> result = hdDate();
		if (!result.getIsSuccess()) {
			return result;
		}
		if(state == null || ((!"pass".equals(state))&& (!"unpass".equals(state)))){
			result.setProperties(false, "状态不正确", null);
			return result;
		}
		if(codesStr == null || codesStr.split(",").length == 0){
			result.setProperties(false, "参数不正确", null);
			return null;
		}
		SqlSession session = getSession();
		try (DistributedLock lock = new DistributedLock(
				MEM_KEY_PREFIX + "batchCheckComments")) {
			lock.weakLock(30, 11);
			List<Integer> ids = new ArrayList<Integer>();
			for(String id : codesStr.split(",")){
				ids.add(Integer.parseInt(id));
			}
			for(Integer code : ids ){
				Wd161CommentsBean commentsBean = wd161Bll.getCommentsByCode( code,session);
				if(commentsBean.getAuditStatus().equals("oncheck")){
					Boolean flag = wd161Bll.batchCheckComments(code,state,staffCode,realName,session);
				}
				continue;
			}
			
			session.commit(true);
			return new ResultBean<>(true, "操作成功", "");
		} catch (Exception e) {
			if(session != null) session.rollback();
			logger.error("topOperator error" ,e);
			return new ResultBean<>(false, "操作失败", "");
		}finally{
			if(session != null) session.close();
		}
	}
	
	/**
	 * 判定活动开启结束时间
	 */
	public ResultBean<String> hdDate() {
		Date nowDate = new Date();
		Date beginDate = new Date();
		Date endDate = new Date();
		// 获取活动信息
		ActionConfigBean actionConfig = qualificationBLL.selectActionConfigByCode(actionCode);
		beginDate = actionConfig.getHdStartD();
		endDate = actionConfig.getHdEndD();
		if (nowDate.getTime() < beginDate.getTime()) {
			return new ResultBean<>(false, "活动尚未开启", null);
		}
		if (nowDate.getTime() >= endDate.getTime()) {
			return new ResultBean<>(false, "活动已结束", null);
		}

		return new ResultBean<>(true, "活动进行中", null);
	}
}
