package cn.gyyx.action.oa.backstage;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.gyyx.action.beans.backstage.ActionBean;
import cn.gyyx.action.beans.backstage.LimitBean;
import cn.gyyx.action.beans.lottery.ActionChangeLog;
import cn.gyyx.action.beans.lottery.ChancePrizeBean;
import cn.gyyx.action.beans.lottery.PrizeBean;
import cn.gyyx.action.beans.lottery.QualificationBean;
import cn.gyyx.action.beans.novicecard.ActivityConfigBean;
import cn.gyyx.action.beans.xwbcreditsluckydraw.NewPageBean;
import cn.gyyx.action.bll.backstage.LimitBLL;
import cn.gyyx.action.bll.lottery.UserLotteryBll;
import cn.gyyx.action.bll.novicecard.ActivityConfigBll;
import cn.gyyx.action.bll.wdninestory.QualificationBLL;
import cn.gyyx.action.service.backstage.BackstageService;
import cn.gyyx.core.DataFormat;
import cn.gyyx.oa.stage.httpRequest.OAUserRequest;
import cn.gyyx.oa.stage.model.OAUserInfoModel;
@Controller
@RequestMapping("/backstage")
public class OaBackstageController {
	private UserLotteryBll userLotteryBll = new UserLotteryBll();
	private LimitBLL limitBLL = new LimitBLL();
	private ActivityConfigBll activityConfigBll = new ActivityConfigBll();
	private QualificationBLL qualificationBLL = new QualificationBLL();
	private BackstageService backstageService = new BackstageService();
	/**
	 * 权限列表
	 * @param model
	 * @return
	 */
    @RequestMapping("limitList")
    public String limitList(Model model,NewPageBean newPageBean) {
    	List<LimitBean> list = limitBLL.getLimitBeanAll(newPageBean);
    	
    	Integer count = limitBLL.selectLimitBeanAllCount();
    	newPageBean.setTotalRecords(count);
    	
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("pageBean", newPageBean);
        return "backstage/limit";
    }
	/**
	 * 显示配置信息
	 * @param model
	 * @return
	 */
	@RequestMapping("configShow")
    public String configShow(Model model,String actionCode) {
    	ActivityConfigBean activityConfigBean  = activityConfigBll.selectActivityConfigByCode(Integer.parseInt(actionCode));
    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	 String flag = "0";
    	if(activityConfigBean!=null){
    		activityConfigBean.setStartDate(sdf.format(activityConfigBean.getActivityStart()));
    		activityConfigBean.setEndDate(sdf.format(activityConfigBean.getActivityEnd()));
    		flag = "1";
    	}
    	model.addAttribute("actionCode", actionCode);
    	model.addAttribute("flag", flag);
		model.addAttribute("bean", activityConfigBean);
        return "backstage/comfig";
    }
	@RequestMapping("QualificationShow")
    public String QualificationShow(Model model,String actionCode) {
		List<QualificationBean> list  = qualificationBLL.selectByAction(Integer.parseInt(actionCode));
    	
    	model.addAttribute("actionCode", actionCode);
    
		model.addAttribute("list", list);
        return "backstage/Qualification";
    }
	  @RequestMapping(value="/uploadFile" , method = RequestMethod.POST)
	  	public String exportQualification(Model model,String actionCode,@RequestParam(value = "xlsFile")MultipartFile file,HttpServletRequest request) {
		  
		
		  	try {
				backstageService.readXls(file.getInputStream(),Integer.parseInt(actionCode));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    String userName = "";
	      	try {
	      		OAUserInfoModel userInfoModel = OAUserRequest
	      				.getUserInfoByRequest(request);
	      		if(userInfoModel!=null){
	      			userName = userInfoModel.getAccount();
	      		}
	  		} catch (IOException e) {
	  			e.printStackTrace();
	  		}
	      	ActionBean actionBean = new ActionBean();
	      	actionBean.setActionDate(new Date());
	      	actionBean.setUserName(userName);
	      	actionBean.setActionName("导入中奖资格");
	      	actionBean.setActionCode(Integer.parseInt(actionCode));
			  limitBLL.inserActionLog(actionBean);
	    	model.addAttribute("actionCode",actionCode);
	    	return "redirect:QualificationShow";
	  	}
	/**
	 * 用户活动列表
	 * @param model
	 * @return
	 */
    @RequestMapping("limitUser")
    public String limitUser(Model model,NewPageBean newPageBean,HttpServletRequest request) {
    	Integer personId = 0;
    	try {
    		OAUserInfoModel userInfoModel = OAUserRequest
    				.getUserInfoByRequest(request);
    		if(userInfoModel!=null){
    		 personId = userInfoModel.getStaffCode();
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	//personId=1730;//测试代码
    	newPageBean.setCode(personId);
    	List<LimitBean> list = limitBLL.getLimitBeanByUser(newPageBean);
    	Integer count = limitBLL.selectLimitBeanByUserCount(personId);
    	newPageBean.setTotalRecords(count);
    	
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("pageBean", newPageBean);
        return "backstage/limitUser";
    }
    @RequestMapping("limitUserDemand")
    public String limitUserDemand(Model model,NewPageBean newPageBean,HttpServletRequest request) {
    	Integer personId = 0;
    	try {
    		OAUserInfoModel userInfoModel = OAUserRequest
    				.getUserInfoByRequest(request);
    		if(userInfoModel!=null){
    		 personId = userInfoModel.getStaffCode();
    		}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	newPageBean.setCode(personId);
    	List<LimitBean> list = limitBLL.getLimitBeanByUser(newPageBean);
    	Integer count = limitBLL.selectLimitBeanByUserCount(personId);
    	newPageBean.setTotalRecords(count);
    	
		model.addAttribute("list", list);
		model.addAttribute("count", count);
		model.addAttribute("pageBean", newPageBean);
        return "backstage/limitUserDemand";
    }
    /**
     * 按编号查询权限
     * @param model
     * @return
     */
    @RequestMapping(value="getLimitByCode", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getLimitByCode(String code,Model model) {
    	LimitBean limitBean = limitBLL.getLimitBeanByCode(Integer.parseInt(code));
    	return DataFormat.objToJson(limitBean);
    }

    /**
     * 
      * <p>
      *   根据编号查询活动配置信息
      * </p>
      *
      * @action
      *    laixiancai 2017年3月3日 下午7:41:20 修改SimpleDateFormat格式化方式
      *
      * @param code
      * @param model
      * @return String
     */
    @RequestMapping(value = "getConfigByCode", method = RequestMethod.GET,
        produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getConfigByCode(String code, Model model) {
        ActivityConfigBean activityConfigBean = activityConfigBll
                .selectActivityConfigByCode(Integer.parseInt(code));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (activityConfigBean != null) {
            activityConfigBean.setStartDate(sdf.format(activityConfigBean
                    .getActivityStart()));
            activityConfigBean.setEndDate(sdf.format(activityConfigBean
                    .getActivityEnd()));
        }
        return DataFormat.objToJson(activityConfigBean);
    }
    
    /**
     * 
      * <p>
      *    更新活动配置信息
      * </p>
      *
      * @action
      *    laixiancai 2017年3月3日 下午7:30:33 修改SimpleDateFormat格式化方式
      *
      * @param model
      * @param activityConfigBean
      * @param request
      * @return String
     */
    @RequestMapping("/updateConfig")
    public String updateConfig(Model model,
            ActivityConfigBean activityConfigBean, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            activityConfigBean.setActivityStart(sdf.parse(activityConfigBean
                    .getStartDate()));
            activityConfigBean.setActivityEnd(sdf.parse(activityConfigBean
                    .getEndDate()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        activityConfigBll.updateActivity(activityConfigBean);
        String userName = "";
        try {
            OAUserInfoModel userInfoModel = OAUserRequest
                    .getUserInfoByRequest(request);
            if (userInfoModel != null) {
                userName = userInfoModel.getAccount();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ActionBean actionBean = new ActionBean();
        actionBean.setActionDate(new Date());
        actionBean.setUserName(userName);
        actionBean.setActionName("修改活动配置信息");
        actionBean.setActionCode(activityConfigBean.getCode());
        limitBLL.inserActionLog(actionBean);
        model.addAttribute("actionCode", activityConfigBean.getCode());
        return "redirect:configShow";
    }
    
    /**
     * 
      * <p>
      *   新增一条活动配置信息
      * </p>
      *
      * @action
      *    laixiancai 2017年3月3日 下午7:36:51 修改SimpleDateFormat格式化方式
      *
      * @param model
      * @param activityConfigBean
      * @param request
      * @return String
     */
    @RequestMapping("/addConfig")
    public String addConfig(Model model, ActivityConfigBean activityConfigBean,
            HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            activityConfigBean.setActivityStart(sdf.parse(activityConfigBean
                    .getStartDate()));
            activityConfigBean.setActivityEnd(sdf.parse(activityConfigBean
                    .getEndDate()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        activityConfigBean.setDelete(false);
        activityConfigBll.insertActivity(activityConfigBean);
        String userName = "";
        try {
            OAUserInfoModel userInfoModel = OAUserRequest
                    .getUserInfoByRequest(request);
            if (userInfoModel != null) {
                userName = userInfoModel.getAccount();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ActionBean actionBean = new ActionBean();
        actionBean.setActionDate(new Date());
        actionBean.setUserName(userName);
        actionBean.setActionCode(activityConfigBean.getCode());
        actionBean.setActionName("添加活动配置信息");
        limitBLL.inserActionLog(actionBean);
        model.addAttribute("actionCode", activityConfigBean.getCode());
        return "redirect:configShow";
    }
    
    @RequestMapping("/updateLimit")
  	public String updateLimit(Model model,LimitBean limitBean,NewPageBean newPageBean,HttpServletRequest request) {
    	limitBLL.updateLimit(limitBean);
    	 String userName = "";
      	try {
      		OAUserInfoModel userInfoModel = OAUserRequest
      				.getUserInfoByRequest(request);
      		if(userInfoModel!=null){
      			userName = userInfoModel.getAccount();
      		}
  		} catch (IOException e) {
  			e.printStackTrace();
  		}
      	ActionBean actionBean = new ActionBean();
      	actionBean.setActionDate(new Date());
      	actionBean.setUserName(userName);
      	actionBean.setActionCode(limitBean.getActionCode());
      	actionBean.setActionName("修改活动权限"+limitBean.getActionName());
 		  limitBLL.inserActionLog(actionBean);
  		return "redirect:limitList";
  	}
    @RequestMapping("/addLimit")
  	public String addLimit(Model model,LimitBean limitBean,NewPageBean newPageBean,HttpServletRequest request) {
    	limitBLL.addLimit(limitBean);
    	 String userName = "";
       	try {
       		OAUserInfoModel userInfoModel = OAUserRequest
       				.getUserInfoByRequest(request);
       		if(userInfoModel!=null){
       			userName = userInfoModel.getAccount();
       		}
   		} catch (IOException e) {
   			e.printStackTrace();
   		}
       	ActionBean actionBean = new ActionBean();
       	actionBean.setActionDate(new Date());
       	actionBean.setUserName(userName);
       	actionBean.setActionCode(limitBean.getActionCode());
       	actionBean.setActionName("增加活动权限"+limitBean.getActionName());
  		  limitBLL.inserActionLog(actionBean);
  		return "redirect:limitList";
  	}
    @RequestMapping("/deleteLimit")
  	public String deleteLimit(Model model,NewPageBean newPageBean,HttpServletRequest request) {
    	 String userName = "";
        	try {
        		OAUserInfoModel userInfoModel = OAUserRequest
        				.getUserInfoByRequest(request);
        		if(userInfoModel!=null){
        			userName = userInfoModel.getAccount();
        		}
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	ActionBean actionBean = new ActionBean();
        	actionBean.setActionDate(new Date());
        	actionBean.setUserName(userName);
        	actionBean.setActionCode(newPageBean.getCode());
        	actionBean.setActionName("删除活动权限");
   		  limitBLL.inserActionLog(actionBean);
    	limitBLL.deleteLimit(newPageBean.getCode());
    	
  		return "redirect:limitList?pageIndex=" + newPageBean.getPageIndex();
  	}
    
    /**
     * 
      * <p>
      *    根据活动编号获取活动所有奖品
      * </p>
      *
      * @action
      *    gyyxlxc 2017年3月3日 下午8:17:30 调整SQL查询语句
      *
      * @param model
      * @param httpResponse
      * @param actionCode
      * @return String
     */
    @RequestMapping(value = "/getProbabilityList", method = RequestMethod.GET)
    public String getProbabilityList(Model model,
            HttpServletResponse httpResponse, String actionCode) {
        List<PrizeBean> list = userLotteryBll.getPrizeAll(Integer
                .parseInt(actionCode));
        if (list != null) {
            for (PrizeBean prizeBean : list) {
                ChancePrizeBean ChancePrizeBean = userLotteryBll
                        .getChancePrizeByprizeCode(prizeBean.getCode());
                if (ChancePrizeBean != null) {
                    prizeBean.setProbability(ChancePrizeBean.getProbability());
                    prizeBean.setNumber(ChancePrizeBean.getNumber());
                }
            }
        }
        
        model.addAttribute("list", list);
        model.addAttribute("actionCode", actionCode);
        return "backstage/prizeList";
    }
    
    @RequestMapping(value="prizeOne", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String prizeOne(Model model,String code) {
        ChancePrizeBean ChancePrizeBean = userLotteryBll.getPrizeProbabilityAndNumberByPrizeCode(Integer.parseInt(code));
        PrizeBean PrizeBean = userLotteryBll.getPrizeByCode(Integer.parseInt(code));
        if(ChancePrizeBean!=null){
                PrizeBean.setProbability(ChancePrizeBean.getProbability());
                PrizeBean.setNumber(ChancePrizeBean.getNumber());
        }
       return DataFormat.objToJson(PrizeBean);
       }
    
    @RequestMapping("/updatePrize")
    public String prizeUpdate(Model model, PrizeBean prizeBean,
            String actionCode, HttpServletRequest request) {
        ChancePrizeBean chancePrizeBean = new ChancePrizeBean();
        List<ChancePrizeBean> chancePrizeBeans = userLotteryBll
                .getChancePrizesByPrizeCode(prizeBean.getCode());
        String userName = "";
        try {
            OAUserInfoModel userInfoModel = OAUserRequest
                    .getUserInfoByRequest(request);
            if (userInfoModel != null) {
                userName = userInfoModel.getAccount();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int prizePoolCount = chancePrizeBeans.size();
        int temp=1;
        double tempOldProbability=0;
        for(ChancePrizeBean tempPrizeBean :chancePrizeBeans){
            chancePrizeBean.setCode(tempPrizeBean.getCode());
            chancePrizeBean.setPrizeCode(prizeBean.getCode());
          //如果奖品数量是-1，则数量不做平均运算
            if(prizeBean.getNumber()==-1){
                chancePrizeBean.setNumber(prizeBean.getNumber());
            }else{
                int remainder=prizeBean.getNumber() % prizePoolCount;//余数
                if(remainder>0){
                    if(temp==chancePrizeBeans.size()){
                        chancePrizeBean.setNumber((prizeBean.getNumber() / prizePoolCount)+remainder);
                    }else{
                        chancePrizeBean.setNumber(prizeBean.getNumber() / prizePoolCount);
                    }
                }else{
                    chancePrizeBean.setNumber(prizeBean.getNumber() / prizePoolCount);
                }
            }
            
            chancePrizeBean.setProbability(prizeBean.getProbability()
                    / prizePoolCount);
            userLotteryBll.updateChancePrizeBean(chancePrizeBean);
            temp++;
            tempOldProbability+=tempPrizeBean.getProbability();//将原来的奖池概率求和，用于下面的日志记录
        }

        userLotteryBll.updatePrizeBeanAll(prizeBean);
        
        ActionChangeLog actionChangeLog = new ActionChangeLog();
        actionChangeLog.setAccount(userName);
        actionChangeLog.setNewProbability(prizeBean.getProbability());
        actionChangeLog.setUpdateDate(new Date());
        actionChangeLog.setOldProbability(tempOldProbability);
        actionChangeLog.setActionCode(Integer.parseInt(actionCode));
        actionChangeLog.setNumber(prizeBean.getNumber());
        userLotteryBll.insertActionChangeLog(actionChangeLog);

        ActionBean actionBean = new ActionBean();
        actionBean.setActionDate(new Date());
        actionBean.setUserName(userName);
        actionBean.setActionCode(Integer.parseInt(actionCode));
        actionBean.setActionName("修改奖品信息");
        limitBLL.inserActionLog(actionBean);
        model.addAttribute("actionCode", actionCode);
        return "redirect:getProbabilityList";
    }
    
    @RequestMapping("/insertPrize")
  	public String insertPrize(Model model,PrizeBean prizeBean,String actionCode,HttpServletRequest request) {
    	 ChancePrizeBean chancePrizeBean = new ChancePrizeBean();
    		String userName = "";
        	try {
        		OAUserInfoModel userInfoModel = OAUserRequest
        				.getUserInfoByRequest(request);
        		if(userInfoModel!=null){
        			userName = userInfoModel.getAccount();
        		}
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    	 ActionChangeLog actionChangeLog = new ActionChangeLog();
    
    		 actionChangeLog.setAccount(userName);
    	 
    	 actionChangeLog.setNewProbability(prizeBean.getProbability());
    	 actionChangeLog.setUpdateDate(new Date());
    	 actionChangeLog.setOldProbability(0);
    	 actionChangeLog.setActionCode(Integer.parseInt(actionCode));
    	 actionChangeLog.setNumber(prizeBean.getNumber());
    	 userLotteryBll.insertPrize(prizeBean);
    	 userLotteryBll.insertActionChangeLog(actionChangeLog);
    	 chancePrizeBean.setPrizeCode(prizeBean.getCode());
    	 chancePrizeBean.setActionCode(prizeBean.getActionCode());
    	 chancePrizeBean.setNumber(prizeBean.getNumber());
    	 chancePrizeBean.setProbability(prizeBean.getProbability());
    	 userLotteryBll.insertPrizeChange(chancePrizeBean);
    	 ActionBean actionBean = new ActionBean();
      	actionBean.setActionDate(new Date());
      	actionBean.setUserName(userName);
      	actionBean.setActionCode(Integer.parseInt(actionCode));
      	actionBean.setActionName("增加奖品信息");
 		  limitBLL.inserActionLog(actionBean);
    	 model.addAttribute("actionCode", actionCode);
  		return "redirect:getProbabilityList";
  	}
    @RequestMapping(value="getPrizeByNum", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getPrizeByNum(String actionCode,String num,Model model) {
    	PrizeBean prizeBean = new PrizeBean();
    	prizeBean.setActionCode(Integer.parseInt(actionCode));
    	prizeBean.setNum(Integer.parseInt(num));
    	PrizeBean prizeBean1 = userLotteryBll.getPrizeByNum(prizeBean);
    	return DataFormat.objToJson(prizeBean1);
    }
    @RequestMapping(value="getPrizeByNumUpdate", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getPrizeByNumUpdate(String actionCode,String num,String code,Model model) {
    	PrizeBean prizeBean = new PrizeBean();
    	prizeBean.setActionCode(Integer.parseInt(actionCode));
    	prizeBean.setNum(Integer.parseInt(num));
    	PrizeBean prizeBean1 = userLotteryBll.getPrizeByNum(prizeBean);
    	if(prizeBean1!=null){
    		if(prizeBean1.getCode()==Integer.parseInt(code)){
    			prizeBean1=null;
    		}
    	}
    	return DataFormat.objToJson(prizeBean1);
    }
}
