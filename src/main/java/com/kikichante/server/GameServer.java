package com.kikichante.server;

import com.kikichante.utils.ColorOutput;
import com.kikichante.utils.Convert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameServer {

    private String gameName;
    private ArrayList<ClientServer> currentPlayer = new ArrayList<ClientServer>();
    private final int numberMiniLaunchGame = 1;
    private boolean isAccessible = true;
    private boolean selectedMusic = false;
    private Bdd bdd = null;
    private Music music = null;

    /******************************************************************************************************************/

    public GameServer(String gameName, Bdd bdd) {
        this.gameName = gameName;
        this.bdd = bdd;
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

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
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
        if (currentPlayer.size() >= numberMiniLaunchGame) {
            for (ClientServer c : currentPlayer) {
                if (!c.isReady())
                    return false;
            }
            return true;
        } else return false;
    }

    public void startGame() {
        this.isAccessible = false;
        if (!selectedMusic)
            selectOneMusic();
        for (ClientServer c : currentPlayer) {
            c.setReady(false);
            c.setInGame(true);
            c.println("STARTGAME");
        }
    }

    private void selectOneMusic() {
        music = bdd.querySelectMusic();
        ColorOutput.redMessage("Musique selected !!!");
        this.selectedMusic = true;
    }

    public void sendMusic(ClientServer c) {
        //TODO CHANGER LA MUSIQUE EN FONCTION DE LA REPONSE DE LA BDD
        //byte[] musicArray = Convert.fileToByteArray("src/main/resources/musiques/5sd.mp3"); // src/main/resources/musiquesServeur/musiques
        byte[] musicArray = Convert.fileToByteArray("src/main/resources/musiquesServeur/" + music.getUrl().toString());
        String message = "RECEIVEDMUSIC:"+ Arrays.toString(musicArray);
        c.println(message);
    }

    public void updateListPlayerInGame(ClientServer clientServer) {
        String message = "UPDATELISTCURRENTPLAYER";
        System.out.println("message = " + message);
        /*
        for (ClientServer client : currentPlayer) {
            message = message.concat(":"+client.getUsernameFromBdd());
        }
        for (ClientServer c : currentPlayer ) {
            c.println(message);
        }
         */
    }

    /******************************************************************************************************************/

}