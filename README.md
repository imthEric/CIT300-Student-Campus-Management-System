# CIT300-Student-Campus-Management-System
 
# University Student Record and Campus Route Management System

**Module:** CIT300 – Data Structures and Algorithms
**Assignment:** Graded Practical Assignment 1 – Week 10
**Type:** Java console-based (terminal) menu-driven application

---

## Project Description

This system manages **university student records** and a **campus location/route network**
in a single integrated console application. Rather than hiding data handling behind
built-in collections, the project implements and connects the core data structures
manually so each one is clearly demonstrated:

```text
Student Records                    Campus Network
      │                                 │
      ├── Linked List  (storage/CRUD)   └── Graph (adjacency list)
      ├── Stack        (action history)      ├── Locations = vertices
      ├── Queue        (service requests)    ├── Roads     = edges
      ├── BST          (ID-ordered search)   └── BFS / DFS traversals
      └── Hash Table   (O(1) ID lookup)
```

All student structures (Linked List, BST, Hash Table) are kept **consistent**: adding,
updating or deleting a student through the menu updates every structure at once, and
meaningful operations are automatically pushed onto the recent-actions Stack.

## Technologies

```text
Java (JDK 8 or later — tested with OpenJDK 17)
GitHub
```

No external libraries or frameworks are used.

## Data Structures Used

| # | Data Structure | Implementation | Where it is used |
|---|----------------|----------------|------------------|
| 1 | **Linked List** | `StudentNode` + `StudentLinkedList` (manual singly linked list) | Primary storage of student records: add, update, delete, search, display (menu 1–4) |
| 2 | **Stack** | `StackNode` + `ActionStack` (linked-node stack, LIFO) | Recent actions/history: student added/updated/deleted, requests processed, graph changes (menu 7). Supports push/pop/peek/isEmpty/display |
| 3 | **Queue** | `QueueNode` + `ServiceQueue` (linked-node queue, FIFO) | Student service requests (Transcript, Registration, ID Card, Academic Letter, General Inquiry) — first in, first processed (menu 5–6) |
| 4 | **BST** | `TreeNode` + `StudentBST` (Binary Search Tree keyed on Student ID) | Organises students; supports insert, ordered search, delete, and **in-order traversal** display (menu 8) |
| 5 | **Hashing** | `HashNode` + `StudentHashTable` (custom hash table, polynomial rolling hash, **separate chaining** for collisions) | Efficient Student ID → Student record lookup (menu 9) |
| 6 | **Graph** | `GraphNode` + `Graph` (**adjacency list**, undirected) | Campus locations as vertices and roads/paths as edges; add/remove locations & connections, display neighbours (menu 10–14) |
| 7 | **BFS / DFS** | Inside `Graph` (BFS uses a queue, DFS uses an explicit stack) | Campus route traversal from any starting location (menu 15) |

## Features

- Add / Update / Delete / Search / Display student records (all structures kept in sync)
- Input validation everywhere:
  - Menu choices (`abc`, `99`, etc. never crash the program)
  - Student ID format check (e.g. `23DA2-0578`) and duplicate-ID rejection
  - Non-empty name and programme
  - Marks restricted to **0 – 100** (rejects `-10`, `105`, `abc`)
  - "Student record not found" handling for update/delete/search
- Service request queue with auto-generated request IDs (SR001, SR002, …) and FIFO processing
- Recent-action stack showing the newest action first (LIFO)
- BST in-order display (students sorted by Student ID)
- Hash-table search plus bucket-chain visualisation of collision handling
- Campus graph: add/remove locations (edges cleaned up automatically), add/remove roads,
  duplicate/self/missing-location validation, neighbour listing
- BFS **and** DFS traversal with start-location validation
- Optional sample-data loader (menu 17) for quick demonstrations
- Clean formatted console output with headings and tables

## Menu

```text
====================================================
 UNIVERSITY STUDENT RECORD AND CAMPUS ROUTE SYSTEM
====================================================
 1. Add Student Record
 2. Update Student Record
 3. Delete Student Record
 4. Display All Records using Linked List
 5. Add Service Request to Queue
 6. Process Next Service Request
 7. Display Recent Actions using Stack
 8. Display Students using BST/AVL
 9. Search Student using Hashing
10. Add Campus Location
11. Remove Campus Location
12. Add Campus Connection/Road
13. Remove Campus Connection/Road
14. Display Campus Connections
15. Traverse Campus Locations using BFS or DFS
16. Exit
17. Load Sample Data (optional demo helper)
```

## Project Structure

```text
src/
├── Main.java                 # entry point - starts the menu
├── Student.java              # student model (ID, name, programme, marks)
├── StudentNode.java          # linked-list node
├── StudentLinkedList.java    # custom linked list (add/update/delete/search/display)
├── Action.java               # action-history entry model
├── StackNode.java            # stack node
├── ActionStack.java          # custom stack (push/pop/peek/isEmpty/display) - LIFO
├── ServiceRequest.java       # service request model
├── QueueNode.java            # queue node
├── ServiceQueue.java         # custom queue (enqueue/dequeue/peek/isEmpty/display) - FIFO
├── TreeNode.java             # BST node
├── StudentBST.java           # BST keyed on Student ID (insert/search/delete/in-order)
├── HashNode.java             # hash-table chain node
├── StudentHashTable.java     # custom hash table with separate chaining
├── GraphNode.java            # graph vertex with adjacency list
├── Graph.java                # campus graph + BFS + DFS
└── UniversitySystem.java     # central controller: menu, validation, integration
```

## How to Run

From the project root:

```bash
cd src
javac *.java
java Main
```

To exit the running program choose option **16** and confirm with `y`.

### Quick demo tip

At the menu, enter **17** first to load sample students
(`23DA2-0578`, `23DA2-0612`, `23DA2-0695`) and the sample campus network
(Main Gate, Library, Lecture Hall A/B, Computer Lab 1, Cafeteria, Auditorium,
Administration Building with their roads). The sample loader is optional — all
structures can also be built manually through options 1, 5, 10 and 12.

## Testing Guidance

The application was verified with both scripted end-to-end console sessions and
direct data-structure unit tests. Suggested manual test cases:

### Student tests
- Add a valid student → "Student added successfully."
- Add the same ID twice → "Error: Student ID already exists."
- Add invalid ID format (e.g. `abc-id`) → format error message
- Enter marks `-10`, `105`, `abc` → re-prompted until a value in 0–100 is given
- Update/delete an existing student → success, verify via options 4/8/9
- Update/delete a missing ID → "Error: Student record not found."
- Enter `abc` or `99` at the main menu → friendly error, no crash

### Stack tests
- Perform several actions then choose 7 → newest action listed as item 1 (LIFO)
- Choose 7 before doing anything → "No recent actions recorded."

### Queue tests
- Add three requests (option 5) → they appear in arrival order
- Process them (option 6) one by one → SR001 first, SR003 last (FIFO)
- Process with an empty queue → "No pending service requests."

### BST tests
- Add students out of ID order, then option 8 → records printed sorted by ID (in-order)
- Delete a middle ID, then option 8 → record gone, tree still consistent

### Hashing tests
- Option 9 with an existing ID → record found instantly
- Option 9 with a missing ID → "Student record not found."
- Insert many similar IDs → chains grow inside buckets (separate chaining handles collisions)

### Graph tests
- Add a location twice → "Error: Campus location already exists."
- Connect two existing locations → success; repeat → "Error: Connection already exists."
- Self-connection / missing location → clear errors
- Remove a location → its roads disappear from every neighbour list
- BFS and DFS from `Main Gate` → all reachable locations visited exactly once
- Invalid start location → "Error: Campus location not found."

During development a throw-away unit-test class covering all seven structures
(27 assertions: hashing/collisions, stack LIFO, queue FIFO, linked-list CRUD,
BST insert/search/delete/traversal, graph BFS/DFS/edge cleanup) passed 27/27,
in addition to scripted end-to-end menu sessions for every validation path.

## Demonstration Plan (video under 15 minutes)

| Time | Segment | What to show |
|------|---------|--------------|
| 0:00–1:00 | Introduction | Title slide, module, group members, overview of the architecture diagram |
| 1:00–2:00 | Setup | Compile (`javac *.java`) and run (`java Main`), load sample data (option 17) |
| 2:00–5:00 | Student records (Linked List) | Add valid student, duplicate ID error, invalid marks error, update, delete, display all (options 1–4) |
| 5:00–6:30 | BST & Hashing | Option 8 (sorted in-order output), option 9 (found + not-found searches) |
| 6:30–8:00 | Queue (FIFO) | Add 3 service requests, process them one by one showing SR001→SR002→SR003 order |
| 8:00–9:00 | Stack (LIFO) | Option 7 — point out the newest action is on top, matching the operations just performed |
| 9:00–12:00 | Graph | Add/remove locations (duplicate + missing errors), add/remove roads (duplicate/self errors), display connections |
| 12:00–13:30 | Traversals | BFS and DFS from Main Gate; invalid start location error |
| 13:30–14:30 | Validation recap | `abc` and `99` at the menu, empty inputs, graceful exit (16 → y) |
| 14:30–15:00 | Wrap-up | Each member states their contribution briefly |

**Recording requirements checklist:** one merged video, under 15 minutes,
every member's face clearly visible throughout, and every member demonstrates
at least one part of the system.

## Group Members

| No. | Name | Student ID | Responsibility | Individual Contribution |
|-----|------|------------|----------------|------------------------|
| 1 | N M IMTHATH | 23da2-0578 | Linked List + Student CRUD | Implemented student linked list; added update and delete operations |
| 2 | A M D R ADHIKARI | 23da2-0965 | Stack + Queue | Implemented action stack; added service request queue |
| 3 | M S M SABREEN | 23da2-0733 | BST + Hashing | Implemented student BST; added student hash search |
| 4 | M L F LAFRA | 23da2-1118 | Graph + BFS/DFS | Implemented campus graph; added BFS traversal |

*(Placeholders — replace with real group information before submission.)*

## GitHub Collaboration

Work should be evidenced through the repository: feature branches, meaningful commits
(e.g. "Implemented Linked List", "Added BFS traversal", "Fixed graph connection validation"),
pull requests and reviews. Use this repository as the collaboration hub — do not fake evidence.

Suggested commit sequence per component:

```text
Initial project structure
Implemented Student class
Implemented Linked List
Implemented Stack
Implemented Queue
Implemented BST
Implemented Hash Table
Implemented Graph
Added BFS traversal
Added input validation
Integrated all data structures
Updated README
Fixed graph connection validation
Final testing and cleanup
```

## Submission Notes

- Submit through the designated **LMS link on or before 29 September**.
- If the project is too large for direct upload: upload the complete project to Google Drive,
  paste the folder link into a `.txt` file, grant **Editor** access to
  `asanka.r@sltc.ac.lk` and `kaushika.w@sltc.ac.lk` (verify permissions), and upload the TXT
  file through the LMS submission link. An email after the deadline is **not** a valid submission.

## Requirement Checklist

```text
[x] Java console application (no GUI/web)
[x] Student class with ID, Name, Programme, Marks
[x] Linked List: add / update / delete / search / display
[x] Stack: recent actions history (push/pop/peek/isEmpty/display, LIFO)
[x] Queue: service requests (enqueue/dequeue/peek/FIFO processing)
[x] BST: insert / search / delete / in-order traversal by Student ID
[x] Hash Table: custom implementation, Student ID keys, separate-chaining collisions
[x] Graph: adjacency list, add/remove locations, add/remove connections, display
[x] BFS and DFS traversal with start-location validation
[x] Full 16-option menu (+ optional sample-data loader as menu 17)
[x] Input validation for every field and menu choice
[x] Data consistency across Linked List / BST / Hash Table
[x] Clear error and success messages
[x] Clean OOP structure with comments and encapsulation
[x] README with description, features, menu, how-to-run, testing, demo plan
[x] Tested end-to-end (scripted console sessions + 27-assertion unit tests)
[ ] Group member names / IDs / contributions  -> fill in the placeholders
[ ] GitHub repo, branches, commits, PRs       -> to be done by group members
[ ] Demo video (<15 min, all faces visible)   -> to be recorded by the group
[ ] Google Drive + LMS submission             -> to be completed before 29 September
```
