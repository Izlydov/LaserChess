package com.example.laserchesstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Database.RoomCodeGenerator;
import network.ApiClient;
import network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LobbyActivity extends AppCompatActivity {
    Button makeRoomButton, joinRoomButton, deleteAllRoomsButton, deleteAllMessagesButton;
    EditText editRoomCode, editPlayerName;
    String roomCode;
    String playerName;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        makeRoomButton = findViewById(R.id.button_join);
        deleteAllMessagesButton = findViewById(R.id.button_delete_messages);
        deleteAllRoomsButton = findViewById(R.id.button_delete_rooms);
        joinRoomButton = findViewById(R.id.button_make);
        editRoomCode = findViewById(R.id.edit_code);
        editPlayerName = findViewById(R.id.edit_playerName);

        apiService = ApiClient.getApiService();

        joinRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomCode = editRoomCode.getText().toString();
                playerName = editPlayerName.getText().toString();
                joinRoom(roomCode, playerName);
            }
        });
        makeRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomCode = RoomCodeGenerator.generateUniqueRoomCode();
                playerName = editPlayerName.getText().toString();
                createRoom(roomCode, playerName);
            }
        });
        deleteAllRoomsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService.deleteAllRooms().enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.w("nice", "nice");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.w("bad", "bad");
                    }
                });
            }
        });
        deleteAllMessagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService.deleteAllMessages().enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.w("nice", "nice");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.w("bad", "bad");
                    }
                });
            }
        });
    }
    private void createRoom(final String roomCode, final String playerName) {
        Room room = new Room();
        room.setRoomCode(roomCode);
        room.setPlayer1(playerName);

        apiService.createRoom(room).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(LobbyActivity.this, RoomActivity.class);
                    intent.putExtra("roomCode", roomCode);
                    intent.putExtra("playerName", playerName);
                    intent.putExtra("isFirst", true);
                    startActivity(intent);
                } else {
                    Toast.makeText(LobbyActivity.this, "Error creating room: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
//                Toast.makeText(LobbyActivity.this, "Error creating room", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                Log.d("dev", t.getMessage());
            }
        });
    }

    private void joinRoom(final String roomCode, final String playerName) {
        apiService.getRoomByCode(roomCode).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Room room = response.body();
                    if (room.getPlayer2() == null) {
                        room.setPlayer2(playerName);
                        apiService.updateRoom(room.getId(), room).enqueue(new Callback<Room>() {
                            @Override
                            public void onResponse(Call<Room> call, Response<Room> response) {
                                if (response.isSuccessful()) {
                                    Intent intent = new Intent(LobbyActivity.this, RoomActivity.class);
                                    intent.putExtra("roomCode", roomCode);
                                    intent.putExtra("playerName", playerName);
                                    intent.putExtra("isFirst", false);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LobbyActivity.this, "Error joining room: " + response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Room> call, Throwable t) {
                                Toast.makeText(LobbyActivity.this, "Error joining room", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(LobbyActivity.this, "Room is full", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LobbyActivity.this, "Room not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Toast.makeText(LobbyActivity.this, "Error joining room", Toast.LENGTH_SHORT).show();
            }
        });
    }

}