package application.modele.personnages;

import application.modele.Direction;
import application.modele.Entite;
import application.modele.Environnement;
import application.modele.armes.Arme;
import javafx.beans.property.*;

public abstract class Personnage extends Entite {

    private String id;
    private ObjectProperty<Direction> directionProperty;
    private boolean saute;
    private float hauteurSaut;
    private int distancePoussee;

    public Personnage(Environnement env) {
        super(env);
        id = "Joueur";
        saute = false;
        directionProperty = new SimpleObjectProperty<>(Direction.Droit);
        hauteurSaut = 0;
        distancePoussee = 0;

        this.getCollider().getHitBox().setWidth(26);
        this.getCollider().getHitBox().setHeight(28);
    }

    public Personnage(Environnement env, String id, int x, int y) {
        super(env, x, y);
        this.id = id;
        saute = false;
        directionProperty = new SimpleObjectProperty<>(Direction.Droit);
        hauteurSaut = 0;
        distancePoussee = 0;
        this.getCollider().getHitBox().setWidth(26);
        this.getCollider().getHitBox().setHeight(28);
    }

    public Personnage(Environnement env, String id, int x, int y, int pv) {
        super(env, x, y, pv);
        this.id = id;
        saute = false;
        directionProperty = new SimpleObjectProperty<>(Direction.Droit);
        hauteurSaut = 0;
        distancePoussee = 0;
        this.getCollider().getHitBox().setWidth(26);
        this.getCollider().getHitBox().setHeight(28);
    }

    protected void seDeplacer() {
        int distance;
        if (getTombe() || saute)
            distance = getVitesse() - 1;
        else
            distance = getVitesse();

        int i = 0;

        while (i < distance && super.getCollider().verifierCollisionDirection(directionProperty.getValue(), 0.45f) == null) {
            i++;
            if (directionProperty.getValue() == Direction.Droit) {
                super.setX(super.getX() + 0.45f);
            } else {
                super.setX(super.getX() - 0.45f);
            }
        }
    }

    protected void sauter() {
        int i = 0;
        while (i < getVitesse() && hauteurSaut < getHauteurMax() && super.getCollider().verifierCollisionDirection(Direction.Haut, 0.60f) == null) {
            i++;
            super.setY(super.getY() - 0.60f);
            hauteurSaut +=0.60f;
        }
        if (i < getVitesse())
            saute = false;
    }


    @Override
    protected void tomber() {
        int i = 0;
        while (i < getVitesse() && super.getCollider().verifierCollisionDirection(Direction.Bas, 0.60f) == null) {
            i++;
            setTombe(true);
            super.setY(super.getY() + 0.60f);
        }

        if (i < getVitesse()) {
            setTombe(false);
            hauteurSaut = 0;
        }
    }

    protected void estPoussee() {
        Direction direction;
        if (distancePoussee > 0)
            direction = Direction.Droit;
        else
            direction = Direction.Gauche;
        int i = 0;
        while (i < 3 && distancePoussee != 0 && super.getCollider().verifierCollisionDirection(direction, 1) == null) {
            i++;
            if (direction == Direction.Droit) {
                super.setX(super.getX() + 1);
                distancePoussee--;
            } else {
                super.setX(super.getX() - 1);
                distancePoussee++;
            }
        }

        if (i < 3) {
            distancePoussee = 0;
        }
    }

    public abstract void update();

    //region Getter & Setter
    protected abstract int getHauteurMax();

    protected float getHauteurSaut() {
        return hauteurSaut;
    }

    protected void setHauteurSaut(float hauteurSaut) {
        this.hauteurSaut = hauteurSaut;
    }

    public final Direction getDirection() {
        return directionProperty.getValue();
    }

    public final void setDirection(Direction direction) {
        this.directionProperty.setValue(direction);
    }

    public final ObjectProperty getDirectionProperty() {
        return directionProperty;
    }

    public boolean getSaute() {
        return saute;
    }

    public void setSaute(boolean saute) {
        this.saute = saute;
    }

    public String getId() {
        return id;
    }

    public int getDistancePoussee() {
        return distancePoussee;
    }

    public void setDistancePoussee(int distancePoussee) {
        this.distancePoussee = distancePoussee;
    }

    public Arme getArme() {
        return null;
    }
    //endregion
}
