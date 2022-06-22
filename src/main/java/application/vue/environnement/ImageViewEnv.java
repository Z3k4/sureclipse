package application.vue.environnement;

import application.controleur.Constantes;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

public class ImageViewEnv extends ImageView {

    private int width;

    private int blocX, blocY;
    public ImageViewEnv(int bType, int blocy, int blocx) {

        this.blocX = blocx;
        this.blocY = blocy;
        bType -= 1;


        this.width = (int)Constantes.tileSetSol.getWidth()/32;
        int y = bType / width;
        int x = bType - y * width;


        if(bType <= 0) {
            x = 13;
            y = 5;
        }




        this.setImage(Constantes.tileSetSol);
        this.setViewport(new Rectangle2D(32 * x, 32 * y, 32,32));


    }

    public int getBlocX() {
        return this.blocX;
    }

    public int getBlocY() {
        return this.blocY;
    }

    public void setCustomView(String nom) {
        int bType = 0;
        switch (nom) {
            case "Terre":
                bType = 91;
                break;
            case "Pierre":
                bType = 19;
                break;
            case "Vide":
                bType = 102;
                break;
        }

        int y = bType / width;
        int x = bType - y * width;

        this.setViewport(new Rectangle2D(32 * x, 32 * y, 32,32));
    }
}
