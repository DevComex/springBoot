package cn.gyyx.playwd.bll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.NovelChapterBean;
import cn.gyyx.playwd.dao.playwd.NovelChapterDao;

/**
 * 
  * <p>
  *   NovelChapterBll描述
  * </p>
  *  
  * @author chenglong
  * @since 0.0.1
 */
@Component
public class NovelChapterBll {
    @Autowired
    NovelChapterDao novelChapterDao;

    /**
     * 插入
     */
    public int insert(NovelChapterBean record) {
        return novelChapterDao.insert(record);
    }

    /**
     * 修改
     */
    public int update(NovelChapterBean record) {
        return novelChapterDao.update(record);
    }
    /**
     * 修改
     */
    public int updateByCodeSelective(NovelChapterBean record) {
        return novelChapterDao.updateByPrimaryKeySelective(record);
    }

    /**
     * get
     */
    public NovelChapterBean get(Integer code) {
        return novelChapterDao.get(code);
    }

    /**
     * 查询用户下同一小说章节名称是否重复
     */
    public int selectCount(Integer userId, String name, String title) {
        NovelChapterBean chapter = new NovelChapterBean();
        chapter.setTitle(title);
        chapter.setUserId(userId);
        chapter.setName(name);
        return novelChapterDao.selectCountJoinNovel(chapter);
    }
/**
 * 
  * <p>
  *    获取目录
  * </p>
  *
  * @action
  *    lihu 2017年5月8日 下午4:32:55 描述
  *
  * @param novelCode
  * @return ResultBean<String>
 */
    public List<Map<String, Object>> novelCatalogue(Integer novelCode) {
        return novelChapterDao.getNovelCatalogue(novelCode);
    }

public List<Map<String, Object>> getPassedNovel(Integer novelCode) {
    return novelChapterDao.getPassedNovel(novelCode);
}

public List<Map<String, Object>> getChapterList(Integer novelCode,
        Integer startChapterNum, Integer endChapterNum, String startTime, String endTime, String status, Integer pageIndex,
        Integer pageSize) {
    int startSize = (pageIndex - 1) * pageSize;
    int endSize =   pageSize;
    Map<String, Object> map = new HashMap<>();
    map.put("novelCode", novelCode);
    map.put("endTime", endTime);
    map.put("startTime", startTime);
    map.put("startChapterNum", startChapterNum);
    map.put("endChapterNum", endChapterNum);
    map.put("status", status);
    map.put("startSize", startSize);
    map.put("endSize", endSize);
    return novelChapterDao.getChapterList(map);
}

public int getChapterListCount(Integer novelCode, Integer startChapterNum, Integer endChapterNum, String startTime,
        String endTime, String status) {
    Map<String, Object> map = new HashMap<>();
    map.put("novelCode", novelCode);
    map.put("startChapterNum", startChapterNum);
    map.put("endChapterNum", endChapterNum);
    map.put("endTime", endTime);
    map.put("startTime", startTime);
    map.put("status", status);
    return  novelChapterDao.getChapterListCount(map);
}

public NovelChapterBean getChangeChapter(Integer novelId, String change, Integer chapterNum) {
    Map<String, Object> map = new HashMap<>();
    map.put("novelId", novelId);
    map.put("change", change);
    map.put("chapterNum", chapterNum);
    return novelChapterDao.getChangeChapter(map);
}

public boolean reviewChapter(Integer chapterCode, String status) {
    Map<String, Object> map = new HashMap<>();
    map.put("chapterCode", chapterCode);
    map.put("status", status);
    return novelChapterDao.reviewChapter(map)>0;
}

public Map<String , Object> chapterInfo(Integer chapterCode,String status) {
    return novelChapterDao.chapterInfo(chapterCode, status);
}
    
}
