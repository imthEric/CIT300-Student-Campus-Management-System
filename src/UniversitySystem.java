import java.util.List;
import java.util.Scanner;

/**
 * UniversitySystem - central controller class.
 *
 * Coordinates every data structure of the application:
 *   StudentLinkedList  -> student storage / management (add, update, delete)
 *   ActionStack        -> recent actions history (LIFO)
 *   ServiceQueue       -> student service requests (FIFO)
 *   StudentBST         -> students organised by Student ID (in-order display)
 *   StudentHashTable   -> efficient Student ID search (separate chaining)
 *   Graph              -> campus locations and connections with BFS/DFS
 *
 * Also provides the menu-driven console interface and all input validation.
 */
public class UniversitySystem {

    // ---------- Data structures (composition) ----------
    private final StudentLinkedList studentList = new StudentLinkedList();
    private final ActionStack actionStack = new ActionStack();
    private final ServiceQueue serviceQueue = new ServiceQueue();
    private final StudentBST studentBST = new StudentBST();
    private final StudentHashTable studentHash = new StudentHashTable();
    private final Graph campusGraph = new Graph();

    private final Scanner scanner = new Scanner(System.in);

    // Auto-incrementing service request counter (SR001, SR002, ...)
    private int requestCounter = 0;

    /** Expected Student ID format, e.g. 23DA2-0578 */
    private static final String STUDENT_ID_PATTERN = "\\d{2}[A-Za-z]{2}\\d-\\d{4}";

    // ==================================================================
    // MAIN MENU LOOP
    // ==================================================================

    /** Display the menu and keep processing choices until Exit. */
    public void run() {
        System.out.println("Welcome to the University Student Record and");
        System.out.println("Campus Route Management System (CIT300).");
        boolean running = true;
        while (running) {
            displayMenu();
            String choice = readLine("Enter your choice: ");
            if (choice == null) {
                break; // input stream ended - exit cleanly
            }
            try {
                int option = Integer.parseInt(choice.trim());
                switch (option) {
                    case 1:  addStudent();                     break;
                    case 2:  updateStudent();                  break;
                    case 3:  deleteStudent();                  break;
                    case 4:  studentList.displayAll();         break;
                    case 5:  addServiceRequest();              break;
                    case 6:  processNextServiceRequest();      break;
                    case 7:  actionStack.display();            break;
                    case 8:  studentBST.displayInOrder();      break;
                    case 9:  searchStudentUsingHashing();      break;
                    case 10: addCampusLocation();              break;
                    case 11: removeCampusLocation();           break;
                    case 12: addCampusConnection();            break;
                    case 13: removeCampusConnection();         break;
                    case 14: campusGraph.displayConnections(); break;
                    case 15: traverseCampusGraph();            break;
                    case 16: running = quit();                 break;
                    case 17: loadSampleData();                 break;
                    default:
                        System.out.println("Invalid menu choice. Please select 1-17.");
                }
            } catch (NumberFormatException e) {
                // Non numeric input such as "abc" must not crash the program
                System.out.println("Invalid input. Please enter a number from the menu.");
            }
            pause();
        }
        System.out.println("Thank you for using the system. Goodbye!");
    }

    private void displayMenu() {
        System.out.println();
        System.out.println("====================================================");
        System.out.println(" UNIVERSITY STUDENT RECORD AND CAMPUS ROUTE SYSTEM");
        System.out.println("====================================================");
        System.out.println(" 1. Add Student Record");
        System.out.println(" 2. Update Student Record");
        System.out.println(" 3. Delete Student Record");
        System.out.println(" 4. Display All Records using Linked List");
        System.out.println(" 5. Add Service Request to Queue");
        System.out.println(" 6. Process Next Service Request");
        System.out.println(" 7. Display Recent Actions using Stack");
        System.out.println(" 8. Display Students using BST/AVL");
        System.out.println(" 9. Search Student using Hashing");
        System.out.println("10. Add Campus Location");
        System.out.println("11. Remove Campus Location");
        System.out.println("12. Add Campus Connection/Road");
        System.out.println("13. Remove Campus Connection/Road");
        System.out.println("14. Display Campus Connections");
        System.out.println("15. Traverse Campus Locations using BFS or DFS");
        System.out.println("16. Exit");
        System.out.println("17. Load Sample Data (optional demo helper)");
    }

    /** Returns true when the user confirms exiting. */
    private boolean quit() {
        System.out.print("Are you sure you want to exit? (y/n): ");
        if (!scanner.hasNextLine()) {
            return true; // no more input - treat as confirmation to exit
        }
        String answer = scanner.nextLine().trim();
        return answer.equalsIgnoreCase("y");
    }

    // ==================================================================
    // INPUT HELPERS WITH VALIDATION
    // ==================================================================

    /**
     * Read a non-empty line, re-prompting until valid text is entered.
     * If the input stream has ended (no more lines available) null is
     * returned so callers can abort gracefully instead of looping forever.
     */
    private String readNonEmpty(String prompt) {
        System.out.print(prompt);
        while (scanner.hasNextLine()) {
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Error: Input cannot be empty. Please try again.");
            System.out.print(prompt);
        }
        return null; // end of input
    }

    /** Read one line safely. Returns null when the input stream has ended. */
    private String readLine(String prompt) {
        System.out.print(prompt);
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return null; // end of input
    }

    /**
     * Read marks within [0, 100], re-prompting on bad input.
     * Returns -1 when the input stream has ended (caller should abort).
     */
    private double readMarks(String prompt) {
        System.out.print(prompt);
        while (scanner.hasNextLine()) {
            String value = scanner.nextLine().trim();
            try {
                double marks = Double.parseDouble(value);
                if (!Student.isValidMarks(marks)) {
                    System.out.println("Error: Marks must be between 0 and 100.");
                    System.out.print(prompt);
                    continue;
                }
                return marks;
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid marks. Please enter a number (e.g. 78.5).");
                System.out.print(prompt);
            }
        }
        return -1; // end of input
    }

    /** Validate the Student ID format (e.g. 23DA2-0578). */
    private boolean isValidStudentIdFormat(String id) {
        return id.matches(STUDENT_ID_PATTERN);
    }

    /** Small helper so output blocks stay readable between menu actions. */
    private void pause() {
        System.out.println();
    }

    // ==================================================================
    // STUDENT OPERATIONS (Linked List + BST + Hash Table kept in sync)
    // ==================================================================

    /** Option 1: Add a new student record to all student structures. */
    private void addStudent() {
        System.out.println("--- Add Student Record ---");
        String id = readNonEmpty("Enter Student ID (Example: 23DA2-0578): ");
        if (id == null) return;
        if (!isValidStudentIdFormat(id)) {
            System.out.println("Error: Invalid Student ID format. Expected example: 23DA2-0578");
            return;
        }
        // Duplicate check across the master Linked List
        if (studentList.search(id) != null || studentHash.get(id) != null) {
            System.out.println("Error: Student ID already exists.");
            return;
        }
        String name = readNonEmpty("Enter Student Name: ");
        if (name == null) return;
        String programme = readNonEmpty("Enter Programme: ");
        if (programme == null) return;
        double marks = readMarks("Enter Marks (0-100): ");
        if (marks < 0) return; // input ended before valid marks were given

        Student student = new Student(id, name, programme, marks);

        // Keep every structure consistent when a student is added
        studentList.add(student);
        studentBST.insert(student);
        studentHash.put(id, student);

        pushAction("Student Added", id, "New student record added");
        System.out.println("Student added successfully.");
    }

    /** Option 2: Update an existing student's details. */
    private void updateStudent() {
        System.out.println("--- Update Student Record ---");
        String id = readNonEmpty("Enter Student ID to update: ");
        if (id == null) return;
        Student existing = studentList.search(id);
        if (existing == null) {
            System.out.println("Error: Student record not found.");
            return;
        }
        System.out.println("Current record: " + existing);
        String name = readNonEmpty("Enter new Student Name: ");
        if (name == null) return;
        String programme = readNonEmpty("Enter new Programme: ");
        if (programme == null) return;
        double marks = readMarks("Enter new Marks (0-100): ");
        if (marks < 0) return;

        studentList.update(id, name, programme, marks);
        // The BST and Hash Table hold the same Student object reference,
        // so updating through the Linked List keeps them consistent too.
        pushAction("Student Updated", id, "Student record updated");
        System.out.println("Student updated successfully.");
    }

    /** Option 3: Delete a student from every structure. */
    private void deleteStudent() {
        System.out.println("--- Delete Student Record ---");
        String id = readNonEmpty("Enter Student ID to delete: ");
        if (id == null) return;
        if (studentList.search(id) == null) {
            System.out.println("Error: Student record not found.");
            return;
        }
        studentList.delete(id);
        studentBST.delete(id);
        studentHash.remove(id);
        pushAction("Student Deleted", id, "Student record deleted");
        System.out.println("Student deleted successfully.");
    }

    /** Option 9: Efficient hash-based lookup of a student. */
    private void searchStudentUsingHashing() {
        System.out.println("--- Search Student using Hashing ---");
        String id = readNonEmpty("Enter Student ID to search: ");
        if (id == null) return;
        Student found = studentHash.get(id);
        if (found == null) {
            System.out.println("Student record not found.");
        } else {
            System.out.println("Found via Hash Table (O(1) average lookup):");
            System.out.println("---------------------------------------------------------------");
            System.out.printf("%-15s %-20s %-12s %s%n", "Student ID", "Name", "Programme", "Marks");
            System.out.println("---------------------------------------------------------------");
            found.display();
            System.out.println("---------------------------------------------------------------");
        }
    }

    // ==================================================================
    // STACK HELPERS
    // ==================================================================

    /** Push an action entry onto the recent-actions stack. */
    private void pushAction(String type, String studentId, String description) {
        actionStack.push(new Action(type, studentId, description));
    }

    // ==================================================================
    // QUEUE OPERATIONS (service requests)
    // ==================================================================

    /** Option 5: Enqueue a new service request. */
    private void addServiceRequest() {
        System.out.println("--- Add Service Request to Queue ---");
        String id = readNonEmpty("Enter requesting Student ID: ");
        if (id == null) return;
        Student student = studentList.search(id);
        if (student == null) {
            System.out.println("Error: Student record not found. Requests need a registered student.");
            return;
        }
        System.out.println("Request types: Transcript Request | Registration Request |"
                + " ID Card Request | Academic Letter Request | General Inquiry");
        String type = readNonEmpty("Enter Request Type: ");
        if (type == null) return;
        String rawDesc = readLine("Enter Description (optional): ");
        String description = rawDesc == null ? "" : rawDesc.trim();
        if (description.isEmpty()) {
            description = "No additional description";
        }

        requestCounter++;
        String requestId = String.format("SR%03d", requestCounter);
        ServiceRequest request =
                new ServiceRequest(requestId, student.getStudentId(), student.getName(), type, description);

        serviceQueue.enqueue(request);
        pushAction("Service Request Added", student.getStudentId(),
                requestId + " (" + type + ") queued");
        System.out.println("Service request added successfully: " + request);
    }

    /** Option 6: Dequeue and process the next request (FIFO order). */
    private void processNextServiceRequest() {
        System.out.println("--- Process Next Service Request ---");
        if (serviceQueue.isEmpty()) {
            System.out.println("No pending service requests.");
            return;
        }
        ServiceRequest next = serviceQueue.peek();
        System.out.println("Processing request:");
        System.out.println(next.getRequestId() + " - " + next.getRequestType()
                + " (Student: " + next.getStudentId() + " - " + next.getStudentName() + ")");
        ServiceRequest processed = serviceQueue.dequeue();
        pushAction("Service Request Processed", processed.getStudentId(),
                processed.getRequestId() + " (" + processed.getRequestType() + ") completed");
        System.out.println("Request processed successfully.");
        if (!serviceQueue.isEmpty()) {
            System.out.println("Next in queue: " + serviceQueue.peek());
        } else {
            System.out.println("Queue is now empty.");
        }
    }

    // ==================================================================
    // GRAPH OPERATIONS (campus network)
    // ==================================================================

    /** Option 10: Add a campus location vertex. */
    private void addCampusLocation() {
        System.out.println("--- Add Campus Location ---");
        String location = readNonEmpty("Enter Location Name: ");
        if (location == null) return;
        if (campusGraph.addLocation(location)) {
            pushAction("Campus Location Added", "-", location);
            System.out.println("Campus location added successfully.");
        } else {
            System.out.println("Error: Campus location already exists.");
        }
    }

    /** Option 11: Remove a campus location and its edges. */
    private void removeCampusLocation() {
        System.out.println("--- Remove Campus Location ---");
        String location = readNonEmpty("Enter Location Name to remove: ");
        if (location == null) return;
        if (campusGraph.removeLocation(location)) {
            pushAction("Campus Location Removed", "-", location);
            System.out.println("Campus location removed successfully "
                    + "(associated connections also removed).");
        } else {
            System.out.println("Error: Campus location not found.");
        }
    }

    /** Option 12: Add an undirected connection between two locations. */
    private void addCampusConnection() {
        System.out.println("--- Add Campus Connection/Road ---");
        String from = readNonEmpty("Enter starting Campus Location (From): ");
        if (from == null) return;
        String to = readNonEmpty("Enter destination Campus Location (To): ");
        if (to == null) return;
        int result = campusGraph.addConnection(from, to);
        if (result == 1) {
            pushAction("Campus Connection Added", "-", from.trim() + " <-> " + to.trim());
            System.out.println("Campus connection added successfully.");
        } else if (result == -1) {
            System.out.println("Error: Connection already exists.");
        } else if (from.trim().equalsIgnoreCase(to.trim())) {
            System.out.println("Error: A location cannot be connected to itself.");
        } else {
            System.out.println("Error: One or both campus locations do not exist.");
        }
    }

    /** Option 13: Remove an existing connection. */
    private void removeCampusConnection() {
        System.out.println("--- Remove Campus Connection/Road ---");
        String from = readNonEmpty("Enter starting Campus Location (From): ");
        if (from == null) return;
        String to = readNonEmpty("Enter destination Campus Location (To): ");
        if (to == null) return;
        if (!campusGraph.containsLocation(from) || !campusGraph.containsLocation(to)) {
            System.out.println("Error: One or both campus locations do not exist.");
            return;
        }
        if (campusGraph.removeConnection(from, to)) {
            pushAction("Campus Connection Removed", "-", from.trim() + " <-> " + to.trim());
            System.out.println("Campus connection removed successfully.");
        } else {
            System.out.println("Error: Connection does not exist.");
        }
    }

    /** Option 15: BFS or DFS traversal from a chosen starting location. */
    private void traverseCampusGraph() {
        System.out.println("--- Traverse Campus Locations ---");
        if (campusGraph.getVertexCount() == 0) {
            System.out.println("The campus graph is empty. Add locations first.");
            return;
        }
        String start = readNonEmpty("Enter starting Campus Location: ");
        if (start == null) return;
        if (!campusGraph.containsLocation(start)) {
            System.out.println("Error: Campus location not found.");
            return;
        }
        System.out.println("Traversal method: 1. BFS (Breadth-First)   2. DFS (Depth-First)");
        String method = readNonEmpty("Choose Traversal Method (1/2): ");
        if (method == null) return;

        List<String> order;
        String label;
        if (method.equals("1")) {
            order = campusGraph.bfs(start);
            label = "BFS Traversal";
        } else if (method.equals("2")) {
            order = campusGraph.dfs(start);
            label = "DFS Traversal";
        } else {
            System.out.println("Invalid input. Please choose 1 or 2.");
            return;
        }
        System.out.println(label + ":");
        System.out.println(String.join(" -> ", order));
        pushAction("Graph Traversal", "-", label + " from " + start.trim());
    }

    // ==================================================================
    // OPTIONAL SAMPLE DATA (menu option 17) - makes demos easy
    // ==================================================================

    /** Load sample students and campus network for demonstration purposes. */
    private void loadSampleData() {
        System.out.println("--- Loading Sample Data ---");

        // Sample students (added only when they do not already exist)
        addSampleStudent("23DA2-0578", "N M Imthath", "BAIT", 78.5);
        addSampleStudent("23DA2-0612", "Aravinda", "BAIT", 82.0);
        addSampleStudent("23DA2-0695", "Prashani", "BAIT", 69.5);

        // Sample campus locations
        String[] locations = {
                "Main Gate", "Library", "Lecture Hall A", "Lecture Hall B",
                "Computer Lab 1", "Cafeteria", "Auditorium", "Administration Building"
        };
        for (String loc : locations) {
            campusGraph.addLocation(loc);
        }

        // Sample connections
        campusGraph.addConnection("Main Gate", "Administration Building");
        campusGraph.addConnection("Main Gate", "Library");
        campusGraph.addConnection("Library", "Cafeteria");
        campusGraph.addConnection("Library", "Lecture Hall A");
        campusGraph.addConnection("Lecture Hall A", "Computer Lab 1");
        campusGraph.addConnection("Cafeteria", "Auditorium");

        pushAction("Sample Data Loaded", "-", "Students and campus network loaded for demo");
        System.out.println("Sample data loaded successfully.");
    }

    /** Helper used by loadSampleData to keep all structures consistent. */
    private void addSampleStudent(String id, String name, String programme, double marks) {
        if (studentList.search(id) != null) {
            return; // skip duplicates silently during sample loading
        }
        Student s = new Student(id, name, programme, marks);
        studentList.add(s);
        studentBST.insert(s);
        studentHash.put(id, s);
    }
}
