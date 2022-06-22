package application.modele.personnages.ennemi;

import application.controleur.Constantes;
import application.modele.Direction;
import application.modele.Entite;
import application.modele.Environnement;

import application.modele.armes.Epee;
import application.modele.objets.Cle;
import application.modele.objets.consommable.Potion;
import application.modele.personnages.allies.Allie;
import application.modele.projectiles.BouleDeFeu;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import static application.modele.Direction.Droit;
import static application.modele.Direction.Gauche;

public class Tyran extends Ennemi {

    private static int DISTANCE_FUITE = 5 * Constantes.TAILLE_TUILE;

    private BooleanProperty chargeProperty;
    private int delaiCharge;
    private float distanceCharge;
    private int delaiLancer;
    private boolean touche;

    public Tyran(Environnement env, int x, int y, int distance) {
        super(env, x, y, distance, new Epee(env, 3));
        setPv(50);
        chargeProperty = new SimpleBooleanProperty(false);
        delaiCharge = 0;
        distanceCharge = 0;
        delaiLancer = 0;
        touche = false;
    }

    protected void deplacement() {
        if (chargeProperty.getValue())
            charger();
        else if (!fuitJoueur() && (Math.abs(getEnv().getJoueur().getX() - (getX())) - 4 >= 6 * Constantes.TAILLE_TUILE
                || Math.abs(getEnv().getJoueur().getY() - getY()) > Constantes.TAILLE_TUILE)
                && (Math.abs(getX() - getOrigineX()) > 1)) {
            if ((getX() - getOrigineX() < -1 && getDirection() == Gauche) || (getX() - getOrigineX() > 1 && getDirection() == Droit))
                setDirection(getDirectionOpposee());
            seDeplacer();
        }
    }

    //charge le joueur et le blesse s'il le touche
    private void charger() {
        if (delaiCharge++ > 30) {
            int i = 0;
            Entite ent;
            while (i < getVitesse() * 3 && distanceCharge < DISTANCE_FUITE) {
                i++;

                ent = super.getCollider().verifierCollisionDirection(getDirection(), 0.45f);
                if (ent != null)
                    ent.detruire();

                if (getDirection() == Direction.Droit)
                    super.setX(super.getX() + 0.45f);
                else
                    super.setX(super.getX() - 0.45f);
                distanceCharge += 0.45f;
            }

            if (!touche && Math.abs(getEnv().getJoueur().getX() - getX()) < 5 && Math.abs(getEnv().getJoueur().getY() - getY()) < Constantes.TAILLE_TUILE) {
                getArme().frapper(this, getEnv().getJoueur());
                touche = true;
            }

            if (i < getVitesse() * 3) {
                chargeProperty.setValue(false);
                delaiCharge = 0;
                distanceCharge = 0;
                touche = false;
            }
        }
    }

    //fuit le joueur à partir d'une certaine distance et charge si le joueur est trop près
    protected boolean fuitJoueur() {
        if (!chargeProperty.getValue() && Math.abs(getEnv().getJoueur().getY() - getY()) < Constantes.TAILLE_TUILE) {
            if (Math.abs(getEnv().getJoueur().getX() - getX()) < 2 * Constantes.TAILLE_TUILE)  {
                chargeProperty.setValue(true);
            } else if (Math.abs(getEnv().getJoueur().getX() - getX()) < DISTANCE_FUITE
                    && getX() >= getOrigineX() - getDistance() && getX() <= getOrigineX() + getDistance()) {
                Direction direction;
                if (getEnv().getJoueur().getX() - getX() <= 0) {
                    direction = Droit;
                    setDirection(Gauche);
                } else {
                    direction = Gauche;
                    setDirection(Droit);
                }
                int i = 0;
                while (i < 3 && getCollider().verifierCollisionDirection(direction, 0.45f) == null) {
                    i++;
                    if (direction == Droit)
                        super.setX(super.getX() + 0.45f);
                    else
                        super.setX(super.getX() - 0.45f);
                }
                return true;
            }
        }
        return false;
    }

    //lance des boules de feu
    //de type 1 si le joueur est loin sinon de type 2
    private void lancer() {
        if (delaiLancer++ >= 90) {
            if (!joueurEnFace() && getEnv().getJoueur().getX() > getOrigineX() - getDistance() &&  getEnv().getJoueur().getX() < getOrigineX() + getDistance()) {
                if (Math.abs(getEnv().getJoueur().getX() - getX()) >= 10 * Constantes.TAILLE_TUILE)
                    getEnv().getListeProjectiles().add(new BouleDeFeu(getEnv(), this, 1));
                else if (Math.abs(getEnv().getJoueur().getX() - getX()) >= DISTANCE_FUITE)
                    getEnv().getListeProjectiles().add(new BouleDeFeu(getEnv(), this, 2));
            }
            delaiLancer = 0;
        }
    }

    @Override
    public void update() {
        tomber();
        if (chargeProperty.getValue()) {
            charger();
        } else {
            lancer();
            deplacement();
        }
    }

    //drop la clé du château lorsqu'il meurt
    @Override
    public void detruire() {
        System.out.println("mort");
        getEnv().getListeEntites().add(new Cle(getEnv(), (int) getX(), (int) getY()));
        getEnv().getListeEnnemis().remove(this);
    }

    @Override
    protected int getHauteurMax() {
        return 0;
    }

    @Override
    protected int getVitesse() {
        return 3;
    }

    public BooleanProperty getChargeProperty() {
        return chargeProperty;
    }
}
