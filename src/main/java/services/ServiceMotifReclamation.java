package services;

import entities.MotifReclamation;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMotifReclamation implements Crud<MotifReclamation> {

    Connection connection;

    public ServiceMotifReclamation() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(MotifReclamation motif) throws SQLException {
        String req = "INSERT INTO motif_reclamation (nom, description) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, motif.getNom());
        ps.setString(2, motif.getDescription());
        ps.executeUpdate();
    }

    @Override
    public void modifier(MotifReclamation motif) throws SQLException {
        String req = "UPDATE motif_reclamation SET nom = ?, description = ? WHERE id_motif = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, motif.getNom());
        ps.setString(2, motif.getDescription());
        ps.setInt(3, motif.getId());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM motif_reclamation WHERE id_motif = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public List<MotifReclamation> afficher() throws SQLException {
        List<MotifReclamation> list = new ArrayList<>();
        String req = "SELECT * FROM motif_reclamation";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            MotifReclamation m = new MotifReclamation(
                    rs.getInt("id_motif"), // attention ici aussi
                    rs.getString("nom"),
                    rs.getString("description")
            );
            list.add(m);
        }

        return list;
    }
}
