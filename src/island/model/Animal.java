package island.model;

import island.util.RandomUtil;

public abstract class Animal {

    private final double weight;
    private final int maxCountPerLocation;
    private final int maxSpeed;
    private final double foodRequired;
    private double foodEaten;
    private boolean alive;

    public Animal(double weight, int maxCountPerLocation, int maxSpeed, double foodRequired) {
        this.weight = weight;
        this.maxCountPerLocation = maxCountPerLocation;
        this.maxSpeed = maxSpeed;
        this.foodRequired = foodRequired;
        this.foodEaten = 0;
        this.alive = true;
    }

    public double getWeight() {
        return weight;
    }

    public int getMaxCountPerLocation() {
        return maxCountPerLocation;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public double getFoodRequired() {
        return foodRequired;
    }

    public double getFoodEaten() {
        return foodEaten;
    }

    public boolean isAlive() {
        return alive;
    }

    protected void setFoodEaten(double foodEaten) {
        this.foodEaten = foodEaten;
    }

    protected void setAlive(boolean alive) {
        this.alive = alive;
    }

    public abstract void eat(Location location);
    public abstract void move(Island island);
    public abstract void breed(Location location);
    public abstract void die(Location location);
}
