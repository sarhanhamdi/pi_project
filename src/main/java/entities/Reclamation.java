package entities;

public class Reclamation {
    private int id_reclamation;
    private int id_motif;
    private String contenu;
    private String statut;

    public Reclamation() {
    }

    public Reclamation(int id_motif, String contenu, String statut) {
        this.id_motif = id_motif;
        this.contenu = contenu;
        this.statut = statut;
    }

    public Reclamation(int id_reclamation, int id_motif, String contenu, String statut) {
        this.id_reclamation = id_reclamation;
        this.id_motif = id_motif;
        this.contenu = contenu;
        this.statut = statut;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public int getId_motif() {
        return id_motif;
    }

    public void setId_motif(int id_motif) {
        this.id_motif = id_motif;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id_reclamation=" + id_reclamation +
                ", id_motif=" + id_motif +
                ", contenu='" + contenu + '\'' +
                ", statut='" + statut + '\'' +
                '}';
    }
}
