
package cn.gyyx.action.service.client.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GiftSilverCoinsEx complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GiftSilverCoinsEx">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transactionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="account" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="chargeType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numberOfSilverCoins" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="giftReasonDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="checkValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serverCaption" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GiftSilverCoinsEx", propOrder = {
    "transactionID",
    "account",
    "chargeType",
    "numberOfSilverCoins",
    "giftReasonDesc",
    "checkValue",
    "serverCaption"
})
public class GiftSilverCoinsEx {

    protected String transactionID;
    protected String account;
    protected int chargeType;
    protected int numberOfSilverCoins;
    protected String giftReasonDesc;
    protected String checkValue;
    protected String serverCaption;

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
     * Gets the value of the chargeType property.
     * 
     */
    public int getChargeType() {
        return chargeType;
    }

    /**
     * Sets the value of the chargeType property.
     * 
     */
    public void setChargeType(int value) {
        this.chargeType = value;
    }

    /**
     * Gets the value of the numberOfSilverCoins property.
     * 
     */
    public int getNumberOfSilverCoins() {
        return numberOfSilverCoins;
    }

    /**
     * Sets the value of the numberOfSilverCoins property.
     * 
     */
    public void setNumberOfSilverCoins(int value) {
        this.numberOfSilverCoins = value;
    }

    /**
     * Gets the value of the giftReasonDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGiftReasonDesc() {
        return giftReasonDesc;
    }

    /**
     * Sets the value of the giftReasonDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGiftReasonDesc(String value) {
        this.giftReasonDesc = value;
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

}
