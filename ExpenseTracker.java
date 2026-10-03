import java.io.*;
import java.util.*;

/**
 * ExpenseTracker - The main driver program that manages a list of transactions.
 * Demonstrates: variables, expressions, conditionals, loops, functions,
 * classes, ArrayList, HashMap, file I/O, inheritance, and interfaces.
 */
public class ExpenseTracker {

    // Java Collection Framework - ArrayList to store polymorphic Transaction objects
    private static ArrayList<Transaction> transactions = new ArrayList<>();

    // Java Collection Framework - HashMap to aggregate totals by category
    private static HashMap<String, Double> categoryTotals = new HashMap<>();

    // File path for saving/loading data
    private static final String DATA_FILE = "transactions.csv";

    /**
     * Main method - entry point of the program.
     * Runs a menu-driven loop until the user chooses to exit.
     */
    public static void main(String[] args) {
        loadFromFile(); // Load any previously saved transactions

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=========================================");
        System.out.println("   Welcome to the Java Expense Tracker   ");
        System.out.println("=========================================");

        // Loop requirement
        while (running) {
            printMenu();
            int choice = getUserChoice(scanner);

            // Conditional requirement (Switch statement)
            switch (choice) {
                case 1:
                    addExpense(scanner);
                    break;
                case 2:
                    addIncome(scanner);
                    break;
                case 3:
                    viewAllTransactions();
                    break;
                case 4:
                    showCategoryTotals();
                    break;
                case 5:
                    showBalance();
                    break;
                case 6:
                    saveToFile();
                    break;
                case 7:
                    running = false;
                    saveToFile();
                    System.out.println("\nGoodbye! Your data has been saved to " + DATA_FILE);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
        scanner.close();
    }

    /**
     * Prints the main menu options to the console.
     */
    private static void printMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Add an Expense");
        System.out.println("2. Add Income");
        System.out.println("3. View All Transactions");
        System.out.println("4. Show Category Totals");
        System.out.println("5. Show Current Balance");
        System.out.println("6. Save Data to File");
        System.out.println("7. Exit & Save");
        System.out.print("Choose an option: ");
    }

    /**
     * Reads and validates the user's menu choice.
     * @param scanner The Scanner object for reading input.
     * @return A valid integer choice between 1 and 7, or -1 if invalid.
     */
    private static int getUserChoice(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Triggers the default case in the switch
        }
    }

    /**
     * Prompts the user for expense details and adds an Expense to the list.
     */
    private static void addExpense(Scanner scanner) {
        System.out.print("Enter expense description: ");
        String desc = scanner.nextLine().trim();

        System.out.print("Enter amount: $");
        double amount = parseAmount(scanner.nextLine());

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();

        System.out.print("Enter category (e.g., Food, Transport): ");
        String category = scanner.nextLine().trim();

        // Create a new Expense object and add it to the ArrayList
        transactions.add(new Expense(desc, amount, date, category));
        System.out.println("--> Expense added successfully!");
    }

    /**
     * Prompts the user for income details and adds an Income to the list.
     */
    private static void addIncome(Scanner scanner) {
        System.out.print("Enter income description: ");
        String desc = scanner.nextLine().trim();

        System.out.print("Enter amount: $");
        double amount = parseAmount(scanner.nextLine());

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine().trim();

        System.out.print("Enter source (e.g., Job, Gift): ");
        String source = scanner.nextLine().trim();

        transactions.add(new Income(desc, amount, date, source));
        System.out.println("--> Income added successfully!");
    }

    /**
     * Helper function to safely parse a dollar amount from user input.
     * @param input The raw string input.
     * @return The parsed double, or 0.0 if invalid.
     */
    private static double parseAmount(String input) {
        try {
            return Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            System.out.println("--> Invalid amount entered. Defaulting to $0.00");
            return 0.0;
        }
    }

    /**
     * Displays all transactions currently stored in the list.
     * Uses an enhanced for loop (for-each).
     */
    private static void viewAllTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("\nNo transactions recorded yet.");
            return;
        }
        System.out.println("\n--- ALL TRANSACTIONS ---");
        // For-each loop requirement
        for (Transaction t : transactions) {
            System.out.println(t.generateReport());
        }
    }

    /**
     * Calculates and displays totals grouped by category.
     * Uses a HashMap to aggregate totals.
     */
    private static void showCategoryTotals() {
        if (transactions.isEmpty()) {
            System.out.println("\nNo transactions to summarize.");
            return;
        }

        categoryTotals.clear(); // Reset before recalculating

        // Classic for loop with index requirement
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            String key = t.getCategory();
            double existing = categoryTotals.getOrDefault(key, 0.0);
            categoryTotals.put(key, existing + t.getAmount());
        }

        System.out.println("\n--- CATEGORY TOTALS ---");
        // Iterate over the HashMap's entrySet
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            System.out.printf("%-15s : $%.2f%n", entry.getKey(), entry.getValue());
        }
    }

    /**
     * Calculates the net balance (total income minus total expenses).
     */
    private static void showBalance() {
        double totalIncome = 0.0;
        double totalExpense = 0.0;

        for (Transaction t : transactions) {
            // Conditional to determine type using instanceof
            if (t instanceof Income) {
                totalIncome += t.getAmount();
            } else if (t instanceof Expense) {
                totalExpense += t.getAmount();
            }
        }

        double balance = totalIncome - totalExpense; // Expression
        System.out.printf("%n--- FINANCIAL SUMMARY ---%n");
        System.out.printf("Total Income : $%.2f%n", totalIncome);
        System.out.printf("Total Expenses: $%.2f%n", totalExpense);
        System.out.printf("Net Balance  : $%.2f%n", balance);
        
        if (balance < 0) {
            System.out.println("Warning: You are spending more than you earn!");
        }
    }

    /**
     * Saves all transactions to a CSV file using file I/O.
     * Uses try-with-resources to ensure the file is closed properly.
     */
    private static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Transaction t : transactions) {
                writer.println(t.toCSV());
            }
            System.out.println("\n--> Saved " + transactions.size() + " transactions to " + DATA_FILE);
        } catch (IOException e) {
            System.out.println("\n--> Error saving file: " + e.getMessage());
        }
    }

    /**
     * Loads transactions from the CSV file into the ArrayList.
     * Parses each line and recreates the correct subclass (Income or Expense).
     */
    private static void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return; // Nothing to load on first run
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split CSV line by the pipe delimiter
                String[] parts = line.split("\\|");
                if (parts.length < 5) continue; // Skip malformed lines

                String type = parts[0];
                String desc = parts[1];
                double amount = Double.parseDouble(parts[2]);
                String date = parts[3];
                String category = parts[4];

                // Conditional to recreate the correct subclass
                if (type.equals("INCOME")) {
                    transactions.add(new Income(desc, amount, date, category));
                } else if (type.equals("EXPENSE")) {
                    transactions.add(new Expense(desc, amount, date, category));
                }
            }
            System.out.println("Loaded " + transactions.size() + " transactions from " + DATA_FILE);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}