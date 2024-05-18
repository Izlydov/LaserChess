package com.example.laserchesstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Database.GameBoard;
import Database.GameBoardDatabase;

public class MenuActivity extends AppCompatActivity {
    Button playButton, rulesButton, savesButton;
    Intent intent;
    LayoutInflater inflater;
    int saves;

    GameBoardDatabase gameBoardDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        savesButton = findViewById(R.id.button_save);
        playButton = findViewById(R.id.button_play);
        rulesButton = findViewById(R.id.button_rules);
        inflater = getLayoutInflater();

        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };

        gameBoardDatabase = Room.databaseBuilder(getApplicationContext(), GameBoardDatabase.class, "GameBoardDB").addCallback(myCallBack).build();
        getSavesCountInBackGround();
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        rulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(MenuActivity.this, inflater, "Правила", getResources().getString(R.string.rules));
            }
        });
        savesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuActivity.this, SavesActivity.class);
                intent.putExtra("count", saves);
                startActivity(intent);
            }
        });
    }





    public void showAlert(Context context, LayoutInflater inflater, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setNeutralButton("Фигуры", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        showAlertPieces(context, inflater, "Фигуры");
                    }
                })
                .show();
    }
    public void showAlertPieces(Context context, LayoutInflater inflater, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View customContentView = inflater.inflate(R.layout.custom_dialog_content, null);
        builder.setTitle(title)
//                .setIcon(R.drawable.blue_d)
                .setView(customContentView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
    public void getSavesCountInBackGround() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                saves = gameBoardDatabase.getGameBoardDao().getGameBoardCount();
            }
        });
    }
}