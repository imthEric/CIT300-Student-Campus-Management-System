/**
 * Action model class.
 * Represents one entry in the recent-actions history stored in the Stack.
 */
public class Action {

    private String actionType;   // e.g. "Student Added"
    private String studentId;    // related student ID (may be "-")
    private String description;  // human readable description

    public Action(String actionType, String studentId, String description) {
        this.actionType = actionType;
        this.studentId = studentId;
        this.description = description;
    }

    public String getActionType()  { return actionType; }
    public String getStudentId()   { return studentId; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("Action: %-28s Student ID: %-12s Description: %s",
                actionType, studentId, description);
    }
}
