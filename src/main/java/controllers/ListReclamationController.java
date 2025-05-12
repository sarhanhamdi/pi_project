package Controllers;

import entities.MotifReclamation;
import entities.Reclamation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ServiceMotifReclamation;
import services.ServiceReclamation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListReclamationController {

    @FXML
    private TableView<Reclamation> reclamationTable;

    @FXML
    private TableColumn<Reclamation, String> motifColumn;

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

    private ServiceReclamation serviceReclamation;
    private ServiceMotifReclamation serviceMotif;
    private List<MotifReclamation> motifs;

    @FXML
    public void initialize() {
        serviceReclamation = new ServiceReclamation();
        serviceMotif = new ServiceMotifReclamation();

        // Load motifs for motifColumn
        try {
            motifs = serviceMotif.afficher();
        } catch (SQLException e) {
            messageLabel.setText("Erreur lors du chargement des motifs : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #e74c3c;");
            e.printStackTrace();
        }

        // Set up Motif column to display MotifReclamation's nom
        motifColumn.setCellValueFactory(cellData -> {
            Reclamation reclamation = cellData.getValue();
            MotifReclamation motif = motifs.stream()
                    .filter(m -> m.getId() == reclamation.getId_motif())
                    .findFirst()
                    .orElse(null);
            return new javafx.beans.property.SimpleStringProperty(motif != null ? motif.getNom() : "Inconnu");
        });

        // Enable/Disable modify and delete buttons based on selection
        reclamationTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean disable = newSelection == null;
            modifyButton.setDisable(disable);
            deleteButton.setDisable(disable);
        });

        // Optionally hide the ID column
        // reclamationTable.getColumns().get(0).setVisible(false);

        loadReclamations();
    }

    private void loadReclamations() {
        try {
            List<Reclamation> reclamations = serviceReclamation.afficher();
            reclamationTable.getItems().setAll(reclamations);
        } catch (SQLException e) {
            messageLabel.setText("Erreur lors du chargement des réclamations : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #e74c3c;");
            e.printStackTrace();
        }
    }

    @FXML
    private void addReclamation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterReclamation.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ajouter une Réclamation");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the table after adding
            loadReclamations();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture du formulaire : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #e74c3c;");
            e.printStackTrace();
        }
    }

    @FXML
    private void modifyReclamation() {
        Reclamation selectedReclamation = reclamationTable.getSelectionModel().getSelectedItem();
        if (selectedReclamation == null) {
            messageLabel.setText("Veuillez sélectionner une réclamation !");
            messageLabel.setStyle("-fx-text-fill: #e74c3c;");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModifierReclamation.fxml"));
            Parent root = loader.load();
            ModifierReclamationController controller = loader.getController();
            controller.setReclamation(selectedReclamation);
            Stage stage = new Stage();
            stage.setTitle("Modifier une Réclamation");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the table after modifying
            loadReclamations();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture du formulaire : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #e74c3c;");
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteReclamation() {
        Reclamation selectedReclamation = reclamationTable.getSelectionModel().getSelectedItem();
        if (selectedReclamation == null) {
            messageLabel.setText("Veuillez sélectionner une réclamation !");
            messageLabel.setStyle("-fx-text-fill: #e74c3c;");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer cette réclamation ?");
        alert.setContentText("Cette action est irréversible.");
        if (alert.showAndWait().get().getButtonData().isCancelButton()) {
            return;
        }

        try {
            serviceReclamation.supprimer(selectedReclamation.getId_reclamation());
            messageLabel.setText("Réclamation supprimée avec succès !");
            messageLabel.setStyle("-fx-text-fill: #28a745;");
            loadReclamations();
        } catch (SQLException e) {
            messageLabel.setText("Erreur lors de la suppression : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #e74c3c;");
        }
    }

    @FXML
    private void cancel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListReponse.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Liste des Réponses");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window
            Stage currentStage = (Stage) reclamationTable.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors du retour à la liste des réponses : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #e74c3c;");
            e.printStackTrace();
        }
    }
}
