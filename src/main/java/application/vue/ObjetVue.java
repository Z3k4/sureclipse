package application.vue;

import application.modele.Entite;
import application.modele.Environnement;
import application.modele.personnages.Joueur;
import javafx.collections.ListChangeListener;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class ObjetVue {

    private Environnement env;
    private ArrayList<ObjetView> objetImageView;
    private Pane paneDecors;

    public ObjetVue(Environnement env, Pane paneDecors) {
        this.env = env;
        this.objetImageView = new ArrayList<>();
        this.paneDecors = paneDecors;
        enregistrerTousLesObjets();


        this.env.getListeEntites().addListener(new ListChangeListener<Entite>() {
            @Override
            public void onChanged(Change<? extends Entite> change) {
                change.next();
                for(int i = 0; i < change.getRemovedSize(); i++) {
                    retirerObjet(change.getRemoved().get(i));
                }

                for(int i = 0; i < change.getAddedSize(); i++) {
                    ajouterObjet(change.getAddedSubList().get(i));
                }
            }
        });
    }

    public void enregistrerTousLesObjets() {
        for(int i = 0; i < this.env.getListeEntites().size(); i++) {
            Entite obj = this.env.getListeEntites().get(i);
            if(!(obj instanceof Joueur)) {
                ObjetView objView = new ObjetView(obj);
                objetImageView.add(objView);
                this.paneDecors.getChildren().add(objView);
            }

        }
    }

    public void ajouterObjet(Entite objet) {
        ObjetView objView = new ObjetView(objet);
        this.objetImageView.add(objView);
        this.paneDecors.getChildren().add(objView);
    }

    public void ajouterObjet(Entite objet,int x, int y) {
        ObjetView objView = new ObjetView(objet);
        objView.setTranslateX(x);
        objView.setTranslateY(y);
        this.objetImageView.add(objView);
        this.paneDecors.getChildren().add(objView);
    }

    public void retirerObjet(Entite objet) {
        for(int i = 0; i < this.objetImageView.size(); i++) {
            ObjetView img = this.objetImageView.get(i);
            if(img.getObjet() == objet) {
                this.paneDecors.getChildren().remove(img);
            }
        }
    }

    public void rendreObjets() {
        for(int i = 0; i < this.objetImageView.size(); i++) {
            ImageView imgView = this.objetImageView.get(i);
            imgView.setVisible(true);
        }
    }

    public void update() {
        rendreObjets();
    }
}
