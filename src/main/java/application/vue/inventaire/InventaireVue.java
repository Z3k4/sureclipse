package application.vue.inventaire;

import application.controleur.InventaireControleur;
import application.modele.ObjetInventaire;
import application.modele.armes.Arme;
import application.modele.armes.Armure;
import application.modele.armes.Hache;
import application.vue.ChargeurRessources;
import application.modele.Inventaire;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
//import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;

import static application.modele.Inventaire.PLACE_INVENTAIRE;
import static application.modele.Inventaire.PLACE_MAIN_PERSONNAGE;

public class InventaireVue {
    private Inventaire inv;

    private InvItem objPrit;
    private boolean affiche = false;

    private Pane paneSacInventaire;
    private Pane paneInventaireMain;
    private Pane inventaireArmure;

    private InventaireControleur controleur;
    public final static int TAILLE_ICON_INVENTAIRE = 32;
    public final static int DECALAGE_CASE_INVENTAIRE = 3;

    //private AudioClip sound = new AudioClip(getClass().getResource("/application/sons/ui_menu_button_click_24.mp3").toExternalForm());

    public InventaireVue(Inventaire inv, InventaireControleur controleur, Pane paneInventaireMain, Pane paneSacInventaire, Pane inventaireArmure) {
        this.inv = inv;
        this.controleur = controleur;

        this.paneInventaireMain = paneInventaireMain;
        this.paneSacInventaire = paneSacInventaire;
        this.inventaireArmure = inventaireArmure;

        this.paneSacInventaire.setLayoutY(52);

        this.paneInventaireMain.setPrefWidth(TAILLE_ICON_INVENTAIRE * PLACE_MAIN_PERSONNAGE);
        this.paneInventaireMain.setPrefHeight(TAILLE_ICON_INVENTAIRE);

        this.paneSacInventaire.setVisible(false);


        Rectangle rectangleSelection = new Rectangle();
        rectangleSelection.setWidth(TAILLE_ICON_INVENTAIRE);
        rectangleSelection.setHeight(TAILLE_ICON_INVENTAIRE);
        rectangleSelection.setFill(Color.TRANSPARENT);
        rectangleSelection.setStroke(Color.WHITE);
        rectangleSelection.strokeWidthProperty().setValue(6);

        rectangleSelection.layoutXProperty().bind(this.inv.getArmeIndexProperty().multiply(TAILLE_ICON_INVENTAIRE)
                .add(this.inv.getArmeIndexProperty().multiply(DECALAGE_CASE_INVENTAIRE)));

        this.paneInventaireMain.getChildren().add(rectangleSelection);

        this.ajouterListeObjetDansLaMain();
        this.ajouterListeObjetsSac();




    }

    //On va récupérer l'élément le plus proche dans le pane par rapport à l'objet déplacer
    public int regarderDansPane(Pane paneChoisi) {
        boolean trouver = false;
        float minDist = 0;
        int index = 0;
        int indexConteneurTrouve = -1;

        while(!trouver && index < paneChoisi.getChildren().size()) {
            if(paneChoisi.getChildren().get(index) instanceof InvSlot) {
                InvSlot img = (InvSlot) paneChoisi.getChildren().get(index);
                InvSlot parent = (InvSlot)this.objPrit.getParent();
                float distanceX = (float) Math.abs(img.getLayoutX() - this.objPrit.getLayoutX() - parent.getLayoutX());
                float distanceY = (float) Math.abs(img.getLayoutY() - this.objPrit.getLayoutY() - parent.getLayoutY());


                float totalDist = distanceX + distanceY;
                if(minDist == 0 || totalDist < minDist) {
                    minDist = totalDist;
                    indexConteneurTrouve = index;
                }
            }
            index++;
        }
        return indexConteneurTrouve;
    }


    public void lacherObjet(double positionYSouris) {
        if(this.paneSacInventaire != null) {

            InvSlot seletecSlot;

            int indexConteneurTrouve = -1;

            InvSlot slotParent = (InvSlot) this.objPrit.getParent();
            if(positionYSouris < TAILLE_ICON_INVENTAIRE) {
                seletecSlot = (InvSlot) this.paneInventaireMain.getChildren().get(regarderDansPane(this.paneInventaireMain));
            } else {
                seletecSlot = (InvSlot) this.paneSacInventaire.getChildren().get(regarderDansPane(this.paneSacInventaire));

            }

            if(seletecSlot != slotParent) {

                //On vérifie si il y a un objet ou non dans la case la plus proche, si c'est le cas, on interverti les deux objets
                if (seletecSlot.getChildren().size() > 1) {
                    //Code pour échanger deux items
                    int autrePlace = this.paneSacInventaire.getChildren().indexOf(slotParent);

                    InvItem selectSlotItem = (InvItem) seletecSlot.getChildren().get(1);
                    seletecSlot.getChildren().remove(selectSlotItem);
                    slotParent.getChildren().remove(this.objPrit);

                    seletecSlot.getChildren().add(this.objPrit);
                    slotParent.getChildren().add(selectSlotItem);

                    this.controleur.echangerObjet(this.objPrit, selectSlotItem, indexConteneurTrouve, autrePlace);

                } else {
                    this.controleur.objetPlaceInventaireChanger(objPrit, slotParent.getIndex(), seletecSlot.getIndex());

                    slotParent.getChildren().remove(this.objPrit);
                    seletecSlot.getChildren().add(this.objPrit);

                    //On calcul la place en prenant en sachant que ça fait + 1 après avoir placé l'imageview et l'objet à affiché
                }

                //On baisse le son de l'audio
                /*sound.setVolume(1. / 30.);
                sound.play();*/
            }
            this.objPrit.setLayoutX(0);
            this.objPrit.setLayoutY(0);
            this.objPrit = null;
        }
    }

    public void enleverObjetAffichage(int indexObjetInventaire) {
        if(indexObjetInventaire < 5)
            ((Pane) this.paneInventaireMain.lookup("#slot"+indexObjetInventaire)).getChildren().remove(1);
        else
            ((Pane) this.paneSacInventaire.lookup("#slot"+indexObjetInventaire)).getChildren().remove(1);
    }

    public void ajouterUnObjet(ObjetInventaire obj) {
        InvSlot slot;

        if(obj.getPlaceInventaire() < 5) {

            slot = (InvSlot) this.paneInventaireMain.lookup("#slot"+obj.getPlaceInventaire());
        } else {

            slot = (InvSlot) this.paneSacInventaire.lookup("#slot"+(obj.getPlaceInventaire()));
        }



        InvItem item = new InvItem(this, obj, TAILLE_ICON_INVENTAIRE);
        item.setPrefWidth(TAILLE_ICON_INVENTAIRE);
        item.setPrefHeight(TAILLE_ICON_INVENTAIRE);

        slot.getChildren().add(item);

    }


    public void ajouterListeObjetsSac() {
        ColorInput color = new ColorInput();
        color.setPaint(Color.RED);

        int indexItem = 5;
        for(int i = 0; i < PLACE_INVENTAIRE / 10; i++) {
            for(int j =0; j < 10; j++) {
                InvSlot invSlot = new InvSlot(ChargeurRessources.iconObjets.get("inventaireSac"));
                invSlot.setId("slot"+((i * 10) + j + PLACE_MAIN_PERSONNAGE));
                invSlot.setSize(TAILLE_ICON_INVENTAIRE,TAILLE_ICON_INVENTAIRE);
                invSlot.setLayoutX(TAILLE_ICON_INVENTAIRE * j + (j * DECALAGE_CASE_INVENTAIRE));
                invSlot.setLayoutY(TAILLE_ICON_INVENTAIRE * i);

                invSlot.setIndex(PLACE_MAIN_PERSONNAGE + (i *10) + j);

                //Ajouter un autre conteneur pour les items
                this.paneSacInventaire.getChildren().add(invSlot);


                //On vérifie que l'index ne dépasse pas le nombre d'objets actuellement portés
                if (indexItem < inv.getObjets().size()) {
                    InvItem slot = new InvItem(this, inv.getObjets().get(indexItem), 0);
                    slot.setPrefWidth(TAILLE_ICON_INVENTAIRE);
                    slot.setPrefHeight(TAILLE_ICON_INVENTAIRE);

                    invSlot.getChildren().add(slot);
                    indexItem++;
                }
            }
        }

    }

    public void ajouterListeObjetDansLaMain() {

        int indexItem = 0;

        for(int j =0; j < PLACE_MAIN_PERSONNAGE; j++) {
            InvSlot invSlot = new InvSlot(ChargeurRessources.iconObjets.get("inventaireMain"), true);
            invSlot.setId("slot"+j);
            invSlot.setIndex(j);
            invSlot.setSize(TAILLE_ICON_INVENTAIRE,TAILLE_ICON_INVENTAIRE);
            invSlot.setLayoutX(TAILLE_ICON_INVENTAIRE * j + (j * 3));

            //Ajouter un autre conteneur pour les items
            this.paneInventaireMain.getChildren().add(invSlot);

            //On vérifie que l'index ne dépasse pas le nombre d'objets actuellement portés
            if (indexItem < inv.getObjets().size()) {

                InvItem slot = new InvItem(this, inv.getObjets().get(indexItem), TAILLE_ICON_INVENTAIRE);
                slot.setPrefWidth(TAILLE_ICON_INVENTAIRE);
                slot.setPrefHeight(TAILLE_ICON_INVENTAIRE);

                invSlot.getChildren().add(slot);
                indexItem++;
            }
        }

    }

    public void mettreEquipement(ObjetInventaire obj) {
        String emplacement;

        if(obj.getEntite() instanceof Arme) {
            emplacement = "#armeEquipement";

        } else {
            emplacement = "#armureEmplacement";
        }

        ImageView img = (ImageView) this.inventaireArmure.lookup(emplacement);

        if (obj.getEntite().getClass().getSuperclass().getSimpleName().equals("Arme"))
            img.setImage(ChargeurRessources.iconObjets.get(obj.getEntite().getClass().getSimpleName() + ((Arme) obj.getEntite()).getQualite()));
        else if (obj.getEntite().getClass().getSimpleName().equals("Armure"))
            img.setImage(ChargeurRessources.iconObjets.get(obj.getEntite().getClass().getSimpleName() + ((Armure) obj.getEntite()).getQualite()));
    }

    public void enleverEquipement(String type) {
        if(type.equals("armure")) {
            ImageView img = (ImageView) this.inventaireArmure.lookup("#armureEmplacement");
            img.setImage(null);
        } else {
            ImageView img = (ImageView) this.inventaireArmure.lookup("#armeEquipement");
            img.setImage(null);
        }

    }


    public void afficherInventaire() {
        this.paneSacInventaire.setVisible(!this.paneSacInventaire.isVisible());
        this.inventaireArmure.setVisible(!this.inventaireArmure.isVisible());

    }

    public void retirerObjetAffichage(ObjetInventaire obj) {
        for(int i = 0; i < this.paneSacInventaire.getChildren().size(); i++) {
            InvSlot node = (InvSlot) this.paneSacInventaire.getChildren().get(i);

            if(node.getChildren().size() == 2) {
                InvItem invItem = (InvItem) node.getChildren().get(1);
                if(invItem.getObjetInventaire() == obj) {
                    node.getChildren().remove(invItem);
                }
            }

        }
    }



    public void jeterObjetInventaire(InvItem item) {
        //Faire en sorte d'équiper quand c'est une armure
        //this.inv.mettreEquipement(item.getObjetInventaire());
        //this.controleur.jeterObjet(item);
    }

    public void definirObjetPrit(InvItem obj) {
        this.objPrit = obj;
    }

    public Inventaire getInv() {
        return inv;
    }
}
