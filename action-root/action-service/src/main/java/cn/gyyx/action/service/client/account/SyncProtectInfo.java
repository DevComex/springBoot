
package cn.gyyx.action.service.client.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SyncProtectInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SyncProtectInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="account" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="securityType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="bindTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="protectValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="md5Checksum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SyncProtectInfo", propOrder = {
    "account",
    "securityType",
    "bindTime",
    "protectValue",
    "md5Checksum"
})
public class SyncProtectInfo {

    protected String account;
    protected int securityType;
    protected String bindTime;
    protected String protectValue;
    protected String md5Checksum;

    /**
     * Gets the value of the account property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccount() {
        return account;
    }

    /**
     * Sets the value of the account property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccount(String value) {
        this.account = value;
    }

    /**
     * Gets the value of the securityType property.
     * 
     */
    public int getSecurityType() {
        return securityType;
    }

    /**
     * Sets the value of the securityType property.
     * 
     */
    public void setSecurityType(int value) {
        this.securityType = value;
    }

    /**
     * Gets the value of the bindTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBindTime() {
        return bindTime;
    }

    /**
     * Sets the value of the bindTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBindTime(String value) {
        this.bindTime = value;
    }

    /**
     * Gets the value of the protectValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtectValue() {
        return protectValue;
    }

    /**
     * Sets the value of the protectValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtectValue(String value) {
        this.protectValue = value;
    }

    /**
     * Gets the value of the md5Checksum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMd5Checksum() {
        return md5Checksum;
    }

    /**
     * Sets the value of the md5Checksum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMd5Checksum(String value) {
        this.md5Checksum = value;
    }

}
