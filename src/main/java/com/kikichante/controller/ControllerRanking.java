package com.kikichante.controller;

import com.kikichante.client.Client;
import com.kikichante.kikichantefx.Application;
import com.kikichante.utils.ColorOutput;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
public class ControllerRanking {


    private Client client;
    @FXML
    private VBox deuxieme;
    @FXML
    private VBox premier;
    @FXML
    private VBox troisieme;


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
            printScoreFx(message);
        } else {
            System.out.println(message);
        }
    }
    public void printScoreFx(String message ){
        String[] messageT = message.split(":");
        messageT = Arrays.copyOfRange(messageT, 1 , messageT.length);
        TreeMap<String, Integer> monTreemap = new TreeMap<>(){};

        for (String joueur : messageT ){
            String[] test = message.split("-");
            monTreemap.put((test[0]),( Integer.valueOf(test[1])));

        }
        String first = monTreemap.firstEntry().getKey();
        monTreemap.pollFirstEntry();
        premier.getChildren().clear();
        Label labelName = new Label(first);
        premier.getChildren().add(labelName);

        monTreemap.pollFirstEntry();
        String deux = monTreemap.firstEntry().getKey();
        deuxieme.getChildren().clear();
        Label labelName2 = new Label(deux);
        deuxieme.getChildren().add(labelName2);

        monTreemap.pollFirstEntry();
        String trois = monTreemap.firstEntry().getKey();
        troisieme.getChildren().clear();
        Label labelName3 = new Label(trois);
        troisieme.getChildren().add(labelName3);

        deuxieme.getChildren().clear();
        troisieme.getChildren().clear();


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
