
package cn.gyyx.action.service.client.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IsAdultResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IsAdultResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://asktao.gbits.com/}ProcessResult">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="Adult" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IsAdultResult")
public class IsAdultResult
    extends ProcessResult
{

    @XmlAttribute(name = "Adult", required = true)
    protected boolean adult;

    /**
     * Gets the value of the adult property.
     * 
     */
    public boolean isAdult() {
        return adult;
    }

    /**
     * Sets the value of the adult property.
     * 
     */
    public void setAdult(boolean value) {
        this.adult = value;
    }

}
