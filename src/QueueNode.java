/**
 * Node used by the custom linked-based Queue of ServiceRequest records.
 * Each node stores one request and a link to the next node towards the rear.
 */
public class QueueNode {

    private ServiceRequest request;  // data carried by this node
    private QueueNode next;          // link to the next node in the queue

    public QueueNode(ServiceRequest request) {
        this.request = request;
        this.next = null;
    }

    public ServiceRequest getRequest() { return request; }

    public QueueNode getNext()             { return next; }
    public void setNext(QueueNode next)    { this.next = next; }
}
