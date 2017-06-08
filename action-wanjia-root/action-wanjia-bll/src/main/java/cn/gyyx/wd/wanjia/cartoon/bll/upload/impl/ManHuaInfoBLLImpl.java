/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-6
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画上传漫画信息的BLL实现
 */
package cn.gyyx.wd.wanjia.cartoon.bll.upload.impl;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;

import cn.gyyx.core.auth.UserInfo;
import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhua;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaBook;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdManhuaPage;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdUser;
import cn.gyyx.wd.wanjia.cartoon.bll.upload.ManHuaInfoBLL;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaBookMapper;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaMapper;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdManhuaPageMapper;

@Service
public class ManHuaInfoBLLImpl implements ManHuaInfoBLL {
	private static final Logger logger = GYYXLoggerFactory.getLogger(ManHuaInfoBLLImpl.class);
	@Autowired
	private WanwdManhuaMapper manhuaMapper;
	@Autowired
	private WanwdManhuaBookMapper manhuaBookMapper;
	@Autowired
	private WanwdManhuaPageMapper pageMapper;

	@Override
	public List<WanwdManhua> findAllUnfinishedManhuaNameByAuthor(UserInfo user) {
		return manhuaMapper.selectManhuaUnfinishedByAuthorId(user.getUserId());
	}

	@Override
	public Integer addManhua(WanwdManhua manhua) {
		return manhuaMapper.insertReturnCode(manhua);
	}

	@Override
	public Integer updateManhua(WanwdManhua manhua) {
		return manhuaMapper.updateByPrimaryKeySelective(manhua);
	}

	@Override
	public WanwdManhua findManhuaByTitle(String title) {
		return manhuaMapper.selectManhuaByTitle(title);
	}

	@Override
	public Integer findManhuaNextBookNum(WanwdManhua manhua) {
		return manhuaBookMapper.selectMaxBookNumByManHuaCode(manhua.getCode())+ 1;
	}

	@Override
	public void insertPages(WanwdManhuaPage page) {
		pageMapper.insertPic(page);
	}

	@Override
	public Integer insertBook(WanwdManhuaBook book) {
		return manhuaBookMapper.insertReturnCode(book);
	}
	@Override
	public WanwdManhua selectByPrimaryKey(Integer code) {
		return manhuaMapper.selectByPrimaryKey(code);
	}

}
