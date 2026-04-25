import java.awt.Color;
import javax.swing.JPanel;

/**
 * Classe abstraite fournissant une implémentation par défaut de configure().
 * Toutes les classes d'éléments de montre héritent de cette classe.
 */
public abstract class ElementMontreAbstrait implements ElementMontre {

 
    public void configure(JPanel panel) {}

    public void setCouleur(Color c) {}
    
    public void setPolice (String p){}
}
