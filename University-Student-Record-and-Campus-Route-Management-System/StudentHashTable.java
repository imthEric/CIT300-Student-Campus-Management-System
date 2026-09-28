/**
 * Custom Hash Table implementation providing efficient Student ID lookups.
 *
 * Mapping:  Student ID (key)  ->  Student record (value)
 *
 * Hashing function: converts the Student ID into a bucket index using a
 * polynomial rolling hash over the characters of the ID, then takes the
 * modulus of the table size.
 *
 * Collision handling: SEPARATE CHAINING - each bucket is the head of a
 * small linked list (HashNode chain) of entries that hashed to the same index.
 */
public class StudentHashTable {

    private static final int TABLE_SIZE = 17;   // prime size gives better distribution

    private HashNode[] buckets;                 // array of bucket heads
    private int count;                          // number of stored entries

    public StudentHashTable() {
        buckets = new HashNode[TABLE_SIZE];
        count = 0;
    }

    /**
     * Hash function: polynomial rolling hash of the Student ID mapped
     * into the range [0, TABLE_SIZE).
     */
    private int hash(String key) {
        int hashValue = 0;
        for (int i = 0; i < key.length(); i++) {
            hashValue = (hashValue * 31 + key.charAt(i)) % TABLE_SIZE;
        }
        return Math.abs(hashValue);
    }

    /** Insert a student into the hash table (replaces existing key). */
    public void put(String studentId, Student student) {
        int index = hash(studentId);
        HashNode node = buckets[index];
        // If the key already exists in this chain, update its value
        while (node != null) {
            if (node.getKey().equalsIgnoreCase(studentId)) {
                node.setValue(student);
                return;
            }
            node = node.getNext();
        }
        // Otherwise chain the new entry at the head of the bucket
        HashNode newNode = new HashNode(studentId, student);
        newNode.setNext(buckets[index]);
        buckets[index] = newNode;
        count++;
    }

    /** Search for a student by ID. Returns null when not present. */
    public Student get(String studentId) {
        int index = hash(studentId);
        HashNode node = buckets[index];
        while (node != null) {
            if (node.getKey().equalsIgnoreCase(studentId)) {
                return node.getValue();
            }
            node = node.getNext();
        }
        return null;
    }

    /** Delete a student entry by ID. Returns true when removed. */
    public boolean remove(String studentId) {
        int index = hash(studentId);
        HashNode node = buckets[index];
        HashNode previous = null;
        while (node != null) {
            if (node.getKey().equalsIgnoreCase(studentId)) {
                if (previous == null) {
                    buckets[index] = node.getNext(); // remove head of chain
                } else {
                    previous.setNext(node.getNext()); // unlink middle/end
                }
                count--;
                return true;
            }
            previous = node;
            node = node.getNext();
        }
        return false;
    }

    public int getCount() { return count; }

    /** Display every bucket and its chain (useful for demonstrating hashing). */
    public void displayAll() {
        System.out.println("==================================================");
        System.out.println("        HASH TABLE CONTENTS (Separate Chaining)");
        System.out.println("==================================================");
        for (int i = 0; i < TABLE_SIZE; i++) {
            HashNode node = buckets[i];
            if (node != null) {
                System.out.print("Bucket " + i + ": ");
                while (node != null) {
                    System.out.print("[" + node.getKey() + "] -> ");
                    node = node.getNext();
                }
                System.out.println("null");
            }
        }
        System.out.println("Entries stored: " + count);
    }
}
