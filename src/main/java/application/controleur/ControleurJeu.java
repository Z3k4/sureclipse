package application.controleur;

import application.controleur.listeners.*;
import application.modele.Environnement;
import application.vue.ArmeVue;
import application.vue.EtabliVue;
import application.vue.PersonnageVue;
import application.vue.VieVue;
import application.vue.EnvironnementVue;
import application.modele.ModeleDialogue;
import application.vue.*;
import application.vue.accueil.AmbianceEnvironnement;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class ControleurJeu implements Initializable {

    private Environnement env;
    private PersonnageVue personnageVue;
    private EnvironnementVue envVue;
    private ArmeVue armeVue;
    private VieVue vievue;
    private ObjetVue objetVue;
    private EtabliVue etabliVue;
    private FeuDeCampVue feuDeCampVue;
    private QueteControleur controleurQuete;
    private InventaireControleur inventaireControleur;
    private VueDialogue vueDialog;
    private ModeleDialogue modeleDialogue;
    private DialogueControleur dialogueControleur;

    private Timeline gameLoop;

    @FXML private Pane root;
    @FXML private TilePane tileSol;
    @FXML private TilePane tileDecors;
    @FXML private TilePane tileFond;
    @FXML private TilePane tileFondDecor;
    @FXML private ImageView spriteJoueur;
    @FXML private ImageView spriteArme;
    @FXML private BorderPane bPaneEtabli;
    @FXML private ImageView spriteEtabli;
    @FXML private Label labelMort;
    @FXML private ImageView spriteFeuDeCamp;

    @FXML private Pane paneDecors;
    @FXML private Pane panePNJ;
    @FXML private Pane inventaireMain;
    @FXML private Pane inventaireSac;
    @FXML private Pane inventaireEquipement;

    @FXML private Text texteDialogue;
    @FXML private TextFlow dialogFlow;
    @FXML private TextFlow conteneurQuetes;

    @FXML private VBox vBoxPause;
    @FXML private VBox vBoxSuicide;


    private AmbianceEnvironnement ambianceEnvironnement;
    private Pane paneAccueil;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        env = new Environnement();
        modeleDialogue = new ModeleDialogue();

        this.controleurQuete = new QueteControleur(env, this.root.getScene(), conteneurQuetes);



        personnageVue = new PersonnageVue(env.getJoueur(), spriteJoueur);
        envVue = new EnvironnementVue(env, root, tileSol, tileDecors, tileFond, tileFondDecor, this);
        objetVue = new ObjetVue(this.env, paneDecors);
        armeVue = new ArmeVue(env.getJoueur(), spriteArme, this);
        vievue = new VieVue(root);
        etabliVue = new EtabliVue(env.getEtabli(), spriteEtabli, bPaneEtabli);
        feuDeCampVue = new FeuDeCampVue(env.getFeuDeCamp(), spriteFeuDeCamp, labelMort);
        vueDialog = new VueDialogue(modeleDialogue, dialogFlow,  texteDialogue);

        this.inventaireControleur = new InventaireControleur(root, controleurQuete, env, inventaireMain, inventaireSac, inventaireEquipement);
        this.dialogueControleur = new DialogueControleur(vueDialog, modeleDialogue, controleurQuete);

        root.addEventHandler(KeyEvent.KEY_PRESSED, new KeyPressed(env));
        root.addEventHandler(KeyEvent.KEY_RELEASED, new KeyReleased(this, env));
        root.addEventHandler(Event.ANY, inventaireControleur);
        root.addEventHandler(MouseEvent.MOUSE_PRESSED, new MousePressed(this, env));
        root.addEventHandler(Event.ANY, dialogueControleur);
        root.addEventHandler(KeyEvent.KEY_PRESSED, new PauseControleur(env, root, vBoxPause, vBoxSuicide));

        env.getJoueur().getInventaire().getArmeProperty().addListener(new ArmeListener(armeVue, inventaireControleur.getInvVue()));
        env.getJoueur().getInventaire().getArmureProperty().addListener(new ArmureListener(inventaireControleur.getInvVue()));


        this.env.getJoueur().getPVProperty().addListener(new VieListener(vievue, this.env.getJoueur()));

        new EtabliControleur(root,env, etabliVue);
        new PersonnageListeners(env.getJoueur(), personnageVue, armeVue, feuDeCampVue);
        new EnvironnementControleur(root, envVue, env, this);


        //On va surtout s'occuper des sons ici




        initAnimation();
        gameLoop.play();
    }

    private void initAnimation() {
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                // on définit le FPS (nbre de frame par seconde)
                Duration.seconds(0.017),
                (ev ->{
                    if (!env.getPause()) {
                        env.getJoueur().update();
                        if (!env.getJoueur().getMort() && !env.getJoueur().getSeRepose()) {
                            objetVue.update();
                            vueDialog.animer(0.017);
                            this.env.update();
                        }
                    }
                })
        );
        gameLoop.getKeyFrames().add(kf);
    }

    public void setAmbianceEnvironnement(AmbianceEnvironnement amb) {
        this.ambianceEnvironnement = amb;
        this.ambianceEnvironnement.changerSon("village");
        new JoueurListener(env.getJoueur(), this);
    }

    public AmbianceEnvironnement getAmbianceEnvironnement() {
        return this.ambianceEnvironnement;
    }

    public ArmeVue getArmeVue() {
        return armeVue;
    }

    public InventaireControleur getInventaireControleur() {
        return this.inventaireControleur;
    }
    public DialogueControleur getDialogueControleur() {
        return this.dialogueControleur;
    }

    public void setPaneAccueil(Pane paneAccueil) {
        this.paneAccueil =paneAccueil;
    }

    public void retournerMenuPrincipal() {
        this.ambianceEnvironnement.changerSon("introjeu");
        this.root.getScene().setRoot(paneAccueil);
        paneAccueil.requestFocus();
    }
}