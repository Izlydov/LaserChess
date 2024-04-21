package com.example.laserchesstest;

import static com.example.laserchesstest.CellResult.BOTTOM;
import static com.example.laserchesstest.CellResult.KILL;
import static com.example.laserchesstest.CellResult.KING_KILL;
import static com.example.laserchesstest.CellResult.LEFT;
import static com.example.laserchesstest.CellResult.NOTHING;
import static com.example.laserchesstest.CellResult.NULL;
import static com.example.laserchesstest.CellResult.RIGHT;
import static com.example.laserchesstest.CellResult.STOP;
import static com.example.laserchesstest.CellResult.TOP;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laserchesstest.Pieces.Defender;
import com.example.laserchesstest.Pieces.DoubleMirror;
import com.example.laserchesstest.Pieces.King;
import com.example.laserchesstest.Pieces.Laser;
import com.example.laserchesstest.Pieces.Mirror;
import com.example.laserchesstest.Pieces.Piece;
import com.example.laserchesstest.Pieces.ReservedCell;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public Boolean FirstPlayerTurn = false;
    public ArrayList<Coordinates> listOfCoordinates = new ArrayList<>();
    public Position[][] Board = new Position[8][10];
    public Position[][] Board2 = new Position[8][10];
    public Boolean AnythingSelected = false;
    boolean DoubleMirrorSelected = false;
    public Coordinates lastPos = null;
    public Coordinates clickedPosition = new Coordinates(0, 0);
    public Coordinates blueLaserCords = new Coordinates(6, 9);
    public Coordinates redLaserCords = new Coordinates(0, 0);
    public TextView game_over;
    public TextView[][] DisplayBoard = new TextView[8][10];
    public TextView[][] DisplayBoardBackground = new TextView[8][10];
    public ArrayList<Position[][]> LastMoves = new ArrayList<>();
    public int numberOfMoves;


    Piece rLaser;
    Piece wLaser;
    Piece rKing;
    Piece wKing;

    Piece rDefender1;
    Piece rDefender2;
    Piece wDefender1;
    Piece wDefender2;

    Piece rDoubleMirror1;
    Piece rDoubleMirror2;
    Piece wDoubleMirror1;
    Piece wDoubleMirror2;

    Piece rMirror1;
    Piece rMirror2;
    Piece rMirror3;
    Piece rMirror4;
    Piece rMirror5;
    Piece rMirror6;
    Piece rMirror7;

    Piece wMirror1;
    Piece wMirror2;
    Piece wMirror3;
    Piece wMirror4;
    Piece wMirror5;
    Piece wMirror6;
    Piece wMirror7;

    Piece bReservedCell;
    Piece rReservedCell;

    private void initializeBoard() {

        rLaser = new Laser(false, 180);
        wLaser = new Laser(true, 0);

        rKing = new King(false, 0);
        wKing = new King(true, 0);

        rDefender1 = new Defender(false, 180);
        rDefender2 = new Defender(false, 180);
        wDefender1 = new Defender(true, 0);
        wDefender2 = new Defender(true, 0);

        rDoubleMirror1 = new DoubleMirror(false, 0);
        rDoubleMirror2 = new DoubleMirror(false, 90);
        wDoubleMirror1 = new DoubleMirror(true, 90);
        wDoubleMirror2 = new DoubleMirror(true, 0);

        rMirror1 = new Mirror(false, 270);
        rMirror2 = new Mirror(false, 0);
        rMirror3 = new Mirror(false, 180);
        rMirror4 = new Mirror(false, 270);
        rMirror5 = new Mirror(false, 270);
        rMirror6 = new Mirror(false, 180);
        rMirror7 = new Mirror(false, 270);

        wMirror1 = new Mirror(true, 90);
        wMirror2 = new Mirror(true, 0);
        wMirror3 = new Mirror(true, 90);
        wMirror4 = new Mirror(true, 0);
        wMirror5 = new Mirror(true, 0);
        wMirror6 = new Mirror(true, 180);
        wMirror7 = new Mirror(true, 90);

        bReservedCell = new ReservedCell(true, 0);
        rReservedCell = new ReservedCell(false, 0);


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                Board[i][j] = new Position(null);
                Board2[i][j] = new Position(null);
            }
        }
        Board[0][0].setPiece(rLaser);
        Board[7][9].setPiece(wLaser);

        Board[0][4].setPiece(rDefender1);
        Board[0][5].setPiece(rKing);
        Board[0][6].setPiece(rDefender2);
        Board[0][7].setPiece(rMirror1);
        Board[1][2].setPiece(rMirror2);
        Board[2][3].setPiece(wMirror1);
        Board[3][0].setPiece(rMirror3);
        Board[3][2].setPiece(wMirror2);
        Board[3][4].setPiece(rDoubleMirror1);
        Board[3][5].setPiece(rDoubleMirror2);
        Board[3][7].setPiece(rMirror4);
        Board[3][9].setPiece(wMirror3);
        Board[4][0].setPiece(rMirror5);
        Board[4][2].setPiece(wMirror4);
        Board[4][4].setPiece(wDoubleMirror1);
        Board[4][5].setPiece(wDoubleMirror2);
        Board[4][7].setPiece(rMirror6);
        Board[4][9].setPiece(wMirror5);
        Board[5][6].setPiece(rMirror7);
        Board[6][7].setPiece(wMirror6);
        Board[7][2].setPiece(wMirror7);
        Board[7][3].setPiece(wDefender1);
        Board[7][4].setPiece(wKing);
        Board[7][5].setPiece(wDefender2);



        DisplayBoard[0][0] = (TextView) findViewById(R.id.R00);
        DisplayBoardBackground[0][0] = (TextView) findViewById(R.id.R00);
        DisplayBoard[0][1] = (TextView) findViewById(R.id.R01);
        DisplayBoardBackground[0][1] = (TextView) findViewById(R.id.R01);
        DisplayBoard[0][2] = (TextView) findViewById(R.id.R02);
        DisplayBoardBackground[0][2] = (TextView) findViewById(R.id.R02);
        DisplayBoard[0][3] = (TextView) findViewById(R.id.R03);
        DisplayBoardBackground[0][3] = (TextView) findViewById(R.id.R03);
        DisplayBoard[0][4] = (TextView) findViewById(R.id.R04);
        DisplayBoardBackground[0][4] = (TextView) findViewById(R.id.R04);
        DisplayBoard[0][5] = (TextView) findViewById(R.id.R05);
        DisplayBoardBackground[0][5] = (TextView) findViewById(R.id.R05);
        DisplayBoard[0][6] = (TextView) findViewById(R.id.R06);
        DisplayBoardBackground[0][6] = (TextView) findViewById(R.id.R06);
        DisplayBoard[0][7] = (TextView) findViewById(R.id.R07);
        DisplayBoardBackground[0][7] = (TextView) findViewById(R.id.R07);
        DisplayBoard[0][8] = (TextView) findViewById(R.id.R08);
        DisplayBoardBackground[0][8] = (TextView) findViewById(R.id.R08);
        DisplayBoard[0][9] = (TextView) findViewById(R.id.R09);
        DisplayBoardBackground[0][9] = (TextView) findViewById(R.id.R09);

        DisplayBoard[1][0] = (TextView) findViewById(R.id.R10);
        DisplayBoardBackground[1][0] = (TextView) findViewById(R.id.R10);
        DisplayBoard[1][1] = (TextView) findViewById(R.id.R11);
        DisplayBoardBackground[1][1] = (TextView) findViewById(R.id.R11);
        DisplayBoard[1][2] = (TextView) findViewById(R.id.R12);
        DisplayBoardBackground[1][2] = (TextView) findViewById(R.id.R12);
        DisplayBoard[1][3] = (TextView) findViewById(R.id.R13);
        DisplayBoardBackground[1][3] = (TextView) findViewById(R.id.R13);
        DisplayBoard[1][4] = (TextView) findViewById(R.id.R14);
        DisplayBoardBackground[1][4] = (TextView) findViewById(R.id.R14);
        DisplayBoard[1][5] = (TextView) findViewById(R.id.R15);
        DisplayBoardBackground[1][5] = (TextView) findViewById(R.id.R15);
        DisplayBoard[1][6] = (TextView) findViewById(R.id.R16);
        DisplayBoardBackground[1][6] = (TextView) findViewById(R.id.R16);
        DisplayBoard[1][7] = (TextView) findViewById(R.id.R17);
        DisplayBoardBackground[1][7] = (TextView) findViewById(R.id.R17);
        DisplayBoard[1][8] = (TextView) findViewById(R.id.R18);
        DisplayBoardBackground[1][8] = (TextView) findViewById(R.id.R18);

        DisplayBoard[1][9] = (TextView) findViewById(R.id.R19);
        DisplayBoardBackground[1][9] = (TextView) findViewById(R.id.R19);
        DisplayBoard[2][0] = (TextView) findViewById(R.id.R20);
        DisplayBoardBackground[2][0] = (TextView) findViewById(R.id.R20);
        DisplayBoard[2][1] = (TextView) findViewById(R.id.R21);
        DisplayBoardBackground[2][1] = (TextView) findViewById(R.id.R21);
        DisplayBoard[2][2] = (TextView) findViewById(R.id.R22);
        DisplayBoardBackground[2][2] = (TextView) findViewById(R.id.R22);
        DisplayBoard[2][3] = (TextView) findViewById(R.id.R23);
        DisplayBoardBackground[2][3] = (TextView) findViewById(R.id.R23);
        DisplayBoard[2][4] = (TextView) findViewById(R.id.R24);
        DisplayBoardBackground[2][4] = (TextView) findViewById(R.id.R24);
        DisplayBoard[2][5] = (TextView) findViewById(R.id.R25);
        DisplayBoardBackground[2][5] = (TextView) findViewById(R.id.R25);
        DisplayBoard[2][6] = (TextView) findViewById(R.id.R26);
        DisplayBoardBackground[2][6] = (TextView) findViewById(R.id.R26);
        DisplayBoard[2][7] = (TextView) findViewById(R.id.R27);
        DisplayBoardBackground[2][7] = (TextView) findViewById(R.id.R27);
        DisplayBoard[2][8] = (TextView) findViewById(R.id.R28);
        DisplayBoardBackground[2][8] = (TextView) findViewById(R.id.R28);
        DisplayBoard[2][9] = (TextView) findViewById(R.id.R29);
        DisplayBoardBackground[2][9] = (TextView) findViewById(R.id.R29);

        DisplayBoard[3][0] = (TextView) findViewById(R.id.R30);
        DisplayBoardBackground[3][0] = (TextView) findViewById(R.id.R30);
        DisplayBoard[3][1] = (TextView) findViewById(R.id.R31);
        DisplayBoardBackground[3][1] = (TextView) findViewById(R.id.R31);
        DisplayBoard[3][2] = (TextView) findViewById(R.id.R32);
        DisplayBoardBackground[3][2] = (TextView) findViewById(R.id.R32);
        DisplayBoard[3][3] = (TextView) findViewById(R.id.R33);
        DisplayBoardBackground[3][3] = (TextView) findViewById(R.id.R33);
        DisplayBoard[3][4] = (TextView) findViewById(R.id.R34);
        DisplayBoardBackground[3][4] = (TextView) findViewById(R.id.R34);
        DisplayBoard[3][5] = (TextView) findViewById(R.id.R35);
        DisplayBoardBackground[3][5] = (TextView) findViewById(R.id.R35);
        DisplayBoard[3][6] = (TextView) findViewById(R.id.R36);
        DisplayBoardBackground[3][6] = (TextView) findViewById(R.id.R36);
        DisplayBoard[3][7] = (TextView) findViewById(R.id.R37);
        DisplayBoardBackground[3][7] = (TextView) findViewById(R.id.R37);
        DisplayBoard[3][8] = (TextView) findViewById(R.id.R38);
        DisplayBoardBackground[3][8] = (TextView) findViewById(R.id.R38);
        DisplayBoard[3][9] = (TextView) findViewById(R.id.R39);
        DisplayBoardBackground[3][9] = (TextView) findViewById(R.id.R39);

        DisplayBoard[4][0] = (TextView) findViewById(R.id.R40);
        DisplayBoardBackground[4][0] = (TextView) findViewById(R.id.R40);
        DisplayBoard[4][1] = (TextView) findViewById(R.id.R41);
        DisplayBoardBackground[4][1] = (TextView) findViewById(R.id.R41);
        DisplayBoard[4][2] = (TextView) findViewById(R.id.R42);
        DisplayBoardBackground[4][2] = (TextView) findViewById(R.id.R42);
        DisplayBoard[4][3] = (TextView) findViewById(R.id.R43);
        DisplayBoardBackground[4][3] = (TextView) findViewById(R.id.R43);
        DisplayBoard[4][4] = (TextView) findViewById(R.id.R44);
        DisplayBoardBackground[4][4] = (TextView) findViewById(R.id.R44);
        DisplayBoard[4][5] = (TextView) findViewById(R.id.R45);
        DisplayBoardBackground[4][5] = (TextView) findViewById(R.id.R45);
        DisplayBoard[4][6] = (TextView) findViewById(R.id.R46);
        DisplayBoardBackground[4][6] = (TextView) findViewById(R.id.R46);
        DisplayBoard[4][7] = (TextView) findViewById(R.id.R47);
        DisplayBoardBackground[4][7] = (TextView) findViewById(R.id.R47);
        DisplayBoard[4][8] = (TextView) findViewById(R.id.R48);
        DisplayBoardBackground[4][8] = (TextView) findViewById(R.id.R48);
        DisplayBoard[4][9] = (TextView) findViewById(R.id.R49);
        DisplayBoardBackground[4][9] = (TextView) findViewById(R.id.R49);

        DisplayBoard[5][0] = (TextView) findViewById(R.id.R50);
        DisplayBoardBackground[5][0] = (TextView) findViewById(R.id.R50);
        DisplayBoard[5][1] = (TextView) findViewById(R.id.R51);
        DisplayBoardBackground[5][1] = (TextView) findViewById(R.id.R51);
        DisplayBoard[5][2] = (TextView) findViewById(R.id.R52);
        DisplayBoardBackground[5][2] = (TextView) findViewById(R.id.R52);
        DisplayBoard[5][3] = (TextView) findViewById(R.id.R53);
        DisplayBoardBackground[5][3] = (TextView) findViewById(R.id.R53);
        DisplayBoard[5][4] = (TextView) findViewById(R.id.R54);
        DisplayBoardBackground[5][4] = (TextView) findViewById(R.id.R54);
        DisplayBoard[5][5] = (TextView) findViewById(R.id.R55);
        DisplayBoardBackground[5][5] = (TextView) findViewById(R.id.R55);
        DisplayBoard[5][6] = (TextView) findViewById(R.id.R56);
        DisplayBoardBackground[5][6] = (TextView) findViewById(R.id.R56);
        DisplayBoard[5][7] = (TextView) findViewById(R.id.R57);
        DisplayBoardBackground[5][7] = (TextView) findViewById(R.id.R57);
        DisplayBoard[5][8] = (TextView) findViewById(R.id.R58);
        DisplayBoardBackground[5][8] = (TextView) findViewById(R.id.R58);
        DisplayBoard[5][9] = (TextView) findViewById(R.id.R59);
        DisplayBoardBackground[5][9] = (TextView) findViewById(R.id.R59);

        DisplayBoard[6][0] = (TextView) findViewById(R.id.R60);
        DisplayBoardBackground[6][0] = (TextView) findViewById(R.id.R60);
        DisplayBoard[6][1] = (TextView) findViewById(R.id.R61);
        DisplayBoardBackground[6][1] = (TextView) findViewById(R.id.R61);
        DisplayBoard[6][2] = (TextView) findViewById(R.id.R62);
        DisplayBoardBackground[6][2] = (TextView) findViewById(R.id.R62);
        DisplayBoard[6][3] = (TextView) findViewById(R.id.R63);
        DisplayBoardBackground[6][3] = (TextView) findViewById(R.id.R63);
        DisplayBoard[6][4] = (TextView) findViewById(R.id.R64);
        DisplayBoardBackground[6][4] = (TextView) findViewById(R.id.R64);
        DisplayBoard[6][5] = (TextView) findViewById(R.id.R65);
        DisplayBoardBackground[6][5] = (TextView) findViewById(R.id.R65);
        DisplayBoard[6][6] = (TextView) findViewById(R.id.R66);
        DisplayBoardBackground[6][6] = (TextView) findViewById(R.id.R66);
        DisplayBoard[6][7] = (TextView) findViewById(R.id.R67);
        DisplayBoardBackground[6][7] = (TextView) findViewById(R.id.R67);
        DisplayBoard[6][8] = (TextView) findViewById(R.id.R68);
        DisplayBoardBackground[6][8] = (TextView) findViewById(R.id.R68);
        DisplayBoard[6][9] = (TextView) findViewById(R.id.R69);
        DisplayBoardBackground[6][9] = (TextView) findViewById(R.id.R69);

        DisplayBoard[7][0] = (TextView) findViewById(R.id.R70);
        DisplayBoardBackground[7][0] = (TextView) findViewById(R.id.R70);
        DisplayBoard[7][1] = (TextView) findViewById(R.id.R71);
        DisplayBoardBackground[7][1] = (TextView) findViewById(R.id.R71);
        DisplayBoard[7][2] = (TextView) findViewById(R.id.R72);
        DisplayBoardBackground[7][2] = (TextView) findViewById(R.id.R72);
        DisplayBoard[7][3] = (TextView) findViewById(R.id.R73);
        DisplayBoardBackground[7][3] = (TextView) findViewById(R.id.R73);
        DisplayBoard[7][4] = (TextView) findViewById(R.id.R74);
        DisplayBoardBackground[7][4] = (TextView) findViewById(R.id.R74);
        DisplayBoard[7][5] = (TextView) findViewById(R.id.R75);
        DisplayBoardBackground[7][5] = (TextView) findViewById(R.id.R75);
        DisplayBoard[7][6] = (TextView) findViewById(R.id.R76);
        DisplayBoardBackground[7][6] = (TextView) findViewById(R.id.R76);
        DisplayBoard[7][7] = (TextView) findViewById(R.id.R77);
        DisplayBoardBackground[7][7] = (TextView) findViewById(R.id.R77);
        DisplayBoard[7][8] = (TextView) findViewById(R.id.R78);
        DisplayBoardBackground[7][8] = (TextView) findViewById(R.id.R78);
        DisplayBoard[7][9] = (TextView) findViewById(R.id.R79);
        DisplayBoardBackground[7][9] = (TextView) findViewById(R.id.R79);

        DisplayBoard[0][1].setBackgroundResource(R.drawable.blue_reserved_cell);
        Board2[0][1].setPiece(bReservedCell);
        DisplayBoard[0][9].setBackgroundResource(R.drawable.blue_reserved_cell);
        Board2[0][9].setPiece(bReservedCell);
        DisplayBoard[1][9].setBackgroundResource(R.drawable.blue_reserved_cell);
        Board2[1][9].setPiece(bReservedCell);
        DisplayBoard[2][9].setBackgroundResource(R.drawable.blue_reserved_cell);
        Board2[2][9].setPiece(bReservedCell);
        DisplayBoard[3][9].setBackgroundResource(R.drawable.blue_reserved_cell);
        Board2[3][9].setPiece(bReservedCell);
        DisplayBoard[4][9].setBackgroundResource(R.drawable.blue_reserved_cell);
        Board2[4][9].setPiece(bReservedCell);
        DisplayBoard[5][9].setBackgroundResource(R.drawable.blue_reserved_cell);
        Board2[5][9].setPiece(bReservedCell);
        DisplayBoard[6][9].setBackgroundResource(R.drawable.blue_reserved_cell);
        Board2[6][9].setPiece(bReservedCell);
        DisplayBoard[7][1].setBackgroundResource(R.drawable.blue_reserved_cell);
        Board2[7][1].setPiece(bReservedCell);

        DisplayBoard[1][0].setBackgroundResource(R.drawable.red_reserved_cell);
        Board2[1][0].setPiece(rReservedCell);
        DisplayBoard[2][0].setBackgroundResource(R.drawable.red_reserved_cell);
        Board2[2][0].setPiece(rReservedCell);
        DisplayBoard[3][0].setBackgroundResource(R.drawable.red_reserved_cell);
        Board2[3][0].setPiece(rReservedCell);
        DisplayBoard[4][0].setBackgroundResource(R.drawable.red_reserved_cell);
        Board2[4][0].setPiece(rReservedCell);
        DisplayBoard[5][0].setBackgroundResource(R.drawable.red_reserved_cell);
        Board2[5][0].setPiece(rReservedCell);
        DisplayBoard[6][0].setBackgroundResource(R.drawable.red_reserved_cell);
        Board2[6][0].setPiece(rReservedCell);
        DisplayBoard[7][0].setBackgroundResource(R.drawable.red_reserved_cell);
        Board2[7][0].setPiece(rReservedCell);
        DisplayBoard[0][8].setBackgroundResource(R.drawable.red_reserved_cell);
        Board2[0][8].setPiece(rReservedCell);
        DisplayBoard[7][8].setBackgroundResource(R.drawable.red_reserved_cell);
        Board2[7][8].setPiece(rReservedCell);

//        for (int g = 0; g < 8; g++) {
//            for (int h = 0; h < 10; h++) {
//                if (Board[g][h].getPiece() == null) {
//                    Board2[g][h].setPiece(null);
//                } else {
//                    Board2[g][h].setPiece(Board[g][h].getPiece());
//                }
//            }
//        }

        numberOfMoves = 0;
        AnythingSelected = false;
        FirstPlayerTurn = true;
        setBoard();
    }

    private void setBoard() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {

                Piece p = Board[i][j].getPiece();
                int x;
                int dir;

                if (Board[i][j].getPiece() != null) {
                    if (p instanceof King) x = 0;
                    else if (p instanceof Defender) x = 1;
                    else if (p instanceof DoubleMirror) x = 2;
                    else if (p instanceof Mirror) x = 3;
                    else if (p instanceof Laser) x = 4;
                    else x = 6;
                    dir = p.getDirection();

                    switch (x) {
                        case 0:
                            if (p.isWhite()) {
                                DisplayBoard[i][j].setBackgroundResource(R.drawable.blue_k);
                            } else {
                                DisplayBoard[i][j].setBackgroundResource(R.drawable.red_k);
                            }
                            break;

                        case 1:
                            if (p.isWhite()) {
                                switch (dir) {
                                    case 0:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.blue_d);
                                        break;
                                    case 90:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.blue_d_90);
                                        break;
                                    case 180:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.blue_d_180);
                                        break;
                                    case 270:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.blue_d_270);
                                        break;
                                    default:
                                }
                            } else {
                                switch (dir) {
                                    case 0:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.red_d);
                                        break;
                                    case 90:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.red_d_90);
                                        break;
                                    case 180:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.red_d_180);
                                        break;
                                    case 270:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.red_d_270);
                                        break;
                                    default:
                                }
                            }
                            break;

                        case 2:
                            if (p.isWhite()) {
                                switch (dir) {
                                    case 0:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.blue_s);
                                        break;
                                    case 90:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.blue_s_90);
                                        break;
                                    case 180:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.blue_s_180);
                                        break;
                                    case 270:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.blue_s_270);
                                        break;
                                    default:
                                }
                            } else {
                                switch (dir) {
                                    case 0:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.red_s);
                                        break;
                                    case 90:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.red_s_90);
                                        break;
                                    case 180:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.red_s_180);
                                        break;
                                    case 270:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.red_s_270);
                                        break;
                                    default:
                                }
                            }
                            break;

                        case 3:
                            if (p.isWhite()) {
                                switch (dir) {
                                    case 0:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.blue_b);
                                        break;
                                    case 90:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.blue_b_90);
                                        break;
                                    case 180:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.blue_b_180);
                                        break;
                                    case 270:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.blue_b_270);
                                        break;
                                    default:
                                }
                            } else {
                                switch (dir) {
                                    case 0:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.red_b);
                                        break;
                                    case 90:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.red_b_90);
                                        break;
                                    case 180:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.red_b_180);
                                        break;
                                    case 270:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.red_b_270);
                                        break;
                                    default:
                                }

                            }
                            break;

                        case 4:
                            if (p.isWhite()) {
                                switch (dir) {
                                    case 0:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.blue_l);
                                        break;
                                    case 90:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.blue_l_90);
                                        break;
                                    case 180:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.blue_l_180);
                                        break;
                                    case 270:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.blue_l_270);
                                        break;
                                    default:
                                }
                            } else {
                                switch (dir) {
                                    case 0:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.red_l);
                                        break;
                                    case 90:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.red_l_90);
                                        break;
                                    case 180:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.red_l_180);
                                        break;
                                    case 270:
                                        DisplayBoard[i][j].setBackgroundResource(R.drawable.red_l_270);
                                        break;
                                    default:
                                }
                            }
                            break;

                        default:

                    }
                } else {
//                    DisplayBoard[i][j].setBackgroundResource(R.color.boardColor);
                }
            }
        }
    }

    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.R00) {
            clickedPosition = new Coordinates(0, 0);
        } else if (viewId == R.id.R01) {
            clickedPosition.setX(0);
            clickedPosition.setY(1);
        } else if (viewId == R.id.R02) {
            clickedPosition.setX(0);
            clickedPosition.setY(2);
        } else if (viewId == R.id.R03) {
            clickedPosition.setX(0);
            clickedPosition.setY(3);
        } else if (viewId == R.id.R04) {
            clickedPosition.setX(0);
            clickedPosition.setY(4);
        } else if (viewId == R.id.R05) {
            clickedPosition.setX(0);
            clickedPosition.setY(5);
        } else if (viewId == R.id.R06) {
            clickedPosition.setX(0);
            clickedPosition.setY(6);
        } else if (viewId == R.id.R07) {
            clickedPosition.setX(0);
            clickedPosition.setY(7);
        } else if (viewId == R.id.R08) {
            clickedPosition.setX(0);
            clickedPosition.setY(8);
        } else if (viewId == R.id.R09) {
            clickedPosition.setX(0);
            clickedPosition.setY(9);
        } else if (viewId == R.id.R10) {
            clickedPosition.setX(1);
            clickedPosition.setY(0);
        } else if (viewId == R.id.R11) {
            clickedPosition.setX(1);
            clickedPosition.setY(1);
        } else if (viewId == R.id.R12) {
            clickedPosition.setX(1);
            clickedPosition.setY(2);
        } else if (viewId == R.id.R13) {
            clickedPosition.setX(1);
            clickedPosition.setY(3);
        } else if (viewId == R.id.R14) {
            clickedPosition.setX(1);
            clickedPosition.setY(4);
        } else if (viewId == R.id.R15) {
            clickedPosition.setX(1);
            clickedPosition.setY(5);
        } else if (viewId == R.id.R16) {
            clickedPosition.setX(1);
            clickedPosition.setY(6);
        } else if (viewId == R.id.R17) {
            clickedPosition.setX(1);
            clickedPosition.setY(7);
        } else if (viewId == R.id.R18) {
            clickedPosition.setX(1);
            clickedPosition.setY(8);
        } else if (viewId == R.id.R19) {
            clickedPosition.setX(1);
            clickedPosition.setY(9);
        } else if (viewId == R.id.R20) {
            clickedPosition.setX(2);
            clickedPosition.setY(0);
        } else if (viewId == R.id.R21) {
            clickedPosition.setX(2);
            clickedPosition.setY(1);
        } else if (viewId == R.id.R22) {
            clickedPosition.setX(2);
            clickedPosition.setY(2);
        } else if (viewId == R.id.R23) {
            clickedPosition.setX(2);
            clickedPosition.setY(3);
        } else if (viewId == R.id.R24) {
            clickedPosition.setX(2);
            clickedPosition.setY(4);
        } else if (viewId == R.id.R25) {
            clickedPosition.setX(2);
            clickedPosition.setY(5);
        } else if (viewId == R.id.R26) {
            clickedPosition.setX(2);
            clickedPosition.setY(6);
        } else if (viewId == R.id.R27) {
            clickedPosition.setX(2);
            clickedPosition.setY(7);
        } else if (viewId == R.id.R28) {
            clickedPosition.setX(2);
            clickedPosition.setY(8);
        } else if (viewId == R.id.R29) {
            clickedPosition.setX(2);
            clickedPosition.setY(9);
        } else if (viewId == R.id.R30) {
            clickedPosition.setX(3);
            clickedPosition.setY(0);
        } else if (viewId == R.id.R31) {
            clickedPosition.setX(3);
            clickedPosition.setY(1);
        } else if (viewId == R.id.R32) {
            clickedPosition.setX(3);
            clickedPosition.setY(2);
        } else if (viewId == R.id.R33) {
            clickedPosition.setX(3);
            clickedPosition.setY(3);
        } else if (viewId == R.id.R34) {
            clickedPosition.setX(3);
            clickedPosition.setY(4);
        } else if (viewId == R.id.R35) {
            clickedPosition.setX(3);
            clickedPosition.setY(5);
        } else if (viewId == R.id.R36) {
            clickedPosition.setX(3);
            clickedPosition.setY(6);
        } else if (viewId == R.id.R37) {
            clickedPosition.setX(3);
            clickedPosition.setY(7);
        } else if (viewId == R.id.R38) {
            clickedPosition.setX(3);
            clickedPosition.setY(8);
        } else if (viewId == R.id.R39) {
            clickedPosition.setX(3);
            clickedPosition.setY(9);
        } else if (viewId == R.id.R40) {
            clickedPosition.setX(4);
            clickedPosition.setY(0);
        } else if (viewId == R.id.R41) {
            clickedPosition.setX(4);
            clickedPosition.setY(1);
        } else if (viewId == R.id.R42) {
            clickedPosition.setX(4);
            clickedPosition.setY(2);
        } else if (viewId == R.id.R43) {
            clickedPosition.setX(4);
            clickedPosition.setY(3);
        } else if (viewId == R.id.R44) {
            clickedPosition.setX(4);
            clickedPosition.setY(4);
        } else if (viewId == R.id.R45) {
            clickedPosition.setX(4);
            clickedPosition.setY(5);
        } else if (viewId == R.id.R46) {
            clickedPosition.setX(4);
            clickedPosition.setY(6);
        } else if (viewId == R.id.R47) {
            clickedPosition.setX(4);
            clickedPosition.setY(7);
        } else if (viewId == R.id.R48) {
            clickedPosition.setX(4);
            clickedPosition.setY(8);
        } else if (viewId == R.id.R49) {
            clickedPosition.setX(4);
            clickedPosition.setY(9);
        } else if (viewId == R.id.R50) {
            clickedPosition.setX(5);
            clickedPosition.setY(0);
        } else if (viewId == R.id.R51) {
            clickedPosition.setX(5);
            clickedPosition.setY(1);
        } else if (viewId == R.id.R52) {
            clickedPosition.setX(5);
            clickedPosition.setY(2);
        } else if (viewId == R.id.R53) {
            clickedPosition.setX(5);
            clickedPosition.setY(3);
        } else if (viewId == R.id.R54) {
            clickedPosition.setX(5);
            clickedPosition.setY(4);
        } else if (viewId == R.id.R55) {
            clickedPosition.setX(5);
            clickedPosition.setY(5);
        } else if (viewId == R.id.R56) {
            clickedPosition.setX(5);
            clickedPosition.setY(6);
        } else if (viewId == R.id.R57) {
            clickedPosition.setX(5);
            clickedPosition.setY(7);
        } else if (viewId == R.id.R58) {
            clickedPosition.setX(5);
            clickedPosition.setY(8);
        } else if (viewId == R.id.R59) {
            clickedPosition.setX(5);
            clickedPosition.setY(9);
        } else if (viewId == R.id.R60) {
            clickedPosition.setX(6);
            clickedPosition.setY(0);
        } else if (viewId == R.id.R61) {
            clickedPosition.setX(6);
            clickedPosition.setY(1);
        } else if (viewId == R.id.R62) {
            clickedPosition.setX(6);
            clickedPosition.setY(2);
        } else if (viewId == R.id.R63) {
            clickedPosition.setX(6);
            clickedPosition.setY(3);
        } else if (viewId == R.id.R64) {
            clickedPosition.setX(6);
            clickedPosition.setY(4);
        } else if (viewId == R.id.R65) {
            clickedPosition.setX(6);
            clickedPosition.setY(5);
        } else if (viewId == R.id.R66) {
            clickedPosition.setX(6);
            clickedPosition.setY(6);
        } else if (viewId == R.id.R67) {
            clickedPosition.setX(6);
            clickedPosition.setY(7);
        } else if (viewId == R.id.R68) {
            clickedPosition.setX(6);
            clickedPosition.setY(8);
        } else if (viewId == R.id.R69) {
            clickedPosition.setX(6);
            clickedPosition.setY(9);
        } else if (viewId == R.id.R70) {
            clickedPosition.setX(7);
            clickedPosition.setY(0);
        } else if (viewId == R.id.R71) {
            clickedPosition.setX(7);
            clickedPosition.setY(1);
        } else if (viewId == R.id.R72) {
            clickedPosition.setX(7);
            clickedPosition.setY(2);
        } else if (viewId == R.id.R73) {
            clickedPosition.setX(7);
            clickedPosition.setY(3);
        } else if (viewId == R.id.R74) {
            clickedPosition.setX(7);
            clickedPosition.setY(4);
        } else if (viewId == R.id.R75) {
            clickedPosition.setX(7);
            clickedPosition.setY(5);
        } else if (viewId == R.id.R76) {
            clickedPosition.setX(7);
            clickedPosition.setY(6);
        } else if (viewId == R.id.R77) {
            clickedPosition.setX(7);
            clickedPosition.setY(7);
        } else if (viewId == R.id.R78) {
            clickedPosition.setX(7);
            clickedPosition.setY(8);
//            boolean b = Board2[7][9].getPiece() instanceof Laser;
//            String stringValue = String.valueOf(b);
//            Log.w("test", stringValue);
        } else if (viewId == R.id.R79) {
            clickedPosition.setX(7);
            clickedPosition.setY(9);
            Log.w("myApp", "test");
        } else if (viewId == R.id.rotate_left) {
            rotatePieceLeft(Board[clickedPosition.getX()][clickedPosition.getY()].getPiece());
            Log.w("myAppLeft", "rotated");
            resetColorAtAllowedPosition(listOfCoordinates);
            setBoard();
            return;
        }else if (viewId == R.id.rotate_right) {
            rotatePieceRight(Board[clickedPosition.getX()][clickedPosition.getY()].getPiece());
            resetColorAtAllowedPosition(listOfCoordinates);
            Log.w("myAppRight", "rotated");
            setBoard();
            return;
        }


        if (!AnythingSelected) {
            if (Board[clickedPosition.getX()][clickedPosition.getY()].getPiece() == null) {
                return;
            } else { // если фигура есть на clickedPosition
                if (Board[clickedPosition.getX()][clickedPosition.getY()].getPiece().isWhite() != FirstPlayerTurn) { // если она ne соответствует ходу
                     // последние изменения
                }else { // смотрим какие ходы разрешены
                    listOfCoordinates.clear();
                    listOfCoordinates = Board[clickedPosition.getX()][clickedPosition.getY()].getPiece().AllowedMoves(clickedPosition, Board);
                    DisplayBoardBackground[clickedPosition.getX()][clickedPosition.getY()].setBackgroundResource(R.color.colorSelected);
                    AnythingSelected = true;
                    setColorAtAllowedPosition(listOfCoordinates);
                    if (Board[clickedPosition.getX()][clickedPosition.getY()].getPiece() instanceof DoubleMirror) {
                        DoubleMirrorSelected = true;
                    }
                } // мы выбрали фигуру
            }

        } else { // если уже что-то выбрано (2 клик)
            if (Board[clickedPosition.getX()][clickedPosition.getY()].getPiece() == null) {  // если куда мы ходим нет фигуры
                if (moveIsAllowed(listOfCoordinates, clickedPosition)) { // если ход разрешен

//                    saveBoard();
                    Board[clickedPosition.getX()][clickedPosition.getY()].setPiece(Board[lastPos.getX()][lastPos.getY()].getPiece()); // ставит фигуру на прошлой позиции на clickedPosition
                    Board[lastPos.getX()][lastPos.getY()].setPiece(null); // убирает фигуру на прошлой позиции
                    FirstPlayerTurn = !FirstPlayerTurn;// ход другому игроку
                    resetColorAtAllowedPosition(listOfCoordinates);
                    DisplayBoard[lastPos.getX()][lastPos.getY()].setBackgroundResource(0);
                    resetColorAtLastPosition(lastPos);
                    AnythingSelected = false;
                    DoubleMirrorSelected = false;

                } else { // если куда мы кликнули неразрешено то сбрасываем до 1 клика
                    resetColorAtLastPosition(lastPos);
                    resetColorAtAllowedPosition(listOfCoordinates);
                    AnythingSelected = false;
                    DoubleMirrorSelected = false;

                }

            } else { // если 2 клик куда мы кликнули есть фигура то выбираем ее и это теперь 1 клик
                resetColorAtAllowedPosition(listOfCoordinates);
                if (Board[clickedPosition.getX()][clickedPosition.getY()].getPiece().isWhite() != Board[lastPos.getX()][lastPos.getY()].getPiece().isWhite()){

                    Piece p = Board[clickedPosition.getX()][clickedPosition.getY()].getPiece();

                    if(moveIsAllowed(listOfCoordinates, clickedPosition)) {
                        if (DoubleMirrorSelected && (p instanceof Mirror || p instanceof Defender)){
                            Board[clickedPosition.getX()][clickedPosition.getY()].setPiece(Board[lastPos.getX()][lastPos.getY()].getPiece()); // ставит фигуру на прошлой позиции на clickedPosition
                            Board[lastPos.getX()][lastPos.getY()].setPiece(p); // меняет фигуру на прошлой позиции
                            laserAttack(wLaser.getDirection(), blueLaserCords);
                            FirstPlayerTurn = !FirstPlayerTurn;// ход другому игроку
                            resetColorAtAllowedPosition(listOfCoordinates);
                            DisplayBoard[lastPos.getX()][lastPos.getY()].setBackgroundResource(0);
                            resetColorAtLastPosition(lastPos);
                            AnythingSelected = false;
                            DoubleMirrorSelected = false;
                        }
                    } else {
                        AnythingSelected = false;
                        DoubleMirrorSelected = false;
                    }
                } else {

                    resetColorAtLastPosition(lastPos);
                    resetColorAtAllowedPosition(listOfCoordinates);
                    listOfCoordinates.clear();
                    listOfCoordinates = Board[clickedPosition.getX()][clickedPosition.getY()].getPiece().AllowedMoves(clickedPosition, Board);
                    DisplayBoardBackground[clickedPosition.getX()][clickedPosition.getY()].setBackgroundResource(R.color.colorSelected);
                    setColorAtAllowedPosition(listOfCoordinates);
                    AnythingSelected = true;
                    DoubleMirrorSelected = false;
                }
            }
        }
        lastPos = new Coordinates(clickedPosition.getX(), clickedPosition.getY());
        setBoard();
    }

    private void resetColorAtAllowedPosition(ArrayList<Coordinates> listOfCoordinates) {
        for(int i=0; i<listOfCoordinates.size(); i++){
            if (Board2[listOfCoordinates.get(i).getX()][listOfCoordinates.get(i).getY()].getPiece() == null){
                DisplayBoardBackground[listOfCoordinates.get(i).getX()][listOfCoordinates.get(i).getY()].setBackgroundResource(0);
            }
            else if(Board2[listOfCoordinates.get(i).getX()][listOfCoordinates.get(i).getY()].getPiece().isWhite()){
                DisplayBoardBackground[listOfCoordinates.get(i).getX()][listOfCoordinates.get(i).getY()].setBackgroundResource(R.drawable.blue_reserved_cell);
            }
            else {
                DisplayBoardBackground[listOfCoordinates.get(i).getX()][listOfCoordinates.get(i).getY()].setBackgroundResource(R.drawable.red_reserved_cell);
            }
            }
        }
    void setColorAtAllowedPosition(ArrayList<Coordinates> list){

        for(int i=0; i<list.size(); i++){
            if(Board[list.get(i).getX()][list.get(i).getY()].getPiece() == null){
                if(Board[list.get(i).getX()][list.get(i).getY()].getPiece() == null) {
                    DisplayBoardBackground[list.get(i).getX()][list.get(i).getY()].setBackgroundResource(R.color.transparent_green);
                }
            }
        }
    }

    private void rotatePieceRight(Piece p){
        if (p != null && p.isWhite() == FirstPlayerTurn) {
            p.setDirection(p.getDirection() + 90);
            if (p.getDirection() == 360){
                p.setDirection(0);
            }
            if (p.getDirection() < 0){
                p.setDirection(360 + p.getDirection());
            }
            AnythingSelected = false;
            FirstPlayerTurn = !FirstPlayerTurn;

        }
    }
    private void rotatePieceLeft(Piece p){
        if (p != null && p.isWhite() == FirstPlayerTurn) {
            p.setDirection(p.getDirection() - 90);
            if (p.getDirection() == 360){
                p.setDirection(0);
            }
            if (p.getDirection() < 0){
                p.setDirection(360 + p.getDirection());
            }
            AnythingSelected = false;
            FirstPlayerTurn = !FirstPlayerTurn;
        }
    }

    public void saveBoard(){
        numberOfMoves++;
        LastMoves.add(numberOfMoves-1 ,Board2 );

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                LastMoves.get(numberOfMoves-1)[i][j] = new Position(null);
            }
        }

        for(int g=0;g<8;g++){
            for(int h=0;h<8;h++){
                if(Board[g][h].getPiece()==null){
                    LastMoves.get(numberOfMoves-1)[g][h].setPiece(null);
                }else{
                    LastMoves.get(numberOfMoves-1)[g][h].setPiece(Board[g][h].getPiece());
                }
            }
        }
    }

    private boolean moveIsAllowed(ArrayList<Coordinates> allowedMoves, Coordinates clickedPosition) {
        Boolean Allowed = false;
        for(int i = 0;i < allowedMoves.size();i++){
            if(allowedMoves.get(i).getX() == clickedPosition.getX()  &&  allowedMoves.get(i).getY() == clickedPosition.getY()){
                if(!(Board2[clickedPosition.getX()][clickedPosition.getY()].getPiece() instanceof ReservedCell)) {
                    Allowed = true;
                    break;
                } else if(Board2[clickedPosition.getX()][clickedPosition.getY()].getPiece().isWhite() == FirstPlayerTurn){
                    Allowed = true;
                    break;
                }
            }
        }
        return Allowed;
    }

    private void resetColorAtLastPosition(Coordinates lastPos){
        if (Board2[lastPos.getX()][lastPos.getY()].getPiece() == null){
        DisplayBoardBackground[lastPos.getX()][lastPos.getY()].setBackgroundResource(0);
    }
        else if(Board2[lastPos.getX()][lastPos.getY()].getPiece().isWhite()){
            DisplayBoardBackground[lastPos.getX()][lastPos.getY()].setBackgroundResource(R.drawable.blue_reserved_cell);
        }
        else {
            DisplayBoardBackground[lastPos.getX()][lastPos.getY()].setBackgroundResource(R.drawable.red_reserved_cell);
        }
    }
    private void gameover(){
        Toast.makeText(this, "Game Over", Toast.LENGTH_LONG).show();
    }

    private Coordinates updateXY(int laserDirection, Coordinates coordinates){
        int x = coordinates.getX();
        int y = coordinates.getY();
        switch (laserDirection){
            case 0:
                x--;
                break;
            case 90:
                y++;
                break;
            case 180:
                x++;
                break;
            case 270:
                y--;
                break;
        }
        coordinates = new Coordinates(x, y);
        return coordinates;
    }
    private void laserAttack(int laserDirection, Coordinates coordinates) {

        if (coordinates.getX() > 7 || coordinates.getY() > 9 || coordinates.getX() < 0 || coordinates.getY() < 0){
            return;
        }
        String check;
        check = "" + coordinates.getX() + " " + coordinates.getY();
        Log.w("Check", check);

        switch (checkCell(coordinates, laserDirection)) {
            case STOP:
                Log.w("Switch", "Stopped");
                break;
            case KILL:
                Board[coordinates.getX()][coordinates.getY()].setPiece(null);
                DisplayBoardBackground[coordinates.getX()][coordinates.getY()].setBackgroundResource(0);
                break;
            case LEFT:
                laserDirection = 270;
                Log.w("Switch", "RotatedtoLeft");
                coordinates = updateXY(laserDirection, coordinates);
                laserAttack(laserDirection, coordinates);
                break;
            case RIGHT:
                laserDirection = 90;
                Log.w("Switch", "RotatedtoRight");
                coordinates = updateXY(laserDirection, coordinates);
                laserAttack(laserDirection, coordinates);
                break;
            case TOP:
                laserDirection = 0;
                Log.w("Switch", "RotatedtoTop");
                coordinates = updateXY(laserDirection, coordinates);
                laserAttack(laserDirection, coordinates);
                break;
            case BOTTOM:
                laserDirection = 180;
                Log.w("Switch", "RotatedtoBottom");
                coordinates = updateXY(laserDirection, coordinates);
                laserAttack(laserDirection, coordinates);
                break;

            case KING_KILL:
                Board[coordinates.getX()][coordinates.getY()].setPiece(null);
                gameover();
                return;
            case DOUBLE_MIRROR:
                Log.w("Switch", "Got DoubleMirror");
                break;
            case NOTHING:
                Log.w("Switch", "Nothing");
                coordinates = updateXY(laserDirection, coordinates);
                laserAttack(laserDirection, coordinates);
                break;
            case NULL:
                Log.w("Switch", "Got Null");
                break;
        }
    }
    private CellResult checkCell(Coordinates coordinates, int laserDirection){
        if (Board[coordinates.getX()][coordinates.getY()].getPiece() == null){
            return NOTHING;
        }
        int direction;
        Piece p = Board[coordinates.getX()][coordinates.getY()].getPiece();
        direction = p.getDirection();

        switch (laserDirection) {

            case 0: // up (top)
                switch (p.getClass().getSimpleName()) {
                    case "Defender":
                        switch (direction) {
                            case 180:
                                return STOP;
                            default:
                                return KILL;
                        }
                    case "Mirror":
                        switch (direction) {
                            case 0:
                                return LEFT;
                            case 90:
                            case 180:
                                return KILL;
                            case 270:
                                return RIGHT;
                        }
                    case "DoubleMirror":
                        switch (direction) {
                            case 0:
                                return LEFT;
                            case 90:
                                return RIGHT;
                            case 180:
                                return LEFT;
                            case 270:
                                return RIGHT;
                        }
                    case "Laser":
                        return STOP;
                    case "King":
                        return KING_KILL;
                }
                break;
            case 90: // left
                switch (p.getClass().getSimpleName()) {
                    case "Defender":
                        switch (direction) {
                            case 270:
                                return STOP;
                            default:
                                return KILL;
                        }
                    case "Mirror":
                        switch (direction) {
                            case 0:
                                return BOTTOM;
                            case 90:
                                return TOP;
                            case 180:
                                return KILL;
                            case 270:
                                return KILL;
                        }
                    case "DoubleMirror":
                        switch (direction) {
                            case 0:
                                return BOTTOM;
                            case 90:
                                return TOP;
                            case 180:
                                return BOTTOM;
                            case 270:
                                return TOP;
                        }
                    case "Laser":
                        return STOP;
                    case "King":
                        return KING_KILL;
                }
                break;
            case 180:
                switch (p.getClass().getSimpleName()) {
                    case "Defender":
                        switch (direction) {
                            case 0:
                                return STOP;
                            default:
                                return KILL;
                        }
                    case "Mirror":
                        switch (direction) {
                            case 0:
                                return KILL;
                            case 90:
                                return LEFT;
                            case 180:
                                return RIGHT;
                            case 270:
                                return KILL;
                        }
                    case "DoubleMirror":
                        switch (direction) {
                            case 0:
                                return RIGHT;
                            case 90:
                                return LEFT;
                            case 180:
                                return RIGHT;
                            case 270:
                                return LEFT;
                        }
                    case "Laser":
                        return STOP;
                    case "King":
                        return KING_KILL;
                }
                break;
            case 270:
                switch (p.getClass().getSimpleName()) {
                    case "Defender":
                        switch (direction) {
                            case 90:
                                return STOP;
                            default:
                                return KILL;
                        }
                    case "Mirror":
                        switch (direction) {
                            case 0:
                                return KILL;
                            case 90:
                                return KILL;
                            case 180:
                                return TOP;
                            case 270:
                                return BOTTOM;
                        }
                    case "DoubleMirror":
                        switch (direction) {
                            case 0:
                                return TOP;
                            case 90:
                                return BOTTOM;
                            case 180:
                                return TOP;
                            case 270:
                                return BOTTOM;
                        }
                    case "Laser":
                        return STOP;
                    case "King":
                        return KING_KILL;
                }
        }
        return NULL;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeBoard();
    }
}