package application.controleur;

import application.modele.Environnement;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static application.Main.HEIGHT_FENETRE;
import static application.Main.WIDTH_FENETRE;

public class PauseControleur implements EventHandler<KeyEvent> {

    private Environnement env;
    private Pane root;
    private VBox vBoxPause;
    private VBox vBoxSuicide;

    public PauseControleur(Environnement env, Pane root, VBox vBoxPause, VBox vBoxSuicide) {
        this.env = env;
        this.root = root;
        this.vBoxPause = vBoxPause;
        this.vBoxSuicide = vBoxSuicide;

        vBoxPause.setLayoutX(WIDTH_FENETRE/2 - vBoxPause.getPrefWidth()/2);
        vBoxPause.setLayoutY(HEIGHT_FENETRE/2 - vBoxPause.getPrefHeight()/2 - 50);
        vBoxPause.setVisible(false);
        vBoxSuicide.setLayoutX(WIDTH_FENETRE/2 - vBoxSuicide.getPrefWidth()/2);
        vBoxSuicide.setLayoutY(HEIGHT_FENETRE/2 - vBoxSuicide.getPrefHeight()/2 - 50);
        vBoxSuicide.setVisible(false);

        ((Button) vBoxPause.lookup("#boutonReprendre")).setOnAction(actionEvent -> {
            pauser();
            root.requestFocus();
        });
        ((Button) vBoxPause.lookup("#boutonSuicide")).setOnAction(actionEvent -> {
            vBoxSuicide.setVisible(true);
        });
        ((Button) vBoxSuicide.lookup("#oui")).setOnAction(actionEvent -> {
            env.getJoueur().detruire();
            vBoxSuicide.setVisible(false);
            pauser();
            root.requestFocus();
        });
        ((Button) vBoxSuicide.lookup("#non")).setOnAction(actionEvent -> {
            vBoxSuicide.setVisible(false);
        });
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE && !vBoxSuicide.isVisible())
            pauser();
    }

    private void pauser() {
        if (!env.pauser())
            root.requestFocus();
        vBoxPause.setVisible(!vBoxPause.isVisible());
    }
}
