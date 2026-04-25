import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Affiche la date — sur le cadran via draw() ou en pleine page via drawPage().
 * Supporte plusieurs formats d'affichage via l'enum FormatDate.
 */
public class DateDisplay extends ElementMontreAbstrait {

    /** Formats de date disponibles. */
    public enum FormatDate {
        COURT        ("dd/MM/yyyy",          "Court  — DD/04/2026"),
        LONG         ("dd MMMM yyyy",        "Long   — 19 avril 2026"),
        AVEC_JOUR    ("EEEE dd MMMM yyyy",   "Complet — dimanche 19 avril 2026"),
        AMERICAIN    ("MM/dd/yyyy",           "Américain — 04/19/2026"),
        ISO          ("yyyy-MM-dd",           "ISO — 2026-04-19"),
        JOUR_ANNEE   ("'Jour' DDD 'de' yyyy", "Jour de l'année — Jour 109 de 2026");

        public final String pattern;
        public final String libelle;

        FormatDate(String pattern, String libelle) {
            this.pattern = pattern;
            this.libelle = libelle;
        }

        @Override
        public String toString() { return libelle; }
    }

    private FormatDate formatActuel = FormatDate.COURT;
    private String     dateTexte    = "";

    public void setFormat(FormatDate f) {
        this.formatActuel = f;
        // Force le recalcul au prochain draw
        dateTexte = "";
    }

    public FormatDate getFormat() { return formatActuel; }

    private String formater() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(
                formatActuel.pattern, Locale.FRENCH);
        return LocalDate.now().format(fmt);
    }

    @Override
    public void update(LocalTime time) {
        dateTexte = formater();
    }

    /** Affichage sur le cadran (petit). */
    @Override
    public void draw(Graphics2D g2d, int cx, int cy, int rayon) {
        if (dateTexte.isEmpty()) dateTexte = formater();

        g2d.setFont(new Font("Arial", Font.PLAIN, 13));
        FontMetrics fm = g2d.getFontMetrics();
        int tw = fm.stringWidth(dateTexte);
        int th = fm.getAscent();
        int x  = cx + rayon / 3 - tw / 2;
        int y  = cy + th / 2;

        g2d.setColor(Color.BLUE);
        g2d.fillRect(x - 4, y - th, tw + 8, th + 4);
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(1f));
        g2d.drawRect(x - 4, y - th, tw + 8, th + 4);
        g2d.drawString(dateTexte, x, y);
    }

    /** Affichage pleine page. */
    public void drawPage(Graphics2D g2d, int cx, int cy) {
        if (dateTexte.isEmpty()) dateTexte = formater();

        // Ligne principale : date
        g2d.setFont(new Font("Arial", Font.BOLD, 42));
        FontMetrics fm = g2d.getFontMetrics();
        // Si le texte est trop long (format complet), on réduit la police
        while (fm.stringWidth(dateTexte) > 320 && g2d.getFont().getSize() > 18) {
            g2d.setFont(g2d.getFont().deriveFont((float)(g2d.getFont().getSize() - 2)));
            fm = g2d.getFontMetrics();
        }
        g2d.setColor(Color.WHITE);
        g2d.drawString(dateTexte, cx - fm.stringWidth(dateTexte) / 2, cy);

        // Ligne secondaire : jour de la semaine (sauf si déjà inclus dans le format)
        if (formatActuel != FormatDate.AVEC_JOUR) {
            String jour = LocalDate.now()
                    .getDayOfWeek()
                    .getDisplayName(TextStyle.FULL, Locale.FRENCH);
            g2d.setFont(new Font("Arial", Font.PLAIN, 22));
            fm = g2d.getFontMetrics();
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawString(jour, cx - fm.stringWidth(jour) / 2, cy + 50);
        }
    }

    @Override
    public String getNom() { return "Date"; }
}