# Vehicle Rental System

A comprehensive Vehicle Rental Management System built with Java that demonstrates strong Object-Oriented Programming principles.

## Features

### Core Features
- **Add Vehicle**: Add different types of vehicles (Cars, Motorcycles, Trucks)
- **Rent Vehicle**: Rent vehicles to customers with date calculation
- **Return Vehicle**: Return vehicles with automatic cost calculation
- **Calculate Rental Cost**: Dynamic pricing based on vehicle type and rental duration
- **View Available Vehicles**: Display all available vehicles for rent

### Bonus Features
- **Late Fee**: Automatic calculation of late return fees (50% of daily rate per day)
- **Fuel Charge**: Calculate fuel charges based on consumption
- **Different Vehicle Types**: Full inheritance hierarchy with Car, Motorcycle, and Truck classes
- **Premium Membership**: Customer loyalty program with discounts
- **File Storage**: Persistent data storage using text files
- **Comprehensive Reporting**: Revenue tracking and vehicle utilization reports

## OOP Principles Demonstrated

### Inheritance
- Abstract `Vehicle` base class
- `Car`, `Motorcycle`, and `Truck` subclasses
- Each subclass has specific properties and behaviors

### Encapsulation
- Private fields with public getters/setters
- Controlled access to internal state
- Proper data validation

### Polymorphism
- `calculateRentalCost()` method overridden in each vehicle type
- `getFuelPricePerLiter()` varies by vehicle type
- Dynamic method dispatch for vehicle operations

### Abstraction
- Abstract `Vehicle` class defines contract
- Hidden implementation details
- Clean interfaces for complex operations

## Project Structure

```
src/
├── Main.java              # Main application with user interface
├── Vehicle.java           # Abstract base class for all vehicles
├── Car.java              # Car subclass
├── Motorcycle.java       # Motorcycle subclass
├── Truck.java            # Truck subclass
├── Customer.java         # Customer management
├── RentalRecord.java     # Rental transaction records
├── RentalService.java    # Core business logic
├── FileStorageService.java # Data persistence
└── TestSystem.java       # Automated testing
```

## How to Run

### Compile the Project
```bash
javac -cp src src/*.java
```

### Run the Interactive Application
```bash
java -cp src Main
```

### Run the Automated Test
```bash
java -cp src TestSystem
```

## System Requirements

- Java 8 or higher
- Text editor or IDE (IntelliJ IDEA recommended)

## Key Classes and Their Responsibilities

### Vehicle (Abstract Class)
- Defines common properties: vehicleId, make, model, year, dailyRate, available, fuelType, fuelLevel
- Abstract methods: `calculateRentalCost()`, `getFuelPricePerLiter()`
- Common methods: `calculateFuelCharge()`, `calculateLateFee()`, `rent()`, `returnVehicle()`

### Car
- Additional properties: doors, transmission
- Pricing: 5% discount for 3+ days, 10% discount for 7+ days
- Fuel prices: Petrol $1.85/L, Diesel $1.95/L, Electric $0.15/kWh

### Motorcycle
- Additional properties: engineCC, type (sport/cruiser)
- Pricing: Sport bikes +20%, Cruiser +10%, 15% discount for 7+ days
- Fuel price: $1.90/L

### Truck
- Additional properties: cargoCapacity, hasRefrigeration
- Pricing: Refrigerated +30%, >5 tons +20%, 10% discount for 5+ days
- Fuel price: $2.10/L

### Customer
- Properties: customerId, name, email, phone, driverLicense, rentalHistory, isPremiumMember
- Features: Premium membership (5+ rentals or $1000+ spent), 10% discount for premium members

### RentalService
- Core business logic for all rental operations
- Vehicle and customer management
- Rental transaction processing
- Report generation

### FileStorageService
- Persistent data storage using text files
- Automatic save/load functionality
- Human-readable data format

## Usage Examples

### Adding a Vehicle
```java
Car car = new Car("CAR001", "Toyota", "Camry", 2023, 45.0, "petrol", 4, "automatic");
rentalService.addVehicle(car);
```

### Renting a Vehicle
```java
RentalRecord record = rentalService.rentVehicle("CUST001", "CAR001", 3);
```

### Returning a Vehicle
```java
RentalRecord returned = rentalService.returnVehicle(record.getRecordId(), 85.0);
```

## Data Files

The system creates and maintains three data files:
- `vehicles.dat` - Vehicle inventory
- `customers.dat` - Customer information
- `records.dat` - Rental history

## Testing

The `TestSystem.java` file provides automated testing of core functionality:
- Vehicle and customer creation
- Rental and return process
- Cost calculation verification
- Report generation testing

## Future Enhancements

- GUI interface using JavaFX or Swing
- Database integration (MySQL/PostgreSQL)
- Online booking system
- Vehicle maintenance scheduling
- Insurance integration
- Multi-location support
