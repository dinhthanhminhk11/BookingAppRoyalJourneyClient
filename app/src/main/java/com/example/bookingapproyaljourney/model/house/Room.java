package com.example.bookingapproyaljourney.model.house;

public class Room {
    private int room;
    private String room1;
    private String room2;

    public Room(int room, String room1, String room2) {
        this.room = room;
        this.room1 = room1;
        this.room2 = room2;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getRoom1() {
        return room1;
    }

    public void setRoom1(String room1) {
        this.room1 = room1;
    }

    public String getRoom2() {
        return room2;
    }

    public void setRoom2(String room2) {
        this.room2 = room2;
    }
}
