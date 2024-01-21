package conuHacks8.zombieApocalypse;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
/**
 * Cette classe lance des boules magiques contenant des particules a l'interieur quand on appuie sur espacce
 * extends EntiteQuiBouge car elle se deplace
 */
public class BouleMagique extends EntiteQuiBouge {
    private Color couleur;
    private double timer;
    private ArrayList<BouleMagique> tabBoulesMagiques = new ArrayList<>();
    private boolean enContact;
    private boolean dessiner = false;
    private boolean estRecharge;
    private final int rayon = 35;
    private ArrayList<Particule> tabParticulesAutourBoule = new ArrayList<>();
    private ArrayList<Particule> tabParticuleVisibles = new ArrayList<>();
    private ArrayList<Particule> tabToutesParticules = new ArrayList<>();
    private final double tempsDeRecharge = 0.6;
    /**
     * Constructeur de la boule magique, on initalise le tableau contenant toutes les particules
     * @param x est la position x du squelette quand on appuie sur espace
     * @param y est la position y de la balle au depart
     */
    public BouleMagique(double x, double y) {
        this.x = x;
        this.y = y ;
        this.vy = -300;

        initialisationTableauParticules();
    }
    /**
     * Retourne la position en x d'une particule de tableau tabParticulesAutourBoule selon son angle
     * @param compteur est la position de la partcule dans le tableau tabParticulesAutourBoule
     * @return la valeur x de la particule
     */
    public double particulesXAutourCercle(int compteur){
        double degresParParticule = (2 * Math.PI)/100;//360 degres divises par 100 particules
        double x= 0;
        for (int i = 0; i<100; i++)
            x = rayon*Math.cos(degresParParticule*compteur);
        return x;
    }
    /**
     * Retourne la position en y d'une particule de tableau tabParticulesAutourBoule selon son angle
     * @param compteur est la position de la partcule dans le tableau tabParticulesAutourBoule
     * @return la valeur y de la particule
     */
    public double particulesYAutourCercle(int compteur){
        double degresParParticule = (2 * Math.PI)/100;//360 degres divises par 100 particules
        double y = 0;
        y = rayon*Math.sin(degresParParticule*compteur);
        return y;
    }
    /**
     * Retourne une position aleatoire en x d'une particule dans la boule magique de tableau tabParticulesVisibles
     * @return la valeur x de la particule
     */
    public double randomXParticule() {
        if (Math.random() > 0.5) return (Math.random() * rayon);
        else return -Math.random() * rayon;
    }
    /**
     * Retourne une position aleatoire en y d'une particule dans la boule magique de tableau tabParticulesVisibles
     * @return la valeur y de la particule
     */
    public double randomYParticule(double valeurX) {
        double maxY = Math.sqrt((rayon) * (rayon) - valeurX * valeurX);
        if (Math.random() > 0.5) return Math.random() * maxY;
        else return -Math.random() * maxY;
    }
    /**
     * Initialisation des 3 tableaux de particules
     */
    public void initialisationTableauParticules() {
        couleur = couleurRandom();
        for (int i = 0; i < 100; i++) {
            Particule particule = new Particule(particulesXAutourCercle(i), particulesYAutourCercle(i),couleur);
            tabParticulesAutourBoule.add(particule);//retourner en parametres valeurX d'une particule autour sur la circonference cercle, pour l'instant retourner random x/y
            tabToutesParticules.add(particule);
        }
        for (int i = 0; i < 15; i++) {
            double valeurX = randomXParticule();
            Particule particule = new Particule(valeurX, randomYParticule(valeurX), couleur);
            tabParticuleVisibles.add(particule);//retourner en parametres valeurX d'une particule autour sur la circonference cercle, pour l'instant retourner random x/y
            tabToutesParticules.add(particule);
        }
    }
    /**
     * Fait les mises a jours de toutes les particules, de la force electrique, du timer, de la position y de la boule magique
     * @param deltaTemps
     */
    public void update(double deltaTemps) {
        timer = timer + deltaTemps;
        y += deltaTemps * vy;
        for (int i = 0; i < tabToutesParticules.size(); i++) {
            tabToutesParticules.get(i).update(deltaTemps);
        }
        for (Particule particule : tabParticuleVisibles) {
            tabToutesParticules.remove(particule);
            particule.loiDeCoulomb(tabToutesParticules);
            tabToutesParticules.add(particule);
        }
    }
    /**
     * Dessine la boule et les particules
     * @param context GraphicsContext
     * @param deltaTemps la variation de temps
     */
    public void draw(GraphicsContext context, double deltaTemps) {
        update(deltaTemps);
        for (int i = 0; i < tabParticuleVisibles.size(); i++) {
            tabParticuleVisibles.get(i).draw(context, x, y);
        }
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }
    /**
     * Verifie si on peut relancer une boule et on remet le timer a 0 si timer > tempsDeRecharge
     * @return estRecharge
     */
    public boolean etatDeCharge() {
        if (timer > tempsDeRecharge) {
            estRecharge = true;
            timer = timer - 0.6;
        }
        return estRecharge;
    }

    /**
     * Cette méthode retourne une couleur aléatoire selon une liste de notre choix
     * @return Color Couleur au hasard
     */
    public Color couleurRandom(){
        Color[]colors = {Color.BLUE,Color.GREEN,Color.RED,Color.ORANGE,Color.GOLD,Color.YELLOW,Color.PINK,Color.PURPLE,Color.ROYALBLUE};
        Color couleur = colors[rd.nextInt(colors.length)];
        return couleur;
    }
    /**
     * Lance des boules magiques contenant des particules si les conditions sont respectees
     * @param xSquelette est la position X du squelette quand la boule est lancee
     * @param ySquelette est la position Y de depart
     * @param context
     * @param deltaTemps est la variation de temps
     */
    public void lancerBouleMagique(double xSquelette, double ySquelette, GraphicsContext context, double deltaTemps) {
        update(deltaTemps);
        boolean estTiree = false;
        if (etatDeCharge()) {
            if (Input.isKeyPressed(KeyCode.SPACE)) {
                tabBoulesMagiques.add(new BouleMagique(xSquelette, ySquelette));
                dessiner = true;
                estTiree = true;
            }
        }
        if (estTiree) {
            estRecharge = false;
            timer = 0;
        }
        if (dessiner) {
            for (int i = 0; i < tabBoulesMagiques.size(); i++) {
                if (tabBoulesMagiques.get(i).getY() > 0) tabBoulesMagiques.get(i).draw(context, deltaTemps);
            }
        }
    }

    /**
     * Cette méthode permet de vérifier si un monstre et la boule magique sont en contact
     * @param monstre Monstre Le monstre auquel on doit véfier la collision
     * @param partie Partie La partie qui est entrain de jouer
     */
    public void enContact(Monstres monstre, Partie partie) {
        int tabBoulesMagiquesSize = tabBoulesMagiques.size();
        for (int i = 0; i < tabBoulesMagiquesSize; i++) {
            double dx = tabBoulesMagiques.get(i).getX() - monstre.getX();
            double dy = tabBoulesMagiques.get(i).getY() - monstre.getY();
            double dCarre = dx * dx + dy * dy;
            if (dCarre < (this.rayon + monstre.getRayon()) * (this.rayon + monstre.getRayon())) enContact = true;
            else enContact = false;
            if (enContact) {
                partie.miseAJourScore();
                monstre.setEffacer(true);
            }
        }
    }
}