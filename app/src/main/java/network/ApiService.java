package network;

import com.example.laserchesstest.Message;
import com.example.laserchesstest.Room;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface ApiService {
    @POST("/api/rooms")
    Call<Room> createRoom(@Body Room room);
    @POST("/api/messages")
    Call<Message> sendMessage(@Body Message message);

    @GET("/api/messages")
    Call<List<Message>> getMessages();
    @GET("/api/messages/{id}")
    Call<Message> getMessageById(@Path("id") Long id);

    @GET("/api/messages/last")
    Call<Message> getMyLastMessage();
    @DELETE("/api/messages/{id}")
    Call<Void> deleteMessage(@Path("id") Long id);

    @GET("/api/rooms/{roomCode}")
    Call<Room> getRoomByCode(@Path("roomCode") String roomCode);

    @PUT("/api/rooms/{id}")
    Call<Room> updateRoom(@Path("id") Long id, @Body Room room);
    @DELETE("/api/rooms/{roomCode}")
    Call<Void> deleteRoomByRoomCode(@Path("roomCode") String roomCode);
    @GET("/api/messages/room/{roomCode}")
    Call<List<Message>> getMessagesByRoomCode(@Path("roomCode") String roomCode);
    @GET("/api/messages/room/{roomCode}/last")
    Call<Message> getLastMessageByRoomCode(@Path("roomCode") String roomCode);
    @DELETE("/api/messages")
    Call<Void> deleteAllMessages();
    @DELETE("/api/rooms")
    Call<Void> deleteAllRooms();

}
