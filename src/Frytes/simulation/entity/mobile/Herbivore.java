package Frytes.simulation.entity.mobile;

import Frytes.simulation.entity.Coordinates;
import Frytes.simulation.entity.Entity;
import Frytes.simulation.entity.immobile.Carrot;

public class Herbivore extends Creature {

    public Herbivore(Coordinates coordinates, int hp, int moveSpeed) {
        super(coordinates);
        this.hp = hp;
        this.moveSpeed = moveSpeed;
    }


    @Override
    protected Class<? extends Entity> getTargetType() {
        return Carrot.class;
    }

    @Override
    protected int getAttackDamage() {
        return 0;
    }

    @Override
    public String toString() {
        return "Herbivore";
    }
}
