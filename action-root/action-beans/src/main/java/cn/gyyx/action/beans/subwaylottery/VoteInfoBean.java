/**
 * -------------------------------------------------------------------------
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：subwaylottery
 * @作者：范永良
 * @联系方式：fanyongliang@gyyx.cn
 * @创建时间： 2015年09月10日
 * @版本号：
 * @本类主要用途描述：地铁投票数据Result
 *-------------------------------------------------------------------------
 */
package cn.gyyx.action.beans.subwaylottery;

public class VoteInfoBean {
	// 红色阵营投票总数
	private int redSideCount;
	// 蓝色阵营投票总数
	private int blueSideCount;
	// 红色阵营线路投票数集合
	private int[] redSideValue;
	// 蓝色阵营线路投票数集合
	private int[] blueSideValue;
	// 用户红色阵营投票信息集合
	private boolean[] redSideBoolean;
	// 用户蓝色阵营投票信息集合
	private boolean[] blueSideBoolean;

	public int getRedSideCount() {
		return redSideCount;
	}

	public void setRedSideCount(int redSideCount) {
		this.redSideCount = redSideCount;
	}

	public int getBlueSideCount() {
		return blueSideCount;
	}

	public void setBlueSideCount(int blueSideCount) {
		this.blueSideCount = blueSideCount;
	}

	public int[] getRedSideValue() {
		return redSideValue;
	}

	public void setRedSideValue(int[] redSideValue) {
		this.redSideValue = redSideValue;
	}

	public int[] getBlueSideValue() {
		return blueSideValue;
	}

	public void setBlueSideValue(int[] blueSideValue) {
		this.blueSideValue = blueSideValue;
	}

	public boolean[] getRedSideBoolean() {
		return redSideBoolean;
	}

	public void setRedSideBoolean(boolean[] redSideBoolean) {
		this.redSideBoolean = redSideBoolean;
	}

	public boolean[] getBlueSideBoolean() {
		return blueSideBoolean;
	}

	public void setBlueSideBoolean(boolean[] blueSideBoolean) {
		this.blueSideBoolean = blueSideBoolean;
	}

	public VoteInfoBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VoteInfoBean(int redSideCount, int blueSideCount,
			int[] redSideValue, int[] blueSideValue) {
		super();
		this.redSideCount = redSideCount;
		this.blueSideCount = blueSideCount;
		this.redSideValue = redSideValue;
		this.blueSideValue = blueSideValue;
	}

	public VoteInfoBean(boolean[] redSideBoolean, boolean[] blueSideBoolean) {
		super();
		this.redSideBoolean = redSideBoolean;
		this.blueSideBoolean = blueSideBoolean;
	}

}
