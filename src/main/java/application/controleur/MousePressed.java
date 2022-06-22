package application.controleur;

import application.modele.Environnement;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import static application.modele.MapJeu.*;

public class MousePressed implements EventHandler<MouseEvent> {
    private ControleurJeu controleur;
    private Environnement env;

    public MousePressed(ControleurJeu controleur, Environnement env) {
        this.controleur = controleur;
        this.env = env;
    }

    //calcule les coordonnées selon la position de la souris et du personnage
    @Override
    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getX() < (WIDTH * Constantes.TAILLE_TUILE) / 2 + 5*Constantes.TAILLE_TUILE && mouseEvent.getX() > (WIDTH * Constantes.TAILLE_TUILE) / 2 - 5* Constantes.TAILLE_TUILE
                && mouseEvent.getY() < (HEIGHT * Constantes.TAILLE_TUILE) / 2 + 5*Constantes.TAILLE_TUILE && mouseEvent.getY() > (HEIGHT * Constantes.TAILLE_TUILE) / 2 - 5*Constantes.TAILLE_TUILE) {
            int mouseX, mouseY;

            if (mouseEvent.getY() < (HEIGHT * Constantes.TAILLE_TUILE) / 2)
                mouseY = (int) (env.getJoueur().getY() / Constantes.TAILLE_TUILE) - 1;
            else if (mouseEvent.getY() > (HEIGHT * Constantes.TAILLE_TUILE) / 2 + Constantes.TAILLE_TUILE)
                mouseY = (int) (env.getJoueur().getY() / Constantes.TAILLE_TUILE) + 1;
            else
                mouseY = (int) (env.getJoueur().getY() / Constantes.TAILLE_TUILE);

            if (mouseEvent.getX() > (WIDTH * Constantes.TAILLE_TUILE) / 2 + Constantes.TAILLE_TUILE)
                if ((env.getJoueur().getX() / Constantes.TAILLE_TUILE) % 1 > 0.8)
                    mouseX = (int) (env.getJoueur().getX() / Constantes.TAILLE_TUILE) + 2;
                else
                    mouseX = (int) (env.getJoueur().getX() / Constantes.TAILLE_TUILE) + 1;
            else if (mouseEvent.getX() < (WIDTH * Constantes.TAILLE_TUILE) / 2)
                if ((env.getJoueur().getX() / Constantes.TAILLE_TUILE) % 1 > 0.8 && mouseY != (int) (env.getJoueur().getY() / Constantes.TAILLE_TUILE))
                    mouseX = (int) (env.getJoueur().getX() / Constantes.TAILLE_TUILE);
                else
                    mouseX = (int) (env.getJoueur().getX() / Constantes.TAILLE_TUILE) - 1;
            else
            if ((env.getJoueur().getX() / Constantes.TAILLE_TUILE) % 1 > 0.5)
                mouseX = (int) (env.getJoueur().getX() / Constantes.TAILLE_TUILE) + 1;
            else
                mouseX = (int) (env.getJoueur().getX() / Constantes.TAILLE_TUILE);


            if(mouseX >= 0 && mouseX < env.getMapJeu().getWidth() && mouseY < env.getMapJeu().getHeight()) {
                if (env.getJoueur().interagit(mouseX, mouseY))
                    controleur.getArmeVue().animationFrappe();
            }
        }
    }
}
