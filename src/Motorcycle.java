public class Motorcycle extends Vehicle {
    private int engineCC;
    private String type;
    
    public Motorcycle(String vehicleId, String make, String model, int year, double dailyRate, 
                     String fuelType, int engineCC, String type) {
        super(vehicleId, make, model, year, dailyRate, fuelType);
        this.engineCC = engineCC;
        this.type = type;
    }
    
    @Override
    public double calculateRentalCost(int days) {
        double baseCost = dailyRate * days;
        if (type.equalsIgnoreCase("sport")) {
            baseCost *= 1.2;
        } else if (type.equalsIgnoreCase("cruiser")) {
            baseCost *= 1.1;
        }
        if (days >= 7) {
            baseCost *= 0.85;
        }
        return baseCost;
    }
    
    @Override
    protected double getFuelPricePerLiter() {
        return 1.90;
    }
    
    @Override
    public String getVehicleInfo() {
        return String.format("MOTORCYCLE: %s - %dcc, %s", 
                super.getVehicleInfo(), engineCC, type);
    }
    
    public int getEngineCC() { return engineCC; }
    public String getType() { return type; }
}
