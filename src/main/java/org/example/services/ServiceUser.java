package org.example.services;

import org.example.entities.User;
import org.example.utils.MyDataBase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements IService<User> {
    Connection connection;

    public ServiceUser() {
        connection = MyDataBase.getInstance().getConnection();

    }

    @Override
    public void ajouter(User user) throws SQLException {
        String req = "INSERT INTO users (nom, prenom, email, password, telephone, ville, daten) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            // Remplir les paramètres de la requête
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getTelephone());
            preparedStatement.setString(6, user.getVille());
            preparedStatement.setDate(7, java.sql.Date.valueOf(user.getDaten()));

            // Exécution de la requête
            preparedStatement.executeUpdate();
            System.out.println("Personne ajoutée");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors de l'ajout de l'utilisateur", e);
        }


    }

    @Override
    public void modifier(User user) throws SQLException {
        String req = "UPDATE users SET nom = ?, prenom = ?, email = ?, password = ?, telephone = ?, ville = ?, daten = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getTelephone());
            preparedStatement.setString(6, user.getVille());
            preparedStatement.setDate(7, java.sql.Date.valueOf(user.getDaten()));
            preparedStatement.setInt(8, user.getId());

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("Utilisateur modifié avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID : " + user.getId());
            }
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, id);

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("Utilisateur supprimé avec succès.");
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'ID : " + id);
            }
        }
    }

    @Override
    public List<User> afficher() throws SQLException {

        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(req);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String telephone = resultSet.getString("telephone");
                String ville = resultSet.getString("ville");
                LocalDate daten = resultSet.getDate("daten").toLocalDate();
                String role = resultSet.getString("role");
                int statut = resultSet.getInt("statut");

                User user = new User(id, nom, prenom, email, password, telephone, ville, daten, role, statut);
                users.add(user);
            }
        }

        return users;
    }

    @Override
    public User findByEmail(String email) throws SQLException {
        String req = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("telephone"),
                        rs.getString("ville"),
                        rs.getDate("daten").toLocalDate(),
                        rs.getString("role"),
                        rs.getInt("statut")
                );
            }
        }
        return null;
    }

    public void updateUserStatut(int userId, int statut) {
        String sql = "UPDATE users SET statut = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Remplir les paramètres de la requête
            preparedStatement.setInt(1, statut);
            preparedStatement.setInt(2, userId);


            // Exécution de la requête
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
