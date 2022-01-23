package com.kikichante.controller;

import com.kikichante.client.Client;
import com.kikichante.utils.ColorOutput;
import com.kikichante.utils.Convert;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
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
import javafx.scene.paint.Color;
import org.controlsfx.control.action.Action;
import javafx.scene.text.Font;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ControllerInGame<randomMusicChoice> implements Initializable {

    @FXML
    private VBox vboxListPlayer;

    @FXML
    private Label lbl;
    @FXML
    private MediaView bgView;
    @FXML
    private TextField author;
    @FXML
    private TextField songName;

    private List<String> listPlayers = new ArrayList<>();
    private Client client;
    private int compteARebours = 5;
    private Timer timer;
    private File file = new File("src/main/resources/musiques/out.mp3");
    private Media song;
    private MediaPlayer player;

    /******************************************************************************************************************/

    public void setClient(Client client) {
        this.client = client;
    }

    /******************************************************************************************************************/

    public void getPlayerInit() {
        ColorOutput.blueMessage("Scene in InGame");
        client.getListPlayerInGame();
        client.askMusic();
        listenner.start();
    }

    /******************************************************************************************************************/

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

    public void onClickReturn(ActionEvent actionEvent) {
        //client.leaveInGame();//CLIENTLEAVEGAME
    }

    /******************************************************************************************************************/

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
         else if (message.startsWith("RECEIVEDMUSIC")) {
             rebuildMusic(message);
             client.haveMusic();
         }
         else if (message.startsWith("PLAYMUSIC")) {
             String path = "src/main/resources/musiques/out.mp3";
             song = new Media(new File(path).toURI().toString());
             player = new MediaPlayer(song);
             player.play();
             ColorOutput.greenMessage("Vas-y dj c'est ton moment !!");
         }
         else if (message.startsWith("STOPMUSICWITHOUTWINNER")) {
             ColorOutput.redMessage("Eclater le DJ");
         }
         else if (message.startsWith("STOPMUSICWITHWINNER")) {
             player.stop();
         }
         else if (message.startsWith("WRONGANSWER")) {
             ColorOutput.redMessage("T'es nul !!");
         }
         else {
             System.out.println("message = " + message);
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
                vboxListPlayer.getChildren().clear();
                for (String nameandScore : playersList) {
                    String[] playerScore = nameandScore.split("-");
                    String name = playerScore[0];
                    String score=("            "+playerScore[1]);
                    System.out.println(playerScore[1]);
                    HBox hboxNamePlayer = new HBox ();
                    hboxNamePlayer.prefHeight(43);
                    hboxNamePlayer.prefWidth(190);
                    Label labelName = new Label(name);
                    Label labelScore = new Label(score);
                    labelName.setAlignment(Pos.TOP_LEFT);
                    labelName.setFont(Font.font("Cooper Black", 15));
                    labelName.prefHeight(18);
                    labelName.prefWidth(190);
                    labelName.setTextFill(Color.web("#f2efef"));

                    labelScore.setAlignment(Pos.TOP_LEFT);
                    labelScore.setFont(Font.font("Cooper Black", 15));
                    labelScore.prefHeight(18);
                    labelScore.prefWidth(190);
                    labelScore.setTextFill(Color.web("#f2efef"));
                    vboxListPlayer.getChildren().add(hboxNamePlayer);
                    hboxNamePlayer.getChildren().add(labelName);
                    hboxNamePlayer.getChildren().add(labelScore);

                }
            }
        });
    }

    /******************************************************************************************************************/

    public void rebuildMusic(String message) {
        String[] resMessage = message.split(":");
        String messageByte = resMessage[1];

        String[] byteValues = messageByte.substring(1, messageByte.length() - 1).split(",");
        byte[] bytes = new byte[byteValues.length];

        for (int i=0, len=bytes.length; i<len; i++) {
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }

        Convert.byteArrayToFile(bytes, file);
    }

    /******************************************************************************************************************/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTimer();
        Media media;
        media = new Media(new File("src/main/resources/video/bg.mp4").toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        bgView.setMediaPlayer(player);
        DoubleProperty mvw = bgView.fitWidthProperty();
        DoubleProperty mvh = bgView.fitHeightProperty();
        mvw.bind(Bindings.selectDouble(bgView.sceneProperty(), "width"));
        //System.out.println(mvh);
        mvh.bind(Bindings.selectDouble(bgView.sceneProperty(), "height"));
        bgView.setPreserveRatio(true);
        player.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
        player.play();
    }

    public void sendAnswer(ActionEvent actionEvent) {
        if (!author.getText().equals("") || !songName.getText().equals("")) {
            client.sendAnswer(
                    (author.getText().equals("") ? null : author.getText()),
                    (songName.getText().equals("") ? null : songName.getText())
            );
        }
    }
}
