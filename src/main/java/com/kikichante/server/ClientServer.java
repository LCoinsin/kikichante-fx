package com.kikichante.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientServer {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    private String userId;
    private ArrayList<ClientServer> activeClient;
    private GameServer game;

    public ClientServer(Socket socket, ArrayList<ClientServer> activeClient) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
        this.activeClient = activeClient;
    }

    // !! WARNING !! if IOException -> disconnect
    public String readLine() throws IOException{
        var line = this.reader.readLine();
        if (line == null) {
            //Delete le client de la liste des clients
            for (ClientServer c : activeClient) {
                if (c.userId.equals(this.userId)) {
                    activeClient.remove(c);
                    break;
                }
            }

            throw new IOException("Client disconnected");
        }
        return line;
    }

    /**************************/

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    /**************************/

    public GameServer getGame() {
        return game;
    }

    public void setGame(GameServer game) {
        this.game = game;
    }

    /**************************/

    //Permet l'envoie d'un message
    public void println(String message){
        this.writer.println(message);
    }
}
