package console;

import utils.ValidationUtils;
import java.util.Scanner;

public class ConsoleMenu {

    private Scanner scanner;

    public ConsoleMenu() {
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println("\n===== Adaptive Traffic Simulation =====");
        System.out.println("1. Start Simulation");
        System.out.println("2. Pause Simulation");
        System.out.println("3. View Statistics");
        System.out.println("4. Manual Signal Override");
        System.out.println("5. Exit");
        System.out.print("Enter choice: ");
    }

    public int getValidChoice(int min, int max) {
        while (true) {
            String input = scanner.nextLine();
            if (ValidationUtils.isValidMenuChoice(input, min, max)) {
                return Integer.parseInt(input.trim());
            }
            System.out.print("Invalid choice. Enter a number between " + min + " and " + max + ": ");
        }
    }

    public String getDirectionInput() {
        System.out.print("Enter direction (NORTH/SOUTH/EAST/WEST): ");
        while (true) {
            String input = scanner.nextLine();
            if (ValidationUtils.isValidDirection(input)) {
                return input.trim().toUpperCase();
            }
            System.out.print("Invalid direction. Try again: ");
        }
    }

    public void close() {
        scanner.close();
    }
}