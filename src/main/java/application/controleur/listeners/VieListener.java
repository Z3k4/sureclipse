package application.controleur.listeners;

import application.modele.personnages.Personnage;
import application.vue.VieVue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class VieListener implements ChangeListener {

    private VieVue vieVue;
    private Personnage personnage;

    public VieListener(VieVue vievue, Personnage perso) {
        this.vieVue = vievue;
        this.personnage = perso;
    }

    @Override
    public void changed(ObservableValue observableValue, Object o, Object t1) {
        vieVue.afficherVie(personnage.getPv());
    }
}
