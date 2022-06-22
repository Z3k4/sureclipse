package application.modele.objets.materiaux;

import application.modele.Environnement;
import application.modele.armes.Hache;

public class Bois extends Materiau {

    private final static int PV_MAX = 2;

    public Bois() {
    }

    public Bois(Environnement env, int x, int y) {
        super(env, x, y, PV_MAX);
    }

    @Override
    public void estFrappe() {
        if (getEnv().getJoueur().getArme() instanceof Hache)
            decrementerPv(2);
        else
            decrementerPv(1);
        getEnv().getJoueur().getArme().decrementerPv();

        if (getPv() <= 0)
            detruire();
    }
}
