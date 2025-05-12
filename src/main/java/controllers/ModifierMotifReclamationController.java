package Controllers;

import entities.MotifReclamation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceMotifReclamation;

import java.sql.SQLException;

public class ModifierMotifReclamationController {

    @FXML
    private TextField nomField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Label messageLabel;

    private MotifReclamation motifToModify;
    private ServiceMotifReclamation serviceMotif;

    public void setMotifReclamation(MotifReclamation motif) {
        this.motifToModify = motif;
        this.serviceMotif = new ServiceMotifReclamation();
        loadMotifData();
    }

    private void loadMotifData() {
        if (motifToModify != null) {
            nomField.setText(motifToModify.getNom());
            descriptionField.setText(motifToModify.getDescription());
        }
    }

    @FXML
    private void saveChanges() {
        if (motifToModify == null) {
            messageLabel.setText("Aucun motif sélectionné !");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String nom = nomField.getText().trim();
        String description = descriptionField.getText().trim();

        if (nom.isEmpty() || description.isEmpty()) {
            messageLabel.setText("Tous les champs sont obligatoires !");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        motifToModify.setNom(nom);
        motifToModify.setDescription(description);

        try {
            serviceMotif.modifier(motifToModify);
            messageLabel.setText("Motif modifié avec succès !");
            messageLabel.setStyle("-fx-text-fill: green;");
            clearFieldsAndClose();
        } catch (SQLException e) {
            messageLabel.setText("Erreur lors de la modification : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void cancel() {
        clearFieldsAndClose();
    }

    private void clearFieldsAndClose() {
        nomField.clear();
        descriptionField.clear();
        messageLabel.setText("");
        Stage stage = (Stage) nomField.getScene().getWindow();
        stage.close();
    }
}