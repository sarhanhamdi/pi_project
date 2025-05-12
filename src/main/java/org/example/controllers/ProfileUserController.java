package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import org.example.entities.User;
import org.example.utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ProfileUserController implements Initializable {

    @FXML
    private Label profilemaillabel;

    @FXML
    private Label profiletellabel;

    @FXML
    private Label profilevillelabel;

    @FXML
    private Label profilnomlabel;

    @FXML
    private Label profilprenomlabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Charger les données de l'utilisateur depuis le singleton
        User user = UserSession.getUser();
        if (user != null) {
            profilnomlabel.setText(user.getNom());
            profilprenomlabel.setText(user.getPrenom());
            profilemaillabel.setText(user.getEmail());
            profiletellabel.setText(user.getTelephone());
            profilevillelabel.setText(user.getVille());
        }
    }

    @FXML
    void ModifierUserButtonFromProfile(ActionEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ModifierUser.fxml"));
            Parent parent = fxmlLoader.load();

            // Redirection vers la scène ModifierUser
            profilemaillabel.getScene().setRoot(parent);

        } catch (IOException e) {
            e.printStackTrace();
            // En production, évite RuntimeException brut
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Impossible de charger la page de modification.");
            alert.show();
        }


    }
    @FXML
    void DeconnexionUser(ActionEvent event) {
        try {

            UserSession.clear();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserAccueil.fxml")); // ← adapte selon ton fichier FXML d’accueil
            Parent parent = loader.load();


            profilemaillabel.getScene().setRoot(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void demanderCovoiturage(ActionEvent event) {

    }

    @FXML
    void offrirCovoiturage(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/OffrirCovoiturage.fxml"));
            Parent parent = fxmlLoader.load();
            profilemaillabel.getScene().setRoot(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}