package services;

import entities.ReponseReclamation;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReponseReclamation implements Crud<ReponseReclamation> {

    private Connection connection;

    public ServiceReponseReclamation() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(ReponseReclamation reponse) throws SQLException {
        String req = "INSERT INTO reponse_reclamation (id_reclamation, texte) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, reponse.getIdReclamation());
        ps.setString(2, reponse.getTexte());
        ps.executeUpdate();
        System.out.println("Réponse ajoutée avec succès !");
    }

    @Override
    public void modifier(ReponseReclamation reponse) throws SQLException {
        String req = "UPDATE reponse_reclamation SET id_reclamation = ?, texte = ? WHERE id_reponse_reclamation = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, reponse.getIdReclamation());
        ps.setString(2, reponse.getTexte());
        ps.setInt(3, reponse.getIdReponseReclamation());
        ps.executeUpdate();
        System.out.println("Réponse modifiée avec succès !");
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM reponse_reclamation WHERE id_reponse_reclamation = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Réponse supprimée avec succès !");
    }

    @Override
    public List<ReponseReclamation> afficher() throws SQLException {
        List<ReponseReclamation> list = new ArrayList<>();
        String req = "SELECT * FROM reponse_reclamation";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            ReponseReclamation r = new ReponseReclamation(
                    rs.getInt("id_reponse_reclamation"),
                    rs.getInt("id_reclamation"),
                    rs.getString("texte")
            );
            list.add(r);
        }

        return list;
    }
}
