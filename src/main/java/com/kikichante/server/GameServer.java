package com.kikichante.server;

import java.util.ArrayList;

public class GameServer {

    private String gameName;
    private ArrayList<ClientServer> currentPlayer = new ArrayList<ClientServer>();

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
}
