package cn.gyyx.playwd.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyyx.playwd.beans.playwd.ArticleBean;
import cn.gyyx.playwd.beans.playwd.CategoryBean;
import cn.gyyx.playwd.beans.playwd.Collect;
import cn.gyyx.playwd.beans.playwd.TimeLineBean;
import cn.gyyx.playwd.bll.ArticleBll;
import cn.gyyx.playwd.bll.TimeLineBll;
import cn.gyyx.playwd.dao.playwd.CollectDao;
/**
 * 
  * <p>
  *   收藏service
  * </p>
  *  
  * @author lihu
  * @since 0.0.1
 */
@Service
public class CollectService {
    
    @Autowired
    CollectDao collectdao;
    /**
     * 图文Bll
     */
    @Autowired
    private ArticleBll articleBll;
    @Autowired
    private TimeLineBll timeLineBll;
     
    
    /**
     * 
      * <p>
      *   获取用户收藏
      * </p>
      *
      * @action
      *    lihu 2017年3月7日 下午7:00:09 描述
      *
      * @param userId
     * @param endSize 
     * @param startSize 
      * @return List<Collect>
     */
    public List<Collect> getCollectByUserId(Integer userId, int startSize, int endSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId",userId);
        map.put("startSize",startSize);
        map.put("endSize",endSize);
        return collectdao.getCollectByUserId(map);
    }

    public int getCollectCountByUserId(Integer userId) {
        return collectdao.getCollectCountByUserId(userId);
    }

    public void cancelCollect(Integer userId, Integer code, String account) {
        //取消收藏
        collectdao.updateStatusByCode(code);
        //插入分享日志
        ArticleBean article = articleBll.getArticle(code);
        timeLineBll.insert(userId, article.getUserId(), CategoryBean.CategoryType.article.Value(), 
                        code, TimeLineBean.OperateType.collect.Value(),
                        new Date(), "取消收藏+1");
    }

   

    public void setCollectdao(CollectDao collectdao) {
        this.collectdao = collectdao;
    }

   

    public void setArticleBll(ArticleBll articleBll) {
        this.articleBll = articleBll;
    }

   

    public void setTimeLineBll(TimeLineBll timeLineBll) {
        this.timeLineBll = timeLineBll;
    }

}
