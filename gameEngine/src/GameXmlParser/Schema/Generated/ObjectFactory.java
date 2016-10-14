
package GameXmlParser.Schema.Generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import java.math.BigInteger;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the GameBoardXmlParser.Schema.Generated package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Blocks_QNAME = new QName("", "Blocks");
    private final static QName _PlayerType_QNAME = new QName("", "PlayerType");
    private final static QName _Columns_QNAME = new QName("", "Columns");
    private final static QName _Value_QNAME = new QName("", "value");
    private final static QName _Name_QNAME = new QName("", "Name");
    private final static QName _Rows_QNAME = new QName("", "Rows");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: GameBoardXmlParser.Schema.Generated
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GameDescriptor }
     */
    public GameDescriptor createGameDescriptor() {
        return new GameDescriptor();
    }

    /**
     * Create an instance of {@link GameDescriptor.Board }
     */
    public GameDescriptor.Board createGameDescriptorBoard() {
        return new GameDescriptor.Board();
    }

    /**
     * Create an instance of {@link Slices }
     */
    public Slices createSlices() {
        return new Slices();
    }

    /**
     * Create an instance of {@link Slice }
     */
    public Slice createSlice() {
        return new Slice();
    }

    /**
     * Create an instance of {@link Square }
     */
    public Square createSquare() {
        return new Square();
    }

    /**
     * Create an instance of {@link GameDescriptor.MultiPlayers }
     */
    public GameDescriptor.MultiPlayers createGameDescriptorMultiPlayers() {
        return new GameDescriptor.MultiPlayers();
    }

    /**
     * Create an instance of {@link GameDescriptor.DynamicMultiPlayers }
     */
    public GameDescriptor.DynamicMultiPlayers createGameDescriptorDynamicMultiPlayers() {
        return new GameDescriptor.DynamicMultiPlayers();
    }

    /**
     * Create an instance of {@link Solution }
     */
    public Solution createSolution() {
        return new Solution();
    }

    /**
     * Create an instance of {@link Players }
     */
    public Players createPlayers() {
        return new Players();
    }

    /**
     * Create an instance of {@link Player }
     */
    public Player createPlayer() {
        return new Player();
    }

    /**
     * Create an instance of {@link GameDescriptor.Board.Definition }
     */
    public GameDescriptor.Board.Definition createGameDescriptorBoardDefinition() {
        return new GameDescriptor.Board.Definition();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "Blocks")
    public JAXBElement<String> createBlocks(String value) {
        return new JAXBElement<String>(_Blocks_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "PlayerType")
    public JAXBElement<String> createPlayerType(String value) {
        return new JAXBElement<String>(_PlayerType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "Columns")
    public JAXBElement<BigInteger> createColumns(BigInteger value) {
        return new JAXBElement<BigInteger>(_Columns_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "value")
    public JAXBElement<String> createValue(String value) {
        return new JAXBElement<String>(_Value_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "Name")
    public JAXBElement<String> createName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     */
    @XmlElementDecl(namespace = "", name = "Rows")
    public JAXBElement<BigInteger> createRows(BigInteger value) {
        return new JAXBElement<BigInteger>(_Rows_QNAME, BigInteger.class, null, value);
    }

}
