import java.awt.Color;
import javax.swing.JPanel;

/**
 * Classe abstraite fournissant une implémentation par défaut de configure().
 * Toutes les classes d'éléments de montre héritent de cette classe.
 *
 * @author Mohamed
 * @version 1.0
 */
public abstract class ElementMontreAbstrait implements ElementMontre {

 
    public void configure(JPanel panel) {}

    public void setCouleur(Color c) {}
    
    public void setPolice (String p){}
}
