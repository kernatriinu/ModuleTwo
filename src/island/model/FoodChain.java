package island.model;

import java.util.HashMap;
import java.util.Map;

public class FoodChain {
    private static final Map<String, Integer> animalIndexMap = new HashMap<>();
    private static final double[][] probabilities = {
            {0, 0, 0, 0, 0, 10, 15, 60, 80, 60, 70, 15, 10, 40, 0, 0}, // Wolf
            {0, 0, 15, 0, 0, 0, 0, 20, 40, 0, 0, 0, 0, 10, 0, 0},      // Boa
            {0, 0, 0, 0, 0, 0, 0, 70, 90, 0, 0, 0, 0, 60, 40, 0},     // Fox
            {0, 80, 0, 0, 0, 40, 80, 80, 90, 70, 70, 50, 20, 10, 0, 0},// Bear
            {0, 0, 10, 0, 0, 0, 0, 90, 90, 0, 0, 0, 0, 80, 0, 0},     // Eagle
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},       // Horse
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},       // Deer
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},       // Rabbit
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 100},      // Mouse
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},       // Goat
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},       // Sheep
            {0, 0, 0, 0, 0, 0, 0, 0, 50, 0, 0, 0, 0, 0, 90, 100},     // Boar
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},       // Buffalo
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 100},      // Duck
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},       // Caterpillar
    };

    static {
        animalIndexMap.put("Wolf", 0);
        animalIndexMap.put("Boa", 1);
        animalIndexMap.put("Fox", 2);
        animalIndexMap.put("Bear", 3);
        animalIndexMap.put("Eagle", 4);
        animalIndexMap.put("Horse", 5);
        animalIndexMap.put("Deer", 6);
        animalIndexMap.put("Rabbit", 7);
        animalIndexMap.put("Mouse", 8);
        animalIndexMap.put("Goat", 9);
        animalIndexMap.put("Sheep", 10);
        animalIndexMap.put("Boar", 11);
        animalIndexMap.put("Buffalo", 12);
        animalIndexMap.put("Duck", 13);
        animalIndexMap.put("Caterpillar", 14);
        animalIndexMap.put("Plants", 15);
    }

    public static double getProbability(String predator, String prey) {
        Integer predatorIndex = animalIndexMap.get(predator);
        Integer preyIndex = animalIndexMap.get(prey);

        if (predatorIndex == null || preyIndex == null) {
            return 0;
        }

        return probabilities[predatorIndex][preyIndex];
    }
}
