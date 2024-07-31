package island.model;

public abstract class Herbivore extends Animal {
    public Herbivore(double weight, int maxCountPerLocation, int maxSpeed, double foodRequired) {
        super(weight, maxCountPerLocation, maxSpeed, foodRequired);
    }

    @Override
    public abstract void eat(Location location);
}
