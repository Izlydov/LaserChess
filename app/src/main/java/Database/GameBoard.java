package Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.laserchesstest.Position;

@Entity(tableName = "game_board")
@TypeConverters(Converters.class)
public class GameBoard {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private Position[][] board;

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    private boolean playerTurn;
    public GameBoard(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Position[][] getBoard() {
        return board;
    }

    public void setBoard(Position[][] board) {
        this.board = board;
    }
}