package com.kikichante.controller;

import com.kikichante.client.Client;
import com.kikichante.utils.ColorOutput;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ControllerInGame<randomMusicChoice> implements Initializable {

    @FXML
    private VBox test;

    @FXML
    private Label lbl;
    @FXML
    private MediaView mediaView;

    private List<String> listPlayers = new ArrayList<>();
    private Client client;

    private int compteARebours = 5;

    private Timer timer;

    public void setClient(Client client) {
        this.client = client;
    }
    public void joinGame() {

    }


    public void setTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(compteARebours > 0)
                {
                    Platform.runLater(() -> lbl.setText((String.valueOf(compteARebours))));
                    //System.out.println(compteARebours);
                    compteARebours--;
                }
                else
                    timer.cancel();
            }
        }, 1000,1000);
    }

    public void onClickReturn() {

    }

    public void getPlayerInit() {
        ColorOutput.blueMessage("Scene in InGame");
        client.getListPlayerInGame();
        listenner.start();
    }


    Thread listenner = new Thread() {
        @Override
        public void run() {
            System.out.println("run thread ok");
            while (true) {
                try {
                    System.out.println("thread while true  ok");
                    var message = client.readLine();
                    System.out.println(message);
                    if (message.startsWith("LEAVEGAME"))
                        break;
                    System.out.println("thread ok ");
                    handleLine(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void handleLine(String message) {
        if (message.startsWith("UPDATECURRENTPLAYER"))
            System.out.println("ok");
        else if (message.startsWith("LISTCURRENTPLAYERINGAME")) {
            messageToListPlayer(message);
            System.out.println("ok2");
        }
        System.out.println(message);
    }

    public void messageToListPlayer(String message) {
        String[] resListPlayer = message.split(":");
        resListPlayer = Arrays.copyOfRange(resListPlayer, 1 , resListPlayer.length);
        listPlayers = Arrays.asList(resListPlayer);
        printList(listPlayers);
        System.out.println("resListPlayer = " + listPlayers);
    }


    public void printList(List<String> playersList) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (String name : playersList) {
                    Label label = new Label(name);
                    label.setAlignment(Pos.CENTER);
                    label.prefHeight(60);
                    label.prefWidth(120);
                    test.getChildren().add(label);

                }
            }
        });
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTimer();

//        Media media;
//        media = new Media(new File("src/main/resources/video/bg.mp4").toURI().toString());
//        MediaPlayer player = new MediaPlayer(media);
//        mediaView.setMediaPlayer(player);
//        DoubleProperty mvw = mediaView.fitWidthProperty();
//        DoubleProperty mvh = mediaView.fitHeightProperty();
//        mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
//        //System.out.println(mvh);
//        mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
//        mediaView.setPreserveRatio(true);
//        player.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
//        player.play();
    }
}
