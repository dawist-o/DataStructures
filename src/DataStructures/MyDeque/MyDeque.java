package DataStructures.MyDeque;

import DataStructures.MyLinkedList.MyLinkedList;

import java.util.Deque;
import java.util.Iterator;

public interface MyDeque<V> extends Iterable<V> {
    int size();
    boolean isEmpty();

    void addFirst(V value);
    void addLast(V value);

    V removeFirst();
    V removeLast();

    V getLast();
    V getFirst();

    boolean add(V value);

    void addAllLast(MyDeque<V> deque);
}


