import java.awt.*;
import java.time.LocalTime;
import java.util.Random;

/**
 * Rythme cardiaque simulé — cadran via draw(), pleine page via drawPage().
 *
 * @author Mohamed
 * @version 2.0
 */
public class RythmeCardiaque extends ElementMontreAbstrait {

    private int bpm = 72;
    private final Random random = new Random();
    private int tickCount = 0;

    @Override
    public void update(LocalTime time) {
        tickCount++;
        if (tickCount % 5 == 0) {
            bpm = 65 + random.nextInt(30);
        }
    }

    /** Affichage sur le cadran (petit). */
    @Override
    public void draw(Graphics2D g2d, int cx, int cy, int rayon) {
        String texte = "\u2665 " + bpm + " bpm";
        g2d.setFont(new Font("Arial", Font.BOLD, 13));
        FontMetrics fm = g2d.getFontMetrics();
        int x = cx - fm.stringWidth(texte) / 2;
        int y = cy - rayon / 3;

        g2d.setColor(new Color(180, 0, 0, 160));
        g2d.fillRoundRect(x - 5, y - fm.getAscent(),
                          fm.stringWidth(texte) + 10, fm.getAscent() + 6, 8, 8);
        g2d.setColor(Color.WHITE);
        g2d.drawString(texte, x, y);
    }

    /** Affichage pleine page. */
    public void drawPage(Graphics2D g2d, int cx, int cy) {
        // Gros cœur
        g2d.setFont(new Font("Arial", Font.PLAIN, 80));
        String coeur = "\u2665";
        FontMetrics fm = g2d.getFontMetrics();
        g2d.setColor(Color.RED);
        g2d.drawString(coeur, cx - fm.stringWidth(coeur) / 2, cy - 20);

        // BPM en grand
        g2d.setFont(new Font("Arial", Font.BOLD, 52));
        String bpmTexte = bpm + " bpm";
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.WHITE);
        g2d.drawString(bpmTexte, cx - fm.stringWidth(bpmTexte) / 2, cy + 60);

        // Indication
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        String info = "Rythme cardiaque";
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawString(info, cx - fm.stringWidth(info) / 2, cy + 100);
    }

    @Override
    public String getNom() { return "Rythme cardiaque"; }
}