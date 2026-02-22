package frytes.simulation.entity.mobile;

import frytes.simulation.action.Damageable;
import frytes.simulation.entity.Coordinates;
import frytes.simulation.entity.Entity;
import frytes.simulation.gamemap.GameMap;
import lombok.Getter;


@Getter
public class Predator extends Creature {
    private static final int HEAL_AMOUNT = 10;
    private final int attackDamage;

    public Predator(Coordinates coordinates, int hp, int speed, int attackDamage) {
        super(coordinates, hp, speed, Herbivore.class);
        this.attackDamage = attackDamage;
    }

    @Override
    protected boolean interact(Entity target, GameMap gameMap) {
        if (target instanceof Damageable victim) {
            boolean isDead = victim.takeDamage(this.attackDamage);
            if (isDead) {
                gameMap.removeEntity(target.getCoordinates());
                this.heal(HEAL_AMOUNT);
                return true;
            }
        }
        return false;
    }
}