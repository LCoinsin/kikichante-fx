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

    private int x = 5;

    private Timer timer;

    public void joinGame() {

    }

    public void countDown() {
//        TextField rb = new TextField();
        System.out.println(x);
//        hBox.getChildren().add(tF);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
//                Hbox.getChildren().clear();

                while (x > 0 ) {
                    System.out.println("x = " + x);
                    lbl.setText(String.valueOf(x));
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {}
                    x--;
                }
            }
        });
    }

    public void setTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(x > 0)
                {
                    Platform.runLater(() -> lbl.setText((String.valueOf(x))));
                    System.out.println(x);
                    x--;
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
