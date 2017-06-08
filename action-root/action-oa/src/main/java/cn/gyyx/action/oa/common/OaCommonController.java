package cn.gyyx.action.oa.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.common.ActionCommonCommentBean;
import cn.gyyx.action.beans.common.ActionCommonFormBean;
import cn.gyyx.action.beans.common.ActionCommonImageBean;
import cn.gyyx.action.beans.novicecard.ServerInfoBean.Value;
import cn.gyyx.action.beans.wdpetdating.CheckImgDTO;
import cn.gyyx.action.beans.wdpetdating.WdDatingPet;
import cn.gyyx.action.bll.common.OaCommonBLL;
import cn.gyyx.action.dao.common.ActionCommonFormDAO;
import cn.gyyx.action.service.agent.CallWebApiAgent;
import cn.gyyx.action.service.common.OaCommonService;

@Controller
@RequestMapping(value = "/oacommon")
public class OaCommonController {
	private OaCommonBLL oaCommonBLL = new OaCommonBLL();
	private OaCommonService oaCommonService = new OaCommonService();
	private Integer actionCode =264;
	private static final CallWebApiAgent callWebApiAgent = new CallWebApiAgent();
		//TODO 
		@RequestMapping(value="/showImgCheck",method=RequestMethod.GET)
		public String showImgCheck(Model model,CheckImgDTO checkImgDTO) throws UnsupportedEncodingException {
			checkImgDTO.setActionCode(264);
			Integer pageCount = 0;
			checkImgDTO.setBeginPage(true);
			checkImgDTO.setLastPage(true);
			List<ActionCommonImageBean> res; 
			List<ActionCommonImageBean> rese; 
			if(checkImgDTO.getNetType()==null){
				checkImgDTO.setNetType("");
			}
			if(checkImgDTO.getAccount()==null){
				checkImgDTO.setAccount("");
			}
			if(checkImgDTO.getArea()==null){
				checkImgDTO.setArea("");
			}
			if(checkImgDTO.getStatus()==null){
				checkImgDTO.setStatus("");
			}
			checkImgDTO.setNetType(new String(checkImgDTO.getNetType().getBytes("ISO-8859-1"),"UTF-8"));
			checkImgDTO.setAccount(new String(checkImgDTO.getAccount().getBytes("ISO-8859-1"),"UTF-8"));
			checkImgDTO.setArea(new String(checkImgDTO.getArea().getBytes("ISO-8859-1"),"UTF-8"));
			checkImgDTO.setStatus(new String(checkImgDTO.getStatus().getBytes("ISO-8859-1"),"UTF-8"));
			if(checkImgDTO.getNetType().equals("1")){
				checkImgDTO.setNetType("网通");
			}
			if(checkImgDTO.getNetType().equals("2")){
				checkImgDTO.setNetType("电信");
			}
			if(checkImgDTO.getNetType().equals("3")){
				checkImgDTO.setNetType("双线");
			}
			rese = oaCommonBLL.getImgBeanByPagePass(checkImgDTO);
				res = oaCommonBLL.getImgBeanByPage(checkImgDTO);
				SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
			for(int i = 0; i < res.size(); i++){  
				ActionCommonImageBean actionCommonImageBean = res.get(i);
				ActionCommonFormDAO formDao = new ActionCommonFormDAO();
				ActionCommonFormBean bean =  formDao.selectActionCommonFormBeanBycode(actionCommonImageBean.getFormCode());
				WdDatingPet wdDatingPet = formDao.convertFromJson(bean, WdDatingPet.class);
				if(wdDatingPet!=null){
					if(wdDatingPet.getUploadDate()!=null){
						
						wdDatingPet.setsDate(sdf.format(wdDatingPet.getUploadDate()));
						}
					actionCommonImageBean.setWdDatingPet(wdDatingPet);
					
				}
				else{
				res.remove(i);  
	            i--;  
				}
	        }  
			checkImgDTO.setCount(rese.size());
			if(checkImgDTO.getPageIndex()==0){
				checkImgDTO.setBeginPage(false);
			}
			if(checkImgDTO.getCount()!=0){
			 if(checkImgDTO.getCount()%10!=0){
				 pageCount = checkImgDTO.getCount()/10 + 1;}
			 else{
				 pageCount = checkImgDTO.getCount()/10;
			 }
			
				if(checkImgDTO.getPageIndex()+1==pageCount){
					checkImgDTO.setLastPage(false);
				}
			
			}
			if(rese.size()==0){
				checkImgDTO.setBeginPage(false);
				checkImgDTO.setLastPage(false);
			}
			model.addAttribute("faces",res);
			model.addAttribute("checkImgDTO",checkImgDTO);
			model.addAttribute("actionCode",actionCode);
			return "oacommon/imgChecked";
		}
		@RequestMapping(value="/showImgCheck1",method=RequestMethod.GET)
		public String showImgCheck1(Model model,CheckImgDTO checkImgDTO) throws UnsupportedEncodingException {
			SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
			checkImgDTO.setActionCode(264);
			checkImgDTO.setPageIndex(0);
			checkImgDTO.setBeginPage(false);
			checkImgDTO.setLastPage(true);
			List<ActionCommonImageBean> res; 
			List<ActionCommonImageBean> rese;
			if(checkImgDTO.getNetType()==null){
				checkImgDTO.setNetType("");
			}
			if(checkImgDTO.getAccount()==null){
				checkImgDTO.setAccount("");
			}
			if(checkImgDTO.getArea()==null){
				checkImgDTO.setArea("");
			}
			if(checkImgDTO.getStatus()==null){
				checkImgDTO.setStatus("");
			}
			checkImgDTO.setNetType(new String(checkImgDTO.getNetType().getBytes("ISO-8859-1"),"UTF-8"));
			checkImgDTO.setAccount(new String(checkImgDTO.getAccount().getBytes("ISO-8859-1"),"UTF-8"));
			checkImgDTO.setArea(new String(checkImgDTO.getArea().getBytes("ISO-8859-1"),"UTF-8"));
			checkImgDTO.setStatus(new String(checkImgDTO.getStatus().getBytes("ISO-8859-1"),"UTF-8"));
			if(checkImgDTO.getNetType().equals("1")){
				checkImgDTO.setNetType("网通");
			}
			if(checkImgDTO.getNetType().equals("2")){
				checkImgDTO.setNetType("电信");
			}
			if(checkImgDTO.getNetType().equals("3")){
				checkImgDTO.setNetType("双线");
			}
			rese = oaCommonBLL.getImgBeanByPagePass(checkImgDTO);
				res = oaCommonBLL.getImgBeanByPage(checkImgDTO);
				checkImgDTO.setCount(rese.size());
		
			for(int i = 0; i < res.size(); i++){  
				ActionCommonImageBean actionCommonImageBean = res.get(i);
				
				ActionCommonFormDAO formDao = new ActionCommonFormDAO();
				ActionCommonFormBean bean =  formDao.selectActionCommonFormBeanBycode(actionCommonImageBean.getFormCode());
				WdDatingPet wdDatingPet = formDao.convertFromJson(bean, WdDatingPet.class);
				if(wdDatingPet!=null){
					if(wdDatingPet.getUploadDate()!=null){
						
						wdDatingPet.setsDate(sdf.format(wdDatingPet.getUploadDate()));
						}
					actionCommonImageBean.setWdDatingPet(wdDatingPet);
				}
				else{
				res.remove(i);  
	            i--;  
				}
	        }  
			Integer pageCount = 0;
			checkImgDTO.setCount(rese.size());
			if(checkImgDTO.getPageIndex()==0){
				checkImgDTO.setBeginPage(false);
			}
			if(checkImgDTO.getCount()!=0){
			 if(checkImgDTO.getCount()%10!=0){
				 pageCount = checkImgDTO.getCount()/10 + 1;}
			 else{
				 pageCount = checkImgDTO.getCount()/10;
			 }
			
				if(checkImgDTO.getPageIndex()+1==pageCount){
					checkImgDTO.setLastPage(false);
				}
			
			}
			if(rese.size()==0){
				checkImgDTO.setBeginPage(false);
				checkImgDTO.setLastPage(false);
			}
		
			
			model.addAttribute("faces",res);
			model.addAttribute("checkImgDTO",checkImgDTO);
			model.addAttribute("actionCode",actionCode);
			return "oacommon/imgChecked";
		}
		/**
		 * 导出中奖名单
		 * @return
		 * @throws UnsupportedEncodingException 
		 */
		@SuppressWarnings("deprecation")
		@RequestMapping(value="/exportTaskList",method=RequestMethod.GET)
		public void index(HttpServletResponse httpResponse,CheckImgDTO checkImgDTO) throws UnsupportedEncodingException {
			SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
			checkImgDTO.setActionCode(264);
			List<ActionCommonImageBean> res; 
			if(checkImgDTO.getNetType()==null){
				checkImgDTO.setNetType("");
			}
			if(checkImgDTO.getAccount()==null){
				checkImgDTO.setAccount("");
			}
			if(checkImgDTO.getArea()==null){
				checkImgDTO.setArea("");
			}
			if(checkImgDTO.getStatus()==null){
				checkImgDTO.setStatus("");
			}
			checkImgDTO.setNetType(new String(checkImgDTO.getNetType().getBytes("ISO-8859-1"),"UTF-8"));
			checkImgDTO.setAccount(new String(checkImgDTO.getAccount().getBytes("ISO-8859-1"),"UTF-8"));
			checkImgDTO.setArea(new String(checkImgDTO.getArea().getBytes("ISO-8859-1"),"UTF-8"));
			checkImgDTO.setStatus(new String(checkImgDTO.getStatus().getBytes("ISO-8859-1"),"UTF-8"));
			if(checkImgDTO.getNetType().equals("1")){
				checkImgDTO.setNetType("网通");
			}
			if(checkImgDTO.getNetType().equals("2")){
				checkImgDTO.setNetType("电信");
			}
			if(checkImgDTO.getNetType().equals("3")){
				checkImgDTO.setNetType("双线");
			}
			res = oaCommonBLL.getImgBeanByPagePass(checkImgDTO);

		
		for(int i = 0; i < res.size(); i++){  
			ActionCommonImageBean actionCommonImageBean = res.get(i);
			ActionCommonFormDAO formDao = new ActionCommonFormDAO();
			ActionCommonFormBean bean =  formDao.selectActionCommonFormBeanBycode(actionCommonImageBean.getFormCode());
			WdDatingPet wdDatingPet = formDao.convertFromJson(bean, WdDatingPet.class);
			if(wdDatingPet!=null){
					if(wdDatingPet.getUploadDate()!=null){
						wdDatingPet.setsDate(sdf.format(wdDatingPet.getUploadDate()));
					}
				actionCommonImageBean.setWdDatingPet(wdDatingPet);
			}
			else{
			res.remove(i);  
            i--;  
			}
        }  
		try {
			oaCommonService.ExtendExcel(httpResponse, res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}	
//		@RequestMapping(value="/showPassCheck",method=RequestMethod.GET)
//		public String showPassCheck(Model model,@RequestParam("pageIndex")int pageIndex) {
//			List<ActionCommonImageBean> res;
//			if(pageIndex >= 0) {
//				res = oaCommonBLL.getImgBeanByPagePass(pageIndex,actionCode);
//			} else {
//				pageIndex = 0;
//				res = oaCommonBLL.getImgBeanByPagePass(pageIndex,actionCode);
//			}
//			for(ActionCommonImageBean actionCommonImageBean:res){
//				WdDatingPet wdDatingPet = petDatingBLL.getWdDatingPetByCode(actionCommonImageBean.getFormCode());
//				actionCommonImageBean.setWdDatingPet(wdDatingPet);
//			}
//			model.addAttribute("faces",res);
//			model.addAttribute("pageIndex",pageIndex);
//			model.addAttribute("actionCode",actionCode);
//			return "oacommon/imgCheck";
//		}
		@RequestMapping(value="/passImg",method=RequestMethod.GET)
		public String passImg(Model model,CheckImgDTO checkImgDTO,@RequestParam("code")int code) {
			ActionCommonImageBean img = oaCommonBLL.getImgBycode(code);
			System.out.println();
			if(img != null) {
				img.setImgType("wd1");
				ResultBean<String> res = oaCommonService.passImg(img);
				if(!res.getIsSuccess()) {
					model.addAttribute("error",res.getMessage());
				} else {
					System.out.println();
					img.setStatus("审核通过");
					oaCommonBLL.updateImg(img);
				}
			} else {
				model.addAttribute("error","img code missing.");
			}
		
			return "redirect:showImgCheck?pageIndex=" + checkImgDTO.getPageIndex()+"&account="+checkImgDTO.getAccount()+"&area="+checkImgDTO.getArea()+"&status="+checkImgDTO.getStatus()+"&netType="+checkImgDTO.getNetType();		}
		
		@RequestMapping(value="/unpassImg",method=RequestMethod.GET)
		public String unpassImg(Model model,CheckImgDTO checkImgDTO,@RequestParam("code")int code) {
			ActionCommonImageBean actionCommonImageBean = new ActionCommonImageBean();
			actionCommonImageBean.setCode(code);
			actionCommonImageBean.setStatus("审核未通过");
			oaCommonBLL.updateImg(actionCommonImageBean);
			return "redirect:showImgCheck?pageIndex=" + checkImgDTO.getPageIndex()+"&account="+checkImgDTO.getAccount()+"&area="+checkImgDTO.getArea()+"&status="+checkImgDTO.getStatus()+"&netType="+checkImgDTO.getNetType();
		}
//		@RequestMapping(value="/unpass",method=RequestMethod.GET)
//		public String undateImg(Model model,@RequestParam("pageIndex")int pageIndex,@RequestParam("code")int code) {
//			ActionCommonImageBean actionCommonImageBean = new ActionCommonImageBean();
//			actionCommonImageBean.setCode(code);
//			actionCommonImageBean.setStatus("审核未通过");
//			oaCommonBLL.updateImg(actionCommonImageBean);
//			model.addAttribute("pageIndex",pageIndex);
//			model.addAttribute("actionCode",actionCode);
//			return "redirect:showPassCheck?pageIndex=" + pageIndex;
//		}
		@RequestMapping(value="/pass",method=RequestMethod.GET)
		public String undatePass(Model model,CheckImgDTO checkImgDTO,@RequestParam("code")int code) {
			ActionCommonImageBean actionCommonImageBean = new ActionCommonImageBean();
			actionCommonImageBean.setCode(code);
			actionCommonImageBean.setStatus("未审核");
			oaCommonBLL.updateImg(actionCommonImageBean);
			model.addAttribute("actionCode",actionCode);
			return "redirect:showImgCheck?pageIndex=" + checkImgDTO.getPageIndex()+"&account="+checkImgDTO.getAccount()+"&area="+checkImgDTO.getArea()+"&status="+checkImgDTO.getStatus()+"&netType="+checkImgDTO.getNetType();
		}
		@RequestMapping(value="/showTalkCheck",method=RequestMethod.GET)
		public String showTalkCheck(Model model,@RequestParam("pageIndex")int pageIndex) {
			List<ActionCommonCommentBean> res;
			if(pageIndex >= 0) {
				res = oaCommonBLL.getTalkByPage (pageIndex, actionCode, "未审核");
			} else {
				pageIndex = 0;
				res = oaCommonBLL.getTalkByPage(pageIndex, actionCode, "未审核");
			}
			Integer i = oaCommonBLL.getTalk(actionCode);
			CheckImgDTO checkImgDTO = new CheckImgDTO();
			Integer pageCount = 0;
			checkImgDTO.setBeginPage(true);
			checkImgDTO.setLastPage(true);
			if(pageIndex==0){
				checkImgDTO.setBeginPage(false);
			}
			if(i!=0){
			 if(i%10!=0){
				 pageCount = i/10 + 1;}
			 else{
				 pageCount = i/10;
			 }
			
				if(pageIndex+1==pageCount){
					checkImgDTO.setLastPage(false);
				}
			}
			if(i==0){
				checkImgDTO.setBeginPage(false);
				checkImgDTO.setLastPage(false);
			}
			System.out.println(checkImgDTO.isBeginPage());
			System.out.println(checkImgDTO.isLastPage());
			model.addAttribute("count",i);
			model.addAttribute("faces",res);
			model.addAttribute("pageIndex",pageIndex);
			model.addAttribute("checkImgDTO",checkImgDTO);
			model.addAttribute("actionCode",actionCode);
			return "oacommon/talkChecked";
		}
		@RequestMapping(value="/returnStatus",method=RequestMethod.GET)
		public String returnStatus(Model model,@RequestParam("pageIndex")int pageIndex,@RequestParam("code")int code) {
			ActionCommonCommentBean actionCommonCommentBean = new ActionCommonCommentBean();
			actionCommonCommentBean.setCode(code);
			actionCommonCommentBean.setStatus("未审核");
			oaCommonBLL.updateTalk(actionCommonCommentBean);
			model.addAttribute("pageIndex",pageIndex);
			model.addAttribute("actionCode",actionCode);
			return "redirect:showTalk?pageIndex=" + pageIndex;
		}
//		@RequestMapping(value="/unpassTalk",method=RequestMethod.GET)
//		public String unpassTalk(Model model,@RequestParam("pageIndex")int pageIndex,@RequestParam("code")int code) {
//			ActionCommonCommentBean actionCommonCommentBean = new ActionCommonCommentBean();
//			actionCommonCommentBean.setCode(code);
//			actionCommonCommentBean.setStatus("审核未通过");
//			oaCommonBLL.updateTalk(actionCommonCommentBean);
//			model.addAttribute("pageIndex",pageIndex);
//			model.addAttribute("actionCode",actionCode);
//			return "redirect:showTalk?pageIndex=" + pageIndex;
//		}
		@RequestMapping(value="/unpassTalkDelete",method=RequestMethod.GET)
		public String unpassTalkDelete(Model model,@RequestParam("pageIndex")int pageIndex,@RequestParam("code")int code) {
			ActionCommonCommentBean actionCommonCommentBean = new ActionCommonCommentBean();
			actionCommonCommentBean.setCode(code);
			actionCommonCommentBean.setStatus("审核未通过");
			oaCommonBLL.updateTalk(actionCommonCommentBean);
			model.addAttribute("pageIndex",pageIndex);
			model.addAttribute("actionCode",actionCode);
			return "redirect:showTalkCheck?pageIndex=" + pageIndex;
		}
		@RequestMapping(value="/passTalk",method=RequestMethod.GET)
		public String passTalk(Model model,@RequestParam("pageIndex")int pageIndex,@RequestParam("code")int code) {
			ActionCommonCommentBean actionCommonCommentBean = new ActionCommonCommentBean();
			actionCommonCommentBean.setCode(code);
			actionCommonCommentBean.setStatus("审核通过");
			oaCommonBLL.updateTalk(actionCommonCommentBean);
			model.addAttribute("pageIndex",pageIndex);
			model.addAttribute("actionCode",actionCode);
			return "redirect:showTalkCheck?pageIndex=" + pageIndex;
		}
		@RequestMapping("/ajaxGetServerByAreaCode")
		public @ResponseBody ResultBean<List<Value>> ajaxGetServerByAreaCode(HttpServletRequest request,@RequestParam("areaCode")int areaCode){
			ResultBean<List<Value>> result = new ResultBean<List<Value>>();
			List<Value> servers = callWebApiAgent.getAllServerByNetTypeCode(areaCode + "");
			if(servers.isEmpty()) {
				result.setIsSuccess(false);
				result.setMessage("查询结果为空");
			} else {
				result.setIsSuccess(true);
				result.setData(servers);
			}
			return result;
		}
}
