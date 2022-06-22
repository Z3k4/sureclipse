package application.vue.accueil;

import application.vue.ChargeurRessources;
//import javafx.scene.media.AudioClip;

public class AmbianceEnvironnement {

    //private AudioClip ambianceSonoreActuel;
    private String sonActuel;


    public AmbianceEnvironnement() {
        /*ambianceSonoreActuel = ChargeurRessources.ensembleSonJeu.get("introjeu");
        ambianceSonoreActuel.setVolume(0.05);
        ambianceSonoreActuel.play();*/
    }

    public void changerSon(String son) {
        /*if(sonActuel != son) {
            sonActuel = son;
            if(ambianceSonoreActuel != null) {
                ambianceSonoreActuel.stop();
            }

            ambianceSonoreActuel = ChargeurRessources.ensembleSonJeu.get(sonActuel);
            ambianceSonoreActuel.setVolume(0.05);

            ambianceSonoreActuel.play();
        }*/

    }

    /**
     *Permet de jouer des sons indépendamment de celui de l'environnement, est utile pour par exemple les armes
     */
    public void jouerSonObjet(String nom) {
        /*if(ChargeurRessources.ensembleSonJeu.get(nom) != null) {
            ChargeurRessources.ensembleSonJeu.get(nom).setVolume(0.05);
            ChargeurRessources.ensembleSonJeu.get(nom).play();
        }*/
    }

    public void stopperSon() {
        //ambianceSonoreActuel.stop();
    }
}
