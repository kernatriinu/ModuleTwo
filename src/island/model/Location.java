package island.model;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private final List<Animal> animals;
    private final List<Plant> plants;

    public Location() {
        this.animals = new ArrayList<>();
        this.plants = new ArrayList<>();
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Plant> getPlants() {
        return plants;
    }
}
