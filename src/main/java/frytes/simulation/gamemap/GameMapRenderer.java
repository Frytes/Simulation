package frytes.simulation.gamemap;

import frytes.simulation.entity.Coordinates;
import frytes.simulation.entity.Entity;
import frytes.simulation.entity.immobile.Carrot;
import frytes.simulation.entity.immobile.House;
import frytes.simulation.entity.immobile.Tree;
import frytes.simulation.entity.mobile.Herbivore;
import frytes.simulation.entity.mobile.Predator;

import java.util.HashMap;
import java.util.Map;

import static frytes.simulation.Simulation.MAP_HEIGHT;
import static frytes.simulation.Simulation.MAP_WIDTH;

public class GameMapRenderer {

    public static final String RESET = "\u001B[0m";
    public static final String BG_GRAY = "\u001B[47m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String BG_EARTH = "\u001B[48;5;94m";
    Map<Class<?>, String> spritesMap = new HashMap<>();

    public GameMapRenderer() {
        spritesMap.put(Carrot.class," 🥕 " );
        spritesMap.put(House.class," 🏚️ ");
        spritesMap.put(Tree.class," 🌳 ");
        spritesMap.put(Herbivore.class," 🐰 ");
        spritesMap.put(Predator.class," 🐍 ");

    }

    public void renderMap(GameMap gameMap) {
        StringBuilder line = new StringBuilder();
        java.util.Map<Coordinates, Entity> worldMap = gameMap.getEntities();
        for(int y = 0; y < MAP_HEIGHT; y++) {
            for(int x = 0; x < MAP_WIDTH; x++) {
                Coordinates coordinates = new Coordinates(x,y);
                if (worldMap.containsKey(coordinates)){
                    Entity entity = worldMap.get(coordinates);
                    String background = getBackgroundColor(entity);
                    String sprite = spritesMap.get(entity.getClass());
                    if (sprite == null) {
                        throw new IllegalArgumentException("Спрайт не найден для сущности: " + entity.getClass().getSimpleName());
                    }
                    line.append(colorizeSprites(background,sprite));
                }else {
                    line.append(defaultColorizeSprites());
                }
            }
            line.append("\n");
        }
        System.out.print(line);
    }
    private String colorizeSprites(String background, String sprites){
        return new StringBuilder()
                .append(background)
                .append(sprites)
                .append(RESET)
                .toString();
    }

    private String defaultColorizeSprites(){
        return new StringBuilder()
                .append(BG_EARTH)
                .append("    ")
                .append(RESET)
                .toString();
    }

    private String getBackgroundColor(Entity entity){
        String background = BG_EARTH;
        if (entity instanceof Carrot || entity instanceof Tree) {
            background = BG_GREEN;
        } else if (entity instanceof House) {
            background = BG_GRAY;
        }
        return background;
    }
}
