package application.modele.collisions;

import application.controleur.Constantes;
import application.modele.Direction;
import application.modele.Entite;

public class Collider {
    private RectangleCol hitbox;
    private Entite ent;
    private boolean ignoreCollision;
    private boolean activeVerifCollision;

    public Collider(Entite ent) {
        this.ent = ent;
        hitbox = new RectangleCol(32, 32);
        this.ignoreCollision = false;
        this.activeVerifCollision = false;
    }

    public boolean getIgnoreCollision() {
        return this.ignoreCollision;
    }

    public void setIgnoreCollision(boolean ignore) {
        this.ignoreCollision = ignore;
    }

    public boolean getActiveVerifCollision() {
        return this.activeVerifCollision;
    }

    public void setActiveVerifCollision(boolean active) {
        this.activeVerifCollision = active;
    }

    public void scaleCollider(int x, int y) {
        this.hitbox.scale(x, y);
    }

    public RectangleCol getHitBox() {
        return this.hitbox;
    }

    public Entite getEnt() {
        return this.ent;
    }

    public boolean intersect(Entite ent) {
        if (ent.getCollider() != null) {

            double entRecX = ent.getX();
            double entRecY = ent.getY();
            double entColWidth = ent.getCollider().getHitBox().getWidth();
            double entColHeight = ent.getCollider().getHitBox().getHeight();

            double colRecX = this.getEnt().getX();
            double colRecY = this.getEnt().getY();
            double colWidth = this.getHitBox().getWidth();
            double colRecHeight = this.getHitBox().getHeight();

            if (colRecX + colWidth > entRecX
                    && colRecX < entRecX + entColWidth
                    && colRecY + colRecHeight > entRecY && colRecY < entRecY + entColHeight) {
                return true;
            }


        }
        return false;
    }

    public Entite verificationCollisionGauche(double valeur) {
        if(this.ent.getX() <= 0) {
            return new Entite();
        }
        valeur = -Math.abs(valeur);

        if (!this.getIgnoreCollision()) {
            for (int i = 0; i < this.getEnt().getEnv().getListeMateriaux().size(); i++) {
                Entite ent = this.getEnt().getEnv().getListeMateriaux().get(i);
                if (ent != this.getEnt()) {
                    double entRecX = ent.getX();
                    double entRecY = ent.getY();
                    double entColWidth = ent.getCollider().getHitBox().getWidth();
                    double entColHeight = ent.getCollider().getHitBox().getHeight();

                    double colRecX = this.getEnt().getX();
                    double colRecY = this.getEnt().getY();
                    double colWidth = this.getHitBox().getWidth();
                    double colRecHeight = this.getHitBox().getHeight();

                    if (colRecX + colWidth + valeur > entRecX
                            && colRecX + valeur < entRecX + entColWidth
                            && colRecY + colRecHeight > entRecY && colRecY < entRecY + entColHeight) {
                        return ent;
                    }
                }
            }
        }

        return null;
    }

    public Entite verificationCollisionDroit(double valeur) {
        if(this.ent.getX() >= (this.ent.getEnv().getMapJeu().getWidth() - 1) * 32) {
            return new Entite();
        }

        valeur = Math.abs(valeur);
        if (!this.getIgnoreCollision()) {
            for (int i = 0; i < this.getEnt().getEnv().getListeMateriaux().size(); i++) {
                Entite ent = this.getEnt().getEnv().getListeMateriaux().get(i);
                if (ent != this.getEnt()) {
                    double entRecX = ent.getX();
                    double entRecY = ent.getY();
                    double entColWidth = ent.getCollider().getHitBox().getWidth();
                    double entColHeight = ent.getCollider().getHitBox().getHeight();

                    double colRecX = this.getEnt().getX();
                    double colRecY = this.getEnt().getY();
                    double colWidth = this.getHitBox().getWidth();
                    double colRecHeight = this.getHitBox().getHeight();

                    if (colRecX + colWidth + valeur > entRecX
                            && colRecX + valeur < entRecX + entColWidth
                            && colRecY + colRecHeight > entRecY && colRecY < entRecY + entColHeight) {
                        return ent;
                    }
                }
            }
        }

        return null;
    }

    public Entite verificationCollisionHaut(double valeur) {
        valeur = -Math.abs(valeur);

        if (!this.getIgnoreCollision()) {
            for (int i = 0; i < this.getEnt().getEnv().getListeMateriaux().size(); i++) {
                Entite ent = this.getEnt().getEnv().getListeMateriaux().get(i);
                if (ent != this.getEnt()) {
                    double entRecX = ent.getX();
                    double entRecY = ent.getY();
                    double entColWidth = ent.getCollider().getHitBox().getWidth();
                    double entColHeight = ent.getCollider().getHitBox().getHeight();

                    double colRecX = this.getEnt().getX();
                    double colRecY = this.getEnt().getY();
                    double colWidth = this.getHitBox().getWidth();
                    double colRecHeight = this.getHitBox().getHeight();

                    if (colRecX + colWidth > entRecX
                            && colRecX < entRecX + entColWidth
                            && colRecY + colRecHeight + valeur > entRecY &&
                            colRecY + valeur < entRecY + entColHeight) {
                        return ent;
                    }
                }
            }
        }
        return null;
    }

    public Entite verificationCollisionBas(double valeur) {
        valeur = Math.abs(valeur);
        if(this.ent.getY() >= (this.ent.getEnv().getMapJeu().getHeight() -1)  * 32) {
            return new Entite();
        }
        if (!this.getIgnoreCollision()) {
            for (int i = 0; i < this.getEnt().getEnv().getListeMateriaux().size(); i++) {
                Entite ent = this.getEnt().getEnv().getListeMateriaux().get(i);
                if (ent != this.getEnt()) {

                    double entRecX = ent.getX();
                    double entRecY = ent.getY();
                    double entColWidth = ent.getCollider().getHitBox().getWidth();
                    double entColHeight = ent.getCollider().getHitBox().getHeight();

                    double colRecX = this.getEnt().getX();
                    double colRecY = this.getEnt().getY();
                    double colWidth = this.getHitBox().getWidth();
                    double colRecHeight = this.getHitBox().getHeight();

                    if (colRecX + colWidth > entRecX
                            && colRecX < entRecX + entColWidth
                            && colRecY + colRecHeight + valeur > entRecY &&
                            colRecY + valeur < entRecY + entColHeight) {

                        return ent;
                    }
                }
            }
        }
        return null;
    }

    public Entite verifierCollisionDirection(Direction direction, double valeur) {
        switch (direction) {
            case Droit:
                return verificationCollisionDroit(valeur);
            case Gauche:
                return verificationCollisionGauche(valeur);
            case Haut:
                return verificationCollisionHaut(valeur);
            case Bas:
                return verificationCollisionBas(valeur);
        }

        return null;
    }
}
