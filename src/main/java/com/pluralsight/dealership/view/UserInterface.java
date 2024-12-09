package com.pluralsight.dealership.view;

import com.pluralsight.dealership.dao.SalesDaoMySqlImpl;
import com.pluralsight.dealership.dao.VehicleDaoMySqlImpl;
import com.pluralsight.dealership.model.SalesContract;
import com.pluralsight.dealership.model.Vehicle;
import com.pluralsight.dealership.utils.ColorCodes;
import com.pluralsight.dealership.utils.InputValidation;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static com.pluralsight.dealership.utils.InputValidation.getPositiveIntegerInput;

public class UserInterface {
    private final VehicleDaoMySqlImpl vehicleDaoMySql;
    private final SalesDaoMySqlImpl salesDaoMySql;

    public UserInterface(DataSource dataSource) {
        this.vehicleDaoMySql = new VehicleDaoMySqlImpl(dataSource);
        this.salesDaoMySql = new SalesDaoMySqlImpl(dataSource);
    }

    public void displayDealerships() {

    }

    // Displays the user interface and handles user input
    public void display() {
        boolean done = false; // Control variable for the main loop
        do {
            printMenuPrompt(); // Show the menu options
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().toLowerCase().trim(); // Get user input
            switch (input) {
                case "a":
                    processGetByPriceRequest(scanner);
                    break;
                case "b":
                    processGetByMakeModelRequest(scanner);
                    break;
                case "c":
                    processGetByYearRequest(scanner);
                    break;
                case "d":
                    processGetByColorRequest(scanner);
                    break;
                case "e":
                    processGetByMileageRequest(scanner);
                    break;
                case "f":
                    processGetByVehicleTypeRequest(scanner);
                    break;
                case "g":
                    processGetAllVehiclesRequest(scanner);
                    break;
                case "h":
                    processAddVehicleRequest(scanner);
                    break;
                case "i":
                    processRemoveVehicleRequest(scanner);
                    break;
                case "j":
                    processVehicleContract(scanner);
                    break;
                case "k":
                    showSoldVehicles(scanner);
                    break;
                case "l":
                    showSalesContracts(scanner);
                case "x":
                    System.out.println("Exiting application...");
                    done = true; // Set done to true to exit the loop
                    break;
                default:
                    System.out.println("Please enter a valid choice (A, B, C, D, E, F, G, H, I, J, K, or X):");
                    break;
            }

        } while (!done); // Repeat until the user chooses to exit
    }

    private void showSalesContracts(Scanner scanner) {
        printSalesContractsHeader();
        for (SalesContract sc : salesDaoMySql.getAllSalesContracts()) {
            System.out.println(sc);
        }
    }

    private void showSoldVehicles(Scanner scanner) {
        printVehicleListHeader();
        for (Vehicle v : salesDaoMySql.getVehiclesSold()) {
            System.out.println(v);
        }
    }


    private void processVehicleContract(Scanner scanner) {
        System.out.print("Please enter the VIN # of the vehicle you want: ");
        String vin = scanner.nextLine().trim();
        if (!vehicleDaoMySql.vehicleExists(vin)) {
            System.out.println("Vehicle does not exist...");
            return;
        }
        Vehicle vehicle = salesDaoMySql.getVehicleByVin(vin);

        LocalDate date = LocalDate.now();

        System.out.print("Please enter your name: ");
        String name = scanner.nextLine().toUpperCase().trim();

        System.out.print("Please enter your email: ");
        String email = scanner.nextLine().toUpperCase().trim();

        System.out.print("Would you like to lease or buy? Enter LEASE or SALE: ");
        String contractType = scanner.nextLine().toUpperCase().trim();

        double price = vehicle.getPrice();
        double taxes = salesDaoMySql.calculateTaxes(price);
        double fees = salesDaoMySql.calculateFees(price);


        if (!(contractType.equalsIgnoreCase("LEASE") || contractType.equalsIgnoreCase("SALE"))) {
            System.out.println("Please enter 'LEASE' or 'SALE'...");
            return;
        } else {
            if (contractType.equalsIgnoreCase("SALE")) {
                System.out.print("How much would you like your initial payment to be?: ");
                double paymentAmount = scanner.nextDouble();
                scanner.nextLine();
                double balanceDue = salesDaoMySql.calculateBalanceDue(price, taxes, fees, paymentAmount);
                //String vin, Date date, String name, String email, double price, double amount, double taxes, double fees, double balanceDue
                salesDaoMySql.addSalesContract(vin, date, name, email, price, paymentAmount, taxes, fees, balanceDue);
            }
        }
    }

    // Displays the list of vehicles to the user
    private void displayVehicles(List<Vehicle> vehicles) {
        printVehicleListHeader(); // Print the header for the vehicle list
        for (Vehicle v: vehicles) {
            System.out.println(v); // Print each vehicle's details
        }
        System.out.println();
    }

    // Handles filtering vehicles by price range
    public void processGetByPriceRequest(Scanner scanner) {
        System.out.print("Please enter the minimum price: ");
        String input = scanner.nextLine().trim();
        double min = InputValidation.getPositiveDoubleInput(input); // Get minimum price input
        if (min == -1) return; // Exit if input is invalid

        System.out.print("Please enter the maximum price: ");
        input = scanner.nextLine().trim();
        double max = InputValidation.getPositiveDoubleInput(input); // Get maximum price input
        if (max == -1) return; // Exit if input is invalid

        // Retrieve and display vehicles in the specified price range
        List<Vehicle> vehiclesByPrice = vehicleDaoMySql.getVehiclesInPriceRange(min, max);
        if (vehiclesByPrice.isEmpty()) {
            System.out.println("There are no vehicles within that price range...");
        } else {
            displayVehicles(vehiclesByPrice);
        }
    }

    // Handles filtering vehicles by make and model
    public void processGetByMakeModelRequest(Scanner scanner) {
        System.out.print("Please enter the make (brand) of the vehicle: ");
        String make = scanner.nextLine().trim().toLowerCase(); // Get make input

        System.out.print("Please enter the model: ");
        String model = scanner.nextLine().trim().toLowerCase(); // Get model input

        // Retrieve and display vehicles by make and model
        List<Vehicle> vehiclesByMakeModel = vehicleDaoMySql.getVehiclesByMakeAndModel(make, model);
        if (vehiclesByMakeModel.isEmpty()) {
            System.out.println("There are no vehicles with that make and model...");
        } else {
            displayVehicles(vehiclesByMakeModel);
        }
    }

    // Handles filtering vehicles by year range
    public void processGetByYearRequest(Scanner scanner) {
        System.out.print("Please enter the minimum year: ");
        String input = scanner.nextLine().trim();
        int min = InputValidation.getPositiveIntegerInput(input); // Get minimum year input
        if (min == -1) return; // Exit if input is invalid

        System.out.print("Please enter the maximum year: "); // Fixed typo in prompt
        input = scanner.nextLine().trim();
        int max = InputValidation.getPositiveIntegerInput(input); // Get maximum year input
        if (max == -1) return; // Exit if input is invalid

        // Retrieve and display vehicles in the specified year range
        List<Vehicle> vehiclesByYear = vehicleDaoMySql.getVehiclesByYearRange(min, max);
        if (vehiclesByYear.isEmpty()) {
            System.out.println("There are no vehicles within those years...");
        } else {
            displayVehicles(vehiclesByYear);
        }
    }

    // Handles filtering vehicles by color
    public void processGetByColorRequest(Scanner scanner) {
        System.out.print("Please enter the color of the vehicle: ");
        String color = scanner.nextLine().trim().toLowerCase(); // Get color input

        // Retrieve and display vehicles by color
        List<Vehicle> vehiclesByColor = vehicleDaoMySql.getVehiclesByColor(color);
        if (vehiclesByColor.isEmpty()) {
            System.out.println("There are no vehicles with that color...");
        } else {
            displayVehicles(vehiclesByColor);
        }
    }

    // Handles filtering vehicles by mileage range
    public void processGetByMileageRequest(Scanner scanner) {
        System.out.print("Please enter the minimum mileage: ");
        String input = scanner.nextLine().trim();
        int min = InputValidation.getPositiveIntegerInput(input); // Get minimum mileage input
        if (min == -1) return; // Exit if input is invalid

        System.out.print("Please enter the maximum mileage: ");
        input = scanner.nextLine().trim();
        int max = InputValidation.getPositiveIntegerInput(input); // Get maximum mileage input
        if (max == -1) return; // Exit if input is invalid

        // Retrieve and display vehicles in the specified mileage range
        List<Vehicle> vehiclesByMileage = vehicleDaoMySql.getVehiclesByMileageRange(min, max);
        if (vehiclesByMileage.isEmpty()) {
            System.out.println("There are no vehicles within that mileage range...");
        } else {
            displayVehicles(vehiclesByMileage);
        }
    }

    // Handles filtering vehicles by type
    public void processGetByVehicleTypeRequest(Scanner scanner) {
        System.out.print("Please enter the vehicle type: ");
        String type = scanner.nextLine().trim().toLowerCase(); // Get vehicle type input

        // Retrieve and display vehicles by type
        List<Vehicle> vehiclesByType = vehicleDaoMySql.getVehiclesByType(type);
        if (vehiclesByType.isEmpty()) {
            System.out.println("There are no vehicles with that type...");
        } else {
            displayVehicles(vehiclesByType);
        }
    }

    // Retrieves and displays all vehicles in the inventory
    public void processGetAllVehiclesRequest(Scanner scanner) {
        displayVehicles(vehicleDaoMySql.getAllVehicles());
    }

    // Handles adding a new vehicle to the dealership
    public void processAddVehicleRequest(Scanner scanner) {
        System.out.println("Please enter a vehicle VIN: ");
        String vin = scanner.nextLine(); // Get VIN input

        System.out.println("Please enter a vehicle year: ");
        String input = scanner.nextLine().toLowerCase().trim();
        int year = InputValidation.getPositiveIntegerInput(input); // Get year input

        System.out.println("Please enter a vehicle brand: ");
        String make = scanner.nextLine(); // Get make input

        System.out.println("Please enter a vehicle model: ");
        String model = scanner.nextLine(); // Get model input

        System.out.println("Please enter a vehicle type: ");
        String type = scanner.nextLine(); // Get type input

        System.out.println("Please enter a vehicle color: ");
        String color = scanner.nextLine(); // Get color input

        System.out.println("Please enter the vehicle's mileage: ");
        input = scanner.nextLine().toLowerCase().trim();
        int mileage = InputValidation.getPositiveIntegerInput(input); // Get mileage input

        System.out.println("Please enter the vehicle's price: ");
        input = scanner.nextLine().toLowerCase().trim();
        double price = InputValidation.getPositiveDoubleInput(input); // Get price input

        // Add new vehicle to vehicles table
        vehicleDaoMySql.addVehicle(vin, year, make, model, type, color, mileage, price, false);

        System.out.println("Successfully added vehicle with vin #: " + vin);
    }

    // Handles removing a vehicle from the dealership
    public void processRemoveVehicleRequest(Scanner scanner) {
        System.out.println("Please enter the VIN number of the vehicle to remove from the dealership: ");
        String vin = scanner.nextLine().trim(); // Get VIN input

        vehicleDaoMySql.removeVehicleByVin(vin);

        System.out.println("Successfully removed the vehicle with VIN #:" + vin);
    }

    // Helper methods
    private void printMenuPrompt() {
        // Displays the menu options to the user
        System.out.print(ColorCodes.CYAN + """
                    ~~~~~~~~~~~~~~~~~~~~
                    |       Menu       |
                    ~~~~~~~~~~~~~~~~~~~~
                    """ +  ColorCodes.RESET + """
                    A) Filter vehicles by price
                    B) Filter vehicles by make and model
                    C) Filter vehicles by year
                    D) Filter vehicles by color
                    E) Filter vehicles by mileage
                    F) Filter vehicles by type
                    G) Show all vehicles
                    H) Add new vehicle to dealership
                    I) Remove vehicle from dealership
                    J) Buy Vehicle
                    K) Show sold vehicles
                    L) Show sales contracts
                    X) Exit application
                    
                    Please enter an option:
                    """);
    }

    private void printVehicleListHeader() {
        // Prints the header for the vehicle list
        System.out.println(ColorCodes.GREEN + "\nVehicles\n~~~~~~~~" + ColorCodes.RESET);
        System.out.printf(ColorCodes.BLUE + "%-21s %-10s %-10s %-10s %-10s %-10s %-15s %5s %10s\n",
                "VIN", "Make", "Model", "Year", "Type", "Color", "Mileage", "Price", "Sold");
        System.out.println("-".repeat(108) + ColorCodes.RESET);
    }

    private void printSalesContractsHeader() {
        // Prints the header for the vehicle list
        System.out.println(ColorCodes.GREEN + "\nSales Contracts\n~~~~~~~~" + ColorCodes.RESET);
        System.out.printf(ColorCodes.BLUE + "%-21s %-10s %-10s %-10s %-10s %-10s %-15s %5s %10s\n",
                "VIN", "Contract Date", "Customer Name", "Customer Email", "Total Price", "Down Payment", "Taxes", "Fees", "Balance Due");
        System.out.println("-".repeat(108) + ColorCodes.RESET);
    }
}
