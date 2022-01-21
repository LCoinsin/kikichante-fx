package com.kikichante.server;

import java.util.ArrayList;

public class GameServer {

    private String gameName;
    private ArrayList<ClientServer> currentPlayer = new ArrayList<ClientServer>();
    private final int numberMiniLaunchGame = 2;
    private boolean isAccessible = true;

    /******************************************************************************************************************/

    public GameServer(String gameName) {
        this.gameName = gameName;
    }

    /******************************************************************************************************************/

    public String getGameName() {
        return gameName;
    }

    public ArrayList<ClientServer> getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isAccessible() {
        return isAccessible;
    }

    /******************************************************************************************************************/

    public boolean addClient(ClientServer client) {
        this.currentPlayer.add(client);
        return true;
    }

    public boolean removeClient(ClientServer client) {
        for (ClientServer c : currentPlayer) {
            if (c.getUserId().equals(client.getUserId())) {
                currentPlayer.remove(c);
                return true;
            }
        }
        return false;
    }

    /******************************************************************************************************************/

    public void updateListPlayerWaitingRoom(ClientServer clientServer) {
        String message = "UPDATECURRENTPLAYER";
        //Recupere le nom de tous les clients
        for (ClientServer client : currentPlayer) {
            message = message.concat(":"+client.getUsernameFromBdd()+"-"+(client.isReady() ? "OK" : "KO"));
        }
        //Envoie le message a tous les clients
        for (ClientServer client : currentPlayer) {
            if (!(client.getUserId().equals(clientServer.getUserId())))
                client.println(message);
        }
    }

    public void updateListPlayerStateWaitingRoom() {
        String message = "UPDATECURRENTPLAYER";
        //Recupere le nom de tous les clients
        for (ClientServer client : currentPlayer) {
            message = message.concat(":"+client.getUsernameFromBdd()+"-"+(client.isReady() ? "OK" : "KO"));
        }
        for (ClientServer client : currentPlayer) {
            client.println(message);
        }
    }

    public boolean canLaunchGame() {
        if (currentPlayer.size() >= 2) {
            for (ClientServer c : currentPlayer) {
                if (!c.isReady())
                    return false;
            }
            return true;
        } else return false;
    }

    public void startGame() {
        this.isAccessible = false;
        for (ClientServer c : currentPlayer) {
            c.setReady(false);
            c.setInGame(true);
            c.println("STARTGAME");
        }
    }

    /******************************************************************************************************************/



}