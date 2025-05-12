package org.example.controllers;

import jakarta.mail.MessagingException;
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
import org.example.services.MailService;
import org.example.services.ServiceUser;
import org.example.utils.UserSession;
import org.example.utils.VerificationSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

public class InscriptionUserController implements Initializable {

    @FXML
    private DatePicker InscriptionDatenUserTF;

    @FXML
    private TextField InscriptionEmailUserTF;

    @FXML
    private TextField InscriptionMdpUserTF;

    @FXML
    private TextField InscriptionMdpconfirmUserTF;

    @FXML
    private TextField InscriptionNomUserTF;

    @FXML
    private TextField InscriptionPrenomUserTF;

    @FXML
    private TextField InscriptionTelUserTF;

    @FXML
    private ComboBox<String> InscriptionVilleUserTF;

    @FXML
    private Button button1;

    public void initialize(URL url, ResourceBundle rb) {

        // Vérifie si le ComboBox est bien initialisé
        if (InscriptionVilleUserTF != null) {
            // Remplir le ComboBox avec une liste de villes
            InscriptionVilleUserTF.setItems(FXCollections.observableArrayList("Tunis", "Sousse", "Sfax", "Gabes"));
            // Sélectionner une valeur par défaut
            InscriptionVilleUserTF.setValue("Tunis");
        } else {
            System.out.println("Le ComboBox n'est pas encore initialisé !");
        }

    }

    @FXML
    void InscriptionUserButton(ActionEvent event) {
        ServiceUser u = new ServiceUser();

        String mdp = InscriptionMdpUserTF.getText();
        String mdpConfirm = InscriptionMdpconfirmUserTF.getText();

        if (InscriptionNomUserTF.getText().isEmpty() || InscriptionPrenomUserTF.getText().isEmpty() ||
                InscriptionEmailUserTF.getText().isEmpty() || mdp.isEmpty() || mdpConfirm.isEmpty() ||
                InscriptionTelUserTF.getText().isEmpty()) {

            showAlert("Erreur", "Tous les champs doivent être remplis.", Alert.AlertType.ERROR);
            return;
        }

        if (!mdp.equals(mdpConfirm)) {
            showAlert("Erreur", "Les mots de passe ne correspondent pas.", Alert.AlertType.ERROR);
            return;
        }

        try {
            if (u.findByEmail(InscriptionEmailUserTF.getText()) != null) {
                showAlert("Erreur", "Cet email est déjà utilisé.", Alert.AlertType.ERROR);
                return;
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la vérification de l'email.", Alert.AlertType.ERROR);
            return;
        }

        LocalDate daten = InscriptionDatenUserTF.getValue();
        if (daten == null) {
            showAlert("Erreur", "Veuillez sélectionner une date de naissance.", Alert.AlertType.ERROR);
            return;
        }


        if (daten.isAfter(LocalDate.now())) {
            showAlert("Erreur", "La date de naissance ne peut pas être dans le futur.", Alert.AlertType.ERROR);
            return;
        }


        if (Period.between(daten, LocalDate.now()).getYears() < 18) {
            showAlert("Erreur", "Vous devez avoir au moins 18 ans pour vous inscrire.", Alert.AlertType.ERROR);
            return;
        }

        User user = new User(
                InscriptionNomUserTF.getText(),
                InscriptionPrenomUserTF.getText(),
                InscriptionEmailUserTF.getText(),
                mdp,
                InscriptionTelUserTF.getText(),
                InscriptionVilleUserTF.getValue(),
                daten
        );

        // Générer un code aléatoire
        String code = String.valueOf((int)(Math.random() * 900000) + 100000);

        try {
            System.out.println("Email saisi : " + user.getEmail());
            MailService.sendVerificationEmail(user.getEmail(), code);
            VerificationSession.setCode(code);
            VerificationSession.setEmail(user.getEmail());
            UserSession.setUser(user); // Stocker temporairement l'objet utilisateur pour ajout futur

            // Rediriger vers la scène de vérification de code
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CodeVerification.fxml"));
            Parent parent = loader.load();
            InscriptionNomUserTF.getScene().setRoot(parent);

        } catch (MessagingException | IOException e) {
            showAlert("Erreur", "Échec de l'envoi de l'email de vérification.", Alert.AlertType.ERROR);
        }
    }
    @FXML
    private void RetourButton(ActionEvent event) {
        try {
            // Load the login interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserAccueil.fxml"));
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
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

}
