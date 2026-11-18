/** Introductory variables and selection; grade thresholds are example rules. */
public class StudentGrade {
    public static String gradeFor(int mark) {
        if (mark < 0 || mark > 100) {
            throw new IllegalArgumentException("Mark must be between 0 and 100.");
        }
        if (mark >= 80) return "A";
        if (mark >= 70) return "B";
        if (mark >= 60) return "C";
        if (mark >= 50) return "D";
        return "F";
    }

    public static void main(String[] args) {
        try {
            int mark = args.length == 0 ? 85 : Integer.parseInt(args[0]);
            System.out.println("Student: Ali");
            System.out.println("Mark: " + mark);
            System.out.println("Grade: " + gradeFor(mark));
        } catch (IllegalArgumentException ex) {
            System.err.println("Invalid mark: enter a whole number from 0 to 100.");
            System.exit(1);
        }
    }
}
