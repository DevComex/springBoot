 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月2日上午11:22:42
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.beans.playwd;

import java.io.Serializable;
import java.util.List;

/**
 * 奖励表
 * @author lidudi
 *
 */
public class PrizeBean implements Serializable  {
	
	private Integer code;

	//奖励名
    private String name;

    //所属分类
    private String contentType;

    private Boolean isDelete;

    private static final long serialVersionUID = 1L;
    
    /**
     *关系id 
     */
    private Integer relCode;

	/**
     * 奖品类
     */
    private List<ItemBean> itemList;
 
    public List<ItemBean> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemBean> itemList) {
		this.itemList = itemList;
	}

	public Integer getRelCode() {
		return relCode;
	}

	public void setRelCode(Integer relCode) {
		this.relCode = relCode;
	}

	public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}
