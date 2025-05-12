package org.example.entities;

import java.time.LocalDate;
import java.util.Date;

public class User {

    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String telephone;
    private String ville;
    private LocalDate daten;
    private String role;
    private int statut;


    public User(int id, String nom, String prenom, String email, String password, String telephone, String ville, LocalDate daten,String role, int statut) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.ville = ville;
        this.daten = daten;
        this.role = role;
        this.statut = statut;
    }
    public User(String nom, String prenom, String email, String password, String telephone, String ville, LocalDate daten) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.ville = ville;
        this.daten = daten;
    }
    public User(String nom, String prenom, String email, String password, String telephone, String ville, LocalDate daten, String role, int statut) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.ville = ville;
        this.daten = daten;
        this.role = role;
        this.statut = statut;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public LocalDate getDaten() {
        return daten;
    }

    public void setDaten(LocalDate daten) {
        this.daten = daten;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                ", ville='" + ville + '\'' +
                ", daten=" + daten +
                ", role='" + role + '\'' +
                ", statut=" + statut +
                '}';
    }
}
