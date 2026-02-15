package Frytes.simulation.action;

import Frytes.simulation.entity.Coordinates;
import Frytes.simulation.entity.Entity;
import Frytes.simulation.entity.mobile.Herbivore;
import Frytes.simulation.entity.mobile.Predator;
import Frytes.simulation.map.Map;

public abstract class Actions {
    protected final Map map;

    public Actions(Map map) {
        this.map = map;
    }

    public abstract void  perform();

    protected void generateEntity(Class<? extends Entity> entityClass, int count) {
        Coordinates coordinates;
        for (int i = 0; i < count; i++) {
            for (int timesCount = 0; timesCount < 100; timesCount++) {
                coordinates = Coordinates.getRandomCoordinated();
                if (map.isEmpty(coordinates)) {
                    try {
                        Entity entity = entityClass
                                .getConstructor(Coordinates.class)
                                .newInstance(coordinates);
                        map.setEntity(coordinates, entity);
                    } catch (Exception e) {
                        System.err.println("Ошибка создания " + entityClass.getSimpleName() +
                                " на (" + coordinates.x() + "," + coordinates.y() + "): " + e.getMessage());
                    }
                    break;
                }
                if (timesCount == 99) {
                    System.out.println("Не удалось найти пустое место, Все ячейки заняты");
                    break;
                }
            }

        }
    }
    protected void createPredator(int count) {
        Coordinates coordinates;
        for (int i = 0; i < count; i++) {
            coordinates = Coordinates.getRandomCoordinated();
            Predator wolf = new Predator(coordinates, 10, 1,5);
            map.setEntity(coordinates, wolf);
        }
    }

    protected void createHerbivore(int count) {
        Coordinates coordinates;
        for (int i = 0; i < count; i++) {
            coordinates = Coordinates.getRandomCoordinated();
            Herbivore bunny = new Herbivore(coordinates, 5, 1);
            map.setEntity(coordinates, bunny);
        }
    }

}
