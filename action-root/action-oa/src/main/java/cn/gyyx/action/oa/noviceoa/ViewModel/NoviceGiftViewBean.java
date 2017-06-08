/**
 * -------------------------------------------------------------------------
 * (C) Copyright Gyyx Tec Corp. 1996-2017 - All Rights Reserved
 *
 * @版权所有：北京光宇在线科技有限责任公司
 * @项目名称：action-root
 * @作者：changlu
 * @联系方式：changlu@gyyx.cn
 * @创建时间：2017/2/24 16:48
 * @版本号：0.0.1 -------------------------------------------------------------------------
 */
package cn.gyyx.action.oa.noviceoa.ViewModel;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 * 描述:礼包管理视图对象
 * </p>
 *
 * @author changlu
 * @since 0.0.1
 */
public class NoviceGiftViewBean {
    private Integer giftId;

    @NotNull(message = "批次编号不可为空")
    @Min(value = 1, message = "批次编号不合法")
    private Integer batchId;

    @NotBlank(message = "礼包名不可为空")
//    @Size(max = 1, min = 60, message = "礼包编码不超过60个字")
    private String giftName;

    @NotBlank(message = "礼包发奖编码不可为空")
//    @Size(max = 1, min = 60, message = "礼包编码不超过60个字")
    private String giftCode;

    private Boolean isDefault;
    
    @Override
    public String toString() {
        return "NoviceGiftViewBean{" +
                "giftId=" + giftId +
                ", batchId=" + batchId +
                ", giftName='" + giftName + '\'' +
                ", giftCode='" + giftCode + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }

    public Integer getGiftId() {
        return giftId;
    }

    public void setGiftId(Integer giftId) {
        this.giftId = giftId;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftCode() {
        return giftCode;
    }

    public void setGiftCode(String giftCode) {
        this.giftCode = giftCode;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}
