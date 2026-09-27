# CIT300 - University Student Record & Campus Route Management System

A comprehensive, high-performance Data Structures & Algorithms Java application designed for **CIT300**. This system integrates custom data structure implementations—without relying on high-level Java collection wrappers—to manage student records, process service desk tickets, maintain undo action history, and navigate campus pathways.

---

## 📁 Folder Structure

```text
CIT300-Student-Campus-Management-System/
│
├── src/
│   ├── Main.java
│   │
│   ├── model/
│   │   ├── Student.java
│   │   └── ServiceRequest.java
│   │
│   └── structures/
│       ├── StudentLinkedList.java
│       ├── ActionStack.java
│       ├── ServiceQueue.java
│       ├── StudentBST.java
│       ├── StudentHashTable.java
│       └── CampusGraph.java
│
├── README.md
└── .gitignore
```

---

## 🚀 Key Features & Data Structures Overview

### 1. `StudentLinkedList.java` (Doubly Linked List)
- **Purpose**: Provides sequential storage and iteration for student records with pointers to both previous and next nodes.
- **Key Operations**: `add()`, `addFirst()`, `remove(studentId)`, `find(studentId)`, `printList()`.
- **Use Case**: Iterating through all registered students in order of registration.

### 2. `StudentBST.java` (Binary Search Tree)
- **Purpose**: Enables sorted storage and search queries organized by Student ID.
- **Key Operations**: `insert()`, `search()`, `delete()`, `getInOrder()`, `getStudentsByGpaRange()`.
- **Use Case**: In-order traversal produces a sorted list of students by ID; range queries filter students by GPA threshold in logarithmic time.

### 3. `StudentHashTable.java` (Hash Table with Separate Chaining)
- **Purpose**: Facilitates constant time $O(1)$ average search, insertion, and deletion by Student ID.
- **Key Operations**: `put()`, `get()`, `remove()`, `containsKey()`, automatic dynamic resizing when load factor exceeds `0.75`.
- **Use Case**: Instant student profile retrieval for administrative staff.

### 4. `ServiceQueue.java` (Priority Queue)
- **Purpose**: Manages student service requests (e.g. Transcript requests, ID card replacements, Financial Aid assistance).
- **Key Operations**: Priority-ordered `enqueue()`, `dequeue()`, `peek()`.
- **Use Case**: Processes urgent student tickets first (Priority 1 = High, 2 = Medium, 3 = Low), maintaining FIFO order for equal priorities.

### 5. `ActionStack.java` (LIFO Action Stack)
- **Purpose**: Records system administrative actions to allow multi-level Undo capability.
- **Key Operations**: `push()`, `pop()`, `peek()`, `printStack()`.
- **Use Case**: Undoing accidental student deletions or service request resolutions.

### 6. `CampusGraph.java` (Weighted Undirected Graph)
- **Purpose**: Models campus buildings (vertices) and walkways/paths (weighted edges with distance in meters).
- **Key Operations**: 
  - **Dijkstra's Algorithm**: `findShortestPath(start, destination)` to calculate the fastest walking route.
  - **BFS Traversal**: `bfsTraversal(start)` for level-by-level campus exploration.
  - **DFS Traversal**: `dfsTraversal(start)` for deep branch exploration.

---

## 📊 Time & Space Complexity Analysis

| Data Structure | Operation / Query | Average Time Complexity | Worst Case Time Complexity | Space Complexity |
|---|---|---|---|---|
| **StudentLinkedList** | Search / Delete by ID | $O(N)$ | $O(N)$ | $O(N)$ |
| **StudentBST** | Search / Insert / Delete | $O(\log N)$ | $O(N)$ | $O(N)$ |
| **StudentHashTable** | Search / Insert / Delete | $O(1)$ | $O(N)$ | $O(N)$ |
| **ServiceQueue** | Enqueue / Dequeue | $O(N)$ / $O(1)$ | $O(N)$ | $O(N)$ |
| **ActionStack** | Push / Pop / Peek | $O(1)$ | $O(1)$ | $O(N)$ |
| **CampusGraph** | Dijkstra Shortest Path | $O((E + V) \log V)$ | $O(V^2)$ | $O(V + E)$ |

---

## 🛠️ Compilation & Execution

### Option A: Standard Terminal / Command Prompt

1. Open terminal in the directory:
   ```bash
   cd CIT300-Student-Campus-Management-System
   ```

2. Compile all Java source files:
   ```bash
   javac -d bin src/model/*.java src/structures/*.java src/Main.java
   ```

3. Run the application:
   ```bash
   java -cp bin Main
   ```

### Option B: Run Automated Diagnostic Test Suite
To run the automated validation test suite non-interactively:
```bash
java -cp bin Main --test
```

---

## 📜 License
Developed for CIT300 Data Structures & Algorithms coursework.
