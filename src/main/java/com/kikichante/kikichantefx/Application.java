package com.kikichante.kikichantefx;

import com.kikichante.client.Client;
import com.kikichante.controller.ControllerJoin;
import com.kikichante.controller.ControllerMenu;
import com.kikichante.controller.ControllerSigninSignup;
import com.kikichante.controller.ControllerStatistique;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

    static Client client;

    @Override
    public void start(Stage stage) throws IOException {
        //****************
        // Scene init
        //****************
        FXMLLoader menuLoader = new FXMLLoader(Application.class.getResource("ViewMenu.fxml"));
        Scene viewMenu = new Scene(menuLoader.load());

        FXMLLoader statistiqueLoader = new FXMLLoader(Application.class.getResource("ViewStatistique.fxml"));
        Scene viewStatistique = new Scene(statistiqueLoader.load());

        FXMLLoader createGameSettingsLoader = new FXMLLoader(Application.class.getResource("ViewCreateGame.fxml"));
        Scene viewCreateGameSettings = new Scene(createGameSettingsLoader.load());

        FXMLLoader joinGameListLoader = new FXMLLoader(Application.class.getResource("ViewJoinListGame.fxml"));
        Scene viewJoinGameList = new Scene(joinGameListLoader.load());

        FXMLLoader loginLoader = new FXMLLoader(Application.class.getResource("ViewSignInSignUp.fxml"));
        Scene viewLoginLoader = new Scene(loginLoader.load());


        //****************
        // Set controller
        //****************
        ControllerSigninSignup controllerSignInSignUp = (ControllerSigninSignup)loginLoader.getController();
        controllerSignInSignUp.setSceneMenu(viewMenu);
        controllerSignInSignUp.setClient(client);

        ControllerMenu controllerMenu = (ControllerMenu)menuLoader.getController();
        controllerMenu.setSceneStatistique(viewStatistique);
        controllerMenu.setSceneCreateGame(viewCreateGameSettings);
        controllerMenu.setSceneJoinGameList(viewJoinGameList);

        ControllerStatistique controllerStatistique = (ControllerStatistique)statistiqueLoader.getController();
        controllerStatistique.setSceneMenu(viewMenu);

        ControllerJoin controllerGameMenuCreateSettings = (ControllerJoin)createGameSettingsLoader.getController();
        controllerGameMenuCreateSettings.setSceneMenu(viewMenu);

        ControllerJoin controllerGameMenuJoinList = (ControllerJoin)joinGameListLoader.getController();
        controllerGameMenuJoinList.setSceneMenu(viewMenu);

        //****************
        // Start app
        //****************
        stage.setScene(viewLoginLoader);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() {
        System.out.println("Stage is closing");
    }

    public static void main(String[] args) {
//        client = new Client();
        launch();
    }
}
