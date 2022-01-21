package com.kikichante.kikichantefx;

import com.kikichante.controller.ControllerListGame;
import com.kikichante.kikichantefx.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

public class TestView extends javafx.application.Application {
    @FXML
    private HBox Hbox;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("ViewJoinListGame.fxml"));
        Scene view = new Scene(loader.load());

        //ControllerListGame controllerListGame = (ControllerListGame) loader.getController();

        stage.setScene(view);
        stage.show();
    }
    static void countDown( int x ) {
        while (x > 0 ) {
            System.out.println("x = " + x);
            TextField rb = new TextField(String.valueOf(x));
            try {
                Thread.sleep(1000);
            } catch (Exception e) {}
            x--;
        }
    }


    public static void main(String[] args) {
        launch();
    }
}
