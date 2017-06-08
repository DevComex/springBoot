/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-1-3
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画专区，用户中心，漫画部分内容Service实现类
 */
package cn.gyyx.wd.wanjia.cartoon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdUser;
import cn.gyyx.wd.wanjia.cartoon.bll.UserCenterManHuaBLL;
import cn.gyyx.wd.wanjia.cartoon.service.UserCenterManHuaService;

@Service
public class UserCenterManHuaServiceImpl implements UserCenterManHuaService {
	@Autowired
	private UserCenterManHuaBLL userCenterManHuaBLL;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.service.UserCenterManHuaService#getUpload(java.
	 * lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public Map<String, Object> getUpload(String status, Integer resourceType, Integer userId, Integer pageIndex,
			Integer pageSize) {
		int start = (pageIndex - 1) * pageSize + 1;
		int end = pageIndex * pageSize;
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		List<Map<String, Object>> list = null;
		map.put("status", status);
		map.put("resourceType", resourceType);
		map.put("userId", userId);
		map.put("start", start);
		map.put("end", end);
		if (status.equals("RECOMMEND")) {
			list = userCenterManHuaBLL.selectRecommendManHua(map);
			Integer totalSize = userCenterManHuaBLL.selectRecommendManHuaTotalSize(map);
			resultMap.put("DataSet", list);
			resultMap.put("Count", totalSize);
			return resultMap;
		}
		if (status.equals("DEFAULT")) {
			map.put("status", null);
		}
		list = userCenterManHuaBLL.selectUserUpload(map);
		Integer totalSize = userCenterManHuaBLL.selectUserUploadTotalSize(map);
		resultMap.put("DataSet", list);
		resultMap.put("Count", totalSize);
		return resultMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.service.UserCenterManHuaService#getMessage(java
	 * .lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Map<String, Object> getMessage(Integer userId, Integer resourceType, Integer pageIndex, Integer pageSize) {
		int start = (pageIndex - 1) * pageSize + 1;
		int end = pageIndex * pageSize;
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		List<Map<String, Object>> list = null;
		map.put("resourceType", resourceType);
		map.put("userId", userId);
		map.put("start", start);
		map.put("end", end);
		list = userCenterManHuaBLL.selectMessageAboutManHua(map);
		Integer totalSize = userCenterManHuaBLL.selectMessageAboutManHuaTotalSize(map);
		resultMap.put("DataSet", list);
		resultMap.put("Count", totalSize);
		return resultMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.service.UserCenterManHuaService#getManagerReply
	 * (java.lang.Integer, java.lang.Integer, java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public Map<String, Object> getManagerReply(Integer userId, Integer resourceType, Integer pageIndex,
			Integer pageSize) {
		int start = (pageIndex - 1) * pageSize + 1;
		int end = pageIndex * pageSize;
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		List<Map<String, Object>> list = null;
		map.put("resourceType", resourceType);
		map.put("userId", userId);
		map.put("start", start);
		map.put("end", end);
		list = userCenterManHuaBLL.selectManagerReply(map);
		Integer totalSize = userCenterManHuaBLL.selectManagerReplyTotalSize(map);
		resultMap.put("DataSet", list);
		resultMap.put("Count", totalSize);
		return resultMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.gyyx.wd.wanjia.cartoon.service.UserCenterManHuaService#getCollection(
	 * java.lang.Integer, java.lang.Integer, java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public Map<String, Object> getCollection(Integer userId, Integer resourceType, Integer pageIndex,
			Integer pageSize) {
		int start = (pageIndex - 1) * pageSize + 1;
		int end = pageIndex * pageSize;
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		List<Map<String, Object>> list = null;
		map.put("resourceType", resourceType);
		map.put("userId", userId);
		map.put("start", start);
		map.put("end", end);
		list = userCenterManHuaBLL.selectUserCollection(map);
		Integer totalSize = userCenterManHuaBLL.selectUserCollectionTotalSize(map);
		resultMap.put("DataSet", list);
		resultMap.put("Count", totalSize);
		return resultMap;
	}

	@Override
	public List<WanwdUser> getRoleInfo(int userId) {
		return userCenterManHuaBLL.getRoleInfo(userId);
	}
}
