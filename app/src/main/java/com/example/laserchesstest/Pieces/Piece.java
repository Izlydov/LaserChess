package com.example.laserchesstest.Pieces;

import com.example.laserchesstest.Coordinates;
import com.example.laserchesstest.Position;

import java.util.ArrayList;

public class Piece {

    private boolean white;
    private int direction;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    Piece(boolean white, int direction) {
        if (direction >= 360){
            direction = 0;
        }
        if (direction < 0){
            direction = 360 + direction;
        }
        this.white = white;
        this.direction = direction;
    }

    public boolean isWhite() {
        return white;
    }



    public ArrayList<Coordinates> AllowedMoves(Coordinates coordinates , Position[][] board){
        ArrayList<Coordinates> allowedMoves = new ArrayList<>();
        Coordinates c;
        for(int i=0;i<8;i++){
            for(int j=0;j<10;j++){
                c = new Coordinates(i,j);
                allowedMoves.add(c);
            }
        }
        return allowedMoves;
    }
}