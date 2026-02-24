import java.util.Map;
import java.util.Scanner;

public class Main {
    private static RentalService rentalService;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        rentalService = new RentalService();
        scanner = new Scanner(System.in);
        
        initializeSampleData();
        
        System.out.println("=== Vehicle Rental System ===");
        System.out.println("Welcome to the Vehicle Rental Management System!");
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1:
                        manageVehicles();
                        break;
                    case 2:
                        manageCustomers();
                        break;
                    case 3:
                        rentVehicle();
                        break;
                    case 4:
                        returnVehicle();
                        break;
                    case 5:
                        viewAvailableVehicles();
                        break;
                    case 6:
                        viewReports();
                        break;
                    case 7:
                        running = false;
                        System.out.println("Thank you for using Vehicle Rental System!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private static void initializeSampleData() {
        System.out.println("Initializing sample data...");
        
        rentalService.addVehicle(new Car("CAR001", "Toyota", "Camry", 2023, 45.0, "petrol", 4, "automatic"));
        rentalService.addVehicle(new Car("CAR002", "Honda", "Civic", 2022, 40.0, "petrol", 4, "manual"));
        rentalService.addVehicle(new Car("CAR003", "Tesla", "Model 3", 2023, 80.0, "electric", 4, "automatic"));
        
        rentalService.addVehicle(new Motorcycle("MOTO001", "Yamaha", "MT-07", 2023, 35.0, "petrol", 689, "sport"));
        rentalService.addVehicle(new Motorcycle("MOTO002", "Harley", "Street 750", 2022, 50.0, "petrol", 749, "cruiser"));
        
        rentalService.addVehicle(new Truck("TRUCK001", "Ford", "F-150", 2023, 120.0, "diesel", 3.5, false));
        rentalService.addVehicle(new Truck("TRUCK002", "Mercedes", "Actros", 2022, 200.0, "diesel", 8.0, true));
        
        rentalService.addCustomer(new Customer("CUST001", "John Doe", "john@email.com", "555-0101", "DL123456"));
        rentalService.addCustomer(new Customer("CUST002", "Jane Smith", "jane@email.com", "555-0102", "DL789012"));
        rentalService.addCustomer(new Customer("CUST003", "Bob Johnson", "bob@email.com", "555-0103", "DL345678"));
        
        System.out.println("Sample data initialized successfully!");
    }
    
    private static void displayMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Manage Vehicles");
        System.out.println("2. Manage Customers");
        System.out.println("3. Rent Vehicle");
        System.out.println("4. Return Vehicle");
        System.out.println("5. View Available Vehicles");
        System.out.println("6. View Reports");
        System.out.println("7. Exit");
    }
    
    private static void manageVehicles() {
        while (true) {
            System.out.println("\n=== Vehicle Management ===");
            System.out.println("1. Add Car");
            System.out.println("2. Add Motorcycle");
            System.out.println("3. Add Truck");
            System.out.println("4. View All Vehicles");
            System.out.println("5. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addCar();
                    break;
                case 2:
                    addMotorcycle();
                    break;
                case 3:
                    addTruck();
                    break;
                case 4:
                    rentalService.displayAllVehicles();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private static void addCar() {
        System.out.println("\n=== Add New Car ===");
        String vehicleId = getStringInput("Vehicle ID: ");
        String make = getStringInput("Make: ");
        String model = getStringInput("Model: ");
        int year = getIntInput("Year: ");
        double dailyRate = getDoubleInput("Daily Rate: ");
        String fuelType = getStringInput("Fuel Type (petrol/diesel/electric): ");
        int doors = getIntInput("Number of doors: ");
        String transmission = getStringInput("Transmission (manual/automatic): ");
        
        Car car = new Car(vehicleId, make, model, year, dailyRate, fuelType, doors, transmission);
        rentalService.addVehicle(car);
        System.out.println("Car added successfully!");
    }
    
    private static void addMotorcycle() {
        System.out.println("\n=== Add New Motorcycle ===");
        String vehicleId = getStringInput("Vehicle ID: ");
        String make = getStringInput("Make: ");
        String model = getStringInput("Model: ");
        int year = getIntInput("Year: ");
        double dailyRate = getDoubleInput("Daily Rate: ");
        String fuelType = getStringInput("Fuel Type: ");
        int engineCC = getIntInput("Engine CC: ");
        String type = getStringInput("Type (sport/cruiser): ");
        
        Motorcycle motorcycle = new Motorcycle(vehicleId, make, model, year, dailyRate, fuelType, engineCC, type);
        rentalService.addVehicle(motorcycle);
        System.out.println("Motorcycle added successfully!");
    }
    
    private static void addTruck() {
        System.out.println("\n=== Add New Truck ===");
        String vehicleId = getStringInput("Vehicle ID: ");
        String make = getStringInput("Make: ");
        String model = getStringInput("Model: ");
        int year = getIntInput("Year: ");
        double dailyRate = getDoubleInput("Daily Rate: ");
        String fuelType = getStringInput("Fuel Type: ");
        double cargoCapacity = getDoubleInput("Cargo Capacity (tons): ");
        boolean hasRefrigeration = getBooleanInput("Has Refrigeration? (y/n): ");
        
        Truck truck = new Truck(vehicleId, make, model, year, dailyRate, fuelType, cargoCapacity, hasRefrigeration);
        rentalService.addVehicle(truck);
        System.out.println("Truck added successfully!");
    }
    
    private static void manageCustomers() {
        while (true) {
            System.out.println("\n=== Customer Management ===");
            System.out.println("1. Add Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. View Customer Details");
            System.out.println("4. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    rentalService.displayCustomers();
                    break;
                case 3:
                    viewCustomerDetails();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private static void addCustomer() {
        System.out.println("\n=== Add New Customer ===");
        String customerId = getStringInput("Customer ID: ");
        String name = getStringInput("Name: ");
        String email = getStringInput("Email: ");
        String phone = getStringInput("Phone: ");
        String driverLicense = getStringInput("Driver License: ");
        
        Customer customer = new Customer(customerId, name, email, phone, driverLicense);
        rentalService.addCustomer(customer);
        System.out.println("Customer added successfully!");
    }
    
    private static void viewCustomerDetails() {
        String customerId = getStringInput("Enter Customer ID: ");
        Customer customer = rentalService.findCustomer(customerId);
        
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }
        
        System.out.println(customer.getCustomerInfo());
        System.out.println(customer.getRentalHistoryString());
        System.out.printf("Total Rentals: %d%n", customer.getTotalRentals());
        System.out.printf("Total Spent: $%.2f%n", customer.getTotalSpent());
    }
    
    private static void rentVehicle() {
        System.out.println("\n=== Rent Vehicle ===");
        rentalService.displayAvailableVehicles();
        
        String vehicleId = getStringInput("Enter Vehicle ID to rent: ");
        String customerId = getStringInput("Enter Customer ID: ");
        int days = getIntInput("Enter number of days: ");
        
        RentalRecord record = rentalService.rentVehicle(customerId, vehicleId, days);
        System.out.println("Vehicle rented successfully!");
        System.out.println("Rental Record: " + record.toString());
    }
    
    private static void returnVehicle() {
        System.out.println("\n=== Return Vehicle ===");
        System.out.println("Active Rentals:");
        
        for (RentalRecord record : rentalService.getActiveRentals()) {
            System.out.println(record.toString());
        }
        
        String recordId = getStringInput("Enter Rental Record ID: ");
        double fuelLevel = getDoubleInput("Enter current fuel level (0-100): ");
        
        RentalRecord record = rentalService.returnVehicle(recordId, fuelLevel);
        System.out.println("Vehicle returned successfully!");
        System.out.println("Final Record: " + record.toString());
    }
    
    private static void viewAvailableVehicles() {
        rentalService.displayAvailableVehicles();
    }
    
    private static void viewReports() {
        while (true) {
            System.out.println("\n=== Reports ===");
            System.out.println("1. All Rental Records");
            System.out.println("2. Active Rentals");
            System.out.println("3. Total Revenue");
            System.out.println("4. Vehicle Utilization");
            System.out.println("5. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    rentalService.displayRentalRecords();
                    break;
                case 2:
                    System.out.println("\n=== Active Rentals ===");
                    for (RentalRecord record : rentalService.getActiveRentals()) {
                        System.out.println(record.toString());
                    }
                    break;
                case 3:
                    System.out.printf("Total Revenue: $%.2f%n", rentalService.calculateTotalRevenue());
                    break;
                case 4:
                    System.out.println("\n=== Vehicle Utilization ===");
                    for (Map.Entry<String, Integer> entry : rentalService.getVehicleUtilization().entrySet()) {
                        System.out.printf("Vehicle %s: %d rentals%n", entry.getKey(), entry.getValue());
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private static boolean getBooleanInput(String prompt) {
        while (true) {
            String input = getStringInput(prompt).toLowerCase();
            if (input.equals("y") || input.equals("yes")) return true;
            if (input.equals("n") || input.equals("no")) return false;
            System.out.println("Please enter 'y' or 'n'.");
        }
    }
}