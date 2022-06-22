package application.vue.inventaire;

import application.modele.ObjetInventaire;
import application.modele.armes.Arme;
import application.modele.armes.Armure;
import application.vue.ChargeurRessources;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static application.vue.inventaire.InventaireVue.TAILLE_ICON_INVENTAIRE;

public class InvItem extends StackPane {
    private ColorInput color;
    private Label quantite;

    private int TAILLE_IMG_OBJET = 28;

    private ImageView imgVObjet;

    private ObjetInventaire Objet;

    private InventaireVue invVue;

    private double decalageBas;

    //Permet de vérifier si on est entrain de porter un objet
    private boolean dragActive =false;


    public InvItem(InventaireVue invVue, ObjetInventaire obj, double decalageBas) {

        this.invVue = invVue;
        this.Objet = obj;
        this.decalageBas = decalageBas;

        Image img;
        if (obj.getEntite().getClass().getSimpleName().equals("Fleche"))
            img = ChargeurRessources.iconObjets.get("Fleche1");
        else if (obj.getEntite().getClass().getSuperclass().getSimpleName().equals("Arme"))
            img = ChargeurRessources.iconObjets.get(obj.getEntite().getClass().getSimpleName() + ((Arme) obj.getEntite()).getQualite());
        else if (obj.getEntite().getClass().getSimpleName().equals("Armure"))
            img = ChargeurRessources.iconObjets.get(obj.getEntite().getClass().getSimpleName() + ((Armure) obj.getEntite()).getQualite());
        else
            img = ChargeurRessources.iconObjets.get(obj.getEntite().getClass().getSimpleName());

        this.imgVObjet = new ImageView(img);


        this.imgVObjet.setFitHeight(TAILLE_IMG_OBJET);
        this.imgVObjet.setFitWidth(TAILLE_IMG_OBJET);


        color = new ColorInput();

        color.setWidth(TAILLE_IMG_OBJET);
        color.setHeight(TAILLE_IMG_OBJET);
        color.setPaint(Color.color(0.3,0.3,.3,0.1));

        this.setOnMouseEntered(mouseEvent -> {
            mouseEntered();
        });

        this.setOnMouseExited(mouseEvent -> {
            mouseExited();
        });

        this.setOnMouseReleased(mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.PRIMARY) {
                dragActive = false;
                this.invVue.lacherObjet(mouseEvent.getSceneY());
            }
        });

        this.setOnMousePressed(mouseEvent -> {

            if(mouseEvent.getButton() == MouseButton.PRIMARY) {
                this.invVue.definirObjetPrit(this);
                dragActive = true;
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY && !dragActive) {
                this.invVue.getInv().interactionObjet(obj);
            }
        });

        this.setOnMouseDragged(mouseEvent ->
        {
            int decalage = 0;

            if(obj.getPlaceInventaire() > 5) {
                decalage = TAILLE_ICON_INVENTAIRE;
            }
            if (dragActive) {
                Parent parent = this.getParent();
                this.setLayoutX(mouseEvent.getSceneX() - parent.getLayoutX() - this.getPrefWidth() / 2);
                this.setLayoutY((mouseEvent.getSceneY() - parent.getLayoutY() - decalage - this.getPrefHeight() / 2));
            }
        });

        quantite = new Label();
        quantite.setTextFill(Color.WHITE);
        quantite.textProperty().bind(obj.getStackActuelProperty().asString());
        this.getChildren().add(this.imgVObjet);
        this.getChildren().add(quantite);

    }


    public ObjetInventaire getObjetInventaire() {
        return this.Objet;
    }
    private void mouseEntered() {
        this.imgVObjet.setEffect(color);
    }

    private void mouseExited() {
        this.imgVObjet.setEffect(null);
    }
}
