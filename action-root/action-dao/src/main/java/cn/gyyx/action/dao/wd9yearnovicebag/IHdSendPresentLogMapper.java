/*------------------------------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司 
 * 作者：周忠凯 王雷
 * 联系方式：zhouzhongkai@gyyx.cn wanglei@gyyx.cn 
 * 创建时间：2015年03月25日 下午4:57:58 
 * 版本号：v1.0 
 * 本类主要用途描述： 
 * 
-------------------------------------------------------------------------*/
package cn.gyyx.action.dao.wd9yearnovicebag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gyyx.action.beans.novicecard.NoviceCardReceiveLogBean;
import cn.gyyx.action.beans.wd9yearnovicebag.HdSendPresentLogBean;

public interface IHdSendPresentLogMapper {
	
	/**
	 * 插入日志信息
	 * @param hdSendPresentLogBean
	 */
	public void insertHdSendPresentLog(HdSendPresentLogBean hdSendPresentLogBean);
	/**
	 * 日期：2014年12月12日 作者：王雷 方法名：selectLogAccount 参 数：NoviceCardReceiveLogBean
	 * 描述：通过userId、gameId、serverId、batchNo查询数据
	 */
	public int selectBagLogAccount(HdSendPresentLogBean hdSendPresentLogBean);

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
	public void spNoviceCardReceiveLog(HdSendPresentLogBean hdSendPresentLogBean);
	/**
	 * @Title: setReceiveLog
	 * @Author: jianghan
	 * @date 2014年12月13日 下午7:12:45
	 * @Description: TODO 插入新手卡日誌表，存储过程
	 * @param param
	 * 
	 */
	public  void spNoviceCardReceiveLog(HashMap<String, Object> map);
	/**
	 * 通过userId、gameId、activityId查询数据
	 * @param hdSendPresentLogBean
	 * @return
	 */
	public List<HdSendPresentLogBean> selectPresentLog(HdSendPresentLogBean hdSendPresentLogBean);
	/**
	 * 通过userId、gameId、activityId查询数据  刮刮乐
	 * @param hdSendPresentLogBean 
	 * @return
	 */
	public List<HdSendPresentLogBean> selectPresentLogByavailable(HdSendPresentLogBean hdSendPresentLogBean);
}
