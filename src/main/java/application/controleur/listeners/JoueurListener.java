package application.controleur.listeners;

import application.controleur.ControleurJeu;
import application.modele.personnages.Joueur;
import application.vue.accueil.AmbianceEnvironnement;

public class JoueurListener {
    private AmbianceEnvironnement ambianceEnvironnement;

    public JoueurListener(Joueur joueur, ControleurJeu controleur) {
        this.ambianceEnvironnement = controleur.getAmbianceEnvironnement();

        joueur.getPVProperty().addListener((arg, ancien, nouveau) -> {
            if(nouveau.intValue() < ancien.intValue()) {
                if(nouveau.intValue() <= 0) {
                    this.ambianceEnvironnement.changerSon("mort");
                } else {
                    this.ambianceEnvironnement.changerSon("battle");

                }
            }
        });
    }
}
