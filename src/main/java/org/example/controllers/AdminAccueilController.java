package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.utils.UserSession;

import java.io.IOException;

public class AdminAccueilController {
    @FXML
    private Label adminlabel;

    @FXML
    void deconnexion(ActionEvent event) {
        try {

            UserSession.clear();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserAccueil.fxml")); // ← adapte selon ton fichier FXML d’accueil
            Parent parent = loader.load();


            adminlabel.getScene().setRoot(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void gererEvenements(ActionEvent event) {

    }

    @FXML
    void voirStatistiques(ActionEvent event) {

    }

    @FXML
    void voirUtilisateurs(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminUsers.fxml"));
            Parent root = loader.load();

            // Récupérer la fenêtre actuelle depuis l'event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
