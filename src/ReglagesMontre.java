import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * Stocke la configuration visuelle de la montre.
 * Utilisée par WatchPanel pour adapter le rendu.
 */
public class ReglagesMontre implements Serializable {

    private static final long serialVersionUID = 1L;

    // --- Forme du boîtier ---
    public enum Forme { ROND, CARRE }

    // --- Type de fond ---
    public enum TypeFond { UNIFORME, DEGRADE, IMAGE }

    // --- Type d'affichage ---
    public enum TypeAffichage { ANALOGIQUE, NUMERIQUE }

    private Forme         forme         = Forme.ROND;
    private TypeFond      typeFond      = TypeFond.UNIFORME;
    private Color         couleurFond   = Color.BLACK;
    private Color         couleurFond2  = Color.DARK_GRAY; // pour le dégradé
    private TypeAffichage typeAffichage = TypeAffichage.ANALOGIQUE;
    private Color         couleurAiguilles = Color.RED;
    private Color         couleurNombres   = Color.WHITE;
    private float         facteurEpaisseur = 1.0f;

    private transient Image imageFond = null;
    private String cheminImageFond    = null;

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

    public Color getCouleurNombres()                { return couleurNombres; }
    public void setCouleurNombres(Color c)          { this.couleurNombres = c; }

    public float getFacteurEpaisseur()              { return facteurEpaisseur; }
    public void setFacteurEpaisseur(float f)        { this.facteurEpaisseur = f; }

    public Image getImageFond()                     { return imageFond; }
    public void setImageFond(Image img, String chemin) {
        this.imageFond       = img;
        this.cheminImageFond = chemin;
        this.typeFond        = TypeFond.IMAGE;
    }
    public void supprimerImageFond() {
        this.imageFond       = null;
        this.cheminImageFond = null;
    }
    public String getCheminImageFond()              { return cheminImageFond; }


    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // désérialise tous les champs non-transient
        if (cheminImageFond != null) {
            imageFond = new javax.swing.ImageIcon(cheminImageFond).getImage();
        }
    }
}