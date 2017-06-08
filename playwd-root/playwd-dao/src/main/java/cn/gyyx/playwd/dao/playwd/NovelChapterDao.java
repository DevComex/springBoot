package cn.gyyx.playwd.dao.playwd;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.NovelChapterBean;

public interface NovelChapterDao {
    //插入
    int insert(NovelChapterBean record);
    //获取by id
    NovelChapterBean get(Integer code);
    //更新
    int update(NovelChapterBean record);
    //更新
    int updateByPrimaryKeySelective(NovelChapterBean record);
    //查询章节数量关联小说表
    int selectCountJoinNovel(NovelChapterBean chapter);
    List<Map<String, Object>> getNovelCatalogue(Integer novelCode);
    List<Map<String, Object>> getPassedNovel(Integer novelCode);
    int getChapterListCount(Map<String, Object> map);
    List<Map<String, Object>> getChapterList(Map<String, Object> map);
    NovelChapterBean getChangeChapter(Map<String, Object> map);
    int reviewChapter(Map<String, Object> map);
    Map<String, Object> chapterInfo(@Param("chapterCode")Integer chapterCode,@Param("status")String status);
}