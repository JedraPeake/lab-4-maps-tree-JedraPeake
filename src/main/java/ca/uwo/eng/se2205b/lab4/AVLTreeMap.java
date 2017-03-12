package ca.uwo.eng.se2205b.lab4;

import java.util.*;


/**
 * Created by PeakeAndSons on 2017-03-06.
 */
public class AVLTreeMap<K extends Comparable<K>,V> extends AbstractMap<K,V> {

    private AVLTree<Entry<K,V>> tree;
    Hashtable<K,V> storage = new Hashtable<K,V>();

    public AVLTreeMap() {
        tree = new AVLTree<Entry<K, V>>();
    }
    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        storage.putAll(m);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Collection<V> values() {
        return storage.values();
    }

    @Override
    public boolean containsValue(Object value) {
        return storage.containsValue(value);
    }

    @Override
    public boolean containsKey(Object key) {
        return storage.containsKey(key);
    }
    @Override
    public V get(Object key) {
        return storage.get(key);
    }

    @Override
    public V put(K key, V value) {
        if(value == null){
            return null;
        }
        return storage.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return storage.remove(key);
    }

    @Override
    public boolean equals(Object o) {
        return storage.equals(o);
    }

    @Override
    public int hashCode() {
        return storage.hashCode();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return storage.entrySet();
    }


//    @Override
//    public Set<Map.Entry<K, V>> entrySet() {
//        return null;
//    }
//
//    @Override
//    public boolean equals(Object o){
//        return tree.equals(o);
//    }
//    @Override
//    public int hashCode(){
//        return tree.hashCode();
//    }
    //    Set<K> keys;
//    Collection<V> values;
//
//    final class MyEntry<K, V> implements Map.Entry<K, V> {
//        private final K key;
//        private V value;
//        public MyEntry(K key, V value) {
//            this.key = key;
//            this.value = value;
//        }
//        @Override
//        public K getKey() {
//            return key;
//        }
//        @Override
//        public V getValue() {
//            return value;
//        }
//        @Override
//        public V setValue(V value) {
//            V old = this.value;
//            this.value = value;
//            return old;
//        }
//    }
//
//    private static final Comparator<Comparable> NATURAL_ORDER = new Comparator<Comparable>() {
//        public int compare(Comparable a, Comparable b) {
//            return a.compareTo(b);
//        }
//    };
//
//    private Comparator<? super K> comparator;
//    private AVLTree<Map.Entry<K,V>> tree = new AVLTree();
//
//    public int size(){
//        return tree.size();
//    }
//
//    public V get(Object key){
//        MyEntry<K, V> entry = findByObject(key);
//        return entry != null ? entry.getValue() : null;
//    }
//
//    public V put(K key, V value){
//        if(key == null){
//            throw new AssertionFailedError("put(null, value)");
//        }
//        if(key ==""){
//            return null;
//        }
//        tree.put(value);
//        return value;
//    }
//
//    public V remove(Object key){
//        tree.remove(key);
//
//    }
//
//    public boolean containsKey(Object key){
//        return tree.contains((V) key);
//    }
//
}
