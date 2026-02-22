package frytes.simulation.entity.mobile;

import frytes.simulation.entity.Coordinates;
import frytes.simulation.entity.Entity;
import frytes.simulation.entity.immobile.Carrot;
import frytes.simulation.gamemap.GameMap;

public class Herbivore extends Creature {
    private static final int CARROT_HEAL_AMOUNT = 5;

    public Herbivore(Coordinates coordinates, int hp, int speed) {
        super(coordinates, hp, speed, Carrot.class);
    }

    @Override
    protected boolean interact(Entity target, GameMap gameMap) {
        if (target instanceof Carrot) {
            gameMap.removeEntity(target.getCoordinates());
            this.heal(CARROT_HEAL_AMOUNT);
            return true;
        }
        return false;
    }
}