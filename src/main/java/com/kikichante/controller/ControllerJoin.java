package com.kikichante.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class ControllerJoin  {
    private Scene sceneMenu;
    private ProgressBar songProgressBar;
    private Media media;
    private MediaPlayer mediaPlayer;
    private File directory;
    private File[] files;
    private ArrayList<File> songs;
    private int songNumber;
    private Timer timer;
    private TimerTask task;
    private boolean running;

    public void test() {
        songs =new ArrayList<File>();
        directory =new
        File("music");
        files =directory.listFiles();
        media =new
        Media(songs.get(songNumber).
        toURI().
        toString());
        mediaPlayer =new
        MediaPlayer(media);

    }
    public void setSceneMenu(Scene sceneMenu) {
        this.sceneMenu = sceneMenu;
    }

    public void onClickGotoMenu(ActionEvent actionEvent) {
        System.out.println("test");
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(this.sceneMenu);
    }
    public void beginTimer() {

        timer = new Timer();

        task = new TimerTask() {

            public void run() {

                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                songProgressBar.setProgress(current/end);

                if(current/end == 1) {

                    cancelTimer();
                }
            }
        };

        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public void cancelTimer() {

        running = false;
        timer.cancel();
    }
}

