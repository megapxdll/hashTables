import java.util.LinkedList;
import java.util.List;

public class HashTableImpl<K, V> implements HashTable<K, V> {

    private final List<Item<K, V>[]>[] list;
    private final Item<K, V>[] data;
    private int size;
    private Item<K, V> emptyItem;

    static class Item<K, V> implements Entry<K, V> {

        private final K key;

        private V value;

        public Item(K key, V value) {
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
            return "Item{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    public HashTableImpl(int initialCapacity) {
        this.list = new LinkedList[initialCapacity * 2];
        this.data = new Item[initialCapacity * 2];
        emptyItem = new Item<>(null, null);
    }

    public HashTableImpl() {
        this(16);
    }

    @Override
    public boolean put(K key, V value) {

        if (size() == data.length) {
           return false;
        }

        int index = hashFunc(key);
        int n = 0;

        /*
        while (data[index] != null && data[index] != emptyItem) {

            if (isKeysEquals(data[index], key)) {
                data[index].setValue(value);
                return true;
            }

            index += getStepDoubleHash(key);
            index += getStepQuadratic(n++);
            index += getStepLinear();
            index %= data.length;

        }
        data[index] = new Item<>(key, value);
        size++;
         */
        if (list[index] != null /*&& list[index] != emptyItem*/) {
            if (isKeysEquals(list[index].get(0), key)) {
                list[index].add(data);
                data[index].setValue();
            }
        }
        list[index] = new LinkedList<>();
        list[index].add(data);
        data[index] = new Item<>(key, value);
        return true;
    }

    private int getStepDoubleHash(K key) {
        return 5 - (key.hashCode() % 5);
    }

    private int getStepQuadratic(int n) {
        return (int) Math.pow(n, 2);
    }

    private int getStepLinear() {
        return 1;
    }

    private boolean isKeysEquals(Item<K,V>[] item, K key) {
        for (int i = 0; i < item.length; i++) {
            if(item[i] == emptyItem) {
                return false;
            }
            return (item[i].getKey() == null) ? (key == null) : item[i].getKey().equals(key);
        }
        return false;
    }

    private int hashFunc(K key) {
        return Math.abs(key.hashCode() % data.length);
    }

    @Override
    public V get(K key) {
        int index = indexOf(key);
        return index == -1 ? null : data[index].getValue();
    }

    private int indexOf(K key) {
        int index = hashFunc(key);

        int count = 0;
        while (count++ < data.length) {
            Item<K, V> item = data[index];
            if (item == null) {
                break;
            }
            if (isKeysEquals(list[index].get(index), key)) {
                return index;
            }

            index += 1;
            index %= data.length;
        }
        return -1;
    }

    @Override
    public V remove(K key) {
        int index = indexOf(key);

        if (index == -1 ) {
            return null;
        }

        Item<K, V> removed = data[index];
        data[index] = emptyItem;

        return removed.getValue();
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
