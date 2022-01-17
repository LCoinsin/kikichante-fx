package com.kikichante.utils;

public class Message {
    public String sender;
    public int cast;
    public String rcvd;
    public String type;
    public String msg;

    public Message() {
        super();
    }

    public Message(String sender, int cast, String type) {
        this.sender = sender;
        this.cast = cast;
        this.type = type;
    }

    public Message(String sender, int cast, String rcvd, String type, String msg) {
        this.sender = sender;
        this.cast = cast;
        this.rcvd = rcvd;
        this.type = type;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", cast='" + cast + '\'' +
                ", rcvd='" + rcvd + '\'' +
                ", type='" + type + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
