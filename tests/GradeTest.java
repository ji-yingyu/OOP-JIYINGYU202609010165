public class GradeTest {
    public static void main(String[] args) {
        int[] marks = {0, 49, 50, 59, 60, 69, 70, 79, 80, 100};
        String[] grades = {"F", "F", "D", "D", "C", "C", "B", "B", "A", "A"};
        for (int i = 0; i < marks.length; i++) {
            if (!grades[i].equals(StudentGrade.gradeFor(marks[i]))) {
                throw new AssertionError("Unexpected grade at mark " + marks[i]);
            }
        }
        for (int invalid : new int[] {-1, 101}) {
            try {
                StudentGrade.gradeFor(invalid);
                throw new AssertionError("Invalid mark was accepted");
            } catch (IllegalArgumentException expected) { }
        }
        System.out.println("Grade boundary checks passed.");
    }
}
