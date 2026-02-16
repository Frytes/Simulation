package frytes.simulation.action;

import frytes.simulation.entity.immobile.Carrot;
import frytes.simulation.entity.mobile.Herbivore;
import frytes.simulation.entity.mobile.Predator;
import frytes.simulation.map.Map;

import java.util.List;

import static frytes.simulation.Simulation.COUNT_CARROT;
import static frytes.simulation.Simulation.MIN_COUNT_CARROT;

public class TurnActions extends Actions {

    public TurnActions(Map map) {
        super(map);
    }

    @Override
    public void perform() {
        List<Herbivore> herbivores = map.getEntitiesByType(Herbivore.class);
        List<Predator> predators = map.getEntitiesByType(Predator.class);

        for (Herbivore h : herbivores){
            h.makeMove(map);
        }
        for (Predator p : predators){
            p.makeMove(map);
        }

        List<Carrot> carrots = map.getEntitiesByType(Carrot.class);
        if (carrots.size() < MIN_COUNT_CARROT){
            generateEntity(Carrot.class, COUNT_CARROT);
        }

    }
}
