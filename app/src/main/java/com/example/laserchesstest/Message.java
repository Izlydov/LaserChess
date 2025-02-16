package com.example.laserchesstest;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Message implements Serializable {


    private Room room;
    @SerializedName("id")
    private Long id;

    @SerializedName("sender")
    private String sender;

    @SerializedName("content")
    private String content;

    public void setPositions(String positions) {
        this.positions = positions;
    }
    public String getPositions(){
        return positions;
    }

    @SerializedName("positions")
    private String positions;
    private String laserWay;



    public Message() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Room getRoom() {
        return room;
    }
    public String getLaserWay() {
        return laserWay;
    }

    public void setLaserWay(String laserWay) {
        this.laserWay = laserWay;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
