package cn.gyyx.playwd.ui.core;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.core.auth.SignedUser;
import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.core.captcha.Captcha;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.NovelBean;
import cn.gyyx.playwd.beans.playwd.NovelChapterBean;
import cn.gyyx.playwd.bll.NovelBll;
import cn.gyyx.playwd.service.NovelService;
import cn.gyyx.playwd.ui.viewmodule.NovelChapterUploadModel;
import cn.gyyx.playwd.utils.StringTools;

/**
 * @Description: 小说前台
 * @author 成龙
 * @date 2017年2月28日 下午17:00:00
 */
@Controller
@RequestMapping(value = "/novel")
public class NovelController {
    private Logger logger = GYYXLoggerFactory.getLogger(getClass());

    @Autowired
    private NovelBll novelBll;
    @Autowired
    private NovelService novelService;
    
    /**
     * 上传章节页
     */
    @GetMapping("/upload")
    public String upload(Model model, HttpServletRequest request) {
            return "novel/upload";
    }

    /**
     * 检查小说名称是否重复
     */
    @RequestMapping(value = "/checkName", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<Object> checkName(String name,
        HttpServletRequest request,HttpServletResponse response) {
        try {
            UserInfo user = SignedUser.getUserInfo(request);
            if (user == null) {
                return new ResultBean<>(false, "您还未登陆", "nologin");
            }
            name = name == null ? "" : name.trim();//去空
            if (StringTools.isBlank(name)) {
                return new ResultBean<>(false, "名称不能为空", "invalid-name");
            }
            return novelService.checkName(user.getUserId(),name);
        } catch (Exception e) {
            logger.error("检查小说名称是否重复接口异常", e);
            return new ResultBean<>(false, "系统繁忙,请稍后重试", "");
        }
    }

    /**
     * 获取当前用户小说列表
     */
    @RequestMapping(value = "/mylist", method = { RequestMethod.GET })
    @ResponseBody
    public ResultBean<Object> getMyNovelList(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserInfo user = SignedUser.getUserInfo(request);
            if (user == null) {
                return new ResultBean<>(false, "您还未登陆", "nologin");
            }
            return new ResultBean<>(true, "", novelBll.selectNovelList(user.getUserId(),NovelBean.Status.unfinished.Value()));
        } catch (Exception e) {
            logger.error("获取当前用户小说列表接口异常", e);
            return new ResultBean<>(false, "系统繁忙,请稍后重试", "");
        }
    }
    
    /**
     * 检查小说章节名称是否重复
     */
    @RequestMapping(value = "/checkChapterTitle", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<String> checkChapterTitle(String name,String title,
        HttpServletRequest request, HttpServletResponse response) {
        try {
            UserInfo user = SignedUser.getUserInfo(request);
            if (user == null) {
                return new ResultBean<>(false, "您还未登陆", "nologin");
            }
            name = name == null ? "" : name.trim();
            title = title == null ? "" : title.trim();
            if(StringTools.isBlank(name) || StringTools.isBlank(title)){
                return new ResultBean<>(false, "参数错误", "");
            }
            return novelService.checkChapterTitle(user.getUserId(),name,title);
        } catch (Exception e) {
            logger.error("检查小说章节名称是否重复接口异常", e);
            return new ResultBean<>(false, "系统繁忙,请稍后重试", "");
        }
    }
    
    /**
     * 上传小说章节
     */
    @RequestMapping(value = "/chapter/upload", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<Object> chapterUpload(@Valid NovelChapterUploadModel uploadMoel,BindingResult bindingResult,
        HttpServletRequest request, HttpServletResponse response) {
        try {
            if (bindingResult.hasErrors()) {
                String messageString = validationData(bindingResult);
                String[] messageStrings = messageString.split("\\|");
                return new ResultBean<>(false, messageStrings[1], null);
            }
            if (!new Captcha(request, response).equals(uploadMoel.getCaptcha())) {
                return new ResultBean<>(false, "验证码填写错误", "");
            }
            UserInfo user = SignedUser.getUserInfo(request);
            if (user == null) {
                return new ResultBean<>(false, "您还未登陆", "nologin");
            }
            //model 转 bean
            NovelBean novel = new NovelBean();
            NovelChapterBean chapter = new NovelChapterBean();
            novel.setUserId(user.getUserId());
            novel.setAccount(user.getAccount());
            this.modelToBean(uploadMoel,chapter,novel);
            return novelService.chapterUpload(chapter,novel);
        } catch (Exception e) {
            logger.error("检查小说章节名称是否重复接口异常", e);
            return new ResultBean<>(false, "系统繁忙,请稍后重试", "");
        }
    }
    
    private void modelToBean(NovelChapterUploadModel uploadMoel,
            NovelChapterBean chapter, NovelBean novel) {
        novel.setName(uploadMoel.getName().trim());
        novel.setCategoryId(Integer.parseInt(uploadMoel.getCategoryId()));
        novel.setCover(uploadMoel.getCover().trim());
        novel.setDescription(uploadMoel.getDescription().trim());
        novel.setStatus(uploadMoel.getStatus());
        chapter.setTitle(uploadMoel.getTitle().trim());
        chapter.setContent(uploadMoel.getContent().trim());
    }

    /**
     * 获取错误信息
     * 
     * @param vresult
     * @return
     */
    private String validationData(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for(FieldError error : fieldErrors){
                System.out.println(error.getDefaultMessage());
        }
        FieldError fieldError = fieldErrors.get(0);
        return fieldError.getDefaultMessage();
    }
	
}
