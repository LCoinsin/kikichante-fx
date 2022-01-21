package com.kikichante.controller;

import com.kikichante.client.Client;
import com.kikichante.kikichantefx.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerCreateGame {
    @FXML
    private TextField textfieldGameName;

    private Client client;

    /******************************************************************************************************************/

    public void setClient(Client client) {
        this.client = client;
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

    /******************************************************************************************************************/

    private void switchWaitingRoom() {

        try {
            Stage primaryStage = (Stage)textfieldGameName.getScene().getWindow();

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

    public void onClickCreateGame(ActionEvent actionEvent) {
        client.createGame(textfieldGameName.getText());
        boolean createGame = false;
        try {
            String res = client.readLine();
            if (res.startsWith("CREATEGAME")) {
                String[] resMessage = res.split(":");
                if (resMessage[1].equals("OK"))
                    createGame = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (createGame)
            switchWaitingRoom();
    }
}
