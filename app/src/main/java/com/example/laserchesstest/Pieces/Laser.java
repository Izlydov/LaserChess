package com.example.laserchesstest.Pieces;

import com.example.laserchesstest.Coordinates;
import com.example.laserchesstest.Position;

import java.util.ArrayList;

public class Laser extends Piece{



    public Laser(boolean white, int direction) {
        super(white, direction);
    }

    public ArrayList<Coordinates> AllowedMoves(Coordinates coordinates , Position[][] board){
        ArrayList<Coordinates> allowedMoves = new ArrayList<>();
        Coordinates c;
//        for(int i=0;i<8;i++){
//            for(int j=0;j<10;j++){
//                c = new Coordinates(i,j);
//                allowedMoves.add(c);
//            }
//        }
        return allowedMoves;
    }
}
