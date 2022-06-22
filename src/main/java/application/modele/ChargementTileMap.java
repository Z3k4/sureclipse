package application.modele;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class ChargementTileMap {

    /**
     * Permet de récupérer sous forme de string le contenu d'un fichier, utile pour un format JSON
     * @param file lien du fichier
     * @return le contenu du fichier sous forme JSON
     */
    private static String recupererString(FileInputStream file) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file));

            String ligne;
            while((ligne = br.readLine()) != null) {
                sb.append(ligne);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * Permet de récupérer la tilemap correspondante en fonction de l'index voulue, sachant que par défaut, 1 c'est ce qu'il y a en fond et 0 ça contient les blocs
     * @param index
     * @return
     */
    public static int[][] recupererTileMap(int index) {

        int[][] tileMap;

        File file = new File("src/main/resources/application/tilemap/donne_tilemap.json");
        if (file.exists()) {
            try {
                FileInputStream is = new FileInputStream("src/main/resources/application/tilemap/donne_tilemap.json");
                //String donne
                String map = recupererString(is);
                JSONObject json = new JSONObject(map);
                int mapWidth = json.getInt("width");
                int mapHeight = json.getInt("height");

                tileMap = new int[mapHeight][mapWidth];


                JSONArray infoCarte = json.getJSONArray("layers");
                //JSONObject ensembleBloc = infoCarte.getString("data");

                JSONObject mapDetails = (JSONObject) infoCarte.get(index);
                JSONArray mapdsdqs = (JSONArray) mapDetails.get("data");

                for (int i = 0; i < mapdsdqs.length(); i++) {
                    int y = i / mapWidth;
                    int x = i - (y) * mapWidth;
                    tileMap[y][x] = mapdsdqs.getInt(i);
                }

                return tileMap;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        return new int[0][0];
    }




}
