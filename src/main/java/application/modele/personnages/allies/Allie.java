package application.modele.personnages.allies;

import application.modele.Environnement;
import application.modele.personnages.PNJ;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Allie extends PNJ {

    private IntegerProperty interactionAvancementProperty;
    public Allie(Environnement env, String id, int x, int y, int distance) {

        super(env, id, x, y, distance);

        //0 Le joueur n'a pas intéragit, 1 il vient d'intéragir à l'instant, 2 il a déjà intéragit

        interactionAvancementProperty = new SimpleIntegerProperty(0);
    }

    public void ajouterStatutInteragir() {
        this.interactionAvancementProperty.setValue(this.interactionAvancementProperty.getValue() + 1);
    }

    public IntegerProperty getInteractionProperty() {
        return this.interactionAvancementProperty;
    }

    public int getInteractionAvancement() {
        return this.interactionAvancementProperty.getValue();
    }
}
