package application.modele.objets;

import application.modele.Entite;
import application.modele.Environnement;

public class Cle extends Entite {

    public Cle(Environnement env, int x, int y) {
        super(env, x, y);
    }

    @Override
    public void detruire() {
        getEnv().getJoueur().getInventaire().retirerObjet(getEnv().getJoueur().getInventaire().getObjetCorrespondant(this));
    }
}
