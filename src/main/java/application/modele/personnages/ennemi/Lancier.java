package application.modele.personnages.ennemi;

import application.modele.Environnement;
import application.modele.armes.Lance;


public class Lancier extends Ennemi {

    public Lancier(Environnement env, int niveau, int x, int y, int distance) {
        super(env, x, y, distance, new Lance(env, niveau));
        setPv(niveau * 6);
    }

    protected void deplacement() {
        poursuiteJoueur();
        if (!getPoursuitJoueur())
            retourOrigine();
        if (getPoursuitJoueur() || getRetourZone() || (Math.abs(getX() - getOrigineX()) > 1)) {
            seDeplacer();
        }
    }
}
