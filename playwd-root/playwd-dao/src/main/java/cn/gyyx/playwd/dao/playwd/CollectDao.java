package cn.gyyx.playwd.dao.playwd;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.playwd.beans.playwd.Collect;


public interface CollectDao {

    List<Collect> getCollectByUserId(Map<String, Object> map);

    int getCollectCountByUserId(Integer userId);
	 
	/**
	 * 插入
	 * 
	 * @param bean
	 * @return
	 */
	int insert(Collect bean);
	/**
	 * 
	  * <p>
	  *    取消收藏
	  * </p>
	  *
	  * @action
	  *    lihu 2017年4月18日 下午5:34:14 描述
	  *
	  * @param code
	  * @return int
	 */
	int updateStatusByCode(Integer code);

    int getUserCollectCount(@Param("userId")Integer userId, @Param("code")int code);
}