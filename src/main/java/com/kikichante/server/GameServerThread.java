package com.kikichante.server;

import java.util.ArrayList;

public class GameServerThread extends Thread {

    private String gameName;
    private ArrayList<ClientServer> currentPlayer = new ArrayList<ClientServer>();

    /*****************************/

    public  GameServerThread(String gameName) {
        this.gameName = gameName;
    }

    /*****************************/

    public String getGameName() {
        return gameName;
    }

    /*****************************/

    @Override
    public void run() {
        //TODO -> lancement de la partie, ecoute des clients
        System.out.println("Lancement de la partie");
    }

    /*****************************/

    public boolean addClient(ClientServer client) {
        this.currentPlayer.add(client);
        return true;
    }

    /*****************************/

    public boolean removeClient(ClientServer client) {
        for (ClientServer c : currentPlayer) {
            if (c.getUserId().equals(client.getUserId())) {
                currentPlayer.remove(c);
                return true;
            }
        }
        return false;
    }

}
