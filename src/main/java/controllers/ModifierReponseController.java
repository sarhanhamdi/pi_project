package controllers;

import entities.Reclamation;
import entities.ReponseReclamation;
import services.ServiceReclamation;
import services.ServiceReponseReclamation;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class ModifierReponseController {

    @FXML
    private ComboBox<String> reclamationComboBox;

    @FXML
    private TextArea reponseField;

    @FXML
    private Label messageLabel;

    private ReponseReclamation reponseToModify;
    private ServiceReponseReclamation serviceReponse;
    private ServiceReclamation serviceReclamation;
    private List<Reclamation> reclamations;

    // Method to initialize the controller with the response to modify
    public void setReponse(ReponseReclamation reponse) {
        this.reponseToModify = reponse;
        this.serviceReponse = new ServiceReponseReclamation();
        this.serviceReclamation = new ServiceReclamation();
        loadReclamations();
        loadReponseData();
    }

    // Load all reclamations into the ComboBox
    private void loadReclamations() {
        try {
            reclamations = serviceReclamation.afficher();
            for (Reclamation reclamation : reclamations) {
                reclamationComboBox.getItems().add("Réclamation #" + reclamation.getId_reclamation());
            }
        } catch (SQLException e) {
            messageLabel.setText("Erreur lors du chargement des réclamations : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
        }
    }

    // Load the data of the response to modify
    private void loadReponseData() {
        if (reponseToModify != null) {
            reclamationComboBox.setValue("Réclamation #" + reponseToModify.getIdReclamation());
            reponseField.setText(reponseToModify.getTexte());
        }
    }

    @FXML
    private void saveChanges() {
        if (reponseToModify == null) {
            messageLabel.setText("Aucune réponse sélectionnée !");
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            return;
        }

        String selectedReclamation = reclamationComboBox.getValue();
        String reponseText = reponseField.getText();

        if (selectedReclamation == null || reponseText.isEmpty()) {
            messageLabel.setText("Tous les champs sont obligatoires !");
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            return;
        }

        // Extract reclamation ID from the selected string (format: "Réclamation #ID")
        int reclamationId;
        try {
            reclamationId = Integer.parseInt(selectedReclamation.split("#")[1]);
        } catch (NumberFormatException e) {
            messageLabel.setText("Erreur dans le format de la réclamation sélectionnée !");
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            return;
        }

        Reclamation reclamation = reclamations.stream()
                .filter(r -> r.getId_reclamation() == reclamationId)
                .findFirst()
                .orElse(null);

        if (reclamation == null) {
            messageLabel.setText("Réclamation non trouvée !");
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            return;
        }

        reponseToModify.setIdReclamation(reclamationId);
        reponseToModify.setTexte(reponseText);

        try {
            serviceReponse.modifier(reponseToModify);
            messageLabel.setText("Réponse modifiée avec succès !");
            messageLabel.setStyle("-fx-text-fill: #4fc3f7;");
            clearFieldsAndClose();
        } catch (SQLException e) {
            messageLabel.setText("Erreur lors de la modification : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
        }
    }

    @FXML
    private void cancel() {
        clearFieldsAndClose();
    }

    private void clearFieldsAndClose() {
        reclamationComboBox.getSelectionModel().clearSelection();
        reponseField.clear();
        Stage stage = (Stage) reponseField.getScene().getWindow();
        stage.close();
    }
}