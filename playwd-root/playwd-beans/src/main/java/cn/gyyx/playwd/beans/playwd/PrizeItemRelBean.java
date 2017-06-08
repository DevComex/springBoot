 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月7日下午5:50:27
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.beans.playwd;

import java.io.Serializable;

/**
 * 奖励奖品表
 * @author lidudi
 *
 */
public class PrizeItemRelBean implements Serializable {
	
    private static final long serialVersionUID = 1L;
	
    private Integer code;

    /**
     * 奖励id
     */
    private Integer prizeId;

    /**
     * 奖品id
     */
    private Integer itemId;

    private Boolean isDelete;

	private ItemBean itemBean;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(Integer prizeId) {
        this.prizeId = prizeId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

	public ItemBean getItemBean() {
		return itemBean;
	}

	public void setItemBean(ItemBean itemBean) {
		this.itemBean = itemBean;
	}
}
