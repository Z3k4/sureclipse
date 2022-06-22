package application.modele.objets.materiaux;

import application.modele.Environnement;

public class Platine extends Materiau {

    private final static int PV_MAX = 8;

    public Platine() {
    }

    public Platine(Environnement env, int x, int y) {
        super(env, x, y, PV_MAX);
    }
}
