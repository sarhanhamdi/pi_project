package services;

import entities.Reclamation;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReclamation implements Crud<Reclamation> {
    Connection connection;

    public ServiceReclamation() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Reclamation r) throws SQLException {
        String req = "INSERT INTO reclamation (id_motif, contenu, statut) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, r.getId_motif());
        ps.setString(2, r.getContenu());
        ps.setString(3, r.getStatut());
        ps.executeUpdate();
        System.out.println("Réclamation ajoutée !");
    }

    @Override
    public void modifier(Reclamation r) throws SQLException {
        String req = "UPDATE reclamation SET id_motif=?, contenu=?, statut=? WHERE id_reclamation=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, r.getId_motif());
        ps.setString(2, r.getContenu());
        ps.setString(3, r.getStatut());
        ps.setInt(4, r.getId_reclamation());
        ps.executeUpdate();
        System.out.println("Réclamation modifiée !");
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM reclamation WHERE id_reclamation = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Réclamation supprimée !");
    }

    @Override
    public List<Reclamation> afficher() throws SQLException {
        List<Reclamation> list = new ArrayList<>();
        String req = "SELECT * FROM reclamation";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Reclamation r = new Reclamation(
                    rs.getInt("id_reclamation"),
                    rs.getInt("id_motif"),
                    rs.getString("contenu"),
                    rs.getString("statut")
            );
            list.add(r);
        }

        return list;
    }
}
