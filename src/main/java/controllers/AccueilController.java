package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AccueilController {

    @FXML
    private Button gestionReclamationButton;

    @FXML
    private Button gestionReponseButton;

    @FXML
    private Button gestionMotifButton;

    @FXML
    private void openListReclamation() {
        navigateTo("/AjouterReclamation.fxml", "Liste des Réclamations");
    }

    @FXML
    private void openListReponse() {
        navigateTo("/ListReponse.fxml", "Liste des Réponses");
    }

    @FXML
    private void openListMotif() {
        navigateTo("/ListMotifReclamation.fxml", "Liste des Motifs");
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);

            // Close the current window
            Stage currentStage = (Stage) gestionReclamationButton.getScene().getWindow();
            currentStage.close();

            // Show the new window
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Show an alert to the user
            new Alert(Alert.AlertType.ERROR, "Erreur lors du chargement de la page : " + e.getMessage()).showAndWait();
        }
    }
}