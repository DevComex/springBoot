
package cn.gyyx.action.service.client.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SetUpdatePhoneAuth complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SetUpdatePhoneAuth">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="account" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bindTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="oper" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SetUpdatePhoneAuth", propOrder = {
    "account",
    "bindTime",
    "oper"
})
public class SetUpdatePhoneAuth {

    protected String account;
    protected String bindTime;
    protected String oper;

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
     * Gets the value of the oper property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOper() {
        return oper;
    }

    /**
     * Sets the value of the oper property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOper(String value) {
        this.oper = value;
    }

}
