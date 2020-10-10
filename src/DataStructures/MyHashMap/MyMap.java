package DataStructures.MyHashMap;

public interface MyMap<K,V> {
    int size();
    boolean isEmpty();
    boolean containsKey(K key);
    boolean containsValue(V val);

    K getKey(V val);
    V getValue(K key);

    boolean put(K key, V val);

    V remove(K key);
}
