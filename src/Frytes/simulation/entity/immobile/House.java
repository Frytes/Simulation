package Frytes.simulation.entity.immobile;

import Frytes.simulation.entity.Coordinates;
import Frytes.simulation.entity.Entity;
import lombok.Setter;


@Setter
public class House extends Entity {
    public House(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public String toString() {
        return "Rock";
    }
}
