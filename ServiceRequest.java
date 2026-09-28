/**
 * ServiceRequest model class.
 * Represents one student service request waiting in the Queue.
 */
public class ServiceRequest {

    private String requestId;    // e.g. SR001
    private String studentId;    // requesting student's ID
    private String studentName;  // requesting student's name
    private String requestType;  // e.g. Transcript Request
    private String description;  // extra details

    public ServiceRequest(String requestId, String studentId, String studentName,
                          String requestType, String description) {
        this.requestId = requestId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.requestType = requestType;
        this.description = description;
    }

    public String getRequestId()   { return requestId; }
    public String getStudentId()   { return studentId; }
    public String getStudentName() { return studentName; }
    public String getRequestType() { return requestType; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("%s - Student %s (%s) - %s",
                requestId, studentId, studentName, requestType);
    }
}
