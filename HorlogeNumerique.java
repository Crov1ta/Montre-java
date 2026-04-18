import java.awt.*;
import java.time.LocalTime;

/**
 * Affichage numérique de l'heure sur le cadran.
 * Remplace les aiguilles quand le mode numérique est sélectionné.
 *
 * @author Mohamed
 * @version 1.0
 */
public class HorlogeNumerique extends ElementMontreAbstrait {

    private int heures;
    private int minutes;
    private int secondes;
    private Color couleur = Color.WHITE;

    @Override
    public void update(LocalTime time) {
        this.heures   = time.getHour();
        this.minutes  = time.getMinute();
        this.secondes = time.getSecond();
    }

    @Override
    public void draw(Graphics2D g2d, int cx, int cy, int rayon) {
        String texte = String.format("%02d:%02d:%02d", heures, minutes, secondes);

        g2d.setFont(new Font("Monospaced", Font.BOLD, rayon / 4));
        FontMetrics fm = g2d.getFontMetrics();
        int tw = fm.stringWidth(texte);

        g2d.setColor(couleur);
        g2d.drawString(texte, cx - tw / 2, cy + fm.getAscent() / 2);
    }

    public Color getCouleur()       { return couleur; }
    public void setCouleur(Color c) { this.couleur = c; }

    @Override
    public String getNom() { return "Horloge numérique"; }
}