package cn.gyyx.action.ui;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.PageBean;
import cn.gyyx.action.beans.common.ServerBean;
import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.core.auth.UserInfo;
@Controller
@RequestMapping("/tenyearcolorful")
public class WDTenYearColorfulController {

	/**
	 * index 首页
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request){
			
		return "wdtenyearcolorful/index";
	}
	
	
	@RequestMapping(value = {"/comment", "/vote", "/myworks"},method=RequestMethod.POST)
        @ResponseBody
        public ResultBean<Boolean> comment(HttpServletRequest request){
                ResultBean<Boolean> result = new ResultBean<Boolean>(false, "谢谢参与，活动已结束", null);
              
                return result;
        }
        
      
        
        /**
          * @Title: getRoleBindStatus
          * @Description:  获取用户绑定状态
          * @param 
          * @return ResultBean<Boolean> 
          * @throws
          */
        @RequestMapping(value="/getbindStatus")
        @ResponseBody
        public ResultBean<Boolean> getRoleBindStatus(HttpServletRequest request){
                ResultBean<Boolean> result = new ResultBean<Boolean>(false, "谢谢参与，活动已结束", null);
                
                return result;
                
        }
        
        
        /**
          * @Title: getServers
          * @Description:  获取服务器信息
          * @param 
          * @return ResultBean<ServerBean> 
          * @throws
          */
        @RequestMapping(value = {"/getservers", "/getRoles", "/bindrole"})
        @ResponseBody
        public ResultBean<ServerBean> getServers(HttpServletRequest request,@RequestParam("nettype")int nettype){
                ResultBean<ServerBean> result = new ResultBean<>(false, "谢谢参与，活动已结束", null);
               
                return result;
        }
      
       

        /**
         * 作品页面
         * @return
         */
        @RequestMapping("/works")
        public String toParticipateWorks(Model model,HttpServletRequest request){

            return "wdtenyearcolorful/works";
        }
        
        /**
          * @Title: searchWorkBean
          * @Description:  根据编码获取作品信息
          * @param 
          * @return ResultBean<WorkBean> 
          * @throws
          */
        @RequestMapping(value = "/searchwork")
        @ResponseBody
        public ResultBean searchWorkBean(@RequestParam("workcode")String workCode){
                ResultBean result = new ResultBean(false, "谢谢参与，活动已结束", null);
              
                return result;
        }
        
         
        /**
          * @Title: getNewsetWorks
          * @Description:  获取最热作品
          * @param 
          * @return ResultBean<List<WorkBean>> 
          * @throws
          */
        @RequestMapping(value = "/works/hottest")
        @ResponseBody
        public ResultBean<PageBean<HashMap>> getHottestWorks(@RequestParam("pageIndex")int pageIndex,@RequestParam("pageSize")int pageSize){
                ResultBean<PageBean<HashMap>> result = new ResultBean<>(false, "谢谢参与，活动已结束", null);
              
                return result;
        }
        
        
        /**
          * @Title: getNewsetWorks
          * @Description:  获取最热作品
          * @param 
          * @return ResultBean<List<WorkBean>> 
          * @throws
          */
        @RequestMapping(value = "/works/newest")
        @ResponseBody
        public ResultBean<PageBean<HashMap>> getNewestWorks(@RequestParam("pageIndex")int pageIndex,@RequestParam("pageSize")int pageSize){
                ResultBean<PageBean<HashMap>> result = new ResultBean<>(false, "谢谢参与，活动已结束", null);
                
                return result;
        }
        
        /**
          * @Title: paintColor
          * @Description:  保存添彩
          * @param 
          * @return ResultBean<Boolean> 
          * @throws
          */
        @RequestMapping(value = "/savepaintcolor",method = RequestMethod.POST)
        @ResponseBody
        public ResultBean<Boolean> paintColor(HttpServletRequest request){
                ResultBean<Boolean> result = new ResultBean<>(false, "谢谢参与，活动已结束", null);

                return result;
        }
        
        @RequestMapping("/attendcompetition")
        @ResponseBody
        public ResultBean<Boolean> attendCompetition(@RequestParam("workId")int workId,HttpServletRequest request){
                ResultBean<Boolean> result = new ResultBean<>(false, "谢谢参与，活动已结束", null);

                return result;
        }
        
        /**
         * 添彩页面
         * @return
         * @throws IOException 
         */
        @RequestMapping("/paintcolor")
        public String toPaintColor(Model model,HttpServletRequest request) throws IOException{

            return "wdtenyearcolorful/paintcolor";
        }
        
        private ResultBean<UserInfo> checkLogin(HttpServletRequest request){
                ResultBean<UserInfo> result = new ResultBean<>(false, "谢谢参与，活动已结束", null);
                
                return result;
        }
	
}
