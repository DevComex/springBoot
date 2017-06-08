/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-6
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画上传分类信息的BLL实现
 */
package cn.gyyx.wd.wanjia.cartoon.bll.upload.impl;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyyx.log.sdk.GYYXLoggerFactory;
import cn.gyyx.wd.wanjia.cartoon.beans.WanwdCategory;
import cn.gyyx.wd.wanjia.cartoon.bll.upload.ManHuaCategoryInfoBLL;
import cn.gyyx.wd.wanjia.cartoon.dao.WanwdCategoryMapper;
@Service
public class ManHuaCategoryInfoBLLImpl implements ManHuaCategoryInfoBLL{
	private static final Logger logger = GYYXLoggerFactory.getLogger(ManHuaCategoryInfoBLLImpl.class);
	@Autowired
	private  WanwdCategoryMapper wanwdCategoryMapper;
	@Override
	public List<WanwdCategory> findAll() {
		
		return wanwdCategoryMapper.selectAllCategoryName();
	}

}
