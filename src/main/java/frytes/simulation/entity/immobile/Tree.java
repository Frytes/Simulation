package frytes.simulation.entity.immobile;

import frytes.simulation.entity.Coordinates;
import frytes.simulation.entity.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Tree extends Entity {
    public Tree(Coordinates coordinates) {
        super(coordinates);
    }
    @Override
    public String toString() {
        return "Tree";
    }
}
