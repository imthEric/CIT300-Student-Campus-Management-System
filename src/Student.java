/**
 * Student model class.
 * Represents a single university student record.
 * Demonstrates encapsulation with private attributes and public getters/setters.
 */
public class Student {

    // Private attributes (encapsulation)
    private String studentId;
    private String name;
    private String programme;
    private double marks;

    /**
     * Constructor for a Student record.
     */
    public Student(String studentId, String name, String programme, double marks) {
        this.studentId = studentId;
        this.name = name;
        this.programme = programme;
        this.marks = marks;
    }

    // ---------- Getters ----------
    public String getStudentId()  { return studentId; }
    public String getName()       { return name; }
    public String getProgramme()  { return programme; }
    public double getMarks()      { return marks; }

    // ---------- Setters ----------
    public void setName(String name)             { this.name = name; }
    public void setProgramme(String programme)   { this.programme = programme; }
    public void setMarks(double marks)           { this.marks = marks; }

    /**
     * Validates whether the marks are within the allowed range 0 - 100.
     */
    public static boolean isValidMarks(double marks) {
        return marks >= 0 && marks <= 100;
    }

    /**
     * Simple display method used by the console interface.
     */
    public void display() {
        System.out.printf("%-15s %-20s %-12s %.1f%n", studentId, name, programme, marks);
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %.1f", studentId, name, programme, marks);
    }
}
