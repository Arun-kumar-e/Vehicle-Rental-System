public class Car extends Vehicle {
    private int doors;
    private String transmission;
    
    public Car(String vehicleId, String make, String model, int year, double dailyRate, 
               String fuelType, int doors, String transmission) {
        super(vehicleId, make, model, year, dailyRate, fuelType);
        this.doors = doors;
        this.transmission = transmission;
    }
    
    @Override
    public double calculateRentalCost(int days) {
        double baseCost = dailyRate * days;
        if (days >= 7) {
            baseCost *= 0.9;
        } else if (days >= 3) {
            baseCost *= 0.95;
        }
        return baseCost;
    }
    
    @Override
    protected double getFuelPricePerLiter() {
        switch (fuelType.toLowerCase()) {
            case "petrol": return 1.85;
            case "diesel": return 1.95;
            case "electric": return 0.15;
            default: return 1.80;
        }
    }
    
    @Override
    public String getVehicleInfo() {
        return String.format("CAR: %s - %d doors, %s transmission", 
                super.getVehicleInfo(), doors, transmission);
    }
    
    public int getDoors() { return doors; }
    public String getTransmission() { return transmission; }
}
