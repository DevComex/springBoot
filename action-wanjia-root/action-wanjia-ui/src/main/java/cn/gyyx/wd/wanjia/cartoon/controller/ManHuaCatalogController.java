package cn.gyyx.wd.wanjia.cartoon.controller;

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
import cn.gyyx.wd.wanjia.cartoon.service.CatalogService;
/**
 * 列表展示controller
 * @author lihu
 *
 */
@Controller
@RequestMapping("/catelog")
public class ManHuaCatalogController {
	@Autowired
	private CatalogService catalogService;
	/**
	 * 收藏列
	 * @param request
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/collectinfo")
	@ResponseBody
	public ResultBean  collectList(HttpServletRequest request,@RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize){
		ResultBean resultBean = new ResultBean<>(false, "fail");
		if(pageIndex==null||pageSize==null){
			resultBean.setMessage("参数错误");
			return resultBean;
		}
		List<Map<String, Object>> list =null;
		int total;
		try {
			UserInfo userInfo = SignedUser.getUserInfo(request);
			if (userInfo == null) {
				resultBean.setMessage("您还没有关注任何漫画哦，快来关注吧");
				return resultBean;
			}
			
			/*UserInfo userInfo = new UserInfo();
			userInfo.setUserId(1);*/
			//查询收藏数据
			list = catalogService.collectinfo(userInfo.getUserId(),pageIndex,pageSize);
			if(list==null||list.size()==0){
				resultBean.setMessage("您还没有关注任何漫画哦，快来关注吧");
				return resultBean;
			}
			//数据总数
			  total = catalogService.selectCountByUid(userInfo.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setMessage("内部运行错误");
			return resultBean;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("list", list);
		resultMap.put("total", total);
		
		
		resultBean.setSuccess(true);
		resultBean.setMessage("返回漫画收藏列表成功");
		resultBean.setData(resultMap);
		return resultBean;
		
	}
	
	/**
	 *  整体展示列表
	 * @param request
	 * @param pageIndex
	 * @param pageSize
	 * @param type   type=0 最新按时间排序  type=1或其他 最热 按阅度次数排序
	 * @param categoryCode  类型值  0为默认所有
	 * @return
	 */
	@RequestMapping("/bodylist")
	@ResponseBody
	public ResultBean  bodylist(HttpServletRequest request,@RequestParam("pageIndex") Integer pageIndex
			 ,@RequestParam("pageSize") Integer pageSize,@RequestParam("type") Integer type
			,@RequestParam("categoryCode") Integer categoryCode){
		ResultBean resultBean = new ResultBean<>(false, "fail");
		if(pageIndex==null||categoryCode==null||type==null){
			resultBean.setMessage("参数错误");
			return resultBean;
		}
		
		List<Map<String, Object>> list =null;
		List<Map<String, Object>> uclist =null;
		
		int total = 0;
		try {
			String order = request.getParameter("order");
			if(order!=null){
				list=catalogService.bodyRightList(order);
				
			}else{
				list=catalogService.bodylist(categoryCode,pageIndex,pageSize,type);
				total = catalogService.bodylistCount(categoryCode);
				UserInfo userInfo = SignedUser.getUserInfo(request);
				if(userInfo!=null){
					uclist=catalogService.userCatalogList(userInfo.getUserId());
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("uclist", uclist);
		resultMap.put("list", list);
		resultMap.put("total", total);
		
		
		resultBean.setSuccess(true);
		resultBean.setMessage("返回首页列表成功");
		resultBean.setData(resultMap);
		return resultBean;
	}
	
	/**
	 * 点击收藏
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping("/catalogbook")
	@ResponseBody
	public ResultBean  catalogbook(HttpServletRequest request,
			@RequestParam("code") Integer code){
		ResultBean resultBean = new ResultBean<>(false, "fail");
		UserInfo userInfo = SignedUser.getUserInfo(request);
		if(userInfo==null){
			resultBean.setMessage("您还未登录，请点击登录按钮进行登录");
			return resultBean;
		}
		resultBean=catalogService.catalogbook(code,userInfo.getUserId(),userInfo.getAccount());
		
		
		return resultBean;
	}
	
}
