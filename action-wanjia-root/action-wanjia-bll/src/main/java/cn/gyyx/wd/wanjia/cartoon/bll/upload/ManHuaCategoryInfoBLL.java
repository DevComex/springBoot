/**
 * --------------------------------------------------- 
 * 版权所有：北京光宇在线科技有限责任公司
 * 作者：才帅
 * 联系方式：caishuai@gyyx.cn 
 * 创建时间：2016-12-6
 * 版本号：v1.0
 * 本类主要用途叙述：玩家天地，漫画上传分类信息的BLL
 */
package cn.gyyx.wd.wanjia.cartoon.bll.upload;

import java.util.List;

import cn.gyyx.wd.wanjia.cartoon.beans.WanwdCategory;

public interface ManHuaCategoryInfoBLL {

	/**
	 * 查询所有分类名
	 * 
	 * @return
	 */
	public List<WanwdCategory> findAll();

}
