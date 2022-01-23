package com.kikichante.controller;

import com.kikichante.client.Client;
import com.kikichante.kikichantefx.Application;
import com.kikichante.utils.ColorOutput;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerRanking {
    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    public void printScore() {
        client.getScorePlayerEnGame();
        try {
            var message = client.readLine();
            handleMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleMessage(String message) {
        if(message.startsWith("SCORESCREEN")) {
            ColorOutput.blueMessage(message);
        } else {
            System.out.println(message);
        }
    }

    public void retourMenu(ActionEvent actionEvent) {
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

}
