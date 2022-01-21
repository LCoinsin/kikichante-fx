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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMenu implements Initializable {
    @FXML
    private MediaView mediaView;
    @FXML
    private Label titleTest;

    private Client client;

    /******************************************************************************************************************/

    public void setClient(Client client) {
        this.client = client;
    }

    /******************************************************************************************************************/

    private void switchSceneStatistique(Stage primaryStage) {
        try {
            FXMLLoader statistiqueLoader = new FXMLLoader(Application.class.getResource("ViewStatistique.fxml"));
            Scene viewStatistique = new Scene(statistiqueLoader.load());

            ControllerStatistique controllerStatistique = (ControllerStatistique)statistiqueLoader.getController();
            controllerStatistique.setClient(client);

            primaryStage.setScene(viewStatistique);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchSceneListGame(Stage primaryStage) {
        try {
            FXMLLoader listGameLoader = new FXMLLoader(Application.class.getResource("ViewListGame.fxml"));
            Scene viewListGame = new Scene(listGameLoader.load());

            ControllerListGame controllerListGame = (ControllerListGame) listGameLoader.getController();
            controllerListGame.setClient(client);
            controllerListGame.getActiveGames();

            client.goInListGame();

            primaryStage.setScene(viewListGame);
           // primaryStage.setFullScreen(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchSceneCreateGame(Stage primaryStage) {
        try {
            FXMLLoader createGameLoader = new FXMLLoader(Application.class.getResource("ViewCreateGame.fxml"));
            Scene viewCreateGame = new Scene(createGameLoader.load());
            
            ControllerCreateGame controllerCreateGame = (ControllerCreateGame) createGameLoader.getController();
            controllerCreateGame.setClient(client);

            primaryStage.setScene(viewCreateGame);
            //primaryStage.setFullScreen(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /******************************************************************************************************************/

    public void onClickGotoStatistique(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        this.switchSceneStatistique(primaryStage);
    }

    public void onClickGotoCreateGame(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        switchSceneCreateGame(primaryStage);
    }

    public void onClickGotoJoinGameList(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        switchSceneListGame(primaryStage);
        //primaryStage.setFullScreen(false);
       // primaryStage.setResizable(false);

    }

    /******************************************************************************************************************/

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
        //System.out.println(mvh);
        mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
        mediaView.setPreserveRatio(true);
        player.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
        player.play();

    }
}
