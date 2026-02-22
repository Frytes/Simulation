package frytes.simulation.entity.mobile;

import frytes.simulation.action.Damageable;
import frytes.simulation.action.PathFinder;
import frytes.simulation.entity.Coordinates;
import frytes.simulation.entity.Entity;
import frytes.simulation.gamemap.GameMap;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Getter
public abstract class Creature extends Entity implements Damageable {
    private final int moveSpeed;
    private int hp;
    private final Class<? extends Entity> targetType;

    protected Creature(Coordinates coordinates, int hp, int moveSpeed, Class<? extends Entity> targetType) {
        super(coordinates);
        this.hp = hp;
        this.moveSpeed = moveSpeed;
        this.targetType = targetType;
    }

    protected abstract boolean interact(Entity target, GameMap gameMap);

    public void makeMove(GameMap gameMap) {
        Entity entityOnMap = gameMap.getEntity(this.getCoordinates()).orElse(null);
        if (entityOnMap != this) {
            return;
        }

        findNearestTarget(targetType, gameMap).ifPresent(target -> {
            PathFinder pathFinder = new PathFinder();
            List<Coordinates> path = pathFinder.find(gameMap, this.getCoordinates(), target.getCoordinates());
            moveAlongPath(path, gameMap);
        });
    }

    private void moveAlongPath(List<Coordinates> path, GameMap gameMap) {
        if (path == null || path.isEmpty()) return;

        for (int i = 0; i < path.size() && i < moveSpeed; i++) {
            Coordinates nextStep = path.get(i);

            if (gameMap.isEmpty(nextStep)) {
                moveEntity(gameMap, nextStep);
            } else {
                Entity entityOnNextStep = gameMap.getEntity(nextStep).orElse(null);
                if (interact(entityOnNextStep, gameMap)) {
                    moveEntity(gameMap, nextStep);
                } else {
                    break;
                }
            }
        }
    }

    private void moveEntity(GameMap gameMap, Coordinates coordinates) {
        if (gameMap.isEmpty(coordinates)) {
            gameMap.removeEntity(this.getCoordinates());
            this.setCoordinates(coordinates);
            gameMap.setEntity(coordinates, this);
        } else {
            throw new IllegalArgumentException("Нельзя передвинуть. Место занято другим");
        }
    }

    private Optional<? extends Entity> findNearestTarget(Class<? extends Entity> targetType, GameMap gameMap) {
        List<? extends Entity> targets = gameMap.getEntitiesByType(targetType);
        return targets.stream()
                .min(Comparator.comparingInt(e ->
                        Math.abs(this.getCoordinates().x() - e.getCoordinates().x())
                                + Math.abs(this.getCoordinates().y() - e.getCoordinates().y())
                ));
    }

    @Override
    public boolean takeDamage(int damage) {
        this.hp -= damage;
        return this.hp <= 0;
    }

    protected void heal(int amount) {
        this.hp += amount;
    }
}