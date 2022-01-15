import java.util.LinkedList;

public class ChainImpl<K, V> implements HashTable<K, V> {

    private final LinkedList<Object<K, V>>[] data;

    private Object<K, V> emptyObject;

    private int size;

    static class Object<K, V> implements Entry<K, V> {

        private final K key;

        private V value;

        public Object(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public void setValue() {
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Object{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    public ChainImpl(int initialCapacity) {
        this.data = new LinkedList[initialCapacity * 2];
        emptyObject = new Object<>(null, null);
    }

    public ChainImpl() {
        this(16);
    }

    @Override
    public boolean put(K key, V value) {

        if (size() == data.length) {
            return false;
        }

        int index = hashFunc(key);
        int n = 0;
        if (data[index] == null) {
            data[index] = new LinkedList<>();
        }
        data[index].add(new Object<>(key, value));

        size++;
        return true;
    }

    /*

    private boolean isKeysEquals(Item<K,V> item, K key) {
        if(item == emptyItem) {
            return false;
        }
        return (item.getKey() == null) ? (key == null) : item.getKey().equals(key);
    }

     */

    private int hashFunc(K key) {
        return Math.abs(key.hashCode() % data.length);
    }

    @Override
    public V get(K key) {
        int index = indexOf(key);
        return index == -1 ? null : data[index].get(0).getValue();
    }

    private int indexOf(K key) {
        int index = hashFunc(key);

        int count = 0;
        while (count++ < data.length) {
            Object<K, V> object = data[index].get(0);
            if (data[index].get(0) == null) {
                break;
            }
        }

        return -1;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void display() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("----------------------------\n");
        for (int i = 0; i < data.length; i++) {
            stringBuilder.append(String.format("%s = [%s]%n", i, data[i]));
        }
        stringBuilder.append("----------------------------\n");
        return stringBuilder.toString();
    }
}
