package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import org.example.entities.User;
import org.example.services.ServiceUser;
import org.example.utils.UserSession;
import org.example.utils.VerificationSession;

import java.io.IOException;
import java.sql.SQLException;

public class CodeVerificationController {

    @FXML
    private TextField codeField;

    @FXML
    private Label statusLabel;

    @FXML
    private void initialize() {
        // Valider avec la touche "Entrée"
        codeField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                handleVerify();
            }
        });
    }

    @FXML
    private void handleVerify() {
        String codeSaisi = codeField.getText();
        String codeCorrect = VerificationSession.getCode();

        if (codeSaisi.equals(codeCorrect)) {
            User user = UserSession.getUser();
            try {
                new ServiceUser().ajouter(user);
                showAlert("Succès", "Inscription réussie!", Alert.AlertType.INFORMATION);
                // Redirection vers l’accueil ou le login
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserAccueil.fxml"));
                Parent parent = null;
                try {
                    parent = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                codeField.getScene().setRoot(parent);
            } catch (SQLException e) {
                showAlert("Erreur", "Impossible d'ajouter l'utilisateur.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Erreur", "Code incorrect. Veuillez réessayer.", Alert.AlertType.ERROR);
        }
    }
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}