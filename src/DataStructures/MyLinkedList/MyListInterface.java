package DataStructures.MyLinkedList;

public interface MyListInterface<V> {
    boolean set(int index, V value);
    int size();
    boolean contains(V value);
    boolean isEmpty();
    boolean add(V value);
    boolean add(int index, V value);
    void clear();
    V get(int index);
}
