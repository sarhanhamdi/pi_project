import entities.Reclamation;
import services.ServiceReclamation;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ServiceReclamation service = new ServiceReclamation();

        try {
            // ➕ Ajouter une réclamation
            Reclamation r1 = new Reclamation(2, "Point de rendez-vous mal indiqué", "en cours");
            service.ajouter(r1);

            // 📃 Afficher les réclamations
            System.out.println("Liste des réclamations :");
            List<Reclamation> liste = service.afficher();
            for (Reclamation r : liste) {
                System.out.println(r);
            }


        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
        }
    }
}