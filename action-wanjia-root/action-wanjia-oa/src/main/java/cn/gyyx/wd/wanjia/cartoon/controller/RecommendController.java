package cn.gyyx.wd.wanjia.cartoon.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.wd.wanjia.ResultBean;
import cn.gyyx.wd.wanjia.cartoon.beans.RecommendBean;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdRecommend;
import cn.gyyx.wd.wanjia.cartoon.beans.tools.EnumUtil;
import cn.gyyx.wd.wanjia.cartoon.beans.tools.FileTools;
import cn.gyyx.wd.wanjia.cartoon.beans.tools.ReCommendEnum;
import cn.gyyx.wd.wanjia.cartoon.service.RecommendService;
/**
 * 推荐controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("recommend")
public class RecommendController {
	@Autowired
	private RecommendService recommendService;
	
	@RequestMapping("/")
	@ResponseBody
	public Object recommend(HttpServletRequest request){
		return "recommend";
	}
	/**
	 *  推荐位点击
	 * @param request
	 * @param list
	 * @param manhuaCode
	 * @param rewarLevel
	 * @return
	 */
	@RequestMapping("/saveFields")
	@ResponseBody
	public ResultBean<Object> saveFields(HttpServletRequest request,
			@RequestParam(value = "locationId[]") List<Integer> list,
			@RequestParam Integer manhuaCode,Integer rewarLevel ){
		ResultBean<Object> resultBean = new ResultBean<Object>(false,"fail");
		rewarLevel=rewarLevel==null?0:rewarLevel;
		if(list==null||list.size()==0){
			resultBean.setMessage("推荐位不可为空");
			return resultBean;
		}
		if(manhuaCode==null){
			resultBean.setMessage("漫画code不可为空");
			return resultBean;
		}
		 resultBean = recommendService.saveFields(list,manhuaCode,rewarLevel);
		
		return resultBean;
	}
	
	/**
	 * 推荐位列表 是否已推荐
	 * @param request
	 * @param manhuaCode
	 * @return
	 */
	@RequestMapping("/fieldsList")
	@ResponseBody
	public ResultBean<Object> fieldsList(HttpServletRequest request,
			 Integer manhuaCode){
		ResultBean<Object> resultBean = new ResultBean<Object>(false,"fail");
		List<RecommendBean>  list = new ArrayList<RecommendBean>();
		Map<Integer, Object> map = new HashMap<>();
		List<ReCommendEnum> fieldsList = EnumUtil.toList(ReCommendEnum.class);
		List<WanwdRecommend> recommendList= recommendService.getManhuaFields(manhuaCode==null?0:manhuaCode);
		
		for (ReCommendEnum reCommendEnum : fieldsList) {
			RecommendBean bean = new RecommendBean();
			for (WanwdRecommend recommend : recommendList) {
				if (recommend.getLocationId()==reCommendEnum.getIndex()) {
					bean.setBool(true);
				} 
			}
			bean.setIndex(reCommendEnum.getIndex());
			bean.setName(reCommendEnum.getName());
			list.add(bean);
		}
		resultBean.setSuccess(true);
		resultBean.setData(list);
		return resultBean;
	}
	
	 /**
	  * 展示管理列表
	  * @param request
	  * @param index
	  * @return
	  */
	@RequestMapping("/manageFieldList")
	@ResponseBody
	public ResultBean<Object> manageFieldList(HttpServletRequest request,
			@RequestParam Integer index,@RequestParam Integer leavel){
		ResultBean<Object> resultBean = new ResultBean<Object>(false,"fail");
		List<WanwdRecommend> recommendList = null;
		try {
			 recommendList= recommendService.getFieldsListByIndex(index,leavel);
			if(recommendList==null){
				resultBean.setProperties(false, "返回列表为空", null);
				return resultBean;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setProperties(false, "内部错误", null);
			return resultBean;
		}
		
		 
		resultBean.setProperties(true, "返回列表", recommendList);
		return resultBean;
	}
	/**
	 * 修改展示状态
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping("/fieldsDisplay")
	@ResponseBody
	public ResultBean<Object> fieldsDisplay(HttpServletRequest request,
			@RequestParam Integer code){
		ResultBean<Object> resultBean = new ResultBean<Object>(false,"fail");
		
		try {
			resultBean=recommendService.fieldsDisplay(code);
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setMessage("内部错误");
			return resultBean;
		}
			 
		
		return resultBean;
	}
	
	
	
	/**
	 * 移动栏位
	 * @param request
	 * @param code
	 * @param type: UP DOWN TOP BOTTOM
	 * @return
	 */
	@RequestMapping("/fieldsMove")
	@ResponseBody
	public ResultBean<Object> fieldsMove(HttpServletRequest request,
			@RequestParam Integer code,@RequestParam String type){
		ResultBean<Object> resultBean = new ResultBean<Object>(false,"fail");
		
		try {
			resultBean=recommendService.fieldsMove(code,type);
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setMessage("内部错误");
			return resultBean;
		}
		
		
		return resultBean;
	}
	/**
	 * 历史推荐列表
	 * @param request
	 * @param location
	 * @param isClosed
	 * @param leavel
	 * @param account
	 * @param title
	 * @return
	 */
	@RequestMapping("/recommendHistory")
	@ResponseBody
	public ResultBean<Object> recommendHistory(HttpServletRequest request,Integer pageSize,
			  Integer location,Integer isClosed,Integer leavel,Integer pageIndex,String account,String title){
		ResultBean<Object> resultBean = new ResultBean<Object>(false,"fail");
		  List<Map<String, Object>> list = new ArrayList<>();
		  int Count =0;
		  Map<String, Object> map = new HashMap<>();
		try {
			 
			list = recommendService.recommendHistory(pageIndex,pageSize,location,isClosed,leavel,account,title);
			Count =recommendService.recommendHistoryCount(location,isClosed,leavel,account,title);
			if(list==null||list.size()==0){
				resultBean.setMessage("返回列表为空");
				resultBean.setSuccess(false);
				return resultBean;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setMessage("内部错误");
			return resultBean;
		}
		map.put("list", list);
		map.put("Count", Count);
		resultBean.setData(map);
		resultBean.setMessage("返回列表成功");
		resultBean.setSuccess(true);
		return resultBean;
	}
	
	/**
	 * 编辑推荐位
	 * 
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping("/editFields")
	@ResponseBody
	public ResultBean<Object> editFields(HttpServletRequest request,
			@RequestParam(value = "locationId[]") List<Integer> list,
			@RequestParam Integer manhuaCode,Integer rewarLevel ){
		ResultBean<Object> resultBean = new ResultBean<Object>(false,"fail");
		
		try {
			resultBean=recommendService.editFields(list,manhuaCode,rewarLevel);
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setMessage("内部错误");
			return resultBean;
		}
		
		
		return resultBean;
	}
	/**
	 * 修改备注
	 * @param request
	 * @param code
	 * @param remark
	 * @return
	 */
	@RequestMapping("/editRemark")
	@ResponseBody
	public ResultBean<Object> editRemark(HttpServletRequest request,
			@RequestParam Integer code,@RequestParam String remark ){
		ResultBean<Object> resultBean = new ResultBean<Object>(false,"fail");
		
		try {
			recommendService.editRemark(code,remark);
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setMessage("内部错误");
			return resultBean;
		}
		
		resultBean.setProperties(true, "修改成功", null);
		return resultBean;
	}
	/**
	 * 下载xsl
	 * @param request
	 * @param response
	 * @param isClosed
	 * @param leavel
	 * @param account
	 * @param title
	 * @return
	 */
	@RequestMapping("/exportPerformanceResultExcel")
	@ResponseBody
	public ResultBean<Object> editRemark1(HttpServletRequest request,HttpServletResponse response
			,Integer isClosed,Integer leavel,String account,String title){
		ResultBean<Object> resultBean = new ResultBean<Object>(false,"fail");
		Workbook wk;
		try {
			wk = recommendService.exportPerformanceResultExcel(request,  isClosed,  leavel,  account,  title);
			if(wk != null){
				FileTools.downFile(request, response, "推荐记录列表.xlsx" , wk);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setProperties(false, "内部错误", null);
			return resultBean;
		}
		/*try {
			recommendService.editRemark(code,remark);
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setMessage("内部错误");
			return resultBean;
		}*/
		
		resultBean.setProperties(true, "修改成功", null);
		return resultBean;
	}
	
}
