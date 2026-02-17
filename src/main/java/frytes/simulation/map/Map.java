package frytes.simulation.map;

import frytes.simulation.entity.Coordinates;
import frytes.simulation.entity.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static frytes.simulation.Simulation.MAP_HEIGHT;
import static frytes.simulation.Simulation.MAP_WIDTH;


public class Map {
    private final HashMap<Coordinates, Entity> worldMap = new HashMap<>();


    public boolean isEmpty(Coordinates coordinates) {
        if (!isValidCoordinates(coordinates)) {
            return false;
        }
        return !worldMap.containsKey(coordinates);

    }

    public void setEntity(Coordinates coordinates, Entity entity) {
        entity.setCoordinates(coordinates);
        worldMap.put(coordinates, entity);
    }

    public java.util.Map<Coordinates, Entity> getEntities() {
        return worldMap;
    }

    public Entity getEntity(Coordinates coordinates) {
        return worldMap.get(coordinates);
    }

    public static boolean isValidCoordinates(Coordinates coordinates) {
        return coordinates.x() >= 0
                && coordinates.y() >= 0
                && coordinates.x() < MAP_WIDTH
                && coordinates.y() < MAP_HEIGHT;
    }


    public void moveEntity(Coordinates coordinates, Entity entity) {
        if (isEmpty(coordinates) ) {
            removeEntity(entity.getCoordinates());
            entity.setCoordinates(coordinates);
            worldMap.put(coordinates, entity);

        } else {
            System.out.println("Нельзя передвинуть. Место занято другим");
        }
    }


    public void removeEntity(Coordinates coordinates) {
        worldMap.remove(coordinates);
    }

    public <T extends Entity> List<T> getEntitiesByType(Class<T> type) {
        return worldMap.values().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }
}
