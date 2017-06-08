/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月12日下午6:46:51
 * 版本号：v1.0
 * 本类主要用途叙述：处理用户相关请求，包括登陆和注册
 */

package cn.gyyx.action.service.challenger;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.challenger.ChallenterLiveRadioBean;
import cn.gyyx.action.beans.challenger.OperationLogBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.bll.challenger.ChallenterLiveRadioBll;
import cn.gyyx.action.bll.challenger.OperationLogBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class ChallenterLiveRadioService {
	private ChallenterLiveRadioBll challenterLiveRadioBll = new ChallenterLiveRadioBll();
	private OperationLogBll operationLogBll = new OperationLogBll();

	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(ChallenterLiveRadioService.class);
	
	/**
	 * 根据id获取
	 * @param bean
	 * @return
	 */
	public ResultBean<ChallenterLiveRadioBean> getChallenterLiveRadioBean(
			int code) {
		ResultBean<ChallenterLiveRadioBean> resultBean = new ResultBean<ChallenterLiveRadioBean>();
		try {
			if(code == 0){
				resultBean.setProperties(false, "视频编号不能为空", null);
				return resultBean;
			}
			
			ChallenterLiveRadioBean data = challenterLiveRadioBll
					.getChallenterLiveRadioBean(code);
			return new ResultBean<ChallenterLiveRadioBean>(true, "获取成功", data);
		} catch (Exception e) {
			logger.error("getChallenterLiveRadioListData" ,e);
			return new ResultBean<ChallenterLiveRadioBean>(false, "获取失败", null);
		}
	}
	
	/**
	 * 分页显示后台
	 * @param bean
	 * @return
	 */
	public ResultBeanWithPage<List<ChallenterLiveRadioBean>> getChallenterLiveRadioListData(
			ChallenterLiveRadioBean bean) {
		try {
			List<ChallenterLiveRadioBean> list = challenterLiveRadioBll
					.getChallenterLiveRadioListPaging(bean);
			int count = challenterLiveRadioBll
					.getChallenterLiveRadioCount(bean);
			return new ResultBeanWithPage<>(true, "获取成功", list, count);
		} catch (Exception e) {
			logger.error("getChallenterLiveRadioListData" ,e);
			return new ResultBeanWithPage<>(false, "获取失败", null, 0);
		}
	}
	
	/**
	 * 分页显示前台
	 * @param bean
	 * @return
	 */
	public ResultBeanWithPage<List<ChallenterLiveRadioBean>> getWebFrontChallenterLiveRadioListData(
			ChallenterLiveRadioBean bean) {
		try {
			List<ChallenterLiveRadioBean> list = challenterLiveRadioBll
					.getWebFrontChallenterLiveRadioListPaging(bean);
			int count = challenterLiveRadioBll
					.getWebFrontChallenterLiveRadioCount(bean);
			return new ResultBeanWithPage<>(true, "获取成功", list, count);
		} catch (Exception e) {
			logger.error("getChallenterLiveRadioListData" ,e);
			return new ResultBeanWithPage<>(false, "获取失败", null, 0);
		}
	}
	
	/**
	 * 前台显示top5
	 * @param bean
	 * @return
	 */
	public ResultBean<List<ChallenterLiveRadioBean>> getTopN(
			ChallenterLiveRadioBean bean) {
		try {
			bean.setCurrentPage(1);
			List<ChallenterLiveRadioBean> list = challenterLiveRadioBll
					.getChallenterLiveRadioListPaging(bean);
			return new ResultBean<List<ChallenterLiveRadioBean>>(true, "获取成功", list);
		} catch (Exception e) {
			logger.error("getChallenterLiveRadioListData" ,e);
			return new ResultBean<List<ChallenterLiveRadioBean>>(false, "获取成功", null);
		}
	}
	
	public ResultBean<String> update(ChallenterLiveRadioBean bean,int staffCode,String realName) {
		ResultBean<String> resultBean = new ResultBean<String>();
		if(bean.getCode() == 0){
			resultBean.setProperties(false, "视频编号不能为空", "");
			return resultBean;
		}
		if(StringUtils.isBlank(bean.getTitle())){
			resultBean.setProperties(false, "视频标题不允许为空", "");
			return resultBean;
		}
		if(StringUtils.isBlank(bean.getPicUrl())){
			resultBean.setProperties(false, "视频图片地址不允许为空", "");
			return resultBean;
		}
		if(StringUtils.isBlank(bean.getRadioUrl())){
			resultBean.setProperties(false, "视频地址不允许为空", "");
			return resultBean;
		}
			
		SqlSession session = getSession();
		try{
			challenterLiveRadioBll.update(bean, session);

			// 加入操作日志 先不加 重要操作才加
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(realName);
			operateBean.setUserid(staffCode);
			operateBean.setType("radioUpdate");
			operateBean.setTid(bean.getCode());
			operateBean.setActionCode(ChallengerConstant.ACTIVITY_CODE);
			operateBean.setDescription("更新视频");
			operationLogBll.addOperationLog(operateBean, session);
			session.commit(true);
		}catch(Exception e){
			if(session != null) session.rollback();
			logger.error("oa----liveradio--update",e);
			return new ResultBean<>(true, "操作失败", "");
		}finally{
			if(session != null) session.close();
		}
		return new ResultBean<>(true, "操作成功", "");
	}
	
	public ResultBean<String> insert(ChallenterLiveRadioBean bean,int staffCode,String realName) {
		ResultBean<String> resultBean = new ResultBean<String>();
		if(StringUtils.isBlank(bean.getTitle())){
			resultBean.setProperties(false, "视频标题不允许为空", "");
			return resultBean;
		}
		if(StringUtils.isBlank(bean.getPicUrl())){
			resultBean.setProperties(false, "视频图片地址不允许为空", "");
			return resultBean;
		}
		if(StringUtils.isBlank(bean.getRadioUrl())){
			resultBean.setProperties(false, "视频地址不允许为空", "");
			return resultBean;
		}
			
		SqlSession session = getSession();
		try{
			bean.setCreateTime(new Date());
			bean.setIsDelete("N");
			bean.setIsTop(ChallengerConstant.liveRadioState.N.toString());
			int code =  challenterLiveRadioBll.insert(bean, session);

			// 加入操作日志
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(realName);
			operateBean.setUserid(staffCode);
			operateBean.setType("radioCreate");
			operateBean.setTid(code);
			operateBean.setActionCode(ChallengerConstant.ACTIVITY_CODE);
			operateBean.setDescription("创建视频");
			operationLogBll.addOperationLog(operateBean, session);
			session.commit(true);
		}catch(Exception e){
			if(session != null) session.rollback();
			logger.error("oa----insert--update",e);
			return new ResultBean<>(true, "操作失败", "");
		}finally{
			if(session != null) session.close();
		}
		return new ResultBean<>(true, "操作成功", "");
	}
	
	public ResultBean<String> deleteRadio(int code, int staffCode,String realName) {
		ResultBean<String> resultBean = new ResultBean<String>();
		SqlSession session = getSession();
		try (DistributedLock lock = new DistributedLock(
				ChallengerConstant.MEM_KEY_PREFIX + "radioDelOperator_")) {
			lock.weakLock(30, 11);
			ChallenterLiveRadioBean t = challenterLiveRadioBll.getChallenterLiveRadioBean(code);
			if(t == null){
				resultBean.setProperties(false, "视频不存在", null);
				return resultBean;
			}
			if(ChallengerConstant.liveRadioState.Y.toString().equals(t.getIsTop())){
				resultBean.setProperties(false, "视频还在推荐中,请先取消推荐", null);
				return resultBean;
			}
			
			//检查数量
			ChallenterLiveRadioBean cbean = new ChallenterLiveRadioBean(); 
			//cbean.setTop(ChallengerConstant.liveRadioState.Y.toString());
			int c = challenterLiveRadioBll.getChallenterLiveRadioCount(cbean);
			if(c <= ChallengerConstant.LIVE_RADIO_TOP_COUNT ){
				resultBean.setProperties(false, "视频个数少于5"+ChallengerConstant.LIVE_RADIO_TOP_COUNT+"个,不允许操作", null);
				return resultBean;
			}
			challenterLiveRadioBll.delete(t, session);

			// 加入操作日志
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(realName);
			operateBean.setUserid(staffCode);
			operateBean.setType("radioDel");
			operateBean.setTid(code);
			operateBean.setActionCode(ChallengerConstant.ACTIVITY_CODE);
			operateBean.setDescription("删除视频");
			operationLogBll.addOperationLog(operateBean, session);
			session.commit(true);
			
			return new ResultBean<>(true, "删除成功", "");
		} catch (Exception e) {
			if(session != null) session.rollback();
			logger.error("deleteRadio error" ,e);
			return new ResultBean<>(false, "操作失败", "");
		}finally{
			if(session != null) session.close();
		}
	}

	public ResultBean<String> topOperator(int code, String state,int staffCode,String realName) {
		ResultBean<String> resultBean = new ResultBean<String>();
		if(state == null || (!ChallengerConstant.liveRadioState.Y.toString().equals(state) && !ChallengerConstant.liveRadioState.N.toString().equals(state))){
			resultBean.setProperties(false, "状态不正确", null);
			return resultBean;
		}
		
		SqlSession session = getSession();
		
		try (DistributedLock lock = new DistributedLock(
				ChallengerConstant.MEM_KEY_PREFIX + "radioTopOperator_")) {
			lock.weakLock(30, 11);
			ChallenterLiveRadioBean t = challenterLiveRadioBll.getChallenterLiveRadioBean(code);
			if(t == null){
				resultBean.setProperties(false, "视频不存在", null);
				return resultBean;
			}
			ChallenterLiveRadioBean cbean = new ChallenterLiveRadioBean(); 
			cbean.setIsTop(ChallengerConstant.liveRadioState.Y.toString());
			int c = challenterLiveRadioBll.getChallenterLiveRadioCount(cbean);
			
			if(ChallengerConstant.liveRadioState.Y.toString().equals(state)){
				if(ChallengerConstant.liveRadioState.Y.toString().equals(t.getIsTop())){
					resultBean.setProperties(false, "已经推荐,不允许再次操作", null);
					return resultBean;
				}
				//检查数量
//				if(c >=5 ){
//					resultBean.setProperties(false, "推荐视频个数超过5个,不允许操作", null);
//					return resultBean;
//				}
			}
			if(ChallengerConstant.liveRadioState.N.toString().equals(state)){
				if(ChallengerConstant.liveRadioState.N.toString().equals(t.getIsTop())){
					resultBean.setProperties(false, "已经取消推荐,不允许再次操作", null);
					return resultBean;
				}
				//检查数量
				if(c <=5 ){
					resultBean.setProperties(false, "推荐视频个数少于5个,不允许操作", null);
					return resultBean;
				}
			}
			
			t.setIsTop(state);
			challenterLiveRadioBll.update(t, session);

			// 加入操作日志
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(realName);
			operateBean.setUserid(staffCode);
			operateBean.setType("topRadio");
			operateBean.setTid(code);
			operateBean.setActionCode(ChallengerConstant.ACTIVITY_CODE);
			operateBean.setDescription(ChallengerConstant.liveRadioState.Y.toString().equals(state)?
					"推荐":"推荐取消");
			operationLogBll.addOperationLog(operateBean, session);
			session.commit(true);
		
			return new ResultBean<>(true, ChallengerConstant.liveRadioState.Y.toString().equals(state)
					?"推荐操作成功":"取消推荐操作成功", "");
		} catch (Exception e) {
			if(session != null) session.rollback();
			logger.error("topOperator error" ,e);
			return new ResultBean<>(false, "操作失败", "");
		}finally{
			if(session != null) session.close();
		}
	}


}
