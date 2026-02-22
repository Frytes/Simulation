package frytes.simulation.action;

import frytes.simulation.Simulation;
import frytes.simulation.entity.immobile.Carrot;
import frytes.simulation.entity.immobile.House;
import frytes.simulation.entity.immobile.Tree;
import frytes.simulation.entity.mobile.Herbivore;
import frytes.simulation.entity.mobile.Predator;
import frytes.simulation.gamemap.GameMap;

    public class InitAction implements Action {
        private final EntityFactory factory;

    public InitAction(GameMap gameMap) {
        this.factory = new EntityFactory(gameMap);
    }

    @Override
    public void perform() {
        generateWithEntityMap();

    }

    private void generateWithEntityMap() {
        factory.spawn(Simulation.COUNT_TREE,      () -> new Tree(null));
        factory.spawn(Simulation.COUNT_HOUSE,     () -> new House(null));
        factory.spawn(Simulation.COUNT_CARROT,    () -> new Carrot(null));
        factory.spawn(Simulation.COUNT_PREDATOR,  () -> new Predator(
                null,
                Simulation.PREDATOR_START_HP,
                Simulation.PREDATOR_SPEED,
                Simulation.PREDATOR_ATTACK
        ));

        factory.spawn(Simulation.COUNT_HERBIVORE, () -> new Herbivore(
                null,
                Simulation.HERBIVORE_START_HP,
                Simulation.HERBIVORE_SPEED
        ));
    }
}
