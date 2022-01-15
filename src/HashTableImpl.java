import java.util.LinkedList;

public class HashTableImpl<K, V> implements HashTable<K, V> {

    //private final Item<K, V>[] data;
    private final LinkedList<Item<K, V>>[] data;
    private Item<K, V> item;
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
        this.data = new LinkedList[initialCapacity * 2];
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
        data[index] = new LinkedList<>();
        if (data[index] == null /*&& data[index].get(0) == emptyItem*/) {

            if (isKeysEquals(data[index].get(0), key)) {
                data[index].add(new Item<>(key, value));
                return true;
            }
        }
        /*
        while (data[index] != null && data[index].get(n++) != emptyItem) {

            if (isKeysEquals(data[index], key)) {
                data[index].add(value);
                return true;
            }

            //index += getStepDoubleHash(key);
            //index += getStepQuadratic(n++);
            //index += getStepLinear();
            //index %= data.length;
        }
        data[index].add(new Item<>(key, value));
        size++;
         */
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

    private boolean isKeysEquals(Item<K, V> item, K key) {
        if(item == emptyItem) {
            return false;
        }
        return (item.getKey() == null) ? (key == null) : item.getKey().equals(key);
    }

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
            Item<K, V> item = data[index].get(0);
            if (item == null) {
                break;
            }
            if (isKeysEquals(data[index].get(0), key)) {
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

        Item<K, V> removed = data[index].get(0);
        //data[index].get(0) = emptyItem;

        data[index].remove(0);
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