package org.example.tests;

import org.example.entities.User;
import org.example.services.ServiceUser;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {}

        /*ServiceUser serviceUser= new ServiceUser();
        User user = new User("hamdi","Sarhan","sarhan@gmail.com","123456","53208004","Gabes", new Date(1996,9,21));
        User user1 = new User("feln","test","test@gmail.com","test","532","Tunis", new Date(1996,9,23));
        User user2 = new User("feln","test","test@gmail.com","test","532","Tunis", new Date(1996,9,23));
        String dateStr = "1998-05-01"; // Par exemple
        Date daten = null;
        try {
            daten = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // Création d’un objet User avec les nouvelles infos
        User updatedUser = new User(
                3,                        // ID de l'utilisateur existant
                "NouveauNom",             // Nouveau nom
                "NouveauPrenom",          // Nouveau prénom
                "nouveau@mail.com",       // Nouvel email
                "newpassword123",         // Nouveau mot de passe
                "99999999",               // Nouveau téléphone
                "NouvelleVille",          // Nouvelle ville
                daten                     // Nouvelle date de naissance
        );
        /*try {
            // servicePersonne.ajouter(p1);
            //serviceUser.ajouter(user);
            //serviceUser.ajouter(user1);
            //serviceUser.ajouter(user2);
            //serviceUser.modifier(updatedUser);
            List<User> utilisateurs = serviceUser.afficher();
            serviceUser.supprimer(3);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

    }
