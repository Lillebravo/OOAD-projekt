package Utillities;

import java.util.Scanner;

public class Validation {

    // Validates and retrieves an integer input.
    public static int getValidInt(Scanner scanner, String prompt, int minValue, String errorMessage) {
        System.out.println("0: <- Go back");
        System.out.println(prompt);

        try {
            int value = Integer.parseInt(scanner.nextLine());
            if (value == 0) {
                return -1; // Special return value to indicate "go back"
            }

            if (value < minValue) {
                System.out.println(errorMessage);
                return -1;
            }

            return value;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return -1;
        }
    }

    // Validates and retrieves a double input
    public static double getValidDouble(Scanner scanner, String prompt, double minValue, String errorMessage) {
        System.out.println("0: <- Go back");
        System.out.println(prompt);

        try {
            double value = Double.parseDouble(scanner.nextLine());
            if (value == 0) {
                return -1.0; // Special return value to indicate "go back"
            }

            if (value < minValue) {
                System.out.println(errorMessage);
                return -1.0;
            }

            return value;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return -1.0;
        }
    }

    // Validates and retrieves a string input
    public static String getValidString(Scanner scanner, String prompt) {
        System.out.println("0: <- Go back");
        System.out.println(prompt);

        String input = scanner.nextLine();
        if (input.equals("0")) {
            return null;
        }

        if (input.trim().isEmpty()) {
            System.out.println("Input cannot be empty.");
            return null;
        }

        return input;
    }
}