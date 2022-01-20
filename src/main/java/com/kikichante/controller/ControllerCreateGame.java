package com.kikichante.controller;

import com.kikichante.client.Client;
import com.kikichante.kikichantefx.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerCreateGame {
    @FXML
    private TextField textfieldGameName;

    private Scene sceneBack;
    private Client client;

    /******************************************************************************************************************/

    public void setClient(Client client) {
        this.client = client;
    }

    public void setSceneBack(Scene sceneBack) {
        this.sceneBack = sceneBack;
    }

    /******************************************************************************************************************/

    public void onClickReturn(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(this.sceneBack);
        primaryStage.setFullScreen(true);
    }

    /******************************************************************************************************************/

    private void switchWaitingRoom() {

        try {
            Stage primaryStage = (Stage)textfieldGameName.getScene().getWindow();

            FXMLLoader waitingRoomLoader = new FXMLLoader(Application.class.getResource("ViewWaitingRoom.fxml"));
            Scene viewWaitingRoom = new Scene(waitingRoomLoader.load());

            ControllerStatistique controllerWaitingRoom = (ControllerStatistique)waitingRoomLoader.getController();
            Scene currentScene = textfieldGameName.getScene();
            controllerWaitingRoom.setSceneBack(currentScene);
            controllerWaitingRoom.setClient(client);

            primaryStage.setScene(viewWaitingRoom);
            primaryStage.setFullScreen(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickCreateGame(ActionEvent actionEvent) {
        client.createGame(textfieldGameName.getText());
        boolean createGame = false;
        try {
            String res = client.readLine();
            if (res.startsWith("CREATEGAME")) {
                String[] resMessage = res.split(":");
                if (resMessage[1].equals("OK")) {
                    createGame = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (createGame)
            switchWaitingRoom();
    }
}
