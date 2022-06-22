package application.controleur.listeners;

import application.controleur.QueteControleur;
import application.modele.ObjetInventaire;
import application.vue.inventaire.InventaireVue;
import javafx.collections.ListChangeListener;

public class InventaireListener implements ListChangeListener {

    private InventaireVue invVue;
    private QueteControleur controleurQuete;

    public InventaireListener(InventaireVue invVue, QueteControleur queteControleur) {
        this.invVue = invVue;
        this.controleurQuete = queteControleur;
    }

    @Override
    public void onChanged(Change change) {
        change.next();

        for (int i = 0; i < change.getRemovedSize(); i++) {
            invVue.enleverObjetAffichage(((ObjetInventaire) change.getRemoved().get(i)).getPlaceInventaire());
        }
        for (int i = 0; i < change.getAddedSize(); i++) {
            ObjetInventaire obj = (ObjetInventaire) change.getAddedSubList().get(i);
            invVue.ajouterUnObjet((ObjetInventaire) change.getAddedSubList().get(i));

            this.controleurQuete.objetAEteAjouteeInventaire(obj);

            //Pour chaque objet on verifie quand son nombre de stack evolue
            obj.getStackActuelProperty().addListener(changement -> {
                this.controleurQuete.objetAEteAjouteeInventaire(obj);
            });
        }
    }
}
