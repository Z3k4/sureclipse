package application.modele.armes;

import application.modele.Entite;
import application.modele.Environnement;

public class Armure extends Entite {

    private int qualite;

    public Armure(Environnement env, int qualite) {
        super(env);
        this.qualite = qualite;
        setPv(5 * qualite);
    }

    public int defendre() {
        return qualite;
    }

    public int getQualite() {
        return qualite;
    }

    @Override
    public void detruire() {
        getEnv().getJoueur().getInventaire().desequiperArmure();
        getEnv().getJoueur().getInventaire().retirerNbRessources(this.getClass().getSimpleName(), 1);
        setPv(20 * qualite);
    }
}
