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
            System.out.println("echec");
        }
    }
    public void printScoreFx(String message ){
        String[] messageT = message.split(":");
        messageT = Arrays.copyOfRange(messageT, 1 , messageT.length);
        TreeMap<Integer, String> monTreemap = new TreeMap<>(){};
        int i =0;
        for ( String r : messageT){
            i++;
            String[] messageTT = r.split("-");
            monTreemap.put(( Integer.valueOf(messageTT[1])),(messageTT[0]));
        }
      System.out.println(monTreemap);
//for (var entry : monTreemap.entrySet())

     String first = monTreemap.lastEntry().getValue();
        //monTreemap.pollLastEntry();
        premier.getChildren().clear();
        Label labelName = new Label(first);
        premier.getChildren().add(labelName);
     if (i>1){
         monTreemap.pollLastEntry();
         String second = monTreemap.lastEntry().getValue();
         deuxieme.getChildren().clear();
         Label labelName2 = new Label(second);
         deuxieme.getChildren().add(labelName);
     }
        if (i>2){
            monTreemap.pollLastEntry();
            String troisi = monTreemap.lastEntry().getValue();

            troisieme.getChildren().clear();
            Label labelName3 = new Label(troisi);
            troisieme.getChildren().add(labelName);
        }

       // String first = monTreemap.firstEntry().getKey();


       // monTreemap.pollFirstEntry();



    }

    public void retourMenu(ActionEvent actionEvent) {
        client.println("LEAVESCORESCENE");
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
