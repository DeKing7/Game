package Map;

import Map.Environment.Action;

public interface Agent {

    Action getAction(Player player);

    void beforeAction(Player player);

    void afterAction(Player player);
}
