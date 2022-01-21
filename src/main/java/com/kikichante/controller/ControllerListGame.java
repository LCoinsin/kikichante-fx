package com.kikichante.controller;

import com.kikichante.client.Client;
import com.kikichante.kikichantefx.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerListGame implements Initializable {
    @FXML
    private Button retour;
    @FXML
    private VBox Vbox;
    private ToggleGroup groupeListGame = new ToggleGroup();

    private String nameGameToJoin;
    private List<String> activeGames = new ArrayList<>();
    private Client client;
    private boolean listen = true;

    /******************************************************************************************************************/

    public void setClient(Client client) {
        this.client = client;
    }

    /******************************************************************************************************************/

    public void getActiveGames() {
        client.getListGame();
        try {
            var message = client.readLine();
            if (message.startsWith("LISTGAME")) {
                messageToListActiveGame(message);
            }
            listenner.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Thread listenner = new Thread() {
        @Override
        public void run() {
            while (listen)
                try {
                    System.out.println("CONTROLLER LIST GAME THREAD");
                    var message = client.readLine();
                    System.out.println(message);
                    if (message.startsWith("EXIT"))
                        break;
                    handleLine(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    };

    public void handleLine(String message) {
        if (message.startsWith("LISTGAME"))
            messageToListActiveGame(message);
    }

    public void messageToListActiveGame(String message) {
        String[] resActiveGames = message.split(":");
        resActiveGames = Arrays.copyOfRange(resActiveGames, 1 , resActiveGames.length);
        activeGames = Arrays.asList(resActiveGames);
        printToIhm(activeGames);
    }

    public void printToIhm(List<String> activeGames) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Vbox.getChildren().clear();
                for (String name : activeGames) {
                    RadioButton rb = new RadioButton(name);
                    rb.setToggleGroup(groupeListGame);
                    rb.setUserData(name);
                    Vbox.getChildren().add(rb);
                }
            }
        });
    }

    /******************************************************************************************************************/

    public void onClickReturn(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        try {
            FXMLLoader menuLoader = new FXMLLoader(Application.class.getResource("ViewMenu.fxml"));
            Scene viewMenu = new Scene(menuLoader.load());

            ControllerMenu controllerMenu = (ControllerMenu) menuLoader.getController();
            controllerMenu.setClient(client);

            client.println("EXIT");
            client.goOutListGame();

            primaryStage.setScene(viewMenu);
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }

    public void switchWaitingRoom() {
        try {
            client.goOutListGame();

            Stage primaryStage = (Stage)retour.getScene().getWindow();

            FXMLLoader waitingRoomLoader = new FXMLLoader(Application.class.getResource("ViewWaitingRoom.fxml"));
            Scene viewWaitingRoom = new Scene(waitingRoomLoader.load());

            ControllerWaitingRoom controllerWaitingRoom = (ControllerWaitingRoom)waitingRoomLoader.getController();
            controllerWaitingRoom.setClient(client);
            controllerWaitingRoom.getPlayerInit();

            primaryStage.setScene(viewWaitingRoom);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void joinGame(ActionEvent actionEvent) {
        if (groupeListGame.getSelectedToggle() != null) {
            client.println("EXIT");

            client.joinGame(groupeListGame.getSelectedToggle().getUserData().toString());
            try {
                var message = client.readLine();
                if (message.startsWith("JOINGAME")) {
                    System.out.println("Rejoindre une game");
                    boolean isJoin = false;
                    String[] resMessage = message.split(":");
                    if (resMessage[1].equals("OK"))
                        isJoin = true;
                    if (isJoin) {
                        listen = false;
                        switchWaitingRoom();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucune game selected");
            //TODO -> Faire une erreur visuel pour le client
        }
    }

    /******************************************************************************************************************/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
