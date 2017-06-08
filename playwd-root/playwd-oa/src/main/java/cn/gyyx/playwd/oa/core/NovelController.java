package cn.gyyx.playwd.oa.core;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deepoove.poi.XWPFTemplate;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.oa.stage.model.OAUserInfoModel;
import cn.gyyx.playwd.utils.FileUtil;
import cn.gyyx.playwd.beans.common.PageBean;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.CategoryBean;
import cn.gyyx.playwd.beans.playwd.MessageBean;
import cn.gyyx.playwd.beans.playwd.NovelBean;
import cn.gyyx.playwd.beans.playwd.NovelChapterBean;
import cn.gyyx.playwd.bll.MessageBll;
import cn.gyyx.playwd.bll.NovelBll;
import cn.gyyx.playwd.bll.NovelChapterBll;
import cn.gyyx.playwd.oa.common.web.BaseController;
import cn.gyyx.playwd.oa.viewmodel.NovelChapterUploadModel;
import cn.gyyx.playwd.service.NovelService;
import cn.gyyx.playwd.service.RecommendService;
import cn.gyyx.playwd.utils.StringTools;

/**
 * @Description: 小说后台
 * @author lihu
 * @date 2017年5月7日 下午17:00:00
 */
@Controller
@RequestMapping(value = "/novel")
public class NovelController  extends BaseController{
    private Logger logger = GYYXLoggerFactory.getLogger(getClass());

    @Autowired
    private NovelBll novelBll;
    @Autowired
    private NovelChapterBll novelChapterBll;  
    @Autowired
    private NovelService novelService;
    @Autowired
    private MessageBll messageBll;
    /**
     * 推荐管理
     */
    @Autowired
    public RecommendService recommendService;
    /**
     * 小说列表页
     */
    @GetMapping({"/manage","/"})
    public String manage(Model model, HttpServletRequest request) {
            return "novel/manage";
    }
    /**
     * 小说上传页
     */
    @GetMapping("/upload")
    public String upload(Model model, HttpServletRequest request) {
    	return "novel/upload";
    }
    /**
     * 小说编辑页
     */
    @GetMapping("/chapter/editor")
    public String editor(Model model, HttpServletRequest request) {
        return "novel/chapter/editor";
    }
    /**
     * 章节列表页
     */
    @GetMapping(value={"/chapter/management","/chapter/","/chapter"})
    public String chapterList(Model model, HttpServletRequest request) {
        return "novel/chapter/management";
    }
    /**
     * 获取官方 小说列表
     */
    @RequestMapping(value = "/officialNovel", method = { RequestMethod.GET })
    @ResponseBody
    public ResultBean<Object> getMyNovelList(HttpServletRequest request, HttpServletResponse response) {
        try {
             
            return new ResultBean<>(true, "", novelBll.selectNovelList(0,NovelBean.Status.unfinished.Value()));
        } catch (Exception e) {
            logger.error("获取当前用户小说列表接口异常", e);
            return new ResultBean<>(false, "系统繁忙,请稍后重试", "");
        }
    }
  /**
     * 检查小说名称是否重复
     */
    @RequestMapping(value = "/checkName", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<Object> checkName(String name,Integer userId,
        HttpServletRequest request,HttpServletResponse response) {
        try {
            
            name = name == null ? "" : name.trim();//去空
            if (StringTools.isBlank(name)) {
                return new ResultBean<>(false, "名称不能为空", "invalid-name");
            }
            if(userId!=null){
                return novelService.checkName(userId, name);
            }
            return novelService.checkName(name);
        } catch (Exception e) {
            logger.error("检查小说名称是否重复接口异常", e);
            return new ResultBean<>(false, "系统繁忙,请稍后重试", "");
        }
    }

   /**
     * 获取小说列表
     */
    @RequestMapping(value = "/list", method = { RequestMethod.GET })
    @ResponseBody
    public PageBean<Map<String, Object>> getMyNovelList(HttpServletRequest request,String name,String isFinished,Integer isShow,Integer categoryId
            ,String status,Integer pageIndex,Integer pageSize) {
        try {
            if(pageIndex==null||pageSize == null){
                return new PageBean<>(false, "pageIndex和pageSize 不可为空");
            }
            name = name == null ? "" : name.trim();//去空
            List<Map<String, Object>> list = novelBll.selectAllNovelList(name, isFinished, isShow, categoryId,status,pageIndex,pageSize);
            int count = novelBll.selectAllNovelListCount(name, isFinished, isShow, categoryId,status);
            
            return PageBean.createPage(true, count, pageIndex, pageSize, list, "获取小说列表成功");
        } catch (Exception e) {
            logger.error("获取小说列表接口异常", e);
            return new PageBean<>(false, "获取小说列表接口异常");
        }
    }
    
    /**
     * 检查小说章节名称是否重复
     */
    @RequestMapping(value = "/checkChapterTitle", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<String> checkChapterTitle(String name,String title,Integer userId,
        HttpServletRequest request, HttpServletResponse response) {
        try {
            
            name = name == null ? "" : name.trim();
            title = title == null ? "" : title.trim();
            if(StringTools.isBlank(name) || StringTools.isBlank(title)){
                return new ResultBean<>(false, "参数错误", "");
            }
            if(userId==null){
                return novelService.checkChapterTitle(name,title);
            }
            return novelService.checkChapterTitle(userId, name, title);
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
            
            //model 转 bean
            NovelBean novel = new NovelBean();
            NovelChapterBean chapter = new NovelChapterBean();
            Date date = new Date();
            novel.setUserId(0);
            novel.setLatestPublishTime(date);
            novel.setAccount("official");
            chapter.setPublishTime(date);//审核时间
            this.modelToBean(uploadMoel,chapter,novel);
            if(uploadMoel.getNovelChapterCode() == null){ // 上传
                return novelService.chapterUpload(chapter,novel);
            }else{ /// 编辑
                return novelService.chapterEditor(chapter,novel);
            }
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
        if(uploadMoel.getNovelChapterCode()!=null){
            chapter.setCode(uploadMoel.getNovelChapterCode());
        }
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
    /**
     * 获取小说目录
     */
    @RequestMapping(value = "/novelCatalogue", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> novelCatalogue(Integer novelCode,
        HttpServletRequest request, HttpServletResponse response) {
        try {
            if(novelCode == null){
                return new ResultBean<>(false, "参数错误", null);
            }
            List<Map<String,Object>> list = novelChapterBll.novelCatalogue(novelCode);
            return new ResultBean<>(true, "获取小说目录成功", list);
        } catch (Exception e) {
            logger.error("获取小说目录接口异常", e);
            return new ResultBean<>(false, "系统繁忙,请稍后重试", null);
        }
    } 
    /**
     * 
      * <p>
      *    展示或隐藏 修改小说状态
      * </p>
      *
      * @action
      *    lihu 2017年5月11日 上午10:54:30 描述
      *
      * @param novelCode
      * @param request
      * @param response
      * @return ResultBean<List<Map<String,Object>>>
     */
    @RequestMapping(value = "/showAndhidden", method = { RequestMethod.POST })
    @ResponseBody
    public ResultBean<String> showAndhidden(Integer novelCode,
        HttpServletRequest request, HttpServletResponse response) {
        try {
            if(novelCode == null){
                return new ResultBean<>(false, "参数错误", null);
            }
            return novelService.updateShowAndhidden(novelCode);
        } catch (Exception e) {
            logger.error("修改小说状态接口异常", e);
            return new ResultBean<>(false, "系统繁忙,请稍后重试", "error");
        }
    } 
    /**
     * 下载
     */
    @RequestMapping(value = "/download", method = { RequestMethod.GET })
    @ResponseBody
    public void dowload(Integer novelCode,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            if(novelCode == null){
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write("参数错误");
            }
            //生成file
            String rootPath = request.getSession().getServletContext().getRealPath("/");
            String savePath = rootPath + "static/doc/";
            XWPFTemplate doc = XWPFTemplate.create(rootPath+"static/templete.docx");
            logger.info("rootPath :"+rootPath);
            //数据转为word文档,并打成zip包  返回zip路径
             String dowloadPath = novelService.download(novelCode,savePath,doc);
             if(dowloadPath!=null){
                 FileUtil.downloadFile(new File(dowloadPath), response, true);
             }else{
                 response.setContentType("text/html;charset=utf-8");
                 response.getWriter().write("该小说暂无审核通过的章节，不可下载");
             }
        } catch (Exception e) {
            logger.error("获取小说目录接口异常", e);
        }
    } 
    /**
     * 小说浏览页
     * @param chapterCode
     * @param model
     * @param request
     * @return
     */
    @GetMapping(value="/chapter/view/{chapterCode}")
    public String chapterList(@PathVariable("chapterCode") Integer chapterCode,
            Model model, HttpServletRequest request) {
        try {
            if (chapterCode == null) {
                return "error/404";
            }

            NovelChapterBean chapterBean = novelChapterBll.get(chapterCode);
            if (chapterBean == null) {
                return "error/404";
            }
            // 获取小编回复消息
            MessageBean messageBean = messageBll.getMessage(
                CategoryBean.CategoryType.novel.Value(), chapterCode,
                MessageBean.MessageType.editor.Value());
            String message = "";
            if(messageBean!=null){
                message = messageBean.getMessage();
            }
            model.addAttribute("message", message);
            model.addAttribute("chapter", chapterBean);
            return "novel/chapter/chapterView";
        } catch (Exception e) {
            logger.error("小说浏览异常", e);
            return "error/404";
        }
    }

    /**
     * 
      * <p>
      *   获取小说章节列表
      * </p>
      *
      * @action
      *    lihu 2017年5月12日 下午1:48:39 描述
      *
      * @param novelCode
      * @param request
      * @param response
      * @return ResultBean<List<Map<String,Object>>>
     */
    @RequestMapping(value = "/chapter/list", method = { RequestMethod.GET })
    @ResponseBody
    public PageBean<Map<String, Object>> novelChapterList(Integer novelCode,String status,
            Integer startChapterNum,Integer endChapterNum,String startTime,String endTime,Integer pageIndex,Integer pageSize,
        HttpServletRequest request, HttpServletResponse response) {
        try {
            if(novelCode == null||pageIndex==null||pageSize == null){
                return new PageBean<>(false, "参数错误");
            }
            return novelService.getChapterPage(novelCode,startChapterNum,endChapterNum, startTime, endTime,status,pageIndex,pageSize);
        } catch (Exception e) {
            logger.error("获取小说章节列表接口异常", e);
            return new PageBean<>(false, "系统繁忙,请稍后重试");
        }
    } 
    /**
     * 
      * <p>
      *    获取上一章/下一章
      * </p>
      *
      * @action
      *    lihu 2017年5月16日 上午11:46:50 描述
      *
      * @param novelCode
      * @param change
      * @param chapterNum
      * @param request
      * @return ResultBean<NovelChapterBean>
     */
    @GetMapping(value = "/chapter/view/changeChapter")
    @ResponseBody
    public ResultBean<NovelChapterBean> chapterChange(Integer novelCode,
            String change, Integer chapterNum, HttpServletRequest request) {
        try {
            if (novelCode == null || change == null || chapterNum == null) {
                return new ResultBean<>(false, "参数不可为空");
            }
            if (!change.equals("next") && !change.equals("prev")) {
                return new ResultBean<>(false, "参数错误");
            }
            
            NovelChapterBean chapter = novelChapterBll.getChangeChapter(novelCode,
                change, chapterNum);
            if (chapter == null) {
                return new ResultBean<NovelChapterBean>(false, "获取上一章/下一章无数据",
                        null);
            }
            MessageBean messageBean = messageBll.getMessage(
                CategoryBean.CategoryType.novel.Value(), chapter.getCode(),
                MessageBean.MessageType.editor.Value());
            if(messageBean!=null){
                chapter.setMessage(messageBean.getMessage());
            }
            return new ResultBean<NovelChapterBean>(true, "获取上一章/下一章成功",
                    chapter);

        } catch (Exception e) {
            logger.error("获取上一章/下一章接口异常", e);
            return new ResultBean<>(false, "获取上一章/下一章接口错误");
        }
    }
    /**
     * 
      * <p>
      *    审核章节
      * </p>
      *
      * @action
      *    lihu 2017年5月16日 上午11:59:26 描述
      *
      * @param chapterCode
      * @param status
      * @param request
      * @return ResultBean<String>
     */
    @PostMapping(value = "/chapter/review")
    @ResponseBody
    public ResultBean<String> reviewChapter(Integer chapterCode, String status,
            HttpServletRequest request) {
        try {
            if (chapterCode == null || status == null) {
                return new ResultBean<>(false, "参数不可为空");
            }
            if (!status.equals(NovelChapterBean.Status.passed.Value())
                    && !status.equals(NovelChapterBean.Status.failed.Value())) {
                return new ResultBean<>(false, "参数错误");
            }
            
            return novelService.reviewChapter(chapterCode, status);

        } catch (Exception e) {
            logger.error("审核章节接口异常", e);
            return new ResultBean<>(false, "审核章节接口错误");
        }
    }
    /**
     * 
      * <p>
      *    添加小编回复
      * </p>
      *
      * @action
      *    lihu 2017年5月17日 下午5:17:48 描述
      *
      * @param chapterCode
      * @param message
      * @param request
      * @return ResultBean<String>
     */
    @PostMapping(value = "/chapter/editorMessage")
    @ResponseBody
    public ResultBean<String> editorMessage(Integer chapterCode, String message,
            HttpServletRequest request) {
        try {
            if (chapterCode == null || message == null) {
                return new ResultBean<>(false, "参数不可为空");
            }
           
            return novelService.editorMessage(chapterCode, message);

        } catch (Exception e) {
            logger.error("小编回复接口异常", e);
            return new ResultBean<>(false, "小编回复接口错误");
        }
    }
    
    /**
     * 
      * <p>
      *    获取小说章节信息
      * </p>
      *
      * @action
      *    lihu 2017年5月22日 上午9:53:49 描述
      *
      * @param chapterCode
      * @param request
      * @return ResultBean<Map<String, Object>>
     */
    @GetMapping(value = "/chapter/info")
    @ResponseBody
    public ResultBean<Map<String, Object>> chapterInfo(Integer chapterCode,
            HttpServletRequest request) {
        try {
            if (chapterCode == null ) {
                return new ResultBean<>(false, "参数不可为空");
            }
            Map<String, Object> map = novelChapterBll.chapterInfo(chapterCode,NovelChapterBean.Status.unreviewd.Value());
            return new ResultBean<>(true, "数据返回成功",map);
            
        } catch (Exception e) {
            logger.error("获取小说章节信息接口异常", e);
            return new ResultBean<>(false, "获取小说章节信息接口错误");
        }
    }
    @GetMapping(value = "/recommend")
    @ResponseBody
    public ResultBean<String> chapterInfo(Integer recommendSlotId,Integer novelId,
            HttpServletRequest request) {
        try {
          //验证登录
            OAUserInfoModel userInfoModel = super.getOAUserInfoModel(request);
            if (userInfoModel == null) {
                    return new ResultBean<String>("incorrect-login", "用户没有登录", null);
            }
            if (recommendSlotId == null|| novelId == null) { 
                return new ResultBean<>(false, "参数不可为空");
            }
            
            return recommendService.novelRecommend(recommendSlotId,novelId);
            
        } catch (Exception e) {
            logger.error("获取小说章节信息接口异常", e);
            return new ResultBean<>(false, "获取小说章节信息接口错误");
        }
    }
}
