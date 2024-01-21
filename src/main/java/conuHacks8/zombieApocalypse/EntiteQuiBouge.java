package conuHacks8.zombieApocalypse;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public abstract class EntiteQuiBouge {
    protected Random rd = new Random();
    protected double x, y;
    protected double w, h;
    protected double vx, vy;
    protected Image imageEntite;
    //True = Vers Gauche, False = Vers Droite
    protected boolean sensPersonnage;

    /**
     * Cette méthode vérifie la direction d'un quelcon image. Si c'est la cas, il retournera l'image de l'autre sens
     * @param context GrahicsContext
     * @param image Image (Celui à vérifier)
     * @return Un Image
     */
    public Image verifierDirection(GraphicsContext context, Image image) {
        if (vx < 0) {
            image = ImageHelpers.flop(image);
            sensPersonnage = true;
        } else if (vx > 0) {
            sensPersonnage = false;
        }
        context.drawImage(image, x, y, w, h);
        return image;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}
