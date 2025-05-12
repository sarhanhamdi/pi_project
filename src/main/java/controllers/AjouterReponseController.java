package Controllers;

import entities.Reclamation;
import entities.ReponseReclamation;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import services.ServiceReclamation;
import services.ServiceReponseReclamation;

import java.sql.SQLException;
import java.util.List;

public class AjouterReponseController {

    @FXML
    private ComboBox<Reclamation> reclamationComboBox;

    @FXML
    private TextArea reponseField;

    @FXML
    private Label messageLabel;

    private ServiceReponseReclamation serviceReponse;
    private ServiceReclamation serviceReclamation;
    private List<Reclamation> reclamations;

    @FXML
    public void initialize() {
        serviceReponse = new ServiceReponseReclamation();
        serviceReclamation = new ServiceReclamation();
        loadReclamations();
    }

    private void loadReclamations() {
        try {
            reclamations = serviceReclamation.afficher();
            reclamationComboBox.getItems().setAll(reclamations);
            reclamationComboBox.setCellFactory(lv -> new javafx.scene.control.ListCell<Reclamation>() {
                @Override
                protected void updateItem(Reclamation item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.getId_reclamation() + " - " + item.getContenu());
                }
            });
            reclamationComboBox.setButtonCell(new javafx.scene.control.ListCell<Reclamation>() {
                @Override
                protected void updateItem(Reclamation item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.getId_reclamation() + " - " + item.getContenu());
                }
            });
        } catch (SQLException e) {
            messageLabel.setText("Erreur lors du chargement des réclamations : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void ajouterReponse() {
        Reclamation selectedReclamation = reclamationComboBox.getValue();
        String reponseText = reponseField.getText().trim();

        if (selectedReclamation == null || reponseText.isEmpty()) {
            messageLabel.setText("Tous les champs sont obligatoires !");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        ReponseReclamation reponse = new ReponseReclamation(selectedReclamation.getId_reclamation(), reponseText);
        try {
            serviceReponse.ajouter(reponse);
            messageLabel.setText("Réponse ajoutée avec succès !");
            messageLabel.setStyle("-fx-text-fill: green;");
            clearFieldsAndClose();
        } catch (SQLException e) {
            messageLabel.setText("Erreur lors de l'ajout : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
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