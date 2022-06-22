package application.modele.personnages.allies;

import application.modele.Environnement;

public class ChefVillage extends Allie{

    public ChefVillage(Environnement env, String id, int x, int y, int distance) {
        super(env, id, x, y, distance);
    }

    public ChefVillage(Environnement env, int x, int y, int distance) {
        super(env, "ChefVillage", x, y, distance);
    }


    @Override
    public void deplacement() {
        deplacementAllerRetour();
        seDeplacer();
    }

    @Override
    public void update() {
        tomber();
        deplacement();
    }

    @Override
    protected int getHauteurMax() {
        return 0;
    }

    @Override
    protected int getVitesse() {
        return 0;
    }

}
