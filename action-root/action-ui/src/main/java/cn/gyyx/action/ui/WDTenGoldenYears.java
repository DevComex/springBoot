package cn.gyyx.action.ui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.gyyx.action.beans.ResultBean;
import cn.gyyx.action.beans.weixin.related.SilentAuthorizeBean;
import cn.gyyx.action.service.weixin.WeChatSilentAuthorize;

@Controller
@RequestMapping("/GoldenYears")
public class WDTenGoldenYears {
	@RequestMapping("/exaPage")
	public String examplePage(String openid){
		return "wdgoldenyears/example";
	}
	/**
	 * 静默授权回调接口
	 * @param code
	 * @return
	 */
	@RequestMapping("/redirect")
	public String wechatRedirect(String code,RedirectAttributes para){
		ResultBean<SilentAuthorizeBean> result = new WeChatSilentAuthorize().getOpenidByCode(code, "Wd");
		para.addAttribute("token", result.getData().getToken());
		para.addAttribute("OpenId", result.getData().getOpenid());
		return "redirect:/GoldenYears/index";
	}
	/**
	 * index
	 * @return
	 */
	@RequestMapping("/index")
	public String index(String OpenId){
		return "wdgoldenyears/index";
	}
	/**
	 * 登录
	 * @return
	 */
	@RequestMapping("/loginPage")
	public String loginPage(){
		return "wdgoldenyears/login";
	}
	/**
	 * 规则
	 * @return
	 */
	@RequestMapping("/actionrule")
	public String actionrule(){
		return "wdgoldenyears/actionrule";
	}
	/**
	 * 制作页
	 * @return
	 */
	@RequestMapping("/making")
	public String making(){
		return "wdgoldenyears/making";
	}
	/**
	 * 我的作品
	 * @return
	 */
	@RequestMapping("/myWorks")
	public String myWorks(){
		return "wdgoldenyears/myworklist";
	}
	@RequestMapping("/search")
	@ResponseBody
	public ResultBean<String> search(String searchType,String para,String page){
		return new ResultBean<>(false,"活动已经结束",null);
	}
	/**
	 * 保存
	 * @param mediaId
	 * @param token
	 * @param openid
	 * @param workCode
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public ResultBean<String> addWork(@RequestParam("mediaId")List<String> mediaId,String token,String openid,String music){
		return new ResultBean<>(false,"活动已经结束",null);
	}
	/**
	 * 点赞
	 * @param openid
	 * @param workCode
	 * @param token
	 * @return
	 */
	@RequestMapping("/approval")
	@ResponseBody
	public ResultBean<String> approval(String openid,String workCode,String token){
		return  new ResultBean<>(false,"活动已经结束",null);
	}
	/**
	 * 显示我的作品
	 * @return
	 */
	@RequestMapping("/show")
	@ResponseBody
	public ResultBean<String> myWorks(String userOpenid){
	    return new ResultBean<String>(false,"活动已经结束",null);
	}
	/**
	 * 上传作品
	 * @param workCode
	 * @return
	 */
	@RequestMapping("/participate")
	@ResponseBody
	public ResultBean<String> participate(String workCode,String userOpenid,String token){
	      ResultBean<String> result = new ResultBean<>(false,"活动已经结束",null);
              return result;
	}
	/**
	 * 删除作品
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public ResultBean<Boolean> delete(String workCode){ 
              return new ResultBean<>(false,"活动已经结束",null);
	}
	/**
	 * 登录绑定
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public ResultBean<String> login (HttpServletRequest request,String openid){
	    return new ResultBean<>(false,"活动已经结束",null);
	}
	/**
	 * 示例
	 * @param workCode
	 * @return
	 */
	@RequestMapping("/example")
	public String example(String workCode,Model model){
		return "wdgoldenyears/example2";
	}
}
