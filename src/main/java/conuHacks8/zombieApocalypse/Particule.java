package conuHacks8.zombieApocalypse;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
/**
 * Classe particule calcule la force electrique, fait des mises a jours de la physique et dessine une particule
 * extends EntiteQuiBouge puisque les particules se deplacent
 */

public class Particule extends EntiteQuiBouge {
    private Color couleur;

    private final int rayon = 5;
    private double ax, ay;
    private final double k = 30;
    private final double q1 = 10;
    private double forceEnX;
    private double forceEnY;
    /**
     * Constructeur d'une particule
     * @param x la position x initiale dans la boule magique
     * @param y la position y intiale dans la boule magique
     * @param couleur couleur attribuee
     */
    public Particule(double x, double y, Color couleur) {
        this.couleur = couleur;
        this.x = x;
        this.y = y;
    }
    /**
     * Dessine une Particule en additionant les composantes de la boule et celles d'une particule et en ajoutant une couleur
     * @param context graphic context
     * @param bouleMagiqueX est la position en x de la boule magique
     * @param bouleMagiqueY est la position en y de la boule magique
     */
    public void draw(GraphicsContext context, double bouleMagiqueX, double bouleMagiqueY) {
        context.fillOval(
                bouleMagiqueX+x,
                bouleMagiqueY+y,
                2 * rayon, 2 * rayon);
        context.setFill(couleur);
    }
    /**
     * Calcule la force electrique en x et en y a l'aide de la loi de coulomb pour toutes les particules
     * @param tabToutesParticules tableau contenant toutes les particules
     */
    public void loiDeCoulomb(ArrayList<Particule> tabToutesParticules) {
        double forceElectrique = 0;
        for (int i = 0; i < tabToutesParticules.size(); i++) {
            double deltaX = x-tabToutesParticules.get(i).x;
            double deltaY = y-tabToutesParticules.get(i).y;
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            if (distance < 0.01)
                distance = 0.01;
            double proportionY = deltaY / distance;
            double proportionX = deltaX / distance;
            forceElectrique += ((k * q1 * q1) / distance);
            forceEnX = forceElectrique*proportionX;
            forceEnY = forceElectrique*proportionY;
        }
    }
    /**
     * Mise a jour de la physique (positions, vitesse, etc...)
     * @param dt est la variation de temps
     */
    public void updatePhysique(double dt) {
        ax = forceEnX*1e-3;
        ay = forceEnY*1e-3;
        vx += dt * ax;
        vy += dt * ay;
        x += dt * vx;
        y += dt * vy;

        // Valide que la balle ne termine jamais hors des côtés de l'écran
        x = Math.min(x, MainJavaFX.WIDTH - rayon);
        x = Math.max(x, rayon);
        y = Math.min(y, MainJavaFX.HEIGHT - rayon);
        y = Math.max(y, rayon);
    }
    /**
     * Mise a jour d'une particule avec toutes ses conditions
     * @param dt est la variation de temps
     */
    public void update(double dt) {
        updatePhysique(dt);
        if (x * x + y * y >= 35 * 35) {
            x = 0;
            y = 0;
            ay = 0;
            ax = 0;
            vx = 0;
            vy = 0;
        }
        if (vx < -50)
            vx = -50;
        if (vx > 50)
            vx = 50;
        vy += dt * ay;
        if (vy < -50)
            vy = -50;
        if (vy > 50)
            vy = 50;
    }
}
