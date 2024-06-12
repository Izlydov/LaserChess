package com.example.laserchesstest;

public interface RoomCallback {
    void onSuccess(Room room);
    void onError(String errorMessage);
}
