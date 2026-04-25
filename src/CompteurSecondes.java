import java.awt.*;
import java.time.LocalTime;

/**
 * Affichage numérique des secondes en bas du cadran.
 */
public class CompteurSecondes extends ElementMontreAbstrait {

    private int secondes;

    @Override
    public void update(LocalTime time) {
        this.secondes = time.getSecond();
    }

    @Override
    public void draw(Graphics2D g2d, int cx, int cy, int rayon) {
        String texte = String.format("%02d", secondes);

        g2d.setFont(new Font("Monospaced", Font.BOLD, 18));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth  = fm.stringWidth(texte);
        int textHeight = fm.getAscent();

        int x = cx - textWidth / 2;
        int y = cy + rayon / 2 + textHeight / 2;

        // Fond
        g2d.setColor(new Color(0, 0, 0, 180));
        g2d.fillRoundRect(x - 6, y - textHeight - 2, textWidth + 12, textHeight + 8, 6, 6);

        // Texte
        g2d.setColor(Color.CYAN);
        g2d.drawString(texte, x, y);
    }


    @Override
    public String getNom() {
        return "Compteur numérique secondes";
    }
}
