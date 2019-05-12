package Map;

import Map.Environment.Action;
import Map.Environment.Result;


    public class Runner {
        private static final int DEFAULT_MAX_STEPS = 200;

        private final World world;
        private int iterations = 0;
        private int maxIterations;

        public Runner(World world) {
            this(world, DEFAULT_MAX_STEPS);
        }

        public Runner(World world, int maxIterations) {
            this.world = world;
            this.maxIterations = maxIterations;
        }


        public void run(Agent agent) throws InterruptedException {
            Player player = world.getPlayer();

            while (canMove()) {
                agent.beforeAction(player);
                Action actions = agent.getAction(player);
                player.setAction(actions);
                agent.afterAction(player);
                iterations++;
            }
        }

        public boolean canMove() {
            Player player = world.getPlayer();
            return iterations < maxIterations && world.getResult() != Result.WIN &&
                    player.isAlive() && player.getLastAction() != Action.EXIT;
        }

    }
