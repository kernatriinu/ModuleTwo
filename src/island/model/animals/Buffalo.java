package island.model.animals;

import island.config.ConfigLoader;
import island.model.*;
import island.util.Notifier;
import island.util.RandomUtil;
import island.util.SingletonNotifier;

public class Buffalo extends Herbivore {
    static Notifier notifier = SingletonNotifier.getNotifier();
    public Buffalo() {
        super(
                ConfigLoader.getDoubleProperty("buffalo.weight"),
                ConfigLoader.getIntProperty("buffalo.maxCountPerLocation"),
                ConfigLoader.getIntProperty("buffalo.maxSpeed"),
                ConfigLoader.getDoubleProperty("buffalo.foodRequired")
        );
    }

    @Override
    public void eat(Location location) {
        for (Plant plant : location.getPlants()) {
            location.getPlants().remove(plant);
            setFoodEaten(getFoodEaten() + plant.getWeight());
            notifier.notifyInfo("Buffalo ate a plant");
            break;
        }
        if (getFoodEaten() < getFoodRequired()) {
            setAlive(false);
        }
    }

    @Override
    public void move(Island island) {
        int x = RandomUtil.getRandomInt(island.getWidth());
        int y = RandomUtil.getRandomInt(island.getHeight());
        Location newLocation = island.getLocation(x, y);
        newLocation.getAnimals().add(this);
        notifier.notifyInfo("Buffalo moved to (" + x + ", " + y + ")");
    }

    @Override
    public void breed(Location location) {
        for (Animal animal : location.getAnimals()) {
            if (animal != this && animal.getClass().equals(this.getClass()) && animal.isAlive()) {
                location.getAnimals().add(new Buffalo());
                notifier.notifyInfo("Buffalo bred");
                break;
            }
        }
    }

    @Override
    public void die(Location location) {
        setAlive(false);
        location.getAnimals().remove(this);
        notifier.notifyInfo("Buffalo died");
    }
}
