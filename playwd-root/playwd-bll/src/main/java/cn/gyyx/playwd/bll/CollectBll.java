package cn.gyyx.playwd.bll;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyyx.playwd.beans.playwd.Collect;
import cn.gyyx.playwd.dao.playwd.CollectDao;

/**
 * 
  * <p>
  *   收藏Bll
  * </p>
  *  
  * @author lihu
  * @since 0.0.1
 */
@Component
public class CollectBll {
    @Autowired
    CollectDao collectDao;

    /**
	 * 插入
	 * 
	 * @param bean
	 * @return
	 */
	public int insert(String sourcesType,int sourcesCode,int userId,String userName) {
		Collect bean = new Collect();
		bean.setCreaterTime(new Date());
		bean.setIsDelete(false);
		bean.setSourcesCode(sourcesCode);
		bean.setSourcesType(sourcesType);
		bean.setUserId(userId);
		bean.setUserName(userName);
		    
		return collectDao.insert(bean);
	}
	/**
	 * 
	  * <p>
	  *    取消收藏
	  * </p>
	  *
	  * @action
	  *    lihu 2017年4月18日 下午5:35:30 描述
	  *
	  * @param code
	  * @return int
	 */
	public int updateStatus(Integer code) {
	    return collectDao.updateStatusByCode(code);
	}
	/**
	 * 
	  * <p>
	  *    获取用户收藏某个文章数
	  * </p>
	  *
	  * @action
	  *    lihu 2017年4月27日 上午11:34:32 描述
	  *
	  * @param userId
	  * @param code
	  * @return int
	 */
    public int getCollectCount(Integer userId, int code) {
        return collectDao.getUserCollectCount(userId,code);
    }
	
	
}
