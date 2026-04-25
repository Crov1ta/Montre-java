import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.time.LocalTime;
import javax.swing.JPanel;

/**
 * Interface représentant un élément affichable sur une montre analogique.
 * Chaque élément peut se mettre à jour, se dessiner et se configurer via l'UI.
 */
public interface ElementMontre extends Serializable {

    void update(LocalTime time);
 
    void draw(Graphics2D g2d, int cx, int cy, int rayon);
 
    void configure(JPanel panel);
 
    String getNom();

    public void setCouleur( Color c);   

    public void setPolice (String p);

    
}