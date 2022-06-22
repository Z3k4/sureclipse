package application.vue;

import application.controleur.Constantes;
import application.modele.MapJeu;
import application.modele.ModeleDialogue;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class VueDialogue {
    private TextFlow dialogFlow;

    private float caractereActuel;
    private Text dialogTxt;

    private int partieTexteAffiche = 0;
    private ModeleDialogue modDialog;
    private boolean afficherFin;
    private boolean doitFermer = false;


    public VueDialogue(ModeleDialogue modDiag, TextFlow dialogFlow, Text dialogTxt){

        this.modDialog = modDiag;
        this.dialogTxt =dialogTxt ;

        this.dialogTxt.setTranslateX(10);
        this.dialogFlow = dialogFlow;
        this.dialogFlow .setPrefSize(MapJeu.WIDTH * Constantes.TAILLE_TUILE, Constantes.TAILLE_TUILE * 4);
        this.dialogFlow .setLayoutY(MapJeu.HEIGHT * Constantes.TAILLE_TUILE - this.dialogFlow .getPrefHeight());
        afficherFin = false;

    }

    public void prochainePartie() {
        this.afficherFin = false;
        partieTexteAffiche = 0;
        caractereActuel = 0;

    }

    public void afficherFin() {
        this.afficherFin = true;
    }

    public void animer(double vitesse) {
        String txt = modDialog.getTexteDialogue();

        if(!afficherFin) {
            if (caractereActuel < txt.length()) {
                caractereActuel += (vitesse + 0.6);
            }
        } else {
            partieTexteAffiche = 0;
            caractereActuel = txt.length();
        }

        if(partieTexteAffiche + (int)caractereActuel <= txt.length()) {
            dialogTxt.setText(txt.substring(partieTexteAffiche, partieTexteAffiche + (int) caractereActuel));

            if(dialogTxt.getBoundsInLocal().getHeight() >= this.dialogFlow .getPrefHeight()) {
                partieTexteAffiche = (int)caractereActuel;
                caractereActuel = 0;

            }
        }
    }

    public void afficher() {
        this.dialogFlow.setVisible(!this.dialogFlow.isVisible());
        if(this.dialogFlow.isVisible()) {
            caractereActuel = 0;
        }
    }

    public boolean estAffiche() {
        return this.dialogFlow.isVisible();
    }

    public void fermer() {
        this.dialogFlow.setVisible(false);
    }
}
