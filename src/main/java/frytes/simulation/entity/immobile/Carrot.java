package frytes.simulation.entity.immobile;

import frytes.simulation.action.Damageable;
import frytes.simulation.entity.Coordinates;
import frytes.simulation.entity.Entity;

public class Carrot extends Entity implements Damageable {
    public Carrot(Coordinates coordinates) {
        super(coordinates);
    }
    @Override
    public String toString() {
        return "Carrot";
    }

    @Override
    public boolean takeDamage(int damage) {
        return true;
    }

}
