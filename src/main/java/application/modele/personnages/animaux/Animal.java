package application.modele.personnages.animaux;

import application.modele.Environnement;
import application.modele.objets.consommable.Viande;
import application.modele.personnages.PNJ;

public abstract class Animal extends PNJ {

    public Animal(Environnement env, String id, int x, int y, int distance, int pv) {
        super(env, id, x, y, distance, pv);
    }

    public abstract int nbViande();

    @Override
    public void detruire() {
        int nbViande = nbViande();
        for (int i = 0; i < nbViande; i++)
            //getEnv().getJoueur().getInventaire().ajouterObjet(new Viande(getEnv()));
            this.getEnv().getListeEntites().add(new Viande(getEnv(), (int) getX(), (int) getY()));
        getEnv().getListeAnimaux().remove(this);
    }
}
