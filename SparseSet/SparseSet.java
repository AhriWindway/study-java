import java.util.*;

public class SparseSet<T extends Hintable> extends AbstractSet<T> {
    private int n = 0;
    private ArrayList<T> dense = new ArrayList<>();
        
    @Override
    public int size() { return n; }
    @Override
    public void clear() { n = 0; }
    @Override
    public Iterator<T> iterator() { return new SetIterator(); }
    private class SetIterator implements Iterator<T> {
        private int count;
        
        public SetIterator() { count = 0; }
        @Override
        public boolean hasNext() { return (count < n); }
        @Override
        public T next() { return dense.get(count++); }       
        @Override
        public void remove() {
            dense.get(n - 1).setHint(dense.get(count - 1).hint());
            dense.set(dense.get(count - 1).hint(), dense.get(n - 1));
            n--;
        }
    }
    @Override
    public boolean contains(Object key) {
        return (!dense.isEmpty()) && (key == dense.get(((T)key).hint()));
    }
    
    @Override
    public boolean add(T key) {
        if (!contains(key)) {
            dense.add(key);
            key.setHint(n);
            n++;
            return true;
        } else return false;
    }
    
    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T x: c) {
            if (!add(x)) return false;
        }
        return true;
    }
    
    @Override
    public boolean remove(Object key) {
        if ((n > 0) && (contains((T)key))) {
            dense.get(n - 1).setHint(((T)key).hint());
            dense.set(((T)key).hint(), dense.get(n - 1));
            n--;
            return true;
        } else return false;
    }    
}
