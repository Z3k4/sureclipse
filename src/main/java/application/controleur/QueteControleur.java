package application.controleur;

import application.modele.Environnement;
import application.modele.ModeleQuetes;
import application.modele.ObjetInventaire;
import application.modele.personnages.ennemi.Tyran;
import application.modele.quetes.ObjectifNombreNecessaire;
import application.modele.quetes.QueteType;
import application.vue.VueQuetes;
import application.vue.quetes.QueteObjectif;
import javafx.scene.Scene;
import javafx.scene.text.TextFlow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class QueteControleur {
    private ModeleQuetes modeleQuetes;
    private VueQuetes vueQuetes;
    private Environnement env;

    private boolean queteEncours;

    public QueteControleur(Environnement environnement, Scene scene, TextFlow conteneurQuete) {
        this.env = environnement;
        this.modeleQuetes = new ModeleQuetes();
        this.vueQuetes = new VueQuetes(conteneurQuete, scene);
        queteEncours = false;

    }


    public boolean verifierQueteFini() {
        if(this.modeleQuetes.getQueteActuelProperty() != null && this.modeleQuetes.getQueteActuelProperty().getValue() >= 0) {
            if(this.modeleQuetes.getQueteActuel().getCompletee()) {
                rendreQuete();
                this.vueQuetes.rendreQuete();
                this.env.getListeEnnemis().add(new Tyran(this.env, 140, 35, 20));

            }
            return this.modeleQuetes.getQueteActuel().getCompletee();
        }
        return false;
    }

    /**
     * On donne la quête (par défaut c'est 0 comme on en a pas d'autres)
     */
    public void donnerQuete() {
        this.queteEncours = true;
        this.modeleQuetes.chargerQuete(0);
        this.vueQuetes.initialiserQuete(this.modeleQuetes.getQueteActuel());
        verifierRessourcesInventaire();
    };

    public boolean getQueteEnCours() {
        return this.queteEncours;

    }

    /**
     *     Permet de vérifier une première fois l'inventaire si jamais on ramasse des objets avant d'avoir récupéré la quête
     */
    public void verifierRessourcesInventaire() {
        HashMap<String, ObjectifNombreNecessaire> objectifs = this.modeleQuetes.getQueteActuel().recupererListeObjectifs().get(QueteType.TYPE_QUETE.RAMASSER);
        for(String ressource : objectifs.keySet()) {
            objectifs.get(ressource).setNombreActuelProperty(env.getJoueur().getInventaire().recupererNombreRessources(ressource));
        }


    }

    public void objetAEteRetireeInventaire(ObjetInventaire obj) {
        HashMap<String, ObjectifNombreNecessaire> objectifs = this.modeleQuetes.getQueteActuel().recupererListeObjectifs().get(QueteType.TYPE_QUETE.RAMASSER);
        String nomEntite = obj.getEntite().getClass().getSimpleName();
        if(objectifs.get(nomEntite) != null) {
            objectifs.get(nomEntite).retirerNombreObjectif(obj.getStackActuelProperty().getValue());
        }

        verifierQueteCompletee();
    }

    public void objetAEteAjouteeInventaire(ObjetInventaire obj) {
        if(this.modeleQuetes.getQueteActuel() != null) {
            HashMap<String, ObjectifNombreNecessaire> objectifs = this.modeleQuetes.getQueteActuel().recupererListeObjectifs().get(QueteType.TYPE_QUETE.RAMASSER);
            String nomEntite = obj.getEntite().getClass().getSimpleName();
            if (objectifs.get(nomEntite) != null) {
                int nombreAAJoutee = 0;

                objectifs.get(nomEntite).ajouterNombreObjectif(nombreAAJoutee + 1);
            }

            this.verifierQueteCompletee();
        }
    }

    /**
     * Permet de vérifier si la quête actuel est complétée ou non
     */
    public void verifierQueteCompletee() {

        if(this.modeleQuetes.getQueteActuelProperty() != null) {
            boolean queteCompletee = true;

            HashMap<QueteType.TYPE_QUETE, HashMap<String, ObjectifNombreNecessaire>> objectifsActuels = this.modeleQuetes.getQueteActuel().recupererListeObjectifs();

            Iterator iterationQueteTYpe = objectifsActuels.entrySet().iterator();

            while (iterationQueteTYpe.hasNext()) {
                Map.Entry<QueteType.TYPE_QUETE, HashMap<String, ObjectifNombreNecessaire>> objectif = (Map.Entry) iterationQueteTYpe.next();
                Iterator iterationObjectif = objectif.getValue().entrySet().iterator();
                while (iterationObjectif.hasNext()) {
                    Map.Entry<String, ObjectifNombreNecessaire> objectifARemplir = (Map.Entry) iterationObjectif.next();

                    QueteObjectif objectifAAffiche = new QueteObjectif(objectif.getKey(), objectifARemplir.getKey(), objectifARemplir.getValue());
                    ObjectifNombreNecessaire objectifCompteur = objectifARemplir.getValue();
                    if (objectifCompteur.getNombreActuel() < objectifCompteur.getNombreNecessaire()) {
                        queteCompletee = false;
                    }
                }

            }


            this.modeleQuetes.getQueteActuel().setCompletee(queteCompletee);
        }

    }

    public void rendreQuete() {
        //On fait apparaître le tyran une fois que la quête a été rendu
        this.env.getListeEnnemis().add(new Tyran(this.env, 100, 39, 20));
    }


}
