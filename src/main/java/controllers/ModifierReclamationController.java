package Controllers;

import entities.MotifReclamation;
import entities.Reclamation;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceMotifReclamation;
import services.ServiceReclamation;

import java.sql.SQLException;
import java.util.List;

public class ModifierReclamationController {

    @FXML
    private TextArea contenuField;

    @FXML
    private TextField statutField;

    @FXML
    private ComboBox<MotifReclamation> motifComboBox;

    @FXML
    private Label messageLabel;

    private Reclamation reclamationToModify;
    private ServiceReclamation serviceReclamation;
    private ServiceMotifReclamation serviceMotif;

    public void setReclamation(Reclamation reclamation) {
        this.reclamationToModify = reclamation;
        this.serviceReclamation = new ServiceReclamation();
        this.serviceMotif = new ServiceMotifReclamation();
        loadMotifs();
        loadReclamationData();
    }

    private void loadMotifs() {
        try {
            List<MotifReclamation> motifs = serviceMotif.afficher();
            motifComboBox.getItems().setAll(motifs);
            motifComboBox.setCellFactory(lv -> new javafx.scene.control.ListCell<MotifReclamation>() {
                @Override
                protected void updateItem(MotifReclamation item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.getNom());
                }
            });
            motifComboBox.setButtonCell(new javafx.scene.control.ListCell<MotifReclamation>() {
                @Override
                protected void updateItem(MotifReclamation item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item.getNom());
                }
            });
        } catch (SQLException e) {
            messageLabel.setText("Erreur lors du chargement des motifs : " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void loadReclamationData() {
        if (reclamationToModify != null) {
            contenuField.setText(reclamationToModify.getContenu());
            statutField.setText(reclamationToModify.getStatut());
            try {
                List<MotifReclamation> motifs = serviceMotif.afficher();
                MotifReclamation selectedMotif = motifs.stream()
                        .filter(m -> m.getId() == reclamationToModify.getId_motif())
                        .findFirst()
                        .orElse(null);
                motifComboBox.setValue(selectedMotif);
            } catch (SQLException e) {
                messageLabel.setText("Erreur lors du chargement du motif : " + e.getMessage());
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        }
    }

    @FXML
    private void saveChanges() {
        if (reclamationToModify == null) {
            messageLabel.setText("Aucune réclamation sélectionnée !");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String contenu = contenuField.getText().trim();
        String statut = statutField.getText().trim();
        MotifReclamation selectedMotif = motifComboBox.getValue();

        if (contenu.isEmpty() || statut.isEmpty() || selectedMotif == null) {
            messageLabel.setText("Tous les champs sont obligatoires !");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        reclamationToModify.setContenu(contenu);
        reclamationToModify.setStatut(statut);
        reclamationToModify.setId_motif(selectedMotif.getId());

        try {
            serviceReclamation.modifier(reclamationToModify);
            messageLabel.setText("Réclamation modifiée avec succès !");
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
        contenuField.clear();
        statutField.clear();
        motifComboBox.getSelectionModel().clearSelection();
        Stage stage = (Stage) contenuField.getScene().getWindow();
        stage.close();
    }
}