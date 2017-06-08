package cn.gyyx.action.ui.xuanwuba;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.lottery.UserInfoBean;
import cn.gyyx.action.beans.novicecard.ResultBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.CommentsBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.Data;
import cn.gyyx.action.beans.xwbcreditsluckydraw.GoodsGetBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.GoodsInfoBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.ListPageBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MaterialAuditBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MissionBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.MissionReceiveBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.PlayerInfoBean;
import cn.gyyx.action.bll.lottery.UserLotteryBll;
import cn.gyyx.action.bll.xwbcreditsluckydraw.CommentsBll;
import cn.gyyx.action.bll.xwbcreditsluckydraw.GoodsBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MaterialAuditBll;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MissionBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.MissionReceiveBLL;
import cn.gyyx.action.bll.xwbcreditsluckydraw.PlayerinfoBLL;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.xuanwuba.XWBService;
import cn.gyyx.core.DataFormat;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;

@Controller
@RequestMapping(value = "/xuanwuba")
public class XWBTaskController {
	private static final Logger logger = GYYXLoggerFactory
			.getLogger(XWBCollectController.class);
	private XWBService xWBService = new XWBService();
	private CommentsBll commentsBll = new CommentsBll();
	private MissionBLL missionBLL = new MissionBLL();
	private MissionReceiveBLL missionReceiveBLL = new MissionReceiveBLL();
	private CallWebApiAgent callWeb = new CallWebApiAgent();
	private PlayerinfoBLL playerinfoBLL = new PlayerinfoBLL();
	private GoodsBLL goodsBLL = new GoodsBLL();
	private UserLotteryBll userLotteryBll = new UserLotteryBll();
	private MaterialAuditBll materialAuditBll = new MaterialAuditBll();
	private int actionCode = 288;
	
	@RequestMapping(value = "/taskShow")
	public String taskShow(Model model,HttpServletRequest request) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		UserInfo userInfo = null;
		if(request.getCookies() != null){
			userInfo = SignedUser.getUserInfo(request);
		}
		// 获取失败，返回为空
		if (userInfo != null) {
			MissionBean missionBean = missionBLL.getOneRecommend();
			if(missionBean!=null){
				MissionReceiveBean missionReceiveBean = missionReceiveBLL.getMissionReceiveBycode(missionBean.getCode(), userInfo.getAccount());
				if(missionReceiveBean!=null){
					missionBean.setStatus(missionReceiveBean.getCompleteStatus());
				}
				else{
					missionBean.setStatus("未领取");
				}
				missionBean.setMissionPublishTimeStr(format.format(missionBean.getMissionPublishTime()));
			}
			model.addAttribute("missionBean", missionBean);
			List<MissionBean> missionBeanList = xWBService.getMissionAll(userInfo.getAccount());
			model.addAttribute("missionBeanList", missionBeanList);
			
		}else{

			MissionBean missionBean = missionBLL.getOneRecommend();
			if(missionBean!=null){
			missionBean.setStatus("未领取");
			missionBean.setMissionPublishTimeStr(format.format(missionBean.getMissionPublishTime()));
			}
			model.addAttribute("missionBean", missionBean);
			List<MissionBean> missionBeanList = missionBLL.getMissionAll();
			if(missionBeanList!=null){
				for(MissionBean missionBean1 : missionBeanList){
					missionBean1.setStatus("未领取");
				}
			}
			model.addAttribute("missionBeanList", missionBeanList);
		}
		return "xuanwuba/wb_task";
		
	}
	
	@RequestMapping(value = "/userUploadShowPageJSON", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String userShowPageJSON(Integer pageIndex,String materialType,Model model,HttpServletRequest request) {
//		try {
//			materialType = new String(materialType.getBytes("iso-8859-1"),"utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		ListPageBean<MaterialAuditBean> bean = new ListPageBean<MaterialAuditBean>();
		List<MaterialAuditBean> list = new ArrayList<MaterialAuditBean>();
		List<MaterialAuditBean> listOld = new ArrayList<MaterialAuditBean>();
		int pageCount = 12;
		int start = (pageIndex-1)*pageCount;
		int end = pageIndex*pageCount-1;
		// 获取失败，返回为空
		if (userInfo == null) {
			list = null;
			bean.setTotalCount(0);
			bean.setPageArray(pageInfo(1, pageIndex));
		}else{
			listOld = xWBService.getMaterialUserShow(userInfo.getAccount(), materialType);
			if(listOld != null && listOld.size() > 0){
				for(int i = 0;i<listOld.size();i++){
					if(i>= start && i<=end){
						list.add(listOld.get(i));
					}
				}
				for (MaterialAuditBean materialAuditBean : list) {
					materialAuditBean.setUploadTimeStr(format.format(materialAuditBean.getUploadTime()));
				}
				bean.setTotalCount(listOld.size());
				bean.setPageArray(pageInfo(listOld.size()%12==0?listOld.size()/12:listOld.size()/12+1, pageIndex));
			}else{
				list = null;
				bean.setTotalCount(0);
				bean.setPageArray(pageInfo(1, pageIndex));
			}
		}
		bean.setList(list);
		bean.setCurrentPage(pageIndex);
		return DataFormat.objToJson(bean);
	}
	
	
	@RequestMapping(value = "/userExchangeShowPageJSON", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String userExchangeShowPageJSON(Integer pageIndex,Model model,HttpServletRequest request) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		ListPageBean<GoodsGetBean> bean = new ListPageBean<GoodsGetBean>();
		List<GoodsGetBean> list = new ArrayList<GoodsGetBean>();
		List<GoodsGetBean> listOld = new ArrayList<GoodsGetBean>();
		int pageCount = 36;
		int start = (pageIndex-1)*pageCount;
		int end = pageIndex*pageCount-1;
		// 获取失败，返回为空
		if (userInfo == null) {
			list = null;
			bean.setTotalCount(0);
			bean.setPageArray(pageInfo(1, pageIndex));
		}else{
			listOld = xWBService.getGoodGetByUser(userInfo.getAccount(), "兑换");
			if(listOld != null && listOld.size()>0){
				for(int i = 0;i<listOld.size();i++){
					if(i>= start && i<=end){
						list.add(listOld.get(i));
					}
				}
				for (GoodsGetBean goodsGetBean : list) {
					if(goodsGetBean.getExchangeDate() != null){
						goodsGetBean.setStrExchangeDate(format.format(goodsGetBean.getExchangeDate()));
					}
					GoodsInfoBean GoodsInfoBean = goodsBLL.getGoodsByCode(goodsGetBean.getGoodsCode());
					if(GoodsInfoBean != null){
						goodsGetBean.setGoodsName(GoodsInfoBean.getGoodsName());
					}
				}
				bean.setTotalCount(listOld.size());
				bean.setPageArray(pageInfo(listOld.size()%36==0?listOld.size()/36:listOld.size()/36+1, pageIndex));
			}else{
				list = null;
				bean.setTotalCount(0);
				bean.setPageArray(pageInfo(1, pageIndex));
			}
		}
		bean.setList(list);
		bean.setCurrentPage(pageIndex);
		return DataFormat.objToJson(bean);
	}
	
	@RequestMapping(value = "/userLotteryShowPageJSON", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String userLotteryShowPageJSON(Integer pageIndex,Model model,HttpServletRequest request) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		ListPageBean<UserInfoBean> bean = new ListPageBean<UserInfoBean>();
		List<UserInfoBean> list = new ArrayList<UserInfoBean>();
		List<UserInfoBean> listOld = new ArrayList<UserInfoBean>();
		int pageCount = 36;
		int start = (pageIndex-1)*pageCount;
		int end = pageIndex*pageCount-1;
		// 获取失败，返回为空
		if (userInfo == null) {
			list = null;
			bean.setTotalCount(0);
			bean.setPageArray(pageInfo(1, pageIndex));
		}else{
			listOld = userLotteryBll
					.wishGetUserLotteryByUserCode(actionCode,
							userInfo.getAccount());
			if(listOld != null && listOld.size()>0){
				for(int i = 0;i<listOld.size();i++){
					if(i>= start && i<=end){
						list.add(listOld.get(i));
					}
				}
				for (UserInfoBean userInfoBean : list) {
					if(userInfoBean.getTime() != null){
						userInfoBean.setTimeStr(format.format(userInfoBean.getTime()));
					}
				}
				bean.setTotalCount(listOld.size());
				bean.setPageArray(pageInfo(listOld.size()%36==0?listOld.size()/36:listOld.size()/36+1, pageIndex));
			}else{
				list = null;
				bean.setTotalCount(0);
				bean.setPageArray(pageInfo(1, pageIndex));
			}
		}
		bean.setList(list);
		bean.setCurrentPage(pageIndex);
		return DataFormat.objToJson(bean);
	}
	
	@RequestMapping(value = "/userCommentShowPageJSON", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String userCommentShowPageJSON(Integer pageIndex,Model model,HttpServletRequest request) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if (request.getCookies() != null) {
			userInfo = SignedUser.getUserInfo(request);
		}
		ListPageBean<CommentsBean> bean = new ListPageBean<CommentsBean>();
		List<CommentsBean> list = new ArrayList<CommentsBean>();
		List<CommentsBean> listOld = new ArrayList<CommentsBean>();
		int pageCount = 8;
		int start = (pageIndex-1)*pageCount;
		int end = pageIndex*pageCount-1;
		// 获取失败，返回为空
		if (userInfo == null) {
			list = null;
			bean.setTotalCount(0);
			bean.setPageArray(pageInfo(1, pageIndex));
		}else{
			listOld = commentsBll.getCommentByUser(userInfo.getAccount());
			if(listOld != null && listOld.size()>0){
				for(int i = 0;i<listOld.size();i++){
					if(i>= start && i<=end){
						list.add(listOld.get(i));
					}
				}
				for (CommentsBean commentsBean : list) {
					commentsBean.setCommentDateStr(format.format(commentsBean.getCommentDate()));
					commentsBean.setContent(replaceImg(commentsBean.getContent()));
					commentsBean.setMaterialType(materialAuditBll.getMaterialTypeByMaterialCode(commentsBean.getMaterialCode()));
				}
				bean.setTotalCount(listOld.size());
				bean.setPageArray(pageInfo(listOld.size()%8==0?listOld.size()/8:listOld.size()/8+1, pageIndex));
			}else{
				list = null;
				bean.setTotalCount(0);
				bean.setPageArray(pageInfo(1, pageIndex));
			}
		}
		bean.setList(list);
		bean.setCurrentPage(pageIndex);
		return DataFormat.objToJson(bean);
	}
	
	@RequestMapping(value = "/videoShow")
	public String videoShow(Model model,HttpServletRequest request) {
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if(request.getCookies() != null){
			userInfo = SignedUser.getUserInfo(request);
		}
		Integer count = 0;
		// 获取失败，返回为空
		if (userInfo != null) {
			count = missionBLL.getUnFinishMissionCount(userInfo.getAccount());
		}else{
			count = missionBLL.getUnFinishMissionCount("");
		}
		model.addAttribute("count", count);
		return "xuanwuba/wb_user_video";
		
	}

	@RequestMapping(value = "/clothingShow")
	public String clothingShow(Model model,HttpServletRequest request) {
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if(request.getCookies() != null){
			userInfo = SignedUser.getUserInfo(request);
		}
		Integer count = 0;
		// 获取失败，返回为空
		if (userInfo != null) {
			count = missionBLL.getUnFinishMissionCount(userInfo.getAccount());
		}else{
			count = missionBLL.getUnFinishMissionCount("");
		}
		model.addAttribute("count", count);
		return "xuanwuba/wb_user_cloth";
		
	}
	
	@RequestMapping(value = "/suggestShow")
	public String suggestShow(Model model,HttpServletRequest request) {
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if(request.getCookies() != null){
			userInfo = SignedUser.getUserInfo(request);
		}
		Integer count = 0;
		// 获取失败，返回为空
		if (userInfo != null) {
			count = missionBLL.getUnFinishMissionCount(userInfo.getAccount());
		}else{
			count = missionBLL.getUnFinishMissionCount("");
		}
		model.addAttribute("count", count);
		return "xuanwuba/wb_user_suggest";
		
	}
	
	@RequestMapping(value = "/convertShow")
	public String convertShow(Model model,HttpServletRequest request) {
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if(request.getCookies() != null){
			userInfo = SignedUser.getUserInfo(request);
		}
		Integer count = 0;
		// 获取失败，返回为空
		if (userInfo != null) {
			count = missionBLL.getUnFinishMissionCount(userInfo.getAccount());
		}else{
			count = missionBLL.getUnFinishMissionCount("");
		}
		model.addAttribute("count", count);
		return "xuanwuba/wb_user_exchange";
		
	}
	
	@RequestMapping(value = "/drawShow")
	public String drawShow(Model model,HttpServletRequest request) {
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if(request.getCookies() != null){
			userInfo = SignedUser.getUserInfo(request);
		}
		Integer count = 0;
		// 获取失败，返回为空
		if (userInfo != null) {
			count = missionBLL.getUnFinishMissionCount(userInfo.getAccount());
		}else{
			count = missionBLL.getUnFinishMissionCount("");
		}
		model.addAttribute("count", count);
		return "xuanwuba/wb_user_draw";
	}
	
	@RequestMapping(value = "/commentsShow")
	public String commentsShow(Model model,HttpServletRequest request) {
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if(request.getCookies() != null){
			userInfo = SignedUser.getUserInfo(request);
		}
		Integer count = 0;
		// 获取失败，返回为空
		if (userInfo != null) {
			count = missionBLL.getUnFinishMissionCount(userInfo.getAccount());
		}else{
			count = missionBLL.getUnFinishMissionCount("");
		}
		model.addAttribute("count", count);
		return "xuanwuba/wb_user_commen";
		
	}
	
	@RequestMapping(value = "/deleteComments")
	public String deleteComments(Model model,HttpServletRequest request) {
		// 接收Cookie中的信息
		UserInfo userInfo = null;
		if(request.getCookies() != null){
			userInfo = SignedUser.getUserInfo(request);
		}
		// 获取失败，返回为空
		if (userInfo != null) {
		commentsBll.deleteByUser(userInfo.getAccount());
			
		}	
		return "redirect:commentsShow";
		
	}
	
	@RequestMapping(value = "/deleteComment")
	public String deleteComment(Model model,HttpServletRequest request,String code) {
		// 获取失败，返回为空
		CommentsBean comm = commentsBll.getCommonByCode(Integer.parseInt(code));
		materialAuditBll.updateCommentNum(-1,comm.getMaterialCode());
		materialAuditBll.resetBestFlag(comm.getMaterialCode());
		commentsBll.deleteComments(Integer.parseInt(code));
		return "redirect:commentsShow";
		
	}
	
	/**
	 * 个人信息
	 * @param model
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getInfo", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getInfo(  Model model,
			HttpServletRequest request) throws UnsupportedEncodingException {
		ResultBean<PlayerInfoBean> result = new ResultBean<PlayerInfoBean>(false, "获取用户信息失败", null);
		UserInfo userInfo = null;
		if(request.getCookies() != null){
			userInfo = SignedUser.getUserInfo(request);
		}
		// 获取失败，返回为空
		if (userInfo == null) {
			result.setProperties(false, "很抱歉，您的登陆超时", null);
			logger.info("message" + result.getMessage());
			return DataFormat.objToJson(result);
			
		}	
		PlayerInfoBean playerInfoBean= playerinfoBLL.getInfoByAccount(userInfo.getAccount());
		
		result.setProperties(true, "成功", playerInfoBean);
		
		logger.info("炫舞吧XWBTaskController-个人信息" + playerInfoBean);
		return DataFormat.objToJson(result);
	}
	
	@RequestMapping(value = "/updateInfo", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateInfo(Model model,
			HttpServletRequest request,PlayerInfoBean playerInfoBean) throws UnsupportedEncodingException {
//		if(playerInfoBean.getRoleSex()!=null){
//			playerInfoBean.setRoleSex(new String(playerInfoBean.getRoleSex().getBytes("iso-8859-1"),"utf-8"));
//		}
		ResultBean<PlayerInfoBean> result = new ResultBean<PlayerInfoBean>(false, "服务器变更失败", null);
		UserInfo userInfo = null;
//		if(playerInfoBean.getServerName()!=null){
//			playerInfoBean.setServerName(new String(playerInfoBean.getServerName().getBytes("ISO-8859-1"),"UTF-8"));
//		} 
		if(request.getCookies() != null){
			userInfo = SignedUser.getUserInfo(request);
		}
		// 获取失败，返回为空
		if (userInfo == null) {
			result.setProperties(false, "很抱歉，您的登陆超时", null);
			
			logger.info("message" + result.getMessage());
			return DataFormat.objToJson(result);
			
		}	
		playerInfoBean.setAccount(userInfo.getAccount());
		playerInfoBean.setUserId(userInfo.getUserId());
		Integer i = xWBService.updateUserInfo(playerInfoBean);
		if(i>0){
			result.setProperties(true, "成功", playerInfoBean);
		}
		logger.info("message" + result.getMessage());
		return DataFormat.objToJson(result);
	}
	
	/**
	 * 根据区号获取服务器列表
	 * */
	@RequestMapping(value = "/getXWBServer", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getXWBServer(HttpServletRequest request,
			HttpServletResponse response, Integer domainNum) {
		return DataFormat.objToJson(getServerByDomainNum(domainNum));
		
	}

	private List<Data> getServerByDomainNum(Integer domainNum) {
		List<Data> list = callWeb.getAllServerXuanWuBa();
		List<Data> serverList = new ArrayList<Data>();
		for (Data temp : list) {
			if (temp.getNetTypeCode() == domainNum && !temp.getServerName().equals("内测体验服务器") && !temp.getServerName().equals("光宇内测") ) {
				serverList.add(temp);
			}
		}
		return serverList;
	}
	
	@RequestMapping(value = "/addMissionReceive")
	public String addMissionReceive(Model model,HttpServletRequest request,String code) {
		MissionReceiveBean missionReceiveBean = new MissionReceiveBean();
		UserInfo userInfo = null;
		if(request.getCookies() != null){
			userInfo = SignedUser.getUserInfo(request);
		}
		// 获取失败，返回为空
		if (userInfo != null) {
			missionReceiveBean.setAcount(userInfo.getAccount());
			missionReceiveBean.setReceiveTime(new Date());
			missionReceiveBean.setMissionCode(Integer.parseInt(code));
			missionReceiveBean.setCompleteStatus("未完成");
			missionReceiveBean.setCount(0);
			missionReceiveBLL.addMissionReceive(missionReceiveBean);
		}	
		return "redirect:taskShow";
		
	}

	/**
	 * 
	 * @日期：2015年10月14日
	 * @Title: "getMissionReceiveLog"
	 * @Description: TODO 查询完成记录 
	 * @param 
	 * @return 
	 */
    @RequestMapping(value="getMissionReceiveLog", method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    @ResponseBody
	public String getMissionReceiveLog(Model model){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	MissionReceiveBean missionReceive = new MissionReceiveBean();
    	List<MissionReceiveBean> list = missionReceiveBLL.getMissionReceiveLog(missionReceive);
    	for (MissionReceiveBean missionReceiveBean : list) {
    		missionReceiveBean.setFinishTimeStr(sdf.format(missionReceiveBean.getFinishTime()));
    		String str = missionReceiveBean.getAcount();
			if(0 < str.length() && str.length() <= 3){
				str = String.valueOf(str.charAt(0))+matchStr(str.length()-1);
			}else if(str.length() == 4){
				str = String.valueOf(str.charAt(0))+matchStr(str.length()-2)+String.valueOf(str.charAt(str.length()-1 ));
			}else if(str.length() >=5){
				str = str.substring(0, 2)+matchStr(str.length()-4)+str.substring(str.length()-2, str.length());
			}else{
				
			}
			missionReceiveBean.setAcount(str);
		}
    	return DataFormat.objToJson(list);
	}
    public static String matchStr(int num){
		String str = "";
		for(int i = 0;i < num;i++){
			str += "*";
		}
		return str;
	}
    private String replaceImg(String str) {
		String[] s = new String[98];
		for(int i = 0 ;i<s.length;i++){
			s[i] = "[em="+(i+1)+"]";
		}
		for(int i = 0 ;i<s.length;i++){
				str = str.replace(s[i],  "<img src='http://res.gyyx.cn/mgp2res/images/faces/"+(i+1)+".gif'/>");
		}
		return str;
	}
	
    private int[] pageInfo(Integer total,Integer index) {
		int[] resultArray;
		int k  = 0;
		if(total <= 7){
			resultArray = new int[total];
			for(int i = 1;i<= total;i++){
				resultArray[k] = i;
				k ++;
			}
		}else{
			resultArray = new int[7];
			if(index - 3 <= 1){
				for(int i = 1;i <= 7; i++){
					resultArray[k] = i;
					k++;
				}
			}else if(index - 3 > 1){
				if(index + 3 < total){
					for(int i = index - 3;i <= index+3; i++){
						resultArray[k] = i;
						k++;
					}
				}else{
					for(int i = total - 6;i <= total; i++){
						resultArray[k] = i;
						k++;
					}
				}
			}
		}
		return resultArray;
	}

}
