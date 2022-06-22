package application.modele.personnages.ennemi;

import application.controleur.Constantes;
import application.modele.Environnement;
import application.modele.armes.Arc;

import static application.modele.Direction.Droit;
import static application.modele.Direction.Gauche;

public class Archer extends Ennemi {

    public Archer(Environnement env, int niveau, int x, int y, int distance) {
        super(env, x, y, distance, new Arc(env, niveau));
        setPv(niveau * 6);
    }

    protected void deplacement() {
        if (!fuitJoueur() && (Math.abs(getEnv().getJoueur().getX() - (getX())) - 4 >= 4 * Constantes.TAILLE_TUILE
                || Math.abs(getEnv().getJoueur().getY() - getY()) > Constantes.TAILLE_TUILE)
                && (Math.abs(getX() - getOrigineX()) > 1)) {
            retourOrigine();
            seDeplacer();
        }
    }

    @Override
    protected boolean joueurEnFace() {
        if (Math.abs(getEnv().getJoueur().getX() - getX()) < getArme().getDistance() * Constantes.TAILLE_TUILE
                && Math.abs(getEnv().getJoueur().getY() - getY()) < Constantes.TAILLE_TUILE) {
            if (getEnv().getJoueur().getX() - getX() <= 0)
                setDirection(Gauche);
            else
                setDirection(Droit);
            return true;
        }
        return false;
    }

    @Override
    public void update() {
        if (getDistancePoussee() != 0)
            estPoussee();
        else {
            tomber();
            if (getAttaque())
                attaquer();
            if (!getAttaque())
                detectionJoueur();
            deplacement();
        }
    }
}
