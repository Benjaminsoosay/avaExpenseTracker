/**
 * Reportable interface - defines a contract for any class that can generate a text report.
 * This satisfies the "Create an interface and use it with the implements keyword" requirement.
 */
public interface Reportable {
    
    /**
     * Generates a formatted report string for the object.
     * @return A String representing the report.
     */
    String generateReport();
}