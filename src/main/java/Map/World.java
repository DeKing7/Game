package Map;

import java.util.HashMap;
import java.util.Random;

import Map.Environment.Element;

public class World {
    private static final int RANDOM_MAX_TRIES = 40;
    private static final int DEFAULT_CASTLE = 1;
    private static final int DEFAULT_GOBLIN = 7;
    private static final int DEFAULT_ABYSS = 8;
    private static final int DEFAULT_UPGRADE = 8;

    private final int width;
    private final int height;
    private final int startPosition;

    private int castle = DEFAULT_CASTLE;
    private int abyss = DEFAULT_ABYSS;
    private int goblin = DEFAULT_GOBLIN;
    private int upgrade = DEFAULT_UPGRADE;

    private boolean randomize = true;
    private HashMap<Integer, Environment.Element> items = new HashMap<Integer, Element>();

    private final Player player;
    private final Tile[] tiles;


    public World(int width, int height) throws InterruptedException,
            InternalError {
        if (width == 1 && height == 1) {
            throw new InternalError("The world size must be greater than 1x1.");
        }
        this.width = width;
        this.height = height;

        tiles = new Tile[width * height];
        for (int i = 0; i < width * height; i++) {
            tiles[i] = new Tile(i, width, height);
        }

        startPosition = getIndex(0, height - 1);

        player = new Player(this);
    }

    public void setAbyss(int value) {
        abyss = value;
    }

    public void setAbyss(int x, int y) {
        setItem(Element.ABYSS, x, y);
    }

    public void setGoblin(int value) {
        goblin = value;
    }

    public void setGoblin(int x, int y) {
        setItem(Element.GOBLIN, x, y);
    }

    public void setCastle(int x, int y) {
        setItem(Element.CASTLE, x, y);
    }

    public void setUpgrade(int value) {
        upgrade = value;
    }

    public void setUpgrade(int x, int y) {
        setItem(Element.UPGRADE, x, y);
    }

    private void setItem(Element element, int x, int y) {
        int idx = getIndex(x, y);
        if (items.containsKey(idx)) {
            throw new InternalError("Tile is not empty!");
        }

        items.put(idx, element);

        randomize = false;
    }

    private void setRandom(Environment.Element element, int times) throws InterruptedException {
        Random random = new Random();
        int tries = 0;

        int[] safeBlocks = player.getTile().getNeighbors();


        for (int i = 0; i < times; i++) {
            Tile position;

            while (true) {
                int z = random.nextInt(width * height - 1);
                position = tiles[z];
                if (position.isEmpty() &&
                        z != safeBlocks[0] && z != safeBlocks[1] && z != safeBlocks[2] &&
                        z != safeBlocks[3]) {
                    position.setItem(element);
                    break;
                }

                if (tries >= RANDOM_MAX_TRIES) {
                    throw new InterruptedException("Cannot set a random position for element after " +
                            "many tries, increase the world dimensions.");
                } else {
                    tries++;
                }
            }
        }
    }
}



