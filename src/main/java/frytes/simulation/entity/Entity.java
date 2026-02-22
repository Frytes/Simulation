package frytes.simulation.entity;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Entity {
    private Coordinates coordinates;

    protected Entity(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

}
