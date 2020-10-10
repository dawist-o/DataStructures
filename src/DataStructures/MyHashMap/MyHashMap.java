package DataStructures.MyHashMap;

public class MyHashMap<K, V> implements MyMap<K, V> {
    private MyHashMap.Node<K, V>[] table;
    private int size = 0;
    private int capacity = 10;
    private int threshold = capacity;
    private final float loadFactor = 0.75F;

    public MyHashMap() {
        table = new MyHashMap.Node[capacity];
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
    public V getValue(K key) {
        int index = indexFor(myHashCode(key));
        if (table[index] == null)
            return null;
        Node<K, V> current = table[index];
        while (current != null) {
            if (current.key == key) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public boolean put(K key, V val) {
        this.size++;
        resize(this.size);
        Node<K, V> node = new Node<>(key, val);
        int index = indexFor(myHashCode(key));
        if (table[index] == null)
            table[index] = node;
        else
            checkCollision(index, key, val);
        return true;
    }

    private void resize(int newSize) {
        if (newSize < threshold)
            return;
        this.capacity = this.capacity << 1;
        threshold = (int) (this.capacity * loadFactor);
        this.size = 0;
        MyHashMap.Node<K, V>[] oldTable = this.table;
        this.table = new MyHashMap.Node[this.capacity];
        for (Node<K, V> oldNode : oldTable) {
            if (oldNode != null) {
                Node<K, V> current = oldNode;
                while (current != null) {
                    this.put(current.key, current.value);
                    current = current.next;
                }
            }
        }
        this.size = newSize;
        System.gc();
    }

    private void checkCollision(int index, K key, V val) {
        if (table[index].key == key) {
            table[index].value = val;
        } else {
            MyHashMap.Node<K, V> current = table[index];
            while (current.next != null) {
                if (current.key == key) {
                    current.value = val;
                    return;
                }
                current = current.next;
            }
            current.next = new Node<>(key, val);
        }
    }

    private int myHashCode(K key) {
        if (key == null)
            return 0;
        int hash = 0;
        int pos = 1;
        String key_str = key.toString();
        for (char ch : key_str.toCharArray()) {
            hash += (int) ch * pos;
            pos++;
        }
        return hash >> 2;
    }

    private int indexFor(int h) {
        return h & (this.capacity - 1);
    }

    @Override
    public V remove(K key) {
        int index = indexFor(myHashCode(key));

        if (table[index] == null) return null;

        V val;
        if (table[index].key == key) {
            val = table[index].value;
            table[index] = table[index].next;
            this.size--;
            return val;
        }

        MyHashMap.Node<K, V> current = table[index];
        while (current.next.key != key) {
            current = current.next;
            if (current.next == null)
                return null;
        }
        val = current.next.value;
        current.next = current.next.next;
        this.size--;

        return val;
    }

    @Override
    public boolean containsKey(K key) {
        for (Node<K, V> node : this.table) {
            if (node != null) {
                MyHashMap.Node<K, V> current = node;
                while (current != null) {
                    if (current.key == key)
                        return true;
                    current = current.next;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(V val) {
        for (Node<K, V> node : this.table) {
            if (node != null) {
                MyHashMap.Node<K, V> current = node;
                while (current != null) {
                    if (current.value == val)
                        return true;
                    current = current.next;
                }
            }
        }
        return false;
    }

    @Override
    public K getKey(V val) {
        return null;
    }

    @Override
    public String toString() {
        if (this.size == 0)
            return "[]";
        StringBuilder sb = new StringBuilder();
        for (Node<K, V> node : this.table) {
            if (node != null) {
                MyHashMap.Node<K, V> current = node;
                while (current != null) {
                    sb.append("{ ").append(current.key).append(", ").append(current.value).append(" }");
                    current = current.next;
                    sb.append(',').append(' ');
                }
            }
        }
        return sb.toString();
    }

    private static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
