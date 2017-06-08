/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：玩家天地
 * @作者：李杜迪
 * @联系方式：lidudi@gyyx.cn
 * @创建时间：2017年4月27日下午3:11:49
 * @版本号：1.0.0
 *-------------------------------------------------------------------------
 */
package cn.gyyx.playwd;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import cn.gyyx.playwd.beans.playwd.CategoryBean;
import cn.gyyx.playwd.beans.playwd.MessageBean;
import cn.gyyx.playwd.beans.playwd.NovelBean;
import cn.gyyx.playwd.beans.playwd.NovelChapterBean;
import cn.gyyx.playwd.bll.MessageBll;
import cn.gyyx.playwd.bll.NovelBll;
import cn.gyyx.playwd.bll.NovelChapterBll;
import cn.gyyx.playwd.service.NovelService;
import cn.gyyx.playwd.utils.FileUtil;

/**
 * 推荐管理
 * 
 * @author lidudi
 *
 */
public class NovelServiceTest {

    private NovelService novelService;

    private NovelBll novelBll;

    private NovelChapterBll novelChapterBll;
    private MessageBll messageBll;
    @Before
    public void setMockup() {
        novelService = new NovelService();
        novelBll = mock(NovelBll.class);
        novelChapterBll = mock(NovelChapterBll.class);
        messageBll = mock(MessageBll.class);
        
        novelService.setNovelBll(novelBll);
        novelService.setMessageBll(messageBll);
        novelService.setNovelChapterBll(novelChapterBll);
    }

    //检查小说名称是否合法_当输入的小说名称重复且状态为未完结_然后提示小说名称重复
    @Test
    public void checkName_whenNameIsRepeatAndStatusIsNotFinished_thenReturnRepeat()
            throws Exception {
        //参数
        int userId = 294319;
        String name = "万古界圣";
        //期望结果
        String resultExpect = "name-repeat";
        //mock过程
        NovelBean novel = new NovelBean();
        novel.setStatus(NovelBean.Status.unfinished.Value());
        when(novelBll.selectNovel(userId,name)).thenReturn(novel);
        //调用函数
        String resultActual = novelService.checkName(userId,name).getMessage();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    
    //检查小说名称是否合法_当输入的小说名称重复且状态为未完结_然后提示小说名称重复
    @Test
    public void checkName_whenNameIsRepeatAndStatusIsFinished_thenReturnFinished()
            throws Exception {
        //参数
        int userId = 294319;
        String name = "万古界圣";
        //期望结果
        String resultExpect = "novel-over";
        //mock过程
        NovelBean novel = new NovelBean();
        novel.setStatus(NovelBean.Status.finished.Value());
        when(novelBll.selectNovel(userId,name)).thenReturn(novel);
        //调用函数
        String resultActual = novelService.checkName(userId,name).getMessage();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    
    //检查小说名称是否合法_当输入的小说名称不重复_然后返回成功
    @Test
    public void checkName_whenNameIsNotRepeat_thenReturnSuccess()
            throws Exception {
        //参数
        int userId = 294319;
        String name = "万古界圣x";
        //期望结果
        boolean resultExpect = true;
        //mock过程
        when(novelBll.selectNovel(userId,name)).thenReturn(null);
        //调用函数
        boolean resultActual = novelService.checkName(userId,name).getIsSuccess();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    
    //检查小说章节名称是否重复_当章节名称重复_然后返回章节重复
    @Test
    public void checkChapterTitle_whenTitleIsRepeat_thenReturnRepeat()
            throws Exception {
        //参数
        int userId = 294319;
        String name = "万古界圣";
        String title = "疯癫老头";
        //期望结果
        String resultExpect = "title-repeat";
        //mock过程
        when(novelChapterBll.selectCount(userId,name,title)).thenReturn(1);
        //调用函数
        String resultActual = novelService.checkChapterTitle(userId,name,title).getData();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    //检查小说章节名称是否重复_当章节名称重复_然后返回章节重复
    @Test
    public void checkChapterTitle3_whenTitleIsRepeat_thenReturnRepeat()
            throws Exception {
        //参数
        int userId = 294319;
        String name = "万古界圣";
        String title = "疯癫老头";
        //期望结果
        String resultExpect = "";
        //mock过程
        when(novelChapterBll.selectCount(userId,name,title)).thenReturn(0);
        //调用函数
        String resultActual = novelService.checkChapterTitle(userId,name,title).getData();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    
    //检查小说章节名称是否重复_当章节名称重复_然后返回章节重复
    @Test
    public void checkChapterTitle_whenTitleIsNotRepeat_thenReturnTrue()
            throws Exception {
        //参数
        int userId = 294319;
        String name = "万古界圣";
        String title = "疯癫老头";
        //期望结果
        boolean resultExpect = true;
        //mock过程
        when(novelChapterBll.selectCount(userId,name,title)).thenReturn(0);
        //调用函数
        boolean resultActual = novelService.checkChapterTitle(userId,name,title).getIsSuccess();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    //检查小说章节名称是否重复_当章节名称重复_然后返回章节重复
    @Test
    public void checkChapterTitle4_whenTitleIsNotRepeat_thenReturnTrue()
            throws Exception {
        //参数
        int userId = 294319;
        String name = "万古界圣";
        String title = "符合条件";
        //期望结果
        boolean resultExpect = true;
        //mock过程
        when(novelChapterBll.selectCount(userId,name,title)).thenReturn(0);
        //调用函数
        boolean resultActual = novelService.checkChapterTitle(name,title).getIsSuccess();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    
    //上传小说_当小说已经完结_然后返回false
    @Test
    public void chapterUpload_whenStatusIsFinished_thenReturnFalse()
            throws Exception {
        //参数
        int userId = 294319;
        String name = "万古界圣";
        NovelChapterBean chapter = new NovelChapterBean();
        chapter.setContent("小说内容为：xxxxx");
        chapter.setUserId(userId);
        NovelBean novel = new NovelBean();
        novel.setUserId(userId);
        novel.setName(name);
        novel.setStatus(NovelBean.Status.finished.Value());
        //期望结果
        boolean resultExpect = false;
        //mock过程
        when(novelBll.selectNovel(novel.getUserId(), novel.getName())).thenReturn(novel);
        //调用函数
        boolean resultActual = novelService.chapterUpload(chapter,novel).getIsSuccess();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    
    //上传小说_当章节名称重复_然后返回false
    @Test
    public void chapterUpload_whenTitleIsRepeat_thenReturnFalse()
            throws Exception {
        //参数
        int userId = 294319;
        String name = "万古界圣";
        NovelChapterBean chapter = new NovelChapterBean();
        chapter.setContent("小说内容为：xxxxx");
        chapter.setUserId(userId);
        NovelBean novel = new NovelBean();
        novel.setUserId(userId);
        novel.setName(name);
        novel.setStatus(NovelBean.Status.unfinished.Value());
        //期望结果
        boolean resultExpect = false;
        //mock过程
        when(novelBll.selectNovel(novel.getUserId(), novel.getName())).thenReturn(novel);
        when(novelChapterBll.selectCount(novel.getUserId(),novel.getName(),chapter.getTitle())).thenReturn(1);
        //调用函数
        boolean resultActual = novelService.chapterUpload(chapter,novel).getIsSuccess();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    
    //上传小说_当小说不存在_然后返回true
    @Test
    public void chapterUpload_whenNovelIsNotExist_thenReturnTrue()
            throws Exception {
        //参数
        int userId = 294319;
        String name = "万古界圣";
        NovelChapterBean chapter = new NovelChapterBean();
        chapter.setContent("小说内容为：xxxxx");
        chapter.setUserId(userId);
        NovelBean novel = new NovelBean();
        novel.setUserId(userId);
        novel.setName(name);
        novel.setCode(1);
        //期望结果
        boolean resultExpect = true;
        //mock过程
        when(novelBll.selectNovel(novel.getUserId(), novel.getName())).thenReturn(null);
        when(novelBll.insert(novel)).thenReturn(1);
        when(novelChapterBll.insert(chapter)).thenReturn(1);
        //调用函数
        boolean resultActual = novelService.chapterUpload(chapter,novel).getIsSuccess();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    @Test
    public void chapterUpload2_whenNovelIsNotExist_thenReturnTrue()
            throws Exception {
        //参数
        int userId = 0;
        String name = "official";
        NovelChapterBean chapter = new NovelChapterBean();
        chapter.setContent("小说内容为：xxxxx");
        chapter.setUserId(userId);
        NovelBean novel = new NovelBean();
        novel.setUserId(userId);
        novel.setName(name);
        novel.setCode(1);
        //期望结果
        boolean resultExpect = true;
        //mock过程
        when(novelBll.selectNovel(novel.getUserId(), novel.getName())).thenReturn(null);
        when(novelBll.insert(novel)).thenReturn(1);
        when(novelChapterBll.insert(chapter)).thenReturn(1);
        //调用函数
        boolean resultActual = novelService.chapterUpload(chapter,novel).getIsSuccess();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    //上传小说_当小说存在且可以上传章节_然后返回true
    @Test
    public void chapterUpload_whenNovelIsExistAndParamsIsOk_thenReturnTrue()
            throws Exception {
        //参数
        int userId = 294319;
        String name = "万古界圣";
        NovelChapterBean chapter = new NovelChapterBean();
        chapter.setContent("小说内容为：xxxxx");
        chapter.setUserId(userId);
        NovelBean novel = new NovelBean();
        novel.setUserId(userId);
        novel.setName(name);
        novel.setCode(1);
        novel.setChapterCount(1);
        novel.setStatus(NovelBean.Status.unfinished.Value());
        //期望结果
        boolean resultExpect = true;
        //mock过程
        when(novelBll.selectNovel(novel.getUserId(), novel.getName())).thenReturn(novel);
        when(novelChapterBll.selectCount(novel.getUserId(),novel.getName(),chapter.getTitle())).thenReturn(0);
        when(novelBll.insert(novel)).thenReturn(1);
        when(novelChapterBll.insert(chapter)).thenReturn(1);
        //调用函数
        boolean resultActual = novelService.chapterUpload(chapter,novel).getIsSuccess();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    // 检测官方上传小说 名称是否可用
    @Test
    public void checkName_whenStatusIsNotFinished_thenReturnRepeat()
            throws Exception {
        //参数
        String name = "万古界圣";
        //期望结果
        String resultExpect = "小说名称已存在";
        //mock过程
        NovelBean novel = new NovelBean();
        novel.setStatus(NovelBean.Status.unfinished.Value());
        when(novelBll.selectOfficialNovel(name)).thenReturn(novel);
        //调用函数
        String resultActual = novelService.checkName(name).getMessage();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
 // 检测官方上传小说 名称是否可用
    @Test
    public void checkName_whenStatusIsFinished_thenReturnRepeat()
            throws Exception {
        //参数
        String name = "万古界圣";
        //期望结果
        String resultExpect = "小说已经完结,不能上传章节";
        //mock过程
        NovelBean novel = new NovelBean();
        novel.setStatus(NovelBean.Status.finished.Value());
        when(novelBll.selectOfficialNovel(name)).thenReturn(novel);
        //调用函数
        String resultActual = novelService.checkName(name).getMessage();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
  //检查小说章节名称是否重复_当章节名称重复_然后返回章节重复
    @Test
    public void checkChapterTitle2_whenTitleIsRepeat_thenReturnRepeat()
            throws Exception {
        //参数
        int userId = 0;
        String name = "万古界圣";
        String title = "疯癫老头";
        //期望结果
        String resultExpect = "title-repeat";
        //mock过程
        when(novelChapterBll.selectCount(userId, name, title) ).thenReturn(1);
        //调用函数
        String resultActual = novelService.checkChapterTitle(name,title).getData();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
 // 检测官方上传小说 名称是否可用
    @Test
    public void checkName4_whenStatusIsFinished_thenReturnRepeat()
            throws Exception {
        //参数
        String name = "万古界圣";
        //期望结果
        String resultExpect = "符合条件";
        //mock过程
        NovelBean novel = new NovelBean();
        novel.setStatus(NovelBean.Status.finished.Value());
        when(novelBll.selectOfficialNovel(name)).thenReturn(null);
        //调用函数
        String resultActual = novelService.checkName(name).getMessage();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }

    // 修改小说状态 小说不存在
    @Test
    public void updateShowAndhidden_whenNovelBeanIsNull_thenReturnNovelIsNull()
            throws Exception {
        //参数
        Integer novelCode = 1;
        //期望结果
        String resultExpect = "无该小说";
        //mock过程
        NovelBean novel = new NovelBean();
        
        when(novelBll.get(novelCode)).thenReturn(null);
        //调用函数
         String resultActual = novelService.updateShowAndhidden(novelCode).getMessage();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    
    // 修改小说状态 如果为展示  修改为隐藏 成功
    @Test
    public void updateShowAndhidden2_whenShowToHiddenSuccess_thenReturnUpdateStatusSuccess()
            throws Exception {
        //参数
        Integer novelCode = 1;
        //期望结果
        String resultExpect = "修改小说状态成功";
        //mock过程
        NovelBean novel = new NovelBean();
        novel.setIsShow(true);
        novel.setCode(novelCode);
        NovelBean daoBean = new NovelBean();
        daoBean.setCode(novelCode);
        
        when(novelBll.get(novelCode)).thenReturn(novel);
        when(novelBll.updateShowAndhidden(any())).thenReturn(true);
        
        //调用函数
        String resultActual = novelService.updateShowAndhidden(novelCode).getMessage();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    // 修改小说状态 如果为展示  修改为隐藏 失败
    @Test
    public void updateShowAndhidden3_whenShowToHiddenFail_thenReturnUpdateStatusFail()
            throws Exception {
        //参数
        Integer novelCode = 1;
        //期望结果
        String resultExpect = "修改小说状态失败";
        //mock过程
        NovelBean novel = new NovelBean();
        novel.setIsShow(true);
        novel.setCode(novelCode);
        NovelBean daoBean = new NovelBean();
        daoBean.setCode(novelCode);
        
        when(novelBll.get(novelCode)).thenReturn(novel);
        when(novelBll.updateShowAndhidden(any())).thenReturn(false);
        //调用函数
        String resultActual = novelService.updateShowAndhidden(novelCode).getMessage();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
 // 修改小说状态如果为隐藏 修改为展示  失败
    @Test
    public void updateShowAndhidden4_whenHiddenToShowFail_thenReturnUpdateStatusFail()
            throws Exception {
        //参数
        Integer novelCode = 1;
        //期望结果
        String resultExpect = "修改小说状态失败";
        //mock过程
        NovelBean novel = new NovelBean();
        novel.setIsShow(false);
        novel.setCode(novelCode);
        NovelBean daoBean = new NovelBean();
        daoBean.setCode(novelCode);
        
        when(novelBll.get(novelCode)).thenReturn(novel);
        when(novelBll.updateShowAndhidden(any())).thenReturn(false);
        
        //调用函数
        String resultActual = novelService.updateShowAndhidden(novelCode).getMessage();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
 // 修改小说状态如果为隐藏 修改为展示  成功
    @Test
    public void updateShowAndhidden5_whenHiddenToShowSuccess_thenReturnUpdateStatusSuccess()
            throws Exception {
        //参数
        Integer novelCode = 1;
        //期望结果
        String resultExpect = "修改小说状态成功";
        //mock过程
        NovelBean novel = new NovelBean();
        novel.setIsShow(false);
        novel.setCode(novelCode);
        NovelBean daoBean = new NovelBean();
        daoBean.setCode(novelCode);
        
        when(novelBll.get(novelCode)).thenReturn(novel);
        when(novelBll.updateShowAndhidden(any())).thenReturn(true);
        
        //调用函数
        String resultActual = novelService.updateShowAndhidden(novelCode).getMessage();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    
    // 章节审核 章节无数据
    @Test
    public void reviewChapter_whenChapterBeanIsNull_thenReturnChapterIsNull()
            throws Exception {
        //参数
        Integer chapterCode = 1;
        String status = "";
        //期望结果
        String resultExpect = "该章节无数据";
        //mock过程
        NovelChapterBean chapterBean = new NovelChapterBean();
       
        
        when(novelChapterBll.get(chapterCode)).thenReturn(null);
        
        //调用函数
        String resultActual = novelService.reviewChapter(chapterCode, status).getMessage();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    // 章节审核  上一章节未审核
    @Test
    public void reviewChapter2_whenPrevChapterIsNull__thenReturnReviewChapterNo()
            throws Exception {
        //参数
        Integer chapterCode = 1;
        String status = "";
        //期望结果
        String resultExpect = "该章节不符合审核规则";
        //mock过程
        NovelChapterBean chapterBean = new NovelChapterBean();
        chapterBean.setStatus("test");
        
        when(novelChapterBll.get(chapterCode)).thenReturn(chapterBean);
        when(novelChapterBll.getChangeChapter(chapterBean.getNovelId(), "prev", chapterBean.getChapterNum())).thenReturn(chapterBean);
        
        //调用函数
        String resultActual = novelService.reviewChapter(chapterCode, status).getMessage();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    //章节审核 成功
    @Test
    public void reviewChapter3_whenReviewSuccess_thenReturnReviewChapterSuccess()
            throws Exception {
        //参数
        Integer chapterCode = 1;
        Integer novelCode = 2;
        String status = "";
        //期望结果
        String resultExpect = "该章节审核成功";
        //mock过程
        NovelChapterBean chapterBean = new NovelChapterBean();
        chapterBean.setStatus("test");
        chapterBean.setNovelId(novelCode);
        NovelBean daoBean = new NovelBean();
        daoBean.setCode(novelCode);
        
        when(novelChapterBll.get(chapterCode)).thenReturn(chapterBean);
        when(novelChapterBll.getChangeChapter(chapterBean.getNovelId(), "prev", chapterBean.getChapterNum())).thenReturn(null);
        when(novelBll.get(chapterBean.getNovelId())).thenReturn(daoBean);
        
        //调用函数
        String resultActual = novelService.reviewChapter(chapterCode, status).getMessage();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    
    //获取章节预览
    @Test
    public void getChapterPage_whenChapterListAndCount_thenReturnGetChapterPageSuccess() throws Exception {
        // 参数
        Integer novelCode = 1;
        Integer startChapterNum = 1;
        Integer endChapterNum = 1;
        String startTime = "2017-04-17";
        String endTime = "2017-05-17";
        String status = "1";
        Integer pageIndex = 1;
        Integer pageSize = 1;
        // 期望结果
        String resultExpect = "获取小说章节列表成功";
        // mock过程
        NovelChapterBean chapterBean = new NovelChapterBean();
        chapterBean.setStatus("test");

        when(novelChapterBll.getChapterList(novelCode,startChapterNum,endChapterNum, startTime, endTime,status,pageIndex,pageSize)).thenReturn(null);
        when(novelChapterBll.getChapterListCount(novelCode,startChapterNum,endChapterNum,  startTime, endTime,status)).thenReturn(0);

        // 调用函数
        String resultActual = novelService.getChapterPage(novelCode, startChapterNum, endChapterNum, startTime, endTime, status, pageIndex, pageSize)
                .getMessage();
        // 断言结果
        assertEquals(resultActual, resultExpect);
    }
    
    // 小编回复
    @Test
    public void editorMessage_whenChapterBeanIsNull_thenReturnChapterIsNull()
            throws Exception {
        //参数
        Integer chapterCode = 1;
        String message = "message";
        //期望结果
        String resultExpect = "章节不存在";
        //mock过程
        NovelChapterBean chapterBean = new NovelChapterBean();
        chapterBean.setStatus("test");
        
        when(novelChapterBll.get(chapterCode)).thenReturn(null);
        
        //调用函数
        String resultActual = novelService.editorMessage(chapterCode, message).getMessage();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    // 小编回复
    @Test
    public void editorMessage2_whenNovelBeanIsNull_thenReturnNovelIsNull()
            throws Exception {
        //参数
        Integer chapterCode = 1;
        String message = "message";
        //期望结果
        String resultExpect = "小说不存在";
        //mock过程
        NovelChapterBean chapterBean = new NovelChapterBean();
        chapterBean.setStatus("test");
        
        when(novelChapterBll.get(chapterCode)).thenReturn(chapterBean);
        when(novelBll.get(any())).thenReturn(null);
        
        //调用函数
        String resultActual = novelService.editorMessage(chapterCode, message).getMessage();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    // 小编回复 消息已存在
    @Test
    public void editorMessage3_whenMessageIsTrue_thenReturnNoAdd()
            throws Exception {
        //参数
        Integer chapterCode = 1;
        String message = "message";
        //期望结果
        String resultExpect = "小编回复内容已存在,不可添加";
        //mock过程
        NovelChapterBean chapterBean = new NovelChapterBean();
        chapterBean.setStatus("test");
        NovelBean daoBean = new NovelBean();
        daoBean.setCode(1);
        
        when(novelChapterBll.get(chapterCode)).thenReturn(chapterBean);
        when(novelBll.get(any())).thenReturn(  daoBean);
        when(messageBll.getMessage(CategoryBean.CategoryType.novel.Value(), chapterCode, MessageBean.MessageType.editor.Value())).thenReturn(new MessageBean());
        
        //调用函数
        String resultActual = novelService.editorMessage(chapterCode, message).getMessage();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    // 小编回复
    @Test
    public void editorMessage4_whenAddMessageFalse_thenReturnAddFail()
            throws Exception {
        //参数
        Integer chapterCode = 1;
        String message = "message";
        //期望结果
        String resultExpect = "添加小编回复失败";
        //mock过程
        NovelChapterBean chapterBean = new NovelChapterBean();
        chapterBean.setStatus("test");
        NovelBean daoBean = new NovelBean();
        daoBean.setCode(1);
        daoBean.setUserId(1);
        
        when(novelChapterBll.get(chapterCode)).thenReturn(chapterBean);
        when(novelBll.get(any())).thenReturn(  daoBean);
        when(messageBll.getMessage(CategoryBean.CategoryType.novel.Value(), chapterCode, MessageBean.MessageType.editor.Value())).thenReturn(null);
        when(messageBll.add(message, MessageBean.MessageType.editor.Value(), chapterCode, CategoryBean.CategoryType.novel.Value(), daoBean.getUserId(), chapterBean.getTitle())).thenReturn(false);
        
        //调用函数
        String resultActual = novelService.editorMessage(chapterCode, message).getMessage();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    @Test
    public void editorMessage5_whenAddMessageSuccess_thenReturnAddSuccess()
            throws Exception {
        //参数
        Integer chapterCode = 1;
        String message = "message";
        //期望结果
        String resultExpect = "添加小编回复成功";
        //mock过程
        NovelChapterBean chapterBean = new NovelChapterBean();
        chapterBean.setStatus("test");
        NovelBean daoBean = new NovelBean();
        daoBean.setCode(1);
        daoBean.setUserId(1);
        
        when(novelChapterBll.get(chapterCode)).thenReturn(chapterBean);
        when(novelBll.get(any())).thenReturn(  daoBean);
        when(messageBll.getMessage(CategoryBean.CategoryType.novel.Value(), chapterCode, MessageBean.MessageType.editor.Value())).thenReturn(null);
        when(messageBll.add(message, MessageBean.MessageType.editor.Value(), chapterCode, CategoryBean.CategoryType.novel.Value(), daoBean.getUserId(), chapterBean.getTitle())).thenReturn(true);
        
        //调用函数
        String resultActual = novelService.editorMessage(chapterCode, message).getMessage();
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    // 下载 当小说为空  返回null
    @Test
    public void download_whenNovelListIsNull_thenReturnNull()
            throws Exception {
        //参数
        Integer novelCode = 1;
        
        //期望结果
        String resultExpect = null;
        //mock过程
        NovelBean novelBean = new NovelBean();
        novelBean.setCode(1);
        novelBean.setUserId(1);
        
        when(novelBll.get(novelCode)).thenReturn(null);
        when(novelChapterBll.getPassedNovel(novelCode)).thenReturn(new ArrayList<>());
        
        //调用函数
        String resultActual = novelService.download(novelCode, "", null);
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    
 // 下载 当小说存在  路劲为空   返回null
    @Test
    public void download_whenNovelListIsTrue_thenReturnPath()
            throws Exception {
        //参数
        Integer novelCode = 1;
        
        //期望结果
        String resultExpect = null;
        //mock过程
        NovelBean novelBean = new NovelBean();
        novelBean.setCode(1);
        novelBean.setUserId(1);
        List<Map<String, Object>> list = new  ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("novel_id", 2);
        map.put("chapter_num", 2);
        list.add(map);
        when(novelBll.get(novelCode)).thenReturn(novelBean);
        when(novelChapterBll.getPassedNovel(novelCode)).thenReturn(list);
        //when(FileUtil.download(any(),any(), any(), any())).thenReturn("path");
        
        //调用函数
        String resultActual = novelService.download(novelCode, "", null);
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    
    
    // 为list 添加上一章状态 当list为空   返回false
    @Test
    public void toListPrevStatus_whenListIsEmpty_thenReturnfalse()
            throws Exception {
        //参数
        Integer novelCode = 1;
        
        //期望结果
        boolean resultExpect = false;
        //mock过程
        
        
        //调用函数
        boolean resultActual = novelService.toListPrevStatus( new ArrayList<>());
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    
    // 为list 添加上一章状态 当list为空   返回false
    @Test
    public void toListPrevStatus_whenNovelChapterBeanIsNull_thenReturnfalse()
            throws Exception {
        //参数
        Integer novelCode = 1;
        
        //期望结果
        boolean resultExpect = false;
        //mock过程
        List<Map<String, Object>> list = new  ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("novel_id", 2);
        map.put("chapter_num", 2);
        list.add(map);
        
        NovelChapterBean bean = new NovelChapterBean();
        bean .setStatus("");
        when(novelChapterBll.getChangeChapter(2, "prev",2)).thenReturn(null);
        
        //调用函数
        boolean resultActual =novelService.toListPrevStatus( new ArrayList<>());
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    
    // 为list 添加上一章状态 当list为空   返回false
    @Test
    public void toListPrevStatus_whenNovelChapterBeanIsprev_thenReturnfalse()
            throws Exception {
        //参数
        Integer novelCode = 1;
        
        //期望结果
        boolean resultExpect = false;
        //mock过程
        List<Map<String, Object>> list = new  ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("novel_id", 2);
        map.put("chapter_num", 2);
        list.add(map);
        
        NovelChapterBean bean = new NovelChapterBean();
        bean .setStatus("passed");
        when(novelChapterBll.getChangeChapter(2, "prev",2)).thenReturn(bean);
        
        //调用函数
        boolean resultActual = novelService.toListPrevStatus( new ArrayList<>());
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    // 为list 添加上一章状态 当list为空   返回false
    @Test
    public void toListPrevStatus_whenNovelChapterBeanIsunreviewd_thenReturnfalse()
            throws Exception {
        //参数
        Integer novelCode = 1;
        
        //期望结果
        boolean resultExpect = false;
        //mock过程
        List<Map<String, Object>> list = new  ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("novel_id", 2);
        map.put("chapter_num", 2);
        list.add(map);
        
        NovelChapterBean bean = new NovelChapterBean();
        bean .setStatus("unreviewd");
        when(novelChapterBll.getChangeChapter(2, "prev",2)).thenReturn(bean);
        
        //调用函数
        boolean resultActual = novelService.toListPrevStatus( new ArrayList<>());
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    // 为list 添加上一章状态 当list为空   返回false
    @Test
    public void toListPrevStatus_whenNovelChapterBeanIsfailed_thenReturnfalse()
            throws Exception {
        //参数
        Integer novelCode = 1;
        
        //期望结果
        boolean resultExpect = false;
        //mock过程
        List<Map<String, Object>> list = new  ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("novel_id", 2);
        map.put("chapter_num", 2);
        list.add(map);
        
        NovelChapterBean bean = new NovelChapterBean();
        bean .setStatus("failed");
        when(novelChapterBll.getChangeChapter(2, "prev",2)).thenReturn(bean);
        
        //调用函数
        boolean resultActual = novelService.toListPrevStatus( new ArrayList<>());
        //断言结果
        assertEquals(resultActual, resultExpect);
    }
    
  //获取章节预览
    @Test
    public void getChapterPage_whentoListPrevStatusIStrue_thenReturnGetChapterPageSuccess() throws Exception {
        // 参数
        Integer novelCode = 1;
        Integer startChapterNum = 1;
        Integer endChapterNum = 1;
        String startTime = "2017-04-17";
        String endTime = "2017-05-17";
        String status = "1";
        Integer pageIndex = 1;
        Integer pageSize = 1;
        List<Map<String, Object>> list = new  ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("novel_id", 2);
        map.put("chapter_num", 2);
        list.add(map);
        // 期望结果
        String resultExpect = "获取小说章节列表成功";
        // mock过程
        NovelChapterBean chapterBean = new NovelChapterBean();
        chapterBean.setStatus("test");
        
        NovelChapterBean novelChapterBean2 = new NovelChapterBean();
        novelChapterBean2.setNovelId(2);
        novelChapterBean2.setChapterNum(2);
        novelChapterBean2.setStatus("unreviewd");

        when(novelChapterBll.getChapterList(novelCode,startChapterNum,endChapterNum, startTime, "2017-05-18",status,pageIndex,pageSize)).thenReturn(list);
        when(novelChapterBll.getChapterListCount(novelCode,startChapterNum,endChapterNum,  startTime, "2017-05-18",status)).thenReturn(0);
        when(novelChapterBll.getChangeChapter(2,"prev",2)).thenReturn(novelChapterBean2);

        // 调用函数
        String resultActual = novelService.getChapterPage(novelCode, startChapterNum, endChapterNum, startTime, endTime, status, pageIndex, pageSize)
                .getMessage();
        // 断言结果
        assertEquals(resultActual, resultExpect);
    }
    //获取章节预览
    @Test
    public void getChapterPage_whentoListPrevStatusISfailed_thenReturnGetChapterPageSuccess() throws Exception {
        // 参数
        Integer novelCode = 1;
        Integer startChapterNum = 1;
        Integer endChapterNum = 1;
        String startTime = "2017-04-17";
        String endTime = "2017-05-17";
        String status = "1";
        Integer pageIndex = 1;
        Integer pageSize = 1;
        List<Map<String, Object>> list = new  ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("novel_id", 2);
        map.put("chapter_num", 2);
        list.add(map);
        // 期望结果
        String resultExpect = "获取小说章节列表成功";
        // mock过程
        NovelChapterBean chapterBean = new NovelChapterBean();
        chapterBean.setStatus("test");
        
        NovelChapterBean novelChapterBean2 = new NovelChapterBean();
        novelChapterBean2.setNovelId(2);
        novelChapterBean2.setChapterNum(2);
        novelChapterBean2.setStatus("failed");
        
        when(novelChapterBll.getChapterList(novelCode,startChapterNum,endChapterNum, startTime, "2017-05-18",status,pageIndex,pageSize)).thenReturn(list);
        when(novelChapterBll.getChapterListCount(novelCode,startChapterNum,endChapterNum,  startTime, "2017-05-18",status)).thenReturn(0);
        when(novelChapterBll.getChangeChapter(2,"prev",2)).thenReturn(novelChapterBean2);
        
        // 调用函数
        String resultActual = novelService.getChapterPage(novelCode, startChapterNum, endChapterNum, startTime, endTime, status, pageIndex, pageSize)
                .getMessage();
        // 断言结果
        assertEquals(resultActual, resultExpect);
    }
    //获取章节预览
    @Test
    public void getChapterPage_whentoListPrevStatusISpassed_thenReturnGetChapterPageSuccess() throws Exception {
        // 参数
        Integer novelCode = 1;
        Integer startChapterNum = 1;
        Integer endChapterNum = 1;
        String startTime = "2017-04-17";
        String endTime = "2017-05-17";
        String status = "1";
        Integer pageIndex = 1;
        Integer pageSize = 1;
        List<Map<String, Object>> list = new  ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("novel_id", 2);
        map.put("chapter_num", 2);
        list.add(map);
        // 期望结果
        String resultExpect = "获取小说章节列表成功";
        // mock过程
        NovelChapterBean chapterBean = new NovelChapterBean();
        chapterBean.setStatus("test");
        
        NovelChapterBean novelChapterBean2 = new NovelChapterBean();
        novelChapterBean2.setNovelId(2);
        novelChapterBean2.setChapterNum(2);
        novelChapterBean2.setStatus("passed");
        
        when(novelChapterBll.getChapterList(novelCode,startChapterNum,endChapterNum, startTime, "2017-05-18",status,pageIndex,pageSize)).thenReturn(list);
        when(novelChapterBll.getChapterListCount(novelCode,startChapterNum,endChapterNum,  startTime, "2017-05-18",status)).thenReturn(0);
        when(novelChapterBll.getChangeChapter(2,"prev",2)).thenReturn(novelChapterBean2);
        
        // 调用函数
        String resultActual = novelService.getChapterPage(novelCode, startChapterNum, endChapterNum, startTime, endTime, status, pageIndex, pageSize)
                .getMessage();
        // 断言结果
        assertEquals(resultActual, resultExpect);
    }
    //获取章节预览
    @Test
    public void getChapterPage_whentoListPrevStatusISnull_thenReturnGetChapterPageSuccess() throws Exception {
        // 参数
        Integer novelCode = 1;
        Integer startChapterNum = 1;
        Integer endChapterNum = 1;
        String startTime = "2017-04-17";
        String endTime = "2017-05-17";
        String status = "1";
        Integer pageIndex = 1;
        Integer pageSize = 1;
        List<Map<String, Object>> list = new  ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("novel_id", 2);
        map.put("chapter_num", 2);
        list.add(map);
        // 期望结果
        String resultExpect = "获取小说章节列表成功";
        // mock过程
        NovelChapterBean chapterBean = new NovelChapterBean();
        chapterBean.setStatus("test");
        
        NovelChapterBean novelChapterBean2 = new NovelChapterBean();
        novelChapterBean2.setNovelId(2);
        novelChapterBean2.setChapterNum(2);
        novelChapterBean2.setStatus("passed");
        
        when(novelChapterBll.getChapterList(novelCode,startChapterNum,endChapterNum, startTime, "2017-05-18",status,pageIndex,pageSize)).thenReturn(list);
        when(novelChapterBll.getChapterListCount(novelCode,startChapterNum,endChapterNum,  startTime, "2017-05-18",status)).thenReturn(0);
        when(novelChapterBll.getChangeChapter(2,"prev",2)).thenReturn(null);
        
        // 调用函数
        String resultActual = novelService.getChapterPage(novelCode, startChapterNum, endChapterNum, startTime, endTime, status, pageIndex, pageSize)
                .getMessage();
        // 断言结果
        assertEquals(resultActual, resultExpect);
    }
    //章节编辑   当章节不存在 返回 节不存在或不可编辑
    @Test
    public void chapterEditor_whenNovelChapterBeanISnull_thenReturnChapterFail() throws Exception {
         
        // 期望结果
        String resultExpect = "章节不存在或不可编辑";
        // mock过程
        
        NovelChapterBean chapter = new NovelChapterBean();
        chapter.setNovelId(2);
        chapter.setChapterNum(2);
        chapter.setStatus("passed");
        chapter.setCode(2);
        NovelBean novel = new NovelBean();
        novel.setCode(2);
        novel.setUserId(2);
        
        when(novelChapterBll.get(chapter.getCode())).thenReturn(null);
        
        // 调用函数
        String resultActual = novelService.chapterEditor(chapter, novel).getMessage();
        // 断言结果
        assertEquals(resultActual, resultExpect);
    }
    //章节编辑   当章节不存在 返回 节不存在或不可编辑
    @Test
    public void chapterEditor_whenNovelBeanISnull_thenReturnChapterFail() throws Exception {
        
        // 期望结果
        String resultExpect = "章节不存在或不可编辑";
        // mock过程
        
        NovelChapterBean chapter = new NovelChapterBean();
        chapter.setNovelId(2);
        chapter.setChapterNum(2);
        chapter.setStatus("passed");
        chapter.setCode(2);
        NovelBean novel = new NovelBean();
        novel.setCode(2);
        novel.setUserId(2);
        
        when(novelChapterBll.get(chapter.getCode())).thenReturn(chapter);
        when(novelBll.get(chapter.getNovelId())).thenReturn(null);
        
        // 调用函数
        String resultActual = novelService.chapterEditor(chapter, novel).getMessage();
        // 断言结果
        assertEquals(resultActual, resultExpect);
    }
    //章节编辑   当章节不存在 返回 节不存在或不可编辑
    @Test
    public void chapterEditor2_whenNovelBeanISnull_thenReturnChapterFail() throws Exception {
        
        // 期望结果
        String resultExpect = "小说不存在或不可编辑";
        // mock过程
        
        NovelChapterBean chapter = new NovelChapterBean();
        chapter.setNovelId(2);
        chapter.setChapterNum(2);
        chapter.setStatus("unreviewd");
        chapter.setCode(2);
        NovelBean novel = new NovelBean();
        novel.setCode(2);
        novel.setUserId(2);
        
        when(novelChapterBll.get(chapter.getCode())).thenReturn(chapter);
        when(novelBll.get(chapter.getNovelId())).thenReturn(null);
        
        // 调用函数
        String resultActual = novelService.chapterEditor(chapter, novel).getMessage();
        // 断言结果
        assertEquals(resultActual, resultExpect);
    }
    @Test
    public void chapterEditor2_whenCountIS_0_thenReturnChapterFail() throws Exception {
        
        // 期望结果
        String resultExpect = "存在同名的章节名称,请更换";
        // mock过程
        
        NovelChapterBean chapter = new NovelChapterBean();
        chapter.setNovelId(2);
        chapter.setChapterNum(2);
        chapter.setStatus("unreviewd");
        chapter.setCode(2);
        chapter.setTitle("2");
        NovelBean novel = new NovelBean();
        novel.setCode(2);
        novel.setUserId(2);
        novel.setName("2");
        
        when(novelChapterBll.get(chapter.getCode())).thenReturn(chapter);
        when(novelBll.get(chapter.getNovelId())).thenReturn(novel);
        when(novelChapterBll.selectCount(novel.getUserId(),
            novel.getName(), chapter.getTitle())).thenReturn(2);
        
        // 调用函数
        String resultActual = novelService.chapterEditor(chapter, novel).getMessage();
        // 断言结果
        assertEquals(resultActual, resultExpect);
    }
    @Test
    public void chapterEditor2_whenCountISTrue_thenReturnChapterSuccess() throws Exception {
        
        // 期望结果
        String resultExpect = "编辑成功";
        // mock过程
        
        NovelChapterBean chapter = new NovelChapterBean();
        chapter.setNovelId(2);
        chapter.setChapterNum(2);
        chapter.setStatus("unreviewd");
        chapter.setCode(2);
        chapter.setTitle("2");
        NovelBean novel = new NovelBean();
        novel.setCode(2);
        novel.setUserId(2);
        novel.setName("2");
        
        novel.setCategoryId(1);
        novel.setCover("2");
        novel.setDescription("2");
        novel.setStatus("2");
        
        chapter.setTitle("2");
        chapter.setContent("2");
        
        when(novelChapterBll.get(chapter.getCode())).thenReturn(chapter);
        when(novelBll.get(chapter.getNovelId())).thenReturn(novel);
        when(novelChapterBll.selectCount(novel.getUserId(),
            novel.getName(), chapter.getTitle())).thenReturn(0);
        
        // 调用函数
        String resultActual = novelService.chapterEditor(chapter, novel).getMessage();
        // 断言结果
        assertEquals(resultActual, resultExpect);
    }
}
