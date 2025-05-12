package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.entities.User;
import org.example.services.ServiceUser;
import org.example.utils.UserSession;

import java.io.IOException;
import java.sql.SQLException;

public class UserAcceuilController {
    @FXML
    private TextField emailconnectTF;

    @FXML
    private TextField mdpconnectTF;

    @FXML
    void ConnecterButtonUser(ActionEvent event) {
        String email = emailconnectTF.getText();
        String password = mdpconnectTF.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.", Alert.AlertType.ERROR);
            return;
        }
        ServiceUser serviceUser = new ServiceUser();
        try {
            User user = serviceUser.findByEmail(email);
            if (user.getStatut() == 0) {
                showAlert("Compte bloqué", "Votre compte a été bloqué. Veuillez contacter l'administrateur.", Alert.AlertType.WARNING);
                return;
            }
            System.out.println("Rôle de l'utilisateur : " + user.getRole());
            if (user != null && user.getPassword().equals(password)) {
                UserSession.setUser(user);
                FXMLLoader fxmlLoader;
                if ("Admin".equals(user.getRole())) {
                    fxmlLoader = new FXMLLoader(getClass().getResource("/AdminAccueil.fxml"));
                } else{

                    fxmlLoader = new FXMLLoader(getClass().getResource("/ProfileUser.fxml"));
                }
                Parent parent = fxmlLoader.load();
                emailconnectTF.getScene().setRoot(parent);
            } else {
                showAlert("Erreur", "Email ou mot de passe incorrect.", Alert.AlertType.ERROR);
            }
        } catch (SQLException | IOException e) {
            showAlert("Erreur", "Une erreur est survenue : " + e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @FXML
    void InscriptionUserButton(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/InscriptionUser.fxml"));
        try {
            Parent parent = fxmlLoader.load();
            emailconnectTF.getScene().setRoot(parent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
