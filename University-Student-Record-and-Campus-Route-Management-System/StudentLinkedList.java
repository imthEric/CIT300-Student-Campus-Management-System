/**
 * Custom singly Linked List implementation for managing Student records.
 * Supports add, update, delete, search and display operations.
 * This is a manual linked-list (head/next links) so the Data Structures
 * concepts are clearly demonstrated rather than hidden behind Java collections.
 */
public class StudentLinkedList {

    private StudentNode head;   // first node of the list
    private int size;           // number of students stored

    public StudentLinkedList() {
        head = null;
        size = 0;
    }

    public int getSize() { return size; }

    public boolean isEmpty() { return head == null; }

    /**
     * Add a new student at the end of the list.
     * Returns false if the student ID already exists (duplicate prevention).
     */
    public boolean add(Student student) {
        if (search(student.getStudentId()) != null) {
            return false; // duplicate Student ID
        }
        StudentNode newNode = new StudentNode(student);
        if (head == null) {
            head = newNode;
        } else {
            StudentNode current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        size++;
        return true;
    }

    /**
     * Search for a student by ID by traversing the list.
     * Returns the Student object or null when not found.
     */
    public Student search(String studentId) {
        StudentNode current = head;
        while (current != null) {
            if (current.getStudent().getStudentId().equalsIgnoreCase(studentId)) {
                return current.getStudent();
            }
            current = current.getNext();
        }
        return null;
    }

    /**
     * Update an existing student's details (name, programme, marks).
     * The student ID itself is treated as the immutable key.
     */
    public boolean update(String studentId, String name, String programme, double marks) {
        Student s = search(studentId);
        if (s == null) {
            return false;
        }
        s.setName(name);
        s.setProgramme(programme);
        s.setMarks(marks);
        return true;
    }

    /**
     * Delete a student node from the list by ID.
     * Handles head removal and middle/end removal by re-linking nodes.
     */
    public boolean delete(String studentId) {
        if (head == null) {
            return false;
        }
        // Special case: the head node holds the target student
        if (head.getStudent().getStudentId().equalsIgnoreCase(studentId)) {
            head = head.getNext();
            size--;
            return true;
        }
        StudentNode current = head;
        while (current.getNext() != null
                && !current.getNext().getStudent().getStudentId().equalsIgnoreCase(studentId)) {
            current = current.getNext();
        }
        if (current.getNext() == null) {
            return false; // not found
        }
        // Skip over the target node (unlink it)
        current.setNext(current.getNext().getNext());
        size--;
        return true;
    }

    /**
     * Display all student records in list order using a formatted table.
     */
    public void displayAll() {
        System.out.println("==================================================");
        System.out.println("              STUDENT RECORDS (Linked List)");
        System.out.println("==================================================");
        if (head == null) {
            System.out.println("No student records found.");
            return;
        }
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-12s %s%n", "Student ID", "Name", "Programme", "Marks");
        System.out.println("---------------------------------------------------------------");
        StudentNode current = head;
        while (current != null) {
            current.getStudent().display();
            current = current.getNext();
        }
        System.out.println("---------------------------------------------------------------");
        System.out.println("Total students: " + size);
    }
}
