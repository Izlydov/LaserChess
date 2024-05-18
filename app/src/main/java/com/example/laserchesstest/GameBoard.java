package com.example.laserchesstest;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game_board")
public class GameBoard {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private Position[][] board;

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