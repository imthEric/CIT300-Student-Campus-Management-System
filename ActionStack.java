/**
 * Custom Stack implementation (linked-node based) used to maintain the
 * recent actions / history of the system.
 *
 * Demonstrates the LIFO (Last-In-First-Out) principle:
 *   push  - place an action on top of the stack
 *   pop   - remove and return the top action
 *   peek  - look at the top action without removing it
 *   isEmpty / display
 */
public class ActionStack {

    private StackNode top;   // reference to the top node of the stack
    private int size;        // number of actions currently stored

    public ActionStack() {
        top = null;
        size = 0;
    }

    /** Push a new action onto the top of the stack. */
    public void push(Action action) {
        StackNode newNode = new StackNode(action);
        newNode.setBelow(top);   // current top becomes the node below
        top = newNode;           // new node becomes the top
        size++;
    }

    /** Pop (remove and return) the top action, or null if the stack is empty. */
    public Action pop() {
        if (isEmpty()) {
            return null;
        }
        Action action = top.getAction();
        top = top.getBelow();    // move the top reference down one node
        size--;
        return action;
    }

    /** Peek at the top action without removing it. */
    public Action peek() {
        if (isEmpty()) {
            return null;
        }
        return top.getAction();
    }

    public boolean isEmpty() { return top == null; }

    public int getSize() { return size; }

    /**
     * Display all recorded actions starting from the most recent (top of stack),
     * which clearly demonstrates LIFO ordering.
     */
    public void display() {
        System.out.println("==================================================");
        System.out.println("           RECENT ACTIONS (Stack - LIFO)");
        System.out.println("==================================================");
        if (isEmpty()) {
            System.out.println("No recent actions recorded.");
            return;
        }
        System.out.println("------------------------------------");
        StackNode current = top;
        int count = 1;
        while (current != null) {
            System.out.println(count + ". " + current.getAction());
            current = current.getBelow();
            count++;
        }
        System.out.println("------------------------------------");
        System.out.println("Total actions recorded: " + size);
    }
}
