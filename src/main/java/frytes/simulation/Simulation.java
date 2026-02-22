package frytes.simulation;

import frytes.simulation.action.Action;
import frytes.simulation.action.InitAction;
import frytes.simulation.action.TurnAction;
import frytes.simulation.gamemap.GameMap;
import frytes.simulation.gamemap.GameMapRenderer;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class Simulation {
    private final GameMap gameMap;
    private final GameMapRenderer gameMapRenderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private int turnCounter = 0;

    private final AtomicBoolean isPaused = new AtomicBoolean(false);
    private final AtomicBoolean isRunning = new AtomicBoolean(true);

    public static final int MAP_WIDTH = 20;
    public static final int MAP_HEIGHT = 10;

    public static final int COUNT_CARROT = 5;
    public static final int MIN_COUNT_CARROT = 5;
    public static final int COUNT_HOUSE = 5;
    public static final int COUNT_TREE = 5;
    public static final int COUNT_PREDATOR = 1;
    public static final int COUNT_HERBIVORE = 1;

    public static final int PREDATOR_START_HP = 25;
    public static final int PREDATOR_SPEED = 1;
    public static final int PREDATOR_ATTACK = 5;

    public static final int HERBIVORE_START_HP = 10;
    public static final int HERBIVORE_SPEED = 1;


    public Simulation(GameMap gameMap) {
        this.gameMap = gameMap;
        this.gameMapRenderer = new GameMapRenderer();
        this.initActions = List.of(new InitAction(gameMap));
        this.turnActions = List.of(new TurnAction(gameMap));
        executeActions(initActions);
    }

    public void startSimulation() {
        Thread simulationThread = new Thread(() -> {
            while (isRunning.get()) {
                if (!isPaused.get()) {
                    nextTurn();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("Поток симуляции прерван!");
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        simulationThread.start();
    }

    public void pauseSimulation() {
        boolean newValue = !isPaused.get();
        isPaused.set(newValue);
        log.info(newValue ? "⏸ Пауза" : "▶ Продолжаем");
    }

    private void nextTurn() {
        clearConsole();
        executeActions(turnActions);
        turnCounter++;
        try {
            gameMapRenderer.renderMap(gameMap);
        } catch (Exception e) {
            log.error("Ошибка рендеринга на ходу {}: {}", turnCounter, e.getMessage(), e);
            isRunning.set(false);
        }
        log.info("Ход: {}", turnCounter);
    }

    private void executeActions(List<Action> actions) {
        for (Action action : actions) {
            action.perform();
        }
    }
    private void clearConsole() {
        System.out.println("---------------------------------------------------------------------------------");
    }
}
