
package cn.gyyx.action.service.client.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GiftCoins complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GiftCoins">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transactionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="account" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="giftType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="goldCoins" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="silverCoins" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="giftReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serverCaption" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "GiftCoins", propOrder = {
    "transactionID",
    "account",
    "giftType",
    "goldCoins",
    "silverCoins",
    "giftReason",
    "serverCaption",
    "checkValue"
})
public class GiftCoins {

    protected String transactionID;
    protected String account;
    protected int giftType;
    protected int goldCoins;
    protected int silverCoins;
    protected String giftReason;
    protected String serverCaption;
    protected String checkValue;

    /**
     * Gets the value of the transactionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionID() {
        return transactionID;
    }

    /**
     * Sets the value of the transactionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionID(String value) {
        this.transactionID = value;
    }

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
     * Gets the value of the giftType property.
     * 
     */
    public int getGiftType() {
        return giftType;
    }

    /**
     * Sets the value of the giftType property.
     * 
     */
    public void setGiftType(int value) {
        this.giftType = value;
    }

    /**
     * Gets the value of the goldCoins property.
     * 
     */
    public int getGoldCoins() {
        return goldCoins;
    }

    /**
     * Sets the value of the goldCoins property.
     * 
     */
    public void setGoldCoins(int value) {
        this.goldCoins = value;
    }

    /**
     * Gets the value of the silverCoins property.
     * 
     */
    public int getSilverCoins() {
        return silverCoins;
    }

    /**
     * Sets the value of the silverCoins property.
     * 
     */
    public void setSilverCoins(int value) {
        this.silverCoins = value;
    }

    /**
     * Gets the value of the giftReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGiftReason() {
        return giftReason;
    }

    /**
     * Sets the value of the giftReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGiftReason(String value) {
        this.giftReason = value;
    }

    /**
     * Gets the value of the serverCaption property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerCaption() {
        return serverCaption;
    }

    /**
     * Sets the value of the serverCaption property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerCaption(String value) {
        this.serverCaption = value;
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
