package com.kikichante.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    //private BddConnection bdd;
    //private Client client;

    //Changement de scene
    public void setSceneMenu(Scene sceneMenu) {
        this.sceneMenu = sceneMenu;
    }

    //public void setBdd(BddConnection bdd) { this.bdd = bdd; }

    //public void setClient(Client client) { this.client = client; }

    private void switchToMenu() {
        Stage primaryStage = (Stage)VBoxTextFieldConnexion.getScene().getWindow();
        primaryStage.setScene(this.sceneMenu);
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
            //client.connexion(textfieldConnexionUsername.getText(), textfieldConnexionPassword.getText());
            //Account accountConnexion = new Connexion(textfieldConnexionUsername.getText(), textfieldConnexionPassword.getText());

            //if(this.bdd.queryConnection(textfieldConnexionUsername.getText(), textfieldConnexionPassword.getText())) {
            if(true) {
                //this.bdd.queryTest();
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
        if (textfieldInscriptionUsername.getText()!="" && textfieldInscriptionPassword.getText()!="" && textfieldInscriptionPasswordConfirm.getText()!="")  {
            //this.bdd.queryInscription(textfieldInscriptionUsername.getText(), textfieldInscriptionPassword.getText());
            //Button et TextField
            switchFormButton(false);
            //TextField
            switchFormTextField(false);
            resetInscription();
            resetConnexion();
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
