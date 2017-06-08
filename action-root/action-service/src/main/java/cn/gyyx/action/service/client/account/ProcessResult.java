
package cn.gyyx.action.service.client.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProcessResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcessResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="Description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ErrorCode" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="RequestIndex" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessResult")
@XmlSeeAlso({
    IsAdultResult.class,
    QueryResult.class,
    GetAccountSettingAutoLockStateResult.class,
    QueryTransactionHistoryResult.class,
    CoinPasswordStateResult.class,
    GetProtectCardStateResult.class,
    GetLockStateResult.class,
    QueryAccountLockStatusResult.class,
    QueryAccountBlockHistoryResult.class,
    BlockRecord.class
})
public class ProcessResult {

    @XmlAttribute(name = "Description")
    protected String description;
    @XmlAttribute(name = "ErrorCode", required = true)
    protected int errorCode;
    @XmlAttribute(name = "RequestIndex", required = true)
    protected int requestIndex;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     */
    public void setErrorCode(int value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the requestIndex property.
     * 
     */
    public int getRequestIndex() {
        return requestIndex;
    }

    /**
     * Sets the value of the requestIndex property.
     * 
     */
    public void setRequestIndex(int value) {
        this.requestIndex = value;
    }

}
