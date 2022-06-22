package application.controleur;

import application.controleur.listeners.PersonnageListeners;
import application.modele.Environnement;
import application.modele.personnages.ennemi.Tyran;
import application.modele.projectiles.BouleDeFeu;
import application.modele.projectiles.Fleche;
import application.modele.objets.Arbre;
import application.modele.objets.Coffre;
import application.modele.objets.materiaux.Materiau;
import application.modele.personnages.Personnage;
import application.modele.personnages.animaux.Animal;
import application.modele.projectiles.Projectile;
import application.vue.*;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import static application.modele.MapJeu.*;

public class EnvironnementControleur {

    private ControleurJeu controleur;
    public EnvironnementControleur(Pane root, EnvironnementVue envVue, Environnement env, ControleurJeu controleur) {

        this.controleur = controleur;
        ((Pane) root.lookup("#panePNJ")).setPrefSize(WIDTH * Constantes.TAILLE_TUILE, HEIGHT * Constantes.TAILLE_TUILE);

        root.lookup("#tileSol").translateXProperty().bind(env.getJoueur().getXProperty().multiply(-1).add(((Constantes.TAILLE_TUILE * WIDTH)) / 2));
        root.lookup("#tileSol").translateYProperty().bind(env.getJoueur().getYProperty().multiply(-1).add(((Constantes.TAILLE_TUILE * HEIGHT)) / 2));


        root.lookup("#tileFondDecor").translateXProperty().bind(env.getJoueur().getXProperty().multiply(-1).add(((Constantes.TAILLE_TUILE * WIDTH)) / 2));
        root.lookup("#tileFondDecor").translateYProperty().bind(env.getJoueur().getYProperty().multiply(-1).add(((Constantes.TAILLE_TUILE * HEIGHT)) / 2));


        root.lookup("#paneDecors").translateXProperty().bind(env.getJoueur().getXProperty().multiply(-1).add(((Constantes.TAILLE_TUILE * WIDTH)) /2));
        root.lookup("#paneDecors").translateYProperty().bind(env.getJoueur().getYProperty().multiply(-1).add(((Constantes.TAILLE_TUILE * HEIGHT)) / 2));
        root.lookup("#panePNJ").translateXProperty().bind(env.getJoueur().getXProperty().multiply(-1).add(((Constantes.TAILLE_TUILE * WIDTH)) / 2));
        root.lookup("#panePNJ").translateYProperty().bind(env.getJoueur().getYProperty().multiply(-1).add(((Constantes.TAILLE_TUILE * HEIGHT)) / 2));

        env.getListeMateriaux().addListener(new ListChangeListener<Materiau>() {
            @Override
            public void onChanged(Change<? extends Materiau> change) {
                change.next();
                for (int i = 0; i < change.getRemovedSize(); i++) {
                    envVue.supprimerBloc(change.getRemoved().get(0));
                }

                for(int i = 0; i < change.getAddedSize(); i++) {
                    Materiau ent = (Materiau) change.getAddedSubList().get(i);
                    int x = (int)ent.getX() / Constantes.TAILLE_TUILE;
                    int y = (int)ent.getY() / Constantes.TAILLE_TUILE;

                    env.getMapJeu().getTabMap()[y][x] = 183;
                    envVue.ajouterBloc(ent.getId(), ent);
                }
            }
        });

        env.getListeArbres().addListener(new ListChangeListener<Arbre>() {
            @Override
            public void onChanged(Change<? extends Arbre> change) {
                change.next();
                for (int i = 0; i < change.getRemovedSize(); i++)
                    envVue.supprimerArbre((int)change.getRemoved().get(i).getY() * env.getMapJeu().getWidth() + (int)change.getRemoved().get(0).getX());
            }
        });

        env.getListeEnnemis().addListener(new ListChangeListener<Personnage>() {
            @Override
            public void onChanged(Change<? extends Personnage> change) {
                change.next();
                for (int i = 0; i < change.getRemovedSize(); i++) {
                    envVue.supprimerPNJ(change.getRemoved().get(i).getId());
                }
                for (int i = 0; i < change.getAddedSize(); i++) {
                    Personnage perso = change.getAddedSubList().get(i);
                    if (perso instanceof Tyran)
                        new PersonnageListeners(perso, new PersonnageVue((Pane) root.lookup("#panePNJ"), perso), new ArmeVue((Pane) root.lookup("#panePNJ"), perso, controleur), root);
                    else
                        new PersonnageListeners(perso, new PersonnageVue((Pane) root.lookup("#panePNJ"), perso), new ArmeVue((Pane) root.lookup("#panePNJ"), perso, controleur));
                }
            }

        });

        //On a qu'un seul villageois
        env.getListeAllies().get(0).getInteractionProperty().addListener(e -> {

            if (env.getListeAllies().get(0).getInteractionAvancement() >= 1) {
                controleur.getDialogueControleur().debutDialogue();
            }
         });

        env.getListeAnimaux().addListener(new ListChangeListener<Animal>() {
            @Override
            public void onChanged(Change<? extends Animal> change) {
                change.next();
                for (int i = 0; i < change.getRemovedSize(); i++)
                    envVue.supprimerPNJ(change.getRemoved().get(i).getId());

                for (int i = 0; i < change.getAddedSize(); i++)
                    new PersonnageListeners(change.getAddedSubList().get(i), new PersonnageVue((Pane) root.lookup("#panePNJ"), change.getAddedSubList().get(i)));
            }
        });

        env.getListeCoffres().addListener(new ListChangeListener<Coffre>() {
            @Override
            public void onChanged(Change<? extends Coffre> change) {
                change.next();
                for (int i = 0; i < change.getRemovedSize(); i++)
                    envVue.changerImgCoffre((int)change.getRemoved().get(i).getY() * WIDTH + (int)change.getRemoved().get(i).getX());
            }
        });

        env.getListeProjectiles().addListener(new ListChangeListener<Projectile>() {
            @Override
            public void onChanged(Change<? extends Projectile> change) {
                change.next();
                for (int i = 0; i < change.getAddedSize(); i++)
                    if (change.getAddedSubList().get(i) instanceof Fleche)
                        new FlecheVue((Pane) root.lookup("#panePNJ"), (Fleche) change.getAddedSubList().get(i));
                    else {
                        BouleDeFeuVue bouleDeFeuVue = new BouleDeFeuVue((Pane) root.lookup("#panePNJ"), (BouleDeFeu) change.getAddedSubList().get(i));
                        ((BouleDeFeu) change.getAddedSubList().get(i)).getChuteProperty().addListener(observable -> bouleDeFeuVue.chute());
                    }
                for (int i = 0; i < change.getRemovedSize(); i++)
                    envVue.supprimerProjectile(change.getRemoved().get(i).getId());
            }
        });
    }
}
