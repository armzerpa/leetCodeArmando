public class HashTable<K, V> {
    private class Entry {
        K key;
        V value;
        Entry next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Entry[] buckets;
    private int size;
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    @SuppressWarnings("unchecked")
    public HashTable() {
        this.buckets = new Entry[INITIAL_CAPACITY];
        this.size = 0;
    }

    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        if ((double) size / buckets.length >= LOAD_FACTOR) {
            resize();
        }

        int index = getIndex(key);
        Entry entry = buckets[index];

        while (entry != null) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
            entry = entry.next;
        }

        Entry newEntry = new Entry(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = getIndex(key);
        Entry entry = buckets[index];

        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }

        return null;
    }

    public boolean remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = getIndex(key);
        Entry entry = buckets[index];
        Entry prev = null;

        while (entry != null) {
            if (entry.key.equals(key)) {
                if (prev == null) {
                    buckets[index] = entry.next;
                } else {
                    prev.next = entry.next;
                }
                size--;
                return true;
            }
            prev = entry;
            entry = entry.next;
        }

        return false;
    }

    private int getIndex(K key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Entry[] oldBuckets = buckets;
        buckets = new Entry[oldBuckets.length * 2];
        size = 0;

        for (Entry entry : oldBuckets) {
            while (entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}