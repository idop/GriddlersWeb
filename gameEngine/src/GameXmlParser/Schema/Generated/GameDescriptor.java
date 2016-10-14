
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
 *       &lt;sequence>
 *         &lt;element name="GameType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="SinglePlayer"/>
 *               &lt;enumeration value="MultiPlayer"/>
 *               &lt;enumeration value="DynamicMultiPlayer"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Board">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Definition">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element ref="{}Rows"/>
 *                             &lt;element ref="{}Columns"/>
 *                             &lt;element ref="{}Slices"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element ref="{}Solution"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="MultiPlayers" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}Players"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="moves" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="DynamicMultiPlayers" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="totalmoves" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                 &lt;attribute name="totalPlayers" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *                 &lt;attribute name="gametitle" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "gameType",
        "board",
        "multiPlayers",
        "dynamicMultiPlayers"
})
@XmlRootElement(name = "GameDescriptor")
public class GameDescriptor {

    @XmlElement(name = "GameType", required = true)
    protected String gameType;
    @XmlElement(name = "Board", required = true)
    protected GameDescriptor.Board board;
    @XmlElement(name = "MultiPlayers")
    protected GameDescriptor.MultiPlayers multiPlayers;
    @XmlElement(name = "DynamicMultiPlayers")
    protected GameDescriptor.DynamicMultiPlayers dynamicMultiPlayers;

    /**
     * Gets the value of the gameType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getGameType() {
        return gameType;
    }

    /**
     * Sets the value of the gameType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGameType(String value) {
        this.gameType = value;
    }

    /**
     * Gets the value of the board property.
     *
     * @return possible object is
     * {@link GameDescriptor.Board }
     */
    public GameDescriptor.Board getBoard() {
        return board;
    }

    /**
     * Sets the value of the board property.
     *
     * @param value allowed object is
     *              {@link GameDescriptor.Board }
     */
    public void setBoard(GameDescriptor.Board value) {
        this.board = value;
    }

    /**
     * Gets the value of the multiPlayers property.
     *
     * @return possible object is
     * {@link GameDescriptor.MultiPlayers }
     */
    public GameDescriptor.MultiPlayers getMultiPlayers() {
        return multiPlayers;
    }

    /**
     * Sets the value of the multiPlayers property.
     *
     * @param value allowed object is
     *              {@link GameDescriptor.MultiPlayers }
     */
    public void setMultiPlayers(GameDescriptor.MultiPlayers value) {
        this.multiPlayers = value;
    }

    /**
     * Gets the value of the dynamicMultiPlayers property.
     *
     * @return possible object is
     * {@link GameDescriptor.DynamicMultiPlayers }
     */
    public GameDescriptor.DynamicMultiPlayers getDynamicMultiPlayers() {
        return dynamicMultiPlayers;
    }

    /**
     * Sets the value of the dynamicMultiPlayers property.
     *
     * @param value allowed object is
     *              {@link GameDescriptor.DynamicMultiPlayers }
     */
    public void setDynamicMultiPlayers(GameDescriptor.DynamicMultiPlayers value) {
        this.dynamicMultiPlayers = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * <p>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Definition">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{}Rows"/>
     *                   &lt;element ref="{}Columns"/>
     *                   &lt;element ref="{}Slices"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element ref="{}Solution"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "definition",
            "solution"
    })
    public static class Board {

        @XmlElement(name = "Definition", required = true)
        protected GameDescriptor.Board.Definition definition;
        @XmlElement(name = "Solution", required = true)
        protected Solution solution;

        /**
         * Gets the value of the definition property.
         *
         * @return possible object is
         * {@link GameDescriptor.Board.Definition }
         */
        public GameDescriptor.Board.Definition getDefinition() {
            return definition;
        }

        /**
         * Sets the value of the definition property.
         *
         * @param value allowed object is
         *              {@link GameDescriptor.Board.Definition }
         */
        public void setDefinition(GameDescriptor.Board.Definition value) {
            this.definition = value;
        }

        /**
         * Gets the value of the solution property.
         *
         * @return possible object is
         * {@link Solution }
         */
        public Solution getSolution() {
            return solution;
        }

        /**
         * Sets the value of the solution property.
         *
         * @param value allowed object is
         *              {@link Solution }
         */
        public void setSolution(Solution value) {
            this.solution = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * <p>
         * <p>The following schema fragment specifies the expected content contained within this class.
         * <p>
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element ref="{}Rows"/>
         *         &lt;element ref="{}Columns"/>
         *         &lt;element ref="{}Slices"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "rows",
                "columns",
                "slices"
        })
        public static class Definition {

            @XmlElement(name = "Rows", required = true)
            @XmlSchemaType(name = "positiveInteger")
            protected BigInteger rows;
            @XmlElement(name = "Columns", required = true)
            @XmlSchemaType(name = "positiveInteger")
            protected BigInteger columns;
            @XmlElement(name = "Slices", required = true)
            protected Slices slices;

            /**
             * Gets the value of the rows property.
             *
             * @return possible object is
             * {@link BigInteger }
             */
            public BigInteger getRows() {
                return rows;
            }

            /**
             * Sets the value of the rows property.
             *
             * @param value allowed object is
             *              {@link BigInteger }
             */
            public void setRows(BigInteger value) {
                this.rows = value;
            }

            /**
             * Gets the value of the columns property.
             *
             * @return possible object is
             * {@link BigInteger }
             */
            public BigInteger getColumns() {
                return columns;
            }

            /**
             * Sets the value of the columns property.
             *
             * @param value allowed object is
             *              {@link BigInteger }
             */
            public void setColumns(BigInteger value) {
                this.columns = value;
            }

            /**
             * Gets the value of the slices property.
             *
             * @return possible object is
             * {@link Slices }
             */
            public Slices getSlices() {
                return slices;
            }

            /**
             * Sets the value of the slices property.
             *
             * @param value allowed object is
             *              {@link Slices }
             */
            public void setSlices(Slices value) {
                this.slices = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * <p>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="totalmoves" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *       &lt;attribute name="totalPlayers" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *       &lt;attribute name="gametitle" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class DynamicMultiPlayers {

        @XmlAttribute(name = "totalmoves", required = true)
        @XmlSchemaType(name = "anySimpleType")
        protected String totalmoves;
        @XmlAttribute(name = "totalPlayers", required = true)
        @XmlSchemaType(name = "anySimpleType")
        protected String totalPlayers;
        @XmlAttribute(name = "gametitle", required = true)
        @XmlSchemaType(name = "anySimpleType")
        protected String gametitle;

        /**
         * Gets the value of the totalmoves property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getTotalmoves() {
            return totalmoves;
        }

        /**
         * Sets the value of the totalmoves property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setTotalmoves(String value) {
            this.totalmoves = value;
        }

        /**
         * Gets the value of the totalPlayers property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getTotalPlayers() {
            return totalPlayers;
        }

        /**
         * Sets the value of the totalPlayers property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setTotalPlayers(String value) {
            this.totalPlayers = value;
        }

        /**
         * Gets the value of the gametitle property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getGametitle() {
            return gametitle;
        }

        /**
         * Sets the value of the gametitle property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setGametitle(String value) {
            this.gametitle = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * <p>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{}Players"/>
     *       &lt;/sequence>
     *       &lt;attribute name="moves" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "players"
    })
    public static class MultiPlayers {

        @XmlElement(name = "Players", required = true)
        protected Players players;
        @XmlAttribute(name = "moves", required = true)
        @XmlSchemaType(name = "anySimpleType")
        protected String moves;

        /**
         * Gets the value of the players property.
         *
         * @return possible object is
         * {@link Players }
         */
        public Players getPlayers() {
            return players;
        }

        /**
         * Sets the value of the players property.
         *
         * @param value allowed object is
         *              {@link Players }
         */
        public void setPlayers(Players value) {
            this.players = value;
        }

        /**
         * Gets the value of the moves property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getMoves() {
            return moves;
        }

        /**
         * Sets the value of the moves property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setMoves(String value) {
            this.moves = value;
        }

    }

}
