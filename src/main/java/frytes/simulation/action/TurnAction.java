package frytes.simulation.action;

import frytes.simulation.Simulation;
import frytes.simulation.entity.immobile.Carrot;
import frytes.simulation.entity.mobile.Creature;
import frytes.simulation.gamemap.GameMap;

import java.util.List;

import static frytes.simulation.Simulation.MIN_COUNT_CARROT;

public class TurnAction implements Action {
    private final EntityFactory factory;
    private final GameMap gameMap;

    public TurnAction(GameMap gameMap) {
        this.gameMap = gameMap;
        this.factory = new EntityFactory(gameMap);
    }

    @Override
    public void perform() {
        List<Creature> creatures = gameMap.getEntitiesByType(Creature.class);
        for (Creature creature : creatures) {
            creature.makeMove(gameMap);
        }

        List<Carrot> carrots = gameMap.getEntitiesByType(Carrot.class);
        if (carrots.size() < MIN_COUNT_CARROT) {
            factory.spawn(Simulation.COUNT_CARROT, () -> new Carrot(null));
        }
    }
}
