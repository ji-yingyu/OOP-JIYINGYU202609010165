public abstract class Employee {
    private String id;
    private String name;

    protected Employee(String id, String name) {
        setId(id);
        setName(name);
    }
    public String getId() { return id; }
    public final void setId(String id) { this.id = requireText(id, "ID"); }
    public String getName() { return name; }
    public final void setName(String name) { this.name = requireText(name, "Name"); }
    private static String requireText(String value, String label) {
        if (value == null || value.trim().isEmpty())
            throw new IllegalArgumentException(label + " cannot be blank.");
        return value.trim();
    }
    protected static double nonNegative(double value) {
        if (!Double.isFinite(value) || value < 0 || value > 1000000)
            throw new IllegalArgumentException("Value must be finite and between 0 and 1000000.");
        return value;
    }
    public abstract double calculateSalary();
    public abstract String getType();
}
