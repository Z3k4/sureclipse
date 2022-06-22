package application.controleur.listeners;

import application.modele.ObjetInventaire;
import application.vue.ArmeVue;
import application.vue.inventaire.InventaireVue;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ArmureListener implements ChangeListener<ObjetInventaire> {

    private InventaireVue invVue;

    public ArmureListener(InventaireVue invVue) {
        this.invVue = invVue;
    }

    @Override
    public void changed(ObservableValue<? extends ObjetInventaire> observableValue, ObjetInventaire objetInventaire, ObjetInventaire t1) {
        if (t1 == null)
            invVue.enleverEquipement("armure");
    }
}
