package island.model.animals;

import island.config.ConfigLoader;
import island.model.*;
import island.util.Notifier;
import island.util.SingletonNotifier;

public class Caterpillar extends Herbivore {
    static Notifier notifier = SingletonNotifier.getNotifier();

    public Caterpillar() {
        super(
                ConfigLoader.getDoubleProperty("caterpillar.weight"),
                ConfigLoader.getIntProperty("caterpillar.maxCountPerLocation"),
                ConfigLoader.getIntProperty("caterpillar.maxSpeed"),
                ConfigLoader.getDoubleProperty("caterpillar.foodRequired")
        );
    }

    @Override
    public void eat(Location location) {
        for (Plant plant : location.getPlants()) {
            location.getPlants().remove(plant);
            setFoodEaten(getFoodEaten() + plant.getWeight());
            notifier.notifyInfo("Caterpillar ate a plant");
            break;
        }
        if (getFoodEaten() < getFoodRequired()) {
            setAlive(false);
        }
    }

    @Override
    public void move(Island island) {
        // Caterpillar does not move
    }

    @Override
    public void breed(Location location) {
        for (Animal animal : location.getAnimals()) {
            if (animal != this && animal.getClass().equals(this.getClass()) && animal.isAlive()) {
                location.getAnimals().add(new Caterpillar());
                notifier.notifyInfo("Caterpillar bred");
                break;
            }
        }
    }

    @Override
    public void die(Location location) {
        setAlive(false);
        location.getAnimals().remove(this);
        notifier.notifyInfo("Caterpillar died");
    }
}
