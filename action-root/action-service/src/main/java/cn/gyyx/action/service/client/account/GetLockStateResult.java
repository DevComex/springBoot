
package cn.gyyx.action.service.client.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetLockStateResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetLockStateResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://asktao.gbits.com/}ProcessResult">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="LockState" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="StateDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetLockStateResult")
public class GetLockStateResult
    extends ProcessResult
{

    @XmlAttribute(name = "LockState", required = true)
    protected int lockState;
    @XmlAttribute(name = "StateDescription")
    protected String stateDescription;

    /**
     * Gets the value of the lockState property.
     * 
     */
    public int getLockState() {
        return lockState;
    }

    /**
     * Sets the value of the lockState property.
     * 
     */
    public void setLockState(int value) {
        this.lockState = value;
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
