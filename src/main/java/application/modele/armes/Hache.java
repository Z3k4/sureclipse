package application.modele.armes;

import application.modele.Environnement;

public class Hache extends Arme {

    public Hache(Environnement env, int qualite) {
        super(env, qualite);
    }

    public int nbDegat() {
        if (getQualite() == 1) {
            return 2;
        } else if (getQualite() == 2) {
            return 3;
        } else {
            return 5;
        }
    }
}
