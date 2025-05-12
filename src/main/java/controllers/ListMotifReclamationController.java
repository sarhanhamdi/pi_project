package Controllers;

import entities.MotifReclamation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ServiceMotifReclamation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListMotifReclamationController {

    @FXML
    private TableView<MotifReclamation> motifTable;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label messageLabel;

    private ServiceMotifReclamation serviceMotif;

    @FXML
    public void initialize() {
        serviceMotif = new ServiceMotifReclamation();
        loadMotifs();

        // Enable/Disable modify and delete buttons based on selection
        motifTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean disable = newSelection == null;
            modifyButton.setDisable(disable);
            deleteButton.setDisable(disable);
        });

        // Hide the ID column
        motifTable.getColumns().get(0).setVisible(false);
    }

    private void loadMotifs() {
        try {
            List<MotifReclamation> motifs = serviceMotif.afficher();
            motifTable.getItems().setAll(motifs);
        } catch (SQLException e) {
            messageLabel.setText("Erreur lors du chargement des motifs : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
        }
    }

    @FXML
    private void addMotif() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterMotifReclamation.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ajouter un Motif de Réclamation");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the table after adding
            loadMotifs();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture du formulaire : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            e.printStackTrace();
        }
    }

    @FXML
    private void modifyMotif() {
        MotifReclamation selectedMotif = motifTable.getSelectionModel().getSelectedItem();
        if (selectedMotif == null) {
            messageLabel.setText("Veuillez sélectionner un motif !");
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierMotifReclamation.fxml"));
            Parent root = loader.load();
            ModifierMotifReclamationController controller = loader.getController();
            controller.setMotifReclamation(selectedMotif);
            Stage stage = new Stage();
            stage.setTitle("Modifier un Motif de Réclamation");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the table after modifying
            loadMotifs();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture du formulaire : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteMotif() {
        MotifReclamation selectedMotif = motifTable.getSelectionModel().getSelectedItem();
        if (selectedMotif == null) {
            messageLabel.setText("Veuillez sélectionner un motif !");
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce motif ?");
        alert.setContentText("Cette action est irréversible.");
        if (alert.showAndWait().get().getButtonData().isCancelButton()) {
            return;
        }

        try {
            serviceMotif.supprimer(selectedMotif.getId());
            messageLabel.setText("Motif supprimé avec succès !");
            messageLabel.setStyle("-fx-text-fill: #4fc3f7;");
            loadMotifs();
        } catch (SQLException e) {
            messageLabel.setText("Erreur lors de la suppression : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
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
            Stage currentStage = (Stage) cancelButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors du retour à l'accueil : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            e.printStackTrace();
        }
    }
}
