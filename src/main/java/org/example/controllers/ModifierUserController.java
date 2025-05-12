package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.example.entities.User;
import org.example.services.ServiceUser;
import org.example.utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifierUserController implements Initializable {

    @FXML
    private TextField ModifierMdpUserTF;

    @FXML
    private TextField ModifierMdpconfirmUserTF;
    @FXML
    private Button ModifierButton;

    @FXML
    private DatePicker ModifierDatenUserTF;

    @FXML
    private TextField ModifierEmailUserTF;

   /* @FXML
    private PasswordField ModifierMdpUserTF;

    @FXML
    private PasswordField ModifierMdpconfirmUserTF;*/

    @FXML
    private TextField ModifierNomUserTF;

    @FXML
    private TextField ModifierPrenomUserTF;

    @FXML
    private TextField ModifierTelUserTF;

    @FXML
    private ComboBox<String> ModifierVilleUserTF;

    private final ServiceUser serviceUser = new ServiceUser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Remplir la comboBox
        ModifierVilleUserTF.setItems(FXCollections.observableArrayList("Tunis", "Sousse", "Sfax", "Gabes"));

        // Pré-remplir les champs avec les données de l'utilisateur actuel
        User currentUser = UserSession.getUser();
        if (currentUser != null) {
            ModifierNomUserTF.setText(currentUser.getNom());
            ModifierPrenomUserTF.setText(currentUser.getPrenom());
            ModifierEmailUserTF.setText(currentUser.getEmail());
            ModifierTelUserTF.setText(currentUser.getTelephone());
            ModifierVilleUserTF.setValue(currentUser.getVille());
            ModifierDatenUserTF.setValue(currentUser.getDaten());
        }
    }

    @FXML
    void ModifierUserButton(ActionEvent event) {
        String mdp = ModifierMdpUserTF.getText();
        String confirmMdp = ModifierMdpconfirmUserTF.getText();

        if (mdp.isEmpty() || confirmMdp.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir les champs de mot de passe et de confirmation.");
            alert.showAndWait();
            return;
        }

        // Vérification que les deux mots de passe sont identiques
        if (!mdp.equals(confirmMdp)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de mot de passe");
            alert.setHeaderText(null);
            alert.setContentText("Les mots de passe ne correspondent pas.");
            alert.showAndWait();
            return;
        }

        User currentUser = UserSession.getUser();
        if (currentUser == null) {
            showAlert("Erreur", "Aucun utilisateur connecté.");
            return;
        }

        // Mettre à jour les champs
        currentUser.setNom(ModifierNomUserTF.getText());
        currentUser.setPrenom(ModifierPrenomUserTF.getText());
        currentUser.setEmail(ModifierEmailUserTF.getText());
        currentUser.setTelephone(ModifierTelUserTF.getText());
        currentUser.setVille(ModifierVilleUserTF.getValue());
        currentUser.setDaten(ModifierDatenUserTF.getValue());
        currentUser.setPassword(mdp);


        try {
            serviceUser.modifier(currentUser);
            showAlert("Succès", "Profil mis à jour avec succès !");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfileUser.fxml"));
            try {
                Parent parent = loader.load();
                ModifierNomUserTF.getScene().setRoot(parent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            showAlert("Erreur", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
