package com.kikichante.controller;

import com.kikichante.client.Client;
import com.kikichante.kikichantefx.Application;
import com.kikichante.utils.ColorOutput;
import com.kikichante.utils.Convert;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import javafx.scene.text.Font;
import org.controlsfx.control.cell.MediaImageCell;
import org.kordamp.bootstrapfx.scene.layout.Panel;

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
    @FXML
    private ProgressBar songProgressBar;
    @FXML
    private Button buttonSend;

    private List<String> listPlayers = new ArrayList<>();
    private Client client;
    private int compteARebours = 4;
    private Timer timer;
    private File file = new File("src/main/resources/musiques/out.mp3");
    private Media song;
    private MediaPlayer player;

    private Timer timerProgressBar;
    private TimerTask task;
    private boolean running;
    private boolean winner = false;

    /******************************************************************************************************************/

    public void setClient(Client client) {
        this.client = client;
    }

    /******************************************************************************************************************/

    public void getPlayerInit() {
        setButtonSend("Pret");
        ColorOutput.blueMessage("Scene in InGame");
        client.getListPlayerInGame();
        client.askMusic();
        listenner.start();
        //setTimer();
        //cantSendAnswer();
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
                else {
                    timer.cancel();
                    player.play();
                    musicDuration();
                    canSendAnswer();
                    compteARebours = 4;
                }
            }
        }, 1000,1000);
    }

    public void musicDuration(){
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                running = true;
                double current = player.getCurrentTime().toSeconds();
                double end = song.getDuration().toSeconds();
                System.out.println(current/end);
                songProgressBar.setProgress(current/end);

                if(current/end == 1){
                    cancelTimer();
                    player.stop();
                    client.println("MUSICNOTFOUND");
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public void cancelTimer(){
        running = false;
        timer.cancel();
    }

    public void onClickReturn(ActionEvent actionEvent) {
        client.leaveInGame();
        try {
            Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            FXMLLoader listGameLoader = new FXMLLoader(Application.class.getResource("ViewListGame.fxml"));
            Scene viewListGame = new Scene(listGameLoader.load());

            ControllerListGame controllerListGame = (ControllerListGame) listGameLoader.getController();
            controllerListGame.setClient(client);
            controllerListGame.getActiveGames();

            client.goInListGame();

            primaryStage.setScene(viewListGame);
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }

    /******************************************************************************************************************/

    Thread listenner = new Thread() {
        @Override
        public void run() {
            String message = null;
            while (true) {
                try {
                    message = client.readLine();
                    if (message.startsWith("EXIT")) {
                        ColorOutput.redMessage("BREAK");
                        break;
                    }
                    handleLine(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(message.startsWith("EXITENDGAME")) {
                switchToRanking();
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
             setButtonSend("Valider");

             String path = "src/main/resources/musiques/out.mp3";
             song = new Media(new File(path).toURI().toString());
             player = new MediaPlayer(song);

             setTimer();
         }
         else if (message.startsWith("STOPMUSICWITHWINNER")) {
             cancelTimer();
             player.stop();
             setButtonSend("Pret");
             client.getListPlayerInGame();
             client.askMusic();
             songProgressBar.setProgress(0);
             author.setText("");
             songName.setText("");
             System.out.println("message = " + message); //TODO -> Afficher le vainqueur a l'ecran
         }
         else if (message.startsWith("STOPWITHOUTWINNER")) {
             setButtonSend("Pret");
             client.getListPlayerInGame();
             client.askMusic();
             author.setText("");
             songName.setText("");
             songProgressBar.setProgress(0);
         }
         else if (message.startsWith("ENDGAME")) {
             client.println("EXITENDGAME");
             cancelTimer();
             task.cancel();
             player.stop();
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
                    String score= playerScore[1];
                    String state = playerScore[2];
                    //System.out.println(playerScore[1]);
                    HBox hboxNamePlayer = new HBox ();
                    hboxNamePlayer.setStyle("-fx-max-height: 40; -fx-min-height: 40; -fx-pref-height: 40");
                    hboxNamePlayer.setStyle("-fx-max-width: 190; -fx-min-width: 190; -fx-pref-width: 190");
                    hboxNamePlayer.setStyle("-fx-background-color: white; -fx-background-radius: 5px");

                    Label labelName = new Label(name);
                    Label labelScore = new Label(score);

                    String path = (state.equals("true") ? "src/main/resources/img/OK.png" : "src/main/resources/img/KO.png");
                    Image check = new Image(new File(path).toURI().toString());
                    ImageView imageState = new ImageView(check);

                    imageState.setFitHeight(20);
                    imageState.setFitWidth(20);

                    labelName.setStyle("-fx-max-height: 40; -fx-min-height: 40; -fx-pref-height: 40; -fx-max-width: 120; -fx-min-width: 120; -fx-pref-width: 120; -fx-text-fill: black");
                    labelName.setAlignment(Pos.CENTER_LEFT);
                    labelName.setFont(Font.font("Cooper Black", 15));

                    labelScore.setStyle("-fx-max-height: 40; -fx-min-height: 40; -fx-pref-height: 40; -fx-max-width: 100; -fx-min-width: 100; -fx-pref-width: 100; -fx-text-fill: black");
                    labelScore.setAlignment(Pos.CENTER);
                    labelScore.setFont(Font.font("Cooper Black", 15));

                    hboxNamePlayer.setAlignment(Pos.CENTER);
                    hboxNamePlayer.getChildren().add(imageState);
                    Separator separator = new Separator();
                    separator.setOrientation(Orientation.VERTICAL);
                    hboxNamePlayer.getChildren().add(separator);
                    hboxNamePlayer.getChildren().add(labelName);
                    hboxNamePlayer.getChildren().add(labelScore);

                    vboxListPlayer.getChildren().add(hboxNamePlayer);

                }
            }
        });
    }

    /******************************************************************************************************************/

    public void switchToRanking() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Stage primaryStage = (Stage)bgView.getScene().getWindow();

                    FXMLLoader rankingLoader = new FXMLLoader(Application.class.getResource("ViewRanking.fxml"));
                    Scene viewRanking = new Scene(rankingLoader.load());

                    ControllerRanking controllerRanking = (ControllerRanking) rankingLoader.getController();
                    controllerRanking.setClient(client);

                    primaryStage.setScene(viewRanking);

                    controllerRanking.printScore();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /******************************************************************************************************************/

    public void canSendAnswer() {
        buttonSend.setDisable(false);
    }

    public void cantSendAnswer() {
        buttonSend.setDisable(true);
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

        client.haveMusic();
    }

    /******************************************************************************************************************/

    public void setButtonSend(String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                buttonSend.setText(text);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Media media;
        media = new Media(new File("src/main/resources/video/bg.mp4").toURI().toString());
        MediaPlayer playerBg = new MediaPlayer(media);
        bgView.setMediaPlayer(playerBg);
        DoubleProperty mvw = bgView.fitWidthProperty();
        DoubleProperty mvh = bgView.fitHeightProperty();
        mvw.bind(Bindings.selectDouble(bgView.sceneProperty(), "width"));
        //System.out.println(mvh);
        mvh.bind(Bindings.selectDouble(bgView.sceneProperty(), "height"));
        bgView.setPreserveRatio(true);
        playerBg.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);

        playerBg.play();
    }

    public void sendAnswer(ActionEvent actionEvent) {
        if (buttonSend.getText().equals("Pret")) {
            client.readyForThisRound();
            cantSendAnswer();
        }
        else if (buttonSend.getText().equals("Valider")) {
            if (!author.getText().equals("") || !songName.getText().equals("")) {
                client.sendAnswer(
                        (author.getText().equals("") ? null : author.getText()),
                        (songName.getText().equals("") ? null : songName.getText())
                );
            }
        }
    }
}
