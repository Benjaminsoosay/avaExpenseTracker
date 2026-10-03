/**
 * Abstract base class representing a generic financial transaction.
 * This satisfies the "Demonstrate inheritance using the extends and abstract keywords" requirement.
 */
public abstract class Transaction implements Reportable {
    
    // Encapsulated instance variables
    private String description;
    private double amount;
    private String date;

    /**
     * Constructor for Transaction.
     * @param description A short description of the transaction.
     * @param amount The monetary value of the transaction.
     * @param date The date of the transaction (e.g., "2026-10-03").
     */
    public Transaction(String description, double amount, String date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    // Public getters for encapsulated fields
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }

    // Abstract methods that subclasses MUST implement
    public abstract String getCategory();
    public abstract String getType();

    /**
     * Default report implementation (can be overridden by subclasses).
     */
    @Override
    public String generateReport() {
        return String.format("[%s] %s - %s: $%.2f (%s)",
                date, getType(), description, amount, getCategory());
    }

    /**
     * Returns a CSV-formatted line for saving to a file.
     * Format: TYPE|DESCRIPTION|AMOUNT|DATE|CATEGORY
     * Note: We use a pipe '|' delimiter to avoid conflicts with commas in descriptions.
     */
    public String toCSV() {
        return getType() + "|" + description + "|" + amount + "|" + date + "|" + getCategory();
    }
}