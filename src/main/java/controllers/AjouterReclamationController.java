package Controllers;

import entities.MotifReclamation;
import entities.Reclamation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceMotifReclamation;
import services.ServiceReclamation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterReclamationController {

    @FXML
    private TextArea contenuField;

    @FXML
    private TextField statusField;

    @FXML
    private ComboBox<String> motifComboBox;

    @FXML
    private Label messageLabel;

    @FXML
    private Button submitButton;

    @FXML
    private Button cancelButton;

    private ServiceReclamation serviceReclamation;
    private ServiceMotifReclamation serviceMotif;
    private List<MotifReclamation> motifs;

    @FXML
    public void initialize() {
        serviceReclamation = new ServiceReclamation();
        serviceMotif = new ServiceMotifReclamation();
        loadMotifs();
    }

    private void loadMotifs() {
        try {
            motifs = serviceMotif.afficher();
            for (MotifReclamation motif : motifs) {
                motifComboBox.getItems().add("Motif #" + motif.getId() + ": " + motif.getNom());
            }
        } catch (SQLException e) {
            messageLabel.setText("Erreur lors du chargement des motifs : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
        }
    }

    @FXML
    private void ajouterReclamation() {
        String contenu = contenuField.getText().trim();
        String status = statusField.getText().trim();
        String selectedMotif = motifComboBox.getValue();

        // Validate inputs
        if (contenu.isEmpty() || status.isEmpty() || selectedMotif == null) {
            messageLabel.setText("Tous les champs sont obligatoires !");
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            return;
        }

        // Validate status
        if (!status.equals("en_cours") && !status.equals("traité")) {
            messageLabel.setText("Le statut doit être 'en_cours' ou 'traité' !");
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            return;
        }

        // Parse motifId
        int motifId;
        try {
            String idStr = selectedMotif.split(":")[0].replace("Motif #", "").trim();
            motifId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            messageLabel.setText("Motif invalide !");
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            return;
        }

        // Create Reclamation
        Reclamation reclamation = new Reclamation(motifId, contenu, status);
        try {
            serviceReclamation.ajouter(reclamation);
            messageLabel.setText("Réclamation ajoutée avec succès !");
            messageLabel.setStyle("-fx-text-fill: #4fc3f7;");
            clearFields();
        } catch (SQLException e) {
            messageLabel.setText("Erreur lors de l'ajout : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
        }
    }

    @FXML
    private void goToList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListReclamation.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Liste des Réclamations");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window
            Stage currentStage = (Stage) contenuField.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture de la liste : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            e.printStackTrace();
        }
    }

    @FXML
    private void cancel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Accueil.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Accueil - Covoiturage Éco");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window
            Stage currentStage = (Stage) contenuField.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors du retour à l'accueil : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            e.printStackTrace();
        }
    }

    private void clearFields() {
        contenuField.clear();
        statusField.clear();
        motifComboBox.getSelectionModel().clearSelection();
        messageLabel.setText("");
    }
}
