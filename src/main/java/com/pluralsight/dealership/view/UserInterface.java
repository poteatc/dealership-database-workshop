package com.pluralsight.dealership.view;

import com.pluralsight.dealership.model.Dealership;
import com.pluralsight.dealership.model.Vehicle;
import com.pluralsight.dealership.utils.ColorCodes;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private Dealership dealership; // Reference to the dealership object

    public UserInterface() {
        // Constructor
    }

    // Initializes the dealership from file
    private void init() {
        this.dealership = new Dealership(1);
    }

    // Displays the user interface and handles user input
    public void display() {
        init(); // Initialize the dealership
        boolean done = false; // Control variable for the main loop
        do {
            printMenuPrompt(); // Show the menu options
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().toLowerCase().trim(); // Get user input
            switch (input) {
                case "a":
                    processGetByPriceRequest();
                    break;
                case "b":
                    processGetByMakeModelRequest();
                    break;
                case "c":
                    processGetByYearRequest();
                    break;
                case "d":
                    processGetByColorRequest();
                    break;
                case "e":
                    processGetByMileageRequest();
                    break;
                case "f":
                    processGetByVehicleTypeRequest();
                    break;
                case "g":
                    processGetAllVehiclesRequest();
                    break;
                case "h":
                    processAddVehicleRequest();
                    break;
                case "i":
                    processRemoveVehicleRequest();
                    break;
                case "j":
                    processVehicleContract();
                    break;
                case "x":
                    System.out.println("Exiting application...");
                    done = true; // Set done to true to exit the loop
                    break;
                default:
                    System.out.println("Please enter a valid choice (A, B, C, D, E, F, G, H, I, J, or X):");
                    break;
            }

        } while (!done); // Repeat until the user chooses to exit
    }


    private void processVehicleContract() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the VIN # of the vehicle you want: ");
        int vin = getPositiveIntegerInput(scanner.nextLine());

        String date = LocalDate.now().toString();

        System.out.print("Please enter your name: ");
        String name = scanner.nextLine().toUpperCase().trim();

        System.out.print("Please enter your email: ");
        String email = scanner.nextLine().toUpperCase().trim();

        System.out.print("Would you like to lease or buy? Enter LEASE or SALE: ");
        String contractType = scanner.nextLine().toUpperCase().trim();

        if (!(contractType.equalsIgnoreCase("LEASE") || contractType.equalsIgnoreCase("SALE"))) {
            System.out.println("Please enter 'LEASE' or 'SALE'...");
            return;
        }
//        } else {
////            Vehicle v = dealership.getAllVehicles().stream().filter(vehicle -> vehicle.getVin() == vin).toList().get(0);
////            if (contractType.equals("SALE")) {
////                double processingFee = v.getPrice() < 10000 ? 295 : 495;
////                System.out.printf("Would you like to finance your new vehicle? Enter 'Y' for yes or any other key for no: ");
////                String financingInput = scanner.nextLine().trim().toLowerCase();
////                boolean isFinancing = financingInput.equalsIgnoreCase("y") ? true : false;
////                Contract contract = new SalesContract(date, name, email, v, processingFee, isFinancing);
////                new ContractDataManager().saveContract(contract);
////                // TODO remove vehicle from dealership
////            } else if (contractType.equals("LEASE")) {
////                Contract contract = new LeaseContract(date, name, email, v);
////                new ContractDataManager().saveContract(contract);
////            }
//        }

    }

    // Displays the list of vehicles to the user
    private void displayVehicles(List<Vehicle> vehicles) {
        printVehicleListHeader(); // Print the header for the vehicle list
        for (Vehicle v: vehicles) {
            System.out.println(v); // Print each vehicle's details
        }
    }

    // Handles filtering vehicles by price range
    public void processGetByPriceRequest() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the minimum price: ");
        String input = scanner.nextLine().trim();
        double min = getPositiveDoubleInput(input); // Get minimum price input
        if (min == -1) return; // Exit if input is invalid

        System.out.print("Please enter the maximum price: ");
        input = scanner.nextLine().trim();
        double max = getPositiveDoubleInput(input); // Get maximum price input
        if (max == -1) return; // Exit if input is invalid

        // Retrieve and display vehicles in the specified price range
        List<Vehicle> vehiclesByPrice = dealership.getVehiclesByPrice(min, max);
        if (vehiclesByPrice.isEmpty()) {
            System.out.println("There are no vehicles within that price range...");
        } else {
            displayVehicles(vehiclesByPrice);
        }
    }

    // Handles filtering vehicles by make and model
    public void processGetByMakeModelRequest() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the make (brand) of the vehicle: ");
        String make = scanner.nextLine().trim().toLowerCase(); // Get make input

        System.out.print("Please enter the model: ");
        String model = scanner.nextLine().trim().toLowerCase(); // Get model input

        // Retrieve and display vehicles by make and model
        List<Vehicle> vehiclesByMakeModel = dealership.getVehiclesByMakeModel(make, model);
        if (vehiclesByMakeModel.isEmpty()) {
            System.out.println("There are no vehicles with that make and model...");
        } else {
            displayVehicles(vehiclesByMakeModel);
        }
    }

    // Handles filtering vehicles by year range
    public void processGetByYearRequest() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the minimum year: ");
        String input = scanner.nextLine().trim();
        int min = getPositiveIntegerInput(input); // Get minimum year input
        if (min == -1) return; // Exit if input is invalid

        System.out.print("Please enter the maximum year: "); // Fixed typo in prompt
        input = scanner.nextLine().trim();
        int max = getPositiveIntegerInput(input); // Get maximum year input
        if (max == -1) return; // Exit if input is invalid

        // Retrieve and display vehicles in the specified year range
        List<Vehicle> vehiclesByYear = dealership.getVehiclesByYear(min, max);
        if (vehiclesByYear.isEmpty()) {
            System.out.println("There are no vehicles within those years...");
        } else {
            displayVehicles(vehiclesByYear);
        }
    }

    // Handles filtering vehicles by color
    public void processGetByColorRequest() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the color of the vehicle: ");
        String color = scanner.nextLine().trim().toLowerCase(); // Get color input

        // Retrieve and display vehicles by color
        List<Vehicle> vehiclesByColor = dealership.getVehiclesByColor(color);
        if (vehiclesByColor.isEmpty()) {
            System.out.println("There are no vehicles with that color...");
        } else {
            displayVehicles(vehiclesByColor);
        }
    }

    // Handles filtering vehicles by mileage range
    public void processGetByMileageRequest() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the minimum mileage: ");
        String input = scanner.nextLine().trim();
        int min = getPositiveIntegerInput(input); // Get minimum mileage input
        if (min == -1) return; // Exit if input is invalid

        System.out.print("Please enter the maximum mileage: ");
        input = scanner.nextLine().trim();
        int max = getPositiveIntegerInput(input); // Get maximum mileage input
        if (max == -1) return; // Exit if input is invalid

        // Retrieve and display vehicles in the specified mileage range
        List<Vehicle> vehiclesByMileage = dealership.getVehiclesByMileage(min, max);
        if (vehiclesByMileage.isEmpty()) {
            System.out.println("There are no vehicles within that mileage range...");
        } else {
            displayVehicles(vehiclesByMileage);
        }
    }

    // Handles filtering vehicles by type
    public void processGetByVehicleTypeRequest() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter the vehicle type: ");
        String type = scanner.nextLine().trim().toLowerCase(); // Get vehicle type input

        // Retrieve and display vehicles by type
        List<Vehicle> vehiclesByType = dealership.getVehiclesByType(type);
        if (vehiclesByType.isEmpty()) {
            System.out.println("There are no vehicles with that type...");
        } else {
            displayVehicles(vehiclesByType);
        }
    }

    // Retrieves and displays all vehicles in the inventory
    public void processGetAllVehiclesRequest() {
        displayVehicles(dealership.getAllVehicles());
    }

    // Handles adding a new vehicle to the dealership
    public void processAddVehicleRequest() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter a vehicle VIN: ");
        String input = scanner.nextLine().toLowerCase().trim();
        String vin = scanner.nextLine(); // Get VIN input

        System.out.println("Please enter a vehicle year: ");
        input = scanner.nextLine().toLowerCase().trim();
        int year = getPositiveIntegerInput(input); // Get year input

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
        int mileage = getPositiveIntegerInput(input); // Get mileage input

        System.out.println("Please enter the vehicle's price: ");
        input = scanner.nextLine().toLowerCase().trim();
        double price = getPositiveDoubleInput(input); // Get price input

        // Create and add the new vehicle to the dealership
        Vehicle vehicle = new Vehicle(vin, year, make, model, type, color, mileage, price, false);
        dealership.addVehicle(vehicle);

        System.out.println("Successfully added vehicle with vin #: " + vin);
        // Save the updated dealership data
       // new DealershipFileManager().saveDealership(dealership);
    }

    // Handles removing a vehicle from the dealership
    public void processRemoveVehicleRequest() {
        System.out.println("Please enter the VIN number of the vehicle to remove from the dealership: ");
        Scanner scanner = new Scanner(System.in);
        String vin = scanner.nextLine(); // Get VIN input

        // Retrieve and remove the vehicle with the specified VIN from the dealership
        Vehicle filtered = dealership.getAllVehicles().stream()
                .filter(vehicle -> vehicle.getVin().equalsIgnoreCase(vin)).toList().get(0);
        dealership.removeVehicle(filtered);

        System.out.println("Successfully removed the vehicle with VIN #" + vin);
        //new DealershipFileManager().saveDealership(dealership); // Save changes to dealership data
    }

    // Helper methods
    private void printMenuPrompt() {
        // Displays the menu options to the user
        System.out.println("""
                    ~~~~~~~~~~~~~~~~~~~~
                    |       Menu       |
                    ~~~~~~~~~~~~~~~~~~~~
                    Please enter an option:
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
                    X) Exit application
                    """);
    }

    private void printVehicleListHeader() {
        // Prints the header for the vehicle list
        System.out.println(ColorCodes.GREEN + "\nVehicles\n~~~~~~~~" + ColorCodes.RESET);
        System.out.printf(ColorCodes.BLUE + "%-10s %-10s %-10s %-10s %-10s %-10s %-15s %5s\n",
                "VIN", "Make", "Model", "Year", "Type", "Color", "Mileage", "Price");
        System.out.println("---------------------------------------------------------------------------------------" + ColorCodes.RESET);
    }

    // Validates and returns a positive double input
    private double getPositiveDoubleInput(String input) {
        double d = -1; // Default value indicating invalid input
        try {
            d = Double.parseDouble(input); // Try to parse input as double
        } catch (NumberFormatException e) {
            System.out.println("Please enter a floating point number (Ex. 100, 54321.09, 123.50)");
        }
        if (d < 0) { // Check if the input is negative
            System.out.println("Please enter a positive number...");
            d = -1; // Reset to default if invalid
        }
        return d;
    }

    // Validates and returns a positive integer input
    private int getPositiveIntegerInput(String input) {
        int i = -1; // Default value indicating invalid input
        try {
            i = Integer.parseInt(input); // Try to parse input as integer
        } catch (NumberFormatException e) {
            System.out.println("Please enter an integer number (Ex. 10, 987, 1234)");
        }
        if (i < 0) { // Check if the input is negative
            System.out.println("Please enter a positive number...");
            i = -1; // Reset to default if invalid
        }
        return i;
    }
}
