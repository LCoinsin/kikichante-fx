package com.kikichante.controller;

import com.kikichante.client.Client;
import com.kikichante.kikichantefx.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ControllerListGame implements Initializable {
    @FXML
    private Button retour;
    @FXML
    private VBox Vbox;
    private ToggleGroup groupeListGame = new ToggleGroup();

    private String nameGameToJoin;
    private Scene sceneBack;
    private Client client;

    /******************************************************************************************************************/

    public void setSceneBack(Scene sceneBack) {
        this.sceneBack = sceneBack;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    /******************************************************************************************************************/

    public void getActiveGames() {
        client.getListGame();
        String listGame = null;
        try {
            listGame = client.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("listGame = " + listGame);
        String[] resListGame = listGame.split(":");
        resListGame = Arrays.copyOfRange(resListGame, 1, resListGame.length);

        for (String name : resListGame) {
            RadioButton rb = new RadioButton(name);
            rb.setToggleGroup(groupeListGame);
            rb.setUserData(name);
            Vbox.getChildren().add(rb);
        }
    }

    /******************************************************************************************************************/

    public void onClickReturn(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        try {
            FXMLLoader menuLoader = new FXMLLoader(Application.class.getResource("ViewMenu.fxml"));
            Scene viewMenu = new Scene(menuLoader.load());

            ControllerMenu controllerMenu = (ControllerMenu) menuLoader.getController();
            controllerMenu.setClient(client);

            primaryStage.setScene(viewMenu);
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }

    public void go(ActionEvent actionEvent) {

    }

    public void switchWaitingRoom() {
        try {
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

            client.joinGame(groupeListGame.getSelectedToggle().getUserData().toString());
            boolean isJoin = false;

            try {
                var res = client.readLine();
                if(res.startsWith("JOINGAME")) {
                    String[] resMessage = res.split(":");
                    if (resMessage[1].equals("OK"))
                        isJoin = true;
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            if (isJoin)
                switchWaitingRoom();
        }
        else {
            //TODO -> Faire une erreur visuel pour le client
        }

    }

    /******************************************************************************************************************/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
