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
    private String usernameFromBdd;
    private ArrayList<ClientServer> activeClient;
    private ArrayList<GameServer> activeGame;
    private GameServer game;
    private boolean isInListGame = false;
    private boolean isReady = false;
    private boolean isInGame = false;
    private boolean haveReceivedMusic = false;

    public ClientServer(Socket socket, ArrayList<ClientServer> activeClient, ArrayList<GameServer> activeGame) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
        this.activeClient = activeClient;
        this.activeGame = activeGame;
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
            if (game != null) {
                if (game.getCurrentPlayer().size() <= 1) {
                    activeGame.removeIf(g -> g.getGameName().equals(game.getGameName()));
                }
                for (ClientServer c : game.getCurrentPlayer()) {
                    if (c.getUserId().equals(this.userId)) {
                        game.removeClient(c);
                        break;
                    }
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

    public String getUsernameFromBdd() {
        return usernameFromBdd;
    }

    public void setUsernameFromBdd(String usernameFromBdd) {
        this.usernameFromBdd = usernameFromBdd;
    }

    /**************************/

    public boolean isInListGame() {
        return isInListGame;
    }

    public void setInListGame(boolean inListGame) {
        isInListGame = inListGame;
    }

    /**************************/

    public boolean isInGame() {
        return isInGame;
    }

    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    /**************************/

    public boolean isHaveReceivedMusic() {
        return haveReceivedMusic;
    }

    public void setHaveReceivedMusic(boolean haveReceivedMusic) {
        this.haveReceivedMusic = haveReceivedMusic;
    }

    /**************************/

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
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
