package application.modele;

import application.modele.objets.materiaux.Pierre;
import application.modele.objets.materiaux.Terre;
import org.junit.Test;

import static org.junit.Assert.*;

public class InventaireTest {
    Environnement env = new Environnement();
    Inventaire inventaire = new Inventaire(env);


    @Test
    public void recupererNombreRessources() {
        //On test avec l'inventaire vide
        assertEquals(0, inventaire.recupererNombreRessources("Pierre"));
        assertEquals(0, inventaire.recupererNombreRessources("Terre"));
        assertEquals(0, inventaire.recupererNombreRessources("Fer"));
        assertEquals(0, inventaire.recupererNombreRessources("Platine"));
        assertEquals(0, inventaire.recupererNombreRessources("Bois"));

        //On en met 6 sachant qu'on peut posséder qu'une stack de 5, la fonction recherche dans toutes les stacks

        for(int i = 0; i < 6; i++) {
            inventaire.ajouterObjet(new Pierre());
        }


        for(int i = 0; i < 99; i++) {
            inventaire.ajouterObjet(new Terre());
        }

        assertEquals(6, inventaire.recupererNombreRessources("Pierre"));
        assertEquals(99, inventaire.recupererNombreRessources("Terre"));

    }

    @Test
    public void retirerObjet() {
        Environnement env = new Environnement();
        env.getListeEntites().add(new Terre());

        for(int i = 0; i < 800; i++) {
            env.getJoueur().getInventaire().ajouterObjet(new Terre());
        }

        assertEquals(2, env.getListeEntites().size());


    }

    @Test
    public void ajouterObjetInventaire() {

        // /!\ Attention il faut prendre en compte que le joueur possède un set d'objet de départ
        Environnement env = new Environnement();
        env.getListeEntites().add(new Terre());


        //On vérifie que c'est bien égale à 2 (le premier étant le joueur)
        assertEquals(2, env.getListeEntites().size());

        env.getJoueur().getInventaire().ajouterObjet(env.getListeEntites().get(1));

        //Le jeu retire l'objet de la liste des entites quand il est ramassé, donc on devrait avoir 1
        assertEquals(1, env.getListeEntites().size());

        //On va remplir l'inventaire seulement de terre
        for(int i = 0; i < 800; i++) {
            env.getJoueur().getInventaire().ajouterObjet(new Terre());
        }

        env.getListeEntites().add(new Pierre());


        //L'inventaire étant que de terre, cette fonction est censé pouvoir récupérer de la pierre

        assertEquals(true, env.getJoueur().getInventaire().ajouterObjet(env.getListeEntites().get(1)));
        assertEquals(1, env.getListeEntites().size());

        env.getListeEntites().add(new Terre());
        assertEquals(false, env.getJoueur().getInventaire().ajouterObjet(env.getListeEntites().get(1)));
        assertEquals(2, env.getListeEntites().size());

        //Par contre si on tente d'ajouter de la terre, c'est pas censé pouvoir la récupérer

    }

    @Test
    public void retirerNbRessources() {
        inventaire.getObjets().clear();

        for(int i = 0; i < 6; i++) {
            inventaire.ajouterObjet(new Pierre());
        }


        for(int i = 0; i < 30; i++) {
            inventaire.ajouterObjet(new Terre());
        }

        inventaire.retirerNbRessources("Terre", 1);
        assertEquals(29, inventaire.recupererNombreRessources("Terre"));

        inventaire.retirerNbRessources("Terre", 10);

        assertEquals(19, inventaire.recupererNombreRessources("Terre"));

    }
}