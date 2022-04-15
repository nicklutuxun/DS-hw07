package hw7.hashing;

import hw7.Map;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ChainingHashMap<K, V> implements Map<K, V> {
  private int capacity;
  private int numFilled;
  private int primeIndex;
  private HashNode<K, V>[] hashMap;
  private final int[] primes = {2, 5, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853,
      25717, 51437,102877, 205759, 411527, 823117, 1646237,3292489, 6584983, 13169977};
  
  /**
   * Instantiate ChainingHashMap.
   */
  public ChainingHashMap() {
    this.capacity = 23;
    this.numFilled = 0;
    this.primeIndex = 3;
    this.hashMap = (HashNode<K, V>[]) new HashNode[capacity];
  }

  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    // TODO Implement Me!
    if (k == null || has(k)) {
      throw new IllegalArgumentException();
    }
  
    if (getLoadFactor() > 0.4) {
      rehash();
    }
  
    int index = getHash(k);
    if (isEmpty(index)) {
      hashMap[index] = new HashNode<>(k, v);
    } else {
      // insert into the linked list as head
      HashNode<K, V> newNode = new HashNode<>(k, v);
      newNode.next = hashMap[index];
      hashMap[index].prev = newNode;
      hashMap[index] = newNode;
    }
    numFilled++;
  }
  
  private boolean isEmpty(int index) {
    return hashMap[index] == null;
  }
  
  private void rehash() {
    this.numFilled = 0;
    this.primeIndex++;
    int oldCapacity = capacity;
    this.capacity = primeIndex >= primes.length ? this.capacity * 2 + 1 : primes[primeIndex];
    // allocate temp hashMap
    HashNode<K, V>[] temp = hashMap;
    this.hashMap = (HashNode<K, V>[]) new HashNode[capacity];
    
    // reinsert old hashMap entries
    for (int i = 0; i < oldCapacity; i++) {
      HashNode<K, V> cur = temp[i];
      while (cur != null) {
        insert(cur.key, cur.value);
        cur = cur.next;
      }
    }
  }
  
  private double getLoadFactor() {
    return (double)numFilled / capacity;
  }
  
  @Override
  public V remove(K k) throws IllegalArgumentException {
    // TODO Implement Me!
    if (k == null) {
      throw new IllegalArgumentException();
    }
    HashNode<K, V> node = containsKey(k);
    if (node == null) {
      throw new IllegalArgumentException();
    }
    numFilled--;
    return removeNode(node);
  }
  
  private V removeNode(HashNode<K, V> node) {
    // Only one node
    if (node.prev == null && node.next == null) {
      hashMap[getHash(node.key)] = null;
      return node.value;
    }
  
    // head node
    if (node.prev == null) {
      node.next.prev = null;
      hashMap[getHash(node.key)] = node.next;
      return node.value;
    }
  
    // tail node
    if (node.next == null) {
      node.prev.next = null;
      return node.value;
    }
  
    // has prev and next
    node.prev.next = node.next;
    node.next.prev = node.prev;
    return node.value;
  }
  
  
  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    // TODO Implement Me!
    if (k == null) {
      throw new IllegalArgumentException();
    }
    HashNode<K, V> node = containsKey(k);
    if (node == null) {
      throw new IllegalArgumentException();
    }
    node.value = v;
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    // TODO Implement Me!
    if (k == null) {
      throw new IllegalArgumentException();
    }
    HashNode<K, V> node = containsKey(k);
    if (node == null) {
      throw new IllegalArgumentException();
    }
    return node.value;
  }

  @Override
  public boolean has(K k) {
    // TODO Implement Me!
    if (k == null) {
      return false;
    }
    return containsKey(k) != null;
  }
  
  private HashNode<K, V> containsKey(K k) {
    int index = getHash(k);
    HashNode<K, V> cur = hashMap[index];
    while (cur != null) {
      if (cur.key.equals(k)) {
        return cur;
      }
      cur = cur.next;
    }
    return null;
  }
  
  private int getHash(K k) {
    int index = k.hashCode() % capacity;
    return index < 0 ? -index : index;
  }
  
  @Override
  public int size() {
    // TODO Implement Me!
    return numFilled;
  }

  @Override
  public Iterator<K> iterator() {
    // TODO Implement Me!
    return new ChainingIterator();
  }
  
  private class ChainingIterator implements Iterator<K> {
    HashNode<K, V> cur;
    // index in hashMap
    int mapIndex;
    // index of current node in terms of total filled nodes
    int nodeIndex;
  
    ChainingIterator() {
      cur = hashMap[0];
      this.mapIndex = 0;
      this.nodeIndex = 0;
    }
  
    @Override
    public boolean hasNext() {
      return nodeIndex < numFilled;
    }
  
    @Override
    public K next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      
      while (cur == null) {
        mapIndex++;
        cur = hashMap[mapIndex];
      }
      K key = cur.key;
      cur = cur.next;
      nodeIndex++;
      return key;
    }
  }
  
  private static class HashNode<K, V> {
    K key;
    V value;
    HashNode<K, V> next;
    HashNode<K, V> prev;
  
    HashNode(K k, V v) {
      this.key = k;
      this.value = v;
    }
  }
}
