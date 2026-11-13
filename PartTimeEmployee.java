public class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private double hoursWorked;
    public PartTimeEmployee(String id, String name, double hourlyRate, double hoursWorked) {
        super(id, name);
        setHourlyRate(hourlyRate);
        setHoursWorked(hoursWorked);
    }
    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double value) { hourlyRate = nonNegative(value); }
    public double getHoursWorked() { return hoursWorked; }
    public void setHoursWorked(double value) {
        nonNegative(value);
        if (value > 744) throw new IllegalArgumentException("Monthly hours cannot exceed 744.");
        hoursWorked = value;
    }
    @Override public double calculateSalary() { return hourlyRate * hoursWorked; }
    @Override public String getType() { return "Part-time"; }
}
