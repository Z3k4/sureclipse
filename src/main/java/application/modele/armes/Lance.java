package application.modele.armes;

import application.controleur.Constantes;
import application.modele.Direction;
import application.modele.Environnement;
import application.modele.personnages.Joueur;
import application.modele.personnages.Personnage;


public class Lance extends Arme {

    public Lance(Environnement env, int qualite) {
        super(env, qualite);
    }

    @Override
    public void frapper(Personnage perso, Personnage ennemi) {
        ennemi.decrementerPv(nbDegat());
        if (perso instanceof Joueur)
            decrementerPv();
        if (perso.getDirection() == Direction.Droit)
            ennemi.setDistancePoussee((getQualite()+1) * Constantes.TAILLE_TUILE);
        else
            ennemi.setDistancePoussee(-(getQualite()+1) * Constantes.TAILLE_TUILE);
    }

    public int getDistance() {
        return 2;
    }
}
