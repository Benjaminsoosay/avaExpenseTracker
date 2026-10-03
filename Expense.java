/**
 * Expense class - represents money going out.
 * Extends Transaction and inherits its fields and methods.
 */
public class Expense extends Transaction {
    
    // Additional variable specific to Expense
    private String category;

    /**
     * Constructor for Expense.
     * @param description Description of the expense.
     * @param amount The amount spent.
     * @param date The date of the expense.
     * @param category The spending category (e.g., "Food", "Transport").
     */
    public Expense(String description, double amount, String date, String category) {
        super(description, amount, date); // Call parent constructor
        this.category = category;
    }

    @Override
    public String getCategory() { return category; }

    @Override
    public String getType() { return "EXPENSE"; }

    /**
     * Overridden report to visually indicate a deduction (negative sign).
     */
    @Override
    public String generateReport() {
        return String.format("[%s] EXPENSE - %s: -$%.2f (%s)",
                getDate(), getDescription(), getAmount(), category);
    }
}