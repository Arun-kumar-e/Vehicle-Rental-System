public class TestSystem {
    public static void main(String[] args) {
        RentalService rentalService = new RentalService();
        
        System.out.println("=== Vehicle Rental System Test ===");
        
        // Add sample vehicles
        rentalService.addVehicle(new Car("CAR001", "Toyota", "Camry", 2023, 45.0, "petrol", 4, "automatic"));
        rentalService.addVehicle(new Motorcycle("MOTO001", "Yamaha", "MT-07", 2023, 35.0, "petrol", 689, "sport"));
        
        // Add sample customers
        rentalService.addCustomer(new Customer("CUST001", "John Doe", "john@email.com", "555-0101", "DL123456"));
        
        System.out.println("\n=== Available Vehicles ===");
        rentalService.displayAvailableVehicles();
        
        System.out.println("\n=== Customers ===");
        rentalService.displayCustomers();
        
        // Test renting a vehicle
        try {
            RentalRecord record = rentalService.rentVehicle("CUST001", "CAR001", 3);
            System.out.println("\n=== Vehicle Rented Successfully ===");
            System.out.println(record.toString());
            
            // Test returning the vehicle
            RentalRecord returnedRecord = rentalService.returnVehicle(record.getRecordId(), 85.0);
            System.out.println("\n=== Vehicle Returned Successfully ===");
            System.out.println(returnedRecord.toString());
            
            // Test reports
            System.out.println("\n=== Total Revenue ===");
            System.out.printf("$%.2f%n", rentalService.calculateTotalRevenue());
            
            System.out.println("\n=== Vehicle Utilization ===");
            for (var entry : rentalService.getVehicleUtilization().entrySet()) {
                System.out.printf("Vehicle %s: %d rentals%n", entry.getKey(), entry.getValue());
            }
            
            System.out.println("\n=== Test Completed Successfully! ===");
            
        } catch (Exception e) {
            System.out.println("Error during test: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
