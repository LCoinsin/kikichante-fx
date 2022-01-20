package com.kikichante.kikichantefx;

import com.kikichante.controller.ControllerListGame;
import com.kikichante.kikichantefx.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestView extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Application.class.getResource("ViewListGame.fxml"));
        Scene view = new Scene(loader.load());

        //ControllerListGame controllerListGame = (ControllerListGame) loader.getController();

        stage.setScene(view);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
