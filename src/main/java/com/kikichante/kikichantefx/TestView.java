package com.kikichante.kikichantefx;

import com.kikichante.controller.ControllerInGame;
import com.kikichante.controller.ControllerListGame;
import com.kikichante.kikichantefx.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TestView extends javafx.application.Application {

    private int x = 5;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("ViewJoinListGame.fxml"));
        Scene view = new Scene(loader.load());

        //****************
        // Set controller
        //****************
        ControllerInGame controllerInGame = (ControllerInGame) loader.getController();

        //ControllerListGame controllerListGame = (ControllerListGame) loader.getController();
        stage.setScene(view);
        stage.show();
        controllerInGame.setTimer();

    }

    public static void main(String[] args) {
        launch();
    }
}
