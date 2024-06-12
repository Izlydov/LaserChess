package com.example.laserchesstest;

import com.example.laserchesstest.Pieces.Piece;

public class Position {
    private Piece piece;


    Position(Piece piece ) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    void setPiece(Piece piece) {
        this.piece = piece;
    }

}