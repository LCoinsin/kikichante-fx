package com.kikichante.controller;

import com.kikichante.client.Client;
import com.kikichante.kikichantefx.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerSigninSignup {
    @FXML
    private TextField textfieldInscriptionUsername;
    @FXML
    private TextField textfieldInscriptionPassword;
    @FXML
    private TextField textfieldInscriptionPasswordConfirm;
    @FXML
    private TextField textfieldConnexionUsername;
    @FXML
    private TextField textfieldConnexionPassword;
    @FXML
    private VBox VBoxTextFieldConnexion;
    @FXML
    private VBox VBoxTextFieldInscription;
    @FXML
    private VBox VBoxButtonConnexion;
    @FXML
    private VBox VBoxButtonInscription;

    private Scene sceneMenu;
    private Client client;

    //Changement de scene
    public void setSceneMenu(Scene sceneMenu) {
        this.sceneMenu = sceneMenu;
    }

    public void setClient(Client client) { this.client = client; }

    private void switchToMenu() {
        Stage primaryStage = (Stage)VBoxTextFieldConnexion.getScene().getWindow();

        try {
            FXMLLoader menuLoader = new FXMLLoader(Application.class.getResource("ViewMenu.fxml"));
            Scene viewMenu = new Scene(menuLoader.load());

            ControllerMenu controllerMenu = (ControllerMenu) menuLoader.getController();
            controllerMenu.setClient(client);

            primaryStage.setScene(viewMenu);
            primaryStage.setFullScreen(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //true -> switch vers l'inscription
    private void switchFormButton(boolean bool) {
        VBoxButtonConnexion.setDisable(bool);
        VBoxButtonConnexion.setVisible(!bool);
        VBoxButtonInscription.setDisable(!bool);
        VBoxButtonInscription.setVisible(bool);
    }

    //true -> switch vers l'inscription
    private void switchFormTextField(boolean bool) {
        VBoxTextFieldConnexion.setDisable(bool);
        VBoxTextFieldConnexion.setVisible(!bool);
        VBoxTextFieldInscription.setDisable(!bool);
        VBoxTextFieldInscription.setVisible(bool);
    }

    //ActionEvent de la scene
    public void onClickSwitchToInscription(ActionEvent actionEvent) {
        //Button
        switchFormButton(true);
        //TextField
        switchFormTextField(true);
        resetConnexion();
        resetInscription();
    }

    public void onClickSwicthToConnexion(ActionEvent actionEvent) {
        //Button et TextField
        switchFormButton(false);
        //TextField
        switchFormTextField(false);
        resetInscription();
        resetConnexion();
    }

    public void resetConnexion() {
        textfieldInscriptionUsername.getStyleClass().remove("textfieldError");
        textfieldInscriptionPassword.getStyleClass().remove("textfieldError");
        textfieldInscriptionPasswordConfirm.getStyleClass().remove("textfieldError");
        textfieldConnexionUsername.setText("");
        textfieldConnexionPassword.setText("");
    }

    public void resetInscription() {
        textfieldConnexionUsername.getStyleClass().remove("textfieldError");
        textfieldConnexionPassword.getStyleClass().remove("textfieldError");
        textfieldInscriptionUsername.setText("");
        textfieldInscriptionPassword.setText("");
        textfieldInscriptionPasswordConfirm.setText("");
    }

    public void onClickConnexion(ActionEvent actionEvent) {
        if(textfieldConnexionUsername.getText() != "" && textfieldConnexionPassword.getText() != "") {
            client.connexion(textfieldConnexionUsername.getText(), textfieldConnexionPassword.getText());
            boolean isConnected = false;
            try {
                String res = client.readLine();
                if(res.startsWith("LOGIN")) {
                    String[] resMessage = res.split(":");
                    if (resMessage[1].equals("OK"))
                        isConnected = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(isConnected) {
                switchToMenu();
            } else {
                textfieldConnexionUsername.getStyleClass().add("textfieldError");
                textfieldConnexionPassword.getStyleClass().add("textfieldError");
            }
        } else {
            if (textfieldConnexionUsername.getText() == "") {
                textfieldConnexionUsername.getStyleClass().add("textfieldError");
            }
            if (textfieldConnexionPassword.getText() == "") {
                textfieldConnexionPassword.getStyleClass().add("textfieldError");
            }
        }
    }

    public void onClickInscription(ActionEvent actionEvent) {
        if (textfieldInscriptionUsername.getText()!="" && textfieldInscriptionPassword.getText()!="" && textfieldInscriptionPasswordConfirm.getText()!="" && textfieldInscriptionPassword.getText().equals(textfieldInscriptionPasswordConfirm.getText()))  {
            client.inscription(textfieldInscriptionUsername.getText(), textfieldInscriptionPassword.getText());
            boolean isInscription = false;
            try {
                String res = client.readLine();
                if (res.startsWith("INSCRIPTION")) {
                    String[] resMessage = res.split(":");
                    if (resMessage[1].equals("OK"))
                        isInscription = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (isInscription) {
                switchToMenu();
            } else {
                textfieldInscriptionUsername.getStyleClass().add("textfieldError");
                textfieldInscriptionPassword.getStyleClass().add("textfieldError");
                textfieldInscriptionPasswordConfirm.getStyleClass().add("textfieldError");
            }
        } else {
            if (textfieldInscriptionUsername.getText() == "") {
                textfieldInscriptionUsername.getStyleClass().add("textfieldError");
            }
            if (textfieldInscriptionPassword.getText() == "") {
                textfieldInscriptionPassword.getStyleClass().add("textfieldError");
            }
            if (textfieldInscriptionPasswordConfirm.getText() == "") {
                textfieldInscriptionPasswordConfirm.getStyleClass().add("textfieldError");
            }
        }
    }

    public void deleteErrorMouse(MouseEvent mouseEvent) {
        Node source = (Node) mouseEvent.getSource();
        source.getStyleClass().remove("textfieldError");
    }

    public void deleteErrorKey(KeyEvent contextMenuEvent) {
        Node source = (Node) contextMenuEvent.getSource();
        source.getStyleClass().remove("textfieldError");
    }
}
