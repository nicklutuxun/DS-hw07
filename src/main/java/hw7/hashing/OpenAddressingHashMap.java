package hw7.hashing;

import hw7.Map;
import java.util.Iterator;

public class OpenAddressingHashMap<K, V> implements Map<K, V> {
  // number of valid element (exclude Tombstones)
  private int numValid;
  // actual size of hashMap
  private int capacity;
  // number of total element (include Tombstones)
  private int numFilled;
  private Element<K, V>[] hashMap;
  
  public OpenAddressingHashMap() {
    this.numValid = 0;
    this.capacity = 29;
    this.numFilled = 0;
    hashMap = (Element<K, V>[]) new Element[capacity];
  }
  
  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    // TODO Implement Me!
    if (k == null || has(k)) {
      throw new IllegalArgumentException();
    }
    
    int index = getHash(k);
    if (isEmpty(index)) {
      hashMap[index] = new Element<>(k, v);
      numFilled++;
      numValid++;
    } else {
      // Find next available position in the hashMap using probing strategy
      probing(k, v);
    }
    
  }
  
  private int getHash(K k) {
    return k.hashCode() % capacity;
  }
  
  private void probing(K k, V v) {
  
  }
  
  private boolean isEmpty(int index) {
    return hashMap[index] == null;
  }
  
  @Override
  public V remove(K k) throws IllegalArgumentException {
    // TODO Implement Me!
    return null;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    // TODO Implement Me!
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    // TODO Implement Me!
    return containsKey(k).value;
  }

  @Override
  public boolean has(K k) {
    // TODO Implement Me!
    if (k == null) {
      return false;
    }
    return containsKey(k) != null;
  }

  @Override
  public int size() {
    return numValid;
  }
  
  private Element<K, V> containsKey(K k) {
    int index = getHash(k);
    for (int i = index; i < capacity; i++) {
      if (hashMap[i] == null) {
        return null;
      }
      if (hashMap[i].key.equals(k) && !hashMap[i].isTombStone) {
        return hashMap[i];
      }
    }
    return null;
  }

  @Override
  public Iterator<K> iterator() {
    // TODO Implement Me!
    return null;
  }
  
  private static class Element<K, V> {
    K key;
    V value;
    boolean isTombStone;
    
    private Element(K k, V v) {
      this.key = k;
      this.value = v;
      this.isTombStone = false;
    }
  }
}
