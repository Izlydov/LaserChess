package com.example.laserchesstest.Pieces;

import com.example.laserchesstest.Coordinates;
import com.example.laserchesstest.Position;

import java.util.ArrayList;
public class Mirror extends Piece {
    private int direction;


    public Mirror(boolean white, int direction) {
        super(white, direction);
    }
    @Override
    public ArrayList<Coordinates> AllowedMoves(Coordinates coordinates , Position[][] board) {
        ArrayList<Coordinates> allowedMoves = new ArrayList<>();
        allowedMoves.clear();
        Coordinates c;
        if((coordinates.getX()+1) < 8 && (coordinates.getY()+1)<10) { // ход вправо-вниз
            if (board[coordinates.getX() + 1][coordinates.getY() + 1].getPiece() == null) {
                c = new Coordinates(coordinates.getX() + 1, coordinates.getY() + 1);
                allowedMoves.add(c);
            }
        }
        if((coordinates.getY()+1)<10) { // ход вправо
            if (board[coordinates.getX()][coordinates.getY() + 1].getPiece() == null) {
                c = new Coordinates(coordinates.getX(), coordinates.getY() + 1);
                allowedMoves.add(c);
            }
        }
        if((coordinates.getX()-1) >=0 && (coordinates.getY()+1)<10) { // ход вправо-вниз
            if (board[coordinates.getX() - 1][coordinates.getY() + 1].getPiece() == null) {
                c = new Coordinates(coordinates.getX() - 1, coordinates.getY() + 1);
                allowedMoves.add(c);
            }
        }
        if((coordinates.getX()+1) < 8 ) { // ход вниз
            if (board[coordinates.getX() + 1][coordinates.getY()].getPiece() == null) {
                c = new Coordinates(coordinates.getX() + 1, coordinates.getY());
                allowedMoves.add(c);
            }
        }
        if((coordinates.getX()-1) >=0 ) { // ход вверх
            if (board[coordinates.getX() - 1][coordinates.getY()].getPiece() == null) {
                c = new Coordinates(coordinates.getX() - 1, coordinates.getY());
                allowedMoves.add(c);
            }
        }
        if((coordinates.getX()+1) < 8 && (coordinates.getY()-1)>=0) { // ход влево-вниз
            if (board[coordinates.getX() + 1][coordinates.getY() - 1].getPiece() == null) {
                c = new Coordinates(coordinates.getX() + 1, coordinates.getY() - 1);
                allowedMoves.add(c);
            }
        }
        if((coordinates.getY()-1)>=0) { // ход влево
            if (board[coordinates.getX()][coordinates.getY() - 1].getPiece() == null) {
                c = new Coordinates(coordinates.getX(), coordinates.getY() - 1);
                allowedMoves.add(c);
            }
        }
        if((coordinates.getX()-1) >= 0 && (coordinates.getY()-1)>=0) { // ход влево-вверх
            if (board[coordinates.getX() - 1][coordinates.getY() - 1].getPiece() == null) {
                c = new Coordinates(coordinates.getX() - 1, coordinates.getY() - 1);
                allowedMoves.add(c);
            }
        }


        return allowedMoves;
    }
}
