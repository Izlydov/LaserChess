package com.example.laserchesstest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    Button playButton, rulesButton;
    Intent intent;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        playButton = findViewById(R.id.button_play);
        rulesButton = findViewById(R.id.button_rules);
        inflater = getLayoutInflater();
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
}