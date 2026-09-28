/**
 * Node used by the custom singly Linked List that stores Student records.
 * Each node holds one Student and a reference to the next node.
 */
public class StudentNode {

    private Student student;   // data carried by this node
    private StudentNode next;  // link to the next node in the list

    public StudentNode(Student student) {
        this.student = student;
        this.next = null;
    }

    public Student getStudent()          { return student; }
    public void setStudent(Student s)    { this.student = s; }

    public StudentNode getNext()               { return next; }
    public void setNext(StudentNode next)      { this.next = next; }
}
