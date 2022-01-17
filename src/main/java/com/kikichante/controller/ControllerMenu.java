package com.kikichante.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControllerMenu {
    private Scene sceneStatistique;
    private Scene sceneCreateGame;
    private Scene sceneJoinGameList;

    public void setSceneStatistique(Scene sceneStatistique) {
        this.sceneStatistique = sceneStatistique;
    }

    public void setSceneCreateGame(Scene sceneCreateGame) {
        this.sceneCreateGame = sceneCreateGame;
    }

    public void setSceneJoinGameList(Scene sceneJoinGameList) {
        this.sceneJoinGameList = sceneJoinGameList;
    }

    public void onClickGotoStatistique(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(this.sceneStatistique);
    }

    public void onClickGotoCreateGame(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(this.sceneCreateGame);
    }

    public void onClickGotoJoinGameList(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(this.sceneJoinGameList);
    }



    @FXML
    private Label titleTest;

    public void onTestClick(ActionEvent actionEvent) {
        titleTest.setText("banane");
    }
}
