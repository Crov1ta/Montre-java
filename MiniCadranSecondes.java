import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.time.LocalTime;

/**
 * Mini cadran analogique des secondes, affiché dans le coin du cadran principal.
 */
public class MiniCadranSecondes extends ElementMontreAbstrait {

    private int secondes;
    private final ReglagesMontre reglages;
    private Color couleurFond = Color.BLACK;

    public MiniCadranSecondes(ReglagesMontre reglages) {
        this.reglages = reglages;
    }

    @Override
    public void update(LocalTime time) {
        this.secondes = time.getSecond();
    }

    @Override
    public void draw(Graphics2D g2d, int cx, int cy, int rayon) {
        int miniRayon = rayon / 5;
        int miniCx = cx;
        int miniCy = cy + rayon / 2;

        // Fond du mini cadran
        g2d.setColor(couleurFond);
        g2d.fill(new Ellipse2D.Double(miniCx - miniRayon, miniCy - miniRayon, miniRayon * 2, miniRayon * 2));

        // Contour
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.draw(new Ellipse2D.Double(miniCx - miniRayon, miniCy - miniRayon,miniRayon * 2, miniRayon * 2));

        // Aiguille des secondes
        double angle = Math.toRadians((secondes * 6) - 90);
        int longueur = (int) (miniRayon * 0.8);
        int x = miniCx + (int) (longueur * Math.cos(angle));
        int y = miniCy + (int) (longueur * Math.sin(angle));

        g2d.setColor(reglages.getCouleurAiguilles());
        g2d.setStroke(new BasicStroke(1.5f * reglages.getFacteurEpaisseur(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawLine(miniCx, miniCy, x, y);

        // Centre
        g2d.fillOval(miniCx - 2, miniCy - 2, 4, 4);
    }


    @Override
    public String getNom() {
        return "Mini cadran secondes";
    }
}
