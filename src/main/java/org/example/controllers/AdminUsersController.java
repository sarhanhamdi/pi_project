package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.entities.User;
import org.example.services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminUsersController {
    @FXML
    private AnchorPane adminUsersPane;

    @FXML
    private VBox usersContainer;

    @FXML
    private Button retourButton;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, Integer> idColumn;

    @FXML
    private TableColumn<User, String> nomColumn;

    @FXML
    private TableColumn<User, String> prenomColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> telColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private TableColumn<User, Void> actionsColumn;

    @FXML
    private Button ajouterButton;

    @FXML
    private Button actualiserButton;

    @FXML
    private void initialize() {

        addBlockButtonToTable();
        ServiceUser serviceUser = new ServiceUser();

        // Lier les colonnes avec les propriétés de la classe User
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        try {
            List<User> users = serviceUser.afficher();
            usersTable.setItems(FXCollections.observableArrayList(users));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchUser(null); // Call your search method
        });

    }
    private void addBlockButtonToTable() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button actionButton = new Button();

            {
                actionButton.setOnAction(event -> {
                    User user = getTableView().getItems().get(getIndex());
                    ServiceUser serviceUser = new ServiceUser();

                    if (user.getStatut() == 1) {
                        serviceUser.updateUserStatut(user.getId(), 0); // Bloquer
                        user.setStatut(0);
                    } else {
                        serviceUser.updateUserStatut(user.getId(), 1); // Débloquer
                        user.setStatut(1);
                    }
                    getTableView().refresh(); // Rafraîchir pour mettre à jour le texte du bouton
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    User user = getTableView().getItems().get(getIndex());
                    actionButton.setText(user.getStatut() == 1 ? "Bloquer" : "Débloquer");
                    actionButton.setStyle(user.getStatut() == 1
                            ? "-fx-background-color: #e74c3c; -fx-text-fill: white;"
                            : "-fx-background-color: #2ecc71; -fx-text-fill: white;");
                    setGraphic(actionButton);
                }
            }
        });
    }


    @FXML
    private void retourAccueil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminAccueil.fxml"));
            Parent parent = loader.load();
            adminUsersPane.getScene().setRoot(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ajouterUtilisateur() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminAddUser.fxml"));
            Parent parent = loader.load();
            adminUsersPane.getScene().setRoot(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void actualiserTable() {
        // Logique pour actualiser le tableau
    }

    @FXML
    void searchUser(ActionEvent event) {
        String searchText = searchField.getText().trim().toLowerCase();
        ServiceUser serviceUser = new ServiceUser();

        try {
            List<User> allUsers = serviceUser.afficher();

            if (searchText.isEmpty()) {
                usersTable.setItems(FXCollections.observableArrayList(allUsers));
            } else {
                List<User> filteredUsers = allUsers.stream()
                        .filter(user -> user.getEmail().toLowerCase().contains(searchText))
                        .toList();

                usersTable.setItems(FXCollections.observableArrayList(filteredUsers));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
