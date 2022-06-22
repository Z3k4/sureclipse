package application.vue;

import application.controleur.Constantes;
import application.modele.Direction;
import application.modele.projectiles.Fleche;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class FlecheVue {
    public FlecheVue(Pane panePNJ, Fleche fleche) {
        ImageView spriteFleche = new ImageView(ChargeurRessources.iconObjets.get("Fleche1"));
        spriteFleche.setId(fleche.getId());
        switch (fleche.getDirection()) {
            case Gauche -> spriteFleche.setScaleX(-1);
            case Bas -> spriteFleche.setRotate(90);
            case Haut -> spriteFleche.setRotate(-90);
        }
        spriteFleche.setFitWidth(Constantes.TAILLE_TUILE);
        spriteFleche.setFitHeight(Constantes.TAILLE_TUILE);
        spriteFleche.translateXProperty().bind(fleche.getXProperty());
        spriteFleche.translateYProperty().bind(fleche.getYProperty());
        panePNJ.getChildren().add(spriteFleche);
    }
}
