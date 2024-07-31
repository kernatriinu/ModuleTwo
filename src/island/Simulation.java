package island;

import island.config.ConfigLoader;
import island.model.Animal;
import island.model.Island;
import island.model.Plant;
import island.model.animals.*;
import island.util.Notifier;
import island.util.RandomUtil;
import island.util.SingletonNotifier;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Simulation {
    private static final int ISLAND_WIDTH = 100;
    private static final int ISLAND_HEIGHT = 20;
    private static final int MAX_ITERATIONS = 10;
    static Notifier notifier = SingletonNotifier.getNotifier();

    public static void main(String[] args) {
        ConfigLoader.loadConfig();

        Island island = new Island(ISLAND_WIDTH, ISLAND_HEIGHT);

        // Populate the island with initial animals and plants
        populateIsland(island);

        // Create a scheduled executor service to run the simulation
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule the plant growth task
        scheduler.scheduleAtFixedRate(() -> growPlants(island), 0, 1, TimeUnit.SECONDS);

        // Schedule the animal actions task
        scheduler.scheduleAtFixedRate(() -> animalActions(island), 0, 1, TimeUnit.SECONDS);

        // Schedule the statistics display task
        scheduler.scheduleAtFixedRate(() -> displayStatistics(island), 0, 1, TimeUnit.SECONDS);

        // Schedule simulation termination
        scheduler.schedule(() -> {
            scheduler.shutdown();
            notifier.notifyInfo("Simulation ended.");
        }, MAX_ITERATIONS, TimeUnit.SECONDS);
    }

    private static void populateIsland(Island island) {
        // Example: Add some wolves and deer
        IntStream.range(0, 10).forEach(i -> {
            island.getLocation(RandomUtil.getRandomInt(ISLAND_WIDTH), RandomUtil.getRandomInt(ISLAND_HEIGHT)).getAnimals().add(new Wolf());
            island.getLocation(RandomUtil.getRandomInt(ISLAND_WIDTH), RandomUtil.getRandomInt(ISLAND_HEIGHT)).getAnimals().add(new Deer());
        });

        // Example: Add some plants
        IntStream.range(0, 100).forEach(i -> island.getLocation(RandomUtil.getRandomInt(ISLAND_WIDTH), RandomUtil.getRandomInt(ISLAND_HEIGHT)).getPlants().add(new Plant(1, 200)));
    }

    private static void growPlants(Island island) {
        // Logic to grow plants periodically
        IntStream.range(0, ISLAND_WIDTH).forEach(i ->
                IntStream.range(0, ISLAND_HEIGHT).forEach(j -> {
                    if (RandomUtil.tryAction(0.1)) { // Adjusted to 10% chance to grow a new plant
                        island.getLocation(i, j).getPlants().add(new Plant(1, 200));
                    }
                })
        );
    }

    private static void animalActions(Island island) {
        // Logic to perform actions for each animal on the island
        IntStream.range(0, ISLAND_WIDTH).forEach(i ->
                IntStream.range(0, ISLAND_HEIGHT).forEach(j -> island.getLocation(i, j).getAnimals().stream()
                        .filter(Animal::isAlive)
                        .forEach(animal -> {
                            animal.eat(island.getLocation(i, j));
                            animal.move(island);
                            animal.breed(island.getLocation(i, j));
                        }))
        );
    }

    private static void displayStatistics(Island island) {
        // Logic to display the current state of the island
        int totalAnimals = IntStream.range(0, ISLAND_WIDTH)
                .map(i -> IntStream.range(0, ISLAND_HEIGHT)
                        .map(j -> island.getLocation(i, j).getAnimals().size())
                        .sum())
                .sum();

        int totalPlants = IntStream.range(0, ISLAND_WIDTH)
                .map(i -> IntStream.range(0, ISLAND_HEIGHT)
                        .map(j -> island.getLocation(i, j).getPlants().size())
                        .sum())
                .sum();

        int iteration = (int) (System.currentTimeMillis() / 1000) % MAX_ITERATIONS; // Track the iteration
        notifier.notifyInfo("Iteration: " + iteration);
        notifier.notifyInfo("Total animals: " + totalAnimals);
        notifier.notifyInfo("Total plants: " + totalPlants);
    }
}
