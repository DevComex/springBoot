package cn.gyyx.playwd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gyyx.playwd.agent.WDGameAgent;
import cn.gyyx.playwd.beans.common.ResultBean;
import cn.gyyx.playwd.beans.playwd.ArticleBean;
import cn.gyyx.playwd.bll.ArticleBll;

/**
 * 
  * <p>
  *   图文service
  * </p>
  *  
  * @author lihu
  * @since 0.0.1
 */
@Service
public class ArticleUploadService {
    
    @Autowired
    ArticleBll articleBll;

    /**
     * 
      * <p>
      *   添加图文
      * </p>
      *
      * @action
      *    lihu 2017年3月7日 下午4:49:43 描述
      *
      * @param articleBean void
     * @param user 
     */
    @Transactional
    public ResultBean<Object>  instertArticle(ArticleBean articleBean, String account,
            Integer userId) {
        ResultBean<Object> resultBean = new ResultBean<>();
      //检测标题
        boolean isExist =articleBll.findCountByTitle(articleBean.getTitle());
        if(isExist){
            resultBean.setProperties(false, "标题已存在", null);
            return resultBean;
        } 
       
        articleBll.insertArticle(articleBean,account,userId);
        resultBean.setProperties(true, "添加文章成功", null);
       return  resultBean;
        //userInfoService.insertUserInfo(userId,account,articleBean.getAuthor(),articleBean.getServerId(),articleBean.getServerName(),articleBean.getNetId(),articleBean.getNetName());
    }
    public boolean checktitle(String title) {
        return   articleBll.findCountByTitle(title);
    }
   
  
    public void setArticleBll(ArticleBll articleBll) {
        this.articleBll = articleBll;
    }
 

}
