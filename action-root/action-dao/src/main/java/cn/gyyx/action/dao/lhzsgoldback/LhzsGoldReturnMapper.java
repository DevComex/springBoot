package cn.gyyx.action.dao.lhzsgoldback;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.lhzsgoldback.LhzsGoldReturnApplyBean;
import cn.gyyx.action.beans.lhzsgoldback.LhzsGoldReturnUserBean;

/**
 * 版        权：光宇游戏
 * 作        者：ChengLong
 * 创建时间：2016年12月16日 下午1:01:14
 * 描        述：
 */
public interface LhzsGoldReturnMapper {

	public LhzsGoldReturnUserBean getUserByAccount(@Param("account") String account) ;

	public int getUserApplyCountByAccount(@Param("account") String account);

	public void insertUserApply(LhzsGoldReturnApplyBean lhzsGoldReturnApplyBean);

}
