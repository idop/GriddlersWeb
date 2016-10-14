
package GameXmlParser.Schema.Generated;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;


/**
 * <p>Java class for anonymous complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="row" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *       &lt;attribute name="column" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Square")
public class Square {

    @XmlAttribute(name = "row", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger row;
    @XmlAttribute(name = "column", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger column;

    /**
     * Gets the value of the row property.
     *
     * @return possible object is
     * {@link BigInteger }
     */
    public BigInteger getRow() {
        return row;
    }

    /**
     * Sets the value of the row property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setRow(BigInteger value) {
        this.row = value;
    }

    /**
     * Gets the value of the column property.
     *
     * @return possible object is
     * {@link BigInteger }
     */
    public BigInteger getColumn() {
        return column;
    }

    /**
     * Sets the value of the column property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setColumn(BigInteger value) {
        this.column = value;
    }

}
