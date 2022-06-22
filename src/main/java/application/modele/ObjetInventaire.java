package application.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ObjetInventaire {

    private Inventaire inventaire;
    private Entite entite;
    private int placeInventaire;
    private IntegerProperty stackActuel;


    /**
     * On encaspule l'entitee dans une super class ObjetInventaire afin de ne pas perdre ses données
     * @param inventaire l'inventaire du personnage
     * @param ent l'entité que l'ObjetInventaire contient
     */
    public ObjetInventaire(Inventaire inventaire, Entite ent) {
        this.inventaire = inventaire;
        this.entite = ent;
        this.stackActuel = new SimpleIntegerProperty(1);

    }

    /**
     * Permet de récupérer la position de l'entité
     * @return la place actuel de l'inventaire
     */
    public int getPlaceInventaire() {
        return this.placeInventaire;
    }

    public void setPlaceInventaire(int nouvellePlace) {
        this.placeInventaire = nouvellePlace;
    }

    public IntegerProperty getStackActuelProperty() {
        return this.stackActuel;
    }

    public int getNombre() {
        return this.stackActuel.getValue();
    }

    public void ajouterDansStack() {
        this.stackActuel.setValue(this.stackActuel.getValue() + 1);
    }

    public void retirerDansStack() {
        this.stackActuel.setValue(this.stackActuel.getValue() - 1);
        if (stackActuel.getValue() == 0) {
            if (this == inventaire.getObjetMain())
                inventaire.setObjetMain(null);
            inventaire.retirerObjet(this);
        }
    }

    public Entite getEntite() {
        return this.entite;
    }
}
