import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Classe abstraite représentant une aiguille de montre.
 * Utilise le patron Template Method : draw() appelle calculerAngle()
 * que chaque sous-classe concrète doit implémenter.
 *
 * @author Mohamed
 * @version 1.0
 */
public abstract class Aiguille extends ElementMontreAbstrait {

    protected  Color  couleur;
    protected  float  epaisseur;
    protected  double ratioLongueur; // fraction du rayon

    protected Aiguille(Color couleur, float epaisseur, double ratioLongueur) {
        this.couleur       = couleur;
        this.epaisseur     = epaisseur;
        this.ratioLongueur = ratioLongueur;
    }

    /**
     * Calcule l'angle de l'aiguille en radians.
     * Chaque sous-classe fournit sa propre formule.
     */
    protected abstract double calculerAngle();

    @Override
    public void draw(Graphics2D g2d, int cx, int cy, int rayon) {
        double angle   = calculerAngle();
        int    longueur = (int) (rayon * ratioLongueur);
        int    x        = cx + (int) (longueur * Math.cos(angle));
        int    y        = cy + (int) (longueur * Math.sin(angle));

        g2d.setColor(couleur);
        g2d.setStroke(new BasicStroke(epaisseur, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawLine(cx, cy, x, y);
    }
    @Override
    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }
    public void setEpesseur( float ep){
        this.epaisseur = ep ;
    }
    public void setratioLongueur( double ratio){
        this.ratioLongueur = ratio ;
    }
}
