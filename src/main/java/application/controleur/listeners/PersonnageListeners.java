package application.controleur.listeners;

import application.modele.personnages.Joueur;
import application.modele.personnages.Personnage;
import application.modele.personnages.ennemi.Tyran;
import application.vue.ArmeVue;
import application.vue.FeuDeCampVue;
import application.vue.FinDeJeuVue;
import application.vue.PersonnageVue;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class PersonnageListeners {

    public PersonnageListeners(Personnage perso, PersonnageVue persoVue, ArmeVue armeVue, FeuDeCampVue feuDeCampVue) {
        perso.getXProperty().addListener((observableValue, number, t1) -> {
            if (((Joueur) perso).getAvance())
                persoVue.animerDeplacement();
        });

        ((Joueur) perso).getMortProperty().addListener(((observableValue, aBoolean, t1) -> {
            if (t1)
                feuDeCampVue.transition(true);
        }));

        ((Joueur) perso).getSeReposeProperty().addListener(((observableValue, aBoolean, t1) -> {
            if (t1)
                feuDeCampVue.transition(false);
        }));

        //si le joueur n'avance plus pour mettre le sprite du personnage immobile
        ((Joueur) perso).getAvanceProperty().addListener((observableValue, aBoolean, t1) -> {if (!t1)
            persoVue.immobile();
        });

        perso.getDirectionProperty().addListener((observableValue, o, t1) ->  {
            persoVue.inverserSprite();
            armeVue.inverserSprite();
        });
    }

    public PersonnageListeners(Personnage perso, PersonnageVue persoVue, ArmeVue armeVue) {
        //appel la méthode animationDeplacement à chaque fois que x change et donc que le joueur se déplace et udpate la position de son arme
        perso.getXProperty().addListener((observableValue, number, t1) -> {
            persoVue.animerDeplacement();
            armeVue.updatePositon();
        });

        perso.getYProperty().addListener((observableValue, number, t1) -> {
            armeVue.updatePositon();
        });

        //retourne le sprite du perso
        perso.getDirectionProperty().addListener((observableValue, o, t1) -> {
            persoVue.inverserSprite();
            armeVue.inverserSprite();
        });
    }


    public PersonnageListeners(Personnage perso, PersonnageVue persoVue, ArmeVue armeVue, Pane root) {
        //appel la méthode animationDeplacement à chaque fois que x change et donc que le joueur se déplace et udpate la position de son arme
        this(perso, persoVue, armeVue);
        ((Tyran) perso).getChargeProperty().addListener((observableValue, aBoolean, t1) -> {
                if (t1)
                    armeVue.rendreVisible();
                else
                    armeVue.rendreInvisible();
            });
        perso.getPVProperty().addListener((observableValue, number, t1) -> {
                if ((Integer) t1 <= 0) {
                    FinDeJeuVue finDeJeuVue = new FinDeJeuVue((Label) root.lookup("#labelFinDeJeu"));
                    finDeJeuVue.transition();
                }
        });
    }

    public PersonnageListeners(Personnage perso, PersonnageVue persoVue) {
        //appel la méthode animationDeplacement à chaque fois que x change et donc que le joueur se déplace et udpate la position de son arme
        perso.getXProperty().addListener((observableValue, number, t1) -> {
            persoVue.animerDeplacement();
        });

        //retourne le sprite du perso
        perso.getDirectionProperty().addListener((observableValue, o, t1) ->  {
            persoVue.inverserSprite();
        });
    }
}
