package conuHacks8.zombieApocalypse;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * La classe MainJavaFX se charge du modele vue.
 * Elle nous permet de changer de scene
 * @author Hugo Valente et Anthony Lim
 */
public class MainJavaFX extends Application {
    private Scene sceneEcranAccueil;
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    private final BackgroundFill fondNoir = new BackgroundFill(Color.BLACK, new CornerRadii(1),
            new Insets(0.0, 0.0, 0.0, 0.0));

    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Initialisation de la scene ecran accueil
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {

        var vbox = new VBox();
        vbox.setBackground(new Background(fondNoir));
        vbox.setId("vbox");
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        ImageView imageView = new ImageView(new Image("hills-615429_1280.jpg"));
        imageView.setFitHeight(HEIGHT - 100);
        imageView.setFitWidth(WIDTH);
        var btnJouer = new Button("Play the game");
        sceneEcranAccueil = new Scene(vbox, WIDTH, HEIGHT);
        var title = new Text("EPYC ZOMBiE SHOOTER 2D");
        title.setFill(Color.RED);
        title.setFont(Font.font("century",30));
        vbox.getChildren().addAll(imageView, title, btnJouer);

        sceneEcranAccueil.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                Platform.exit();
        });


        btnJouer.setOnAction(event -> {

            primaryStage.setScene(sceneJouer(primaryStage));
        });
        primaryStage.setTitle("EPYX ZOOMBiE SHOOTER 2D");
        primaryStage.getIcons().addAll(new Image("ak47u.png"));
        primaryStage.setScene(sceneEcranAccueil);
        primaryStage.show();
    }
    /**
     * Cette methode nous permet d'afficher les informations
     * @param btn qui est le bouton retour pour revenir a l'ecran principal
     * @param stage pour changer de scene si on appuie sur le bouton Escape
     * @return la scene infos
     */
    public Scene sceneInfos(Button btn, Stage stage) {
        var root = new VBox();
        Text titreDuJeu = new Text("Squelette Espiègle");
        titreDuJeu.setFont(Font.font(36));

        //Deuxième boîte
        Text par = new Text("Par");
        Text et = new Text("et");
        par.setFont(Font.font(24));
        et.setFont(Font.font(24));
        var createur1 = new HBox();
        var createur2 = new HBox();
        Text nom1 = new Text(" Anthony Lim");
        Text nom2 = new Text(" Hugo Galvao Valente");
        nom1.setFill(Color.GREEN);
        nom2.setFill(Color.RED);
        nom1.setFont(Font.font(24));
        nom2.setFont(Font.font(24));
        createur1.getChildren().addAll(par, nom1);
        createur2.getChildren().addAll(et, nom2);
        createur1.setAlignment(Pos.CENTER);
        createur2.setAlignment(Pos.CENTER);
        Text ligne1 = new Text("Travail remis à Nicolas Hurtbuise. Graphismes adaptés de");
        Text ligne2 = new Text("https://game-icons.net/. Développé dans le cadre du cours 420-203-RE");
        Text ligne3 = new Text("Développement de programmes dans un environnement graphique, au");
        Text ligne4 = new Text("Collège de Bois-de-Boulogne");
        root.getChildren().addAll(titreDuJeu, createur1, createur2, ligne1, ligne2, ligne3, ligne4, btn);
        root.setAlignment(Pos.CENTER);

        Scene sceneInfos = new Scene(root, WIDTH, HEIGHT);
        sceneInfos.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                stage.setScene(sceneEcranAccueil);
        });
        return sceneInfos;
    }
    /**
     * Cette methode nous permet d'afficher la scene de jeu et de joueur au jeu grace a un Animation Timer
     * @param stage pour changer de scene si on appuie sur le bouton Escape
     * @return la scene Jouer
     */
    public Scene sceneJouer(Stage stage) {
        var pane = new Pane();
        pane.setBackground(new Background(fondNoir));
        Scene sceneJeu = new Scene(pane, WIDTH, HEIGHT);
        var canvas = new Canvas(WIDTH, HEIGHT);
        var context = canvas.getGraphicsContext2D();

        Partie partie = new Partie();
        var timer = new AnimationTimer() {
            long lastTime = System.nanoTime();
            double timerFin = 0;

            @Override
            public void handle(long now) {
                double deltaTemps = (now - lastTime) * 1e-9;
                if (partie.getEstFinie()) {
                    timerFin = timerFin + deltaTemps;
                    if (timerFin > 3) {
                        this.stop();
                        stage.setScene(sceneEcranAccueil);
                    }
                }
                partie.drawInterfaceDeJeu(context, partie, deltaTemps);


                lastTime = now;
            }

        };
        timer.start();

        sceneJeu.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.setScene(sceneEcranAccueil);
                timer.stop();
            } else {
                Input.setKeyPressed(event.getCode(), true);
            } if (Input.isKeyPressed(KeyCode.J)) {
                partie.miseAJourScore();
            }
            if (Input.isKeyPressed(KeyCode.H)) {
                partie.setNiveau(partie.getNiveau()+1);
                partie.setScore(partie.getScore()+5);
            }
            if (Input.isKeyPressed(KeyCode.K)) {
                partie.augmenterNbVies();
            }
            if (Input.isKeyPressed(KeyCode.L)){
                partie.setEstFinie(true);
            }
        });
        sceneJeu.setOnKeyReleased((e) -> {
            Input.setKeyPressed(e.getCode(), false);
        });
        pane.getChildren().addAll(canvas);
        return sceneJeu;
    }
}

