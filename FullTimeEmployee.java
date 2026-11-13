public class FullTimeEmployee extends Employee {
    private double basicSalary;
    private double allowance;
    public FullTimeEmployee(String id, String name, double basicSalary, double allowance) {
        super(id, name);
        setBasicSalary(basicSalary);
        setAllowance(allowance);
    }
    public double getBasicSalary() { return basicSalary; }
    public void setBasicSalary(double value) { basicSalary = nonNegative(value); }
    public double getAllowance() { return allowance; }
    public void setAllowance(double value) { allowance = nonNegative(value); }
    @Override public double calculateSalary() { return basicSalary + allowance; }
    @Override public String getType() { return "Full-time"; }
}
