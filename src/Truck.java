public class Truck extends Vehicle {
    private double cargoCapacity;
    private boolean hasRefrigeration;
    
    public Truck(String vehicleId, String make, String model, int year, double dailyRate, 
                 String fuelType, double cargoCapacity, boolean hasRefrigeration) {
        super(vehicleId, make, model, year, dailyRate, fuelType);
        this.cargoCapacity = cargoCapacity;
        this.hasRefrigeration = hasRefrigeration;
    }
    
    @Override
    public double calculateRentalCost(int days) {
        double baseCost = dailyRate * days;
        if (hasRefrigeration) {
            baseCost *= 1.3;
        }
        if (cargoCapacity > 5.0) {
            baseCost *= 1.2;
        }
        if (days >= 5) {
            baseCost *= 0.9;
        }
        return baseCost;
    }
    
    @Override
    protected double getFuelPricePerLiter() {
        return 2.10;
    }
    
    @Override
    public String getVehicleInfo() {
        return String.format("TRUCK: %s - %.1ft cargo, %s", 
                super.getVehicleInfo(), cargoCapacity, 
                hasRefrigeration ? "refrigerated" : "non-refrigerated");
    }
    
    public double getCargoCapacity() { return cargoCapacity; }
    public boolean hasRefrigeration() { return hasRefrigeration; }
}
