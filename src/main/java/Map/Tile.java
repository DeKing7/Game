package Map;

import java.util.HashSet;
import Map.Environment.Element;

public class Tile {
    private int x, y, w, h;
    private HashSet<Environment.Element> elements = new HashSet<Element>();


    public Tile(int position, int width, int height) {
        x = position % width;
        y = position / width;
        w = width;
        h = height;
        clear();
    }


    public int getIndex() { return x + y * w; }


    public int getIndex(int x, int y) { return x + y * w; }


    public int getX() { return x; }


    public int getY() { return y; }



    public int[] getNeighbors() {
        int[] neighbors = {-1, -1, -1, -1};
        // Calculate the coordinates to each direction
        int north = y - 1;
        int south = y + 1;
        int west = x - 1;
        int east = x + 1;
        // Limit the boundaries
        if (north >= 0) neighbors[0] = getIndex(x, north);
        if (south < h)  neighbors[2] = getIndex(x, south);
        if (east < w)   neighbors[1] = getIndex(east, y);
        if (west >= 0)  neighbors[3] = getIndex(west, y);

        return neighbors;
    }


    public void clear() {
        elements.clear();
    }


    public void remove(Environment.Element item) {
        elements.remove(item);
    }


    public boolean isEmpty() {
        return elements.isEmpty();
    }


    public boolean contains(Element element) {
        return elements.contains(element);
    }


    public void setItem(Element element)  {
        elements.add(element);
    }
}

