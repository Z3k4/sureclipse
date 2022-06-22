package application.modele.objets.materiaux;

import application.controleur.Constantes;
import application.modele.Entite;
import application.modele.Environnement;
import application.modele.armes.Pioche;


public abstract class Materiau extends Entite {

    private int idMateriau;

    public Materiau() {
        super(null, 0, 0, 100);
    }

    public Materiau(Environnement env,  int x, int y, int pv) {

        super(env, x * Constantes.TAILLE_TUILE, y * Constantes.TAILLE_TUILE, pv);

        this.idMateriau = x + y * env.getMapJeu().getWidth();
    }

    public int getId() {
        return this.idMateriau;
    }

    //appelé quand le bloc idMateriau cliqué décremente selon la qualité si le joueur a la bonne arme sinon de 1
    public void estFrappe() {
        if (getEnv().getJoueur().getArme() instanceof Pioche)
            decrementerPv(getEnv().getJoueur().getArme().nbDegat());
        else
            decrementerPv();
        getEnv().getJoueur().getArme().decrementerPv();
    }

    public void detruire() {
        Materiau materiau;
        int positionX = (int)this.getX() / Constantes.TAILLE_TUILE;
        int positionY = (int)this.getY() / Constantes.TAILLE_TUILE;
        switch (this.getClass().getSimpleName()) {
            case "Pierre": materiau = new Pierre(this.getEnv(), positionX, positionY); break;
            case "Fer": materiau = new Fer(this.getEnv(), positionX, positionY); break;
            case "Platine": materiau = new Platine(this.getEnv(), positionX, positionY); break;
            case "Terre" : materiau = new Terre(this.getEnv(), positionX, positionY); break;
            case "Bois" : materiau = new Bois(this.getEnv(), positionX, positionY); break;
            default: materiau = null; break;
        }
        this.getEnv().getListeEntites().add(materiau);

        getEnv().getMapJeu().getTabMap()[(int) (getY() / Constantes.TAILLE_TUILE)][(int) (getX() / Constantes.TAILLE_TUILE)] = 0;
        getEnv().getListeMateriaux().remove(this);
    }
}


