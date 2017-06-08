
package cn.gyyx.action.service.client.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AutomaticLock complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AutomaticLock">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="account" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="securityType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="options" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AutomaticLock", propOrder = {
    "account",
    "securityType",
    "options"
})
public class AutomaticLock {

    protected String account;
    protected int securityType;
    protected int options;

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
     * Gets the value of the options property.
     * 
     */
    public int getOptions() {
        return options;
    }

    /**
     * Sets the value of the options property.
     * 
     */
    public void setOptions(int value) {
        this.options = value;
    }

}
