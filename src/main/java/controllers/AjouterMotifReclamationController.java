package controllers;

import entities.MotifReclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceMotifReclamation;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterMotifReclamationController {

    @FXML
    private TextField nomField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Button ajouterButton;

    @FXML
    private Button cancelButton;

    private final ServiceMotifReclamation serviceMotif = new ServiceMotifReclamation();

    @FXML
    void ajouterMotif(ActionEvent event) {
        String nom = nomField.getText().trim();
        String description = descriptionField.getText().trim();

        if (nom.isEmpty() || description.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Champs vides", "Veuillez remplir tous les champs.", "#ffab91");
            return;
        }

        MotifReclamation nouveauMotif = new MotifReclamation(nom, description);

        try {
            serviceMotif.ajouter(nouveauMotif);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Motif ajouté avec succès.", "#4fc3f7");
            nomField.clear();
            descriptionField.clear();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout : " + e.getMessage(), "#ffab91");
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListMotifReclamation.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Liste des Motifs");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window
            Stage currentStage = (Stage) cancelButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du retour à la liste : " + e.getMessage(), "#ffab91");
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String titre, String contenu, String textFill) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setContentText(contenu);
        // Apply custom styling to match theme
        alert.getDialogPane().setStyle("-fx-font-family: 'System'; -fx-font-size: 13px; -fx-text-fill: " + textFill + ";");
        alert.show();
    }
}
