package Frytes.simulation.entity.immobile;

import Frytes.simulation.action.Damageable;
import Frytes.simulation.entity.Coordinates;
import Frytes.simulation.entity.Entity;

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
