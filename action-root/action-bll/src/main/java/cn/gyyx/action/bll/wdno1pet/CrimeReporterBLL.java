/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2014年12月15日下午2:29:20
 * 版本号：v1.0
 * 本类主要用途叙述：关于举报的业务层
 */

package cn.gyyx.action.bll.wdno1pet;

import cn.gyyx.action.beans.wdno1pet.CrimeReporterBean;
import cn.gyyx.action.dao.wdno1pet.CrimeReporterDAO;
import cn.gyyx.action.dao.wdno1pet.ICrimeReporter;

public class CrimeReporterBLL {
	/**
	 * 添加一条举报记录
	 * 
	 * @param crime
	 */
	public void addTipster(CrimeReporterBean crime) {
		ICrimeReporter icReporter = new CrimeReporterDAO();
		icReporter.insertTipster(crime);
	}

}
