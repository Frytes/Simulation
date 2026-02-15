package Frytes.simulation.action;

import Frytes.simulation.entity.immobile.Carrot;
import Frytes.simulation.entity.immobile.House;
import Frytes.simulation.entity.immobile.Tree;
import Frytes.simulation.map.Map;

import static Frytes.simulation.Simulation.*;

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
