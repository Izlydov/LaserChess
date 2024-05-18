package com.example.laserchesstest;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

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
    @Query("delete from game_board")
    public void deleteAllGameBoard();

}
