import java.awt.*;
import java.time.LocalTime;

/**
 * État de la batterie simulé — cadran via draw(), pleine page via drawPage().
 *
 * @author Mohamed
 * @version 2.0
 */
public class EtatBatterie extends ElementMontreAbstrait {

    private int pourcentage = 100;
    private int tickCount   = 0;

    @Override
    public void update(LocalTime time) {
        tickCount++;
        if (tickCount >= 60 && pourcentage > 0) {
            tickCount = 0;
            pourcentage--;
        }
    }

    /** Affichage sur le cadran (petit). */
    @Override
    public void draw(Graphics2D g2d, int cx, int cy, int rayon) {
        int bw = 36, bh = 14, tw = 4, th = 6;
        int x  = cx - rayon / 2 - bw / 2;
        int y  = cy - bh / 2;

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRect(x, y, bw, bh);
        g2d.fillRect(x + bw, y + (bh - th) / 2, tw, th);

        g2d.setColor(getCouleurBarre());
        int fill = (int) (bw * pourcentage / 100.0);
        g2d.fillRect(x + 2, y + 2, fill - 2, bh - 4);

        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        FontMetrics fm = g2d.getFontMetrics();
        String texte = pourcentage + "%";
        g2d.setColor(Color.WHITE);
        g2d.drawString(texte, cx - rayon / 2 - fm.stringWidth(texte) / 2,
                       y + bh + fm.getAscent() + 2);
    }

    /** Affichage pleine page. */
    public void drawPage(Graphics2D g2d, int cx, int cy) {
        // Grande barre de batterie
        int bw = 200, bh = 60, tw = 12, th = 30;
        int x  = cx - bw / 2;
        int y  = cy - bh / 2;

        // Contour
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(3f));
        g2d.drawRect(x, y, bw, bh);

        // Embout
        g2d.fillRect(x + bw, y + (bh - th) / 2, tw, th);

        // Remplissage
        g2d.setColor(getCouleurBarre());
        int fill = (int) (bw * pourcentage / 100.0);
        if (fill > 4) g2d.fillRect(x + 3, y + 3, fill - 3, bh - 6);

        // Pourcentage centré dans la barre
        g2d.setFont(new Font("Arial", Font.BOLD, 28));
        FontMetrics fm = g2d.getFontMetrics();
        String texte = pourcentage + "%";
        g2d.setColor(Color.WHITE);
        g2d.drawString(texte, cx - fm.stringWidth(texte) / 2, cy + fm.getAscent() / 2 - 2);

        // Indication en dessous
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        String info = "État de la batterie";
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawString(info, cx - fm.stringWidth(info) / 2, cy + bh / 2 + 40);
    }

    private Color getCouleurBarre() {
        if (pourcentage > 50) return Color.GREEN;
        if (pourcentage > 20) return Color.ORANGE;
        return Color.RED;
    }

    @Override
    public String getNom() { return "État de la batterie"; }
}