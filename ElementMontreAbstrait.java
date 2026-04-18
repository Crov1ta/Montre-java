import java.awt.Color;
import java.awt.Graphics2D;
import java.time.LocalTime;
import javax.swing.JPanel;

/**
 * Classe abstraite fournissant une implémentation par défaut de configure().
 * Toutes les classes d'éléments de montre héritent de cette classe.
 *
 * @author Mohamed
 * @version 1.0
 */
public abstract class ElementMontreAbstrait implements ElementMontre {

    @Override
    public void configure(JPanel panel) {
        // Par défaut : aucune option de configuration
    }

    @Override
    public void setCouleur(Color c) {
        // Par défaut : ignoré
    }
}
