import java.lang.reflect.Modifier;

public class EncapsulationTest {
    public static void main(String[] args) throws Exception {
        for (String field : new String[] {"studentID", "name", "cgpa", "programme"}) {
            if (!Modifier.isPrivate(Student.class.getDeclaredField(field).getModifiers())) {
                throw new AssertionError("Student state must be private: " + field);
            }
        }
        Student student = new Student();
        student.setStudentID(" CU12345 ");
        student.setName(" Ali ");
        student.setProgramme("BIT");
        student.setCGPA(0);
        student.setCGPA(4);
        if (!"CU12345".equals(student.getStudentID()) || !"Ali".equals(student.getName())
                || !"BIT".equals(student.getProgramme())) throw new AssertionError("Getter mismatch");
        for (double invalid : new double[] {-0.01, 4.01, Double.NaN, Double.POSITIVE_INFINITY}) {
            try {
                student.setCGPA(invalid);
                throw new AssertionError("Invalid CGPA accepted");
            } catch (IllegalArgumentException expected) { }
            if (student.getCGPA() != 4) throw new AssertionError("Rejected input changed state");
        }
        try {
            student.setName(" ");
            throw new AssertionError("Blank name accepted");
        } catch (IllegalArgumentException expected) { }
        if (!"Ali".equals(student.getName())) throw new AssertionError("Name changed on failure");
        System.out.println("Encapsulation and validation checks passed.");
    }
}
