package cn.gyyx.playwd.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.RenderAPI;

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
import cn.gyyx.playwd.utils.DateTools;

/**
 * 
 * <p>
 * 小说service描述
 * </p>
 * 
 * @author chenglong
 * @since 0.0.1
 */
@Service
public class NovelService {

    public NovelBll novelBll;

    public NovelChapterBll novelChapterBll;
    public MessageBll messageBll;
    /**
     * 
     * <p>
     * 检查小说名称是否重复（相同用户的小说名称不能一样,不同用户小说可以一样）<br/>
     * 检查小说是否完结
     * </p>
     *
     * @action chenglong 2017年5月2日 下午6:40:23 描述
     *
     * @param userId
     * @param name
     * @return ResultBean<String>
     */
    public ResultBean<Object> checkName(Integer userId, String name) {
        NovelBean novel = novelBll.selectNovel(userId, name);
        if (novel != null) {
            // 完结的小说不能上传
            if (novel.getStatus().equals(NovelBean.Status.finished.Value())) {
                return new ResultBean<>(false, "novel-over", novel);// 小说已经完结,不能上传章节
            }
            return new ResultBean<>(false, "name-repeat", novel);// 存在同名的小说
        }
        return new ResultBean<>(true, "符合条件", null);
    }

    /**
     * 
     * <p>
     * 检查小说章节名称是否重复
     * </p>
     *
     * @action chenglong 2017年5月2日 下午7:23:18 描述
     *
     * @param userId
     *            userId
     * @param name
     *            小说名称
     * @param title
     *            章节名称
     * @return ResultBean<Object>
     */
    public ResultBean<String> checkChapterTitle(Integer userId, String name,
            String title) {
        int count = novelChapterBll.selectCount(userId, name, title);
        if (count > 0) {
            return new ResultBean<>(false, "存在同名的章节名称,请更换", "title-repeat");
        }
        return new ResultBean<>(true, "符合条件", "");
    }

    /**
     * 
     * <p>
     * 上传章节
     * </p>
     *
     * @action chenglong 2017年5月2日 下午8:06:26 描述
     *
     * @param chapter
     * @param novel
     * @return ResultBean<Object>
     */
    @Transactional
    public ResultBean<Object> chapterUpload(NovelChapterBean chapter,
            NovelBean novel) {
        novel.setCollectCount(0);
        novel.setLikeCount(0);
        novel.setViewCount(0);
        novel.setCreateTime(new Date());
        int contentLength = chapter.getContent().length();

        chapter.setCreateTime(new Date());
        chapter.setWordCount(contentLength);
        if (novel.getUserId() == 0) {
            chapter.setStatus(NovelChapterBean.Status.passed.Value());
        } else {
            chapter.setStatus(NovelChapterBean.Status.unreviewd.Value());

        }

        // 判断小说是否存在
        NovelBean novelItem = novelBll.selectNovel(novel.getUserId(),
            novel.getName());
        if (novelItem != null) {
            // 完结的小说不能上传
            if (novelItem.getStatus()
                    .equals(NovelBean.Status.finished.Value())) {
                return new ResultBean<>(false, "小说已经完结,不能上传章节", "novel-over");
            }
            // 检查小说章节是否已经存在
            int count = novelChapterBll.selectCount(novel.getUserId(),
                novel.getName(), chapter.getTitle());
            if (count > 0) {
                return new ResultBean<>(false, "存在同名的章节名称,请更换", "chapter-over");
            }
            if(novelItem.getUserId()==0){
                novelItem.setLatestPublishTime(novel.getLatestPublishTime());
            }
            chapter.setNovelId(novelItem.getCode());
            novelItem.setDescription(novel.getDescription());
            novelItem.setChapterCount(novelItem.getChapterCount() + 1);
            novelItem.setStatus(novel.getStatus());
            novelItem.setCover(novel.getCover());
            chapter.setChapterNum(novelItem.getChapterCount());
            novelBll.update(novelItem);// 修改章节
        } else {
            novel.setChapterCount(1);
            novelBll.insert(novel);
            chapter.setChapterNum(1);
            chapter.setNovelId(novel.getCode());// 保存章节
        }
        // 保存章节
        novelChapterBll.insert(chapter);
        return new ResultBean<>(true, "上传成功", "");
    }

    @Autowired
    public void setNovelBll(NovelBll novelBll) {
        this.novelBll = novelBll;
    }

    @Autowired
    public void setNovelChapterBll(NovelChapterBll novelChapterBll) {
        this.novelChapterBll = novelChapterBll;
    }

    /**
     * 
     * <p>
     * 检测官方上传小说名称
     * </p>
     *
     * @action lihu 2017年5月8日 下午3:51:01 描述
     *
     * @param name
     * @return ResultBean<Object>
     */
    public ResultBean<Object> checkName(String name) {
        NovelBean novel = novelBll.selectOfficialNovel(name);
        if (novel != null) {
            // 完结的小说不能上传
            if (novel.getStatus().equals(NovelBean.Status.finished.Value())) {
                return new ResultBean<>(false, "小说已经完结,不能上传章节", novel);// 小说已经完结,不能上传章节
            }
            return new ResultBean<>(false, "小说名称已存在", novel);// 存在同名的小说
        }
        return new ResultBean<>(true, "符合条件", null);
    }

    /**
     * 
     * <p>
     * 检测官方上传小说 - 章节名称
     * </p>
     *
     * @action lihu 2017年5月8日 下午3:51:35 描述
     *
     * @param name
     * @param title
     * @return ResultBean<String>
     */
    public ResultBean<String> checkChapterTitle(String name, String title) {
        int count = novelChapterBll.selectCount(0, name, title);
        if (count > 0) {
            return new ResultBean<>(false, "存在同名的章节名称,请更换", "title-repeat");
        }
        return new ResultBean<>(true, "符合条件", "");
    }

    public String download(Integer novelCode, String savePath,
            XWPFTemplate doc) {
        NovelBean novelBean = novelBll.get(novelCode);
        List<Map<String, Object>> list = novelChapterBll
                .getPassedNovel(novelCode);
        if (list.isEmpty() || novelBean == null) {
            return null;
        }
        return FileUtil.download(novelBean, list, savePath, doc);
         
    }

    public ResultBean<String> updateShowAndhidden(Integer novelCode) {
        NovelBean bean = novelBll.get(novelCode);
        NovelBean daoBean = new NovelBean();
        daoBean.setCode(novelCode);
        if(bean==null){
            return new ResultBean<String>(false, "无该小说", "no-novel");
        }
        boolean flag = false;
        if(bean.getIsShow()){ //如果为展示  修改为隐藏
            daoBean.setIsShow(false);
            flag=novelBll.updateShowAndhidden(daoBean);
        }else{//如果为隐藏 修改为展示 
            daoBean.setIsShow(true);
            flag=novelBll.updateShowAndhidden(daoBean);
        }
        if(flag){
            return new ResultBean<String>(true, "修改小说状态成功", "update-success");
        }
        return new ResultBean<String>(false, "修改小说状态失败", "update-fail");
    }

public ResultBean<String> reviewChapter(Integer chapterCode, String status) {
    NovelChapterBean chapterBean = novelChapterBll.get(chapterCode);
    if(chapterBean ==null){
       return new ResultBean<>(false, "该章节无数据");
    }
    //查询
    NovelChapterBean novelChapterBean = novelChapterBll.getChangeChapter(chapterBean.getNovelId(), "prev", chapterBean.getChapterNum());
    if(novelChapterBean==null ||novelChapterBean.getStatus().equals(NovelChapterBean.Status.passed.Value())){
        //修改章节状态
        novelChapterBll.reviewChapter(chapterCode,status);
        // 修改小说最新更新时间
        NovelBean novelBean = novelBll.get(chapterBean.getNovelId());
        novelBean.setLatestPublishTime(new Date());
        novelBll.update(novelBean);
        
        return new ResultBean<>(true, "该章节审核成功");
    }
    
    return new ResultBean<>(false, "该章节不符合审核规则");
}

public PageBean<Map<String, Object>> getChapterPage(Integer novelCode,
        Integer startChapterNum, Integer endChapterNum, String startTime,
        String endTime, String status, Integer pageIndex, Integer pageSize) throws ParseException {
    if(endTime !=null&&!"".equals(endTime)){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //结束时间往后延一天
        Date nextDate = DateTools.getNextDate(sdf.parse(endTime));
        endTime=sdf.format(nextDate);
    }
    List<Map<String, Object>> list = novelChapterBll.getChapterList(novelCode,startChapterNum,endChapterNum, startTime, endTime,status,pageIndex,pageSize);
    int count = novelChapterBll.getChapterListCount(novelCode,startChapterNum,endChapterNum,  startTime, endTime,status);
    //为list 添加上一章状态
    boolean flag =toListPrevStatus(list);
    return PageBean.createPage(true, count, pageIndex, pageSize, list, "获取小说章节列表成功"); 
}

public boolean toListPrevStatus(List<Map<String, Object>> list) {
    boolean flag = false;
    for (Map<String, Object> map : list) {
       
        String novel_id = map.get("novel_id").toString();
        String chapter_num = map.get("chapter_num").toString();
        NovelChapterBean novelChapterBean = novelChapterBll.getChangeChapter(Integer.valueOf(novel_id), "prev",Integer.valueOf(chapter_num));
        if(novelChapterBean==null){
            map.put("prevStatus", true);
        }else{
            String status = novelChapterBean.getStatus();
            if("unreviewd".equals(status)){
                map.put("prevStatus", false);
            }else if("failed".equals(status)){
                map.put("prevStatus", false);
            }else if("passed".equals(status)){
                map.put("prevStatus", true);
            }
        }
        flag = true;
    }
    return flag;
}

public ResultBean<String> editorMessage(Integer chapterCode, String message) {
    NovelChapterBean chapterBean = novelChapterBll.get(chapterCode);
    if(chapterBean == null){
        return new ResultBean<String>(false, "章节不存在");
    }
    NovelBean novelBean = novelBll.get(chapterBean.getNovelId());
    if(novelBean == null){
        return new ResultBean<String>(false, "小说不存在");
    }
    MessageBean messageBean = messageBll.getMessage(CategoryBean.CategoryType.novel.Value(), chapterCode, MessageBean.MessageType.editor.Value());
    if(messageBean != null){
        return new ResultBean<String>(false, "小编回复内容已存在,不可添加");
    }
    boolean flag = messageBll.add(message, MessageBean.MessageType.editor.Value(), chapterCode, CategoryBean.CategoryType.novel.Value(), novelBean.getUserId(), chapterBean.getTitle());
    if(flag){
        return new ResultBean<String>(true, "添加小编回复成功");
        
    }
    return new ResultBean<String>(false, "添加小编回复失败");
}
@Autowired
public void setMessageBll(MessageBll messageBll) {
    this.messageBll = messageBll;
}
/**
 * 
  * <p>
  *    编辑章节小说
  * </p>
  *
  * @action
  *    lihu 2017年5月22日 下午3:33:23 描述
  *
  * @param chapter
  * @param novel
  * @return ResultBean<Object>
 */
    public ResultBean<Object> chapterEditor(NovelChapterBean chapter,
            NovelBean novel) {
        // 查询章节是否待审核
        NovelChapterBean chapterBean = novelChapterBll.get(chapter.getCode());
        if (chapterBean == null || !chapterBean.getStatus()
                .equals(NovelChapterBean.Status.unreviewd.Value())) {
            return new ResultBean<>(false, "章节不存在或不可编辑", "chapter-uneditor");
        }
        // 判断小说是否存在
        NovelBean novelBean = novelBll.get(chapterBean.getNovelId());
        if (novelBean == null) {
            return new ResultBean<>(false, "小说不存在或不可编辑", "novel-uneditor");
        }
        // 检查小说章节是否已经存在
        int count = novelChapterBll.selectCount(novel.getUserId(),
            novel.getName(), chapter.getTitle());
        if (count > 0) {
            return new ResultBean<>(false, "存在同名的章节名称,请更换", "chapter-over");
        }
        novelBean.setName(novel.getName().trim());
        novelBean.setCategoryId(novel.getCategoryId());
        novelBean.setCover(novel.getCover().trim());
        novelBean.setDescription(novel.getDescription().trim());
        novelBean.setStatus(novel.getStatus());

        chapterBean.setTitle(chapter.getTitle().trim());
        chapterBean.setContent(chapter.getContent());

        // 保存章节
        novelBll.update(novelBean);
        novelChapterBll.updateByCodeSelective(chapterBean);
        return new ResultBean<>(true, "编辑成功", "");
    }

}
