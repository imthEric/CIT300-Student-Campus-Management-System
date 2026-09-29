/**
 * Custom Queue implementation (linked-node based) for managing student
 * service requests.
 *
 * Demonstrates the FIFO (First-In-First-Out) principle:
 *   enqueue - add a request at the rear of the queue
 *   dequeue - remove the request at the front of the queue
 *   peek/front, isEmpty and display operations
 */
public class ServiceQueue {

    private QueueNode front;   // next request to be processed
    private QueueNode rear;    // most recently added request
    private int size;          // number of pending requests

    public ServiceQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    /** Add (enqueue) a new service request at the rear of the queue. */
    public void enqueue(ServiceRequest request) {
        QueueNode newNode = new QueueNode(request);
        if (rear != null) {
            rear.setNext(newNode);  // link current rear to the new node
        }
        rear = newNode;             // new node becomes the rear
        if (front == null) {
            front = newNode;        // first item is also the front
        }
        size++;
    }

    /** Remove (dequeue) and return the front request, or null when empty. */
    public ServiceRequest dequeue() {
        if (isEmpty()) {
            return null;
        }
        ServiceRequest request = front.getRequest();
        front = front.getNext();    // advance the front pointer
        if (front == null) {
            rear = null;            // queue became empty
        }
        size--;
        return request;
    }

    /** Look at the front request without removing it. */
    public ServiceRequest peek() {
        if (isEmpty()) {
            return null;
        }
        return front.getRequest();
    }

    public boolean isEmpty() { return front == null; }

    public int getSize() { return size; }

    /** Display all pending requests in arrival (FIFO) order. */
    public void display() {
        System.out.println("==================================================");
        System.out.println("        SERVICE REQUEST QUEUE (FIFO)");
        System.out.println("==================================================");
        if (isEmpty()) {
            System.out.println("No pending service requests.");
            return;
        }
        QueueNode current = front;
        int count = 1;
        while (current != null) {
            System.out.println(count + ". " + current.getRequest());
            current = current.getNext();
            count++;
        }
        System.out.println("Pending requests: " + size);
    }
}
