package application.modele.quetes;

import application.modele.ModeleQuetes;
import javafx.collections.ObservableMap;

import java.util.HashMap;

public class BaseQuete {


    private HashMap<QueteType.TYPE_QUETE, HashMap<String, ObjectifNombreNecessaire>> listeObjectifs;

    private String nom;
    private boolean completee;

    public BaseQuete(String nom) {
        this.nom = nom;
        this.completee = false;
        this.listeObjectifs = new HashMap<>();
    }

    public String getNom() {
        return this.nom;
    }

    public void ajouterObjectif(QueteType.TYPE_QUETE type, String nom, int nombre) {
        if(this.listeObjectifs.get(type) == null) {
            this.listeObjectifs.put(type, new HashMap<>());
        }
        this.listeObjectifs.get(type).put(nom, new ObjectifNombreNecessaire(nombre));
    }

    public HashMap<QueteType.TYPE_QUETE, HashMap<String, ObjectifNombreNecessaire>> recupererListeObjectifs() {
        return this.listeObjectifs;
    }

    public void setCompletee(boolean finis) {
        this.completee = finis;
    }

    public boolean getCompletee() {
        return this.completee;
    }
}
