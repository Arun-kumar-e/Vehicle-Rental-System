import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RentalRecord {
    private String recordId;
    private String customerId;
    private String vehicleId;
    private LocalDate rentalDate;
    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;
    private double rentalCost;
    private double lateFee;
    private double fuelCharge;
    private double totalCost;
    private double initialFuelLevel;
    private double finalFuelLevel;
    
    public RentalRecord(String recordId, String customerId, String vehicleId, 
                       LocalDate rentalDate, LocalDate expectedReturnDate, 
                       double rentalCost, double initialFuelLevel) {
        this.recordId = recordId;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.rentalDate = rentalDate;
        this.expectedReturnDate = expectedReturnDate;
        this.rentalCost = rentalCost;
        this.initialFuelLevel = initialFuelLevel;
        this.finalFuelLevel = initialFuelLevel;
        this.lateFee = 0.0;
        this.fuelCharge = 0.0;
        this.totalCost = rentalCost;
        this.actualReturnDate = null;
    }
    
    public void closeRental(LocalDate actualReturnDate, double finalFuelLevel, double lateFee, double fuelCharge) {
        this.actualReturnDate = actualReturnDate;
        this.finalFuelLevel = finalFuelLevel;
        this.lateFee = lateFee;
        this.fuelCharge = fuelCharge;
        this.totalCost = rentalCost + lateFee + fuelCharge;
    }
    
    public boolean isActive() {
        return actualReturnDate == null;
    }
    
    public long getRentalDays() {
        LocalDate endDate = actualReturnDate != null ? actualReturnDate : LocalDate.now();
        return java.time.temporal.ChronoUnit.DAYS.between(rentalDate, endDate);
    }
    
    public long getDaysLate() {
        if (actualReturnDate == null) return 0;
        if (actualReturnDate.isBefore(expectedReturnDate) || actualReturnDate.equals(expectedReturnDate)) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(expectedReturnDate, actualReturnDate);
    }
    
    public double getFuelUsed() {
        return Math.max(0, initialFuelLevel - finalFuelLevel);
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        sb.append("Record #").append(recordId)
          .append(" - Vehicle: ").append(vehicleId)
          .append(" - Customer: ").append(customerId)
          .append(" - Rented: ").append(rentalDate.format(formatter))
          .append(" - Expected: ").append(expectedReturnDate.format(formatter));
        
        if (actualReturnDate != null) {
            sb.append(" - Returned: ").append(actualReturnDate.format(formatter))
              .append(" - Cost: $").append(String.format("%.2f", totalCost));
            if (lateFee > 0) {
                sb.append(" (Late fee: $").append(String.format("%.2f", lateFee)).append(")");
            }
            if (fuelCharge > 0) {
                sb.append(" (Fuel: $").append(String.format("%.2f", fuelCharge)).append(")");
            }
        } else {
            sb.append(" - [ACTIVE]");
        }
        
        return sb.toString();
    }
    
    public String getRecordId() { return recordId; }
    public String getCustomerId() { return customerId; }
    public String getVehicleId() { return vehicleId; }
    public LocalDate getRentalDate() { return rentalDate; }
    public LocalDate getExpectedReturnDate() { return expectedReturnDate; }
    public LocalDate getActualReturnDate() { return actualReturnDate; }
    public double getRentalCost() { return rentalCost; }
    public double getLateFee() { return lateFee; }
    public double getFuelCharge() { return fuelCharge; }
    public double getTotalCost() { return totalCost; }
    public double getInitialFuelLevel() { return initialFuelLevel; }
    public double getFinalFuelLevel() { return finalFuelLevel; }
}
