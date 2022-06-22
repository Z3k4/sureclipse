package application.modele.personnages.animaux;

import application.controleur.Constantes;
import application.modele.Environnement;


public class Lapin extends Animal {

    public final static int PV_MAX = 2;

    private static int id = 0;
    private int delaiSaut;

    public Lapin(Environnement env, int x, int y, int distance) {
        super(env, "Lapin" + id++, x, y, distance, PV_MAX);
        delaiSaut = (int) (Math.random() * 10);
    }

    @Override
    public void deplacement() {
        deplacementAllerRetour();
        seDeplacer();
    }

    public void saut() {
        if (!getSaute() && !getTombe())
            if (delaiSaut++ > 30) {
                setSaute(true);
                delaiSaut = (int) (Math.random() * 10);
            }
    }

    @Override
    public void update() {
        saut();
        if (getSaute()) sauter();
        else tomber();
        deplacement();
    }

    @Override
    protected int getHauteurMax() {
        return Constantes.TAILLE_TUILE;
    }

    @Override
    protected int getVitesse() {
        return 4;
    }

    @Override
    public int nbViande() {
        return 1;
    }
}
