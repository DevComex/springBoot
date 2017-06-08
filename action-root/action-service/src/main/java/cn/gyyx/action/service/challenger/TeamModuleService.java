

package cn.gyyx.action.service.challenger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.challenger.ChallenterTeamApplyLogBean;
import cn.gyyx.action.beans.challenger.ChallenterTeamBean;
import cn.gyyx.action.beans.challenger.ChallenterTeamUserBean;
import cn.gyyx.action.beans.challenger.ChallenterUserInfoBean;
import cn.gyyx.action.beans.challenger.OperationLogBean;
import cn.gyyx.action.beans.lottery.LuckyDrawLogBean;
import cn.gyyx.action.beans.predicable.ResultBeanWithPage;
import cn.gyyx.action.bll.challenger.ChallenterTeamApplyLogBll;
import cn.gyyx.action.bll.challenger.ChallenterTeamBll;
import cn.gyyx.action.bll.challenger.ChallenterUserInfoBll;
import cn.gyyx.action.bll.challenger.OperationLogBll;
import cn.gyyx.action.dao.MyBatisConnectionFactory;
import cn.gyyx.distribute.lock.DistributedLock;
import cn.gyyx.log.sdk.GYYXLoggerFactory;

public class TeamModuleService {
	private ChallenterTeamBll challenterTeamBll = new ChallenterTeamBll();
	private ChallenterTeamApplyLogBll challenterTeamApplyLogBll = new ChallenterTeamApplyLogBll();
	private ChallenterUserInfoBll challenterUserInfoBll = new ChallenterUserInfoBll();
	private OperationLogBll operationLogBll = new OperationLogBll();

	private static SqlSession getSession() {
		SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory
				.getSqlActionDBV2SessionFactory();
		return sqlSessionFactory.openSession();
	}

	private static final Logger logger = GYYXLoggerFactory
			.getLogger(TeamModuleService.class);
	
	/**
	 * 点击按钮创建队伍-进入校验
	 */
	public ResultBean<String> createTeamEntry(
			ChallenterUserInfoBean userInfoBean) {
		ResultBean<String> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "检查用户队伍信息失败", null);
		try {
			ChallenterUserInfoBean ub = challenterUserInfoBll
					.getUserInfoBean(userInfoBean);
			if (ub != null) {
				if (ChallengerConstant.userState.oncheck.toString().equals(
						ub.getState())) {
					resultBean.setProperties(false, "报名审核中,请等待", "oncheck");
					return resultBean;
				} 
			}else{
				resultBean.setProperties(false, "用户未报名,不能创建队伍", "nosign");
				return resultBean;
			}
			ChallenterTeamBean q = new ChallenterTeamBean();
			q.setUserId(userInfoBean.getUserId());
			ChallenterTeamBean tb = challenterTeamBll.getUserTeamInfo(q);
			
			resultBean.setProperties(true, "用户未进入任一队伍", "unjoin");
		} catch (Exception e) {
			logger.error("问道新世界挑战赛[检查用户队伍信息失败]", e);
		}
		return resultBean;
	}

	public ResultBean<String> entryCreateTeam(ChallenterUserInfoBean userInfo) {
		ResultBean<String> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "是否可以创建队伍接口异常", null);
		try {
			ChallenterUserInfoBean ub = challenterUserInfoBll
					.getUserInfoBean(userInfo);
			if (ub != null) {
				if (ChallengerConstant.userState.oncheck.toString().equals(
						ub.getState())) {
					resultBean.setProperties(false, "报名审核中,请等待","oncheck");
					return resultBean;
				}
			}else{
				resultBean.setProperties(false, "未成功报名,请先报名", "nosign");
				return resultBean;
			}
			//校验用户是否创建队伍或队伍审核中或申请队伍审核中 有的话给予提示
			ChallenterTeamBean b1 = challenterTeamBll.getUserCreateTeam(userInfo.getUserId());
			if(b1 != null){
				if (ChallengerConstant.teamState.oncheck.toString().equals(
						b1.getState())) {
					resultBean.setProperties(false, "创建队伍审核中,不能创建队伍", "createOnCheck");
					return resultBean;
				} else {
					resultBean.setProperties(false, "已创建队伍,不能再创建队伍","Create");
					return resultBean;
				}
			}
			ChallenterTeamBean b2 = challenterTeamBll.getUserTeamInfo(userInfo.getUserId());
			if(b2 != null){
				resultBean.setProperties(false, "已经加入队伍,不能再创建队伍", "join");
				return resultBean;
			}
			int count1 = challenterTeamApplyLogBll.getUserApplyTeamCount(userInfo.getUserId(),ChallengerConstant.teamState.oncheck.toString());
			if(count1 > 0){
				resultBean.setProperties(false, "申请队伍审核中,不能创建队伍", "applyOnCheck");
				return resultBean;
			}
			
			resultBean.setProperties(true, "允许创建队伍", ub.getRoleName());
		} catch (Exception e) {
			logger.error("问道新世界挑战赛[是否可以创建队伍接口异常]", e);
		}
		return resultBean;
	}
	
	public ResultBean<String> createTeam(ChallenterUserInfoBean userInfo,ChallenterTeamBean teamBean) {
		ResultBean<String> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "创建队伍接口异常", null);
		try {
			if (StringUtils.isBlank(teamBean.getTeamName())) {
				resultBean.setMessage("队伍名称不能为空");
				return resultBean;
			}
			if (StringUtils.isBlank(teamBean.getContactType())) {
				resultBean.setMessage("联系方式类型不能为空");
				return resultBean;
			}
			if (StringUtils.isBlank(teamBean.getContactName())) {
				resultBean.setMessage("联系方式不能为空");
				return resultBean;
			}
			if (StringUtils.isBlank(teamBean.getTarget())) {
				resultBean.setMessage("队伍目标不能为空");
				return resultBean;
			}
			if (StringUtils.isBlank(teamBean.getDeclaration())) {
				resultBean.setMessage("队伍宣言不能为空");
				return resultBean;
			}
			try (DistributedLock lock = new DistributedLock(
//					ChallengerConstant.MEM_KEY_PREFIX + "createTeam_"
					ChallengerConstant.MEM_KEY_PREFIX + "createOrApply_"//创建与申请同时加锁
							+ userInfo.getUserId())) {
				lock.weakLock(30, 11);
			    
				ResultBean<String> r1 = this.entryCreateTeam(userInfo);
				if(!r1.getIsSuccess()){
					return r1;
				}

				boolean isExist = challenterTeamBll
						.checkTeamNameExist(teamBean.getTeamName().trim());
				if (isExist) {
					resultBean.setMessage("队伍名称重复,请重新写填");
					return resultBean;
				}
				ChallenterTeamBean obj = new ChallenterTeamBean();
				obj.setContactType(teamBean.getContactType().trim());
				obj.setTeamName(teamBean.getTeamName().trim());
				obj.setUserId(userInfo.getUserId());
				obj.setAccount(userInfo.getAccount());
				obj.setContactName(teamBean.getContactName().trim());
				obj.setCreateTime(new Date());
				obj.setDeclaration(teamBean.getDeclaration().trim());
				obj.setState(ChallengerConstant.teamState.oncheck.toString());
				obj.setTarget(teamBean.getTarget().trim());

				challenterTeamBll.insert(obj);
				resultBean.setProperties(true, "成功创建队伍", "");
			} catch (Exception e) {
				throw e;
			}
		} catch (Exception e) {
			logger.error("问道新世界挑战赛[创建队伍接口异常]", e);
		}
		return resultBean;
	}

	public ResultBean<String> entryTeamInfo(ChallenterUserInfoBean userInfo) {
		ResultBean<String> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "是否创建申请队伍异常", null);
		try {
			ChallenterUserInfoBean ub = challenterUserInfoBll
					.getUserInfoBean(userInfo);
			if (ub != null) {
				if (ChallengerConstant.userState.oncheck.toString().equals(
						ub.getState())) {
					resultBean.setProperties(false, "报名审核中,请等待","oncheck");
					return resultBean;
				}
			}else{
				resultBean.setProperties(false, "未成功报名,请先报名", "nosign");
				return resultBean;
			}
			resultBean.setProperties(true, "允许进入我的队伍", "");
		} catch (Exception e) {
			logger.error("问道新世界挑战赛[是否创建申请队伍异常]", e);
		}
		return resultBean;
	}

	public ResultBeanWithPage<List<ChallenterTeamBean>> teamList(int currentPage,int pageSize) {
		ResultBeanWithPage<List<ChallenterTeamBean>> resultBean = new ResultBeanWithPage<List<ChallenterTeamBean>>();
		resultBean.setProperties(false, "获取队伍列表异常",null, 0);
		try {
			ChallenterTeamBean b = new ChallenterTeamBean();
			b.setState(ChallengerConstant.userState.pass.toString());
			int count = challenterTeamBll.teamListPagingCount(b);
			b.setCurrentPage(currentPage);
			b.setPageSize(pageSize);
			b.setState(ChallengerConstant.userState.pass.toString());
			List<ChallenterTeamBean> list = challenterTeamBll.teamListPaging(b);
			//修改查询方式  单表查询
			if(list != null && list.size() > 0){
				List<Integer> teamIds = new ArrayList<Integer>();
				for(ChallenterTeamBean bc :list){
					teamIds.add(bc.getCode());
				}
				//队伍关系表
				List<ChallenterTeamUserBean> teamUserBearList = challenterTeamBll.getTeamUserBearListByTeamIds(teamIds);
				//队员
				List<ChallenterUserInfoBean> teamMemberUserInfoList = challenterUserInfoBll.getTeamMemberUserInfoListByTeamIds(teamIds);
				
				Map<Integer,List<ChallenterUserInfoBean>> map = new HashMap<Integer,List<ChallenterUserInfoBean>>();
					
				//得到map key：teamId  value:成员对象列表
				for(ChallenterTeamUserBean teamUserB :teamUserBearList){
					List<ChallenterUserInfoBean> ll = null;
					if(map.get(teamUserB.getTeamId()) == null){
						ll = new ArrayList<ChallenterUserInfoBean>();
						map.put(teamUserB.getTeamId(), ll);
					}
					ll = map.get(teamUserB.getTeamId());
					
					for(ChallenterUserInfoBean memberUserB :teamMemberUserInfoList){
						if(memberUserB.getUserId() == teamUserB.getUserId()){
							ll.add(memberUserB);
							break;
						}
					}
				}
				
				for(ChallenterTeamBean c :list){
					//设置队员和队长
					List<ChallenterUserInfoBean> info = map.get(c.getCode());
					if(info != null && info.size() > 0){
						StringBuilder sb = new StringBuilder("");
						for(ChallenterUserInfoBean ss : info){
							if(c.getUserId() == ss.getUserId()){
								c.setRoleName(ss.getRoleName());
							}else{
								sb.append(ss.getRoleName()).append("、");
							}
						}
						c.setTeamMember(sb.toString().replaceAll("、$",""));
					}else{
						c.setTeamMember("");
						c.setRoleName("");
					}
				}
				
			}
//			if(list != null){
//				for(ChallenterTeamBean c :list){
//					ChallenterUserInfoBean q = new ChallenterUserInfoBean();
//					q.setUserId(c.getUserId());
//					q = challenterUserInfoBll
//							.getUserInfoBean(q);
//					c.setRoleName(q.getRoleName());
//					List<ChallenterUserInfoBean> memberNames = challenterTeamBll.getTeamMemberNamesNotLeader(c.getCode());
//					StringBuilder sb = new StringBuilder("");
//					if(memberNames != null && memberNames.size() > 0){
//						for(ChallenterUserInfoBean ss : memberNames){
//							sb.append(ss.getRoleName()).append("、");
//						}
//						c.setTeamMember(sb.toString().replaceAll("、$",""));
//					}else{
//						c.setTeamMember("");
//					}
//					
//				}
//			}
			return new ResultBeanWithPage<>(true, "获取队伍列表成功", list, count);
		} catch (Exception e) {
			logger.error("问道新世界挑战赛[获取队伍列表异常]", e);
		}
		return resultBean;
	}

	public ResultBean<String> applyTeam(int teamId, ChallenterUserInfoBean userInfo) {
		ResultBean<String> resultBean = new ResultBean<>();
		resultBean.setProperties(false, "申请加入队伍接口异常", null);
		SqlSession session = getSession();
		ChallenterUserInfoBean ub = challenterUserInfoBll
				.getUserInfoBean(userInfo);
		if (ub != null) {
			if (ChallengerConstant.userState.oncheck.toString().equals(
					ub.getState())) {
				resultBean.setProperties(false, "报名审核中,请等待......", "oncheck");
				return resultBean;
			}
		}else{
			resultBean.setProperties(false, "未成功报名,请先报名", "nosign");
			return resultBean;
		}
		//队伍是否存在
		ChallenterTeamBean s = challenterTeamBll.getTeam(teamId);
		if(s == null || !ChallengerConstant.teamState.pass.toString().equals(s.getState())){
			resultBean.setProperties(false, "队伍不存在或未审核","no_team");
			return resultBean;
		}
		try (DistributedLock lock = new DistributedLock(
//					ChallengerConstant.MEM_KEY_PREFIX + "applyTeam_"
//					+ userInfo.getUserId() + "_" + teamId)) {
				ChallengerConstant.MEM_KEY_PREFIX + "createOrApply_"//创建和申请使用一个key
						+ userInfo.getUserId())) {
			lock.weakLock(30, 11);
		
			ChallenterTeamBean b1 = challenterTeamBll.getUserCreateTeam(userInfo.getUserId());
			if(b1 != null){
				if (ChallengerConstant.teamState.oncheck.toString().equals(
						b1.getState())) {
					resultBean.setProperties(false, "创建队伍审核中,不允许申请", ",create_team_oncheck");
					return resultBean;
				}
			}
			
			ChallenterTeamBean b2 = challenterTeamBll.getUserTeamInfo(userInfo.getUserId());
			if(b2 != null){
				resultBean.setProperties(false, "已经加入队伍,不允许申请", "join");
				return resultBean;
			}
			
			//申请过该队，且正在审核中，不允许申请
			ChallenterTeamApplyLogBean t = new ChallenterTeamApplyLogBean();
			t.setTeamId(teamId);
			t.setUserId(userInfo.getUserId());
			int count = challenterTeamApplyLogBll.getApplyCount(t);
			if(count != 0){
				resultBean.setProperties(false, "该队伍您已提交申请，请等待审核", ",has_apply_team");
				return resultBean;
			}
			
			ChallenterTeamApplyLogBean log = new ChallenterTeamApplyLogBean();
			log.setCreateTime(new Date());
			log.setState(ChallengerConstant.teamApplyState.oncheck.toString());
			log.setTeamId(teamId);
			log.setUserId(userInfo.getUserId());
			
			challenterTeamBll.updateTeamApplyCountAddOne(session,teamId);
			challenterTeamApplyLogBll.insert(log,session);
			session.commit(true);
		} catch (Exception e) {
			if(session != null) session.rollback();
			logger.error("问道新世界挑战赛[申请加入队伍接口异常]", e);
		} finally{
			if(session != null) session.close();
		}
		s = challenterTeamBll.getTeam(teamId);
		resultBean.setProperties(true, "申请加入队伍成功", ""+s.getApplyCount());
		return resultBean;
	}

	public ResultBean<ChallenterTeamBean> teamDetail(int teamId) {
		ResultBean<ChallenterTeamBean> resultBean = new ResultBean<ChallenterTeamBean>();
		resultBean.setProperties(false, "获取队伍详情信息接口异常", null);
		try {
			//队伍是否存在
			ChallenterTeamBean s = challenterTeamBll.getTeam(teamId);
			if(s == null || !ChallengerConstant.teamState.pass.toString().equals(s.getState())){
				resultBean.setProperties(false, "队伍不存在或未审核通过 ",null);
				return resultBean;
			}
			ChallenterTeamBean rs1 = challenterTeamBll.getTeam(teamId);
			List<ChallenterUserInfoBean> memberNames = challenterTeamBll.getTeamMemberNamesNotLeader(teamId);
			StringBuilder sb = new StringBuilder();
			if(memberNames != null && memberNames.size() > 0){
				for(ChallenterUserInfoBean ss : memberNames){
					sb.append(ss.getRoleName()).append("、");
				}
				rs1.setTeamMember(sb.toString().replaceAll("、$",""));
			}else{
				rs1.setTeamMember("");
			}
			ChallenterUserInfoBean q = new ChallenterUserInfoBean();
			q.setUserId(s.getUserId());
			q = challenterUserInfoBll
					.getUserInfoBean(q);
			rs1.setRoleName(q.getRoleName());
			resultBean.setProperties(true, "获取队伍详情信息成功", rs1);
		} catch (Exception e) {
			logger.error("问道挑战新世界[获取队伍详情信息]接口异常", e);
		}

		return resultBean;
	}
	
	public ChallenterTeamBean getUserTeamInfo(int userId) {
		return challenterTeamBll.getUserTeamInfo(userId);
	}
	
	public ChallenterTeamBean getUserCreateTeam(int userId) {
		return challenterTeamBll.getUserCreateTeam(userId);
	}
	
	public int getUserApplyTeamCount(int userId) {
		return challenterTeamApplyLogBll.getUserApplyTeamCount(userId,ChallengerConstant.teamState.oncheck.toString());
	}
	
	public ChallenterTeamBean getTeam(int teamId,boolean flag) {//flag 是否包含成员
		ChallenterTeamBean t = challenterTeamBll.getTeam(teamId);
		if(t != null){
			if(flag){
				List<ChallenterUserInfoBean> memberNames = challenterTeamBll.getTeamMemberNamesNotLeader(teamId);
				StringBuilder sb = new StringBuilder();
				if(memberNames != null && memberNames.size() > 0){
					for(ChallenterUserInfoBean ss : memberNames){
						sb.append(ss.getRoleName()).append("、");
					}
					t.setTeamMember(sb.toString().replaceAll("、$",""));
				}else{
					t.setTeamMember("");
				}
			}
			ChallenterUserInfoBean q = new ChallenterUserInfoBean();
			q.setUserId(t.getUserId());
			q = challenterUserInfoBll
					.getUserInfoBean(q);
			t.setRoleName(q.getRoleName());
		}
		return t;
	}

	public List<ChallenterUserInfoBean> getUserTeamMembers(int teamId) {
		return challenterTeamBll.getTeamMemberNamesNotLeader(teamId);
	}

	public List<ChallenterUserInfoBean> getUserTeamApplyLogList(int teamId) {
		return challenterTeamApplyLogBll.getUserTeamApplyLogList(teamId);
	}

	public ResultBean<String> teamApplyReview(ChallenterUserInfoBean userInfo,
			int applyId, String status) {
		
		ResultBean<String> resultBean = new ResultBean<String>();
		resultBean.setProperties(false, "队伍申请审核接口异常", null);
		if(status == null || (!ChallengerConstant.memberApplyTeamState.pass.toString().equals(status) && !ChallengerConstant.memberApplyTeamState.unpass.toString().equals(status))){
			resultBean.setProperties(false, "审核状态不正确", null);
			return resultBean;
		}
		
		//根据applyId的申请
		ChallenterTeamApplyLogBean log = challenterTeamApplyLogBll.getChallenterTeamApplyLogByCode(applyId);
		if(log == null){
			resultBean.setProperties(false, "申请不存在", null);
			return resultBean;
		}
		int userId = log.getUserId();
		ChallenterUserInfoBean b = new ChallenterUserInfoBean();
		b.setUserId(userId);
		b = challenterUserInfoBll.getUserInfoBean(b);
		if(b == null || !ChallengerConstant.userState.pass.toString().equals(b.getState())){
			resultBean.setProperties(false, "玩家不存在或者未报名成功", null);
			return resultBean;
		}
		ChallenterTeamBean b1 = challenterTeamBll.getUserCreateTeam(userInfo.getUserId());
		SqlSession session = getSession();
		try (DistributedLock lock = new DistributedLock(
				ChallengerConstant.MEM_KEY_PREFIX + "memberApplyTeam_"
						+ userInfo.getUserId() + "_" + b1.getCode())) {
			lock.weakLock(30, 11);
			if(ChallengerConstant.memberApplyTeamState.pass.toString().equals(status)){
				ChallenterTeamBean b2 = challenterTeamBll.getUserTeamInfo(userId);
				if(b2 != null){
					resultBean.setProperties(false, "玩家已被抢走，快点扩充队伍吧~", "joinOther");
					return resultBean;
				}
			}
			
			log.setCreateTime(new Date());
			log.setState(ChallengerConstant.teamApplyState.oncheck.toString());
//				log.setTeamId(teamId);
			log.setUserId(userInfo.getUserId());
//					
//					
//					s = challenterTeamBll.getTeam(teamId);
			if(ChallengerConstant.memberApplyTeamState.pass.toString().equals(status)){
				ChallenterTeamUserBean ub = new ChallenterTeamUserBean();
				ub.setCreateTime(new Date());
				ub.setTeamId(b1.getCode());
				ub.setUserId(userId);
				
				challenterTeamBll.insertTeamAndUser(session,ub);
				
				LuckyDrawLogBean logBean = new LuckyDrawLogBean();
				logBean.setAccount(b.getAccount());
				logBean.setActionCode(ChallengerConstant.ACTIVITY_CODE);
				logBean.setDrawCount(1);
				logBean.setSource("applyTeam");
				logBean.setUserId(userId);
				// 这就得加抽奖次数
				LotteryService.setLotteryTimesSqlSession(logBean, session);
				
				//将所有用户的申请状态改成unpass 不包括此条
				challenterTeamApplyLogBll.updateUserTeamApplyStateUnPass(session,applyId,userId);
			}
			
			challenterTeamApplyLogBll.updateApplyState(status,session,applyId);
			session.commit(true);

			resultBean.setProperties(true, "操作成功", status);
		} catch (Exception e) {
			if(session != null) session.rollback();
			logger.error("问道新世界挑战赛[队伍申请审核接口异常]", e);
		} finally{
			if(session != null) session.close();
		}

		return resultBean;
	}

	/**
	 * 分页显示后台oa队伍审核列表
	 * @param bean
	 * @return
	 */
	public ResultBeanWithPage<List<ChallenterTeamBean>> getTeamListData(
			ChallenterTeamBean bean) {
		try {
			List<ChallenterTeamBean> list = challenterTeamBll
					.getTeamListData(bean);
			int count = challenterTeamBll
					.getTeamListDataCount(bean);
			return new ResultBeanWithPage<>(true, "获取成功", list, count);
		} catch (Exception e) {
			logger.error("getTeamListData" ,e);
			return new ResultBeanWithPage<>(false, "获取失败", null, 0);
		}
	}

	public ResultBean<String> reviewCreateTeamApply(int code, String state,int staffCode,String realName) {
		ResultBean<String> resultBean = new ResultBean<String>();
		if(state == null || (!ChallengerConstant.memberApplyTeamState.pass.toString().equals(state) && !ChallengerConstant.memberApplyTeamState.unpass.toString().equals(state))){
			resultBean.setProperties(false, "审核状态不正确", null);
			return resultBean;
		}
		SqlSession session = getSession();
		try (DistributedLock lock = new DistributedLock(
				ChallengerConstant.MEM_KEY_PREFIX + "reviewCreateTeamApply_"
						+ code)) {
			lock.weakLock(30, 11);
			ChallenterTeamBean t = challenterTeamBll.getTeam(code);
			if(t == null){
				resultBean.setProperties(false, "队伍不存在", null);
				return resultBean;
			}
			ChallenterUserInfoBean bean = new ChallenterUserInfoBean();
			bean.setUserId(t.getUserId());
			bean = challenterUserInfoBll.getUserInfoBean(bean);
			if(ChallengerConstant.memberApplyTeamState.pass.toString().equals(state)){
				if(ChallengerConstant.memberApplyTeamState.pass.toString().equals(t.getState())){
					resultBean.setProperties(false, "已经审核通过,不允许再次通过", null);
					return resultBean;
				}
			}
			if(ChallengerConstant.memberApplyTeamState.unpass.toString().equals(state)){
				if(ChallengerConstant.memberApplyTeamState.pass.toString().equals(t.getState())){
					resultBean.setProperties(false, "已经审核通过,不允许不通过", null);
					return resultBean;
				}
			}
			
			// 审核
			challenterTeamBll.reviewChallenterTeam(code, state, session);
			if(ChallengerConstant.memberApplyTeamState.pass.toString().equals(state)){
				ChallenterTeamUserBean ub = new ChallenterTeamUserBean();
				ub.setCreateTime(new Date());
				ub.setTeamId(t.getCode());
				ub.setUserId(t.getUserId());
				challenterTeamBll.insertTeamAndUser(session,ub);
				
				LuckyDrawLogBean logBean = new LuckyDrawLogBean();
				logBean.setAccount(bean.getAccount());
				logBean.setActionCode(ChallengerConstant.ACTIVITY_CODE);
				logBean.setDrawCount(1);
				logBean.setSource("createTeam");
				logBean.setUserId(bean.getUserId());
				// 这就得加抽奖次数
				LotteryService.setLotteryTimesSqlSession(logBean, session);
			}
			// 加入操作日志
			OperationLogBean operateBean = new OperationLogBean();
			operateBean.setUserName(realName);
			operateBean.setUserid(staffCode);
			operateBean.setType("createTeamCheck");
			operateBean.setTid(code);
			operateBean.setActionCode(ChallengerConstant.ACTIVITY_CODE);
			operateBean.setDescription(ChallengerConstant.memberApplyTeamState.pass.toString().equals(state)?
					"创建队伍审核通过":"创建队伍审核未通过");
			operationLogBll.addOperationLog(operateBean, session);
			session.commit(true);
			
			return new ResultBean<>(true, ChallengerConstant.memberApplyTeamState.pass.toString().equals(state)
					?"审核通过操作成功":"审核不通过操作成功", "");
		} catch (Exception e) {
			if(session != null) session.rollback();
			logger.error("reviewCreateTeamApply error" ,e);
			return new ResultBean<>(false, "审核失败", "");
		} finally{
			if(session != null) session.close();
		}
	}

}

