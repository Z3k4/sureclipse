package application.modele.personnages;

import application.controleur.Constantes;
import application.modele.*;
import application.modele.armes.*;
import application.modele.objets.Arbre;
import application.modele.objets.Cle;
import application.modele.objets.Coffre;
import application.modele.objets.consommable.Consommable;
import application.modele.objets.materiaux.*;
import application.modele.personnages.allies.Allie;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class Joueur extends Personnage {

    public final static int PV_MAX = 10;

    private Inventaire inventaire;
    private boolean freeze;
    private BooleanProperty mortProperty;
    private BooleanProperty seReposeProperty;
    private long delai;
    private BooleanProperty avanceProperty;

    public Joueur(Environnement env) {
        super(env);
        this.inventaire = new Inventaire(super.getEnv());
        this.inventaire.ajouterObjet(new Pioche(getEnv(), 1));
        this.inventaire.ajouterObjet(new Hache(getEnv(), 1));
        mortProperty = new SimpleBooleanProperty(false);
        seReposeProperty = new SimpleBooleanProperty(false);
        avanceProperty = new SimpleBooleanProperty(false);
        freeze = false; delai = 0;

        this.setY(21 * 32);
        this.setX(15 * 32);

    }

    public boolean interagit(int x, int y) {
        return (interactionVillageois() || interactionFeuDeCamp(x,y) || interactionEtabli(x, y) || poserBloc(x, y) || (this.inventaire.getArme() != null && (frapper(x, y) || miner(x, y) || couper(x, y))) || ouvrirCoffre(x, y));
    }

    //Pour l'instant on se contente d'une fonction simple étant donné qu'il n'y a qu'un seul villageois
    private boolean interactionVillageois() {
        Allie chefvillage = getEnv().getListeAllies().get(0);
        double distance = Math.abs(chefvillage.getX() - this.getX()) + Math.abs(chefvillage.getY() - this.getY());
        if(distance <= 32) {
            chefvillage.ajouterStatutInteragir();
            return true;
        }

        return false;
    }

    private boolean interactionFeuDeCamp(int x, int y) {
        if (x == getEnv().getFeuDeCamp().getX() && y == getEnv().getFeuDeCamp().getY()) {
            if (!seReposeProperty.getValue()) {
                seReposeProperty.setValue(true);
                delai = System.currentTimeMillis();
            }
            return true;
        }
        return false;
    }

    private boolean interactionEtabli(int x, int y) {
        if (x == getEnv().getEtabli().getX() && y == getEnv().getEtabli().getY()) {
            getEnv().getEtabli().interagir();
            return true;
        }
        return false;
    }

    public boolean poserBloc(int x, int y) {
        ObjetInventaire objetEquipe = this.getInventaire().getObjetMain();
        if(objetEquipe != null && !(objetEquipe.getEntite() instanceof Arme)
                && (x != (int) getX()/Constantes.TAILLE_TUILE || y != (int) getY()/ Constantes.TAILLE_TUILE)) {
            try {
                if (x > (int) getX()/Constantes.TAILLE_TUILE && Math.abs(x - getX()/Constantes.TAILLE_TUILE) < 0.8)
                    x++;
                if (y > (int) getY()/Constantes.TAILLE_TUILE && Math.abs(y - getY()/Constantes.TAILLE_TUILE ) < 0.8)
                    y++;
                if (getEnv().getMinerai(x,y) == null) {
                    Materiau materiau;
                    switch (objetEquipe.getEntite().getClass().getSimpleName()) {
                        case "Pierre": materiau = new Pierre(this.getEnv(), x, y); break;
                        case "Fer": materiau = new Fer(this.getEnv(), x, y); break;
                        case "Platine": materiau = new Platine(this.getEnv(), x, y); break;
                        case "Terre": materiau = new Terre(this.getEnv(), x, y); break;
                        case "Bois": materiau = new Bois(this.getEnv(), x, y); break;
                        default: materiau = null; break;
                    }

                    objetEquipe.retirerDansStack();
                    this.getEnv().getListeMateriaux().add(materiau);
                }
            } catch(Exception exception) {
                exception.printStackTrace();
            }
        }

        return false;
    }

    private boolean ouvrirCoffre(int x, int y){
        Coffre coffre = getEnv().getCoffre(x, y);
        if (coffre != null) {
            coffre.ouvrir();
            return  true;
        }
        return false;
    }

    private boolean frapper(int x, int y) {
        if (getArme() instanceof Arc) {
            ((Arc) getArme()).frapper(x,y);
            return true;
        } else {
            PNJ pnj = getEnv().getEnnemi(x, y);
            if (pnj == null)
                pnj = getEnv().getAnimal(x, y);
            if (pnj != null) {
                getArme().frapper(this, pnj);
                return true;
            }
            return false;
        }
    }

    private boolean couper(int x, int y) {
        Arbre arbre = getEnv().getArbre(x, y);
        if (arbre != null) {
            arbre.estFrappe();
            return true;
        }
        return false;
    }

    private boolean miner(int x, int y) {
        Materiau minerai = getEnv().getMinerai(x, y);
        if (minerai != null) {
            minerai.estFrappe();
            return true;
        }
        return false;
    }

    @Override
    public void quandCollisionDetectee(Entite ent) {
        if (ent instanceof ObjetJeu || ent instanceof Materiau || ent instanceof Consommable || ent instanceof Cle) {
            this.inventaire.ajouterObjet(ent);
        }
    }

    @Override
    public void update() {
        super.collide();
        if (mortProperty.getValue())
            mourir();
        else if (seReposeProperty.getValue())
            seReposer();
        else if (getDistancePoussee() != 0)
            estPoussee();
        else {
            if (!freeze) {
                if (getSaute()) sauter();
                if (getAvance()) seDeplacer();
            }
            if (!getSaute()) tomber();
        }
    }

    public void decrementerPv(int degat) {
        int degatSubit;
        if (inventaire.getArmure() == null)
            degatSubit = degat;
        else if (degat <= inventaire.getArmure().defendre()) {
            degatSubit = 0;
            inventaire.getArmure().decrementerPv(degat);
        } else {
            degatSubit = degat - inventaire.getArmure().defendre();
            inventaire.getArmure().decrementerPv(inventaire.getArmure().defendre());
        }
        if (getPv() - degatSubit < 0)
            setPv(0);
        else
            setPv(getPv() - degatSubit);
        if (getPv() <= 0)
            detruire();
    }

    //attend l'ecran noir avant d'executer pour pas voir les changements avant
    private void seReposer() {
        if (System.currentTimeMillis() - delai >= 5_000) {
            seReposeProperty.setValue(false);
        } else if (System.currentTimeMillis() - delai >= 2_000 && getX() + Constantes.TAILLE_TUILE != getEnv().getFeuDeCamp().getX() * Constantes.TAILLE_TUILE) {
            setSaute(false); setAvance(false); setDistancePoussee(0);
            getEnv().getFeuDeCamp().seReposer();
        }
    }

    private void mourir() {
        if (System.currentTimeMillis() - delai >= 5_000) {
            mortProperty.setValue(false);
        } else if (System.currentTimeMillis() - delai >= 2_000 && getX() + Constantes.TAILLE_TUILE != getEnv().getFeuDeCamp().getX() * Constantes.TAILLE_TUILE) {
            setSaute(false); setAvance(false); setDistancePoussee(0);
//            if (getArme() != null) getArme().detruire();
//            if (inventaire.getArmure() != null) inventaire.getArmure().detruire();

            for (int i = inventaire.getObjets().size() - 1; i >= 0; i--)
                if (inventaire.getObjets().get(i).getEntite() instanceof Materiau)
                    inventaire.retirerObjet(inventaire.getObjets().get(i));

            int i = 0;
            while (i < inventaire.getObjets().size() && !(inventaire.getObjets().get(i).getEntite() instanceof Arme)) i++;

            if (i == inventaire.getObjets().size()) {
                this.inventaire.ajouterObjet(new Pioche(getEnv(), 1));
                this.inventaire.ajouterObjet(new Hache(getEnv(), 1));
            }
            getEnv().getFeuDeCamp().seReposer();
        }
    }

    @Override
    public void detruire() {
        mortProperty.setValue(true);
        delai = System.currentTimeMillis();
    }

    public void freezer() {
        freeze = !freeze;
    }

    @Override
    protected int getHauteurMax() {
        return (int) (2.5 * Constantes.TAILLE_TUILE);
    }

    @Override
    protected int getVitesse() {
        return 3;
    }

    public Inventaire getInventaire() {
        return this.inventaire;
    }

    public final boolean getMort() {
        return mortProperty.getValue();
    }

    public final BooleanProperty getMortProperty() {
        return mortProperty;
    }

    public final boolean getAvance() {
        return avanceProperty.getValue();
    }

    public final BooleanProperty getAvanceProperty() {
        return avanceProperty;
    }

    public void setAvance(boolean avance) {
        this.avanceProperty.setValue(avance);
    }

    @Override
    public Arme getArme() {
        return inventaire.getArme();
    }

    public final boolean getSeRepose() {
        return seReposeProperty.getValue();
    }

    public final BooleanProperty getSeReposeProperty() {
        return seReposeProperty;
    }
}
