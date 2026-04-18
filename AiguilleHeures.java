import java.awt.Color;
import java.time.LocalTime;

/**
 * Aiguille des heures d'une montre analogique.
 *
 * @author Mohamed
 * @version 2.0
 */
public class AiguilleHeures extends Aiguille {

    private int heures;
    private int minutes;

    public AiguilleHeures() {
        super(Color.RED, 6f, 0.55);
    }

    @Override
    public void update(LocalTime time) {
        this.heures  = time.getHour() % 12;
        this.minutes = time.getMinute();
    }

    @Override
    protected double calculerAngle() {
        return Math.toRadians((heures * 30) + (minutes * 0.5) - 90);
    }

    @Override
    public String getNom() {
        return "Aiguille des heures";
    }
}
