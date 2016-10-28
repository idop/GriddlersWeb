package Game.Player;

import Game.GameMove;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ido on 18/08/2016.
 */
public class PlayerTurn {
    private List<GameMove> moves;



    public PlayerTurn() {
        this.moves = new ArrayList<GameMove>();
    }

    public PlayerTurn(List<GameMove> i_moves)
    {
        this.moves = i_moves;
    }

    public void addGameMove(GameMove move) {
        moves.add(move);
    }

    public List<GameMove> getMoves() {
        return moves;
    }

    public void undo() {
        if (moves != null) {
            for (GameMove move : moves) {
                move.undo();
            }
        }
    }

    public void printTurn()
    {
        System.out.printf("|  Start location - row %d col %d | Move size: %d | Move type: %s%n",
                this.moves.get(0).getRow() + 1, this.moves.get(0).getColumn() + 1,      // fixing numbering of row/col
                this.moves.size(), this.moves.get(0).getNewBoardSquare().name());
        System.out.println();
    }
}
