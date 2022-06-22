package application.modele.objets;

import application.modele.Entite;
import application.modele.Environnement;
import application.modele.armes.Hache;
import application.modele.objets.materiaux.Bois;

public class Arbre extends Entite {

    private final static int PV_MAX = 12;

    public Arbre(Environnement env, int x, int y) {
        super(env, x, y, PV_MAX);

    }
    //retourne le nombre de bois drop
    public void estFrappe() {
        int nbDegats;

        if (getEnv().getJoueur().getArme() instanceof Hache)
            nbDegats = getEnv().getJoueur().getArme().nbDegat();
        else
            nbDegats = 1;

        int nbBois = 0;
        for (int i = 0; i < nbDegats; i++) {
            decrementerPv();
            if (getPv() % 4 == 0) nbBois++;
        }
        getEnv().getJoueur().getArme().decrementerPv();

        for (int i = 0; i < nbBois; i++)
            getEnv().getListeEntites().add(new Bois(getEnv(), (int) getX(), (int) getY()));
    }

    @Override
    public void detruire() {
        getEnv().getMapJeu().getTabMap()[(int) getY()][(int) getX()] = 0;
        getEnv().getListeArbres().remove(this);
    }
}
