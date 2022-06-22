package application.modele.projectiles;

import application.controleur.Constantes;
import application.modele.Direction;
import application.modele.Entite;
import application.modele.Environnement;
import application.modele.personnages.Joueur;
import application.modele.personnages.Personnage;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import static application.modele.Direction.*;

public class BouleDeFeu extends Projectile {

    private final static int DEGATS = 3;

    private static int idMax = 0;

    private BooleanProperty chuteProperty;
    private int type;
    private float force;

    public BouleDeFeu(Environnement env, Personnage perso, int type) {
        super(env, (int) perso.getX(), (int) perso.getY() - 10, "BouleDeFeu" + idMax++, perso);
        chuteProperty = new SimpleBooleanProperty(false);
        if (getEnv().getJoueur().getX() < getX())
            setDirection(Gauche);
        else
            setDirection(Droit);
        this.type = type;
        force = 0.5f;
    }

    @Override
    public void seDeplacer() {
        if (type == 1)
            deplacementType1();
        else
            deplacementType2();
    }

    //la boule de feu se deplace en arc de cercle
    private void deplacementType1() {
        if (!chuteProperty.getValue()) {
            deplacementType1SousPartie(Haut);
            force-=0.01f;
            if (force <= 0.01f) {
                setDistanceParcourue(0);
                chuteProperty.setValue(true);
            }
        } else {
            deplacementType1SousPartie(Bas);
            force += 0.01f;
            if (force > 0.62f) {
                getEnv().getListeProjectiles().remove(this);
            }
        }
    }

    private void deplacementType1SousPartie(Direction direction) {
        int i = 0;
        while (i < getVitesse()) {
            i++;
            Entite touchee = this.getCollider().verifierCollisionDirection(getDirection(), force);
            if (touchee != null)
                quandCollisionDetectee(touchee);
            touchee = this.getCollider().verifierCollisionDirection(direction, 0.5f);
            if (touchee != null)
                quandCollisionDetectee(touchee);

            if (direction == Haut)
                setY(getY() - force);
            else
                setY(getY() + force);

            if (getDirection() == Droit)
                setX(getX() + 0.5f);
            else
                setX(getX() - 0.5f);
        }
    }

    //la boule de feu se deplace en ligne droite
    private void deplacementType2() {
        int i = 0;
        while (i < getVitesse() && getDistanceParcourue() < 8 * Constantes.TAILLE_TUILE) {
            i++;
            Entite touchee = this.getCollider().verifierCollisionDirection(getDirection(), 0.3f);
            if(touchee != null)
                quandCollisionDetectee(touchee);

            if (getDirection() == Droit)
                setX(getX() + 0.3f);
            else
                setX(getX() - 0.3f);

            incrementerDistanceParcourue(0.3f);
            //collide();
        }

        if (i < getVitesse())
            getEnv().getListeProjectiles().remove(this);
    }
    @Override
    public void update() {
        super.collide();
        seDeplacer();
    }

    @Override
    public void quandCollisionDetectee(Entite ent) {
        if (!getTouche() && ent != getPerso()) {
            if (ent instanceof Joueur) {
                ent.decrementerPv(DEGATS);
                setTouche(true);
                getEnv().getListeProjectiles().remove(this);
            }
            else if (!getEnv().getListeEntites().contains(ent)){
                ent.detruire();
            }
        }
    }

    public BooleanProperty getChuteProperty() {
        return chuteProperty;
    }

    public int getType() {
        return type;
    }
}
