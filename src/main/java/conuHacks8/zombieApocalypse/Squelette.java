package conuHacks8.zombieApocalypse;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class Squelette extends EntiteQuiBouge {
    private final double w = 24;
    private final double h = 48;
    private final Image[] listeImagesFrames = new Image[3];
    private Image imageSquelette = new Image("stable.png");
    private double x, y;
    private double accelerationX;
    private double accelerationY = 0;
    private boolean dansLesAirs = true;
    private final double vitesseMax = 300;
    private int nbVies;

    public Squelette() {
        listeFrames();
        this.x = MainJavaFX.WIDTH/2;
        this.y = MainJavaFX.HEIGHT/2;
        this.vx = 0;
        this.nbVies = 3;
        this.accelerationX = 0;
    }

    public void update(double deltaTemps) {
        updatePhysique();
        vy += deltaTemps * accelerationY;
        vx += deltaTemps * accelerationX;
        y += deltaTemps * vy;
        x += deltaTemps * vx;
    }

    /**
     * Cette méthode s'occupe de la physique du squelette. Elle s'assure d'abord que le squelette ne puisse pas
     * sauter 2 fois, que le squelette ne sort pas de l'écran, le pouvoir de le contrôler avec les flèches, etc.
     */
    public void updatePhysique() {
        if (y + h >= MainJavaFX.HEIGHT) {
            y = MainJavaFX.HEIGHT - h;
            vy = 0;
            dansLesAirs = false;
        } else {
            dansLesAirs = true;
        }
        if (y  <= 0) {
            y = 0;
            vy = 0;
            dansLesAirs = false;
        }

        if (x < 0) {
            x = 0;
            vx = -vx;
        } else if (x + w > MainJavaFX.WIDTH) {
            x = MainJavaFX.WIDTH - w;
            vx = -vx;
        }

        boolean left = Input.isKeyPressed(KeyCode.LEFT);
        boolean right = Input.isKeyPressed(KeyCode.RIGHT);
        boolean jump = Input.isKeyPressed(KeyCode.UP);
        boolean down = Input.isKeyPressed(KeyCode.DOWN);

        if (left) {
            accelerationX = -1000;
        } else if (right) {
            accelerationX = 1000;
        } else {
            //Rebondir sur le côté de l'écran
            if (vx > 4)
                accelerationX = -200;
            else if (vx < -4)
                accelerationX = 200;
            else if (Math.abs(vx) <= 4) {
                vx = 0;
                accelerationX = 0;
            }
        }
        if (jump) {
            accelerationY = -1000;
        } else if (down) {
            accelerationY = 1000;
        }
        //Vitesse max
        if (vx >= vitesseMax)
            vx = vitesseMax;
        if (vx <= -vitesseMax)
            vx = -vitesseMax;
        if (vy >= vitesseMax)
            vy = vitesseMax;
        if (vy <= -vitesseMax)
            vy = -vitesseMax;
    }

    public void draw(GraphicsContext context, int frame) {
        if (vx != 0) {
            imageSquelette = listeImagesFrames[frame % listeImagesFrames.length];
        }
        imageSquelette = verifierDirection(context, imageSquelette);
        Image soldier = new Image("space-suit.png");
        Image weapon = new Image("sawed-off-shotgun.png");

        context.drawImage(soldier, x, y, w, h);
        context.drawImage(weapon,x+w, y-5,15, 15);
    }

    /**
     * Cette méthode contient les frames du squelette
     */
    public void listeFrames() {
        listeImagesFrames[0] = new Image("stable.png");
        listeImagesFrames[1] = new Image("marche1.png");
        listeImagesFrames[2] = new Image("marche2.png");
    }

    public boolean checkCollision(Monstres bob){
        boolean collision;
        Rectangle monstre = new Rectangle(bob.getRayon()*2, bob.getRayon()*2);
        Rectangle squelette = new Rectangle(w,h);
        squelette.setX(x);
        squelette.setY(y);
        monstre.setX(bob.getX());
        monstre.setY(bob.getY());
        if(monstre.getBoundsInParent().intersects(squelette.getBoundsInParent())) {
           return true;
        }else {
            return false;
        }
    }
    public void updateNbVies(Monstres monstres){
//        if (monstres.aSurvecu()) {
//            nbVies--;
//        }
//        double dx = tabBoulesMagiques.get(i).getX() - monstre.getX();
//        double dy = tabBoulesMagiques.get(i).getY() - monstre.getY();
//        double dCarre = dx * dx + dy * dy;
//        if (dCarre < (this.rayon + monstre.getRayon()) * (this.rayon + monstre.getRayon())) enContact = true;
//        else enContact = false;
        boolean enContact;
//        double dx = monstres.getX() - x;
//        double dy = monstres.getY() - y;
//        double dCarre = dx * dx + dy * dy;
//        dCarre = monstres.getRayon() * monstres.getRayon();
//        if ((dCarre > (((x) + monstres.getRayon()) * ((x) + monstres.getRayon())))&& (dCarre > (((y+(h/2)) + monstres.getRayon()) * ((y+(h/2) + monstres.getRayon())))) ){
//            enContact = true;
//            System.out.println("there is a collision");
//
//        }
//
//        else enContact = false;
//        System.out.println("the distance is " +dCarre);
//        System.out.println("the other distance is " + ((x) + monstres.getRayon()) * ((x) + monstres.getRayon()));
//        if (enContact) {
//            nbVies--;
//        }
       if (checkCollision(monstres)){
           nbVies--;
           monstres.setEffacer(true);
       }
    }
    public int getNbVies() {
        return nbVies;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setNbVies(int nbVies) {
        this.nbVies = nbVies;
    }
}