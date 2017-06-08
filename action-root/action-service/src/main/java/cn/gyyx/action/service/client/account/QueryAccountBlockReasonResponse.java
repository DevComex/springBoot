
package cn.gyyx.action.service.client.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QueryAccountBlockReasonResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QueryAccountBlockReasonResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://asktao.gbits.com/}queryAccountBlockReasonResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryAccountBlockReasonResponse", propOrder = {
    "_return"
})
public class QueryAccountBlockReasonResponse {

    @XmlElement(name = "return")
    protected QueryAccountBlockReasonResult _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link QueryAccountBlockReasonResult }
     *     
     */
    public QueryAccountBlockReasonResult getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryAccountBlockReasonResult }
     *     
     */
    public void setReturn(QueryAccountBlockReasonResult value) {
        this._return = value;
    }

}
