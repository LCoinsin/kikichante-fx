package com.kikichante.controller;

import com.kikichante.client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class ControllerWaitingRoom {

    private Scene sceneBack;
    private Client client;
    private boolean listen = true;
    private List<String> listPlayers = new ArrayList<>();

    /******************************************************************************************************************/

    public void setSceneBack(Scene sceneBack) {
        this.sceneBack = sceneBack;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    /******************************************************************************************************************/

    public void getPlayerInit() {
        client.getListPlayerInWaitingRoom();
        try {
            var message = client.readLine();
            if (message.startsWith("LISTCURRENTPLAYERINWAITINGROOM")) {
                //TODO -> Traiter la liste de joueurs
                messageToListPlayer(message);
                //System.out.println("message = " + message);
            }
            listenner.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /******************************************************************************************************************/

    Thread listenner = new Thread() {
        @Override
        public void run() {
            while (listen) {
                try {
                    var message = client.readLine();
                    System.out.println("message = " + message);
                    if (message.startsWith("LEAVEGAME"))
                        break;
                    handleLine(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void handleLine(String message) {
        if (message.startsWith("UPDATECURRENTPLAYER"))
            messageToListPlayer(message);
    }

    public void messageToListPlayer(String message) {
        String[] resListPlayer = message.split(":");
        resListPlayer = Arrays.copyOfRange(resListPlayer, 1 , resListPlayer.length);
        listPlayers = Arrays.asList(resListPlayer);
        System.out.println("resListPlayer = " + listPlayers);
    }

    /******************************************************************************************************************/

    public void onClickReturn(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        client.leaveGame();
        listen = false;
        primaryStage.setScene(this.sceneBack);
        primaryStage.setFullScreen(true);
    }

}
