package application.controleur;

import application.modele.Environnement;
import application.vue.EtabliVue;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class EtabliControleur {

    public EtabliControleur(Pane root, Environnement env, EtabliVue etabliVue) {
        VBox vBoxObjets = (VBox) ((ScrollPane) etabliVue.getbPaneEtabli().lookup("#sPObjets")).getContent();
        Button boutonFabriquer = (Button) etabliVue.getbPaneEtabli().lookup("#VboxFabriquer").lookup("#boutonFabriquer");

        //pour empêcher le joueur de fabriquer
        boutonFabriquer.setDisable(true);
        etabliVue.affichageBouton(0.5);

        //afficher ou cacher l'établi quand le joueur clique sur l'établi
        env.getEtabli().getOuvertProperty().addListener((observableValue, aBoolean, t1) -> {
            if (!t1)
                root.requestFocus();
            etabliVue.affichageEtabli();
        });

        //pour afficher les infos de l'etabli lorsque cliquée
        vBoxObjets.getChildren().get(0).setOnMouseClicked(mouseEvent -> {
            etabliVue.affichageObjetSelected(Color.BLACK);
            env.getEtabli().setObjetSelected(((HBox)mouseEvent.getSource()).getId());
            etabliVue.affichageObjetSelected(Color.WHITE);
            if (env.getEtabli().getNiveau() < 3)
                etabliVue.affichageInfosObjetSelected();
            env.getEtabli().peutFabriquer();
        });

        //pour afficher les infos d'une arme lorsque cliquée
        for (int i = 1; i < vBoxObjets.getChildren().size(); i++) {
            vBoxObjets.getChildren().get(i).setOnMouseClicked(mouseEvent -> {
                etabliVue.affichageObjetSelected(Color.BLACK);
                env.getEtabli().setObjetSelected(((HBox)mouseEvent.getSource()).getId());
                etabliVue.affichageObjetSelected(Color.WHITE);
                etabliVue.affichageInfosObjetSelected();
                env.getEtabli().peutFabriquer();
            });
        }

        //pour lancer la fabrication
        boutonFabriquer.setOnAction(actionEvent -> {
            env.getEtabli().fabriquer();
            if (env.getEtabli().getObjetSelected().equals("Etabli"))
                if (env.getEtabli().getNiveau() < 3)
                    etabliVue.affichageInfosObjetSelected();
                else
                    ((ScrollPane) etabliVue.getbPaneEtabli().lookup("#sPObjets")).getContent().lookup("#Etabli").setOpacity(0.5);
            root.requestFocus();
        });

        //pour fermer l'etabli quand cliqué sur la croix
        ((Button) etabliVue.getbPaneEtabli().lookup("#boutonFermer")).setOnAction(actionEvent -> {
            env.getEtabli().interagir();
        });

        //retire l'opacité des hboxs des armes dispo
        env.getEtabli().getNiveauProperty().addListener(((observableValue, number, t1) -> etabliVue.amelioration()));

        //rend le bouton fabriquer utilisable ou non si l'arme selectionné est fabricable
        env.getEtabli().getFabricableProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1) {
                boutonFabriquer.setDisable(false);
                etabliVue.affichageBouton(1);
            } else {
                boutonFabriquer.setDisable(true);
                etabliVue.affichageBouton(0.5);
            }
        });

        //simule un clique pour l'initialisation
        vBoxObjets.lookup("#Etabli").fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null));
    }
}
