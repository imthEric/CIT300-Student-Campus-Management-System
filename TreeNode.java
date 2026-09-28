/**
 * Node used by the Binary Search Tree (BST) that organises students
 * by Student ID (the primary key).
 */
public class TreeNode {

    private Student student;      // data carried by this node
    private TreeNode left;        // left child  (IDs "less than" this node)
    private TreeNode right;       // right child (IDs "greater than" this node)

    public TreeNode(Student student) {
        this.student = student;
        this.left = null;
        this.right = null;
    }

    public Student getStudent()            { return student; }
    public void setStudent(Student student){ this.student = student; }

    public TreeNode getLeft()               { return left; }
    public void setLeft(TreeNode left)      { this.left = left; }

    public TreeNode getRight()              { return right; }
    public void setRight(TreeNode right)    { this.right = right; }
}
