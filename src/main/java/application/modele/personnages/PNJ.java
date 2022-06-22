package application.modele.personnages;

import application.controleur.Constantes;
import application.modele.Direction;
import application.modele.Entite;
import application.modele.Environnement;
import application.modele.objets.materiaux.Materiau;

import static application.modele.Direction.Droit;
import static application.modele.Direction.Gauche;

public abstract class PNJ extends Personnage {

    private float origineX;
    private float origineY;
    private int distance;

    public PNJ(Environnement env, String id, int x, int y, int distance) {
        super(env, id, x * Constantes.TAILLE_TUILE, y * Constantes.TAILLE_TUILE);
        origineX = x * Constantes.TAILLE_TUILE;
        origineY = y * Constantes.TAILLE_TUILE;
        this.distance = distance * Constantes.TAILLE_TUILE;
    }

    public PNJ(Environnement env, String id, int x, int y, int distance, int pv) {
        super(env, id, x * Constantes.TAILLE_TUILE, y * Constantes.TAILLE_TUILE, pv);
        origineX = x * Constantes.TAILLE_TUILE;
        origineY = y * Constantes.TAILLE_TUILE;
        this.distance = distance * Constantes.TAILLE_TUILE;
    }

    protected void deplacement() {
    }

    protected void deplacementAllerRetour() {
        if ((getX() >= origineX && getX() <= origineX + distance && getY() == origineY && estBloque() || (getX() < origineX && getDirection() == Gauche) || (getX() > origineX + distance && getDirection() == Droit)))
            setDirection(getDirectionOpposee());
        else if (estBloque()) {
            if (getDirection() == Gauche)
                origineX = getX();
            else
                origineX = getX() - getDistance();
            origineY = getY();
        }
    }

    protected boolean estBloque() {
        Entite collide1 = super.getCollider().verifierCollisionDirection(getDirection(), 0.45);
        Entite collide2 = super.getCollider().verifierCollisionDirection(getDirectionOpposee(), 10);
        return (collide1 != null && collide1 instanceof Materiau) && (collide2 == null || !(collide2 instanceof Materiau));
    }

    protected Direction getDirectionOpposee() {
        if (getDirection() == Droit)
            return Gauche;
        else
            return Droit;
    }

    public float getOrigineX() {
        return origineX;
    }

    public float getOrigineY() {
        return origineY;
    }

    public void setOrigineX(float origineX) {
        this.origineX = origineX;
    }

    public void setOrigineY(float origineY) {
        this.origineY = origineY;
    }

    public int getDistance() {
        return distance;
    }
}
