package cn.gyyx.action.beans.noviceoa;

/*
 * 新手卡生成渠道实体类
 */
public class NoviceChannelBean {

	private Integer code;

	private String mediaName;

	public NoviceChannelBean(Integer code, String mediaName) {
		this.code = code;
		this.mediaName = mediaName;
	}

	public NoviceChannelBean() {
		super();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	@Override
	public String toString() {
		return "NoviceChannelBean{" + "code=" + code + ", mediaName='" + mediaName + '\'' + "}";
	}

}
