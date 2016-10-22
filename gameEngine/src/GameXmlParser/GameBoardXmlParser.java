package GameXmlParser;

import Game.BoardSquare;
import Game.SolutionBoard;
import GameXmlParser.Schema.Constraint;
import GameXmlParser.Schema.Constraints;
import GameXmlParser.Schema.GameType;
import GameXmlParser.Schema.Generated.*;
import com.sun.deploy.util.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by ido on 13/08/2016.
 */
public class GameBoardXmlParser {
    private static final String JAXB_XML_GAME_PACKAGE_NAME = "GameXmlParser.Schema.Generated";
    private static final String illegalXmlFileMessage = "Game Definition Xml File is illegal";
    private static final String sliceIsDefinedMoreThenOneTime = "Slice with orientation %s and index %d is defined more then one time";
    private static final String sliceIsDefinedWithIllegalId = "Slice with orientation %s and index %d exceeds the maximum index  of %d";
    private static final String unknownSliceOrentation = "Unknown slice orientation of %s is defined";
    private static final String invalidGameTypeMessage = "Defined Game type of %s is not a valid Game Type";
    private static final String invalidBlocksDefinition = "Invalid Blocks Definition at Slice with orientation %s and index %d";
    private static final String invalidSlicesDefinition = "Invalid slices definition";
    private static final String invalidSliceDefinition = "Invalid slice definition";
    private static final String NumberDefinitionExceedsMaximum = "The sum the number constraints inside the Slice with orientation %s  and index %d exceeds the maximum allowed Of %d ";
    private static final String invalidNumberFormatDefinition = "Invalid Number Definition inside a Slice with orientation %s and index %d ";
    private static final String solutionIsNotDefined = "Solution is not defined in the provided xml file";
    private static final String squareIsNotDefined = "a square is not defined correctly in the provided xml file";
    private static final String orientationRow = "row";
    private static final String orientationColumn = "column";
    private static final String InvalidConstraintsOnSolution = "Solution has a %s Constraint that does not match the constraint defined in the xml file";
    private static final String squareIsDefinedTwice = "Solution square in Row %d and Column %d is defined twice";
    private static final int minIndexValue = 0;
    private static final int maxDimension = 100;
    private static final int minDimension = 10;
    private static final String NOT_ALL_CONSTRAINTS_ARE_DEFIND = "Not All Constraints(Slices) are Defined";
    private GameDescriptor gameDescriptor;
    private GameType gametype;
    private SolutionBoard solutionBoard;
    private Constraints[] rowConstraints;
    private Constraints[] columnConstraints;
    private int rows;
    private int columns;
    private int totalMoves;
    private String gameTitle;
    private int  totalPlayers;

    public int getTotalPlayers() {
        return totalPlayers;
    }

    public int getTotalMoves() {
        return totalMoves;
    }

    public String getGameTitle() {
        return gameTitle;
    }


    public List<Constraints> getRowConstraints() {
        return new ArrayList<>(Arrays.asList(rowConstraints));
    }

    public List<Constraints> getColumnConstraints() {

        return new ArrayList<>(Arrays.asList(columnConstraints));
    }

    public GameBoardXmlParser(InputStream gameDefinitionsXmlFile) throws GameDefinitionsXmlParserException {
        parseXml(gameDefinitionsXmlFile);
    }

    private void parseXml(InputStream gameDefinitionsXmlFile) throws GameDefinitionsXmlParserException {
        parseGameDescriptor(gameDefinitionsXmlFile);
        extractGameType();
        extractBoardDimensions();
        extractSlices();
        extractSolutionBoard();
        extractDynamicMultiPlayersInfo();
    }

    private void extractDynamicMultiPlayersInfo() throws GameDefinitionsXmlParserException {
        try {
            totalMoves = Integer.parseInt(gameDescriptor.getDynamicMultiPlayers().getTotalmoves());
            if(totalMoves <=0) {
                throw new GameDefinitionsXmlParserException("Total Moves must be Greater then 1");
            }
            gameTitle = gameDescriptor.getDynamicMultiPlayers().getGametitle();
            if(gameTitle == null || gameTitle.trim().isEmpty() || gameTitle.equals("")) {
                throw new GameDefinitionsXmlParserException("Total Players must be Greater then 1");
            }
            totalPlayers = Integer.parseInt(gameDescriptor.getDynamicMultiPlayers().getTotalPlayers());
            if(totalPlayers <=0) {
                throw new GameDefinitionsXmlParserException("Total Players must be Greater then 1");
            }
        } catch (Exception e) {
            throw new GameDefinitionsXmlParserException(e.getMessage());
        }
    }

    private void extractSolutionBoard() throws GameDefinitionsXmlParserException {
        Solution solution = gameDescriptor.getBoard().getSolution();
        if (solution != null) {
            solutionBoard = new SolutionBoard(rows, columns);
            for (Square square : solution.getSquare()) {
                if (square != null) {
                    int row = square.getRow().intValue() - 1;
                    int column = square.getColumn().intValue() - 1;
                    if (isValidRange(row, rows) && isValidRange(column, columns)) {
                        if (solutionBoard.getBoardSquare(row, column).equals(BoardSquare.Black)) {
                            throw new GameDefinitionsXmlParserException(String.format(squareIsDefinedTwice, row, column));
                        }
                        solutionBoard.setBoardSquareAsBlack(row, column);
                    } else {
                        throw new GameDefinitionsXmlParserException(squareIsNotDefined);
                    }
                } else {
                    throw new GameDefinitionsXmlParserException(squareIsNotDefined);
                }
            }
        } else {
            throw new GameDefinitionsXmlParserException(solutionIsNotDefined);
        }
        //checkConstraintsOnSolutionBoard();
    }

    private void checkConstraintsOnSolutionBoard() throws GameDefinitionsXmlParserException {
        if (!solutionBoard.validRowsConstraints(rowConstraints)) {
            throw new GameDefinitionsXmlParserException(String.format(InvalidConstraintsOnSolution, orientationRow));
        }
        if (!solutionBoard.validColumnsConstraints(columnConstraints)) {
            throw new GameDefinitionsXmlParserException(String.format(InvalidConstraintsOnSolution, orientationColumn));
        }
    }

    private void extractBoardDimensions() throws GameDefinitionsXmlParserException {
        rows = gameDescriptor.getBoard().getDefinition().getRows().intValue();
        columns = gameDescriptor.getBoard().getDefinition().getColumns().intValue();
        if (!validDimension(rows) || !validDimension(columns)) {
            throw new GameDefinitionsXmlParserException("Board Dimensions should be greater of equal then 10 and less then 100");
        }
    }

    private boolean validDimension(int dimension) {
        return (dimension >= minDimension) && (dimension < maxDimension);
    }

    private void extractSlices() throws GameDefinitionsXmlParserException {
        Slices slices = gameDescriptor.getBoard().getDefinition().getSlices();
        boolean[] isRowDefined = new boolean[rows];
        boolean[] isColumnDefined = new boolean[columns];
        rowConstraints = new Constraints[rows];
        columnConstraints = new Constraints[columns];
        if (slices != null) {
            for (Slice slice : slices.getSlice()) {
                if (slice != null) {
                    int currentSliceIndex = slice.getId().intValue() - 1;
                    switch (slice.getOrientation()) {
                        case orientationRow:
                            extractBlocksFromSlice(isRowDefined, slice, currentSliceIndex, rows);
                            break;
                        case orientationColumn:
                            extractBlocksFromSlice(isColumnDefined, slice, currentSliceIndex, columns);
                            break;
                        default:
                            throw new GameDefinitionsXmlParserException(String.format(unknownSliceOrentation, slice.getOrientation()));
                    }
                } else {
                    throw new GameDefinitionsXmlParserException(invalidSliceDefinition);
                }
            }
        } else {
            throw new GameDefinitionsXmlParserException(invalidSlicesDefinition);
        }
        validateThatAllConstraintsAreDefined(isRowDefined);
        validateThatAllConstraintsAreDefined(isColumnDefined);

    }

    private void validateThatAllConstraintsAreDefined(boolean[] isConstraintsDefined) throws GameDefinitionsXmlParserException {
        for (boolean IsConstraintDefined : isConstraintsDefined) {
            if (!IsConstraintDefined) {
                throw new GameDefinitionsXmlParserException(NOT_ALL_CONSTRAINTS_ARE_DEFIND);
            }
        }
    }

    private void extractBlocksFromSlice(boolean[] isOrientationDefined, Slice slice, int currentSliceIndex, int maxIndexValue) throws GameDefinitionsXmlParserException {
        if (isValidRange(currentSliceIndex, maxIndexValue)) {
            if (!isOrientationDefined[currentSliceIndex]) {
                isOrientationDefined[currentSliceIndex] = true;
                setConstraints(currentSliceIndex, slice);
            } else {
                throw new GameDefinitionsXmlParserException(String.format(sliceIsDefinedMoreThenOneTime, slice.getOrientation(), currentSliceIndex));
            }
        } else {
            throw new GameDefinitionsXmlParserException(String.format(sliceIsDefinedWithIllegalId, slice.getOrientation(), currentSliceIndex, maxIndexValue));
        }
    }

    private void setConstraints(int index, Slice slice) throws GameDefinitionsXmlParserException {
        String blocks = slice.getBlocks();
        String parts[];
        if (blocks != null) {
            parts = blocks.split(",");
            Constraints constraints = new Constraints(parts.length);
            int sum = 0;
            for (String part : parts) {
                try {
                    int currentInt = Integer.parseInt(part.trim());
                    sum += currentInt;
                    constraints.addConstraint(new Constraint(currentInt));
                } catch (NumberFormatException e) {
                    throw new GameDefinitionsXmlParserException(String.format(invalidNumberFormatDefinition, slice.getOrientation(), slice.getId()), e);
                }

            }
            if (slice.getOrientation().equals(orientationRow)) {
                if ((sum + parts.length - 1) > columns) {
                    throw new GameDefinitionsXmlParserException(String.format(NumberDefinitionExceedsMaximum, slice.getOrientation(), slice.getId(), columns));
                } else {
                    rowConstraints[index] = constraints;
                }
            } else {
                if ((sum + parts.length - 1) > rows) {
                    throw new GameDefinitionsXmlParserException(String.format(NumberDefinitionExceedsMaximum, slice.getOrientation(), slice.getId(), rows));
                } else {
                    columnConstraints[index] = constraints;
                }
            }
        } else {
            throw new GameDefinitionsXmlParserException(String.format(invalidBlocksDefinition, slice.getOrientation(), slice.getId()));
        }
    }

    private boolean isValidRange(int currentSliceId, int maxIndexValue) {
        return (currentSliceId >= minIndexValue && currentSliceId < maxIndexValue);
    }

    private void extractGameType() throws GameDefinitionsXmlParserException {
        try {
            gametype = GameType.valueOf(gameDescriptor.getGameType());
            if (gametype != GameType.DynamicMultiPlayer) {
                throw new GameDefinitionsXmlParserException("Game Type must be DynamicMultiPlayers");
            }
        } catch (Exception e) {
            throw new GameDefinitionsXmlParserException(String.format(invalidGameTypeMessage, gameDescriptor.getGameType()), e);
        }
    }

    private void parseGameDescriptor(InputStream gameDefinitionsXmlFile) throws GameDefinitionsXmlParserException {
        try {
            gameDescriptor = GameBoardXmlParser.deserializeFrom(gameDefinitionsXmlFile);
        } catch (JAXBException e) {
            throw new GameDefinitionsXmlParserException(illegalXmlFileMessage, e);
        }
    }

    public GameType getGameType() {
        return gametype;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public SolutionBoard getSolutionBoard() {
        return solutionBoard;
    }

    private static GameDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (GameDescriptor) u.unmarshal(in);
    }
}
