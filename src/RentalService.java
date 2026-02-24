import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RentalService {
    private Map<String, Vehicle> vehicles;
    private Map<String, Customer> customers;
    private List<RentalRecord> rentalRecords;
    private FileStorageService fileStorage;
    
    public RentalService() {
        this.vehicles = new HashMap<>();
        this.customers = new HashMap<>();
        this.rentalRecords = new ArrayList<>();
        this.fileStorage = new FileStorageService();
        loadData();
    }
    
    public void addVehicle(Vehicle vehicle) {
        vehicles.put(vehicle.getVehicleId(), vehicle);
        saveData();
    }
    
    public void addCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        saveData();
    }
    
    public RentalRecord rentVehicle(String customerId, String vehicleId, int days) {
        Customer customer = customers.get(customerId);
        Vehicle vehicle = vehicles.get(vehicleId);
        
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found: " + customerId);
        }
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle not found: " + vehicleId);
        }
        if (!vehicle.isAvailable()) {
            throw new IllegalArgumentException("Vehicle is not available: " + vehicleId);
        }
        if (!customer.canRent()) {
            throw new IllegalArgumentException("Customer has active rentals: " + customerId);
        }
        
        LocalDate rentalDate = LocalDate.now();
        LocalDate expectedReturnDate = rentalDate.plusDays(days);
        double rentalCost = vehicle.calculateRentalCost(days);
        rentalCost = customer.applyDiscount(rentalCost);
        
        String recordId = generateRecordId();
        RentalRecord record = new RentalRecord(recordId, customerId, vehicleId, 
                                              rentalDate, expectedReturnDate, 
                                              rentalCost, vehicle.getFuelLevel());
        
        vehicle.rent();
        customer.addRentalRecord(record);
        rentalRecords.add(record);
        customer.upgradeToPremium();
        
        saveData();
        return record;
    }
    
    public RentalRecord returnVehicle(String recordId, double finalFuelLevel) {
        RentalRecord record = findRentalRecord(recordId);
        if (record == null) {
            throw new IllegalArgumentException("Rental record not found: " + recordId);
        }
        
        Vehicle vehicle = vehicles.get(record.getVehicleId());
        Customer customer = customers.get(record.getCustomerId());
        
        LocalDate actualReturnDate = LocalDate.now();
        
        double lateFee = vehicle.calculateLateFee(record.getExpectedReturnDate(), actualReturnDate);
        double fuelUsed = record.getFuelUsed();
        double fuelCharge = vehicle.calculateFuelCharge(fuelUsed);
        
        record.closeRental(actualReturnDate, finalFuelLevel, lateFee, fuelCharge);
        vehicle.returnVehicle(finalFuelLevel);
        
        saveData();
        return record;
    }
    
    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> available = new ArrayList<>();
        for (Vehicle vehicle : vehicles.values()) {
            if (vehicle.isAvailable()) {
                available.add(vehicle);
            }
        }
        return available;
    }
    
    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles.values());
    }
    
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }
    
    public List<RentalRecord> getAllRentalRecords() {
        return new ArrayList<>(rentalRecords);
    }
    
    public List<RentalRecord> getActiveRentals() {
        List<RentalRecord> active = new ArrayList<>();
        for (RentalRecord record : rentalRecords) {
            if (record.isActive()) {
                active.add(record);
            }
        }
        return active;
    }
    
    public Vehicle findVehicle(String vehicleId) {
        return vehicles.get(vehicleId);
    }
    
    public Customer findCustomer(String customerId) {
        return customers.get(customerId);
    }
    
    public RentalRecord findRentalRecord(String recordId) {
        for (RentalRecord record : rentalRecords) {
            if (record.getRecordId().equals(recordId)) {
                return record;
            }
        }
        return null;
    }
    
    public double calculateTotalRevenue() {
        return rentalRecords.stream()
                .filter(record -> !record.isActive())
                .mapToDouble(RentalRecord::getTotalCost)
                .sum();
    }
    
    public Map<String, Integer> getVehicleUtilization() {
        Map<String, Integer> utilization = new HashMap<>();
        for (RentalRecord record : rentalRecords) {
            String vehicleId = record.getVehicleId();
            utilization.put(vehicleId, utilization.getOrDefault(vehicleId, 0) + 1);
        }
        return utilization;
    }
    
    private String generateRecordId() {
        return "R" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private void saveData() {
        fileStorage.saveData(vehicles, customers, rentalRecords);
    }
    
    private void loadData() {
        fileStorage.loadData(vehicles, customers, rentalRecords);
    }
    
    public void displayAvailableVehicles() {
        List<Vehicle> available = getAvailableVehicles();
        if (available.isEmpty()) {
            System.out.println("No vehicles available for rent.");
            return;
        }
        
        System.out.println("\n=== Available Vehicles ===");
        for (Vehicle vehicle : available) {
            System.out.println(vehicle.getVehicleInfo());
        }
    }
    
    public void displayAllVehicles() {
        System.out.println("\n=== All Vehicles ===");
        for (Vehicle vehicle : vehicles.values()) {
            System.out.println(vehicle.getVehicleInfo());
        }
    }
    
    public void displayCustomers() {
        System.out.println("\n=== Customers ===");
        for (Customer customer : customers.values()) {
            System.out.println(customer.getCustomerInfo());
        }
    }
    
    public void displayRentalRecords() {
        System.out.println("\n=== Rental Records ===");
        for (RentalRecord record : rentalRecords) {
            System.out.println(record.toString());
        }
    }
}
