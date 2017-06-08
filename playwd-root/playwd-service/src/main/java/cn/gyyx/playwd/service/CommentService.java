package cn.gyyx.playwd.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyyx.playwd.beans.common.PageBean;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.CommentBean;
import cn.gyyx.playwd.beans.playwd.RoleBean;
import cn.gyyx.playwd.bll.CommentBll;
import cn.gyyx.playwd.bll.RoleBll;
import cn.gyyx.playwd.bll.UserBll;
import cn.gyyx.playwd.utils.DateTools;

/**
 * 
 * <p>
 * 评论service
 * </p>
 * 
 * @author lihu
 * @since 0.0.1
 */
@Service
public class CommentService {

    /**
     * 评论
     */
    @Autowired
    CommentBll commentBll;

    /**
     * 用户信息
     */
    @Autowired
    UserBll memberInfoBll;
    @Autowired
    RoleBll roleBll;

    /**
     * 获取评论list
     * 
     * @param contentType
     * @param contentId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ResultBean<List<Map<String, String>>> getBrowsePageCommentList(
            String contentType, int contentId, Integer pageIndex,
            Integer pageSize) {
        int startSize = (pageIndex - 1) * pageSize;
        int endSize = pageSize;
        List<Map<String, String>> resultList = new ArrayList<>();
        // 获取浏览页评论列表
        List<CommentBean> list = commentBll.getBrowsePageCommentList(
            contentType, contentId, startSize, endSize);
        if (list != null && list.size() > 0) {
            for (CommentBean item : list) {
                //UserBean user = memberInfoBll.getByUserId(item.getFromUserId());
                //获取默认角色信息
                RoleBean roleBean = roleBll.getDefaultRole(item.getFromUserId());
                String picture ="";
                if(roleBean!=null){
                    picture=roleBean.getPicture();
                }
                
                Map<String, String> map = new HashMap<>();
                map.put("commentId", item.getCode() + "");
                map.put("fromUserAccount",
                    memberInfoBll.accountEncrypt(item.getFromUserAccount()));
                map.put("icon", picture);// 获取用户头像
                map.put("commentTime",
                    commentBll.converCommentDisplayTime(item.getCreateTime()));
                map.put("replyCount", item.getReplyCount() + "");
                map.put("content", item.getContent());
                resultList.add(map);
            }
        }
        return new ResultBean<>(true, "获取成功", resultList);
    }

    @SuppressWarnings("rawtypes")
    public PageBean getCommentList(Integer pageIndex, Integer pageSize,
            String content, String account, String contentType, String title,
            Integer isShow, String startTime, String endTime) throws ParseException   {
        if(endTime !=null&&!"".equals(endTime)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //结束时间往后延一天
            Date nextDate = DateTools.getNextDate(sdf.parse(endTime));
            endTime=sdf.format(nextDate);
        }
        List<Map<String, Object>> list = commentBll.selectCommentList(pageIndex,
            pageSize, content, account, contentType, title, isShow, startTime,
            endTime);
        int count =commentBll.selectCommentListCount(pageIndex,
            pageSize, content, account, contentType, title, isShow, startTime,
            endTime);
        return PageBean.createPage(true, count, pageIndex, pageSize, list, "获取列表成功");
    }

    /**
     * 获取回复列表
     * 
     * @param commentId
     * @return
     */
    public ResultBean<List<Map<String, String>>> getBrowsePageReplyList(
            int commentId) {
        List<Map<String, String>> resultList = new ArrayList<>();
        // 获取浏览页回复列表
        List<CommentBean> list = commentBll.getBrowsePageReplyList(commentId);
        for (CommentBean item : list) {
            //UserBean user = memberInfoBll.getByUserId(item.getFromUserId());
            //获取默认角色信息
            String picture ="";
            RoleBean roleBean = roleBll.getDefaultRole(item.getFromUserId());
            if(roleBean!=null){
                picture=roleBean.getPicture();
            }
            
            Map<String, String> map = new HashMap<>();
            map.put("commentId", item.getCode() + "");
            map.put("fromUserAccount",
                memberInfoBll.accountEncrypt(item.getFromUserAccount()));
            map.put("icon", picture);// 获取用户头像
            map.put("replyTime",
                commentBll.converCommentDisplayTime(item.getCreateTime()));
            map.put("content", item.getContent());
            resultList.add(map);
        }
        return new ResultBean<>(true, "获取成功", resultList);
    }

    /**
     * 回复/评论
     * 
     * @param bean
     * @return
     */
    public ResultBean<Object> comment(CommentBean bean) {
        // 判断用户是否有权限评论 TODO

        // 拼装数据
        bean.setShow(true);
        if (bean.getCode() != null) {
            // 回复评论
            CommentBean item = commentBll.get(bean.getCode());
            if (item == null) {
                return new ResultBean<>(false, "查询不到评论或回复", "");
            }
            bean.setContentType(item.getContentType());
            bean.setContentId(item.getContentId());
            if (item.getParentCode() != 0) {// 代表回复的是回复
                bean.setParentCode(item.getParentCode());
            } else {
                bean.setParentCode(bean.getCode());
            }
            bean.setToUserId(item.getFromUserId());
            bean.setToUserAccount(item.getFromUserAccount());
            if(item.getFromUserId().equals(bean.getFromUserId())){
                return new ResultBean<>(false, "不要自言自语哦~", "");
            }
        } else {
            // 评论文章
            bean.setToUserId(0);
            bean.setToUserAccount("");
            bean.setParentCode(0);
        }

        // 插入评论或回复
        bean.setCreateTime(new Date());
        // 获取楼号 查询之前的记录数
        int count = commentBll.getCount(bean.getContentType(),
            bean.getContentId());
        commentBll.insert(bean);

        // 是否增加消息通知 TODO

        // 会显评论信息
        //UserBean user = memberInfoBll.getByUserId(bean.getFromUserId());
      //获取默认角色信息
        String picture ="";
        RoleBean roleBean = roleBll.getDefaultRole(bean.getFromUserId());
        if(roleBean!=null){
            picture=roleBean.getPicture();
        }
        
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("account",
            memberInfoBll.accountEncrypt(bean.getFromUserAccount()));
        userMap.put("icon", picture);
        userMap.put("userId", bean.getFromUserId());
        userMap.put("createTime", "刚刚");
        userMap.put("code", bean.getCode().intValue());
        userMap.put("content", bean.getContent());
        userMap.put("num", (count + 1));
        return new ResultBean<>(true, "评论成功", userMap);
    }
    /**
     * 
      * <p>
      *    获取详情
      * </p>
      *
      * @action
      *    lihu 2017年4月17日 下午4:01:11 描述
      *
      * @param code
      * @return ResultBean<CommentBean>
     */
    public ResultBean  viewDetails(Integer code) {
        CommentBean commentBean = commentBll.get(code);
        String parent ="child";
        if(commentBean.getParentCode() == 0){
            parent ="parent";
        }
        List<Map<String, Object>> list=commentBll.viewDetailsByCode(code,parent);
        return new ResultBean<>(true, "获取列表成功", list);
    }
    /**
     * 
      * <p>
      *    修改评论状态
      * </p>
      *
      * @action
      *    lihu 2017年4月17日 下午7:25:05 描述
      *
      * @param code
      * @param status void
     * @return 
     */
    public ResultBean<String> auditing(Integer code, String status) {
        boolean flag = false;
        String message = "隐藏修改成功";
        if("show".equals(status)){
            message = "展示修改成功";
            flag =true;
        }
        commentBll.changeCommentIsShow(code, flag);
        return new ResultBean<String>(true,message);
    }

   

    public void setCommentBll(CommentBll commentBll) {
        this.commentBll = commentBll;
    }

    

    public void setMemberInfoBll(UserBll memberInfoBll) {
        this.memberInfoBll = memberInfoBll;
    }

}
