/*
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：炫舞吧积分活动
 * @作者：王雷
 * @联系方式：wanglei@gyyx.cn
 * @创建时间： 2015年9月6日
 * @版本号：V1.214
 * @本类主要用途描述：素材首页显示信息类
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.xwbcreditsluckydraw;

import java.util.ArrayList;
import java.util.List;

public class ResultIndexShowBean {
	//素材信息集合
	List<MaterialShowBean> materialShowList = new ArrayList<MaterialShowBean>();
	//条数
	Integer length;

	public List<MaterialShowBean> getMaterialShowList() {
		return materialShowList;
	}

	public void setMaterialShowList(List<MaterialShowBean> materialShowList) {
		this.materialShowList = materialShowList;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public ResultIndexShowBean(List<MaterialShowBean> materialShowList,
			Integer length) {
		super();
		this.materialShowList = materialShowList;
		this.length = length;
	}

	public ResultIndexShowBean() {
		super();
	}
	
	
}
