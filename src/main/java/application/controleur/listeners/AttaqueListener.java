package application.controleur.listeners;

import application.vue.ArmeVue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class AttaqueListener implements ChangeListener<Boolean> {

    private ArmeVue armeVue;

    public AttaqueListener(ArmeVue armeVue) {
        this.armeVue = armeVue;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
        if (t1)
            armeVue.rendreVisible();
        else
            armeVue.animationFrappe();
    }
}
