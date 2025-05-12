package entities;

public class MotifReclamation {
    private int id;
    private String nom;
    private String description;

    public MotifReclamation() {}

    public MotifReclamation(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    public MotifReclamation(String nom, String description) {
        this.nom = nom;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MotifReclamation{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
