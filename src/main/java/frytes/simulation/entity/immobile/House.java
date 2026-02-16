package frytes.simulation.entity.immobile;

import frytes.simulation.entity.Coordinates;
import frytes.simulation.entity.Entity;
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
