/**
 * Node used by the custom linked-based Stack of Action records.
 * Each node stores one Action and a link to the node below it.
 */
public class StackNode {

    private Action action;      // data carried by this node
    private StackNode below;    // link to the next node down the stack

    public StackNode(Action action) {
        this.action = action;
        this.below = null;
    }

    public Action getAction()          { return action; }

    public StackNode getBelow()              { return below; }
    public void setBelow(StackNode below)    { this.below = below; }
}
