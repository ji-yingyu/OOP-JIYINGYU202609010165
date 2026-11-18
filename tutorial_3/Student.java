public class Student {
    private String studentID;
    private String name;
    private double cgpa;
    private String programme;

    private static String requireText(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be blank.");
        }
        return text.trim();
    }

    public void setStudentID(String studentID) { this.studentID = requireText(studentID); }
    public void setName(String name) { this.name = requireText(name); }
    public void setProgramme(String programme) { this.programme = requireText(programme); }

    public void setCGPA(double cgpa) {
        if (!Double.isFinite(cgpa) || cgpa < 0 || cgpa > 4.0) {
            throw new IllegalArgumentException("CGPA must be between 0.0 and 4.0.");
        }
        this.cgpa = cgpa;
    }

    public String getStudentID() { return studentID; }
    public String getName() { return name; }
    public double getCGPA() { return cgpa; }
    public String getProgramme() { return programme; }
}
