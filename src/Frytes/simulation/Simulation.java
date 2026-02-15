package Frytes.simulation;

import Frytes.simulation.action.Actions;
import Frytes.simulation.action.InitActions;
import Frytes.simulation.action.TurnActions;
import Frytes.simulation.map.Map;
import Frytes.simulation.map.RenderMap;

public class Simulation {
    private final Map map;
    private final RenderMap renderMap;
    private final Actions turnActions;
    private int turnCounter = 0;
    private boolean isRunning = true;


    public static final int MAP_WIDTH = 20;
    public static final int MAP_HEIGHT = 10;
    public static final int COUNT_CARROT = 5;
    public static final int MIN_COUNT_CARROT = 5;
    public static final int COUNT_HOUSE = 5;
    public static final int COUNT_TREE = 5;
    public static final int COUNT_PREDATOR = 5;
    public static final int COUNT_HERBIVORE = 5;



    public Simulation() {
        this.map = new Map();

        Actions initActions = new InitActions(map);
        initActions.perform();
        this.renderMap = new RenderMap();
        this.turnActions = new TurnActions(map);
    }

    public void startSimulation() {
        renderMap.renderMap(map);
        while (isRunning) {
            nextTurn();

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println("Симуляция прервана!");
                break;
            }
        }
    }


    private void nextTurn() {
        clearConsole();
        turnActions.perform();
        turnCounter++;
        renderMap.renderMap(map);
        System.out.println("Ход: " + turnCounter);
    }


    private void clearConsole() {
        System.out.println("---------------------------------------------------------------------------------");
    }

    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        simulation.startSimulation();
    }
}