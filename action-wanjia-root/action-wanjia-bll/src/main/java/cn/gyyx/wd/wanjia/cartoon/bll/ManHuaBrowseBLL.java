/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-12
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画专区，漫画浏览的Bll接口
 */
package cn.gyyx.wd.wanjia.cartoon.bll;

import java.util.Map;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdCategory;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdCollect;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhua;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaBook;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaGood;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaPage;

public interface ManHuaBrowseBLL {
	/**
	 * 得到对应浏览的漫画界面展示的信息
	 * 
	 * @param map
	 * @return
	 */
	public Map<String,Object> getManHuaBrowseInfo(Map<String,Object> map);

	/**
	 * 得到漫画的可浏览的最大章节
	 * 
	 * @param manhuaCode
	 * @return
	 */
	public int getManHuaMaxBookNum(int manhuaCode);

	/**
	 * 得到漫画信息by manhua code
	 * 
	 * @param code
	 * @return
	 */
	public WanwdManhua getManHuaByCode(Integer code);

	/**
	 * 得到漫画点赞数by manhua code
	 * 
	 * @param manhuaCode
	 * @return
	 */
	public int getManHuaGoodCount(Integer manhuaCode);

	/**
	 * 查询漫画点赞状态
	 * 
	 * @param manhuaGood
	 * @return
	 */
	public int getGoodStatus(WanwdManhuaGood manhuaGood);

	/**
	 * 查询收藏状态
	 * 
	 * @param collect
	 * @return
	 */
	public WanwdCollect selectCollectionStatus(WanwdCollect collect);

	/**
	 * 添加点赞记录
	 * 
	 * @param good
	 * @return
	 */
	public int addGoodStatus(WanwdManhuaGood good);

	/**
	 * 查询页码code主键对应的页码信息
	 * 
	 * @param pageCode
	 * @return
	 */
	public WanwdManhuaPage selectPageByPrimaryKey(Integer pageCode);

	/**
	 * 查询章节code主键对应的章节信息
	 * 
	 * @param bookCode
	 * @return
	 */
	public WanwdManhuaBook selectBookByPrimaryKey(Integer bookCode);

	/**
	 * 阅读记录+1
	 * 
	 * @param manhuaCode
	 * @return
	 */
	public int readCountPlusOne(Integer manhuaCode);

	/**
	 * 记录浏览历史
	 * 
	 * @param collectCode
	 * @param pageCode
	 * @return
	 */
	public int writeReadlog(Integer collectCode, Integer pageCode);

	/**
	 * 获取漫画类型详情
	 * 
	 * @param categoryCode
	 * @return
	 */
	public WanwdCategory getCategoryByPirmaryKey(Integer categoryCode);

	/**
	 * 获取最小的章节
	 * 
	 * @param manhuaCode
	 * @return
	 */
	public Integer getMinBookNum(Integer manhuaCode);
	/**
	 * 查询浏览页漫画基本信息 bymanhuacode 
	 * @param code
	 * @return
	 */
	public Map<String, Object> selectManhuaInfo(Integer code);
}
