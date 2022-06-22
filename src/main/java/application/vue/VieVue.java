package application.vue;

import application.modele.personnages.Joueur;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


public class VieVue  {

    private Pane rootPane;

    private  Image[] listeVie;
    private ImageView image ;
    private Pane viePane;

    // constructeur avec placement du Pane de la vie et la taille et le placment des images
    public VieVue(Pane root){
        this.rootPane=root;
        this.viePane=new Pane();
        this.listeVie= new Image[11];
        this.image= new ImageView();
        this.viePane.setMaxWidth(200);// dimension du pane
        this.viePane.setMaxHeight(100);


        this.viePane.setLayoutX(rootPane.getPrefWidth() + this.viePane.getMaxWidth());// coordonné du Pane

        this.viePane.setVisible(true);
        image.setFitHeight(50);//dimension de l'image
        image.setFitWidth(120);
        this.viePane.getChildren().add(this.image);
        this.rootPane.getChildren().add(this.viePane);

        listeImageVie();
        this.afficherVie(Joueur.PV_MAX);

    }
    // fonction pour cree une image View qui contient toute les images de la vie
    public void listeImageVie(){
        for(int i = 0; i<this.listeVie.length; i++) {
            this.listeVie[i] = new Image("file:src/main/resources/application/Vie/" + i + ".png");
        }
    }

    // fonction qu affiche les differente image en fonction de la vie
    public void afficherVie(int pv){
        image.setImage(listeVie[pv]);
    }




}
