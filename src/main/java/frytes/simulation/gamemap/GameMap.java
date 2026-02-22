package frytes.simulation.gamemap;

import frytes.simulation.entity.Coordinates;
import frytes.simulation.entity.Entity;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class GameMap {
    private final Map<Coordinates, Entity> entities = new HashMap<>();
    @Getter public final int height;
    @Getter public final int width;

    public GameMap(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public Map<Coordinates, Entity> getEntities() {
        return new HashMap<>(entities);
    }


    public Optional<Entity> getEntity(Coordinates coordinates) {
        return Optional.ofNullable(entities.get(coordinates));
    }

    public <T extends Entity> List<T> getEntitiesByType(Class<T> type) {
        return entities.values().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .toList();
    }

    public void setEntity(Coordinates coordinates, Entity entity) {
        if(isValidCoordinates(coordinates)){
            entity.setCoordinates(coordinates);
            entities.put(coordinates, entity);
        } else {
            throw new IllegalArgumentException("Не удалось сохранить. Перепроверьте правильность координат"); }
    }

    public void removeEntity(Coordinates coordinates) {
        if(isValidCoordinates(coordinates)){
            entities.remove(coordinates);
        } else {
            throw new IllegalArgumentException("Не удалось удалить. Проверьте правильность координат");
        }
    }

    public boolean isEmpty(Coordinates coordinates) {
        if (!isValidCoordinates(coordinates)) {
            return false;
        }
        return !entities.containsKey(coordinates);

    }

    public boolean isValidCoordinates(Coordinates coordinates) {
        return coordinates.x() >= 0
                && coordinates.y() >= 0
                && coordinates.x() < this.getWidth()
                && coordinates.y() < this.getHeight();
    }
}
