package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

import java.io.IOException;


public class ConnecterAppController {

    @FXML
    private Label bienvenuelabel;

    @FXML
    void AdminConnectButton(ActionEvent event) {

    }

    @FXML
    void UserConnectButton(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UserAccueil.fxml"));
        try {
            Parent parent = fxmlLoader.load();
            bienvenuelabel.getScene().setRoot(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
