package com.kikichante.controller;

import com.kikichante.client.Client;
import com.kikichante.kikichantefx.Application;
import com.kikichante.utils.ColorOutput;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
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

    public void printScoreFx(String message) {
        String[] messageT = message.split(":");
        messageT = Arrays.copyOfRange(messageT, 1, messageT.length);
        HashMap<String,Integer> map = new HashMap<String,Integer>();
        ValueComparator comparateur = new ValueComparator(map);
       // TreeMap<String, Integer> monTreemap = new TreeMap<>() {};
        TreeMap<String,Integer> monTreemap = new TreeMap<String,Integer>(comparateur);
        int i = 0;
        for (String r : messageT) {
            i++;
            String[] messageTT = r.split("-");
            monTreemap.put(((messageTT[0])), Integer.valueOf(messageTT[1]));
        }

        for (var entry : monTreemap.entrySet()){

        }
        System.out.println(monTreemap);
        premier.getChildren().clear();
        deuxieme.getChildren().clear();
        troisieme.getChildren().clear();
        String first = monTreemap.lastEntry().getKey();

        Label labelName = new Label(first);
        labelName.setFont(Font.font("Cooper Black", 15));
        premier.getChildren().add(labelName);
        if (i > 1) {
            monTreemap.pollLastEntry();
            String second = monTreemap.lastEntry().getKey();
            deuxieme.getChildren().clear();
            Label labelName2 = new Label(second);
            labelName2.setFont(Font.font("Cooper Black", 15));
            deuxieme.getChildren().add(labelName2);
        }
        if (i > 2) {
            monTreemap.pollLastEntry();
            String troisi = monTreemap.lastEntry().getKey();
            troisieme.getChildren().clear();
            Label labelName3 = new Label(troisi);
            labelName3.setAlignment(Pos.CENTER_LEFT);
            labelName3.setFont(Font.font("Cooper Black", 15));
            troisieme.getChildren().add(labelName3);
        }


    }

    public void retourMenu(ActionEvent actionEvent) {
        client.println("LEAVESCORESCENE");
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        try {
            FXMLLoader menuLoader = new FXMLLoader(Application.class.getResource("ViewMenu.fxml"));
            Scene viewMenu = new Scene(menuLoader.load());

            ControllerMenu controllerMenu = (ControllerMenu) menuLoader.getController();
            controllerMenu.setClient(client);

            primaryStage.setScene(viewMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class ValueComparator implements Comparator<String> {
    Map<String, Integer> base;
    public ValueComparator(Map<String, Integer> base) {
        this.base = base;
    }

    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        }
    }
}
