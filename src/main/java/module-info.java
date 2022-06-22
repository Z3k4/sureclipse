module application.letyran {
    requires javafx.controls;
    requires javafx.fxml;
    //requires javafx.media;
    requires org.json;
    requires junit;


    opens application to javafx.fxml;
    exports application;

    opens application.controleur to javafx.fxml;
    exports application.controleur;
    exports application.modele;
    opens application.modele to javafx.fxml;
    exports application.vue;
    opens application.vue to javafx.fxml;
    exports application.modele.objets;
    opens application.modele.objets to javafx.fxml;
    exports application.modele.collisions;
    opens application.modele.collisions to javafx.fxml;
    exports application.modele.personnages;
    opens application.modele.personnages to javafx.fxml;
    exports application.modele.personnages.ennemi;
    opens application.modele.personnages.ennemi to javafx.fxml;
    exports application.vue.inventaire;
    opens application.vue.inventaire to javafx.fxml;
    exports application.modele.personnages.animaux;
    opens application.modele.personnages.animaux to javafx.fxml;
    exports application.modele.objets.materiaux;
    opens application.modele.objets.materiaux to javafx.fxml;
    exports application.modele.objets.consommable;
    opens application.modele.objets.consommable to javafx.fxml;
    exports application.modele.projectiles;
    opens application.modele.projectiles to javafx.fxml;
    exports application.controleur.listeners;
    opens application.controleur.listeners to javafx.fxml;
}