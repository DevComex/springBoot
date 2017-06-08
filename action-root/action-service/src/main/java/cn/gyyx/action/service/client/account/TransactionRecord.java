
package cn.gyyx.action.service.client.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionRecord complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionRecord">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="TransactionID" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="ItemName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ItemAmount" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="GoldCoins" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="SilverCoins" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionRecord")
public class TransactionRecord {

    @XmlAttribute(name = "TransactionID", required = true)
    protected int transactionID;
    @XmlAttribute(name = "ItemName")
    protected String itemName;
    @XmlAttribute(name = "ItemAmount", required = true)
    protected int itemAmount;
    @XmlAttribute(name = "GoldCoins", required = true)
    protected int goldCoins;
    @XmlAttribute(name = "SilverCoins", required = true)
    protected int silverCoins;

    /**
     * Gets the value of the transactionID property.
     * 
     */
    public int getTransactionID() {
        return transactionID;
    }

    /**
     * Sets the value of the transactionID property.
     * 
     */
    public void setTransactionID(int value) {
        this.transactionID = value;
    }

    /**
     * Gets the value of the itemName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the value of the itemName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemName(String value) {
        this.itemName = value;
    }

    /**
     * Gets the value of the itemAmount property.
     * 
     */
    public int getItemAmount() {
        return itemAmount;
    }

    /**
     * Sets the value of the itemAmount property.
     * 
     */
    public void setItemAmount(int value) {
        this.itemAmount = value;
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

}
