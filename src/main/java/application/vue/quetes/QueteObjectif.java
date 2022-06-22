package application.vue.quetes;

import application.modele.quetes.ObjectifNombreNecessaire;
import application.modele.quetes.QueteType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class QueteObjectif extends HBox {


    private IntegerProperty nombreActuel;
    private IntegerProperty nombreAFaire;

    public QueteObjectif(QueteType.TYPE_QUETE type, String nomObjet, ObjectifNombreNecessaire objectifNombreNecessaire) {
        String typeQuete = "Non défini";
        switch (type) {
            case RAMASSER:
                typeQuete = "Récupérer";
                break;
            case TUER:
                typeQuete = "Tuer";
                break;
            default:
                break;
        }


        nombreActuel = new SimpleIntegerProperty(0);
        nombreAFaire = new SimpleIntegerProperty(0);

        nombreActuel.bind(objectifNombreNecessaire.nombreActuelPropertyProperty());
        nombreAFaire.bind(objectifNombreNecessaire.nombreNecessairePropertyProperty());

        TextFlow text = new TextFlow();

        text.setPrefWidth(100);
        text.setTranslateX(10);
        text.setPadding(new Insets(5,10,5,0));
        Text descriptif = new Text();

        descriptif.setFill(Color.WHITE);
        descriptif.setText(typeQuete.concat(" ").concat(nomObjet));

        Text nbObjectif = new Text();
        nbObjectif.setTranslateY(6);
        nbObjectif.setFill(Color.WHITE);
        nbObjectif.textProperty().bind(nombreActuel.asString().concat(" / ").concat(nombreAFaire.getValue()));
        nbObjectif.setTextAlignment(TextAlignment.RIGHT);


        text.getChildren().add(descriptif);
        this.getChildren().add(text);
        this.getChildren().add(nbObjectif);
    }


}
