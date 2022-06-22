package application.vue;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

import static application.Main.HEIGHT_FENETRE;
import static application.Main.WIDTH_FENETRE;

public class FinDeJeuVue {

    private Label finDeJeu;
    private FadeTransition fade;


    public FinDeJeuVue(Label finDeJeu) {
        this.finDeJeu = finDeJeu;

        this.finDeJeu.toBack();
        this.finDeJeu.setPrefSize(WIDTH_FENETRE, HEIGHT_FENETRE);

        fade = new FadeTransition();
        fade.setNode(this.finDeJeu);
        fade.setDuration(Duration.seconds(2));
    }

    //affiche un ecran noir pour la transition
    public void transition() {
        finDeJeu.toFront();
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
}
