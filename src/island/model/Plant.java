package island.model;

public class Plant {
    private final double weight;
    private final int maxCountPerLocation;

    public Plant(double weight, int maxCountPerLocation) {
        this.weight = weight;
        this.maxCountPerLocation = maxCountPerLocation;
    }

    public double getWeight() {
        return weight;
    }

    public int getMaxCountPerLocation() {
        return maxCountPerLocation;
    }
}
