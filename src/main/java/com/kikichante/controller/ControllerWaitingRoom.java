package com.kikichante.controller;

import com.kikichante.client.Client;
import com.kikichante.kikichantefx.Application;
import com.kikichante.utils.ColorOutput;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class ControllerWaitingRoom {
    @FXML
    private VBox VBoxRowClient;

    private Client client;
    private boolean listen = true;
    private List<String> listPlayers = new ArrayList<>();
    private boolean isReady = false;

    /******************************************************************************************************************/

    public void setClient(Client client) {
        this.client = client;
    }

    /******************************************************************************************************************/

    public void getPlayerInit() {
        ColorOutput.blueMessage("Scene in waiting room");
        client.getListPlayerInWaitingRoom();
        try {
            var message = client.readLine();
            if (message.startsWith("LISTCURRENTPLAYERINWAITINGROOM")) {
                messageToListPlayer(message);
            }
            listenner.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /******************************************************************************************************************/

    Thread listenner = new Thread() {
        @Override
        public void run() {
            while (listen) {
                try {
                    var message = client.readLine();
                    if (message.startsWith("EXIT")) {
                        ColorOutput.redMessage("Scene out waitingRoom");
                        break;
                    }
                    handleLine(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void handleLine(String message) {
        if (message.startsWith("UPDATECURRENTPLAYER"))
            messageToListPlayer(message);
        else if (message.startsWith("STARTGAME"))
            switchSceneToInGame();
    }

    public void messageToListPlayer(String message) {
        String[] resListPlayer = message.split(":");
        resListPlayer = Arrays.copyOfRange(resListPlayer, 1 , resListPlayer.length);
        listPlayers = Arrays.asList(resListPlayer);
        printListToIhm(listPlayers);
        System.out.println("resListPlayer = " + listPlayers);
    }

    public void printListToIhm(List<String> playersList) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                VBoxRowClient.getChildren().clear();
                HBox hBox = null;
                for (int i = 0; i<playersList.size(); i++) {
                    if (i%3 == 0) {
                        hBox = new HBox();
                        hBox.setAlignment(Pos.CENTER);
                        Label label = new Label();
                        //TODO => Set le style pour l'affichage des clients
                        label.setText(playersList.get(i));
                        label.setAlignment(Pos.CENTER);
                        label.prefHeight(60);
                        label.prefWidth(120);
                        label.setStyle("--fxbackground-color: red; -fx-border-color: transparent; -fx-border-width: 3;");
                        hBox.getChildren().add(label);
                        VBoxRowClient.getChildren().add(hBox);
                    } else {
                        Label label = new Label();
                        label.setText(playersList.get(i));
                        label.setAlignment(Pos.CENTER);
                        label.setStyle("--fxbackground-color: red; -fx-border-color: transparent; -fx-border-width: 3;");
                        hBox.getChildren().add(label);
                    }
                }
            }
        });
    }

    /******************************************************************************************************************/

    public void onClickReady(ActionEvent actionEvent) {
        if (isReady) {
            this.isReady = false;
            client.setNotReady();
            ((Button) actionEvent.getSource()).setStyle("-fx-background-color: red");
        } else {
            this.isReady = true;
            client.setReady();
            ((Button) actionEvent.getSource()).setStyle("-fx-background-color: green");
        }
    }

    /******************************************************************************************************************/

    public void switchSceneToInGame() {
        this.client.println("EXIT"); //QUITTER MON THREAD
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Stage primaryStage = (Stage)VBoxRowClient.getScene().getWindow();

                    FXMLLoader inGameLoader = new FXMLLoader(Application.class.getResource("ViewJoinListGame.fxml"));
                    Scene viewInGame = new Scene(inGameLoader.load()); // .load charger la scene et lance initializabe

                    ControllerInGame controllerInGame = (ControllerInGame) inGameLoader.getController();
                    controllerInGame.setClient(client);
                    controllerInGame.getPlayerInit();
                    //client.goInListGame();

                    primaryStage.setScene(viewInGame);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /******************************************************************************************************************/

    public void switchSceneListGame(Stage primaryStage) {
        try {
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

    public void onClickReturn(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        client.leaveGame();
        listen = false;
        switchSceneListGame(primaryStage);
    }

}
