package frytes.simulation.action;

import frytes.simulation.entity.immobile.Carrot;
import frytes.simulation.entity.immobile.House;
import frytes.simulation.entity.immobile.Tree;
import frytes.simulation.map.Map;

import static frytes.simulation.Simulation.*;

public class InitActions extends Actions {

    public InitActions(Map map) {
        super(map);
    }

    @Override
    public void perform() {
        generateWithEntityMap();
    }

    private void generateWithEntityMap() {
        generateEntity(House.class, COUNT_HOUSE);
        generateEntity(Carrot.class, COUNT_CARROT);
        generateEntity(Tree.class, COUNT_TREE);
        createHerbivore(COUNT_HERBIVORE);
        createPredator(COUNT_PREDATOR);
    }
}
