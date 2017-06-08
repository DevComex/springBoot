package cn.gyyx.action.dao.WdHalloffame;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import cn.gyyx.action.beans.wdhalloffame.WdHalloffameBean;

public interface IWdHalloffameMapper {

	/**
	 * 查询标记
	 * 
	 * @param
	 * @return
	 */

	public int selectismark();

	/**
	 * 分组查询
	 * 
	 * @param pageSize
	 * @param start
	 * @return
	 */
	public List<WdHalloffameBean> selectbypage(@Param("pageSize") int pageSize, @Param("start") int start);

	/**
	 * 查询总数
	 * 
	 * @param 
	 * @return
	 */

	public int selecttotal();

	/**
	 * 按QQ号查询
	 * 
	 * @param qqNo
	 * @return
	 */

	public List<WdHalloffameBean> selectbyqq(@Param("qqNo") String qqNo);

	/**
	 * 按用户名查询
	 * 
	 * @param userName
	 * @return
	 */

	public List<WdHalloffameBean> selectbyuserid(@Param("userName") String userName);

	/**
	 * 按时间查询
	 * 
	 * @param stdate
	 * @param endate
	 * @return
	 */

	public List<WdHalloffameBean> selectbytime(@Param("stdate") Date stdate, @Param("endate") Date endate);

	/**
	 * 更新标记
	 * @param code
	 * @param isMark
	 * @return
	 */

	public void updateismark(@Param("code") int code, @Param("isMark") int isMark);

	/**
	 * 更新备注
	 * 
	 * @param reMark
	 * @param code
	 * @return
	 */

	public void updateremark(@Param("code") int code, @Param("reMark") String reMark);

	/**
	 * 按时间查询
	 * 
	 * @param stdate 
	 * @param endate
	 * @return
	 */

	public List<WdHalloffameBean> insertexcel(@Param("stdate") Date stdate, @Param("endate") Date endate);

	/**
	 * 插入EXCEL表
	 * 
	 * @param WdHalloffameBean
	 * @return
	 */
	public int insertexcel(List<WdHalloffameBean> WdHalloffameBean);

	/**
	 *查询game_vip_qualification表
	 * 
	 * @param userName
	 * @return
	 */
	public String selectbyusername(@Param("userName") String userName);
	/**
	 *逐条插入game_vip_qualification表
	 * 
	 * @param userName
	 * @return
	 */
	public void insertusername(@Param("userName") String userName);
	
}
