package conuHacks8.zombieApocalypse;

import javafx.scene.image.Image;
/**
 * Cette classe hérite de la classe monstre est un type de monstre special une trajectoire différente
 */
public class Oeil extends Monstres{
    double timerOeil = 0;
    public Oeil(int niveau) {
        imageEntite = new Image("oeil.png");
        positionInitial();
        vx = 100 * Math.pow(niveau, 0.33) + 200;
        directionInitial();
    }
    @Override
    public void update(double deltaTemps) {
        updatePhysique(deltaTemps);
        super.update(deltaTemps);
    }

    /**
     * Cette méthode fait la trajectoire du monstre Oeil, qui est différente des monstre normaux
     * @param deltaTemps Double La différence de temps
     */
    @Override
    public void updatePhysique(double deltaTemps) {
        timerOeil = timerOeil + deltaTemps;

        if (!sensPersonnage) {
            if (timerOeil > 0.5 && vx > 0) {
                vx = -vx;
            } else if (timerOeil > 0.75) {
                vx = -vx;
                timerOeil = timerOeil - 0.75;
            }
        } else {
            if (timerOeil > 0.5 && vx < 0) {
                vx = -vx;
            } else if (timerOeil > 0.75) {
                vx = -vx;
                timerOeil = timerOeil - 0.75;
            }
        }
    }
}

