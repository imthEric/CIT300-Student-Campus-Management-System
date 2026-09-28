/**
 * Binary Search Tree (BST) implementation used to organise and search
 * student records using Student ID as the primary key.
 *
 * BST property (using String comparison on IDs):
 *   left subtree IDs  <  node ID  <  right subtree IDs
 *
 * Supports insert, search, delete and in-order traversal which lists
 * students in sorted Student-ID order.
 */
public class StudentBST {

    private TreeNode root;   // root of the tree
    private int size;        // number of students stored

    public StudentBST() {
        root = null;
        size = 0;
    }

    public int getSize() { return size; }

    // ---------------- Insert ----------------

    /** Public insert method - delegates to the recursive helper. */
    public boolean insert(Student student) {
        if (search(student.getStudentId()) != null) {
            return false; // duplicate Student ID not allowed in the tree
        }
        root = insertRecursive(root, student);
        size++;
        return true;
    }

    private TreeNode insertRecursive(TreeNode node, Student student) {
        if (node == null) {
            return new TreeNode(student);  // found the correct empty spot
        }
        int cmp = student.getStudentId().compareToIgnoreCase(node.getStudent().getStudentId());
        if (cmp < 0) {
            node.setLeft(insertRecursive(node.getLeft(), student));
        } else {
            node.setRight(insertRecursive(node.getRight(), student));
        }
        return node;
    }

    // ---------------- Search ----------------

    /** Search for a student by ID using BST ordered navigation. */
    public Student search(String studentId) {
        TreeNode current = root;
        while (current != null) {
            int cmp = studentId.compareToIgnoreCase(current.getStudent().getStudentId());
            if (cmp == 0) {
                return current.getStudent();
            } else if (cmp < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        return null; // not found
    }

    // ---------------- Delete ----------------

    /** Delete a student from the tree by ID. */
    public boolean delete(String studentId) {
        TreeNode node = findNode(root, null, studentId);
        if (node == null) {
            return false;
        }
        root = deleteRecursive(root, studentId);
        size--;
        return true;
    }

    private TreeNode findNode(TreeNode node, TreeNode parent, String studentId) {
        if (node == null) {
            return null;
        }
        int cmp = studentId.compareToIgnoreCase(node.getStudent().getStudentId());
        if (cmp == 0) {
            return node;
        }
        return cmp < 0 ? findNode(node.getLeft(), node, studentId)
                       : findNode(node.getRight(), node, studentId);
    }

    private TreeNode deleteRecursive(TreeNode node, String studentId) {
        if (node == null) {
            return null;
        }
        int cmp = studentId.compareToIgnoreCase(node.getStudent().getStudentId());
        if (cmp < 0) {
            node.setLeft(deleteRecursive(node.getLeft(), studentId));
        } else if (cmp > 0) {
            node.setRight(deleteRecursive(node.getRight(), studentId));
        } else {
            // Node found - handle three cases
            if (node.getLeft() == null) {
                return node.getRight();     // no left child: replace with right
            } else if (node.getRight() == null) {
                return node.getLeft();      // no right child: replace with left
            }
            // Two children: replace with in-order successor (smallest of right subtree)
            TreeNode successor = findMin(node.getRight());
            node.setStudent(successor.getStudent());
            node.setRight(deleteRecursive(node.getRight(),
                    successor.getStudent().getStudentId()));
        }
        return node;
    }

    private TreeNode findMin(TreeNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    // ---------------- Traversal / Display ----------------

    /** Display all students using an in-order traversal (sorted by Student ID). */
    public void displayInOrder() {
        System.out.println("==================================================");
        System.out.println("      STUDENTS IN BST ORDER (In-order Traversal)");
        System.out.println("==================================================");
        if (root == null) {
            System.out.println("BST is empty - no student records.");
            return;
        }
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-12s %s%n", "Student ID", "Name", "Programme", "Marks");
        System.out.println("---------------------------------------------------------------");
        inOrder(root);
        System.out.println("---------------------------------------------------------------");
        System.out.println("Total students in BST: " + size);
    }

    /** Recursive in-order traversal: Left -> Node -> Right. */
    private void inOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.getLeft());
        node.getStudent().display();
        inOrder(node.getRight());
    }
}
