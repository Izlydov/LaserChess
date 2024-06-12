package com.example.laserchesstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import network.ApiClient;
import network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomActivity extends AppCompatActivity {
    TextView roomCodeView, player1View, player2View;
    String roomCode, playerName;
    ApiService apiService;
    Room currentRoom;
    Button deleteRoomButton;
    private Button sendButton;
    private EditText messageInput;
    private TextView messagesView;
    private boolean isActive;
    private boolean gameStarted = false;
    private boolean isBlue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        roomCodeView = findViewById(R.id.room_code);
        player1View = findViewById(R.id.player1);
        player2View = findViewById(R.id.player2);
        deleteRoomButton = findViewById(R.id.button_delete_room);

        roomCode = getIntent().getStringExtra("roomCode");
        playerName = getIntent().getStringExtra("playerName");
        isBlue = getIntent().getBooleanExtra("isFirst", false);

        sendButton = findViewById(R.id.button_send);
        messageInput = findViewById(R.id.edit_message);
        messagesView = findViewById(R.id.text_messages);

        roomCodeView.setText("Код комнаты: " + roomCode);

        apiService = ApiClient.getApiService();
        fetchRoomDetails();
        fetchMessages();


        sendButton.setOnClickListener(v -> {
            String content = messageInput.getText().toString();
            Message message = new Message();
            message.setRoom(currentRoom);
            message.setSender(playerName);
            message.setContent(content);
            sendMessage(message);
        });

        deleteRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService.deleteRoomByRoomCode(roomCode).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(RoomActivity.this, "Комната удалена", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RoomActivity.this, LobbyActivity.class);
                        startActivity(intent);

                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(RoomActivity.this, "Error deleting room", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void sendMessage(Message message) {
        apiService.sendMessage(message).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    fetchMessages();
                } else {
                    Log.e("RoomActivity", "Error sending message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e("RoomActivity", "Error sending message", t);
            }
        });
    }

    private void fetchMessages() {
        apiService.getMessagesByRoomCode(roomCode).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    StringBuilder messagesText = new StringBuilder();
                    for (Message message : response.body()) {
                        messagesText.append(message.getSender()).append(": ").append(message.getContent()).append("\n");
                    }
                    messagesView.setText(messagesText.toString());
                } else {
                    Log.e("RoomActivity", "Error fetching messages: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.e("RoomActivity", "Error fetching messages", t);
            }
        });
    }

    private void fetchRoomDetails() {
        apiService.getRoomByCode(roomCode).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful() && response.body() != null) {
                    currentRoom = response.body();
                    player1View.setText("Игрок 1: " + currentRoom.getPlayer1());
                    if (currentRoom.getPlayer2() != null) {
                        player2View.setText("Игрок 2: " + currentRoom.getPlayer2());
                        startGame();
                    } else {
                        player2View.setText("Ожидаем игрока 2...");
                        waitForPlayer2();
                    }
                } else {
                    Log.e("RoomActivity", "Error fetching room details" + response.message());
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Log.e("RoomActivity", "Error fetching room details", t);
            }
        });
    }

    private void waitForPlayer2() {
        isActive = true;
        new Thread(() -> {
            while (isActive && currentRoom.getPlayer2() == null) {
                try {
                    Thread.sleep(5000); // wait for 5 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                apiService.getRoomByCode(roomCode).enqueue(new Callback<Room>() {
                    @Override
                    public void onResponse(Call<Room> call, Response<Room> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            currentRoom = response.body();
                            if (currentRoom.getPlayer2() != null) {
                                runOnUiThread(() -> {
                                    player2View.setText("Игрок 2: " + currentRoom.getPlayer2());
                                    startGame();
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Room> call, Throwable t) {
                        Log.e("RoomActivity", "Error fetching room details", t);
                    }
                });
            }
        }).start();
    }


    private void startGame() {
        if (!gameStarted) {
            if(currentRoom.getPlayer1().equals(currentRoom.getPlayer2())){
                currentRoom.setPlayer1(currentRoom.getPlayer1() + " (Player 1)");
                playerName = currentRoom.getPlayer1();
                currentRoom.setPlayer2(currentRoom.getPlayer2() + " (Player 2)");
            }
            gameStarted = true;
            Toast.makeText(this, "Оба игрока подключены! Загрузка игры...", Toast.LENGTH_SHORT).show();
            Message message = new Message();
            message.setSender("System");
            message.setRoom(currentRoom);
            message.setContent("StartMessage");
            sendMessage(message);
            Intent intent = new Intent(RoomActivity.this, MainActivity.class);
            intent.putExtra("roomCode", roomCode);
            intent.putExtra("isGameOnline", true);
            intent.putExtra("isPlayer1", isBlue);
            startActivity(intent);
            isActive = false;
        }
    }
}
