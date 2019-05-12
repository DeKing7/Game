package Map;

import java.util.Scanner;



public class Environment {

    public enum Element {
        GOBLIN, ABYSS, KNIGHT, UPGRADE, CASTLE
    }
    public enum Perception {
        SCREAM, STENCH, BREEZE, GLITTER, BUMP
    }
    public enum Action {
        GO_FORWARD, TURN_LEFT, TURN_RIGHT, GRAB, ATTACK, EXIT
    }
    public enum Result {
        WIN, LOOSE, IN_GAME
    }
    public static void trace() {
        try {
            System.out.println("Press ENTER to continue...");
            Scanner scanner = new Scanner(System.in).useDelimiter("");
            scanner.next();
        } catch (Exception error) {

        }
    }
    public static int getScore(Player player) {
        int sum = 0;
        if (player.isDead()) sum += -1000;
        if (player.hasCastle()) sum += +1000;
        if(player.hasUpgrade()) sum += +100;
        for(Action action : player.getActions()) {
            switch (action) {
                case GO_FORWARD:
                case TURN_LEFT:
                case TURN_RIGHT:
                case GRAB:
                    sum += 1;
                    break;
                case ATTACK:
                    sum += 10;
                    break;
            }
        }
        return sum;
    }
    public static String getIcon(Element element) {
        switch (element) {
            case GOBLIN: return "G";
            case KNIGHT: return "K";
            case ABYSS: return "A";
            case UPGRADE: return "$";
            case CASTLE: return "C";
        }
        return " ";
    }
    public static String getIcon(Perception perception) {
        switch (perception) {
            case GLITTER: return "*";
            case STENCH: return "~";
            case BREEZE: return "≈";
        }
        return " ";
    }
    public static String getIcon(Player player) {
        if (player.isDead()) return "†";

        switch (player.getDirection()) {
            case N: return "↑";
            case E: return "→";
            case S: return "↓";
            case W: return "←";
        }

        return "H";
    }
}
