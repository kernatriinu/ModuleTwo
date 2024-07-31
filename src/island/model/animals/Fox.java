package island.model.animals;

import island.config.ConfigLoader;
import island.model.*;
import island.util.Notifier;
import island.util.RandomUtil;
import island.util.SingletonNotifier;

public class Fox extends Carnivore {
    static Notifier notifier = SingletonNotifier.getNotifier();
    public Fox() {
        super(
                ConfigLoader.getDoubleProperty("fox.weight"),
                ConfigLoader.getIntProperty("fox.maxCountPerLocation"),
                ConfigLoader.getIntProperty("fox.maxSpeed"),
                ConfigLoader.getDoubleProperty("fox.foodRequired")
        );
    }

    @Override
    public void eat(Location location) {
        for (Animal animal : location.getAnimals()) {
            if (animal != this && animal.isAlive()) {
                double probability = FoodChain.getProbability("Fox", animal.getClass().getSimpleName());
                if (RandomUtil.tryAction(probability / 100)) {
                    animal.die(location);
                    setFoodEaten(getFoodEaten() + animal.getWeight());
                    notifier.notifyInfo("Fox ate " + animal.getClass().getSimpleName());
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
        notifier.notifyInfo("Fox moved to (" + x + ", " + y + ")");
    }

    @Override
    public void breed(Location location) {
        for (Animal animal : location.getAnimals()) {
            if (animal != this && animal.getClass().equals(this.getClass()) && animal.isAlive()) {
                location.getAnimals().add(new Fox());
                notifier.notifyInfo("Fox bred");
                break;
            }
        }
    }

    @Override
    public void die(Location location) {
        setAlive(false);
        location.getAnimals().remove(this);
        notifier.notifyInfo("Fox died");
    }
}
