package application.vue;

import application.controleur.Constantes;
import application.controleur.ControleurJeu;
import application.controleur.listeners.AttaqueListener;
import application.modele.Direction;
import application.modele.armes.Lance;
import application.modele.armes.Arc;
import application.modele.personnages.ennemi.Ennemi;
import application.modele.personnages.Joueur;
import application.modele.personnages.Personnage;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ArmeVue {

    private Personnage perso;
    private ImageView spriteArme;
    private RotateTransition rt;
    private TranslateTransition tt;
    private int dir;
    private boolean rendreVisible;

    private ControleurJeu controleur;

    public ArmeVue(Personnage perso, ImageView spriteArme, ControleurJeu controleur) {
        this.controleur = controleur;
        this.perso = perso;
        this.spriteArme = spriteArme;
        spriteArme.setVisible(false);
        rt = new RotateTransition(Duration.millis(90), spriteArme);
        tt = new TranslateTransition(Duration.millis(150), spriteArme);
        initDirection(); initTt();
        spriteArme.setX(PersonnageVue.POSITION_VUE_JOUEUR_X + dir * 10);
        spriteArme.setY(PersonnageVue.POSITION_VUE_JOUEUR_Y);
        rendreVisible = false;
    }


    public ArmeVue(Pane panePNJ, Personnage perso, ControleurJeu controleur) {
        this.controleur = controleur;
        this.perso = perso;
        initSprite();
        rt = new RotateTransition(Duration.millis(90), spriteArme);
        tt = new TranslateTransition(Duration.millis(150), spriteArme);
        initDirection(); initAnimation();
        if (perso.getArme() instanceof Lance) initTt();
        panePNJ.getChildren().add(spriteArme);
        ((Ennemi) perso).getAttaqueProperty().addListener(new AttaqueListener(this));
        rendreVisible = false;
        updatePositon();
    }

    //region INITIALISATION
    private void initSprite() {
        spriteArme = new ImageView();
        spriteArme.setVisible(false);
        spriteArme.setId(perso.getId() + "Arme");
        spriteArme.setFitWidth(28);
        spriteArme.setFitHeight(28);
        spriteArme.setImage(ChargeurRessources.iconObjets.get(perso.getArme().getClass().getSimpleName() + perso.getArme().getQualite()));
    }

    private void initDirection() {
        if (perso.getDirection() == Direction.Droit)
            dir = -1;
        else
            dir = 1;
    }

    //change l'orientation de l'imageView pour que le sprite soit vertical
    public void initAnimation() {
        rt.setDuration(Duration.ONE);
        rt.setCycleCount(1);
        if (perso.getArme() instanceof Lance)
            rt.setByAngle(dir * 135);
        else if (perso.getArme() instanceof Arc)
            rt.setByAngle(dir * 233);
        else
            rt.setByAngle(dir * 50);
        rt.setOnFinished(actionEvent -> {
            if (perso.getDirection() == Direction.Droit && dir == -1 || perso.getDirection() == Direction.Gauche && dir == 1)
                inverserSprite();
        });
        rt.play();
    }

    private void initTt() {
        tt.setCycleCount(2);
        tt.setAutoReverse(true);
        tt.setOnFinished(actionEvent -> {
            spriteArme.setVisible(rendreVisible);
            rendreVisible = false;
            if (perso.getDirection() == Direction.Droit && dir == -1 || perso.getDirection() == Direction.Gauche && dir == 1)
                inverserSprite();
        });
    }
    //endregion

    public void animationFrappe() {
        if (rt.getCurrentRate() == 0 && tt.getCurrentRate() == 0 && perso.getArme() != null) {
            if (perso instanceof Joueur) rendreVisible();
            if (perso.getArme() instanceof Lance) {
                tt.setByX(dir* Constantes.TAILLE_TUILE);
                tt.play();
            } else {
                if (perso.getArme() instanceof Arc) {
                    rt.setDuration(Duration.millis(300));
                    rt.setByAngle(0);
                } else {
                    rt.setDuration(Duration.millis(150));
                    rt.setByAngle(dir * 90);
                    rt.setAutoReverse(true);
                    rt.setCycleCount(2);
                }
                rt.setOnFinished(actionEvent -> {
                    //rend le sprite invisible et l'inverse si nécessaire
                    spriteArme.setVisible(rendreVisible);
                    rendreVisible = false;
                    if (perso.getDirection() == Direction.Droit && dir == -1 || perso.getDirection() == Direction.Gauche && dir == 1)
                        inverserSprite();
                });
                rt.play();


                this.controleur.getAmbianceEnvironnement().jouerSonObjet("Bruit"+perso.getArme().getClass().getSimpleName());

            }
        }
    }

    //inverse l'image selon la direction
    public void inverserSprite() {
        if (rt.getCurrentRate() == 0 && tt.getCurrentRate() == 0) {
            spriteArme.setScaleX(dir);
            dir = -dir;
            if (perso instanceof Joueur)
                spriteArme.setX(PersonnageVue.POSITION_VUE_JOUEUR_X + dir * 10);
            rt.setDuration(Duration.ONE);
            rt.setCycleCount(1);
            rt.setOnFinished(actionEvent1 -> {});
            if (perso.getArme() instanceof Lance) {
                rt.setByAngle(dir * -90);
            } else {
                rt.setByAngle(dir * 100);
            }
            rt.play();
        }
    }

    public void rendreVisible() {
        if (rt.getCurrentRate() == 0)
            spriteArme.setVisible(true);
        else
            rendreVisible = true;
    }

    public void rendreInvisible() {
            spriteArme.setVisible(false);
    }

    public void updatePositon() {
        spriteArme.setTranslateX(perso.getX() + dir * 10);
        spriteArme.setTranslateY(perso.getY());
    }

    //change l'image et remet l'imageView à son orientation initial
    public void changementArme() {
        spriteArme.setImage(ChargeurRessources.iconObjets.get(perso.getArme().getClass().getSimpleName() + perso.getArme().getQualite()));
        RotateTransition rt = new RotateTransition(Duration.ONE, spriteArme);
        rt.setFromAngle(0);
        rt.setToAngle(0);
        rt.setOnFinished(actionEvent -> initAnimation());
        rt.play();
    }

    public void retirer() {
        spriteArme.setImage(null);
    }

}
