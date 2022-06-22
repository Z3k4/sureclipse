package application.modele.objets.materiaux;

import application.modele.Environnement;

public class Fer extends Materiau {

    private final static int PV_MAX = 5;

    public Fer() {}

    public Fer(Environnement env, int x, int y) {
        super(env, x, y, PV_MAX);
    }
}
