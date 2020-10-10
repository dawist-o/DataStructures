package DataStructures.MyLinkedList;

import DataStructures.MyDeque.MyDeque;
import DataStructures.MyExceptions.EmptyListException;
import DataStructures.MyExceptions.IncorrectInputArraySizeException;

import java.util.*;
import java.util.function.Consumer;

public class MyLinkedList<V extends Comparable<V>> implements MyListInterface<V>, MyDeque<V> {
    private int size;
    private Node first;
    private Node last;


    public void toArray(V[] array) {
        if (array.length < this.size)
            throw new IncorrectInputArraySizeException("Input array size is less then list size");
        if (this.size == 0)
            throw new EmptyListException("List size is empty");
        else {
            Node current = first;
            int index = 0;
            while (current.next != null) {
                array[index] = current.value;
                current = current.next;
                index++;
            }
            array[index] = current.value;
        }
    }

    @Override
    public boolean contains(V value) {
        if (size == 0)
            return false;
        boolean contains = false;
        Node current = first;
        while (current != last) {
            if (current.value == value) {
                contains = true;
                break;
            }
            current = current.next;
        }
        current = current.next;
        return contains ? contains : current.value == value;
    }

    public boolean set(int index, V value) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index " + index + " is out of bound for lenght " + size);
        if (size == 0)
            throw new EmptyListException("Can't set value. List is empty");
        if (index == 0) {
            first.value = value;
            return true;
        }
        if (index == size - 1) {
            last.value = value;
            return true;
        }
        Node current = first;
        for (int currentIndex = 0; currentIndex < index; currentIndex++) {
            current = current.next;
        }
        current.value = value;
        return true;
    }

    @Override
    public boolean add(V value) {
        this.addLast(value);
        return true;
    }

    @Override
    public boolean add(int index, V value) {
        //if index is less then zero or more then list size
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        //if index is zero then add element into first position
        if (index == 0) {
            addFirst(value);
            return true;
        }
        //if index is (size-1) then add element into last position
        if (index == size - 1) return add(value);
        Node current = first;
        for (int currentIndex = 0; currentIndex < index; currentIndex++) {
            current = current.next;
        }
        Node newNode = new Node(value);
        //connecting new node to both sides
        //current(index) will be moved to left side
        newNode.next = current;
        newNode.prev = current.prev;
        //connecting sides elements to new node
        current.prev.next = newNode;
        current.prev = newNode;
        size++;
        return true;
    }

    @Override
    public void addLast(V value) {
        Node newNode = new Node(value);
        if (first == null)
            first = newNode;
        else {
            last.next = newNode;
            newNode.prev = last;
        }
        last = newNode;
        size++;
    }

    @Override
    public void addFirst(V value) {
        Node newNode = new Node(value);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            first.prev = newNode;
            newNode.next = first;
            first = newNode;
        }
        size++;
    }

    public V removeAt(int index) {
        if (size == 0) throw new EmptyListException("List is empty");
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        if (index == 0) return removeFirst();
        if (index == size - 1) return removeLast();
        Node current = first;
        for (int currentIndex = 0; currentIndex < index; currentIndex++) {
            current = current.next;
        }
        V val = current.value;
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
        return val;
    }

    @Override
    public V removeLast() {
        if (size == 0) throw new EmptyListException("List is empty");
        if (size == 1) {
            V val = last.value;
            this.clear();
            return val;
        }
        V val = last.value;
        last = last.prev;
        last.next = null;
        size--;
        return val;
    }

    @Override
    public V removeFirst() {
        if (size == 0) throw new EmptyListException("List is empty");
        if (size == 1) {
            V val = first.value;
            this.clear();
            return val;
        }
        V val = first.value;
        first = first.next;
        first.prev = null;
        size--;
        return val;
    }


    @Override
    public void clear() {
        first = null;
        last = null;
        System.gc();
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public V get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        if (index == 0) return first.value;
        if (index == size - 1) return last.value;
        Node current = first;
        for (int currentIndex = 0; currentIndex < index; currentIndex++) {
            current = current.next;
        }
        return current.value;
    }

    public V getLast() {
        if (last != null) return last.value;
        throw new NoSuchElementException();
    }

    public V getFirst() {
        if (first != null) return first.value;
        throw new NoSuchElementException();
    }

    public void sort() {
        if (size == 1 || size == 0) return;
        LinkedList<V> listForSort = new LinkedList<>();
        Node current = first;
        while (current.next != null) {
            listForSort.add(current.value);
            current = current.next;
        }
        listForSort.add(current.value);
        Collections.sort(listForSort);
        current = first;
        for (V sortedValue : listForSort) {
            current.value = sortedValue;
            current = current.next;
        }
    }

    @Override
    public String toString() {
        if (size == 0) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        Node current = first;
        while (current.next != null) {
            sb.append(current.value);
            current = current.next;
            sb.append(',').append(' ');
        }
        sb.append(current.value).append(']');
        return sb.toString();

    }

    @Override
    public void addAllLast(MyDeque<V> values) {
        for (V value : values) {
            this.addLast(value);
        }
    }

    @Override
    public Iterator iterator() {
        return new MyListIterator();
    }

    private class MyListIterator implements Iterator<V> {
        private Node current;

        @Override
        public boolean hasNext() {
            if (current == null) {
                current = first;
            } else {
                current = current.next;
            }
            return Optional.ofNullable(current).isPresent();
        }

        public MyListIterator() {
        }

        @Override
        public V next() {
            return current.value;
        }
    }

    public class Node {
        private Node next;
        private Node prev;
        private V value;

        public Node(V value) {
            this.value = value;
        }
    }
}
