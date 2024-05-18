package Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Database.GameBoard;

@Dao
public interface GameBoardDao {
    @Insert
    public void addGameBoard(GameBoard gameBoard);

    @Update
    public void updateGameBoard(GameBoard gameBoard);
    @Delete
    public void deleteGameBoard(GameBoard gameBoard);
    @Query("select * from game_board;")
    public List<GameBoard> getAllGameBoard();
    @Query("SELECT COUNT(*) FROM game_board")
    int getGameBoardCount();
    @Query("SELECT * FROM game_board WHERE id = :id")
    GameBoard getGameBoardById(int id);
    @Query("SELECT * FROM game_board ORDER BY id DESC LIMIT 1")
    public GameBoard getLastGameBoard();

    @Query("delete from game_board")
    public void deleteAllGameBoard();

}
