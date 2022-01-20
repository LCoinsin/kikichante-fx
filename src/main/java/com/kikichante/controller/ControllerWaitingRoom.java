package com.kikichante.controller;

import com.kikichante.client.Client;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControllerWaitingRoom {

    private Scene sceneBack;
    private Client client;

    /******************************************************************************************************************/

    public void setSceneBack(Scene sceneBack) {
        this.sceneBack = sceneBack;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    /******************************************************************************************************************/

    public void onClickReturn(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        client.leaveGame();
        primaryStage.setScene(this.sceneBack);
        primaryStage.setFullScreen(true);
    }

}
