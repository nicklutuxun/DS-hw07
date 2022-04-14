package hw7.hashing;

import hw7.Map;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OpenAddressingHashMap<K, V> implements Map<K, V> {
  // number of valid element (exclude Tombstones)
  private int numValid;
  // actual size of hashMap
  private int capacity;
  // number of total element (include Tombstones)
  private int numFilled;
  private int primeIndex;
  private final int[] primes = {2, 5, 11, 23, 47, 97, 197, 397, 797, 1597, 3203, 6421, 12853,
      25717, 51437,102877, 205759, 411527, 823117, 1646237,3292489, 6584983, 13169977};
  private Element<K, V>[] hashMap;
  
  /**
   * Instantiate OpenAddressingHashMap.
   */
  public OpenAddressingHashMap() {
    this.numValid = 0;
    this.capacity = 23;
    this.numFilled = 0;
    this.primeIndex = 3;
    hashMap = (Element<K, V>[]) new Element[capacity];
  }
  
  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    // TODO Implement Me!
    if (k == null || has(k)) {
      throw new IllegalArgumentException();
    }
    
    if (getLoadFactor() > 0.75) {
      rehash();
    }
    
    int index = getHash(k);
    if (isEmpty(index)) {
      hashMap[index] = new Element<>(k, v);
      numFilled++;
      numValid++;
    } else {
      // Find next available position in the hashMap using probing strategy
      probing(k, v, index);
    }
  }
  
  private void rehash() {
    this.numValid = 0;
    this.numFilled = 0;
    this.primeIndex++;
    int oldCapacity = capacity;
    this.capacity = primeIndex > primes.length ? this.capacity * 2 : primes[primeIndex];
    // allocate new hashMap
    Element<K, V>[] temp = hashMap;
    this.hashMap = (Element<K, V>[]) new Element[capacity];
    
    // reinsert old hashMap entries
    for (int i = 0; i < oldCapacity; i++) {
      if (temp[i] != null && !temp[i].isTombStone) {
        insert(temp[i].key, temp[i].value);
      }
    }
  }
  
  private double getLoadFactor() {
    return (double)numFilled / capacity;
  }
  
  private int getHash(K k) {
    return k.hashCode() % capacity;
  }
  
  private void probing(K k, V v, int start) {
    for (int i = start; i < capacity; i++) {
      if (hashMap[i] == null) {
        hashMap[i] = new Element<>(k, v);
        numFilled++;
        numValid++;
        break;
      } else if (hashMap[i].isTombStone) {
        hashMap[i] = new Element<>(k, v);
        numValid++;
        break;
      }
    }
  }
  
  private boolean isEmpty(int index) {
    return hashMap[index] == null;
  }
  
  @Override
  public V remove(K k) throws IllegalArgumentException {
    // TODO Implement Me!
    if (k == null) {
      throw new IllegalArgumentException();
    }
    Element<K, V> element = containsKey(k);
    if (element == null) {
      throw new IllegalArgumentException();
    }
    element.isTombStone = true;
    numValid--;
    return element.value;
  }

  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    // TODO Implement Me!
    if (k == null) {
      throw new IllegalArgumentException();
    }
    
    Element<K, V> element = containsKey(k);
    if (element == null) {
      throw new IllegalArgumentException();
    }
    element.value = v;
  }

  @Override
  public V get(K k) throws IllegalArgumentException {
    // TODO Implement Me!
    if (k == null) {
      throw new IllegalArgumentException();
    }
    Element<K, V> element = containsKey(k);
    if (element == null) {
      throw new IllegalArgumentException();
    }
    return element.value;
  }

  @Override
  public boolean has(K k) {
    // TODO Implement Me!
    if (k == null) {
      throw new IllegalArgumentException();
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
    return new OpenAddressingIterator();
  }
  
  private class OpenAddressingIterator implements Iterator<K> {
    int curValid;
    int index;
  
    OpenAddressingIterator() {
      this.curValid = 0;
      this.index = 0;
    }
  
    @Override
    public boolean hasNext() {
      return curValid < numValid;
    }
  
    @Override
    public K next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      while (true) {
        if (hashMap[index] != null && !hashMap[index].isTombStone) {
          curValid++;
          return hashMap[index++].key;
        }
        index++;
      }
    }
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
