package cn.gyyx.playwd.oa.core;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.oa.stage.model.OAUserInfoModel;
import cn.gyyx.playwd.beans.common.PageBean;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean;
import cn.gyyx.playwd.bll.ArticleBll;
import cn.gyyx.playwd.bll.CategoryBll;
import cn.gyyx.playwd.oa.common.web.BaseController;
import cn.gyyx.playwd.oa.viewmodel.ArticleSaveModule;
import cn.gyyx.playwd.oa.viewmodel.ArticleViewModel;
import cn.gyyx.playwd.service.ArticleService;

/**
 * @Description: 图文后台管理
 * @author 成龙
 * @date 2017年2月28日 下午17:00:00
 */
@Controller
@RequestMapping(value = "/article")
public class ArticleController extends BaseController {
	
	private Logger logger = GYYXLoggerFactory.getLogger(getClass());

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private CategoryBll categoryBll;
	
	@Autowired
	private ArticleBll articleBll;

	/**
	 * 文字批量展示或隐藏 show：批量展示 hide：批量隐藏
	 */
	@RequestMapping(value = "/review/batchshow", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> batchShow(String type, @RequestParam("ids[]")int[] ids, Model model,
			HttpServletRequest request) {
		OAUserInfoModel userInfoModel = null;
		try {
			userInfoModel = super.getOAUserInfoModel(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "请您先登录", "");
			}
			if (type == null || (!type.equals("show")) && !type.equals("hide")) {
				return new ResultBean<>(false, "参数[type]错误", "");
			}
			if (ids == null || ids.length == 0) {
				return new ResultBean<>(false, "请至少选择一条要操作的记录", "");
			}
			return articleService.batchShowOrHide(type, ids,userInfoModel);
		} catch (Exception e) {
			logger.error("设置文章批量展示或隐藏接口异常", e);
			return new ResultBean<>(false, "设置文章批量展示或隐藏接口异常", "");
		}
	}
	
	/**
	 * 审核 pass：通过 fail：不通过
	 */
	@RequestMapping(value = "/review", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<String> review(String type, int id, Model model,
			HttpServletRequest request) {
		OAUserInfoModel userInfoModel = null;
		try {
			userInfoModel = super.getOAUserInfoModel(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false, "请您先登录", "");
			}
			if (type == null || (!type.equals("pass") && !type.equals("fail"))) {
				return new ResultBean<>(false, "参数[type]错误", "");
			}
			return articleService.reviewArticle(type, id, userInfoModel);
		} catch (Exception e) {
			logger.error("审核文章接口异常", e);
			return new ResultBean<>(false, "审核文章接口异常", "");
		}
	}

	/**
	 * 添加/编辑 文章 lihu
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	@ResponseBody
	public ResultBean<Object> saveArticle(@Valid ArticleSaveModule articleSaveModule,
			BindingResult bindingResult, HttpServletRequest request) {
		try {
			OAUserInfoModel userInfoModel = super.getOAUserInfoModel(request);
			if (userInfoModel == null) {
				return new ResultBean<>(false,"请您先登录", "");
			}
			if (bindingResult.hasErrors()) {
				String messageString = validationData(bindingResult);
				String[] messageStrings = messageString.split("\\|");
				return new ResultBean<>(false, messageStrings[1], null);
			}
			ModelMapper modelMapper = new ModelMapper();
			ArticleBean articleBean = modelMapper.map(articleSaveModule, ArticleBean.class);
			return articleService.saveArticle(articleBean,userInfoModel.getAccount(),userInfoModel.getCode());
		} catch (Exception e) {
			logger.error("文章编辑接口异常", e);
			return new ResultBean<>(false,"文章编辑接口异常", "");
		}
	}

	/**
	 * 获取文章信息
	 * @param code
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.GET })
	@ResponseBody
	public ResultBean<Object> getArticle(Integer code, HttpServletRequest request) {
		try {
			OAUserInfoModel userInfoModel = super.getOAUserInfoModel(request);
			if (userInfoModel == null) {
				return new ResultBean<Object>(false,"请您先登录", "");
			}
			if (code == null) {
				return new ResultBean<Object>(false, "参数[code]错误", "");
			}
			ArticleBean articleBean = articleBll.getArticle(code);
			return new ResultBean<Object>(true,"返回图文成功", articleBean);
		} catch (Exception e) {
			logger.error("图文预览接口异常", e);
			return new ResultBean<Object>(false,"图文预览接口异常", "");
		}
	}

	/**
	 * 查询图文列表 yangteng
	 * @param viewModel
	 * @param bindingResult
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	@ResponseBody
	public PageBean<Map<String, String>> getList(@Valid ArticleViewModel viewModel,BindingResult bindingResult,
			HttpServletRequest request) {
		
		if(bindingResult.hasErrors()){
			String messageString=validationData(bindingResult);
			String [] messageStrings=messageString.split("\\|");
			return PageBean.createPage(messageStrings[0], 0, 1, 10, null, messageStrings[1]);
		}

		return articleService.getList(
				viewModel.getType(), viewModel.getKey(),
				viewModel.getDisplayStatus(), viewModel.getFirstCategoryId(),
				viewModel.getSecondCategoryId(), viewModel.getAuthorType(),
				viewModel.getStartTime(), viewModel.getEndTime(),
				viewModel.getStatus(), viewModel.getPageIndex(),
				viewModel.getPageSize());			
	}

	/**
	 * 
	  * <p>
	  *    对文章标题加特殊说明
	  * </p>
	  *
	  * @action
	  *    lihu 2017年3月21日 下午5:04:47 描述
	  *
	  * @param code
	  * @param special
	  * @param request
	  * @return ResultBean<String>
	 */
	@RequestMapping(value = "/editSpecial", method = { RequestMethod.POST,RequestMethod.GET })
        @ResponseBody
        public ResultBean<String> editSpecial( @RequestParam("code")Integer code, @RequestParam("special")String special,
                        HttpServletRequest request) {
        try {
    	    OAUserInfoModel userInfoModel = super.getOAUserInfoModel(request);
            if (userInfoModel == null) {
                    return new ResultBean<>(false, "请您先登录", "");
            }
            logger.info("对文章标题加特殊说明 code:{},special{}",code,special);
            boolean isSuccess= articleBll.editSpecial(  code,   special);
            if(isSuccess){
                return new ResultBean<>(true, "修改文章特殊说明成功");
            }
            return new ResultBean<>(false, "修改文章特殊说明失败");
        } catch (Exception e) {
            logger.error("对文章标题加特殊说明接口异常", e);
            return new ResultBean<>(false, "对文章标题加特殊说明接口异常", "");
        }
    }
	
	
	/**
	 * 获取错误信息
	 * @param vresult
	 * @return
	 */
	private String validationData(BindingResult bindingResult) {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		FieldError fieldError = fieldErrors.get(0);
		return fieldError.getDefaultMessage();
	}	
	
	/**
	 * 图文浏览
	 */
	@RequestMapping(value = "/view/{code}", method = { RequestMethod.GET })
	public String view(@PathVariable("code") int code, Model model,HttpServletRequest request,HttpServletResponse response) {
		try {
			ResultBean<ArticleBean> resultBean = articleService.preview(code);
			if(resultBean.getStatus().equals("incorrect-notExist")){
				return "error/404";
			}
			
			model.addAttribute("item", resultBean.getData());
			return "articleView";
		} catch (UnsupportedEncodingException e) {
			logger.error("图文浏览异常", e);
			return "error/404";
		}		
	}
}
