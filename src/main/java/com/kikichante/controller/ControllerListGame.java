package com.kikichante.controller;

import com.kikichante.client.Client;
import com.kikichante.kikichantefx.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerListGame implements Initializable {
    @FXML
    private TextField textfieldGameName;
    @FXML
    private MediaView bgView;
    @FXML
    private ProgressBar songProgressBar;

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

    public void getActiveGames() {
        client.getListGame();
        String listGame = null;
        try {
            listGame = client.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("listGame = " + listGame);
    }

    /******************************************************************************************************************/

    public void onClickReturn(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(this.sceneBack);
        primaryStage.setFullScreen(true);
    }

    public void go(ActionEvent actionEvent) {

    }

    public void switchWaitingRoom() {
        try {
            Stage primaryStage = (Stage)textfieldGameName.getScene().getWindow();

            FXMLLoader waitingRoomLoader = new FXMLLoader(Application.class.getResource("ViewWaitingRoom.fxml"));
            Scene viewWaitingRoom = new Scene(waitingRoomLoader.load());

            ControllerWaitingRoom controllerWaitingRoom = (ControllerWaitingRoom)waitingRoomLoader.getController();
            Scene currentScene = textfieldGameName.getScene();
            controllerWaitingRoom.setSceneBack(currentScene);
            controllerWaitingRoom.setClient(client);

            primaryStage.setScene(viewWaitingRoom);
            primaryStage.setFullScreen(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void joinGame(ActionEvent actionEvent) {
        client.joinGame(textfieldGameName.getText());
        boolean isJoin = false;
        try {
            var res = client.readLine();
            if(res.startsWith("JOINGAME")) {
                String[] resMessage = res.split(":");
                if (resMessage[1].equals("OK"))
                    isJoin = true;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        if (isJoin)
            switchWaitingRoom();
        //System.out.println(textfieldGameName.getText());
    }

    /******************************************************************************************************************/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
