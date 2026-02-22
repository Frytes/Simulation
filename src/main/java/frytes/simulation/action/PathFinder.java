package frytes.simulation.action;

import frytes.simulation.entity.Coordinates;
import frytes.simulation.gamemap.GameMap;

import java.util.*;


public class PathFinder {
    public List<Coordinates> find(GameMap gameMap, Coordinates start, Coordinates target) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Map<Coordinates, Coordinates> cameFrom = new HashMap<>();
        Map<Coordinates, Integer> gCost = new HashMap<>();

        queue.add(new Node(start, 0));
        cameFrom.put(start, null);
        gCost.put(start, 0);

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll().coordinates;

            if (current.equals(target)) {
                break;
            }

            List<Coordinates> potentialNeighbors = getNeighbors(current); // Я вынес это в метод для читаемости, но можно оставить как было

            for (Coordinates neighborCoord : potentialNeighbors) {
                boolean isWalkable = gameMap.isValidCoordinates(neighborCoord)
                        && (gameMap.isEmpty(neighborCoord) || neighborCoord.equals(target));

                if (!isWalkable) {
                    continue;
                }

                int newCost = gCost.get(current) + 1;
                if (!gCost.containsKey(neighborCoord) || newCost < gCost.get(neighborCoord)) {
                    gCost.put(neighborCoord, newCost);
                    int priority = newCost + heuristic(neighborCoord, target);
                    queue.add(new Node(neighborCoord, priority));
                    cameFrom.put(neighborCoord, current);
                }
            }
        }
        if (!cameFrom.containsKey(target)) {
            return Collections.emptyList();
        }
        return reconstructPath(start, target, cameFrom);
    }

    private List<Coordinates> getNeighbors(Coordinates c) {
        return List.of(
                new Coordinates(c.x() - 1, c.y()),
                new Coordinates(c.x() + 1, c.y()),
                new Coordinates(c.x(), c.y() - 1),
                new Coordinates(c.x(), c.y() + 1)
        );
    }

    private List<Coordinates> reconstructPath(Coordinates start, Coordinates target, Map<Coordinates, Coordinates> cameFrom) {
        List<Coordinates> path = new ArrayList<>();
        Coordinates step = target;

        while (step != null && !step.equals(start)) {
            path.add(step);
            step = cameFrom.get(step);
        }

        Collections.reverse(path);
        return path;
    }

    private int heuristic(Coordinates a, Coordinates b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    private record Node(Coordinates coordinates, int priority) implements Comparable<Node> {
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.priority, other.priority);
        }
    }
}


