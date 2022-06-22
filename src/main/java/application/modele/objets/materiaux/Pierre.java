package application.modele.objets.materiaux;

import application.modele.Environnement;

public class Pierre extends Materiau {

    private final static int PV_MAX = 3;

    public Pierre() {
    }

    public Pierre(Environnement env, int x, int y) {
        super(env, x, y, PV_MAX);
    }
}
