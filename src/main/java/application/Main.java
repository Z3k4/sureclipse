package application;

import application.controleur.Constantes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static application.modele.MapJeu.HEIGHT;
import static application.modele.MapJeu.WIDTH;

public class Main extends Application {

    public final static int WIDTH_FENETRE = WIDTH* Constantes.TAILLE_TUILE;
    public final static int HEIGHT_FENETRE = HEIGHT*Constantes.TAILLE_TUILE;

    @Override
    public void start(Stage primaryStage) {
        try {
            Pane root = FXMLLoader.load(getClass().getResource("vue_accueil.fxml"));
            Scene scene = new Scene(root, WIDTH*Constantes.TAILLE_TUILE,HEIGHT*Constantes.TAILLE_TUILE);
            root.requestFocus();
            primaryStage.setResizable(false);
            primaryStage.setTitle("Le Tyran");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
