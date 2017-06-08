/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-19
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画专区，后台漫画管理审核的Service接口
 */
package cn.gyyx.wd.wanjia.cartoon.service;

import java.util.List;
import java.util.Map;

import cn.gyyx.wd.wanjia.ResultBean;

public interface ManHuaManageReviewService {
	/**
	 * 查询筛选条件下的所有记录
	 * 
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> getViewList(Map<String, Object> map);

	/**
	 * 将记录展示状态全部置为展示
	 * 
	 * @param list
	 * @return
	 */
	public ResultBean<String> showAll(List<Integer> list);

	/**
	 * 将记录展示状态全部置为未展示
	 * 
	 * @param list
	 * @return
	 */
	public ResultBean<String> hiddenAll(List<Integer> list);

	/**
	 * 将记录审核状态全部置为Fail
	 * 
	 * @param list
	 * @return
	 */
	public ResultBean<String> statusFailAll(List<Integer> list);

	/**
	 * 显示漫画的所有章节，单章删除也如此判定
	 * 
	 * @param bookCode
	 * @return
	 */
	List<Map<String, Object>> showAllBookForOneManhua(Integer bookCode);

	/**
	 * 显示章节的所有漫画图片和章节信息
	 * 
	 * @param bookCode
	 * @return
	 */
	Map<String, Object> getBookInfoAndPage(Integer bookCode);

	/**
	 * 审核完成，改变审核状态
	 * 
	 * @param bookCode
	 * @param name
	 * @param urls
	 * @param reply
	 * @return
	 */
	public ResultBean<String> reviewFinish(Integer bookCode, List<String> name, List<String> urls,String reply);

}
