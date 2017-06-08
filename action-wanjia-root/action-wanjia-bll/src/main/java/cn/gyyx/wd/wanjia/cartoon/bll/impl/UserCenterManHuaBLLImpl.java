/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-1-3
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画专区，用户中心，漫画部分内容bll
 */
package cn.gyyx.wd.wanjia.cartoon.bll.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdUser;
import cn.gyyx.wd.wanjia.cartoon.bll.UserCenterManHuaBLL;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaBookMapper;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaMapper;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdMessageMapper;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdUserMapper;

@Service
public class UserCenterManHuaBLLImpl implements UserCenterManHuaBLL {
	@Autowired
	private WanwdManhuaBookMapper bookMapper;
	@Autowired
	private WanwdMessageMapper messageMapper;
	@Autowired
	private WanwdManhuaMapper manhuaMapper;
	@Autowired
	private WanwdUserMapper userMapper;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.UserCenterManHuaBLL#selectUserUpload(java.
	 * util.Map)
	 */
	@Override
	public List<Map<String, Object>> selectUserUpload(Map<String, Object> map) {
		return bookMapper.selectUserUpload(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.gyyx.wd.wanjia.cartoon.bll.UserCenterManHuaBLL#
	 * selectUserUploadTotalSize(java.util.Map)
	 */
	@Override
	public Integer selectUserUploadTotalSize(Map<String, Object> map) {
		return bookMapper.selectUserUploadTotalSize(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.UserCenterManHuaBLL#selectManagerReply(java
	 * .util.Map)
	 */
	@Override
	public List<Map<String, Object>> selectManagerReply(Map<String, Object> map) {

		return messageMapper.selectMagagerManHuaReply(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.gyyx.wd.wanjia.cartoon.bll.UserCenterManHuaBLL#
	 * selectManagerReplyTotalSize(java.util.Map)
	 */
	@Override
	public Integer selectManagerReplyTotalSize(Map<String, Object> map) {
		return messageMapper.selectMagagerManHuaReplyTotalSize(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.UserCenterManHuaBLL#selectRecommendManHua(
	 * java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> selectRecommendManHua(Map<String, Object> map) {
		return manhuaMapper.selectRecommendManHua(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.gyyx.wd.wanjia.cartoon.bll.UserCenterManHuaBLL#
	 * selectRecommendManHuaTotalSize(java.util.Map)
	 */
	@Override
	public int selectRecommendManHuaTotalSize(Map<String, Object> map) {
		return manhuaMapper.selectRecommendManHuaTotalSize(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.bll.UserCenterManHuaBLL#selectUserCollection(
	 * java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> selectUserCollection(Map<String, Object> map) {
		return manhuaMapper.selectUserCollection(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.gyyx.wd.wanjia.cartoon.bll.UserCenterManHuaBLL#
	 * selectUserCollectionTotalSize(java.util.Map)
	 */
	@Override
	public int selectUserCollectionTotalSize(Map<String, Object> map) {
		return manhuaMapper.selectUserCollectionTotalSize(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.gyyx.wd.wanjia.cartoon.bll.UserCenterManHuaBLL#
	 * selectMessageAboutManHua(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> selectMessageAboutManHua(Map<String, Object> map) {
		return messageMapper.selectMessageAboutManHua(map);
	}

	/*
	 * s(non-Javadoc)
	 * 
	 * @see cn.gyyx.wd.wanjia.cartoon.bll.UserCenterManHuaBLL#
	 * selectMessageAboutManHuaTotalSize(java.util.Map)
	 */
	@Override
	public int selectMessageAboutManHuaTotalSize(Map<String, Object> map) {
		return messageMapper.selectMessageAboutManHuaTotalSize(map);
	}
	@Override
	public List<WanwdUser> getRoleInfo(int userId) {
		return userMapper.selectByUserId(userId);
	}
}
