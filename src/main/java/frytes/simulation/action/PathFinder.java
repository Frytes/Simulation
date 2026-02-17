package frytes.simulation.action;

import frytes.simulation.entity.Coordinates;
import frytes.simulation.entity.Entity;
import frytes.simulation.map.Map;

import java.util.*;


public class PathFinder {
    public List<Coordinates> findPath(Coordinates start, Coordinates target, Map map, Class<? extends Entity> targetType) {

        PriorityQueue<Node> queue = new PriorityQueue<>();
        HashMap<Coordinates, Coordinates> cameFrom = new HashMap<>();

        HashMap<Coordinates, Integer> gCost = new HashMap<>();

        queue.add(new Node(start, 0));
        cameFrom.put(start, null);
        gCost.put(start, 0);

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll().coordinates;
            if (current.equals(target)) {
                break;
            }

            int x = current.x();
            int y = current.y();

            List<Coordinates> potentialNeighbors = List.of(
                    new Coordinates(x - 1, y),
                    new Coordinates(x + 1, y),
                    new Coordinates(x, y - 1),
                    new Coordinates(x, y + 1)
            );

            for (Coordinates neighborCoord : potentialNeighbors) {
                if (!Map.isValidCoordinates(neighborCoord)) continue;

                Entity neighborEntity = map.getEntity(neighborCoord);

                boolean isWalkable = (neighborEntity == null) || targetType.isInstance(neighborEntity);

                if (!isWalkable) continue;

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

    private List<Coordinates> reconstructPath(Coordinates start, Coordinates target, HashMap<Coordinates, Coordinates> cameFrom) {
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


