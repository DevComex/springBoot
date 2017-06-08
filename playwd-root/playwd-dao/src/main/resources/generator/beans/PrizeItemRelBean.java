package beans;

import java.io.Serializable;

public class PrizeItemRelBean implements Serializable {
    private Integer code;

    private Integer prizeId;

    private Integer itemId;

    private Boolean isDelete;

    private static final long serialVersionUID = 1L;

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
}