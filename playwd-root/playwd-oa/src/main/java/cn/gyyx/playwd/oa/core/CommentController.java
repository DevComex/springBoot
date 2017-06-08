package cn.gyyx.playwd.oa.core;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.playwd.beans.common.PageBean;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.service.CommentService;

/**
 * 评论controller
 * 
 * @author lihu
 *
 */
@Controller
@RequestMapping(value = "/comment")
public class CommentController {

    private Logger logger = GYYXLoggerFactory.getLogger(getClass());

    @Autowired
    private CommentService commentService;
    @RequestMapping(value = {"/",""})
    public String commentManage(){
        return "comment/commentManage";
    }  
   /**
    * 
     * <p>
     *    获取评论分页
     * </p>
     *
     * @action
     *    lihu 2017年4月17日 下午2:47:21 描述
     *
     * @param pageIndex
     * @param pageSize
     * @param content
     * @param account
     * @param contentType
     * @param title
     * @param isShow
     * @param startTime
     * @param endTime
     * @param request
     * @param response
     * @return PageBean<Map<String,Object>>
    */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/list")
    @ResponseBody
    public PageBean<Map<String, Object>> list(Integer pageIndex,
            Integer pageSize, String content, String account,
            String contentType, String title, Integer isShow, String startTime,
            String endTime, HttpServletRequest request,
            HttpServletResponse response) {
        if (pageIndex == null || pageSize == null) {
            return new PageBean<>(false, "pageIndex或pageSize不可为空");
        }
        if(!this.regChinese(content)){
            return new PageBean<>(false, "关键字搜索必须包含中文");
        }
        try {
            logger.info(
                "获取评论列表接口:contentType:{},content:{} ,pageIndex:{},pageSize:{},account{},title{},isShow{},startTime{},endTime{}",
                contentType, content, pageIndex, pageSize, account, title,
                isShow, endTime);
            return commentService.getCommentList(pageIndex, pageSize, content,
                account, contentType, title, isShow, startTime, endTime);
        } catch (Exception e) {
            logger.error("获取评论列表接口异常:", e);
            return new PageBean<>(false, "获取评论列表接口异常");
        }
    }
    /**
     * 
      * <p>
      *    查看详情
      * </p>
      *
      * @action
      *    lihu 2017年4月17日 下午3:53:46 描述
      *
      * @param code
      * @param request
      * @return ResultBean<CommentBean>
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/viewDetails")
    @ResponseBody
    public ResultBean<List<Map<String, Object>>> viewDetails(Integer code, HttpServletRequest request) {
        try {
            if(code==null){
                return new ResultBean<>(false, "主键不可为空");
            }
            logger.info("查看详情 code={}",code);
            return commentService.viewDetails(code);
        } catch (Exception e) {
            logger.error("查看详情接口异常:", e);
            return new PageBean<>(false, "查看详情接口异常");
        }
        
    }
    /**
     * 
      * <p>
      *    批量隐藏/展示
      * </p>
      *
      * @action
      *    lihu 2017年4月17日 下午7:16:41 描述
      *
      * @param codeIteam
      * @param status
      * @param request
      * @return ResultBean<List<Map<String,Object>>>
     */
    @RequestMapping(value = "/review")
    @ResponseBody
    public ResultBean<String> auditing(@RequestParam("codeIteam[]")Integer[] codeIteam,String status, HttpServletRequest request) {
        try {
            if(codeIteam==null){
                return new ResultBean<>(false, "主键不可为空");
            }
            logger.info("查看详情 code={}",Arrays.toString(codeIteam));
            ResultBean<String>  result = new ResultBean<>();
            for (Integer code : codeIteam) {
                result =commentService.auditing(code,status);
            }
            
            return result;
        } catch (Exception e) {
            logger.error("批量隐藏/展示接口异常:", e);
            return new PageBean<>(false, "批量隐藏/展示接口异常");
        }
        
    }
    
    private boolean regChinese(String comment) {
        if(comment==null||"".equals(comment)){
            return true;
        }
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher matcher = pattern.matcher(comment);
        
        if (matcher.find()) {
            return true;
        }
        return false;

    }
}
