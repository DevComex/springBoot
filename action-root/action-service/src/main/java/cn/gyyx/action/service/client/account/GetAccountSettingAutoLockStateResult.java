
package cn.gyyx.action.service.client.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetAccountSettingAutoLockStateResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetAccountSettingAutoLockStateResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://asktao.gbits.com/}ProcessResult">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="AccountSettingAutoLockState" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="StateDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetAccountSettingAutoLockStateResult")
public class GetAccountSettingAutoLockStateResult
    extends ProcessResult
{

    @XmlAttribute(name = "AccountSettingAutoLockState", required = true)
    protected int accountSettingAutoLockState;
    @XmlAttribute(name = "StateDescription")
    protected String stateDescription;

    /**
     * Gets the value of the accountSettingAutoLockState property.
     * 
     */
    public int getAccountSettingAutoLockState() {
        return accountSettingAutoLockState;
    }

    /**
     * Sets the value of the accountSettingAutoLockState property.
     * 
     */
    public void setAccountSettingAutoLockState(int value) {
        this.accountSettingAutoLockState = value;
    }

    /**
     * Gets the value of the stateDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateDescription() {
        return stateDescription;
    }

    /**
     * Sets the value of the stateDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateDescription(String value) {
        this.stateDescription = value;
    }

}
