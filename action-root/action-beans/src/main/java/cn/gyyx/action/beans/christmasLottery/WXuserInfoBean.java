package cn.gyyx.action.beans.christmasLottery;

import java.util.Date;

public class WXuserInfoBean {
			private int code;
			private String openId;
			private int jifen;
			private Date createTime;
			private Date updateTime;
			private String weixin_type;
			public int getCode() {
				return code;
			}
			public void setCode(int code) {
				this.code = code;
			}
			public String getOpenId() {
				return openId;
			}
			public void setOpenId(String openId) {
				this.openId = openId;
			}
			public int getJifen() {
				return jifen;
			}
			public void setJifen(int jifen) {
				this.jifen = jifen;
			}
			public Date getCreateTime() {
				return createTime;
			}
			public void setCreateTime(Date createTime) {
				this.createTime = createTime;
			}
			public Date getUpdateTime() {
				return updateTime;
			}
			public void setUpdateTime(Date updateTime) {
				this.updateTime = updateTime;
			}
			public String getWeixin_type() {
				return weixin_type;
			}
			public void setWeixin_type(String weixin_type) {
				this.weixin_type = weixin_type;
			}
			@Override
			public String toString() {
				return "WXuserInfoBean [code=" + code + ", openId=" + openId + ", jifen=" + jifen + ", createTime="
						+ createTime + ", updateTime=" + updateTime + ", weixin_type=" + weixin_type + "]";
			}
			
	
}
