import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PayrollSystem {
    private final List<Employee> employees = new ArrayList<>();
    private final Scanner scanner;
    public PayrollSystem(Scanner scanner) { this.scanner = scanner; }
    public void addEmployee(Employee employee) {
        if (employee == null) throw new IllegalArgumentException("Employee is required.");
        if (findEmployee(employee.getId()) != null)
            throw new IllegalArgumentException("Employee ID already exists.");
        employees.add(employee);
    }
    public Employee findEmployee(String id) {
        for (Employee employee : employees)
            if (employee.getId().equalsIgnoreCase(id.trim())) return employee;
        return null;
    }
    public double calculateTotalPayroll() {
        double total = 0;
        for (Employee employee : employees) total += employee.calculateSalary();
        return total;
    }
    public void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees registered.");
            return;
        }
        System.out.printf("%-10s %-22s %-12s %12s%n", "ID", "Name", "Type", "Gross (RM)");
        for (Employee employee : employees)
            System.out.printf(Locale.US, "%-10s %-22s %-12s %12.2f%n",
                employee.getId(), employee.getName(), employee.getType(), employee.calculateSalary());
    }
    private String read(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    private void createEmployee(boolean fullTime) {
        String id = read("Employee ID: ");
        if (findEmployee(id) != null) throw new IllegalArgumentException("Employee ID already exists.");
        String name = read("Name: ");
        if (fullTime) {
            double salary = Double.parseDouble(read("Basic monthly salary (RM): "));
            double allowance = Double.parseDouble(read("Monthly allowance (RM): "));
            addEmployee(new FullTimeEmployee(id, name, salary, allowance));
        } else {
            double rate = Double.parseDouble(read("Hourly rate (RM): "));
            double hours = Double.parseDouble(read("Hours worked this month: "));
            addEmployee(new PartTimeEmployee(id, name, rate, hours));
        }
        System.out.println("Employee added successfully.");
    }
    public void run() {
        while (true) {
            System.out.println("\nEMPLOYEE PAYROLL SYSTEM"
                + "\n1. Add full-time employee\n2. Add part-time employee"
                + "\n3. Display employees\n4. Show total payroll\n5. Find employee\n0. Exit");
            try {
                switch (read("Choice: ")) {
                    case "1":
                        createEmployee(true);
                        break;
                    case "2":
                        createEmployee(false);
                        break;
                    case "3":
                        displayEmployees();
                        break;
                    case "4":
                        System.out.printf(Locale.US, "Total monthly gross payroll: RM %.2f%n", calculateTotalPayroll());
                        break;
                    case "5":
                        Employee employee = findEmployee(read("Search ID: "));
                        if (employee == null) {
                            System.out.println("Employee not found.");
                        } else {
                            System.out.printf(Locale.US, "%s | %s | %s | RM %.2f%n",
                                employee.getId(), employee.getName(), employee.getType(), employee.calculateSalary());
                        }
                        break;
                    case "0":
                        System.out.println("Goodbye.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number. Employee was not added.");
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (NoSuchElementException ex) {
                System.out.println("\nInput ended. Goodbye.");
                return;
            }
        }
    }
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            new PayrollSystem(scanner).run();
        }
    }
}
