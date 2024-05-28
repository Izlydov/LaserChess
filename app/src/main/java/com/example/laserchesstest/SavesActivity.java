package com.example.laserchesstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Database.GameBoard;
import Database.GameBoardDatabase;

public class SavesActivity extends AppCompatActivity implements SaveAdapter.OnItemClickListener {
    Button playButton, deleteButton;
    Intent intent;
    RecyclerView recyclerView;
    private int lastPosition;
    public boolean isEmptySaves;
    public GameBoardDatabase gameBoardDatabase;
    public List<GameBoard> savedGames;
    String[] savedGamesTexts;
    public int[] savedGamesIds;
    public int saves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saves);
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
        playButton = findViewById(R.id.button_play);
        deleteButton = findViewById(R.id.button_delete);
        saves = getIntent().getIntExtra("count", 0);
        if(saves == 0){
            showAlertNoSaves();
            isEmptySaves = true;
        }
        savedGamesIds = new int[saves];
        savedGamesTexts = new String[saves];

        setupRecyclerView();
        getAllSavedGames();



        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(SavesActivity.this, MainActivity.class);
                if(isEmptySaves == true){
                    startActivity(intent);
                } else {
                    intent.putExtra("saveId", savedGamesIds[lastPosition]);
                    startActivity(intent);
                }
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAllSavesInBackground();
                Toast.makeText(SavesActivity.this, "Сохранения удалены", Toast.LENGTH_SHORT).show();
                isEmptySaves = true;
            }
        });
    }

    private void showAlertNoSaves() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Уведомление")
                .setMessage("Похоже у вас нет сохраненных партий")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void deleteAllSavesInBackground() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                gameBoardDatabase.getGameBoardDao().deleteAllGameBoard();
            }
        });
    }
    private void getAllSavedGames() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                savedGames = gameBoardDatabase.getGameBoardDao().getAllGameBoard();
                savedGamesIds = new int[savedGames.size()];
                savedGamesTexts = new String[savedGames.size()];

                for (int i = 0; i < savedGames.size(); i++) {
                    savedGamesIds[i] = savedGames.get(i).getId();
                    savedGamesTexts[i] = "Сохранение " + (i + 1);
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setupRecyclerView();
                    }
                });
            }
        });
    }

    public void onItemClick(int position) {
        String selectedSave = "Вы выбрали: " + (position+1);
        Toast.makeText(this, selectedSave, Toast.LENGTH_SHORT).show();
        resetSelectedColor(lastPosition);

        lastPosition = position;
        View view = recyclerView.getChildAt(position);
        TextView textView = view.findViewById(R.id.saveTextView);
        textView.setTextColor(Color.RED);
    }

    public void resetSelectedColor(int position){
        View view = recyclerView.getChildAt(position);
        TextView textView = view.findViewById(R.id.saveTextView);
        textView.setTextColor(Color.BLACK);
    }
    private void setupRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SaveAdapter adapter = new SaveAdapter(savedGamesTexts, this);
        recyclerView.setAdapter(adapter);
    }
}