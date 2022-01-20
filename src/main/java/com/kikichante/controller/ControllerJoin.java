package com.kikichante.controller;

import com.kikichante.client.Client;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.ProgressBar;


public class ControllerJoin implements Initializable {
    @FXML
    private TextField textfieldGameName;
    @FXML
    private MediaView bgView;
    @FXML
    private ProgressBar songProgressBar;

    private Scene sceneBack;
    private Scene sceneWaitingRoom;
    private Client client;

    private Media media;
    private MediaPlayer mediaPlayer;
    private int songNumber;
    private Timer timer;
    private TimerTask task;
    private boolean running;

    /******************************************************************************************************************/

    public void setSceneBack(Scene sceneBack) {
        this.sceneBack = sceneBack;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setSceneWaitingRoom(Scene sceneWaitingRoom) {
        this.sceneWaitingRoom = sceneWaitingRoom;
    }

    /******************************************************************************************************************/

    public void onClickGotoMenu(ActionEvent actionEvent) {
        System.out.println("test");
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(this.sceneBack);
        //primaryStage.setFullScreen(true);
    }

    /******************************************************************************************************************/

    public void go(ActionEvent actionEvent){

    }

    public void playMusic(String path){
        media = new Media(new File(path).toURI().toString());
        //Instantiating MediaPlayer class
        mediaPlayer = new MediaPlayer(media);
        //by setting this property to true, the audio will be played
        mediaPlayer.setAutoPlay(false);
        musicDuration();
//        label.setText("Music is playing...");
        mediaPlayer.play();
//        mediaPlayer.getTotalDuration();
//        media.getDuration();
//        for (int i = 0; i < 10; i++) {
//            System.out.println(media.getDuration());
//        }*
    }

    public void musicDuration(){
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                System.out.println(current/end);
                songProgressBar.setProgress(current/end);

                if(current/end == 1){
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public void cancelTimer(){
        running = false;
        timer.cancel();
    }

    @FXML
    void initialize() throws SQLException {
        System.out.println("La je suis init");
        /*
        int idMus;

        idMus = (new Random()).nextInt(4);

        ChoiceMusic choix = new ChoiceMusic();
        Music mus = choix.queryConnexionMusic();

        System.out.println(mus.getUrl());
        playMusic(mus.getUrl());*/
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Media media = new Media(new File("src/main/resources/video/bg.mp4").toURI().toString());
        System.out.println("media = " + media);
        MediaPlayer player = new MediaPlayer(media);
        System.out.println("player = " + player.toString());
        bgView.setMediaPlayer(player);
        player.play();
        DoubleProperty mvw = bgView.fitWidthProperty();
        DoubleProperty mvh = bgView.fitHeightProperty();
        mvw.bind(Bindings.selectDouble(bgView.sceneProperty(), "width"));
        mvh.bind(Bindings.selectDouble(bgView.sceneProperty(), "height"));
        bgView.setPreserveRatio(true);
        player.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
        player.play();
    }

    /***********************/

    private void switchInGame() {
        Stage primaryStage = (Stage)textfieldGameName.getScene().getWindow();
        primaryStage.setScene(this.sceneWaitingRoom);
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
            switchInGame();
    }
}



