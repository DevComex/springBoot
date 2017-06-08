 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年4月18日下午3:03:08
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.module.viewmodule;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 增加我的消息
 * @author lidudi
 *
 */
public class AddMessageViewModel {
	
	@NotNull(message="invalid-userId|参数userId不能为null")
	public Integer userId;	
	
	@NotNull(message="invalid-contentId|参数contentId不能为null")
	public Integer contentId;
	
	@NotBlank(message="invalid-message|参数message不能为空")
	public String message;
	
	@NotBlank(message="invalid-messageType|参数messageType不能为空")
	@Pattern(regexp="\\b(pass|recommend|fail|editor)\\b",message="invalid-messageType|参数messageType不合法")
	public String messageType;
	
	@NotBlank(message="invalid-contentType|参数contentType不合法")
	@Pattern(regexp="\\b(article|manhua|picture|video)\\b",message="invalid-contentType|参数contentType不合法")
	public String contentType;
	
	@NotBlank(message="invalid-contentTitle|参数contentTitle不能为空")
	public String contentTitle;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}
}
