package cn.gyyx.playwd.oa.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.oa.stage.model.OAUserInfoModel;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.CategoryBean;
import cn.gyyx.playwd.bll.CategoryBll;
import cn.gyyx.playwd.oa.common.web.BaseController;
import cn.gyyx.playwd.utils.StringTools;

/**
 * @Description: 分类管理
 * @author 成龙
 * @date 2017年2月28日 下午17:00:00
 */
@Controller
@RequestMapping(value = "/category")
public class CategoryController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CategoryBll categoryBll;
	
	/**
	 * 
	  * <p>
	  *    获取图文分类
	  * </p>
	  *
	  * @action
	  *    lihu 2017年3月6日 下午1:26:08 描述
	  *
	  * @param model
	  * @param type  为空取全部分类,parent 取全部一级分类, 数值 取二级分类
	  * @param request
	  * @return ResultBean<Object>
	 */
	@RequestMapping(value = "/getCategory")
	@ResponseBody
	public ResultBean<Object> getCategory(Model model,String categoryType,String contentType,
			HttpServletRequest request) {
	        contentType = StringTools.isBlank(contentType) ? "article" : contentType;
		List<CategoryBean> list=null;
		try {
		        if(categoryType==null){
		            list = categoryBll.getCategory(contentType);
		            return new ResultBean<Object>(true, "返回全部分类成功", list);
		        }else{
		            if(categoryType.equals("parent")){
		                list= categoryBll.getParentCategory(contentType);
		                return new ResultBean<Object>(true, "返回一级分类成功", list);
		            }else {
		                int categoryInt = Integer.parseInt(categoryType);
		                list=categoryBll.getChildCategory(categoryInt,contentType);
		                return new ResultBean<Object>(true, "返回二级分类成功", list);
		            }
		        }
		} catch (Exception e) {
			logger.error("获取分类信息接口异常", e);
			 
			return new ResultBean<Object>(false, "获取分类信息接口异常", null);
		}
	}
	
	/**
	 * 获取一级分类
	 */
	@RequestMapping(value = "/getfirstcategorylist", method = { RequestMethod.GET })
	@ResponseBody
	public ResultBean<List<Object>> getFirstCategoryList(String type, Model model,
			HttpServletRequest request) {
		try {
			if(type==null){
	            return new ResultBean<>(false, "内容名称不允许为空", null);
	        }
			logger.info("获取一级分类列表,分类类型:{}",type);
			List<CategoryBean> list = categoryBll.getFirstCategoryListByContentType(type);
			List<Object> resultList=new ArrayList<>();						
			for (CategoryBean c : list) {
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("code", c.getCode());
				map.put("title", c.getTitle());
				resultList.add(map);
			}	
			return new ResultBean<>(true, "获取成功", resultList);
		} catch (Exception e) {
			logger.error("获取一级分类接口异常", e);
			return new ResultBean<>(false, "获取一级分类接口异常", null);
		}
	}
	
	/**
	 * 获取子分类列表
	 */
	@RequestMapping(value = "/getsubcategorylist", method = { RequestMethod.GET })
	@ResponseBody
	public ResultBean<List<Object>> getSubCategoryList(int pid, Model model,
			HttpServletRequest request) {
		try {
			logger.info("获取子分类列表,父类型:{}",pid);
			List<CategoryBean> list = categoryBll.getSubCategoryList(pid);
			List<Object> resultList=new ArrayList<>();						
			for (CategoryBean c : list) {
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("code", c.getCode());
				map.put("title", c.getTitle());
				resultList.add(map);
			}	
			return new ResultBean<>(true, "获取成功", resultList);
		} catch (Exception e) {
			logger.error("获取子分类列表接口异常", e);
			return new ResultBean<>(false, "获取子分类列表接口异常", null);
		}
	}
	
	/**
	 * 分类页
	 */
	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String index(Model model,
			HttpServletRequest request,HttpServletResponse response) {
		return "categoryIndex";
	}
	
	/**
	 * 编辑分类
	 */
	@RequestMapping(value = "/edit", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> edit(CategoryBean bean, Model model,
			HttpServletRequest request) {
		try {
			logger.info("编辑分类,类型ID:{}",bean.getCode());
			OAUserInfoModel userInfoModel = super.getOAUserInfoModel(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "请先登录", "");
			}
			if (bean.getCode() == null) {
				return new ResultBean<>(false, "分类ID不能为空", "");
			}
			if (StringUtils.isEmpty(bean.getTitle())) {
				return new ResultBean<>(false, "分类名称不能为空", "");
			}
			CategoryBean item = categoryBll.getCategroyByCode(bean.getCode());
			if(item == null){
				return new ResultBean<>(false, "分类不存在", "");
			}
			item.setTitle(bean.getTitle());
			categoryBll.update(item);
			return new ResultBean<>(true, "操作成功", "");
		} catch (Exception e) {
			logger.error("编辑分类接口异常", e);
			return new ResultBean<>(false, "编辑分类接口异常", null);
		}
	}
	
	/**
	 * 添加分类
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> save(CategoryBean bean, Model model,
			HttpServletRequest request) {
		try {
			logger.info("添加分类,类型title:{}",bean.getTitle());
			OAUserInfoModel userInfoModel = super.getOAUserInfoModel(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "请先登录", "");
			}
			if (StringUtils.isEmpty(bean.getTitle())) {
				return new ResultBean<>(false, "分类名称不能为空", "");
			}
			if (bean.getParentId() == null || bean.getParentId() == 0) {
				if (StringUtils.isEmpty(bean.getContentType())) {
					return new ResultBean<>(false, "内容类型不能为空", "");
				}
				//添加一级分类
				categoryBll.insert(bean.getTitle(),bean.getContentType(),0);
			}else{
				//查看一级分类
				CategoryBean parentCategory = categoryBll.getCategroyByCode(bean.getParentId());
				if(parentCategory == null){
					return new ResultBean<>(false, "父分类不存在", "");
				}
				//添加二级分类
				categoryBll.insert(bean.getTitle(),parentCategory.getContentType(),bean.getParentId());
			}
			
			return new ResultBean<>(true, "操作成功", "");
		} catch (Exception e) {
			logger.error("添加分类接口异常", e);
			return new ResultBean<>(false, "添加分类接口异常", null);
		}
	}

	/**
	 * 删除分类
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> delete(int code, Model model,
			HttpServletRequest request) {
		try {
			logger.info("删除分类,类型ID:{}",code);
			OAUserInfoModel userInfoModel = super.getOAUserInfoModel(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "请先登录", "");
			}
			CategoryBean item = categoryBll.getCategroyByCode(code);
			if(item == null){
				return new ResultBean<>(false, "分类不存在", "");
			}
			categoryBll.delete(code);
			return new ResultBean<>(true, "操作成功", "");
		} catch (Exception e) {
			logger.error("删除分类接口异常", e);
			return new ResultBean<>(false, "删除分类接口异常", null);
		}
	}

}
