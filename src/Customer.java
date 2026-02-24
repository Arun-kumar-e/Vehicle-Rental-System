import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String phone;
    private String driverLicense;
    private List<RentalRecord> rentalHistory;
    private boolean isPremiumMember;
    
    public Customer(String customerId, String name, String email, String phone, String driverLicense) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.driverLicense = driverLicense;
        this.rentalHistory = new ArrayList<>();
        this.isPremiumMember = false;
    }
    
    public void addRentalRecord(RentalRecord record) {
        rentalHistory.add(record);
    }
    
    public double getTotalSpent() {
        return rentalHistory.stream()
                .mapToDouble(RentalRecord::getTotalCost)
                .sum();
    }
    
    public int getTotalRentals() {
        return rentalHistory.size();
    }
    
    public boolean canRent() {
        return rentalHistory.stream()
                .noneMatch(record -> record.getActualReturnDate() == null);
    }
    
    public void upgradeToPremium() {
        if (getTotalRentals() >= 5 || getTotalSpent() >= 1000) {
            this.isPremiumMember = true;
        }
    }
    
    public double applyDiscount(double amount) {
        if (isPremiumMember) {
            return amount * 0.9;
        }
        return amount;
    }
    
    public String getCustomerInfo() {
        return String.format("Customer: %s (%s) - %s - %s - %s%s", 
                name, customerId, email, phone, driverLicense,
                isPremiumMember ? " [PREMIUM]" : "");
    }
    
    public String getRentalHistoryString() {
        if (rentalHistory.isEmpty()) {
            return "No rental history";
        }
        
        StringBuilder sb = new StringBuilder("Rental History:\n");
        for (RentalRecord record : rentalHistory) {
            sb.append("  ").append(record.toString()).append("\n");
        }
        return sb.toString();
    }
    
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getDriverLicense() { return driverLicense; }
    public List<RentalRecord> getRentalHistory() { return rentalHistory; }
    public boolean isPremiumMember() { return isPremiumMember; }
    
    public void setPremiumMember(boolean premiumMember) { isPremiumMember = premiumMember; }
}
