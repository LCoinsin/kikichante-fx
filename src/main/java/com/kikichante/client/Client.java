package com.kikichante.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class Client {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String userId;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
        userId = String.valueOf(UUID.randomUUID());
    }

    /********************************/

    public String readLine() throws IOException {
        var line = this.reader.readLine();
        if (line == null) throw new IOException("Serveur disconnected");
        return line;
    }

    /********************************/

    public void println(String message) {
        this.writer.println(message);
    }

    /********************************/

    public void uuid() {
        String message = "UUID:"+this.userId;
        println(message);
    }

    public void connexion(String username, String password) {
        String message = "LOGIN:" + username + ":" + password;
        println(message);
    }

    public void inscription(String username, String password) {
        String message = "CREATEACCOUNT:" + username + ":" + password;
        println(message);
    }

    public void getListGame() {
        String message = "GETCURRENTLISTGAME";
        println(message);
    }

    public void getListPlayerInWaitingRoom() {
        String message = "GETCURRENTPLAYERINWAITINGROOM";
        println(message);
    }
    public void getListPlayerInGame() {
        String message = "GETCURRENTPLAYERINGAME";
        println(message);
    }

    public void createGame(String gameName) {
        String message = "CREATEGAME:"+gameName;
        println(message);
    }

    public void joinGame(String gameName) {
        String message = "JOINGAME:" + gameName;
        println(message);
    }

    public void leaveGame() {
        String message = "LEAVEGAME";
        println(message);
    }

    public void setReady() {
        String message = "SETREADY:OK";
        println(message);
    }

    public void setNotReady() {
        String message = "SETREADY:KO";
        println(message);
    }

    public void goInListGame() {
        String message = "GOVIEWLISTGAME:OK";
        println(message);
    }

    public void goOutListGame() {
        String message = "GOOUTVIEWLISTGAME";
        println(message);
    }

    public void leaveInGame() {
        String message = "CLIENTLEAVEGAME";
        println(message);
    }

}
