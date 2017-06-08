package cn.gyyx.playwd.oa.viewmodel;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class RecommendContentEditViewModel {
	
	//编号	
	@NotBlank(message="invalid-code|编号不能为空")
	@Pattern(regexp="^([1-9][0-9]*)$",message="invalid-code|编号格式不正确")
	private String code;
	//标题
	@NotBlank(message="invalid-title|标题不能为空")
	@Length(max=20,message="invalid-title|标题长度不能大于20个汉字")
	private String title;
	//内容url
	@NotBlank(message="invalid-url|内容地址不能为空")
	@Length(max=500,message="invalid-url|内容地址不能大于100个汉字")
	@Pattern(regexp="[a-zA-z]+://[^\\s]*",message="invalid-url|内容地址格式不正确")
	private String url;
	//图片url
	@NotBlank(message="invalid-cover|图片地址不能为空")
	@Length(max=500,message="invalid-cover|图片地址不能大于200个汉字")
	@Pattern(regexp="^http://[^\\s]+",message="invalid-cover|图片地址格式不正确")
	private String cover;	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
}  
