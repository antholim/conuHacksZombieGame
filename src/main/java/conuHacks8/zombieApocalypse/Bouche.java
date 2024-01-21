package conuHacks8.zombieApocalypse;

import javafx.scene.image.Image;

/**
 * Cette classe hérite de la classe monstre est un type de monstre special une trajectoire différente
 */
public class Bouche extends Monstres {
    private double tempsEcoule = 0;
    private double yBase;
    public Bouche(int niveau) {
        imageEntite = new Image("bouche.png");
        positionInitial();
        //Vitesse à changer pour les monstres différents
        vx = 1.3 * (100 * Math.pow(niveau, 0.33) + 200);

        directionInitial();
        directionInitial();


    }

    @Override
    public void update(double deltaTemps) {
        super.update(deltaTemps);
        updatePhysique(deltaTemps);
    }

    /**
     * Cette méthode fait la trajectoire du monstre Bouche pour qu'elle varie selon une fonction sinus
     * @param deltaTemps La différence de temps
     */
    @Override
    public void updatePhysique(double deltaTemps) {

        tempsEcoule += deltaTemps;
        y = yBase + 50 * Math.sin(10 * tempsEcoule)+200;

    }
}


