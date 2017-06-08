package beans;

import java.io.Serializable;

public class RecommendSlotBean implements Serializable {
    private Integer code;

    private String slotGroup;

    private String slot;

    private Boolean hasPicture;

    private Boolean isDelete;

    private static final long serialVersionUID = 1L;

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
        this.slotGroup = slotGroup == null ? null : slotGroup.trim();
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot == null ? null : slot.trim();
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
}