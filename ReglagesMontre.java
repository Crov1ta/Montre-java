import java.awt.Color;
import java.io.Serializable;

/**
 * Stocke la configuration visuelle de la montre.
 * Utilisée par WatchPanel pour adapter le rendu.
 *
 * @author Mohamed
 * @version 1.0
 */
public class ReglagesMontre implements Serializable {

    private static final long serialVersionUID = 1L;

    // --- Forme du boîtier ---
    public enum Forme { ROND, CARRE }

    // --- Type de fond ---
    public enum TypeFond { UNIFORME, DEGRADE }

    // --- Type d'affichage ---
    public enum TypeAffichage { ANALOGIQUE, NUMERIQUE }

    private Forme         forme         = Forme.ROND;
    private TypeFond      typeFond      = TypeFond.UNIFORME;
    private Color         couleurFond   = Color.BLACK;
    private Color         couleurFond2  = Color.DARK_GRAY; // pour le dégradé
    private TypeAffichage typeAffichage = TypeAffichage.ANALOGIQUE;
    private Color         couleurAiguilles;

    // --- Getters / Setters ---

    public Forme getFormeBoitier()                  { return forme; }
    public void setFormeBoitier(Forme f)            { this.forme = f; }

    public TypeFond getTypeFond()                   { return typeFond; }
    public void setTypeFond(TypeFond t)             { this.typeFond = t; }

    public Color getCouleurFond()                   { return couleurFond; }
    public void setCouleurFond(Color c)             { this.couleurFond = c; }

    public Color getCouleurFond2()                  { return couleurFond2; }
    public void setCouleurFond2(Color c)            { this.couleurFond2 = c; }

    public TypeAffichage getTypeAffichage()         { return typeAffichage; }
    public void setTypeAffichage(TypeAffichage t)   { this.typeAffichage = t; }

    public Color getCouleurAiguilles()              { return couleurAiguilles; }
    public void setCouleurAiguilles(Color c)        { this.couleurAiguilles = c; }
}