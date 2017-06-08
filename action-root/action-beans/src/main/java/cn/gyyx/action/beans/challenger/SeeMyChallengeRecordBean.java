/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年7月14日下午9:29:30
 * 版本号：v1.0
 * 本类主要用途叙述：查看我的挑战纪录实体
 */

package cn.gyyx.action.beans.challenger;

import java.util.List;

public class SeeMyChallengeRecordBean {

	private int count;

	private List<String> roleNames;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<String> getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}

}
