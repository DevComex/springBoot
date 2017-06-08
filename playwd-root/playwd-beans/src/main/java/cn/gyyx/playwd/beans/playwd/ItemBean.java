 /**
    * -------------------------------------------------------------------------
    * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
    * @版权所有：北京光宇在线科技有限责任公司
    * @项目名称：玩家天地
    * @作者：李杜迪
    * @联系方式：lidudi@gyyx.cn
    * @创建时间：2017年3月7日下午5:45:05
    * @版本号：1.0.0
    *-------------------------------------------------------------------------
    */
package cn.gyyx.playwd.beans.playwd;

import java.io.Serializable;

/**
 * 奖品表
 * @author lidudi
 *
 */
public class ItemBean implements Serializable {
	
    private Integer code;

    /**
     * 奖品名字
     */
    private String name;

    /**
     * 奖品值 （为人民币发奖时候使用 其他奖品同name）
     */
    private String value;

    /**
     * 分类
     */
    private String type;

    /**
     * 游戏内发奖码
     */
    private String itemCode;

    private Boolean isDelete;

    private static final long serialVersionUID = 1L;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
    
    /**
	 * 分类类型
	 */
	public enum ItemType{
		wdgift("wdgift","问道虚拟物品"),
		rmb("rmb","现金奖励");
		
		private String value;
		private String desc;

		private ItemType(String value,String desc) {
			this.value = value;
			this.desc = desc;
		}

		public String Value() {
			return value;
		}

		public String Desc() {
			return desc;
		}
		
	}
}
