package com.pluralsight.dealership.utils;

public class InputValidation {
    // Validates and returns a positive double input
    public static double getPositiveDoubleInput(String input) {
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
    public static int getPositiveIntegerInput(String input) {
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
