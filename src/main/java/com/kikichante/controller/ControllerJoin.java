package com.kikichante.controller;

import com.kikichante.server.ChoiceMusic;
import com.kikichante.server.Music;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class ControllerJoin  {
    private Scene sceneMenu;
    @FXML
    private ProgressBar songProgressBar;
    private Media media;
    private MediaPlayer mediaPlayer;
    private int songNumber;
    private Timer timer;
    private TimerTask task;
    private boolean running;

    public void setSceneMenu(Scene sceneMenu) {
        this.sceneMenu = sceneMenu;
    }

    public void onClickGotoMenu(ActionEvent actionEvent) {
        System.out.println("test");
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(this.sceneMenu);
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
        int idMus;

        idMus = (new Random()).nextInt(4);

        ChoiceMusic choix = new ChoiceMusic();
        Music mus = choix.queryConnexionMusic();

        System.out.println(mus.getUrl());
        playMusic(mus.getUrl());
    }

}

