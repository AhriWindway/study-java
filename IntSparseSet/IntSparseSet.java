import java.util.*;

public class IntSparseSet extends AbstractSet<Integer> {
    private int n = 0, u, low;
    int[] sparse, dense;
    
    public IntSparseSet(int low, int high) {
        this.low = low;
        u = high - low;
        sparse = new int[u];
        dense = new int[u];
    }
    
    @Override
    public int size() { return n; }
    @Override
    public void clear() { n = 0; }
    @Override
    public Iterator<Integer> iterator() { return new SetIterator(); }
    private class SetIterator implements Iterator<Integer> {
        private int count;
        
        public SetIterator() { count = 0; }
        @Override
        public boolean hasNext() { return (count < n); }
        @Override
        public Integer next() {
                return dense[count++] + low;
        }
        
        @Override
        public void remove() {
            int e = dense[n - 1];
            n--;
            dense[count - 1] = e;
            sparse[e] = count - 1;            
        }
    }
    @Override
    public boolean contains(Object key) {
        int k = (int) key;
        return (((k - low) >= 0) && ((k - low) < u) && (sparse[k - low] < n) && (dense[sparse[k - low]] == k - low));
    }
    
    @Override
    public boolean add(Integer k) {
        if (((k - low) >= 0) && ((k - low) < u) && (sparse[k - low] >= n) | (dense[sparse[k - low]] != k - low)) {
            sparse[k - low] = n;
            dense[n] = k - low;
            n++;
            return true;
        } else return false;
    }
    
    @Override
    public boolean remove(Object key) {
        int k = (int) key;
        if (((k - low) >= 0) && ((k - low) < u) && (sparse[k - low] <= n - 1) && (dense[sparse[k - low]] == k - low)) {
            int e = dense[n - 1];
            n--;
            dense[sparse[k - low]] = e;
            sparse[e] = sparse[k - low];
            return true;
        } else return false;
    }    
}
