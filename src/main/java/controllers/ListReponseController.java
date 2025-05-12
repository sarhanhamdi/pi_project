package controllers;

import entities.ReponseReclamation;
import services.ServiceReponseReclamation;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListReponseController {

    @FXML
    private TableView<ReponseReclamation> reponseTable;

    @FXML
    private Button addButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button listReclamationButton;

    @FXML
    private Label messageLabel;

    private ServiceReponseReclamation serviceReponse;

    public void initialize() {
        serviceReponse = new ServiceReponseReclamation();
        loadReponses();

        // Enable/Disable modify and delete buttons based on selection
        reponseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean disable = newSelection == null;
            modifyButton.setDisable(disable);
            deleteButton.setDisable(disable);
        });

        // Customize the reclamation column to show the Reclamation ID
        TableColumn<ReponseReclamation, Integer> reclamationColumn = (TableColumn<ReponseReclamation, Integer>) reponseTable.getColumns().get(1);
        reclamationColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getIdReclamation()).asObject());

        // Hide the ID column
        reponseTable.getColumns().get(0).setVisible(false);
    }

    private void loadReponses() {
        try {
            List<ReponseReclamation> reponses = serviceReponse.afficher();
            reponseTable.getItems().setAll(reponses);
        } catch (SQLException e) {
            messageLabel.setText("Erreur lors du chargement des réponses : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
        }
    }

    @FXML
    private void addReponse() {
        try {
            String path = "/AjouterReponse.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ajouter une Réponse");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the table after adding
            loadReponses();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture du formulaire : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            e.printStackTrace();
        }
    }

    @FXML
    private void modifyReponse() {
        ReponseReclamation selectedReponse = reponseTable.getSelectionModel().getSelectedItem();
        if (selectedReponse == null) {
            messageLabel.setText("Veuillez sélectionner une réponse !");
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            return;
        }

        try {
            String path = "/ModifierReponse.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            ModifierReponseController controller = loader.getController();
            controller.setReponse(selectedReponse);
            Stage stage = new Stage();
            stage.setTitle("Modifier une Réponse");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Refresh the table after modifying
            loadReponses();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture du formulaire : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteReponse() {
        ReponseReclamation selectedReponse = reponseTable.getSelectionModel().getSelectedItem();
        if (selectedReponse == null) {
            messageLabel.setText("Veuillez sélectionner une réponse !");
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer cette réponse ?");
        alert.setContentText("Cette action est irréversible.");
        if (alert.showAndWait().get().getButtonData().isCancelButton()) {
            return;
        }

        try {
            serviceReponse.supprimer(selectedReponse.getIdReponseReclamation());
            messageLabel.setText("Réponse supprimée avec succès !");
            messageLabel.setStyle("-fx-text-fill: #4fc3f7;");
            loadReponses();
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
            Stage currentStage = (Stage) reponseTable.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors du retour à l'accueil : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            e.printStackTrace();
        }
    }

    @FXML
    private void goToListReclamation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListReclamation.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Liste des Réclamations");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window
            Stage currentStage = (Stage) reponseTable.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture de la liste des réclamations : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #ffab91;");
            e.printStackTrace();
        }
    }
}
