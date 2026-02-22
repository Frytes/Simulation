package frytes.simulation.action;

import frytes.simulation.entity.Coordinates;
import frytes.simulation.entity.Entity;
import frytes.simulation.gamemap.GameMap;

import java.util.Random;
import java.util.function.Supplier;

public class EntityFactory {
    private static final int MAX_CREATION_ATTEMPTS = 100;
    private final GameMap gameMap;
    private final Random random = new Random();

    public EntityFactory(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public <T extends Entity> void spawn(int count, Supplier<T> factory) {
        for (int i = 0; i < count; i++) {
            Coordinates pos = getRandomEmptyCoordinates();
            T entity = factory.get();
            entity.setCoordinates(pos);
            gameMap.setEntity(pos, entity);
        }
    }

    private Coordinates getRandomEmptyCoordinates() {
        for (int i = 0; i < MAX_CREATION_ATTEMPTS; i++) {
            Coordinates coordinates = new Coordinates(
                    random.nextInt(gameMap.getWidth()),
                    random.nextInt(gameMap.getHeight())
            );

            if (gameMap.isEmpty(coordinates)) {
                return coordinates;
            }
        }
        throw new IllegalStateException("Не удалось найти пустое место на карте");
    }
}
