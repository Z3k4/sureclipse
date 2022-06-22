package application.modele;

public class ObjetJeu extends Entite {
    private String nom;
    private int place;
    private int stack;
    private static int slotInventaire = 0;

    public ObjetJeu(Environnement env, String nom, int stack) {
        super(env);
        this.nom = nom;
        this.place = slotInventaire++;
        this.stack = stack;
    }

    public void setPlaceInventaire(int nouvellePlace) {
        this.place = nouvellePlace;
    }

    public int getPlaceInventaire() {
        return this.place;
    }

    public String toString() {
        return this.nom;
    }
}
