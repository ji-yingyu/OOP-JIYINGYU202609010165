public class Student {
    String name;
    int age;
    double gpa;

    public Student(String studentName, int studentAge, double studentGpa) {
        name = studentName;
        age = studentAge;
        gpa = studentGpa;
    }

    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("GPA: " + gpa);
    }

    public void study() {
        System.out.println(name + " is studying.");
    }

    public void takeExam() {
        System.out.println(name + " is taking an exam.");
    }
}
