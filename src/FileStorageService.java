import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileStorageService {
    private static final String VEHICLES_FILE = "vehicles.dat";
    private static final String CUSTOMERS_FILE = "customers.dat";
    private static final String RECORDS_FILE = "records.dat";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public void saveData(Map<String, Vehicle> vehicles, Map<String, Customer> customers, 
                        List<RentalRecord> rentalRecords) {
        try {
            saveVehicles(vehicles);
            saveCustomers(customers);
            saveRentalRecords(rentalRecords);
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
    
    public void loadData(Map<String, Vehicle> vehicles, Map<String, Customer> customers, 
                        List<RentalRecord> rentalRecords) {
        try {
            loadVehicles(vehicles);
            loadCustomers(customers);
            loadRentalRecords(rentalRecords);
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }
    
    private void saveVehicles(Map<String, Vehicle> vehicles) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(VEHICLES_FILE))) {
            for (Vehicle vehicle : vehicles.values()) {
                if (vehicle instanceof Car) {
                    Car car = (Car) vehicle;
                    writer.printf("CAR|%s|%s|%s|%d|%.2f|%s|%d|%s|%.1f|%b%n",
                            car.getVehicleId(), car.getMake(), car.getModel(), car.getYear(),
                            car.getDailyRate(), car.getFuelType(), car.getDoors(),
                            car.getTransmission(), car.getFuelLevel(), car.isAvailable());
                } else if (vehicle instanceof Motorcycle) {
                    Motorcycle motorcycle = (Motorcycle) vehicle;
                    writer.printf("MOTORCYCLE|%s|%s|%s|%d|%.2f|%s|%d|%s|%.1f|%b%n",
                            motorcycle.getVehicleId(), motorcycle.getMake(), motorcycle.getModel(),
                            motorcycle.getYear(), motorcycle.getDailyRate(), motorcycle.getFuelType(),
                            motorcycle.getEngineCC(), motorcycle.getType(), motorcycle.getFuelLevel(),
                            motorcycle.isAvailable());
                } else if (vehicle instanceof Truck) {
                    Truck truck = (Truck) vehicle;
                    writer.printf("TRUCK|%s|%s|%s|%d|%.2f|%s|%.1f|%b|%.1f|%b%n",
                            truck.getVehicleId(), truck.getMake(), truck.getModel(), truck.getYear(),
                            truck.getDailyRate(), truck.getFuelType(), truck.getCargoCapacity(),
                            truck.hasRefrigeration(), truck.getFuelLevel(), truck.isAvailable());
                }
            }
        }
    }
    
    private void loadVehicles(Map<String, Vehicle> vehicles) throws IOException {
        File file = new File(VEHICLES_FILE);
        if (!file.exists()) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 10) continue;
                
                String type = parts[0];
                String vehicleId = parts[1];
                String make = parts[2];
                String model = parts[3];
                int year = Integer.parseInt(parts[4]);
                double dailyRate = Double.parseDouble(parts[5]);
                String fuelType = parts[6];
                double fuelLevel = Double.parseDouble(parts[parts.length - 2]);
                boolean available = Boolean.parseBoolean(parts[parts.length - 1]);
                
                Vehicle vehicle;
                if (type.equals("CAR")) {
                    int doors = Integer.parseInt(parts[7]);
                    String transmission = parts[8];
                    vehicle = new Car(vehicleId, make, model, year, dailyRate, fuelType, doors, transmission);
                } else if (type.equals("MOTORCYCLE")) {
                    int engineCC = Integer.parseInt(parts[7]);
                    String motorcycleType = parts[8];
                    vehicle = new Motorcycle(vehicleId, make, model, year, dailyRate, fuelType, engineCC, motorcycleType);
                } else if (type.equals("TRUCK")) {
                    double cargoCapacity = Double.parseDouble(parts[7]);
                    boolean hasRefrigeration = Boolean.parseBoolean(parts[8]);
                    vehicle = new Truck(vehicleId, make, model, year, dailyRate, fuelType, cargoCapacity, hasRefrigeration);
                } else {
                    continue;
                }
                
                vehicle.setFuelLevel(fuelLevel);
                if (!available) {
                    vehicle.rent();
                }
                vehicles.put(vehicleId, vehicle);
            }
        }
    }
    
    private void saveCustomers(Map<String, Customer> customers) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUSTOMERS_FILE))) {
            for (Customer customer : customers.values()) {
                writer.printf("%s|%s|%s|%s|%s|%b%n",
                        customer.getCustomerId(), customer.getName(), customer.getEmail(),
                        customer.getPhone(), customer.getDriverLicense(), customer.isPremiumMember());
            }
        }
    }
    
    private void loadCustomers(Map<String, Customer> customers) throws IOException {
        File file = new File(CUSTOMERS_FILE);
        if (!file.exists()) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length != 6) continue;
                
                Customer customer = new Customer(parts[0], parts[1], parts[2], parts[3], parts[4]);
                customer.setPremiumMember(Boolean.parseBoolean(parts[5]));
                customers.put(parts[0], customer);
            }
        }
    }
    
    private void saveRentalRecords(List<RentalRecord> rentalRecords) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RECORDS_FILE))) {
            for (RentalRecord record : rentalRecords) {
                writer.printf("%s|%s|%s|%s|%s|%.2f|%.2f|%.2f|%.2f|%.2f|%.2f|%s%n",
                        record.getRecordId(), record.getCustomerId(), record.getVehicleId(),
                        record.getRentalDate().format(DATE_FORMATTER),
                        record.getExpectedReturnDate().format(DATE_FORMATTER),
                        record.getRentalCost(), record.getLateFee(), record.getFuelCharge(),
                        record.getTotalCost(), record.getInitialFuelLevel(), record.getFinalFuelLevel(),
                        record.getActualReturnDate() != null ? 
                                record.getActualReturnDate().format(DATE_FORMATTER) : "NULL");
            }
        }
    }
    
    private void loadRentalRecords(List<RentalRecord> rentalRecords) throws IOException {
        File file = new File(RECORDS_FILE);
        if (!file.exists()) return;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length != 13) continue;
                
                String recordId = parts[0];
                String customerId = parts[1];
                String vehicleId = parts[2];
                LocalDate rentalDate = LocalDate.parse(parts[3], DATE_FORMATTER);
                LocalDate expectedReturnDate = LocalDate.parse(parts[4], DATE_FORMATTER);
                double rentalCost = Double.parseDouble(parts[5]);
                double initialFuelLevel = Double.parseDouble(parts[9]);
                
                RentalRecord record = new RentalRecord(recordId, customerId, vehicleId, 
                                                     rentalDate, expectedReturnDate, 
                                                     rentalCost, initialFuelLevel);
                
                if (!parts[12].equals("NULL")) {
                    LocalDate actualReturnDate = LocalDate.parse(parts[12], DATE_FORMATTER);
                    double lateFee = Double.parseDouble(parts[6]);
                    double fuelCharge = Double.parseDouble(parts[7]);
                    double finalFuelLevel = Double.parseDouble(parts[10]);
                    record.closeRental(actualReturnDate, finalFuelLevel, lateFee, fuelCharge);
                }
                
                rentalRecords.add(record);
            }
        }
    }
}
