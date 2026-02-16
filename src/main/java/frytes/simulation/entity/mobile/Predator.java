package frytes.simulation.entity.mobile;

import frytes.simulation.entity.Coordinates;
import frytes.simulation.entity.Entity;

public class Predator extends Creature {
    int attackDamage;

    public Predator(Coordinates coordinates, int hp, int moveSpeed, int attackDamage) {
        super(coordinates);
        this.hp = hp;
        this.moveSpeed = moveSpeed;
        this.attackDamage = attackDamage;
    }


    @Override
    protected Class<? extends Entity> getTargetType() {
        return Herbivore.class;
    }

    @Override
    protected int getAttackDamage() {
        return attackDamage;
    }

    @Override
    public String toString() {
        return "Predator";
    }
}
