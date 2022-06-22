package application.modele;

import java.util.ArrayList;
import java.util.HashMap;

public class ModeleDialogue {
    private int partieActuelle;
    private int nombrePartieTotal;
    private HashMap<Integer, ArrayList<String>> texteEntier;


    //Change en fonction du dialogue qu'on veut afficher, par exemple si on donne une quête et qu'on veut dire autre chose une fois que celle ci
    //a été accomplie
    private int indexDialogue;


    public ModeleDialogue () {
        indexDialogue = 0;

        texteEntier = new HashMap<>();
        ArrayList<String> dialogueQueteNonFini = new ArrayList<>();
        dialogueQueteNonFini.add("Bonjour, bienvenue au village Village");
        dialogueQueteNonFini.add("Comme vous pouvez le voir, nous ne sommes plus beaucoup...");
        dialogueQueteNonFini.add("En effet depuis l'arrivé du nouveau roi la vie est devenu presque impossible, l'ancien n'était pas un ange" +
                "mais vous pouvions vivre correctement tout de même");
        dialogueQueteNonFini.add("Celui se faisant appeler Momo le Tyran ne nous laisse aucuns répit, entre l'augmentation des taxes, l'enlèvement de nos jeunes");
        dialogueQueteNonFini.add("Regardez moi, ma fille s'est faite kidnappé dans le but de devenir son esclave et de le servir pour le restant de ses jours");
        dialogueQueteNonFini.add("Aventurier, accepteriez vous de me venir en aide et de récupérer ma fille ?");
        dialogueQueteNonFini.add("Je pense qu'avant cela vous devriez vous entraîner en amassant des ressources et des équipements");
        dialogueQueteNonFini.add("Voici une liste de choses à faire avant que vous soyez prêt");

        texteEntier.put(0, dialogueQueteNonFini);

        ArrayList<String> dialogueQueteEncours = new ArrayList<>();
        dialogueQueteEncours.add("J'espère que vous réussirez là où de nombreux ont échoués...");
        dialogueQueteEncours.add("Vous êtes mon seul espoir de pouvoir revoir ma fille..");

        ArrayList<String> dialogueQueteFini = new ArrayList<>();
        dialogueQueteFini.add("Vous revoilà.. Je pense que vous êtes fin prêt à affronter Momo le Tyran."
                 + "Mais prenez garde, la route sera ardue et le combat ne sera pas facile");
        dialogueQueteFini.add("Faîtes preuve de prudence, et que votre route soit accompagnée par la chance...");


        texteEntier.put(1, dialogueQueteEncours);
        texteEntier.put(2, dialogueQueteFini);



        partieActuelle = 0;
        nombrePartieTotal = texteEntier.get(indexDialogue).size() - 1;
    }

    public void reinitialiserDialogue() {
        partieActuelle = 0;
    }

    public String getTexteDialogue() {
        return this.texteEntier.get(indexDialogue).get(partieActuelle);
    }

    /**
     * Permet de récupérer la partie du texte à affiché
     * @param index l'index de la partie voulue
     */
    public void changerDialogue(int index) {
        indexDialogue = index;
        partieActuelle = 0;
        nombrePartieTotal = texteEntier.get(indexDialogue).size() - 1;
    }

    public void avancerPartie() {
        if(partieActuelle < nombrePartieTotal) {
            partieActuelle++;
        }
    }

    public boolean dernierePartie() {
        return this.partieActuelle == this.nombrePartieTotal;
    }


}
