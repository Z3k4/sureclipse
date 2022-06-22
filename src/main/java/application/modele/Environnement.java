package application.modele;

import application.controleur.Constantes;
import application.modele.objets.*;
import application.modele.objets.materiaux.*;
import application.modele.personnages.*;
import application.modele.personnages.allies.Allie;
import application.modele.personnages.allies.ChefVillage;
import application.modele.personnages.animaux.Animal;
import application.modele.personnages.animaux.Lapin;
import application.modele.personnages.animaux.Sanglier;
import application.modele.personnages.ennemi.*;
import application.modele.projectiles.Projectile;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Environnement {

    private BooleanProperty pauseProperty;
    private Joueur joueur;
    private MapJeu mapJeu;
    private Etabli etabli;
    private FeuDeCamp feuDeCamp;
    private ObservableList<Entite> listeEntites;
    private ObservableList<Materiau> listeMateriaux;
    private ObservableList<Arbre> listeArbres;
    private ObservableList<Coffre> listeCoffres;
    private ObservableList<Ennemi> listeEnnemis;
    private ObservableList<Animal> listeAnimaux;
    private ObservableList<Allie> listeAllies;
    private ObservableList<Projectile> listeProjectiles;

    public Environnement() {
        pauseProperty = new SimpleBooleanProperty(false);
        joueur = new Joueur(this);

        mapJeu = new MapJeu();
        etabli = new Etabli(this);
        feuDeCamp = new FeuDeCamp(this, 13, 21);

        listeEntites = FXCollections.observableArrayList();
        listeCoffres = FXCollections.observableArrayList();
        listeEntites.add(joueur);

        initListeMateriaux();
        initListeArbres();
        initListeCoffres();
        listeEnnemis = FXCollections.observableArrayList();
        initListeEnnemis();
        listeAnimaux = FXCollections.observableArrayList();
        initListeAnimaux();
        listeAllies = FXCollections.observableArrayList();
        initListeAllies();
        listeProjectiles = FXCollections.observableArrayList();
    }

    //region init
    private void initListeArbres() {
        listeArbres = FXCollections.observableArrayList();
        for (int i = 0; i < mapJeu.getHeight(); i++) {
            for (int j = 0; j < mapJeu.getWidth(); j++) {
                if (mapJeu.getTabMap()[i][j] == 160) {
                    listeArbres.add(new Arbre(this, j, i));
                    listeArbres.get(listeArbres.size()-1).getX();
                }
            }
        }
    }

    private void initListeMateriaux() {
        listeMateriaux = FXCollections.observableArrayList();
        for (int i = 0; i < mapJeu.getHeight(); i++) {
            for (int j = 0; j < mapJeu.getWidth(); j++) {
                int id = mapJeu.getTabMap()[i][j];
                    if(id == 20) {
                        listeMateriaux.add(new Pierre(this, j, i));
                    } else if(id == 178) {
                        listeMateriaux.add(new Fer(this, j, i));
                    } else if(id == 179) {
                        listeMateriaux.add(new Platine(this, j, i));
                    }else if(id == 74 || id == 92) {
                        listeMateriaux.add(new Terre(this, j, i));
                    }
            }
        }
    }

    public void initListeEnnemis() {
        listeEnnemis.clear();
        listeEnnemis.add(new Epeiste(this,1, 46, 23, 5));
        listeEnnemis.add(new Epeiste(this,1, 53, 24, 5));
        listeEnnemis.add(new Epeiste(this,1, 60, 24, 5));
        listeEnnemis.add(new Lancier(this, 2, 81, 26, 0));
        listeEnnemis.add(new Archer(this, 2, 84, 26, 0));
        listeEnnemis.add(new Lancier(this, 2, 101, 24, 0));
        listeEnnemis.add(new Lancier(this, 3, 107, 22, 0));
        listeEnnemis.add(new Archer(this, 3, 112, 21, 0));
        listeEnnemis.add(new Epeiste(this,3, 116, 19, 4));
    }

    private void initListeCoffres() {
        listeCoffres.add(new Coffre(this, 114, 50));
    }

    public void initListeAnimaux() {
        listeAnimaux.clear();
        listeAnimaux.add(new Lapin(this, 20,21, 8));
        listeAnimaux.add(new Lapin(this, 25,21, 9));
        listeAnimaux.add(new Sanglier(this, 36,22, 9));
        listeAnimaux.add(new Lapin(this, 67,26, 10));
        listeAnimaux.add(new Sanglier(this, 88,26, 6));
    }

    public void initListeAllies() {
        listeAllies.clear();
        listeAllies.add(new ChefVillage(this, 2,21, 11));
    }
            //endregion

    public boolean pauser() {
        this.pauseProperty.setValue(!pauseProperty.getValue());
        return pauseProperty.getValue();
    }

    public Materiau getMinerai(int x, int y) {
        for (Materiau minerai : getListeMateriaux()) {
            if (minerai.getX() == x * Constantes.TAILLE_TUILE && minerai.getY() == y * Constantes.TAILLE_TUILE)
                return minerai;
        }
        return null;

    }

    public Arbre getArbre(int x, int y) {

        System.out.println(mapJeu.getTabMap()[y][x]);
        if (mapJeu.getTabMap()[y][x] == 161) y++;
        else if (mapJeu.getTabMap()[y][x] == 162) y+=2;

        for (Arbre arbre : listeArbres)
            if (arbre.getX() == x && arbre.getY() == y)
                return arbre;
        return null;
    }

    public Ennemi getEnnemi(int x, int y) {
        for (Ennemi ennemi : listeEnnemis) {
            if (Math.abs(ennemi.getX() / Constantes.TAILLE_TUILE - x) < 1 && Math.abs(ennemi.getY() / Constantes.TAILLE_TUILE - y) < 1)
                return ennemi;
        }
        return null;
    }

    public Animal getAnimal(int x, int y) {
        for (Animal animal : listeAnimaux) {
            if (Math.abs(animal.getX() / Constantes.TAILLE_TUILE - x) < 1 && Math.abs(animal.getY() / Constantes.TAILLE_TUILE - y) < 1)
                return animal;
        }
        return null;
    }

    public Coffre getCoffre(int x, int y) {
        for (Coffre coffre : listeCoffres)
            if (coffre.getX() == x && coffre.getY() == y)
                return coffre;
        return null;
    }

    public void update() {
        for(int i = 0; i < this.listeEntites.size(); i++)
            this.listeEntites.get(i).update();

        for (int i = 0; i < listeEnnemis.size(); i++)
            listeEnnemis.get(i).update();

        for (int i = 0; i < listeAnimaux.size(); i++)
            listeAnimaux.get(i).update();

        for (int i = 0; i < listeAllies.size(); i++)
            listeAllies.get(i).update();

        for (int i = 0; i < listeProjectiles.size(); i++)
            listeProjectiles.get(i).update();
    }

    //region Getter & Setter


    public ObservableList<Entite> getListeEntites() {
        return listeEntites;
    }

    public ObservableList<Materiau> getListeMateriaux() {
        return listeMateriaux;
    }

    public ObservableList<Arbre> getListeArbres() {
        return listeArbres;
    }

    public ObservableList<Coffre> getListeCoffres() {
        return listeCoffres;
    }

    public ObservableList<Ennemi> getListeEnnemis() {
        return listeEnnemis;
    }

    public ObservableList<Animal> getListeAnimaux() {
        return listeAnimaux;
    }

    public ObservableList<Allie> getListeAllies() {
        return listeAllies;
    }

    public ObservableList<Projectile> getListeProjectiles() {
        return listeProjectiles;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public MapJeu getMapJeu() {
        return mapJeu;
    }

    public Etabli getEtabli() {
        return etabli;
    }

    public FeuDeCamp getFeuDeCamp() {
        return feuDeCamp;
    }

    public final boolean getPause() {
        return pauseProperty.getValue();
    }

    //endregion

}
