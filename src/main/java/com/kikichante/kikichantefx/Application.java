package com.kikichante.kikichantefx;

import com.kikichante.client.Client;
import com.kikichante.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Application extends javafx.application.Application {

    static Client client = null;
    static final String ADRESS = "18.195.102.21";   //18.195.102.21
    static final int PORT = 5000;

    @Override
    public void start(Stage stage) throws IOException {
        //****************
        // Scene init
        //****************
        FXMLLoader loginLoader = new FXMLLoader(Application.class.getResource("ViewSignInSignUp.fxml"));
        Scene viewLoginLoader = new Scene(loginLoader.load());

        //****************
        // Set controller
        //****************
        ControllerSigninSignup controllerSignInSignUp = (ControllerSigninSignup)loginLoader.getController();
        controllerSignInSignUp.setClient(client);

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
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {
        Socket socket;

        try {
            socket = new Socket(ADRESS, PORT);
            client = new Client(socket);
            client.uuid();
        } catch (IOException e) {
            System.out.println("Serveur eteint");
            e.printStackTrace();
            //TODO -> Afficher une fenetre erreur avec le message serveur eteint
            System.exit(0);
        }

        launch();
    }
}
