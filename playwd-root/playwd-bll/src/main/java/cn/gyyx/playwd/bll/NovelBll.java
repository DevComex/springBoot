package cn.gyyx.playwd.bll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.playwd.NovelBean;
import cn.gyyx.playwd.dao.playwd.NovelDao;

/**
 * 
  * <p>
  *   NovelBll描述
  * </p>
  *  
  * @author chenglong
  * @since 0.0.1
 */
@Component
public class NovelBll {
    @Autowired
    NovelDao novelDao;

    /**
     * 插入
     */
    public int insert(NovelBean record) {
        return novelDao.insert(record);
    }

    /**
     * 修改
     */
    public int update(NovelBean record) {
        return novelDao.update(record);
    }

    /**
     * get
     */
    public NovelBean get(Integer code) {
        return novelDao.get(code);
    }

    /**
     * 根据用户和名称查询小说
     */
    public NovelBean selectNovel(Integer userId,String name) {
        NovelBean novel = new NovelBean();
        novel.setUserId(userId);
        novel.setName(name);
        return novelDao.selectNovel(novel);
    }

    /**
     * 根据用户和状态查询list
     */
    public List<NovelBean> selectNovelList(Integer userId, String status) {
        NovelBean novel = new NovelBean();
        novel.setUserId(userId);
        novel.setStatus(status);
        return novelDao.selectNovelList(novel);
    }

  
/**
 * 
  * <p>
  *    后台获取小说列表
  * </p>
  *
  * @action
  *    lihu 2017年5月8日 上午9:57:21 描述
  *
  * @param name
  * @return List<Map<String,Object>>
 */
    public List<Map<String, Object>> selectAllNovelList(String name,String isFinished,Integer isShow,Integer categoryId
            ,String status,Integer pageIndex,Integer pageSize) {
        int startSize = (pageIndex - 1) * pageSize;
        int endSize =   pageSize;
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("isFinished", isFinished);
        map.put("isShow", isShow);
        map.put("categoryId", categoryId);
        map.put("status", status);
        map.put("startSize", startSize);
        map.put("endSize", endSize);
        
        return novelDao.selectAllNovelList(map);
    }

public int selectAllNovelListCount(String name, String isFinished,
        Integer isShow, Integer categoryId, String status) {
    Map<String, Object> map = new HashMap<>();
    map.put("name", name);
    map.put("isFinished", isFinished);
    map.put("isShow", isShow);
    map.put("categoryId", categoryId);
    map.put("status", status);   
    return novelDao.selectAllNovelListCount(map);
}

public NovelBean selectOfficialNovel(String name) {
    return novelDao.selectGuanNovel(name);
}

public boolean updateShowAndhidden(NovelBean bean) {
    return novelDao.updateShowAndhidden(bean) >0;
}

public int getNovelManagementCount(Integer recommendSlotId) {
    
    return novelDao.getNovelManagementCount(recommendSlotId) ;
}

public List<Map<String, Object>> getNovelManagementList(Integer recommendSlotId,
        int startSize, int endSize) {
    
    return novelDao.getNovelManagementList(recommendSlotId,startSize,endSize) ;
}
    
}
