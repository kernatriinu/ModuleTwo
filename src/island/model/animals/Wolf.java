package island.model.animals;

import island.config.ConfigLoader;
import island.model.*;
import island.util.Notifier;
import island.util.RandomUtil;
import island.util.SingletonNotifier;

public class Wolf extends Carnivore {
    static Notifier notifier = SingletonNotifier.getNotifier();

    public Wolf() {
        super(
                ConfigLoader.getDoubleProperty("wolf.weight"),
                ConfigLoader.getIntProperty("wolf.maxCountPerLocation"),
                ConfigLoader.getIntProperty("wolf.maxSpeed"),
                ConfigLoader.getDoubleProperty("wolf.foodRequired")
        );
    }

    @Override
    public void eat(Location location) {
        for (Animal animal : location.getAnimals()) {
            if (animal != this && animal.isAlive()) {
                double probability = FoodChain.getProbability("Wolf", animal.getClass().getSimpleName());
                if (RandomUtil.tryAction(probability / 100)) {
                    animal.die(location);
                    setFoodEaten(getFoodEaten() + animal.getWeight());
                    notifier.notifyInfo("Wolf ate " + animal.getClass().getSimpleName());
                    break;
                }
            }
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
        notifier.notifyInfo("Wolf moved to (" + x + ", " + y + ")");
    }

    @Override
    public void breed(Location location) {
        for (Animal animal : location.getAnimals()) {
            if (animal != this && animal.getClass().equals(this.getClass()) && animal.isAlive()) {
                location.getAnimals().add(new Wolf());
                notifier.notifyInfo("Wolf bred");
                break;
            }
        }
    }

    @Override
    public void die(Location location) {
        setAlive(false);
        location.getAnimals().remove(this);
        notifier.notifyInfo("Wolf died");
    }
}
