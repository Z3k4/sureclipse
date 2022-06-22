package application.controleur.events;

import application.modele.ModeleDialogue;
import application.vue.VueDialogue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;

public class DialogueListener implements EventHandler<MouseEvent> {
    private ModeleDialogue modeleDialogue;

    public DialogueListener(ModeleDialogue modeleDialogue) {
        this.modeleDialogue = modeleDialogue;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        this.modeleDialogue.avancerPartie();
    }
}
