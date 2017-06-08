package cn.gyyx.wd.wanjia.cartoon.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.wd.wanjia.ResultBean;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhua;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaBook;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaComment;
import cn.gyyx.wd.wanjia.cartoon.service.CatalogService;
import cn.gyyx.wd.wanjia.cartoon.service.DetailsService;
/**
 * 目录页 controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/details")
public class ManHuaDetailsController {
	@Autowired
	private DetailsService detailsService;
 
	 /**
	  * 评论展示
	  * @param request
	  * @param pageIndex
	  * @param pageSize
	  * @param type 1 :众说纷纭  2:作者有话说
	  * @param code
	  * @return
	  */
	@RequestMapping("/comment")
	@ResponseBody
	public ResultBean  comment(HttpServletRequest request,@RequestParam("pageIndex") Integer pageIndex,
			@RequestParam("pageSize") Integer pageSize,@RequestParam("type")String type,
			@RequestParam("manhuaCode")Integer manhuaCode){
		ResultBean resultBean = new ResultBean<>(false, "fail");
		if(pageIndex==null||pageSize==null||manhuaCode==null||type==null){
			resultBean.setMessage("参数不可为空");
			return resultBean;
		}
		Map<String, Object> map=null;
		int total;
		try {
			
			
			map=detailsService.findDetailsList(pageIndex,pageSize,type,manhuaCode);
			
		 
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setMessage("内部运行错误");
			return resultBean;
		}
		 
		resultBean.setSuccess(true);
		resultBean.setMessage("返回评论列表成功");
		resultBean.setData(map);
		return resultBean;
		
	}
	
	 /**
	  * 保存评论
	  * @param request
	  * @param type 1 :众说纷纭  2:作者有话说
	  * @param manhuaCode
	  * @param context
	  * @param parentCode
	  * @return
	  */
	@RequestMapping("/saveComment")
	@ResponseBody
	public ResultBean  saveComment(HttpServletRequest request,@RequestParam("type")String type,
			@RequestParam("manhuaCode")Integer manhuaCode,@RequestParam("context")String context ,
			Integer parentCode){
		ResultBean resultBean = new ResultBean<>(false, "fail");
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if (userInfo == null) {
			resultBean.setMessage("您还未登录，请点击登录按钮进行登录");
			return resultBean;
		}
		Integer userId = userInfo.getUserId();
		String account = userInfo.getAccount();
		 if(type.equals("1")){     //众说纷纭
			 
			 if(parentCode==null){//盖楼 我来评论
				 
				 detailsService.saveParentComment(type,manhuaCode,context,userId,account);
			 }else{//我也说一句
				 detailsService.saveChildComment(type,manhuaCode,context,userId,account,parentCode);
			 }
		 }else if(type.equals("2")){//作者有话说
			 if(parentCode==null){//盖楼 我来评论
				 WanwdManhua b= detailsService.selectManhuaByCode(manhuaCode);
				 if(!b.getAuthorId().equals(userId)){
					 resultBean.setMessage("您好，您并不是该篇文章的作者哦~不能在此板块发言，但是可以回复作者哦");
						return resultBean;
				 }else{
					 detailsService.saveParentComment(type,manhuaCode,context,userId,account);
				 }
			 }else{//我也说一句
				 
					 detailsService.saveChildComment(type,manhuaCode,context,userId,account,parentCode);
				
			 }
		 }else{
			 resultBean.setMessage("type值为:1(众说纷纭),2(作者有话说)");
			return resultBean;
		 }
		 
		resultBean.setSuccess(true);
		resultBean.setMessage("添加评论列表成功");
		return resultBean;
		
	}
	
	/**
	 * 获取章节列表
	 * @param request
	 * @param manhuaCode
	 * @return
	 */
	@RequestMapping("/bookList")
	@ResponseBody
	public ResultBean  bookList(HttpServletRequest request,@RequestParam("manhuaCode")Integer manhuaCode){
		ResultBean resultBean = new ResultBean<>(false, "fail");
		List<WanwdManhuaBook> list=null;
		int i;
		try {
			list = detailsService.getBookList(manhuaCode);
			i = detailsService.getBookListCount(manhuaCode);
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setSuccess(false);
			resultBean.setMessage("内部错误");
			return resultBean;
		}
		if(list==null||list.size()==0){
			resultBean.setSuccess(false);
			resultBean.setMessage("章节列表为空");
			return resultBean;
		}
		 
		Map<Integer, Object> map =new HashMap<Integer, Object>();
		for (int j = 1; j <= i; j++) {
			int start = (j - 1) * 12 + 1;
	        int end = j * 12;
	        
	        map.put(j,MessageFormat.format("第{0}章-第{1}章", start,end));
		}
		Map<String, Object> resultMap =new HashMap<String, Object>();
		resultMap.put("list", list);
		resultMap.put("group", map);
		
		resultBean.setSuccess(true);
		resultBean.setMessage("返回章节列表成功");
		resultBean.setData(resultMap);
		return resultBean;
	}
	
	
	@RequestMapping("/bookGroupList")
	@ResponseBody
	public ResultBean  bookGroupList(HttpServletRequest request,@RequestParam("manhuaCode")Integer manhuaCode,
			@RequestParam("groupKey")Integer groupKey){
		ResultBean resultBean = new ResultBean<>(false, "fail");
		List<WanwdManhuaBook> list=null;
		try {
			list = detailsService.getbookGroupList(manhuaCode,groupKey);
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setSuccess(false);
			resultBean.setMessage("内部错误");
			return resultBean;
		}
		if(list==null||list.size()==0){
			resultBean.setSuccess(false);
			resultBean.setMessage("章节列表为空");
			return resultBean;
		}
		
		resultBean.setSuccess(true);
		resultBean.setMessage("返回章节列表成功");
		resultBean.setData(list);
		return resultBean;
	}
	
	
	
	
	
}
