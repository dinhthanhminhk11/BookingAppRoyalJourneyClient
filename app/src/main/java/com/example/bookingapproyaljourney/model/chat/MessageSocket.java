package com.example.bookingapproyaljourney.model.chat;

public class MessageSocket {
    private String user;
    private String room;
    private String sender;
    private String message;
    private String time_send;

    public MessageSocket(String user, String room, String sender, String message, String time_send) {
        this.user = user;
        this.room = room;
        this.sender = sender;
        this.message = message;
        this.time_send = time_send;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime_send() {
        return time_send;
    }

    public void setTime_send(String time_send) {
        this.time_send = time_send;
    }
}
