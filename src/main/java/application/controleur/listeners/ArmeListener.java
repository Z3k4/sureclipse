package application.controleur.listeners;

import application.modele.ObjetInventaire;
import application.vue.ArmeVue;
import application.vue.inventaire.InventaireVue;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ArmeListener implements ChangeListener<ObjetInventaire> {

    private ArmeVue armeVue;
    private InventaireVue invVue;

    public ArmeListener(ArmeVue armeVue, InventaireVue invVue) {
        this.armeVue = armeVue;
        this.invVue = invVue;
    }

    @Override
    public void changed(ObservableValue<? extends ObjetInventaire> observableValue, ObjetInventaire objetInventaire, ObjetInventaire t1) {
        if (t1 != null) {
            armeVue.changementArme();
            invVue.mettreEquipement(t1);
        }
        else {
            armeVue.retirer();
            invVue.enleverEquipement("arme");
        }
    }
}
