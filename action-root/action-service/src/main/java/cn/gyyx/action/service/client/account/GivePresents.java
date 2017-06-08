
package cn.gyyx.action.service.client.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GivePresents complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GivePresents">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="account" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dist" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="characterID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="encourageType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="expiredTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="memo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="checkValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GivePresents", propOrder = {
    "account",
    "dist",
    "characterID",
    "encourageType",
    "expiredTime",
    "memo",
    "checkValue"
})
public class GivePresents {

    protected String account;
    protected String dist;
    protected String characterID;
    protected String encourageType;
    protected String expiredTime;
    protected String memo;
    protected String checkValue;

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
     * Gets the value of the dist property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDist() {
        return dist;
    }

    /**
     * Sets the value of the dist property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDist(String value) {
        this.dist = value;
    }

    /**
     * Gets the value of the characterID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharacterID() {
        return characterID;
    }

    /**
     * Sets the value of the characterID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharacterID(String value) {
        this.characterID = value;
    }

    /**
     * Gets the value of the encourageType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncourageType() {
        return encourageType;
    }

    /**
     * Sets the value of the encourageType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncourageType(String value) {
        this.encourageType = value;
    }

    /**
     * Gets the value of the expiredTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpiredTime() {
        return expiredTime;
    }

    /**
     * Sets the value of the expiredTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpiredTime(String value) {
        this.expiredTime = value;
    }

    /**
     * Gets the value of the memo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMemo() {
        return memo;
    }

    /**
     * Sets the value of the memo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMemo(String value) {
        this.memo = value;
    }

    /**
     * Gets the value of the checkValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckValue() {
        return checkValue;
    }

    /**
     * Sets the value of the checkValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckValue(String value) {
        this.checkValue = value;
    }

}
