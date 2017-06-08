
package cn.gyyx.action.service.client.account;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BlockRecord complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BlockRecord">
 *   &lt;complexContent>
 *     &lt;extension base="{http://asktao.gbits.com/}ProcessResult">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="OperationIntermal" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="OperationReason" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="OperationTime" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="OperationType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Operator" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BlockRecord")
public class BlockRecord
    extends ProcessResult
{

    @XmlAttribute(name = "OperationIntermal")
    protected String operationIntermal;
    @XmlAttribute(name = "OperationReason")
    protected String operationReason;
    @XmlAttribute(name = "OperationTime")
    protected String operationTime;
    @XmlAttribute(name = "OperationType")
    protected String operationType;
    @XmlAttribute(name = "Operator")
    protected String operator;

    /**
     * Gets the value of the operationIntermal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationIntermal() {
        return operationIntermal;
    }

    /**
     * Sets the value of the operationIntermal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationIntermal(String value) {
        this.operationIntermal = value;
    }

    /**
     * Gets the value of the operationReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationReason() {
        return operationReason;
    }

    /**
     * Sets the value of the operationReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationReason(String value) {
        this.operationReason = value;
    }

    /**
     * Gets the value of the operationTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationTime() {
        return operationTime;
    }

    /**
     * Sets the value of the operationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationTime(String value) {
        this.operationTime = value;
    }

    /**
     * Gets the value of the operationType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationType() {
        return operationType;
    }

    /**
     * Sets the value of the operationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationType(String value) {
        this.operationType = value;
    }

    /**
     * Gets the value of the operator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Sets the value of the operator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperator(String value) {
        this.operator = value;
    }

}
