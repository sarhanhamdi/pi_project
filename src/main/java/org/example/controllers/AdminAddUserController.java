package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.entities.User;
import org.example.services.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

public class AdminAddUserController implements Initializable {

    @FXML
    private Button ajouterUserBtn;

    @FXML
    private DatePicker dateNaissanceUserTF;

    @FXML
    private TextField emailUserTF;

    @FXML
    private TextField mdpUserTF;

    @FXML
    private TextField nomUserTF;

    @FXML
    private TextField prenomUserTF;

    @FXML
    private ComboBox<String> roleUserCB;

    @FXML
    private ComboBox<String> statutUserCB;

    @FXML
    private TextField telUserTF;

    @FXML
    private ComboBox<String> villeUserTF;

    public void initialize(URL url, ResourceBundle rb) {

        // Vérifie si le ComboBox est bien initialisé
        if (villeUserTF != null) {
            // Remplir le ComboBox avec une liste de villes
            villeUserTF.setItems(FXCollections.observableArrayList("Tunis", "Sousse", "Sfax", "Gabes"));
            // Sélectionner une valeur par défaut
            villeUserTF.setValue("Tunis");
        } else {
            System.out.println("Le ComboBox n'est pas encore initialisé !");
        }

    }

    @FXML
    void ajouterUser(ActionEvent event) {
        String nom = nomUserTF.getText();
        String prenom = prenomUserTF.getText();
        String email = emailUserTF.getText();
        String mdp = mdpUserTF.getText();
        String tel = telUserTF.getText();
        String ville = villeUserTF.getValue();
        String role = roleUserCB.getValue();
        String statutStr = statutUserCB.getValue();
        LocalDate dateNaissance = dateNaissanceUserTF.getValue();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty()
                || tel.isEmpty() || ville == null || role == null || statutStr == null || dateNaissance == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs.", Alert.AlertType.ERROR);
            return;
        }
        if (dateNaissance == null) {
            showAlert("Erreur", "Veuillez sélectionner une date de naissance.", Alert.AlertType.ERROR);
            return;
        }


        if (dateNaissance.isAfter(LocalDate.now())) {
            showAlert("Erreur", "La date de naissance ne peut pas être dans le futur.", Alert.AlertType.ERROR);
            return;
        }


        if (Period.between(dateNaissance, LocalDate.now()).getYears() < 18) {
            showAlert("Erreur", "Vous devez avoir au moins 18 ans pour vous inscrire.", Alert.AlertType.ERROR);
            return;
        }

        int statut = statutStr.equals("Actif") ? 1 : 0;

        User user = new User(nom, prenom, email, mdp, tel, ville, dateNaissance, role, statut);

        ServiceUser serviceUser = new ServiceUser();
        try {
            try {
                if (serviceUser.findByEmail(email) != null) {
                    showAlert("Erreur", "Cet email est déjà utilisé.", Alert.AlertType.ERROR);
                    return;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            serviceUser.ajouter(user);
            showAlert("Succès", "Utilisateur ajouté avec succès.", Alert.AlertType.INFORMATION);
            clearForm();
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de l'ajout : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        nomUserTF.clear();
        prenomUserTF.clear();
        emailUserTF.clear();
        mdpUserTF.clear();
        telUserTF.clear();
        villeUserTF.setValue(null);
        roleUserCB.setValue(null);
        statutUserCB.setValue(null);
        dateNaissanceUserTF.setValue(null);
    }
    @FXML
    private void RetourButton(ActionEvent event) {
        try {
            // Load the login interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminUsers.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create new scene and set it
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }


