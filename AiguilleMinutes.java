import java.awt.Color;
import java.time.LocalTime;

/**
 * Aiguille des minutes d'une montre analogique.
 *
 * @author Mohamed
 * @version 2.0
 */
public class AiguilleMinutes extends Aiguille {

    private int minutes;
    private int secondes;

    public AiguilleMinutes() {
        super(Color.RED, 4f, 0.80);
    }

    @Override
    public void update(LocalTime time) {
        this.minutes  = time.getMinute();
        this.secondes = time.getSecond();
    }

    @Override
    protected double calculerAngle() {
        return Math.toRadians((minutes * 6) + (secondes * 0.1) - 90);
    }

    @Override
    public String getNom() {
        return "Aiguille des minutes";
    }
}
