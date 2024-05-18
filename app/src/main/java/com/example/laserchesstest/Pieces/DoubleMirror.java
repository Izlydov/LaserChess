package com.example.laserchesstest.Pieces;
import com.example.laserchesstest.Coordinates;
import com.example.laserchesstest.Position;
import com.example.laserchesstest.Pieces.King;

import java.util.ArrayList;
public class DoubleMirror extends Piece {


    public DoubleMirror(boolean white, int direction) {
        super(white, direction);
    }

    @Override
    public ArrayList<Coordinates> AllowedMoves(Coordinates coordinates, Position[][] board) {
        ArrayList<Coordinates> allowedMoves = new ArrayList<>();
        allowedMoves.clear();
        Coordinates c;
        if ((coordinates.getX() + 1) < 8 && (coordinates.getY() + 1) < 10) { // ход вправо-вниз
            if (board[coordinates.getX() + 1][coordinates.getY() + 1].getPiece() == null) {
                c = new Coordinates(coordinates.getX() + 1, coordinates.getY() + 1);
                allowedMoves.add(c);
            }
            else {
                Piece p = board[coordinates.getX() + 1][coordinates.getY() + 1].getPiece();
                if(p instanceof Mirror || p instanceof Defender){
                    c = new Coordinates(coordinates.getX() + 1, coordinates.getY() + 1);
                    allowedMoves.add(c);
                }
            }
        }
        if ((coordinates.getY() + 1) < 10) { // ход вправо
            if (board[coordinates.getX()][coordinates.getY() + 1].getPiece() == null) {
                c = new Coordinates(coordinates.getX(), coordinates.getY() + 1);
                allowedMoves.add(c);
            }else {
                Piece p = board[coordinates.getX()][coordinates.getY() + 1].getPiece();
                if(p instanceof Mirror || p instanceof Defender){
                    c = new Coordinates(coordinates.getX(), coordinates.getY() + 1);
                    allowedMoves.add(c);
                }
            }
        }
        if ((coordinates.getX() - 1) >= 0 && (coordinates.getY() + 1) < 10) { // ход вправо-вниз
            if (board[coordinates.getX() - 1][coordinates.getY() + 1].getPiece() == null) {
                c = new Coordinates(coordinates.getX() - 1, coordinates.getY() + 1);
                allowedMoves.add(c);
            }else {
                Piece p = board[coordinates.getX() - 1][coordinates.getY() + 1].getPiece();
                if(p instanceof Mirror || p instanceof Defender){
                    c = new Coordinates(coordinates.getX() - 1, coordinates.getY() + 1);
                    allowedMoves.add(c);
                }
            }

        }
        if ((coordinates.getX() + 1) < 8) { // ход вниз
            if (board[coordinates.getX() + 1][coordinates.getY()].getPiece() == null) {
                c = new Coordinates(coordinates.getX() + 1, coordinates.getY());
                allowedMoves.add(c);
            }
            else {
                Piece p = board[coordinates.getX() + 1][coordinates.getY()].getPiece();
                if(p instanceof Mirror || p instanceof Defender){
                    c = new Coordinates(coordinates.getX() + 1, coordinates.getY());
                    allowedMoves.add(c);
                }
            }
        }
        if ((coordinates.getX() - 1) >= 0) { // ход вверх
            if (board[coordinates.getX() - 1][coordinates.getY()].getPiece() == null) {
                c = new Coordinates(coordinates.getX() - 1, coordinates.getY());
                allowedMoves.add(c);
            }else {
                Piece p = board[coordinates.getX() - 1][coordinates.getY()].getPiece();
                if(p instanceof Mirror || p instanceof Defender){
                    c = new Coordinates(coordinates.getX() - 1, coordinates.getY());
                    allowedMoves.add(c);
                }
            }
        }
        if ((coordinates.getX() + 1) < 8 && (coordinates.getY() - 1) >= 0) { // ход влево-вниз
            if (board[coordinates.getX() + 1][coordinates.getY() - 1].getPiece() == null) {
                c = new Coordinates(coordinates.getX() + 1, coordinates.getY() - 1);
                allowedMoves.add(c);
            }
            else {
                Piece p = board[coordinates.getX() + 1][coordinates.getY() - 1].getPiece();
                if(p instanceof Mirror || p instanceof Defender){
                    c = new Coordinates(coordinates.getX() + 1, coordinates.getY() - 1);
                    allowedMoves.add(c);
                }
            }
        }
        if ((coordinates.getY() - 1) >= 0) { // ход влево
            if (board[coordinates.getX()][coordinates.getY() - 1].getPiece() == null) {
                c = new Coordinates(coordinates.getX(), coordinates.getY() - 1);
                allowedMoves.add(c);
            }
            else {
                Piece p = board[coordinates.getX()][coordinates.getY() - 1].getPiece();
                if(p instanceof Mirror || p instanceof Defender){
                    c = new Coordinates(coordinates.getX(), coordinates.getY() - 1);
                    allowedMoves.add(c);
                }
            }
        }
        if ((coordinates.getX() - 1) >= 0 && (coordinates.getY() - 1) >= 0) { // ход влево-вверх
            if (board[coordinates.getX() - 1][coordinates.getY() - 1].getPiece() == null) {
                c = new Coordinates(coordinates.getX() - 1, coordinates.getY() - 1);
                allowedMoves.add(c);
            }
            else {
                Piece p = board[coordinates.getX() - 1][coordinates.getY() - 1].getPiece();
                if(p instanceof Mirror || p instanceof Defender){
                    c = new Coordinates(coordinates.getX() - 1, coordinates.getY() - 1);
                    allowedMoves.add(c);
                }
            }
        }


        return allowedMoves;
    }
}