package application.vue.accueil;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
//import javafx.scene.media.AudioClip;

public class InteractionUI {
    @FXML
    private Button nouvellePartie;

    @FXML
    private Button quitterJeu;

    //private AudioClip interactionBruit = new AudioClip(getClass().getResource("/application/sons/ui/ui_menu_popup_03.wav").toExternalForm());

    public InteractionUI(Button nouvellePartie, Button quitterJeu) {
        /*interactionBruit.setVolume(0.2);
        nouvellePartie.setOnMouseEntered(e-> {
            interactionBruit.play();
        });

        quitterJeu.setOnMouseEntered(e-> {
            interactionBruit.play();
        });*/
    }
}
