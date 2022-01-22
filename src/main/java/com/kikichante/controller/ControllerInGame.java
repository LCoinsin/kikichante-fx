package com.kikichante.controller;

import com.kikichante.client.Client;
import com.kikichante.utils.ColorOutput;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ControllerInGame<randomMusicChoice> implements Initializable {

    @FXML
    private VBox vboxListPlayer;

    @FXML
    private Label lbl;
    @FXML
    private MediaView mediaView;

    private List<String> listPlayers = new ArrayList<>();
    private Client client;

    private int compteARebours = 6;

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
            while (true) {
                try {
                    var message = client.readLine();
                    if (message.startsWith("LEAVEGAME"))
                        break;
                    handleLine(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void handleLine(String message) {
         if (message.startsWith("LISTCURRENTPLAYERINGAME")) {
            messageToListPlayer(message);
        }
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
                    HBox hboxNamePlayer = new HBox ();
                    Label label = new Label(name);
                    label.setAlignment(Pos.CENTER);
                    label.setFont(Font.font("Cooper Black", 15));
                    label.prefHeight(60);
                    label.prefWidth(120);
                    vboxListPlayer.getChildren().add(hboxNamePlayer);
                    hboxNamePlayer.getChildren().add(label);

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
