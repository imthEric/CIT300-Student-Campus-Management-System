/**
 * Entry node used inside each bucket of the custom Hash Table.
 * The hash table uses SEPARATE CHAINING for collision handling, so every
 * bucket holds a small linked list of HashNode entries.
 */
public class HashNode {

    private String key;              // Student ID
    private Student value;           // Student record
    private HashNode next;           // next entry sharing the same bucket (collision chain)

    public HashNode(String key, Student value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    public String getKey()              { return key; }
    public Student getValue()           { return value; }
    public void setValue(Student value) { this.value = value; }

    public HashNode getNext()               { return next; }
    public void setNext(HashNode next)      { this.next = next; }
}
