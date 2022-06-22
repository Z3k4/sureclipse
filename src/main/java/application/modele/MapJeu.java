package application.modele;

public class MapJeu {
    public final static int WIDTH = 30;
    public final static int HEIGHT = 20;

    private int[][] tabMap;

    public MapJeu() {
        construireMap();
    }

    public int getHeight()  {
        return tabMap.length;
    }

    public int getWidth()  {
        return tabMap[0].length;
    }

    private void construireMap() {
        tabMap = ChargementTileMap.recupererTileMap(0);

    }

    public int[][] getTabMap() {
        return tabMap;
    }
}
