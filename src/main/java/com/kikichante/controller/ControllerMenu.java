package com.kikichante.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMenu implements Initializable {
    @FXML
    private MediaView mediaView;

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
        primaryStage.setFullScreen(true);
    }

    public void onClickGotoCreateGame(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(this.sceneCreateGame);
        primaryStage.setFullScreen(true);
    }

    public void onClickGotoJoinGameList(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(this.sceneJoinGameList);
        primaryStage.setFullScreen(true);
    }



    @FXML
    private Label titleTest;

    public void onTestClick(ActionEvent actionEvent) {
        titleTest.setText("banane");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Media media;
        //media = new Media(this.getClass().getResource("video/bg.mp4").toExternalForm());
        media = new Media(new File("src/main/resources/video/bg.mp4").toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        mediaView.setMediaPlayer(player);
        DoubleProperty mvw = mediaView.fitWidthProperty();
        DoubleProperty mvh = mediaView.fitHeightProperty();
        mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
        mediaView.setPreserveRatio(true);
        player.play();

    }
}
