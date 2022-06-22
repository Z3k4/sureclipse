package application.controleur;

import application.modele.Environnement;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyReleased implements EventHandler<KeyEvent> {

    private ControleurJeu controleur;
    private Environnement jeu;

    public KeyReleased(ControleurJeu controleur, Environnement jeu) {
        this.controleur = controleur;
        this.jeu = jeu;
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case SPACE:
                jeu.getJoueur().setSaute(false);
                break;
            case Q:
            case D:
                jeu.getJoueur().setAvance(false);
                break;
            default:
                break;
        }
    }
}
