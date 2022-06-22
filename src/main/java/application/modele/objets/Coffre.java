package application.modele.objets;

import application.modele.Entite;
import application.modele.Environnement;
import application.modele.armes.Epee;
import application.modele.objets.consommable.Potion;
import application.modele.objets.consommable.Viande;
import application.modele.objets.materiaux.Pierre;

import java.util.ArrayList;


public class Coffre extends Entite {

    private ArrayList<Entite> loot;

    public Coffre(Environnement env, int x, int y) {
        super(env, x, y);
        loot= new ArrayList<>();
        this.remplirLoot();
    }

    public Coffre(Environnement env, int x, int y, ArrayList loot) {
        super(env, x, y);
        this.loot = loot;
    }

    private void remplirLoot(){
        for (int i = 0; i < (int) (Math.random() * 4); i++) {
            switch ((int) (Math.random() * 4) + 1) {
                case 1: loot.add(new Potion(getEnv())); break;
                case 2:
                    loot.add(new Viande(getEnv()));
                    loot.add(new Viande(getEnv()));
                    break;
                case 3: loot.add(new Epee(getEnv(), 2)); break;
                default: break;
            }
        }
    }

    public void ouvrir() {
        for (int i = 0 ; i < loot.size();i++)
            getEnv().getJoueur().getInventaire().ajouterObjet(loot.get(i));
        detruire();
    }

    @Override
    public  void detruire() {
        getEnv().getMapJeu().getTabMap()[(int) getY()][(int) getX()] = 59;
        getEnv().getListeCoffres().remove(this);
    }

    public float getX() {
        return super.getX();
    }

    public float getY() {
        return super.getY();
    }
}
