import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

/**
 * Panneau Swing qui dessine le cadran de la montre.
 * Adapte son rendu selon les ReglagesMontre (forme, fond, mode d'affichage).
 */
public class WatchPanel extends JPanel {

    private WatchModel     modele;
    private ReglagesMontre reglages;

    public WatchPanel(WatchModel modele, ReglagesMontre reglages) {
        this.modele   = modele;
        this.reglages = reglages;
        setPreferredSize(new Dimension(350, 350));
        setBackground(Color.DARK_GRAY);
    }

    public void setModele(WatchModel modele) {
        this.modele = modele;
        repaint();
    }
    
    public void setReglages(ReglagesMontre reglages) {
        this.reglages = reglages;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        int cx    = getWidth()  / 2;
        int cy    = getHeight() / 2;
        int rayon = Math.min(getWidth(), getHeight()) / 2 - 10;

        boolean estRond = reglages.getFormeBoitier() == ReglagesMontre.Forme.ROND;

        // 1. Fond du cadran (uniforme ou dégradé)
        Shape forme = estRond
            ? new Ellipse2D.Double(cx - rayon, cy - rayon, rayon * 2, rayon * 2)
            : new RoundRectangle2D.Double(cx - rayon, cy - rayon, rayon * 2, rayon * 2, 20, 20);

        if (reglages.getTypeFond() == ReglagesMontre.TypeFond.IMAGE
                && reglages.getImageFond() != null) {
            // Clipper l'image à la forme du cadran pour qu'elle reste à l'intérieur
            Shape ancienClip = g2d.getClip();
            g2d.setClip(forme);
            g2d.drawImage(reglages.getImageFond(),
                          cx - rayon, cy - rayon, rayon * 2, rayon * 2, null);
            g2d.setClip(ancienClip);
        } else if (reglages.getTypeFond() == ReglagesMontre.TypeFond.DEGRADE) {
            GradientPaint degrade = new GradientPaint(
                cx, cy - rayon, reglages.getCouleurFond(),
                cx, cy + rayon, reglages.getCouleurFond2());
            g2d.setPaint(degrade);
            g2d.fill(forme);
        } else {
            g2d.setColor(reglages.getCouleurFond());
            g2d.fill(forme);
        }

        // 2. Contour
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(3f));
        g2d.draw(forme);

        // 3. Nombres (1-12) — uniquement en mode analogique
        if (reglages.getTypeAffichage() == ReglagesMontre.TypeAffichage.ANALOGIQUE) {
            g2d.setColor(reglages.getCouleurNombres());
            g2d.setFont(new Font("Arial", Font.BOLD, 16));

            for (int i = 1; i <= 12; i++) {
                double angle = Math.toRadians(i * 30 - 90);
                int x = (int) (cx + (rayon - 30) * Math.cos(angle));
                int y = (int) (cy + (rayon - 30) * Math.sin(angle));

                String text = String.valueOf(i);
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(text, x - fm.stringWidth(text) / 2, y + fm.getAscent() / 2);
            }
        }

        // 4. Chaque élément se dessine (aiguilles, horloge numérique, etc.)
        for (ElementMontre element : modele.getElements()) {
            element.draw(g2d, cx, cy, rayon);
        }
    }

}