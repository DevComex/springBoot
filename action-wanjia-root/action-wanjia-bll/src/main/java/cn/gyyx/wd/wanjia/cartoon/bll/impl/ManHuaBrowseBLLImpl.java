/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-12
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画专区，漫画浏览的Bll接口实现类
 */
package cn.gyyx.wd.wanjia.cartoon.bll.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdCategory;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdCollect;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhua;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaBook;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaGood;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaPage;
import cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdCategoryMapper;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdCollectMapper;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaBookMapper;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaGoodMapper;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaMapper;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaPageMapper;

@Service
public class ManHuaBrowseBLLImpl implements ManHuaBrowseBLL {
	@Autowired
	private WanwdManhuaPageMapper wanwdManhuaPageMapper;
	@Autowired
	private WanwdManhuaBookMapper wanwdManhuaBookMapper;
	@Autowired
	private WanwdManhuaMapper wanwdManhuaMapper;
	@Autowired
	private WanwdManhuaGoodMapper wanwdManhuaGoodMapper;
	@Autowired
	private WanwdCollectMapper wanwdCollectMapper;
	@Autowired
	private WanwdCategoryMapper wanwdCategoryMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL#getManHuaBrowseInfo(java.
	 * util. Map)
	 */
	@Override
	public Map<String, Object> getManHuaBrowseInfo(Map<String, Object> map) {
		Map<String, Object> map2 = wanwdManhuaBookMapper.selectManhuaBrowseInfo(map);
		if (map2 == null) {
			return null;
		}
		map.remove("pictureNum");
		int upBookNum = wanwdManhuaBookMapper.selectUpBookNum(map);
		int downBookNum = wanwdManhuaBookMapper.selectDownBookNum(map);
		int pageSize = wanwdManhuaPageMapper.selectPageSize((Integer) map2.get("code"));
		map2.put("upBookNum", upBookNum);
		map2.put("downBookNum", downBookNum);
		map2.put("pageSize", pageSize);
		return map2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL#getManHuaMaxBookNum(int)
	 */
	@Override
	public int getManHuaMaxBookNum(int manhuaCode) {
		return wanwdManhuaBookMapper.selectMaxBookNumByManHuaCodeWithStatus(manhuaCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL#getManHuaByCode(java.lang.
	 * Integer)
	 */
	@Override
	public WanwdManhua getManHuaByCode(Integer code) {
		return wanwdManhuaMapper.selectByPrimaryKey(code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL#getManHuaGoodCount(java.
	 * lang.Integer)
	 */
	@Override
	public int getManHuaGoodCount(Integer manhuaCode) {
		return wanwdManhuaGoodMapper.getCountByManhuaCode(manhuaCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL#getGoodStatus(cn.gyyx.wd.
	 * wanjia.cartoon.beans.WanwdManhuaGood)
	 */
	@Override
	public int getGoodStatus(WanwdManhuaGood manhuaGood) {
		return wanwdManhuaGoodMapper.getGoodStatus(manhuaGood);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL#selectCollectionStatus(cn.
	 * gyyx.wd.wanjia.cartoon.beans.WanwdCollect)
	 */
	@Override
	public WanwdCollect selectCollectionStatus(WanwdCollect collect) {
		return wanwdCollectMapper.selectCollectionStatusAndReadLog(collect);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL#addGoodStatus(cn.gyyx.wd.
	 * wanjia.cartoon.beans.WanwdCollect)
	 */
	@Override
	public int addGoodStatus(WanwdManhuaGood good) {
		return wanwdManhuaGoodMapper.insertSelective(good);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL#selectPageByPrimaryKey(
	 * java. lang.Integer)
	 */
	@Override
	public WanwdManhuaPage selectPageByPrimaryKey(Integer pageCode) {
		return wanwdManhuaPageMapper.selectByPrimaryKey(pageCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL#selectBookByPrimaryKey(java
	 * .lang.Integer)
	 */
	@Override
	public WanwdManhuaBook selectBookByPrimaryKey(Integer bookCode) {
		return wanwdManhuaBookMapper.selectByPrimaryKey(bookCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL#readCountPlusOne(java.lang.
	 * Integer)
	 */
	@Override
	public int readCountPlusOne(Integer manhuaCode) {
		return wanwdManhuaMapper.updateReadCountPlusOne(manhuaCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL#writeReadlog(cn.gyyx.wd.
	 * wanjia.cartoon.beans.WanwdCollect)
	 */
	@Override
	public int writeReadlog(Integer collectCode, Integer pageCode) {
		WanwdCollect collect = new WanwdCollect();
		collect.setCode(collectCode);
		collect.setReadLogCode(pageCode);
		return wanwdCollectMapper.updateReadLogByPrimaryKey(collect);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL#getCategoryByPirmaryKey(
	 * java.lang.Integer)
	 */
	@Override
	public WanwdCategory getCategoryByPirmaryKey(Integer categoryCode) {
		return wanwdCategoryMapper.selectByPrimaryKey(categoryCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL#getMinBookNum(java.lang.
	 * Integer)
	 */
	@Override
	public Integer getMinBookNum(Integer manhuaCode) {
		return wanwdManhuaBookMapper.selectMinBookNum(manhuaCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.ManHuaBrowseBLL#selectManhuaInfo(java.lang.
	 * Integer)
	 */
	@Override
	public Map<String, Object> selectManhuaInfo(Integer code) {

		return wanwdManhuaMapper.selectManhuaInfo(code);
	}

}
