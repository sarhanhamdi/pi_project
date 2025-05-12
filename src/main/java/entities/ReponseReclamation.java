package entities;

public class ReponseReclamation {
    private int idReponseReclamation;
    private int idReclamation;
    private String texte;

    public ReponseReclamation() {}

    public ReponseReclamation(int idReclamation, String texte) {
        this.idReclamation = idReclamation;
        this.texte = texte;
    }

    public ReponseReclamation(int idReponseReclamation, int idReclamation, String texte) {
        this.idReponseReclamation = idReponseReclamation;
        this.idReclamation = idReclamation;
        this.texte = texte;
    }

    public int getIdReponseReclamation() {
        return idReponseReclamation;
    }

    public void setIdReponseReclamation(int idReponseReclamation) {
        this.idReponseReclamation = idReponseReclamation;
    }

    public int getIdReclamation() {
        return idReclamation;
    }

    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    @Override
    public String toString() {
        return "ReponseReclamation{" +
                "idReponseReclamation=" + idReponseReclamation +
                ", idReclamation=" + idReclamation +
                ", texte='" + texte + '\'' +
                '}';
    }
}
