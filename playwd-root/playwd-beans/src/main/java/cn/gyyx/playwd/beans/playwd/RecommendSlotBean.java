 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月1日下午3:43:01
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.beans.playwd;

import java.util.List;

/**
 * 推荐位实体
 * @author lidudi
 *
 */
public class RecommendSlotBean {
	
	private Integer code;

	//位置父集
    private String slotGroup;

    //位置
    private String slot;

    //是否需要图片
    private Boolean hasPicture;
    
    //是否删除
    private Boolean isDelete;
    private List<RecommendSlotBean> list;
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getSlotGroup() {
		return slotGroup;
	}

	public void setSlotGroup(String slotGroup) {
		this.slotGroup = slotGroup;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public Boolean getHasPicture() {
		return hasPicture;
	}

	public void setHasPicture(Boolean hasPicture) {
		this.hasPicture = hasPicture;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

    public List<RecommendSlotBean> getList() {
        return list;
    }

    public void setList(List<RecommendSlotBean> list) {
        this.list = list;
    }
	
}
