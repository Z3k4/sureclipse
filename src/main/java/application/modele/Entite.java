package application.modele;

import application.modele.collisions.Collider;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public class Entite {
    private FloatProperty xProperty;
    private FloatProperty yProperty;
    private Environnement env;
    private Collider collider;
    private IntegerProperty pv;
    private boolean tombe;

    public Entite(Environnement env) {
        pv= new SimpleIntegerProperty(100);
        this.xProperty = new SimpleFloatProperty(0);
        this.yProperty = new SimpleFloatProperty(0);
        this.collider = new Collider(this);

        this.env = env;
        this.pv = new SimpleIntegerProperty(10);
        tombe = false;
    }


    //Pour les matériaux

    /**
     * On initialise l'entitee
     * @param env L'environnement dans lequel se situe l'entité
     * @param x sa position en x
     * @param y sa position en y
     */
    public Entite(Environnement env, int x, int y) {
        this.xProperty = new SimpleFloatProperty(x);
        this.yProperty = new SimpleFloatProperty(y);
        this.env = env;
        this.collider = new Collider(this);
        this.pv = new SimpleIntegerProperty(100);
        tombe = false;
    }

    /**
     * On initialise l'entitee
     * @param env L'environnement dans lequel se situe l'entité
     * @param x sa position en x
     * @param y sa position en y
     * @param pv on définit des pv qu'on veut à l'entitée
     */
    public Entite(Environnement env, int x, int y, int pv) {
        this.xProperty = new SimpleFloatProperty(x);
        this.yProperty = new SimpleFloatProperty(y);
        this.env = env;
        this.collider = new Collider(this);
        this.pv = new SimpleIntegerProperty(pv);
        tombe = false;
    }

    public Entite() {
        this.xProperty = new SimpleFloatProperty(0);
        this.yProperty = new SimpleFloatProperty(0);
        this.collider = new Collider(this);
        tombe = false;
    }

    public void update() {
        if(this.getCollider() != null) {
            collide();
        }
        tomber();
    }

    protected void tomber() {
        int i = 0;
        while (i < getVitesse() && getCollider().verifierCollisionDirection(Direction.Bas, 0.60f) == null) {
            i++;
            tombe = true;
            setY(getY() + 0.60f);
        }

        if (i < getVitesse()) {
            tombe = false;
        }
    }

    public void detruire() {
    }

    /**
     * On vérifié les collisions avec les entites qui nous intéresses
     */
    public void collide() {
        verifCollide(env.getListeEntites());
        verifCollide(env.getListeEnnemis());
        verifCollide(env.getListeAnimaux());
    }

    private void verifCollide(ObservableList liste) {
        if(!this.getCollider().getIgnoreCollision()) {
            for (int i = 0; i < liste.size(); i++) {
                Entite ent = (Entite) liste.get(i);
                if (ent != this && !ent.getCollider().getIgnoreCollision() && this.getCollider().intersect(ent)) {
                    this.quandCollisionDetectee(ent);
                }
            }
        }
    }

    /**
     * Permet de retirer les pv un par un
     */
    public void decrementerPv() {
        pv.setValue(pv.getValue() - 1);
        if (getPv() <= 0)
            detruire();
    }

    /**
     * Permet de retirer un certain nombre de pv
     * @param degat
     */

    public void decrementerPv(int degat) {
        pv.setValue(pv.getValue() - degat);
        if (getPv() <= 0)
            detruire();
    }

    /**
     * Cette fonction permet de transmettre quelles entites ont été détecté comme collision
     * @param ent Contient l'entité avec qui on est rentré en collision
     */
    public void quandCollisionDetectee(Entite ent) {}

    protected int getVitesse() {
        return 4;
    }
    //region Getter & Setter
    public float getX() {
        return xProperty.getValue();
    }

    public float getY() {
        return yProperty.getValue();
    }

    public void setX(float valeur) {
        xProperty.setValue(valeur);
    };

    public void setY(float valeur) {
        yProperty.setValue(valeur);
    };

    public FloatProperty getYProperty() {
        return yProperty;
    }

    public FloatProperty getXProperty() {
        return xProperty;
    }

    public Collider getCollider() {
        return this.collider;
    }

    public Environnement getEnv() {
        return env;
    }

    public void setEnv(Environnement nouvEnv) {
        this.env = nouvEnv;
    }

    public int getPv() {
        return this.pv.getValue();
    }

    public IntegerProperty getPVProperty()  {
        return this.pv;
    }

    public void setPv(int value) {
        this.pv.setValue(value);
    }

    public boolean getTombe() {
        return tombe;
    }

    public void setTombe(boolean tombe) {
        this.tombe = tombe;
    }

    //endregion
}
