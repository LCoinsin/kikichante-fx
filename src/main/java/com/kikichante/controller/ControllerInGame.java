package com.kikichante.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ControllerInGame implements Initializable {

    @FXML
    private HBox hBox;

    @FXML
    private Label lbl;

    private int compteARebours = 5;

    private Timer timer;

    public void joinGame() {

    }


    public void setTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(compteARebours > 0)
                {
                    Platform.runLater(() -> lbl.setText((String.valueOf(compteARebours))));
                    System.out.println(compteARebours);
                    compteARebours--;
                }
                else
                    timer.cancel();
            }
        }, 1000,1000);
    }

    public void onClickReturn() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
