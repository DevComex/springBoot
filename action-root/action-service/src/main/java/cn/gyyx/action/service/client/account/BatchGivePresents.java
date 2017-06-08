
package cn.gyyx.action.service.client.account;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BatchGivePresents complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BatchGivePresents">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accounts" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="characterIDs" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="dists" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "BatchGivePresents", propOrder = {
    "accounts",
    "characterIDs",
    "dists",
    "encourageType",
    "expiredTime",
    "memo",
    "checkValue"
})
public class BatchGivePresents {

    protected List<String> accounts;
    protected List<String> characterIDs;
    protected List<String> dists;
    protected String encourageType;
    protected String expiredTime;
    protected String memo;
    protected String checkValue;

    /**
     * Gets the value of the accounts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accounts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccounts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAccounts() {
        if (accounts == null) {
            accounts = new ArrayList<String>();
        }
        return this.accounts;
    }

    /**
     * Gets the value of the characterIDs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the characterIDs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCharacterIDs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCharacterIDs() {
        if (characterIDs == null) {
            characterIDs = new ArrayList<String>();
        }
        return this.characterIDs;
    }

    /**
     * Gets the value of the dists property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dists property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDists().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDists() {
        if (dists == null) {
            dists = new ArrayList<String>();
        }
        return this.dists;
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
