package com.kikichante.controller;

import com.kikichante.client.Client;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControllerStatistique {

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

    public void onClickGotoMenu(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(this.sceneBack);
    }

}