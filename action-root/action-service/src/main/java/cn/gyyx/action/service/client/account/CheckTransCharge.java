
package cn.gyyx.action.service.client.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CheckTransCharge complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CheckTransCharge">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Account" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChargeType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="GoldCoins" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="SilverCoins" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CheckValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CheckTransCharge", propOrder = {
    "transactionID",
    "account",
    "chargeType",
    "goldCoins",
    "silverCoins",
    "checkValue"
})
public class CheckTransCharge {

    @XmlElement(name = "TransactionID")
    protected String transactionID;
    @XmlElement(name = "Account")
    protected String account;
    @XmlElement(name = "ChargeType")
    protected int chargeType;
    @XmlElement(name = "GoldCoins")
    protected int goldCoins;
    @XmlElement(name = "SilverCoins")
    protected int silverCoins;
    @XmlElement(name = "CheckValue")
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
