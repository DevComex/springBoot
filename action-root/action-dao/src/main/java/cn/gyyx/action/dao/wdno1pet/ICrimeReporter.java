/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月15日下午2:08:28
 * 版本号：v1.0
 * 本类主要用途叙述：关于举报的DAO层接口
 */

package cn.gyyx.action.dao.wdno1pet;

import cn.gyyx.action.beans.wdno1pet.CrimeReporterBean;

public interface ICrimeReporter {
	/**
	 * 向数据库中插入一条举报信息
	 * 
	 * @param crime
	 */
	public void insertTipster(CrimeReporterBean crime);

}
