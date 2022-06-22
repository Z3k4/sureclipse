package application.controleur;

import application.controleur.listeners.InventaireListener;
import application.modele.Inventaire;
import application.modele.Environnement;
import application.vue.inventaire.InventaireVue;
import application.vue.inventaire.InvItem;
import javafx.event.Event;
import javafx.event.EventHandler;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

public class InventaireControleur implements EventHandler<Event> {

    private Pane root;
    private Environnement jeu;
    private Inventaire inv;
    private InventaireVue invVue;

    public InventaireControleur(Pane root, QueteControleur controleurQuete, Environnement jeu, Pane inventaireMain, Pane inventaireSac, Pane inventaireEquip) {
        this.root = root;
        this.jeu = jeu;

        this.inv = this.jeu.getJoueur().getInventaire();

        this.invVue = new InventaireVue(inv, this, inventaireMain, inventaireSac, inventaireEquip);

        //this.inv.getObjets().addListener(new ObjetInventaireChanger(this, controleurQuete));
        this.inv.getObjets().addListener(new InventaireListener(invVue, controleurQuete));

        inventaireEquip.getChildren().get(0).setPickOnBounds(true);
        inventaireEquip.getChildren().get(0).setOnMouseClicked(mouseEvent -> {
            inv.desequiperArmure();
        });

        inventaireEquip.getChildren().get(1).setOnMouseClicked(mouseEvent -> {
            inv.desequiperArme();
        });

    }

    /**
     * Permet de savoir quand on appuie sur une touche, au quel cas on vérifie si ça correspond à une voulue
     * @param keyvent
     */
    public void gererEntreeClavier(KeyEvent keyvent) {
        if(keyvent.getCode() == KeyCode.I) {
            this.invVue.afficherInventaire();
        }
    }


    /**
     * Permet de savoir quand on a scroll vers le haut ou vers le bas, si c'est le cas, on change l'index de l'objet équipé
     * @param scrollEvent
     */
    public void gererEntreeSouris(ScrollEvent scrollEvent) {
        if(scrollEvent.getDeltaY() > 0) {
            this.inv.scrollObjetMain(-1);
        } else if(scrollEvent.getDeltaY() < 0) {
            this.inv.scrollObjetMain(+1);

        }
    }

    @Override
    public void handle(Event event) {
        if(event.getEventType() == KeyEvent.KEY_PRESSED) {
            gererEntreeClavier((KeyEvent) event);
        } else if(event.getEventType() == ScrollEvent.SCROLL) {
            gererEntreeSouris((ScrollEvent) event);
        }
    }


    /**
     * Permet de changer la place d'un objet quand le deplace dans avec l'UI
     * @param objetInventaire l'objet actuel
     * @param anciennePlace l'ancienne place dans l'inventaire
     * @param nouvellePlace la nouvelle place une fois le déplacement fait
     */
    public void objetPlaceInventaireChanger(InvItem objetInventaire, int anciennePlace, int nouvellePlace) {
        this.inv.libererPlacePrise(anciennePlace);
        this.inv.definirPlacePrise(nouvellePlace);

        objetInventaire.getObjetInventaire().setPlaceInventaire(nouvellePlace);
    }

    /**
     * Permet de drop un objet
     * @param vueObjet la case avec l'objet correspondant
     */
    public void jeterObjet(InvItem vueObjet) {
        this.inv.lacherObjet(vueObjet.getObjetInventaire());

    }


    /**
     * Permet d'échanger la place entre deux objets
     * @param premier Premier objet
     * @param second Second objet
     * @param nouvPlacePrem nouvelle place dans l'inventaire du premier objet
     * @param nouvPlaceSec nouvelle place dans l'inventaire du second objet
     */
    public void echangerObjet(InvItem premier, InvItem second, int nouvPlacePrem, int nouvPlaceSec) {
        int placeEchange = second.getObjetInventaire().getPlaceInventaire();


        second.getObjetInventaire().setPlaceInventaire(premier.getObjetInventaire().getPlaceInventaire());
        premier.getObjetInventaire().setPlaceInventaire(placeEchange);


    }

    /**
     * Permet de récupérer la vue associé à l'inventaire
     * @return
     */
    public InventaireVue getInvVue() {
        return invVue;
    }
}
