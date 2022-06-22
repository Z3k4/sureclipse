package application.vue.inventaire;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class InvSlot extends Pane {
    private ImageView imgView;
    private boolean estPlaceMain;
    private int index;

    public InvSlot(Image img) {
        this.imgView = new ImageView();
        imgView.setImage(img);

        this.getChildren().add(imgView);
    }

    public InvSlot(Image img, boolean placeMain) {
        this.imgView = new ImageView();
        imgView.setImage(img);

        this.getChildren().add(imgView);
        this.estPlaceMain = placeMain;
    }

    public void setIndex(int indx) {
        this.index = indx;
    }

    public void setSize(int x, int y) {
        this.imgView.setFitWidth(x);
        this.imgView.setFitHeight(y);

    }

    public int getIndex() {
        return this.index;
    }

    public boolean getEstPlaceMain() {
        return estPlaceMain;
    }


}
