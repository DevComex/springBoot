package cn.gyyx.playwd.ui.core;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.CategoryBean;
import cn.gyyx.playwd.bll.CategoryBll;
import cn.gyyx.playwd.utils.StringTools;

/**
 * @Description: 分类管理
 * @author 成龙
 * @date 2017年2月28日 下午17:00:00
 */
@Controller
@RequestMapping(value = "/category")
public class CategoryController  {
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

}
