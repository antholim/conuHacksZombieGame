package conuHacks8.zombieApocalypse;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

/**
 * Cette classe abstraite groupe tous les différents types de monstres
 */
public abstract class Monstres extends EntiteQuiBouge {
    Random r = new Random();
    protected double xSquellette;
    protected double ySquellette;
    protected boolean effacer = false;
    protected boolean aSurvecu = false;
    protected Random rd = new Random();
    protected double rayon;
    protected ArrayList<Image> listeImagesMonstre = new ArrayList();
    protected int choixDuMonstre;


    public void update(double deltaTemps) {
        x += deltaTemps * vx;
        y+= deltaTemps * vy;
    }

    public abstract void updatePhysique(double deltaTemps);

    public void draw(GraphicsContext context) {
        context.drawImage(imageEntite, x, y, w, h);
    }

    public Image directionPersonnage(Image monstre) {
        if (vx < 0) {
            monstre = ImageHelpers.flop(monstre);
        }
        return monstre;
    }

    /**
     * Cette méthode décide aléatoirement le sens du monstre
     */

    public void directionInitial() {
        x = 0 - w;
//        sensPersonnage = rd.nextBoolean();
//
//        if (sensPersonnage) {
//            x = MainJavaFX.WIDTH;
//            vx = -vx;
//        }
//        imageEntite = directionPersonnage(imageEntite);
        int random = r.nextInt(20);
        //if (random == 0){
            if (xSquellette < x){
                vx = -vx;
            }
            if (ySquellette > y){
                vy = 100;
            }
            if (ySquellette < y){
                vy = -100;
            }
        System.out.println("position du squellete : " +xSquellette);
        System.out.println("position du monstre "+ y );
        //}
    }

    /**
     * Cette méthode décide aléatoirement la position initiale du monstre
     */
    public void positionInitial() {
        rayon = rd.nextInt(50 - 20) + 20;
        w = rayon * 2;
        h = rayon * 2;
        y = MainJavaFX.HEIGHT * 2 / 5 + (MainJavaFX.HEIGHT * 4 / 5 - MainJavaFX.HEIGHT / 5) * rd.nextDouble();
        if (y + h + 96 > MainJavaFX.HEIGHT) {
            y = y - h - 96;
        } else if (y - h - 96 < 0) {
            y = y + h + 96;
        }
    }

    public double getRayon() {
        return rayon;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Cette méthode vérifie si le monstre a survécu
     * @return Un boolean
     */
    public boolean aSurvecu() {
//        if (x < 0 - w || x > MainJavaFX.WIDTH) {
//            aSurvecu = true;
//            effacer = true;
//        } else  {
//            aSurvecu = false;
//        }

        return aSurvecu;
    }

    public boolean getEffacer() {
        return effacer;
    }
    public void setEffacer(boolean effacer) {
        this.effacer = effacer;
    }
}
