package application.modele.objets.consommable;

import application.modele.Entite;
import application.modele.Environnement;
import application.modele.personnages.Joueur;


public abstract class Consommable extends Entite {

    public Consommable(Environnement env) {
        super(env);
    }

    public Consommable(Environnement env, int x, int y) {
        super(env, x, y);
    }

    public void consommer() {
        if (getEnv().getJoueur().getPv() + getHeal() > Joueur.PV_MAX)
            getEnv().getJoueur().setPv(Joueur.PV_MAX);
        else
            getEnv().getJoueur().setPv(getEnv().getJoueur().getPv() + getHeal());
        detruire();
    }

    public abstract int getHeal();

    @Override
    public void detruire() {
        getEnv().getJoueur().getInventaire().retirerNbRessources(this.getClass().getSimpleName(), 1);
    }
}
