/**
 * Income class - represents money coming in.
 * Extends Transaction and inherits its fields and methods.
 */
public class Income extends Transaction {
    
    // Additional variable specific to Income
    private String source;

    /**
     * Constructor for Income.
     * @param description Description of the income.
     * @param amount The amount received.
     * @param date The date of the income.
     * @param source Where the money came from (e.g., "Job", "Gift").
     */
    public Income(String description, double amount, String date, String source) {
        super(description, amount, date);
        this.source = source;
    }

    @Override
    public String getCategory() { return source; }

    @Override
    public String getType() { return "INCOME"; }

    /**
     * Overridden report to visually indicate an addition (positive sign).
     */
    @Override
    public String generateReport() {
        return String.format("[%s] INCOME - %s: +$%.2f (%s)",
                getDate(), getDescription(), getAmount(), source);
    }
}