package application.controleur;

import application.vue.ChargeurRessources;
import application.vue.accueil.AmbianceEnvironnement;
import application.vue.accueil.InteractionUI;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static application.Main.HEIGHT_FENETRE;
import static application.Main.WIDTH_FENETRE;

public class ControleurAccueil implements Initializable {
    @FXML
    private Pane root;

    @FXML
    private VBox btnQuitNouv;

    @FXML
    private Label nomJeu;

    private Timeline gameLoop;

    @FXML
    private Button nouvellePartie;

    @FXML
    private Button quitterJeu;

    @FXML
    private Pane paneNuages;

    private double textRedColor = 1;
    private int nbFrame = 0;

    private AmbianceEnvironnement ambianceEnvironnement;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ChargeurRessources.charger();
        root.setPrefSize(150 * 32, 34 * 32);
        this.paneNuages.setPrefSize(root.getPrefWidth(), root.getPrefHeight());

        btnQuitNouv.setLayoutX(WIDTH_FENETRE / 2 - btnQuitNouv.getPrefWidth() / 2);
        btnQuitNouv.setLayoutY(HEIGHT_FENETRE / 2 + btnQuitNouv.getPrefHeight() / 3);

        new InteractionUI(nouvellePartie, quitterJeu);
        ambianceEnvironnement = new AmbianceEnvironnement();


        initAnimation();
        ajouterNuages();


        gameLoop.play();
    }

    private void initAnimation() {
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                // on définit le FPS (nbre de frame par seconde)
                Duration.seconds(0.017),
                (ev ->{
                    animerNuages();
                    nbFrame++;
                })
        );
        gameLoop.getKeyFrames().add(kf);
    }


    public void ajouterNuages() {
        ImageView previous = null;
        for(int i = 0; i < 7; i++) {
            ImageView imgView = new ImageView(new Image("file:src/main/resources/application/ciel/nuage_" + (int)(Math.random() * 6 +1) + ".png"));
            imgView.setFitWidth(100);
            imgView.setFitHeight(50);

            if (i > 0) {
                imgView.setTranslateY(previous.getTranslateY());
                if (i % 2 == 0)
                    imgView.setTranslateY(previous.getTranslateY() + 50 + Math.random() * 20);
            }
            else {
                imgView.setTranslateY(0);
            }

            imgView.setTranslateX(((i *3) + i % 2) * imgView.getFitWidth() + Math.random() * imgView.getFitWidth());
            this.paneNuages.getChildren().add(imgView);
            previous = imgView;
        }
    }

    private void animerNuages() {
        for (int i = 0; i < this.paneNuages.getChildren().size(); i++) {
            ImageView img = (ImageView) this.paneNuages.getChildren().get(i);

            img.setTranslateX((int)(img.getTranslateX() - 1));
            if(img.getTranslateX() < 0 - img.getFitWidth()) {
                img.setTranslateX(this.root.getPrefWidth() + img.getFitWidth());
            }
        }
    }

    public void lancerJeu() throws IOException {
        URL location = getClass().getResource("/application/vue.fxml");
        FXMLLoader loader = new FXMLLoader(location);
        Pane nouveauRoot = loader.load(location.openStream());

        ControleurJeu controleurJeu = (ControleurJeu) loader.getController();
        controleurJeu.setAmbianceEnvironnement(this.ambianceEnvironnement);
        controleurJeu.setPaneAccueil(root);

        this.root.getScene().setRoot(nouveauRoot);
        nouveauRoot.requestFocus();

        //Controleur controleurJeu = (Controleur) nouveauRoot

    }


    public void quitterLeJeu() {
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.close();
    }

}
