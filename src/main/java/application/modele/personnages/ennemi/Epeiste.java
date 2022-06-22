package application.modele.personnages.ennemi;

import application.modele.Environnement;
import application.modele.armes.Epee;

public class Epeiste extends Ennemi {

    public Epeiste(Environnement env, int niveau, int x, int y, int distance) {
        super(env, x, y, distance, new Epee(env, niveau));
        setPv(niveau * 6);
    }

    protected void deplacement() {
        retourneDansZone();
        poursuiteJoueur();
        if (!getPoursuitJoueur())
                deplacementAllerRetour();
        seDeplacer();
    }
}
