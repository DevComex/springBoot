/**
 * --------------------------------------------------- 
 *  版权所有：北京光宇在线科技有限责任公司
 * 作者：王宇飞 
 * 联系方式：wangyufei@gyyx.cn 
 * 创建时间：2016年2月17日上午10:00:51
 * 版本号：v1.0
 * 本类主要用途叙述：申请信息的实体
 */

package cn.gyyx.action.beans.positionApply;

import java.sql.Date;

public class ApplyInfoBean {
	// 主键
	private int code;
	// 申请者姓名
	private String name;
	// 申请者电话
	private String phone;
	// qq
	private String qq;
	// 性别
	private String sex;
	// 推荐者姓名
	private String introducer;
	// 推荐者邮箱
	private String introducerEmail;
	// 申请职位
	private String position;
	// 申请时间
	private Date creatTime;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIntroducer() {
		return introducer;
	}

	public void setIntroducer(String introducer) {
		this.introducer = introducer;
	}

	public String getIntroducerEmail() {
		return introducerEmail;
	}

	public void setIntroducerEmail(String introducerEmail) {
		this.introducerEmail = introducerEmail;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public ApplyInfoBean(int code, String name, String phone, String qq,
			String sex, String introducer, String introducerEmail,
			String position) {
		this.code = code;
		this.name = name;
		this.phone = phone;
		this.qq = qq;
		this.sex = sex;
		this.introducer = introducer;
		this.introducerEmail = introducerEmail;
		this.position = position;
	}

	public ApplyInfoBean() {
	}

	@Override
	public String toString() {
		return "ApplyInfoBean [code=" + code + ", name=" + name + ", phone="
				+ phone + ", qq=" + qq + ", sex=" + sex + ", introducer="
				+ introducer + ", introducerEmail=" + introducerEmail
				+ ", position=" + position + "]";
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public ApplyInfoBean positionGet(ApplyInfoBean applyInfoBean) {
		if (applyInfoBean.getPosition().equals("560022751")) {
			applyInfoBean.setPosition("AS3客户端开发工程师");
		} else if (applyInfoBean.getPosition().equals("560025830")) {
			applyInfoBean.setPosition("U3D客户端主程");
		} else if (applyInfoBean.getPosition().equals("560022755")) {
			applyInfoBean.setPosition("服务器端开发工程师");
		} else if (applyInfoBean.getPosition().equals("560022767")) {
			applyInfoBean.setPosition("资深场景原画师");
		} else if (applyInfoBean.getPosition().equals("560022763")) {
			applyInfoBean.setPosition("资深角色原画师");
		} else if (applyInfoBean.getPosition().equals("560022771")) {
			applyInfoBean.setPosition("特效设计师");
		} else if (applyInfoBean.getPosition().equals("560025832")) {
			applyInfoBean.setPosition("3D场景设计师");
		} else if (applyInfoBean.getPosition().equals("560025834")) {
			applyInfoBean.setPosition("3D角色设计师");
		} else if (applyInfoBean.getPosition().equals("560014472")) {
			applyInfoBean.setPosition("游戏数值策划");
		} else if (applyInfoBean.getPosition().equals("560025836")) {
			applyInfoBean.setPosition("游戏文案策划");
		} else if (applyInfoBean.getPosition().equals("560025838")) {
			applyInfoBean.setPosition("UI/UE策划");
		} else if (applyInfoBean.getPosition().equals("560022722")) {
			applyInfoBean.setPosition("linux系统工程师");
		} else if (applyInfoBean.getPosition().equals("560022759")) {
			applyInfoBean.setPosition("Mysql DBA");
		} else if (applyInfoBean.getPosition().equals("560025840")) {
			applyInfoBean.setPosition("游戏运维工程师");
		} else if (applyInfoBean.getPosition().equals("560022730")) {
			applyInfoBean.setPosition("数据库工程师");
		} else if (applyInfoBean.getPosition().equals("560022939")) {
			applyInfoBean.setPosition(".NET开发工程师");
		} else if (applyInfoBean.getPosition().equals("560024606")) {
			applyInfoBean.setPosition("Web前端工程师");
		} else if (applyInfoBean.getPosition().equals("560022689")) {
			applyInfoBean.setPosition("游戏数据分析主管");
		} else if (applyInfoBean.getPosition().equals("560018759")) {
			applyInfoBean.setPosition("客户调研分析师");
		} else if (applyInfoBean.getPosition().equals("560025842")) {
			applyInfoBean.setPosition("互联网产品策划");
		}else if (applyInfoBean.getPosition().equals("560019863")) {
			applyInfoBean.setPosition("市场策划主管/专员");
		} else if (applyInfoBean.getPosition().equals("560022685")) {
			applyInfoBean.setPosition("媒介专员");
		} else if (applyInfoBean.getPosition().equals("560012688")) {
			applyInfoBean.setPosition("商务主管/专员");
		} else if (applyInfoBean.getPosition().equals("560023058")) {
			applyInfoBean.setPosition("智能营销(新媒体)");
		} else if (applyInfoBean.getPosition().equals("560022705")) {
			applyInfoBean.setPosition("美术原画(CG影视方向)");
		} else if (applyInfoBean.getPosition().equals("560022697")) {
			applyInfoBean.setPosition("三维绑定动作师");
		} else if (applyInfoBean.getPosition().equals("560020127")) {
			applyInfoBean.setPosition("三维材质渲染");
		} else if (applyInfoBean.getPosition().equals("560011824")) {
			applyInfoBean.setPosition("高级会计");
		} else {
			applyInfoBean.setPosition("");
		}
		return applyInfoBean;
	}

}
