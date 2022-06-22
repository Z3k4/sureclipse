package application.modele.quetes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ObjectifNombreNecessaire {
    private IntegerProperty nombreNecessaireProperty;
    private IntegerProperty nombreActuelProperty;

    public ObjectifNombreNecessaire(int nombreNecessaire) {
        this.nombreNecessaireProperty = new SimpleIntegerProperty(nombreNecessaire);
        this.nombreActuelProperty = new SimpleIntegerProperty(0);
    }

    public int getNombreNecessaireProperty() {
        return nombreNecessaireProperty.getValue();
    }

    public IntegerProperty nombreNecessairePropertyProperty() {
        return nombreNecessaireProperty;
    }

    public void setNombreNecessaireProperty(int nombreNecessaireProperty) {
        this.nombreNecessaireProperty.setValue(nombreNecessaireProperty);
    }

    public IntegerProperty getNombreActuelProperty() {
        return nombreActuelProperty;
    }

    public int getNombreActuel() {
        return nombreActuelProperty.getValue();
    }

    public int getNombreNecessaire() {
        return this.nombreNecessaireProperty.getValue();
    }


    public IntegerProperty nombreActuelPropertyProperty() {
        return nombreActuelProperty;
    }

    public void setNombreActuelProperty(int nombreActuelProperty) {
        this.nombreActuelProperty.setValue(nombreActuelProperty);
    }

    public void ajouterObjectif() {
        this.nombreActuelProperty.setValue(this.nombreActuelProperty.getValue() + 1);
    }

    public void retirerObjectif() {
        this.nombreActuelProperty.setValue(this.nombreActuelProperty.getValue() - 1);
    }


    public void retirerNombreObjectif(int nombre) {
        this.nombreActuelProperty.setValue(this.nombreActuelProperty.getValue() - nombre);
    }

    public void ajouterNombreObjectif(int nombre) {
        this.nombreActuelProperty.setValue(this.nombreActuelProperty.getValue() + nombre);
    }

}
