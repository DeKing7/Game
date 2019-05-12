package Map;

import Map.Environment.Action;
import Map.Environment.Element;
import Map.Environment.Perception;

import java.util.ArrayList;

public class Player extends Object {
    public enum Direction {
        N, E, S, W
    }

    private final World world;
    private int x, y;

    private Tile tile;

    private ArrayList<Perception> perceptions = new ArrayList<Perception>();
    private ArrayList<Action> actions = new ArrayList<Action>();
    private Direction direction = Direction.E;
    private boolean alive = true;
    private boolean castle = false;
    private int HP = 9;
    private boolean upgrade = false;

    public Player(World world) {
        this.world = world;
    }

    public int getX() {
        return tile.getX();
    }

    public int getY() {
        return tile.getY();
    }

    protected void reset() {
        sword = 1;
        upgrade = false;
        castle = false;
        direction = Direction.E;
        actions.clear();
    }

    protected Tile getTile() {
        return tile;
    }

    protected void setTile(int index) {

        if (tile != null) {
            tile.remove(Element.KNIGHT);
        }
        tile = world.getPosition(index);
        tile.setItem(Environment.Element.KNIGHT);

        x = tile.getX();
        y = tile.getY();

        alive = !(tile.contains(Element.GOBLIN) || tile.contains(Element.ABYSS));
    }

    public Direction getDirection() {
        return direction;
    }

    public Environment.Perception attack() {

        //Dodać akcje ataku
        ///
        //
        //


        int[] neighbors = getTile().getNeighbors();
        Tile neighbor = null;
        switch (direction) {
            case N:
                if (neighbors[0] > -1) neighbor = world.getPosition(neighbors[0]);
                break;
            case E:
                if (neighbors[1] > -1) neighbor = world.getPosition(neighbors[1]);
                break;
            case S:
                if (neighbors[2] > -1) neighbor = world.getPosition(neighbors[2]);
                break;
            case W:
                if (neighbors[3] > -1) neighbor = world.getPosition(neighbors[3]);
                break;
        }

        if (neighbor != null && neighbor.contains(Element.GOBLIN)) {
            neighbor.remove(Element.GOBLIN);

            return Perception.SCREAM;
        }

        return null;


    }

    protected void setAction(Action action) {
        actions.add(action);

        switch (action) {
            case GO_FORWARD:
                int[] neighbors = tile.getNeighbors();
                switch (direction) {
                    case N:
                        if (neighbors[0] > -1) setTile(neighbors[0]);
                        break;
                    case E:
                        if (neighbors[1] > -1) setTile(neighbors[1]);
                        break;
                    case S:
                        if (neighbors[2] > -1) setTile(neighbors[2]);
                        break;
                    case W:

                        if (neighbors[3] > -1) setTile(neighbors[3]);
                        break;
                }
                break;
            case TURN_LEFT:

                switch (direction) {
                    case N:
                        direction = Direction.W;
                        break;
                    case W:
                        direction = Direction.S;
                        break;
                    case S:
                        direction = Direction.E;
                        break;
                    case E:
                        direction = Direction.N;
                        break;
                }
                break;
            case TURN_RIGHT:
                // Mover clockwise
                switch (direction) {
                    case N:
                        direction = Direction.E;
                        break;
                    case E:
                        direction = Direction.S;
                        break;
                    case S:
                        direction = Direction.W;
                        break;
                    case W:
                        direction = Direction.N;
                        break;
                }
                break;
            case GRAB:
                // If tile has gold store and remove from the tile
                if (tile.contains(Environment.Element.CASTLE)) {
                    tile.remove(Environment.Element.CASTLE);
                    castle = true;
                }
                break;
            case ATTACK:
                Perception perception = attack();
                if (perception != null) setPerceptions(perception);
                return;
        }
        setPerceptions();
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public Action getLastAction() {
        if (actions.size() == 0) return null;
        return actions.get(actions.size() - 1);
    }

    public int getScore() {
        return Environment.getScore(this);
    }

    protected ArrayList<Perception> getPerceptions() {
        return perceptions;
    }


    protected void setPerceptions() {
        perceptions.clear();

        if (tile.contains(Element.UPGRADE)) {
            perceptions.add(Perception.GLITTER);
        }

        int[] neighbors = tile.getNeighbors();
        for (int i = 0; i < neighbors.length; i++) {

            if (neighbors[i] == -1) {
                if ((i == 0 && direction == Direction.N) ||
                        (i == 1 && direction == Direction.E) ||
                        (i == 2 && direction == Direction.S) ||
                        (i == 3 && direction == Direction.W)) {
                    perceptions.add(Perception.BUMP);
                }
            } else {
                Tile neighbor = world.getPosition(neighbors[i]);

                if (neighbor.contains(Element.ABYSS)) {
                    perceptions.add(Perception.BREEZE);
                }

                if (neighbor.contains(Element.GOBLIN)) {
                    perceptions.add(Perception.STENCH);
                }
            }
        }
    }
    protected void setPerceptions(Perception value) {
        setPerceptions();
        perceptions.add(value);
    }
    public boolean isAlive() { return alive; }

    public boolean isDead() { return !alive; }

    public boolean hasCastle() { return castle; }

    public boolean hasUpgrade() {return upgrade; }

    public boolean hasBump() {
        return perceptions.contains(Perception.BUMP);
    }

    public boolean hasBreeze() {
        return perceptions.contains(Perception.BREEZE);
    }

    public boolean hasStench() {
        return perceptions.contains(Perception.STENCH);
    }

    public boolean hasScream() {
        return perceptions.contains(Perception.SCREAM);
    }

    public boolean hasGlitter() {
        return perceptions.contains(Perception.GLITTER);
    }

    public String render() {
        return ConsoleRenderer.render(world);
    }

    public String debug() {
        StringBuilder output = new StringBuilder();
        // Position and direction
        output.append("Position: ").append("(").append(x).append(",").append(y).append(",")
                .append(direction).append(")").append("\n");
        // Score
        output.append("Score: ").append(getScore()).append("\n");
        // Perceptions
        output.append("Perceptions: ").append(perceptions.toString());

        return output.toString();
    }


}

