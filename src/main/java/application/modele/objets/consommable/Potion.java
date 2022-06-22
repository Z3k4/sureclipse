package application.modele.objets.consommable;

import application.modele.Environnement;

public class Potion extends Consommable {

    public Potion(Environnement env) {
        super(env);
    }

    public Potion(Environnement env, int x, int y) {
        super(env, x, y);
    }

    @Override
    public int getHeal() {
        return 10;
    }
}
