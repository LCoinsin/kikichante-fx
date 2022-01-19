package com.kikichante.controller;

import com.kikichante.client.Client;
import com.kikichante.kikichantefx.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMenu implements Initializable {
    @FXML
    private MediaView mediaView;
    @FXML
    private Label titleTest;

    private Scene sceneStatistique;
    private Scene sceneCreateGame;
    private Scene sceneJoinGameList;
    private Client client;

    public void setSceneStatistique(Scene sceneStatistique) {
        this.sceneStatistique = sceneStatistique;
    }

    public void setSceneCreateGame(Scene sceneCreateGame) {
        this.sceneCreateGame = sceneCreateGame;
    }

    public void setSceneJoinGameList(Scene sceneJoinGameList) {
        this.sceneJoinGameList = sceneJoinGameList;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    private void switchSceneStatistique(Stage primaryStage) {
        try {
            FXMLLoader statistiqueLoader = new FXMLLoader(Application.class.getResource("ViewStatistique.fxml"));
            Scene viewStatistique = new Scene(statistiqueLoader.load());

            ControllerStatistique controllerStatistique = (ControllerStatistique)statistiqueLoader.getController();
            Scene currentScene = mediaView.getScene();
            controllerStatistique.setSceneMenu(currentScene);

            primaryStage.setScene(viewStatistique);
            primaryStage.setFullScreen(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchSceneListGame() {

    }

    private void switchSceneCreateGame() {

    }

    public void onClickGotoStatistique(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        this.switchSceneStatistique(primaryStage);
        //primaryStage.setScene(this.sceneStatistique);
        //primaryStage.setFullScreen(true);
    }

    public void onClickGotoCreateGame(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(this.sceneCreateGame);
        //primaryStage.setFullScreen(true);
    }

    public void onClickGotoJoinGameList(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(this.sceneJoinGameList);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
    }

    public void onTestClick(ActionEvent actionEvent) {
        titleTest.setText("banane");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Media media;
        media = new Media(new File("src/main/resources/video/fond.mp4").toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        mediaView.setMediaPlayer(player);
        DoubleProperty mvw = mediaView.fitWidthProperty();
        DoubleProperty mvh = mediaView.fitHeightProperty();
        mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
        mediaView.setPreserveRatio(true);
        player.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
        player.play();
    }
}
