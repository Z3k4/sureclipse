package application.modele.objets.consommable;

import application.modele.Environnement;

public class Viande extends Consommable {

    public Viande(Environnement env) {
        super(env);
    }

    public Viande(Environnement env, int x, int y) {
        super(env, x, y);
    }

    @Override
    public int getHeal() {
        return 5;
    }
}
