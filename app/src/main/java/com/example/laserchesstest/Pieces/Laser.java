package com.example.laserchesstest.Pieces;

import com.example.laserchesstest.Coordinates;
import com.example.laserchesstest.Position;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Laser extends Piece{
    private String name = "Laser";

    public Laser(boolean white, int direction, String name) {
        super(white, direction, name);
    }
    @SerializedName("laser_direction")
    public int getLaserDirection() {
        return super.getDirection();
    }

    @SerializedName("laser_direction")
    public void setLaserDirection(int direction) {
        super.setDirection(direction);
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
