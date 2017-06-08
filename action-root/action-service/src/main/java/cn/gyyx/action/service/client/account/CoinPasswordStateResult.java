
package cn.gyyx.action.service.client.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CoinPasswordStateResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CoinPasswordStateResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://asktao.gbits.com/}ProcessResult">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="CoinPasswordState" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="StateDescription" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CoinPasswordStateResult")
public class CoinPasswordStateResult
    extends ProcessResult
{

    @XmlAttribute(name = "CoinPasswordState", required = true)
    protected int coinPasswordState;
    @XmlAttribute(name = "StateDescription")
    protected String stateDescription;

    /**
     * Gets the value of the coinPasswordState property.
     * 
     */
    public int getCoinPasswordState() {
        return coinPasswordState;
    }

    /**
     * Sets the value of the coinPasswordState property.
     * 
     */
    public void setCoinPasswordState(int value) {
        this.coinPasswordState = value;
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
