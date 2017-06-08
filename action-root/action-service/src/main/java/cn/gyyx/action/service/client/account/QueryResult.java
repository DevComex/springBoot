
package cn.gyyx.action.service.client.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QueryResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QueryResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://asktao.gbits.com/}ProcessResult">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="GoldCoins" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="SilverCoins" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryResult")
public class QueryResult
    extends ProcessResult
{

    @XmlAttribute(name = "GoldCoins", required = true)
    protected int goldCoins;
    @XmlAttribute(name = "SilverCoins", required = true)
    protected int silverCoins;

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
