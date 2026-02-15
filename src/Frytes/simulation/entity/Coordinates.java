package Frytes.simulation.entity;

import java.util.Random;

import static Frytes.simulation.Simulation.MAP_HEIGHT;
import static Frytes.simulation.Simulation.MAP_WIDTH;


public record Coordinates(int x, int y) {

    public static Coordinates getRandomCoordinated() {
        Random random = new Random();
        int x = (random.nextInt(MAP_WIDTH));
        int y = (random.nextInt(MAP_HEIGHT));
        return new Coordinates(x, y);
    }




    @Override
    public String toString() {
        return "{" +
                "x:" + x +
                ",y:" + y +
                '}';
    }
}
