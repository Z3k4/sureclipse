package application.controleur;

import application.modele.ModeleDialogue;
import application.vue.VueDialogue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class DialogueControleur implements EventHandler<Event> {

    private VueDialogue vueDialogue;
    private ModeleDialogue modDialog;
    private QueteControleur queteControleur;

    private int nombreClick = 0;

    public DialogueControleur(VueDialogue vueDialogue, ModeleDialogue modeleDialogue, QueteControleur queteControleur) {
        this.vueDialogue = vueDialogue;
        this.modDialog = modeleDialogue;
        this.queteControleur = queteControleur;
    }

    //Permet de faire des actions spécifiques en fonction du nombre de fois que le joueur à cliqué sur l'écran
    //Exemple avancer rapidement le texte, passer à une autre partie du dialogue, etc..

    @Override
    public void handle(Event event) {

        if(event instanceof MouseEvent && event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if ( ((MouseEvent) event).getButton() == MouseButton.PRIMARY) {
                nombreClick++;


                if (nombreClick == 1) {
                    this.vueDialogue.afficherFin();
                } else if (nombreClick == 2 && !this.modDialog.dernierePartie()) {
                    this.vueDialogue.prochainePartie();
                    this.modDialog.avancerPartie();
                    nombreClick = 0;
                } else if (nombreClick == 3 || nombreClick == 2 && this.modDialog.dernierePartie()) {
                    this.vueDialogue.fermer();

                    //Devrait être adapté pour les personnages qui ne donnent pas de quête
                    this.queteControleur.donnerQuete();
                }

            }
        } else if( event instanceof KeyEvent && event.getEventType() == KeyEvent.KEY_RELEASED) {
            if(((KeyEvent) event).getCode() == KeyCode.H ) {

                this.vueDialogue.afficher();
                if(this.vueDialogue.estAffiche()) {
                    lancerDialogue();
                }
            }
        }

    }

    /**
     * S'occupe d'afficher le bon dialogue en fonction du statut de la quête (récupéré, en cours, terminer)
     */
    public void debutDialogue()  {

        if(this.queteControleur.verifierQueteFini()) {
            this.modDialog.changerDialogue(2);
        } else if (this.queteControleur.getQueteEnCours()){
            this.modDialog.changerDialogue(1);
        }

        this.vueDialogue.afficher();
        if (this.vueDialogue.estAffiche()) {
            lancerDialogue();
        }

    }

    public void lancerDialogue() {
        this.modDialog.reinitialiserDialogue();
        nombreClick = 0;

    }
}
