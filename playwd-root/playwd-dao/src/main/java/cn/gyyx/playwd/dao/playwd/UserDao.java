package cn.gyyx.playwd.dao.playwd;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.playwd.beans.playwd.UserBean;

/**
 * 用户信息dao
 * @author chenglong
 */
public interface UserDao {

	/**
	 * 插入
	 * @param record
	 * @return
	 */
    int insert(UserBean record);

    /**
	 * 根据code获取
	 * @param record
	 * @return
	 */
    UserBean getByUserId(Integer userId);

    /**
     * 更新ByCode
     * @param record
     * @return
     */
    int updateByCode(UserBean record);
    
    /**
     * 更新ByUserId
     * @param record
     * @return
     */
    int updateByUserId(UserBean record);

	List<UserBean> getTopNOrderByUserReward(@Param("topN") int topN);
	
	/**
	 * 更新财富 
	 * @param rmb
	 * @param silverCoin
	 * @param userId
	 * @return
	 */
	int updateRmbAndSilverCoin(@Param("rmb")BigDecimal rmb,@Param("silverCoin") int silverCoin,
			@Param("userId") int userId);
}