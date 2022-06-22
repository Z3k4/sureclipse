package application.modele.projectiles;

import application.modele.Direction;
import application.modele.Entite;
import application.modele.Environnement;
import application.modele.personnages.Personnage;


public abstract class Projectile extends Entite {

    private String id;
    private Personnage perso;
    private Direction direction;
    private float distanceParcourue;
    private boolean touche;

    public Projectile() {}

    public Projectile(Environnement env, int x, int y, String id, Personnage perso, Direction direction) {
        super(env, x, y);
        this.id = id;
        this.perso = perso;
        this.direction = direction;
        this.distanceParcourue = 0;
        this.touche = false;
    }

    public Projectile(Environnement env, int x, int y, String id, Personnage perso) {
        super(env, x, y);
        this.id = id;
        this.perso = perso;
        this.distanceParcourue = 0;
        this.touche = false;
    }

    public abstract void seDeplacer();

    @Override
    public void update() {
        super.collide();
        seDeplacer();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public float getDistanceParcourue() {
        return distanceParcourue;
    }

    public void setDistanceParcourue(float distanceParcourue) {
        this.distanceParcourue = distanceParcourue;
    }

    public void incrementerDistanceParcourue(float valeur) {
        this.distanceParcourue+=valeur;
    }

    public boolean getTouche() {
        return touche;
    }

    public void setTouche(boolean touche) {
        this.touche = touche;
    }

    public String getId() {
        return id;
    }

    public Personnage getPerso() {
        return perso;
    }

    protected int getVitesse() {
        return 7;
    }
}
