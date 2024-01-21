package conuHacks8.zombieApocalypse;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Random;

/**
 * Cette classe s'occupe de créer la partie entemps
 */
public class Partie {
    private final Random rd = new Random();
    //True = Oeil, False = Bouche
    private boolean monstreSpeciauxChoix;
    private final Squelette squelette = new Squelette();
    private final BouleMagique bouleMagique = new BouleMagique(squelette.getX(), squelette.getY());
    private final ArrayList<Monstres> monstres = new ArrayList<>();
    private double timerPaix = 0;
    private double timerMonstresNormaux = 0;
    private double timerMonstresSpeciaux = 0;
    private int niveau = 1;
    private int score = 0;
    private boolean estFinie;

    public Partie() {
        this.estFinie = false;
    }

    public void miseAJourScore() {

        score++;
        if (score % 5 == 0) {
            niveau++;
            timerPaix = 0;
        }
    }

    public void miseAJourJeu(int nbVies) {
        estFinie(nbVies);
    }

    public void estFinie(int nbVies) {
        if (nbVies == 0)
            estFinie = true;
    }

    public void drawTetesMort(GraphicsContext context) {
        Image image = new Image("squelette.png");
        image = ImageHelpers.colorize(image, Color.PINK);
        int nombre = 75;
        int nombre2 = 0;
        for (int i = 0; i < squelette.getNbVies(); i++) {
            nombre2 += 30;
            context.drawImage(image, MainJavaFX.WIDTH / 2.05 - nombre + nombre2, 150, 30, 30);
        }
    }

    public void drawNiveau(GraphicsContext context) {
        if (!estFinie) {
            if (0 < timerPaix && timerPaix < 3) {
                context.setFill(Color.WHITE);
                context.fillText("NIVEAU " + getNiveau(), MainJavaFX.WIDTH / 2, MainJavaFX.HEIGHT / 2, MainJavaFX.WIDTH - 200);
                context.setFont(Font.font(40));
                context.setTextAlign(TextAlignment.CENTER);
            }
        }
        if (estFinie) {
            context.setFill(Color.RED);
            context.fillText("Vous avez perdu !",MainJavaFX.WIDTH / 2, MainJavaFX.HEIGHT / 2, MainJavaFX.WIDTH - 200);
        }
    }

    public void drawScore(GraphicsContext context) {
        context.setFill(Color.WHITE);
        context.fillText(String.valueOf(getScore()), MainJavaFX.WIDTH / 2, 100, MainJavaFX.WIDTH - 200);
        context.setTextAlign(TextAlignment.CENTER);
    }

    public void draw(GraphicsContext context) {
        drawScore(context);
        drawNiveau(context);
        drawTetesMort(context);
        context.setFill(Color.WHITE);
    }

    /**
     * Cette méthode appelle toutes les méthodes de draw et appelle les autres méthodes nécéssaire pour
     * la logique du jeu.
     * @param context GraphicsContext
     * @param partie Partie la partie en question du jeu
     * @param deltaTemps Double La différence de temps
     */
    public void drawInterfaceDeJeu(GraphicsContext context, Partie partie, double deltaTemps) {
        updateTimer(deltaTemps);
        partie.creerEntite();
        double frameRate = 10 * 1e-9;
        context.clearRect(0, 0, MainJavaFX.WIDTH, MainJavaFX.HEIGHT);
        int frame = (int) Math.floor((deltaTemps * frameRate) * 1e12);

        int monstreSize = monstres.size();

        for (int i = 0; i < monstreSize; i++) {
            monstres.get(i).update(deltaTemps);
            monstres.get(i).draw(context);
            if (monstres.get(i).getEffacer()) {
                monstres.remove(i);
            }
            monstreSize = monstres.size();
        }
        bouleMagique.lancerBouleMagique(squelette.getX(), squelette.getY(), context, deltaTemps);
        for (int i = 0; i < monstreSize; i++) {
            bouleMagique.enContact(monstres.get(i), partie);
            squelette.updateNbVies(monstres.get(i));
        }
        squelette.update(deltaTemps);
        squelette.draw(context, frame);
        miseAJourJeu(squelette.getNbVies());
        draw(context);
    }
    public void updateTimer(double deltaTemps) {
        timerPaix = timerPaix + deltaTemps;
        timerMonstresNormaux = timerMonstresNormaux + deltaTemps;
        if (niveau > 1) {
            timerMonstresSpeciaux = timerMonstresSpeciaux + deltaTemps;
        }
    }

    /**
     * Cette méthode s'occupe de créer les vagues de monstres dépendamment du niveau dans lequel on est.
     */
    public void creerEntite() {
        if (!(0 < timerPaix && timerPaix < 3)) {
            if (timerMonstresNormaux > 3) {
                var monstre = new MonstresNormaux(niveau, squelette.getX(), squelette.getY());
                monstres.add(monstre);
                timerMonstresNormaux = timerMonstresNormaux - 3;
            }
            if (timerMonstresSpeciaux > 5) {
                monstreSpeciauxChoix = rd.nextBoolean();
                if (monstreSpeciauxChoix) {
                    monstres.add(new Oeil(niveau));
                } else {
                    var bouche = new Bouche(niveau);
                    monstres.add(bouche);
                }
                timerMonstresSpeciaux = timerMonstresSpeciaux - 5;
            }
        }
    }

    public void setEstFinie(boolean estFinie) {
        this.estFinie = estFinie;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean getEstFinie() {
        return estFinie;
    }
    public void augmenterNbVies(){
        squelette.setNbVies(squelette.getNbVies()+1);
    }
}
