import java.time.LocalDate;

public abstract class Vehicle {
    protected String vehicleId;
    protected String make;
    protected String model;
    protected int year;
    protected double dailyRate;
    protected boolean available;
    protected String fuelType;
    protected double fuelLevel;
    
    public Vehicle(String vehicleId, String make, String model, int year, double dailyRate, String fuelType) {
        this.vehicleId = vehicleId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.dailyRate = dailyRate;
        this.available = true;
        this.fuelType = fuelType;
        this.fuelLevel = 100.0;
    }
    
    public abstract double calculateRentalCost(int days);
    
    public double calculateFuelCharge(double fuelUsed) {
        return fuelUsed * getFuelPricePerLiter();
    }
    
    protected abstract double getFuelPricePerLiter();
    
    public String getVehicleInfo() {
        return String.format("%s %s %d (%s) - $%.2f/day - %s", 
                make, model, year, vehicleId, dailyRate, 
                available ? "Available" : "Rented");
    }
    
    public void rent() {
        this.available = false;
    }
    
    public void returnVehicle(double fuelReturned) {
        this.available = true;
        this.fuelLevel = Math.min(100.0, fuelReturned);
    }
    
    public double calculateLateFee(LocalDate returnDate, LocalDate actualReturnDate) {
        if (actualReturnDate.isAfter(returnDate)) {
            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(returnDate, actualReturnDate);
            return daysLate * dailyRate * 0.5;
        }
        return 0.0;
    }
    
    public String getVehicleId() { return vehicleId; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getDailyRate() { return dailyRate; }
    public boolean isAvailable() { return available; }
    public String getFuelType() { return fuelType; }
    public double getFuelLevel() { return fuelLevel; }
    
    public void setFuelLevel(double fuelLevel) { this.fuelLevel = fuelLevel; }
}
