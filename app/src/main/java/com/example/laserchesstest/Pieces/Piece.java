package com.example.laserchesstest.Pieces;

import androidx.annotation.NonNull;

import com.example.laserchesstest.Coordinates;
import com.example.laserchesstest.Position;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Piece implements Cloneable{

    private ArrayList<Object> stringList;
    @SerializedName("piece_name")
    private String name; // имя нужно для определения типа фигуры после сохранения

    @NonNull
    @Override
    public Piece clone() {
        try {
            // Сначала копируем примитивные типы или неизменяемые объекты
            Piece cloned = (Piece) super.clone();
            // Затем копируем изменяемые объекты
            cloned.stringList = new ArrayList<>(stringList); // Глубокое копирование списка строк
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); // Обработка исключения CloneNotSupportedException
        }
    }

    private boolean white;
    @SerializedName("piece_direction")
    private int direction;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    Piece(boolean white, int direction, String name) {
        if (direction >= 360){
            direction = 0;
        }
        if (direction < 0){
            direction = 360 + direction;
        }
        this.white = white;
        this.direction = direction;
        this.stringList = new ArrayList<>();
        this.name = name;
    }

    public boolean isWhite() {
        return white;
    }
    public String getName() {
        return name;
    }



    public ArrayList<Coordinates> AllowedMoves(Coordinates coordinates , Position[][] board){
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