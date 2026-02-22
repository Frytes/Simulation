package frytes.simulation;

import frytes.simulation.gamemap.GameMap;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class Application {
    public static void main(String[] args) {
        GameMap gameMap = new GameMap(Simulation.MAP_HEIGHT, Simulation.MAP_WIDTH);
        Simulation simulation = new Simulation(gameMap);

        simulation.startSimulation();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();

            if ("p".equalsIgnoreCase(command)) {
                simulation.pauseSimulation();
            } else if ("q".equalsIgnoreCase(command)) {
                log.info("Выход.");
                System.exit(0);
            }

        }
    }
}
