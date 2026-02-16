package frytes.simulation.entity.mobile;

import frytes.simulation.action.Damageable;
import frytes.simulation.action.PathFinder;
import frytes.simulation.entity.Coordinates;
import frytes.simulation.entity.Entity;
import frytes.simulation.map.Map;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.Queue;

@Getter
@Setter
public abstract class Creature extends Entity implements Damageable {
    Integer moveSpeed;
    int hp;
    Queue<Integer> turnToMove;

    public Creature(Coordinates coordinates) {
        super(coordinates);
    }

    protected abstract int getAttackDamage();

    protected abstract Class<? extends Entity> getTargetType();

    public void makeMove(Map map) {
        Entity entityOnMap = map.getEntity(this.getCoordinates());
        if (entityOnMap != this) {
            return;
        }
        Class<? extends Entity> targetType = getTargetType();
        Entity target = findNearestTarget(targetType, map);
        if (target != null) {
            PathFinder pathFinder = new PathFinder();
            List<Coordinates> path = pathFinder.findPath(this.getCoordinates(), target.getCoordinates(), map,targetType);
            moveAlongPath(path, map);
        }
    }

    private Entity findNearestTarget(Class<? extends Entity> targetType, Map map) {
        List<? extends Entity> targets = map.getEntitiesByType(targetType);

        return targets.stream()
                .min(Comparator.comparingInt(e ->
                        Math.abs(this.getCoordinates().x() - e.getCoordinates().x())
                                + Math.abs(this.getCoordinates().y() - e.getCoordinates().y())
                ))
                .orElse(null);
    }


    private void moveAlongPath(List<Coordinates> path, Map map) {
        if (path == null || path.isEmpty()) return;

        for (int i = 0; i < path.size() && i < moveSpeed; i++) {
            Coordinates nextStep = path.get(i);
            Entity entityOnNextStep = map.getEntity(nextStep);
            if (map.isEmpty(nextStep)) {
                map.moveEntity(nextStep, this);
            }
            else {
                if (interact(entityOnNextStep, map)) {
                    map.moveEntity(nextStep, this);
                } else {
                    break;
                }
            }
        }
    }

    protected boolean interact(Entity target, Map map) {
        Class<? extends Entity> myTargetType = getTargetType();

        if (myTargetType.isInstance(target)) {
            return attack(target, map);
        }
        return false;
    }

    protected boolean attack(Entity target, Map map) {
        if (target instanceof Damageable victim) {
            int damage = this.getAttackDamage();

            boolean isDead = victim.takeDamage(damage);

            if (isDead) {
                map.removeEntity(target.getCoordinates());
                this.hp += 5;
                return true;
            }
        }return false;
    }

    @Override
    public boolean takeDamage(int damage) {
        this.hp -= damage;
        return this.hp <= 0;
    }


}
