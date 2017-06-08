/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间：2014年12月10日 
 * @版本号：v1.0
 * @本类主要用途描述：对novice_card_receive_log表操作的接口类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.dao.novicecard;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.gyyx.action.beans.novicecard.NoviceCardReceiveLogBean;
import cn.gyyx.action.beans.novicecard.NoviceOaBean;

public interface IReceiveLogMapper {

	/**
	 * 日期：2014年12月12日 作者：王雷 方法名：selectLogAccount 参 数：NoviceCardReceiveLogBean
	 * receiveLogPara 返回值：List<NoviceCardReceiveLogBean>
	 * 描述：通过userId、gameId、serverId、batchNo查询数据
	 */
	public int selectLogAccount(
			NoviceCardReceiveLogBean receiveLogPara);

	/**
	 * @Title: setNoviceCardReceiveLog
	 * @Author: jianghan
	 * @date 2014年12月12日 下午2:32:09
	 * @Description: TODO 向新手卡日志表中插入
	 * @param noviceCardBean
	 * @return boolean
	 * 
	 */
	public boolean setNoviceCardReceiveLog(
			NoviceCardReceiveLogBean noviceCardReceiveLogBean);

	/**
	 * 
	 * @param param
	 */
	public void spNoviceCardReceiveItem(NoviceCardReceiveLogBean param);

	/**
	 * @Title: spNoviceCardReceiveLog
	 * @Author: jianghan
	 * @date 2014年12月13日 下午7:11:12
	 * @Description: TODO 插入新手卡日誌表，存储过程
	 * @param param
	 * 
	 */
	public void spNoviceCardReceiveLog(NoviceCardReceiveLogBean param);

	/**
	 * 
	 * @日期：2014年12月25日
	 * @Title: spNoviceCardReceiveLogV3
	 * @Description: TODO 插入二维码新手卡日志表 存储过程
	 * @param NoviceCardReceiveLog
	 */

	public void spNoviceCardReceiveLogV3(
			NoviceCardReceiveLogBean NoviceCardReceiveLog);
	
	public List<NoviceOaBean> getReceiveLogByAccountAndTimeAndLikeGiftPageName(
			@Param("account") String account,@Param("timeStr") String timeStr,@Param("hdType") String hdType);

	public List<NoviceOaBean> getSendToGameLogByAccountAndTimeAndLikeGiftPageName(
			@Param("account") String account,@Param("timeStr") String timeStr,@Param("hdType") String hdType);

	public List<NoviceOaBean> getNoviceServerList(@Param("batchNo") String batchNo,
			@Param("activityId") String activityId);
	
	public List<Map<String, String>> getNoviceReceiveCountByMonthAndLikeGiftPackage(@Param("month") String month,@Param("hdType")String hdType,@Param("serverId")int serverId);

	public void deleteNoviceCardServer(@Param("code") int code);

	public void deleteActionServerConfig(@Param("code") int code);

	public int getNoviceCardServerCountByCodeAndBatchNo(@Param("serverId") int serverId,@Param("batchNo") int batchNo);

	public void insertNoviceCardServer(NoviceOaBean bean);

}
