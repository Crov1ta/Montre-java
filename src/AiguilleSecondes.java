import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.time.LocalTime;

/**
 * Aiguille des secondes d'une montre analogique.
 * Surcharge draw() pour ajouter une queue derrière le centre.
 */
public class AiguilleSecondes extends Aiguille {

    private int secondes;

    public AiguilleSecondes() {
        super(Color.RED, 2f, 0.90);
    }

    @Override
    public void update(LocalTime time) {
        this.secondes = time.getSecond();
    }
    @Override
    protected double calculerAngle() {
        return Math.toRadians((secondes * 6) - 90);
    }

    /** Surcharge pour ajouter la queue et le point central. */
    @Override
    public void draw(Graphics2D g2d, int cx, int cy, int rayon) {
        double angle    = calculerAngle();
        int    longueur = (int) (rayon * ratioLongueur);
        int    x        = cx + (int) (longueur * Math.cos(angle));
        int    y        = cy + (int) (longueur * Math.sin(angle));

        int xQueue = cx - (int) ((rayon * 0.15) * Math.cos(angle));
        int yQueue = cy - (int) ((rayon * 0.15) * Math.sin(angle));

        g2d.setColor(couleur);
        g2d.setStroke(new BasicStroke(epaisseur, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawLine(xQueue, yQueue, x, y);
        g2d.fillOval(cx - 4, cy - 4, 8, 8);
    }

    @Override
    public String getNom() {
        return "Aiguille des secondes";
    }
}
