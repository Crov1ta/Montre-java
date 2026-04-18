import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Affiche la date — sur le cadran via draw() ou en pleine page via drawPage().
 *
 * @author Mohamed
 * @version 2.0
 */
public class DateDisplay extends ElementMontreAbstrait {

    private String dateTexte = "";
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void update(LocalTime time) {
        dateTexte = LocalDate.now().format(FORMAT);
    }

    /** Affichage sur le cadran (petit). */
    @Override
    public void draw(Graphics2D g2d, int cx, int cy, int rayon) {
        if (dateTexte.isEmpty()) dateTexte = LocalDate.now().format(FORMAT);

        g2d.setFont(new Font("Arial", Font.PLAIN, 13));
        FontMetrics fm = g2d.getFontMetrics();
        int tw = fm.stringWidth(dateTexte);
        int th = fm.getAscent();
        int x  = cx + rayon / 3 - tw / 2;
        int y  = cy + th / 2;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(x - 4, y - th, tw + 8, th + 4);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1f));
        g2d.drawRect(x - 4, y - th, tw + 8, th + 4);
        g2d.drawString(dateTexte, x, y);
    }

    /** Affichage pleine page. */
    public void drawPage(Graphics2D g2d, int cx, int cy) {
        if (dateTexte.isEmpty()) dateTexte = LocalDate.now().format(FORMAT);

        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        FontMetrics fm = g2d.getFontMetrics();
        int tw = fm.stringWidth(dateTexte);

        g2d.setColor(Color.WHITE);
        g2d.drawString(dateTexte, cx - tw / 2, cy);

        // Jour de la semaine en dessous
        String jour = LocalDate.now()
            .getDayOfWeek()
            .getDisplayName(java.time.format.TextStyle.FULL,
                            java.util.Locale.FRENCH);
        g2d.setFont(new Font("Arial", Font.PLAIN, 22));
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawString(jour, cx - fm.stringWidth(jour) / 2, cy + 50);
    }

    @Override
    public String getNom() { return "Date"; }
}