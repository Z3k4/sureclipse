package application.vue;

import application.controleur.Constantes;
import application.modele.Direction;
import application.modele.personnages.Joueur;
import application.modele.personnages.Personnage;
import application.modele.MapJeu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;


public class PersonnageVue {

    private final static HashMap<String,ArrayList<Image>> LISTE_SPRITES = new HashMap<>() {{
        put("Joueur", new ArrayList<>() {{
            add(new Image("file:src/main/resources/application/perso/joueur/perso_immobile.png"));
            add(new Image("file:src/main/resources/application/perso/joueur/perso_mouvement1.png"));
            add(new Image("file:src/main/resources/application/perso/joueur/perso_immobile.png"));
            add(new Image("file:src/main/resources/application/perso/joueur/perso_mouvement2.png"));
        }});
        put("Epeiste", new ArrayList<>() {{
            add(new Image("file:src/main/resources/application/perso/ennemi/epeiste/epeiste_immobile.png"));
            add(new Image("file:src/main/resources/application/perso/ennemi/epeiste/epeiste_mouvement1.png"));
            add(new Image("file:src/main/resources/application/perso/ennemi/epeiste/epeiste_immobile.png"));
            add(new Image("file:src/main/resources/application/perso/ennemi/epeiste/epeiste_mouvement2.png"));
        }});
        put("Lancier", new ArrayList<>() {{
            add(new Image("file:src/main/resources/application/perso/ennemi/lancier/lancier_immobile.png"));
            add(new Image("file:src/main/resources/application/perso/ennemi/lancier/lancier_mouvement1.png"));
            add(new Image("file:src/main/resources/application/perso/ennemi/lancier/lancier_immobile.png"));
            add(new Image("file:src/main/resources/application/perso/ennemi/lancier/lancier_mouvement2.png"));
        }});
        put("Archer", new ArrayList<>() {{
            add(new Image("file:src/main/resources/application/perso/ennemi/archer/archer_immobile.png"));
            add(new Image("file:src/main/resources/application/perso/ennemi/archer/archer_mouvement1.png"));
            add(new Image("file:src/main/resources/application/perso/ennemi/archer/archer_immobile.png"));
            add(new Image("file:src/main/resources/application/perso/ennemi/archer/archer_mouvement2.png"));
        }});
        put("Lapin", new ArrayList<>() {{
            add(new Image("file:src/main/resources/application/perso/lapin/lapin_1.png"));
            add(new Image("file:src/main/resources/application/perso/lapin/lapin_2.png"));
            add(new Image("file:src/main/resources/application/perso/lapin/lapin_3.png"));
        }});
        put("Sanglier", new ArrayList<>() {{
            add(new Image("file:src/main/resources/application/perso/sanglier/sanglier_1.png"));
            add(new Image("file:src/main/resources/application/perso/sanglier/sanglier_2.png"));
            add(new Image("file:src/main/resources/application/perso/sanglier/sanglier_3.png"));
        }});
        put("Tyran", new ArrayList<>() {{
            add(new Image("file:src/main/resources/application/perso/ennemi/tyran/tyran_immobile.png"));
            add(new Image("file:src/main/resources/application/perso/ennemi/tyran/tyran_mouvement1.png"));
            add(new Image("file:src/main/resources/application/perso/ennemi/tyran/tyran_immobile.png"));
            add(new Image("file:src/main/resources/application/perso/ennemi/tyran/tyran_mouvement2.png"));
        }});
        put("ChefVillage", new ArrayList<>() {{
            add(new Image("file:src/main/resources/application/personnages/chef_village.png"));
        }});
    }};

    private Personnage perso;
    private ImageView spritePerso;
    private int indexSprite;
    private long lastUpdate;

    public final static int POSITION_VUE_JOUEUR_X = (MapJeu.WIDTH * Constantes.TAILLE_TUILE) / 2;
    public final static int POSITION_VUE_JOUEUR_Y = (MapJeu.HEIGHT * Constantes.TAILLE_TUILE) / 2;

    public PersonnageVue(Personnage perso, ImageView spritesJoueur) {
        this.perso = perso;
        this.spritePerso = spritesJoueur;
        lastUpdate = System.currentTimeMillis();
        indexSprite = 0;
        construirePerso();

    }
    public PersonnageVue(Pane panePNJ, Personnage perso) {
        this.perso = perso;
        lastUpdate = System.currentTimeMillis();
        creationSprite();
        panePNJ.getChildren().add(spritePerso);
    }

    private void creationSprite() {
        spritePerso = new ImageView();
        spritePerso.setId(perso.getId());
        spritePerso.setFitWidth(Constantes.TAILLE_TUILE);
        spritePerso.setFitHeight(Constantes.TAILLE_TUILE);
        spritePerso.setImage(LISTE_SPRITES.get(perso.getClass().getSimpleName()).get(0));
        spritePerso.translateXProperty().bind(perso.getXProperty());
        spritePerso.translateYProperty().bind(perso.getYProperty());
        inverserSprite();
    }

    private void construirePerso() {
        spritePerso.setImage(LISTE_SPRITES.get(perso.getClass().getSimpleName()).get(0));
        spritePerso.setTranslateX(POSITION_VUE_JOUEUR_X);
        spritePerso.setTranslateY(POSITION_VUE_JOUEUR_Y);
        inverserSprite();
    }

    //modifie le sprite tout les 200 ms
    public void animerDeplacement() {
        long now = System.currentTimeMillis();
        if (now - lastUpdate >= 200) {
            lastUpdate = now;
            spritePerso.setImage(LISTE_SPRITES.get(perso.getClass().getSimpleName()).get(indexSprite++));
            if (indexSprite == LISTE_SPRITES.get(perso.getClass().getSimpleName()).size()) indexSprite = 0;
        }
    }

    //inverse l'image selon la direction
    public void inverserSprite() {
        if (perso.getDirection() == Direction.Droit)
            spritePerso.setScaleX(1);
        else
            spritePerso.setScaleX(-1);
    }

    //met le sprite du perso immobile
    public void immobile() {
        spritePerso.setImage(LISTE_SPRITES.get(perso.getClass().getSimpleName()).get(0));
    }
}
