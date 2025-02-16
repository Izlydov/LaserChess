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

import static Database.Converters.fromPositionArray;
import static Database.Converters.toPositionArray;
import static Database.LaserWayConverter.fromLaserWay;
import static Database.LaserWayConverter.toLaserWay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.Toast;

import com.example.laserchesstest.Pieces.Defender;
import com.example.laserchesstest.Pieces.DoubleMirror;
import com.example.laserchesstest.Pieces.King;
import com.example.laserchesstest.Pieces.Laser;
import com.example.laserchesstest.Pieces.Mirror;
import com.example.laserchesstest.Pieces.Piece;
import com.example.laserchesstest.Pieces.ReservedCell;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Database.GameBoard;
import Database.GameBoardDatabase;
import Database.RoomCodeGenerator;
import network.ApiClient;
import network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public Boolean FirstPlayerTurn = false;
    public ArrayList<Coordinates> listOfCoordinates = new ArrayList<>();
    public Position[][] Board = new Position[8][10];
    public Position[][] Board2 = new Position[8][10];
    public Position[][] Board3 = new Position[8][10];
    public Position[][] BoardSave = new Position[8][10];
    public GameBoard gameBoard;
    ApiService apiService;
    public GameBoardDatabase gameBoardDatabase;
    public Boolean AnythingSelected = false;
    public Coordinates lastPos = null;
    public Coordinates clickedPosition = new Coordinates(0, 0);
    public Coordinates blueLaserCords = new Coordinates(7, 9);
    public Coordinates redLaserCords = new Coordinates(0, 0);
    public ImageButton[][] DisplayBoard = new ImageButton[8][10];
    public ArrayList<Position[][]> LastMoves = new ArrayList<>();
    public ArrayList<Object[]> LaserWay = new ArrayList<>();
    public ArrayList<Coordinates> LaserWayReset = new ArrayList<>();
    public boolean isGameOver;
    public boolean isGameOnline;
    private Room currentRoom;
    private String roomCode;
    private String bluePlayer;
    private String redPlayer;
    private boolean isBlue;

    MenuActivity menuActivity = new MenuActivity();
    LayoutInflater inflater;

    Drawable[] layers;
    LayerDrawable layerDrawable;
    public int numberOfMoves;
    int prevDirection;


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
    Drawable blue_Mirror_0;
    Drawable blue_Mirror_90;
    Drawable blue_Mirror_180;
    Drawable blue_Mirror_270;
    Drawable blue_DoubleMirror_0;
    Drawable blue_DoubleMirror_90;
    Drawable blue_DoubleMirror_180;
    Drawable blue_DoubleMirror_270;
    Drawable blue_Defender_0;
    Drawable blue_Defender_90;
    Drawable blue_Defender_180;
    Drawable blue_Defender_270;
    Drawable blue_Laser_0;
    Drawable blue_Laser_90;
    Drawable blue_Laser_180;
    Drawable blue_Laser_270;
    Drawable blue_King;
    Drawable blue_Reserved_Cell;
    Drawable red_Mirror_0;
    Drawable red_Mirror_90;
    Drawable red_Mirror_180;
    Drawable red_Mirror_270;
    Drawable red_DoubleMirror_0;
    Drawable red_DoubleMirror_90;
    Drawable red_DoubleMirror_180;
    Drawable red_DoubleMirror_270;
    Drawable red_Defender_0;
    Drawable red_Defender_90;
    Drawable red_Defender_180;
    Drawable red_Defender_270;
    Drawable red_Laser_0;
    Drawable red_Laser_90;
    Drawable red_Laser_180;
    Drawable red_Laser_270;
    Drawable red_King;
    Drawable red_Reserved_Cell;
    Drawable blank_Cell;
    Drawable transparent_Green;
    Drawable laser_0;
    Drawable laser_90;
    Drawable laser_180;
    Drawable laser_270;
    Drawable laser_x;
    Drawable laser_LeftTop;
    Drawable laser_LeftBottom;
    Drawable laser_RightTop;
    Drawable laser_RightBottom;
    private String playerName;
    private Long lastProcessedMessageId;
    private String LaserWayJson;
    private String finalResult = "Победа";

    private void initializeBoard() {

        rLaser = new Laser(false, 180, "Laser");
        wLaser = new Laser(true, 0, "Laser");

        rKing = new King(false, 0, "King");
        wKing = new King(true, 0, "King");

        rDefender1 = new Defender(false, 180, "Defender");
        rDefender2 = new Defender(false, 180, "Defender");
        wDefender1 = new Defender(true, 0, "Defender");
        wDefender2 = new Defender(true, 0, "Defender");

        rDoubleMirror1 = new DoubleMirror(false, 0, "DoubleMirror");
        rDoubleMirror2 = new DoubleMirror(false, 90, "DoubleMirror");
        wDoubleMirror1 = new DoubleMirror(true, 90, "DoubleMirror");
        wDoubleMirror2 = new DoubleMirror(true, 0, "DoubleMirror");

        rMirror1 = new Mirror(false, 270, "Mirror");
        rMirror2 = new Mirror(false, 0, "Mirror");
        rMirror3 = new Mirror(false, 180, "Mirror");
        rMirror4 = new Mirror(false, 270, "Mirror");
        rMirror5 = new Mirror(false, 270, "Mirror");
        rMirror6 = new Mirror(false, 180, "Mirror");
        rMirror7 = new Mirror(false, 270, "Mirror");

        wMirror1 = new Mirror(true, 90, "Mirror");
        wMirror2 = new Mirror(true, 0, "Mirror");
        wMirror3 = new Mirror(true, 90, "Mirror");
        wMirror4 = new Mirror(true, 90, "Mirror");
        wMirror5 = new Mirror(true, 0, "Mirror");
        wMirror6 = new Mirror(true, 180, "Mirror");
        wMirror7 = new Mirror(true, 90, "Mirror");

        bReservedCell = new ReservedCell(true, 0, "Cell");
        rReservedCell = new ReservedCell(false, 0, "Cell");

        red_Mirror_0 = ContextCompat.getDrawable(this, R.drawable.red_b);
        red_Mirror_90 = ContextCompat.getDrawable(this, R.drawable.red_b_90);
        red_Mirror_180 = ContextCompat.getDrawable(this, R.drawable.red_b_180);
        red_Mirror_270 = ContextCompat.getDrawable(this, R.drawable.red_b_270);
        red_DoubleMirror_0 = ContextCompat.getDrawable(this, R.drawable.red_s);
        red_DoubleMirror_90 = ContextCompat.getDrawable(this, R.drawable.red_s_90);
        red_DoubleMirror_180 = ContextCompat.getDrawable(this, R.drawable.red_s_180);
        red_DoubleMirror_270 = ContextCompat.getDrawable(this, R.drawable.red_s_270);
        red_Defender_0 = ContextCompat.getDrawable(this, R.drawable.red_d);
        red_Defender_90 = ContextCompat.getDrawable(this, R.drawable.red_d_90);
        red_Defender_180 = ContextCompat.getDrawable(this, R.drawable.red_d_180);
        red_Defender_270 = ContextCompat.getDrawable(this, R.drawable.red_d_270);
        red_Laser_0 = ContextCompat.getDrawable(this, R.drawable.red_l);
        red_Laser_90 = ContextCompat.getDrawable(this, R.drawable.red_l_90);
        red_Laser_180 = ContextCompat.getDrawable(this, R.drawable.red_l_180);
        red_Laser_270 = ContextCompat.getDrawable(this, R.drawable.red_l_270);
        red_King = ContextCompat.getDrawable(this, R.drawable.red_k);
        red_Reserved_Cell = ContextCompat.getDrawable(this, R.drawable.red_reserved_cell);

        blue_Mirror_0 = ContextCompat.getDrawable(this, R.drawable.blue_b);
        blue_Mirror_90 = ContextCompat.getDrawable(this, R.drawable.blue_b_90);
        blue_Mirror_180 = ContextCompat.getDrawable(this, R.drawable.blue_b_180);
        blue_Mirror_270 = ContextCompat.getDrawable(this, R.drawable.blue_b_270);
        blue_DoubleMirror_0 = ContextCompat.getDrawable(this, R.drawable.blue_s);
        blue_DoubleMirror_90 = ContextCompat.getDrawable(this, R.drawable.blue_s_90);
        blue_DoubleMirror_180 = ContextCompat.getDrawable(this, R.drawable.blue_s_180);
        blue_DoubleMirror_270 = ContextCompat.getDrawable(this, R.drawable.blue_s_270);
        blue_Defender_0 = ContextCompat.getDrawable(this, R.drawable.blue_d);
        blue_Defender_90 = ContextCompat.getDrawable(this, R.drawable.blue_d_90);
        blue_Defender_180 = ContextCompat.getDrawable(this, R.drawable.blue_d_180);
        blue_Defender_270 = ContextCompat.getDrawable(this, R.drawable.blue_d_270);
        blue_Laser_0 = ContextCompat.getDrawable(this, R.drawable.blue_l);
        blue_Laser_90 = ContextCompat.getDrawable(this, R.drawable.blue_l_90);
        blue_Laser_180 = ContextCompat.getDrawable(this, R.drawable.blue_l_180);
        blue_Laser_270 = ContextCompat.getDrawable(this, R.drawable.blue_l_270);
        blue_King = ContextCompat.getDrawable(this, R.drawable.blue_k);
        blue_Reserved_Cell = ContextCompat.getDrawable(this, R.drawable.blue_reserved_cell);
        blank_Cell = ContextCompat.getDrawable(this, R.drawable.blank_cell);
        transparent_Green = ContextCompat.getDrawable(this, R.color.transparent_green);

        laser_0 = ContextCompat.getDrawable(this, R.drawable.laser_180half);
        laser_90 = ContextCompat.getDrawable(this, R.drawable.laser_90half);
        laser_180 = ContextCompat.getDrawable(this, R.drawable.laser_180half);
        laser_270 = ContextCompat.getDrawable(this, R.drawable.laser_90half);
        laser_LeftTop = ContextCompat.getDrawable(this, R.drawable.laser_left_top);
        laser_LeftBottom = ContextCompat.getDrawable(this, R.drawable.laser_left_bottom);
        laser_RightTop = ContextCompat.getDrawable(this, R.drawable.laser_right_top);
        laser_RightBottom = ContextCompat.getDrawable(this, R.drawable.laser_right_bottom);


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                Board[i][j] = new Position(null);
                Board2[i][j] = new Position(null);
                Board3[i][j] = new Position(null);
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

        DisplayBoard[0][0] = findViewById(R.id.R00);
        DisplayBoard[0][1] = findViewById(R.id.R01);
        DisplayBoard[0][2] = findViewById(R.id.R02);
        DisplayBoard[0][3] = findViewById(R.id.R03);
        DisplayBoard[0][4] = findViewById(R.id.R04);
        DisplayBoard[0][5] = findViewById(R.id.R05);
        DisplayBoard[0][6] = findViewById(R.id.R06);
        DisplayBoard[0][7] = findViewById(R.id.R07);
        DisplayBoard[0][8] = findViewById(R.id.R08);
        DisplayBoard[0][9] = findViewById(R.id.R09);

        DisplayBoard[1][0] = findViewById(R.id.R10);
        DisplayBoard[1][1] = findViewById(R.id.R11);
        DisplayBoard[1][2] = findViewById(R.id.R12);
        DisplayBoard[1][3] = findViewById(R.id.R13);
        DisplayBoard[1][4] = findViewById(R.id.R14);
        DisplayBoard[1][5] = findViewById(R.id.R15);
        DisplayBoard[1][6] = findViewById(R.id.R16);
        DisplayBoard[1][7] = findViewById(R.id.R17);
        DisplayBoard[1][8] = findViewById(R.id.R18);

        DisplayBoard[1][9] = findViewById(R.id.R19);
        DisplayBoard[2][0] = findViewById(R.id.R20);
        DisplayBoard[2][1] = findViewById(R.id.R21);
        DisplayBoard[2][2] = findViewById(R.id.R22);
        DisplayBoard[2][3] = findViewById(R.id.R23);
        DisplayBoard[2][4] = findViewById(R.id.R24);
        DisplayBoard[2][5] = findViewById(R.id.R25);
        DisplayBoard[2][6] = findViewById(R.id.R26);
        DisplayBoard[2][7] = findViewById(R.id.R27);
        DisplayBoard[2][8] = findViewById(R.id.R28);
        DisplayBoard[2][9] = findViewById(R.id.R29);

        DisplayBoard[3][0] = findViewById(R.id.R30);
        DisplayBoard[3][1] = findViewById(R.id.R31);
        DisplayBoard[3][2] = findViewById(R.id.R32);
        DisplayBoard[3][3] = findViewById(R.id.R33);
        DisplayBoard[3][4] = findViewById(R.id.R34);
        DisplayBoard[3][5] = findViewById(R.id.R35);
        DisplayBoard[3][6] = findViewById(R.id.R36);
        DisplayBoard[3][7] = findViewById(R.id.R37);
        DisplayBoard[3][8] = findViewById(R.id.R38);
        DisplayBoard[3][9] = findViewById(R.id.R39);

        DisplayBoard[4][0] = findViewById(R.id.R40);
        DisplayBoard[4][1] = findViewById(R.id.R41);
        DisplayBoard[4][2] = findViewById(R.id.R42);
        DisplayBoard[4][3] = findViewById(R.id.R43);
        DisplayBoard[4][4] = findViewById(R.id.R44);
        DisplayBoard[4][5] = findViewById(R.id.R45);
        DisplayBoard[4][6] = findViewById(R.id.R46);
        DisplayBoard[4][7] = findViewById(R.id.R47);
        DisplayBoard[4][8] = findViewById(R.id.R48);
        DisplayBoard[4][9] = findViewById(R.id.R49);

        DisplayBoard[5][0] = findViewById(R.id.R50);
        DisplayBoard[5][1] = findViewById(R.id.R51);
        DisplayBoard[5][2] = findViewById(R.id.R52);
        DisplayBoard[5][3] = findViewById(R.id.R53);
        DisplayBoard[5][4] = findViewById(R.id.R54);
        DisplayBoard[5][5] = findViewById(R.id.R55);
        DisplayBoard[5][6] = findViewById(R.id.R56);
        DisplayBoard[5][7] = findViewById(R.id.R57);
        DisplayBoard[5][8] = findViewById(R.id.R58);
        DisplayBoard[5][9] = findViewById(R.id.R59);

        DisplayBoard[6][0] = findViewById(R.id.R60);
        DisplayBoard[6][1] = findViewById(R.id.R61);
        DisplayBoard[6][2] = findViewById(R.id.R62);
        DisplayBoard[6][3] = findViewById(R.id.R63);
        DisplayBoard[6][4] = findViewById(R.id.R64);
        DisplayBoard[6][5] = findViewById(R.id.R65);
        DisplayBoard[6][6] = findViewById(R.id.R66);
        DisplayBoard[6][7] = findViewById(R.id.R67);
        DisplayBoard[6][8] = findViewById(R.id.R68);
        DisplayBoard[6][9] = findViewById(R.id.R69);

        DisplayBoard[7][0] = findViewById(R.id.R70);
        DisplayBoard[7][1] = findViewById(R.id.R71);
        DisplayBoard[7][2] = findViewById(R.id.R72);
        DisplayBoard[7][3] = findViewById(R.id.R73);
        DisplayBoard[7][4] = findViewById(R.id.R74);
        DisplayBoard[7][5] = findViewById(R.id.R75);
        DisplayBoard[7][6] = findViewById(R.id.R76);
        DisplayBoard[7][7] = findViewById(R.id.R77);
        DisplayBoard[7][8] = findViewById(R.id.R78);
        DisplayBoard[7][9] = findViewById(R.id.R79);

        DisplayBoard[0][1].setBackground(blue_Reserved_Cell);
        Board2[0][1].setPiece(bReservedCell);
        DisplayBoard[0][9].setBackground(blue_Reserved_Cell);
        Board2[0][9].setPiece(bReservedCell);
        DisplayBoard[1][9].setBackground(blue_Reserved_Cell);
        Board2[1][9].setPiece(bReservedCell);
        DisplayBoard[2][9].setBackground(blue_Reserved_Cell);
        Board2[2][9].setPiece(bReservedCell);
        DisplayBoard[3][9].setBackground(blue_Reserved_Cell);
        Board2[3][9].setPiece(bReservedCell);
        DisplayBoard[4][9].setBackground(blue_Reserved_Cell);
        Board2[4][9].setPiece(bReservedCell);
        DisplayBoard[5][9].setBackground(blue_Reserved_Cell);
        Board2[5][9].setPiece(bReservedCell);
        DisplayBoard[6][9].setBackground(blue_Reserved_Cell);
        Board2[6][9].setPiece(bReservedCell);
        DisplayBoard[7][1].setBackground(blue_Reserved_Cell);
        Board2[7][1].setPiece(bReservedCell);

        DisplayBoard[1][0].setBackground(red_Reserved_Cell);
        Board2[1][0].setPiece(rReservedCell);
        DisplayBoard[2][0].setBackground(red_Reserved_Cell);
        Board2[2][0].setPiece(rReservedCell);
        DisplayBoard[3][0].setBackground(red_Reserved_Cell);
        Board2[3][0].setPiece(rReservedCell);
        DisplayBoard[4][0].setBackground(red_Reserved_Cell);
        Board2[4][0].setPiece(rReservedCell);
        DisplayBoard[5][0].setBackground(red_Reserved_Cell);
        Board2[5][0].setPiece(rReservedCell);
        DisplayBoard[6][0].setBackground(red_Reserved_Cell);
        Board2[6][0].setPiece(rReservedCell);
        DisplayBoard[7][0].setBackground(red_Reserved_Cell);
        Board2[7][0].setPiece(rReservedCell);
        DisplayBoard[0][8].setBackground(red_Reserved_Cell);
        Board2[0][8].setPiece(rReservedCell);
        DisplayBoard[7][8].setBackground(red_Reserved_Cell);
        Board2[7][8].setPiece(rReservedCell);

        for(int g=0;g<8;g++){
            for(int h=0;h<10;h++){
                if(Board[g][h].getPiece()==null){
                    Board3[g][h].setPiece(null);
                    if(Board2[g][h].getPiece() == null) {
                        DisplayBoard[g][h].setBackground(blank_Cell);
                    }
                }else{
                    Board3[g][h].setPiece(Board[g][h].getPiece());
                }
            }
        }

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
                                DisplayBoard[i][j].setBackground(blue_King);
                            } else {
                                DisplayBoard[i][j].setBackground(red_King);
                            }
                            break;

                        case 1:
                            if (p.isWhite()) {
                                switch (dir) {
                                    case 0:
                                        DisplayBoard[i][j].setBackground(blue_Defender_0);
                                        break;
                                    case 90:
                                        DisplayBoard[i][j].setBackground(blue_Defender_90);
                                        break;
                                    case 180:
                                        DisplayBoard[i][j].setBackground(blue_Defender_180);
                                        break;
                                    case 270:
                                        DisplayBoard[i][j].setBackground(blue_Defender_270);
                                        break;
                                    default:
                                }
                            } else {
                                switch (dir) {
                                    case 0:
                                        DisplayBoard[i][j].setBackground(red_Defender_0);
                                        break;
                                    case 90:
                                        DisplayBoard[i][j].setBackground(red_Defender_90);
                                        break;
                                    case 180:
                                        DisplayBoard[i][j].setBackground(red_Defender_180);
                                        break;
                                    case 270:
                                        DisplayBoard[i][j].setBackground(red_Defender_270);
                                        break;
                                    default:
                                }
                            }
                            break;

                        case 2:
                            if (p.isWhite()) {
                                switch (dir) {
                                    case 0:
                                        DisplayBoard[i][j].setBackground(blue_DoubleMirror_0);
                                        break;
                                    case 90:
                                        DisplayBoard[i][j].setBackground(blue_DoubleMirror_90);
                                        break;
                                    case 180:
                                        DisplayBoard[i][j].setBackground(blue_DoubleMirror_180);
                                        break;
                                    case 270:
                                        DisplayBoard[i][j].setBackground(blue_DoubleMirror_270);
                                        break;
                                    default:
                                }
                            } else {
                                switch (dir) {
                                    case 0:
                                        DisplayBoard[i][j].setBackground(red_DoubleMirror_0);
                                        break;
                                    case 90:
                                        DisplayBoard[i][j].setBackground(red_DoubleMirror_90);
                                        break;
                                    case 180:
                                        DisplayBoard[i][j].setBackground(red_DoubleMirror_180);
                                        break;
                                    case 270:
                                        DisplayBoard[i][j].setBackground(red_DoubleMirror_270);
                                        break;
                                    default:
                                }
                            }
                            break;

                        case 3:
                            if (p.isWhite()) {
                                switch (dir) {
                                    case 0:
                                        DisplayBoard[i][j].setBackground(blue_Mirror_0);
                                        break;
                                    case 90:
                                        DisplayBoard[i][j].setBackground(blue_Mirror_90);
                                        break;
                                    case 180:
                                        DisplayBoard[i][j].setBackground(blue_Mirror_180);
                                        break;
                                    case 270:
                                        DisplayBoard[i][j].setBackground(blue_Mirror_270);
                                        break;
                                    default:
                                }
                            } else {
                                switch (dir) {
                                    case 0:
                                        DisplayBoard[i][j].setBackground(red_Mirror_0);
                                        break;
                                    case 90:
                                        DisplayBoard[i][j].setBackground(red_Mirror_90);
                                        break;
                                    case 180:
                                        DisplayBoard[i][j].setBackground(red_Mirror_180);
                                        break;
                                    case 270:
                                        DisplayBoard[i][j].setBackground(red_Mirror_270);
                                        break;
                                    default:
                                }

                            }
                            break;

                        case 4:
                            if (p.isWhite()) {
                                switch (dir) {
                                    case 0:
                                        DisplayBoard[i][j].setBackground(blue_Laser_0);
                                        break;
                                    case 90:
                                        DisplayBoard[i][j].setBackground(blue_Laser_90);
                                        break;
                                    case 180:
                                        DisplayBoard[i][j].setBackground(blue_Laser_180);
                                        break;
                                    case 270:
                                        DisplayBoard[i][j].setBackground(blue_Laser_270);
                                        break;
                                    default:
                                }
                            } else {
                                switch (dir) {
                                    case 0:
                                        DisplayBoard[i][j].setBackground(red_Laser_0);
                                        break;
                                    case 90:
                                        DisplayBoard[i][j].setBackground(red_Laser_90);
                                        break;
                                    case 180:
                                        DisplayBoard[i][j].setBackground(red_Laser_180);
                                        break;
                                    case 270:
                                        DisplayBoard[i][j].setBackground(red_Laser_270);
                                        break;
                                    default:
                                }
                            }
                            break;

                        default:

                    }
                }
            }
        }
    }

    public void onClick(View v) {

        resetLaserWay();
        setBoard();
        int viewId = v.getId();
        if (viewId == R.id.back) {
            if(isGameOnline) {
                showBackAlert(this, "Вы не сможете вернутся назад");
            } else{
                showBackAlert(this, "Не забудьте сохранить недоигранную партию");
            }


        } else if (viewId == R.id.info) {
            inflater = getLayoutInflater();
            menuActivity.showAlert(MainActivity.this, inflater, "Правила", getResources().getString(R.string.rules));
        }
        if(isGameOver){
            gameover();
            return;
        }
        if (isGameOnline) {
            if (isBlue != FirstPlayerTurn) {
                return; // Ожидаем хода другого игрока
            }
        }

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
        } else if (viewId == R.id.R79) {
            clickedPosition.setX(7);
            clickedPosition.setY(9);
        } else if (viewId == R.id.rotate_left) {
            rotatePieceLeft(Board[clickedPosition.getX()][clickedPosition.getY()].getPiece());
            Log.w("myAppLeft", "rotated");
            resetLaserWay();
            if(isGameOnline){
                sendMessage();
            }
            return;
        } else if (viewId == R.id.rotate_right) {
            rotatePieceRight(Board[clickedPosition.getX()][clickedPosition.getY()].getPiece());
            Log.w("myAppRight", "rotated");
            resetLaserWay();
            if(isGameOnline){
                sendMessage();
            }
            return;
        } else if (viewId == R.id.undo) {
            AnythingSelected = false;
            undo();
            resetColorAtAllowedPosition(listOfCoordinates);
            Log.w("myAppRight", "undo");
            setBoard();
            return;
        } else if (viewId == R.id.save) {
            gameBoard = new GameBoard();
            gameBoard.setBoard(Board);
            gameBoard.setPlayerTurn(FirstPlayerTurn);
            addGameBoardInBackground(gameBoard);
        }
        if (!AnythingSelected) {
            if (Board[clickedPosition.getX()][clickedPosition.getY()].getPiece() == null) {
                setBoard();
                return;
            } else { // если фигура есть на clickedPosition
                if (Board[clickedPosition.getX()][clickedPosition.getY()].getPiece().isWhite() != FirstPlayerTurn) { // если она ne соответствует ходу
                    // последние изменения
                } else { // смотрим какие ходы разрешены
                    listOfCoordinates.clear();
                    listOfCoordinates = Board[clickedPosition.getX()][clickedPosition.getY()].getPiece().AllowedMoves(clickedPosition, Board);
                    AnythingSelected = true;
                    setColorAtAllowedPosition(listOfCoordinates);
                } // мы выбрали фигуру
            }

        } else { // если уже что-то выбрано (2 клик)
            if (Board[clickedPosition.getX()][clickedPosition.getY()].getPiece() == null) {  // если куда мы ходим нет фигуры
                if (moveIsAllowed(listOfCoordinates, clickedPosition)) { // если ход разрешен

                    saveBoard();
                    Board[clickedPosition.getX()][clickedPosition.getY()].setPiece(Board[lastPos.getX()][lastPos.getY()].getPiece()); // ставит фигуру на прошлой позиции на clickedPosition
                    Board[lastPos.getX()][lastPos.getY()].setPiece(null); // убирает фигуру на прошлой позиции
                    startLaserAttack(FirstPlayerTurn);
                    FirstPlayerTurn = !FirstPlayerTurn;// ход другому игроку
                    resetColorAtAllowedPosition(listOfCoordinates);
                    DisplayBoard[lastPos.getX()][lastPos.getY()].setBackground(blank_Cell);
                    resetColorAtLastPosition(lastPos);
                    AnythingSelected = false;
                    if (isGameOnline) {
                        sendMessage();
                    }

                } else { // если куда мы кликнули неразрешено то сбрасываем до 1 клика
                    resetColorAtLastPosition(lastPos);
                    resetColorAtAllowedPosition(listOfCoordinates);
                    AnythingSelected = false;
                }

            } else { // если 2 клик куда мы кликнули есть фигура то выбираем ее и это теперь 1 клик
                resetColorAtAllowedPosition(listOfCoordinates);
                if (Board[clickedPosition.getX()][clickedPosition.getY()].getPiece().isWhite() != Board[lastPos.getX()][lastPos.getY()].getPiece().isWhite()) {

                    Piece p = Board[clickedPosition.getX()][clickedPosition.getY()].getPiece();

                    if (moveIsAllowed(listOfCoordinates, clickedPosition)) {
                        if (Board[lastPos.getX()][lastPos.getY()].getPiece() instanceof DoubleMirror && (p instanceof Mirror || p instanceof Defender)) {
                            saveBoard();
                            Board[clickedPosition.getX()][clickedPosition.getY()].setPiece(Board[lastPos.getX()][lastPos.getY()].getPiece()); // ставит фигуру на прошлой позиции на clickedPosition
                            Board[lastPos.getX()][lastPos.getY()].setPiece(p); // меняет фигуру на прошлой позиции
                            startLaserAttack(FirstPlayerTurn);
                            FirstPlayerTurn = !FirstPlayerTurn;// ход другому игроку
                            resetColorAtAllowedPosition(listOfCoordinates);
                            DisplayBoard[lastPos.getX()][lastPos.getY()].setBackground(blank_Cell);
                            resetColorAtLastPosition(lastPos);
                            AnythingSelected = false;
                            if (isGameOnline) {
                                sendMessage();
                            }
                        }
                    } else {
                        AnythingSelected = false;
                    }
                } else {

                    resetColorAtLastPosition(lastPos);
                    resetColorAtAllowedPosition(listOfCoordinates);
                    listOfCoordinates.clear();
                    listOfCoordinates = Board[clickedPosition.getX()][clickedPosition.getY()].getPiece().AllowedMoves(clickedPosition, Board);
                    DisplayBoard[clickedPosition.getX()][clickedPosition.getY()].setBackground(transparent_Green);
                    setColorAtAllowedPosition(listOfCoordinates);
                    AnythingSelected = true;
                }
            }
        }
        lastPos = new Coordinates(clickedPosition.getX(), clickedPosition.getY());
        setBoard();
    }

    private void resetColorAtAllowedPosition(ArrayList<Coordinates> listOfCoordinates) {
        for (int i = 0; i < listOfCoordinates.size(); i++) {
            if (Board2[listOfCoordinates.get(i).getX()][listOfCoordinates.get(i).getY()].getPiece() == null) {
                DisplayBoard[listOfCoordinates.get(i).getX()][listOfCoordinates.get(i).getY()].setBackground(blank_Cell);
            } else if (Board2[listOfCoordinates.get(i).getX()][listOfCoordinates.get(i).getY()].getPiece().isWhite()) {
                DisplayBoard[listOfCoordinates.get(i).getX()][listOfCoordinates.get(i).getY()].setBackground(blue_Reserved_Cell);
            } else {
                DisplayBoard[listOfCoordinates.get(i).getX()][listOfCoordinates.get(i).getY()].setBackground(red_Reserved_Cell);
            }
        }
    }

    void setColorAtAllowedPosition(ArrayList<Coordinates> list) {

        for (int i = 0; i < list.size(); i++) {
            if (Board[list.get(i).getX()][list.get(i).getY()].getPiece() == null) {
                layers = new Drawable[2];
                layers[0] = DisplayBoard[list.get(i).getX()][list.get(i).getY()].getBackground();
                layers[1] = transparent_Green;
                layerDrawable = new LayerDrawable(layers);
                DisplayBoard[list.get(i).getX()][list.get(i).getY()].setBackground(layerDrawable);
            }
        }
    }

    private void rotatePieceRight(Piece p) {
        if (p != null && p.isWhite() == FirstPlayerTurn) {
            saveBoard();
            p.setDirection(p.getDirection() + 90);
            if (p.getDirection() == 360) {
                p.setDirection(0);
            }
            if (p.getDirection() < 0) {
                p.setDirection(360 + p.getDirection());
            }
            startLaserAttack(FirstPlayerTurn);
            AnythingSelected = false;
            FirstPlayerTurn = !FirstPlayerTurn;
            resetColorAtAllowedPosition(listOfCoordinates);
        }
        setBoard();
    }

    private void rotatePieceLeft(Piece p) {
        if (p != null && p.isWhite() == FirstPlayerTurn) {
            saveBoard();
            p.setDirection(p.getDirection() - 90);
            if (p.getDirection() == 360) {
                p.setDirection(0);
            }
            if (p.getDirection() < 0) {
                p.setDirection(360 + p.getDirection());
            }
            startLaserAttack(FirstPlayerTurn);
            AnythingSelected = false;
            FirstPlayerTurn = !FirstPlayerTurn;
            resetColorAtAllowedPosition(listOfCoordinates);
        }
        setBoard();
    }

    public void saveBoard() {
        numberOfMoves++;
        LastMoves.add(numberOfMoves - 1, Board3);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 10; j++) {
                LastMoves.get(numberOfMoves - 1)[i][j] = new Position(null);
            }
        }

        for (int g = 0; g < 8; g++) {
            for (int h = 0; h < 10; h++) {
                if (Board[g][h].getPiece() == null) {
                    LastMoves.get(numberOfMoves - 1)[g][h].setPiece(null);
                } else {
                    Piece p = Board[g][h].getPiece();
                    LastMoves.get(numberOfMoves - 1)[g][h].setPiece(p.clone());
                }
            }
        }
    }

    private boolean moveIsAllowed(ArrayList<Coordinates> allowedMoves, Coordinates clickedPosition) {
        boolean Allowed = false;
        for (int i = 0; i < allowedMoves.size(); i++) {
            if (allowedMoves.get(i).getX() == clickedPosition.getX() && allowedMoves.get(i).getY() == clickedPosition.getY()) {
                if (!(Board2[clickedPosition.getX()][clickedPosition.getY()].getPiece() instanceof ReservedCell)) {
                    Allowed = true;
                    break;
                } else if (Board2[clickedPosition.getX()][clickedPosition.getY()].getPiece().isWhite() == FirstPlayerTurn) {
                    Allowed = true;
                    break;
                }
            }
        }
        return Allowed;
    }

    private void resetColorAtLastPosition(Coordinates lastPos) {
        if (Board2[lastPos.getX()][lastPos.getY()].getPiece() == null) {
            DisplayBoard[lastPos.getX()][lastPos.getY()].setBackground(blank_Cell);
        } else if (Board2[lastPos.getX()][lastPos.getY()].getPiece().isWhite()) {
            DisplayBoard[lastPos.getX()][lastPos.getY()].setBackground(blue_Reserved_Cell);
        } else {
            DisplayBoard[lastPos.getX()][lastPos.getY()].setBackground(red_Reserved_Cell);
        }
    }

    private void gameover() {
        showGameOverAlert(this, "Игра окончена", finalResult);
    }

    public void undo(){
        String s = String.valueOf(numberOfMoves);
        Log.w("123", s);
        if(numberOfMoves>0) {
            for(int g=0;g<8;g++){
                for(int h=0;h<10;h++){
                    if(LastMoves.get(numberOfMoves-1)[g][h].getPiece()==null){
                        Board[g][h].setPiece(null);
                        resetColorAtLastPosition(new Coordinates(g, h));
                    }else{
                        Board[g][h].setPiece(LastMoves.get(numberOfMoves-1)[g][h].getPiece());
                    }
                }
            }
            numberOfMoves--;
            AnythingSelected = false;

            setBoard();
            resetLaserWay();
            FirstPlayerTurn = !FirstPlayerTurn;
        }
    }


    private Coordinates updateXY(int laserDirection, Coordinates coordinates) {
        int x = coordinates.getX();
        int y = coordinates.getY();
        switch (laserDirection) {
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
    private void startLaserAttack(Boolean FirstPlayerTurn){
        Log.w("LaserAttackStart", "Started");
        if (FirstPlayerTurn){
            int laserDirection = Board[blueLaserCords.getX()][blueLaserCords.getY()].getPiece().getDirection();
            laserAttack(laserDirection, updateXY(laserDirection, blueLaserCords)); }
        if (!FirstPlayerTurn) {
            int laserDirection = Board[redLaserCords.getX()][redLaserCords.getY()].getPiece().getDirection();
            laserAttack(laserDirection, updateXY(laserDirection, redLaserCords)); }
    }

    private void laserAttack(int laserDirection, Coordinates coordinates) {

        if (coordinates.getX() > 7 || coordinates.getY() > 9 || coordinates.getX() < 0 || coordinates.getY() < 0) {
            drawLaserWay();
            return;
        }
        String check;
        check = "" + coordinates.getX() + " " + coordinates.getY();
        Log.w("Check", check);
        switch (checkCell(coordinates, laserDirection)) {
            case STOP:
                Log.w("Switch", "Stopped");
                addCell(STOP, coordinates, laserDirection, prevDirection);
                drawLaserWay();
                break;
            case KILL:
                prevDirection = laserDirection;
                addCell(KILL, coordinates, laserDirection, prevDirection);
                Board[coordinates.getX()][coordinates.getY()].setPiece(null);
                resetColorAtLastPosition(coordinates);
                drawLaserWay();
                break;
            case LEFT:
                prevDirection = laserDirection;
                laserDirection = 270;
                Log.w("Switch", "RotatedtoLeft");
                addCell(LEFT, coordinates, laserDirection, prevDirection);
                coordinates = updateXY(laserDirection, coordinates);
                laserAttack(laserDirection, coordinates);
                break;
            case RIGHT:
                prevDirection = laserDirection;
                laserDirection = 90;
                Log.w("Switch", "RotatedtoRight");
                addCell(RIGHT, coordinates, laserDirection, prevDirection);
                coordinates = updateXY(laserDirection, coordinates);
                laserAttack(laserDirection, coordinates);
                break;
            case TOP:
                prevDirection = laserDirection;
                laserDirection = 0;
                Log.w("Switch", "RotatedtoTop");
                addCell(TOP, coordinates, laserDirection, prevDirection);
                coordinates = updateXY(laserDirection, coordinates);
                laserAttack(laserDirection, coordinates);
                break;
            case BOTTOM:
                prevDirection = laserDirection;
                laserDirection = 180;
                Log.w("Switch", "RotatedtoBottom");
                addCell(BOTTOM, coordinates, laserDirection, prevDirection);
                coordinates = updateXY(laserDirection, coordinates);
                laserAttack(laserDirection, coordinates);
                break;

            case KING_KILL:
                if(Board[coordinates.getX()][coordinates.getY()].getPiece().isWhite()){
                    finalResult = "Победа красных";
                } else {
                    finalResult = "Победа синих";
                }
                Board[coordinates.getX()][coordinates.getY()].setPiece(null);
                drawLaserWay();
                gameover();
                if(isGameOnline){
                    sendMessage();
                }
                return;
            case NOTHING:
                prevDirection = laserDirection;
                Log.w("Switch", "Nothing");
                addCell(NOTHING, coordinates, laserDirection, prevDirection);
                coordinates = updateXY(laserDirection, coordinates);
                laserAttack(laserDirection, coordinates);
                break;
            case NULL:
                Log.w("Switch", "Got Null");
                break;
        }
    }
    public void addCell(CellResult result, Coordinates coordinates, int laserDirection, int prevDirection) {
        Object[] cell = {result, coordinates, laserDirection, prevDirection};
        LaserWay.add(cell);
    }
    public void drawLaserWay() {
        Handler handler = new Handler();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Object[] cell : LaserWay){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            CellResult result = (CellResult) cell[0];
                            Coordinates coordinates = (Coordinates) cell[1];
                            int laserDirection = (int) cell[2];
                            int prevDirection = (int) cell[3];
                            switch (laserDirection) {
                                case 0:
                                    laser_x = laser_0;
                                    break;
                                case 90:
                                    laser_x = laser_90;
                                    break;
                                case 180:
                                    laser_x = laser_180;
                                    break;
                                case 270:
                                    laser_x = laser_270;
                                    break;
                            }
                            switch (result) {
                                case KILL:
                                    break;
                                case LEFT:
                                    if (prevDirection == 0) {
                                        Drawable[] layers = new Drawable[2];
                                        layers[0] = DisplayBoard[coordinates.getX()][coordinates.getY()].getBackground();
                                        layers[1] = laser_LeftBottom;
                                        layerDrawable = new LayerDrawable(layers);
                                        DisplayBoard[coordinates.getX()][coordinates.getY()].setBackground(layerDrawable);
                                        break;
                                    }
                                    if (prevDirection == 180) {
                                        Drawable[] layers = new Drawable[2];
                                        layers[0] = DisplayBoard[coordinates.getX()][coordinates.getY()].getBackground();
                                        layers[1] = laser_LeftTop;
                                        layerDrawable = new LayerDrawable(layers);
                                        DisplayBoard[coordinates.getX()][coordinates.getY()].setBackground(layerDrawable);
                                        break;
                                    }
                                case RIGHT:
                                    if (prevDirection == 0) {
                                        Drawable[] layers = new Drawable[2];
                                        layers[0] = DisplayBoard[coordinates.getX()][coordinates.getY()].getBackground();
                                        layers[1] = laser_RightBottom;
                                        layerDrawable = new LayerDrawable(layers);
                                        DisplayBoard[coordinates.getX()][coordinates.getY()].setBackground(layerDrawable);
                                        break;
                                    }
                                    if (prevDirection == 180) {
                                        Drawable[] layers = new Drawable[2];
                                        layers[0] = DisplayBoard[coordinates.getX()][coordinates.getY()].getBackground();
                                        layers[1] = laser_RightTop;
                                        layerDrawable = new LayerDrawable(layers);
                                        DisplayBoard[coordinates.getX()][coordinates.getY()].setBackground(layerDrawable);
                                        break;
                                    }
                                    break;
                                case TOP:
                                    if (prevDirection == 90) {
                                        Drawable[] layers = new Drawable[2];
                                        layers[0] = DisplayBoard[coordinates.getX()][coordinates.getY()].getBackground();
                                        layers[1] = laser_LeftTop;
                                        layerDrawable = new LayerDrawable(layers);
                                        DisplayBoard[coordinates.getX()][coordinates.getY()].setBackground(layerDrawable);
                                        break;
                                    }
                                    if (prevDirection == 270) {
                                        Drawable[] layers = new Drawable[2];
                                        layers[0] = DisplayBoard[coordinates.getX()][coordinates.getY()].getBackground();
                                        layers[1] = laser_RightTop;
                                        layerDrawable = new LayerDrawable(layers);
                                        DisplayBoard[coordinates.getX()][coordinates.getY()].setBackground(layerDrawable);
                                        break;
                                    }
                                case BOTTOM:
                                    if (prevDirection == 90) {
                                        Drawable[] layers = new Drawable[2];
                                        layers[0] = DisplayBoard[coordinates.getX()][coordinates.getY()].getBackground();
                                        layers[1] = laser_LeftBottom;
                                        layerDrawable = new LayerDrawable(layers);
                                        DisplayBoard[coordinates.getX()][coordinates.getY()].setBackground(layerDrawable);
                                        break;
                                    }
                                    if (prevDirection == 270) {
                                        Drawable[] layers = new Drawable[2];
                                        layers[0] = DisplayBoard[coordinates.getX()][coordinates.getY()].getBackground();
                                        layers[1] = laser_RightBottom;
                                        layerDrawable = new LayerDrawable(layers);
                                        DisplayBoard[coordinates.getX()][coordinates.getY()].setBackground(layerDrawable);
                                        break;
                                    }
                                case NOTHING:
                                    LaserWayReset.add(coordinates);
                                    Drawable[] layers = new Drawable[2];
                                    layers[0] = DisplayBoard[coordinates.getX()][coordinates.getY()].getBackground();
                                    layers[1] = laser_x;
                                    layerDrawable = new LayerDrawable(layers);
                                    DisplayBoard[coordinates.getX()][coordinates.getY()].setBackground(layerDrawable);
                                    break;
                            }
//                            String s = "Result: " + result + ", Coordinates: (" + coordinates.getX() + ", " + coordinates.getY() + ")" + ", laserDirection = " + laserDirection;
//                            Log.w("draw", s);
                        }
                    },  10);
                }
                LaserWayJson = fromLaserWay(LaserWay);
                LaserWay.clear();
            }

        });
    }
    public void resetLaserWay(){
        for (Coordinates coordinates : LaserWayReset) {
            if(Board2[coordinates.getX()][coordinates.getY()].getPiece() != null){
                if(Board2[coordinates.getX()][coordinates.getY()].getPiece().isWhite()){
                    DisplayBoard[coordinates.getX()][coordinates.getY()].setBackground(blue_Reserved_Cell);
                }
                else{
                    DisplayBoard[coordinates.getX()][coordinates.getY()].setBackground(red_Reserved_Cell);
                }
            }
            else {
                DisplayBoard[coordinates.getX()][coordinates.getY()].setBackground(blank_Cell);
            }
        }
        LaserWayReset.clear();
    }

    private CellResult checkCell(Coordinates coordinates, int laserDirection) {
        if (Board[coordinates.getX()][coordinates.getY()].getPiece() == null) {
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
    public void showGameOverAlert(Context context, String title, String message) {
        isGameOver = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("В меню", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent i = new Intent(MainActivity.this, MenuActivity.class);
                        startActivity(i);
                    }
                })
                .setNeutralButton("Новая игра", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                })
                .show();
    }
    public void showBackAlert(Context context, String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Система")
                .setMessage(text)
                .setNeutralButton("В меню", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent i = new Intent(MainActivity.this, MenuActivity.class);
                        startActivity(i);
                    }
                })
                .setPositiveButton("Назад", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    public void addGameBoardInBackground(GameBoard gameBoard) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                gameBoardDatabase.getGameBoardDao().addGameBoard(gameBoard);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };

        gameBoardDatabase = androidx.room.Room.databaseBuilder(getApplicationContext(), GameBoardDatabase.class, "GameBoardDB")
                .addCallback(myCallBack)
                .build();

        int saveId = getIntent().getIntExtra("saveId", -1);
        if(saveId == -1) {
            initializeBoard();
        } else {
            initializeBoard();
            getBoardInBackground(saveId);
        }

        apiService = ApiClient.getApiService();
        isGameOnline = getIntent().getBooleanExtra("isGameOnline", false);
        if(isGameOnline){
            TextView localSave = findViewById(R.id.save);
            ImageView undo = findViewById(R.id.undo);
            undo.setClickable(false);
            undo.setImageResource(0);
            localSave.setClickable(false);
            localSave.setBackground(null);
            startMessageFetching();
            roomCode = getIntent().getStringExtra("roomCode");
            isBlue = getIntent().getBooleanExtra("isPlayer1", false);
            getRoomByRoomCode(roomCode, new RoomCallback() {
                @Override
                public void onSuccess(Room room) {
                    currentRoom = room;
                    bluePlayer = currentRoom.getPlayer1();
                    redPlayer = currentRoom.getPlayer2();
                    if (isBlue) {
                        playerName = bluePlayer;
                    } else {
                        playerName = redPlayer;
                    }
                    Log.d("MainActivity", "Room found: " + currentRoom.getRoomCode());
                    Log.d("MainActivity", "Blue: " + currentRoom.getPlayer1());
                    Log.d("MainActivity", "Red: " + currentRoom.getPlayer2());
                }
                @Override
                public void onError(String errorMessage) {
                    Log.e("MainActivity", errorMessage);
                }
            });
        }
    }

    private void getBoardInBackground(int id) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                gameBoard = gameBoardDatabase.getGameBoardDao().getGameBoardById(id);
                if (gameBoard != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            BoardSave = gameBoard.getBoard();
                            FirstPlayerTurn = gameBoard.isPlayerTurn();
                            setBoardSave(BoardSave);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.w("MainActivity", "GameBoard not found for ID: " + id);
                        }
                    });
                }
            }
        });
    }

    private void startMessageFetching() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000); // Проверка каждые 2 секунды
                    fetchMessages();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void fetchMessages() {
        apiService.getLastMessageByRoomCode(roomCode).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Message message = response.body();
                    if (!message.getId().equals(lastProcessedMessageId)) {
                        lastProcessedMessageId = message.getId();
                        if(response.body().getSender() != playerName) {
                            handleReceivedMessage(response.body());
                        } else {
                            Log.w("mainActivity", "Ignoring own message: " + message.getId());
                        }
                    } else {
                    Log.w("mainActivity", "Ignoring checked message: " + message.getId());
                }
                } else {
                    Log.e("MainActivity", "Error fetching last message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e("MainActivity", "Error fetching last message", t);
            }
        });
    }
    private void sendMessage() {
        Message message = new Message();
        message.setRoom(currentRoom);
        message.setSender(playerName);
        message.setContent("");
        message.setLaserWay(LaserWayJson);
        if(isGameOver){
            message.setContent("gameOver");
        }
        message.setPositions(fromPositionArray(Board));
        Log.d("MainActivity", "Message sent + " + message.getSender());

        apiService.sendMessage(message).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (!response.isSuccessful()) {
                    Log.e("MainActivity", "Error sending message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e("MainActivity", "Error sending message", t);
            }
        });
    }
    private void handleReceivedMessage(Message message) {
        if(message.getContent().equals("StartMessage")){
            Log.w("mainActivity", "got start message" + message.getId());
            return;
        }
        if (!message.getSender().equals(playerName)) {
            if(message.getContent().equals("gameOver")) {
                gameover();
                isGameOver = true;
            }
            Position[][] receivedBoard = toPositionArray(message.getPositions());
            LaserWay = toLaserWay(message.getLaserWay());
            Log.w("mainActivity", "got board");
            runOnUiThread(() -> {
                setBoardSave(receivedBoard);
                drawLaserWay();
                FirstPlayerTurn = !FirstPlayerTurn;
            });
        }
    }
    private void getRoomByRoomCode(String roomCode, RoomCallback callback) {
        apiService.getRoomByCode(roomCode).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error getting room: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                callback.onError("Error getting room: " + t.getMessage());
            }
        });
    }


    private void setBoardSave(Position[][] BoardSave){
        if(BoardSave != null) {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 10; y++) {
                    Board[x][y].setPiece(null);
                    resetColorAtLastPosition(new Coordinates(x, y));
                }
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 10; j++) {
                    if (BoardSave[i][j].getPiece() == null) {
                        Board[i][j].setPiece(null);
                    } else {
                        String name = BoardSave[i][j].getPiece().getName();
                        int direction = BoardSave[i][j].getPiece().getDirection();
                        boolean white = BoardSave[i][j].getPiece().isWhite();
                        Piece p = null;
                        switch (name) {
                            case "Laser":
                                p = new Laser(white, direction, name);
                                break;
                            case "Mirror":
                                p = new Mirror(white, direction, name);
                                break;
                            case "DoubleMirror":
                                p = new DoubleMirror(white, direction, name);
                                break;
                            case "Defender":
                                p = new Defender(white, direction, name);
                                break;
                            case "King":
                                p = new King(white, direction, name);
                                break;
                            default:
                                p = null;
                                Log.w("MainActivity", "nullPiece");
                        }
                        Board[i][j].setPiece(p);
                    }
                }
            }
            Log.w("setBoardSave", "Setted");
            setBoard();
        } else {
            Log.e("MainActivity", "Received board is null");
        }
    }

}