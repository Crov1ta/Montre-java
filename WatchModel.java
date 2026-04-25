import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Modèle principal de la montre.
 * Contient la liste des éléments et gère l'heure.
 */
public class WatchModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ElementMontre> elements;
    private transient LocalTime heureCourante;
    private boolean simulationManuelle = false;
      private ReglagesMontre reglages; 

    public WatchModel(ReglagesMontre reglages) {
        this.elements      = new ArrayList<>();
        this.heureCourante = LocalTime.now();
        this.reglages      = reglages;
    }

    /** Avance l'heure d'une seconde et notifie tous les éléments. */
    public void tick() {
        if (simulationManuelle) {
            heureCourante = heureCourante.plusSeconds(1);
        } else {
            heureCourante = LocalTime.now();
        }
        for (ElementMontre e : elements) {
            e.update(heureCourante);
        }
    }

    public void ajouterElement(ElementMontre e)  { elements.add(e); }
    public void retirerElement(ElementMontre e)  { elements.remove(e); }
    public void ajouterElementEnPremier(ElementMontre e) { elements.add(0, e); }
    public List<ElementMontre> getElements()     { return elements; }
    public LocalTime getHeureCourante()          { return heureCourante; }

    /** Démarre une simulation à partir d'une heure donnée. */
    public void demarrerSimulation(int h, int m, int s) {
        this.simulationManuelle = true;
        this.heureCourante = LocalTime.of(h, m, s);
    }

    public void utiliserHeureSysteme() {
        this.simulationManuelle = false;
        this.heureCourante = LocalTime.now();
    }

    // --- Sauvegarde / Chargement ---

    public void sauvegarder(String chemin) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(chemin))) {
            oos.writeObject(this);
        }
    }

    public static WatchModel charger(String chemin)
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(chemin))) {
            WatchModel m = (WatchModel) ois.readObject();
            m.heureCourante = LocalTime.now();
            m.simulationManuelle = false;
            m.tick();
            return m;
        }
    }
    public void retirerElementType(Class<?> type) {
        int i = 0;
        while (i < elements.size()) {
            if (type.isInstance(elements.get(i))) {
                elements.remove(i);
            } else {
                i++;
            }
        }
    }
    public ReglagesMontre getReglages() { return reglages; }
    
}