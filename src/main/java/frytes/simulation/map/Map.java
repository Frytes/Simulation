package frytes.simulation.map;

import frytes.simulation.entity.Coordinates;
import frytes.simulation.entity.EmptyCell;
import frytes.simulation.entity.Entity;
import frytes.simulation.entity.immobile.BorderCell;

import java.util.ArrayList;
import java.util.Arrays;
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
        Entity entity = worldMap.get(coordinates);
        return entity == null || entity instanceof EmptyCell;
    }

    public void setEntity(Coordinates coordinates, Entity entity) {
        entity.setCoordinates(coordinates);
        worldMap.put(coordinates, entity);
    }

    public java.util.Map<Coordinates, Entity> getEntities() {
        return worldMap;
    }

    public Entity getEntity(Coordinates coordinates) {
        return worldMap.getOrDefault(coordinates, new EmptyCell(coordinates));
    }

    public List<Entity> getEntitiesNearOrNull(Coordinates coords) {
        List<Entity> entities = new ArrayList<>();
        int x = coords.x();
        int y = coords.y();
        List <Coordinates> coordinates = Arrays.asList(
                new Coordinates(x - 1, y),     // лево
                new Coordinates(x, y + 1),     // верх
                new Coordinates(x + 1, y),     // право
                new Coordinates(x, y - 1)      // низ
        );

        for (Coordinates c : coordinates){
            if (isValidCoordinates(c)) {
                entities.add(getEntity(c));
            } else {
                entities.add(new BorderCell(c));
            }
        }
        return entities;
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
