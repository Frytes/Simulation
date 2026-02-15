package Frytes.simulation.entity.immobile;

import Frytes.simulation.entity.Coordinates;
import Frytes.simulation.entity.Entity;
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
