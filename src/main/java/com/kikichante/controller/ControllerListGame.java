package com.kikichante.controller;

import com.kikichante.client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    /******************************************************************************************************************/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
