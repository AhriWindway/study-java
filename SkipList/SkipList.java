import java.util.*;

public class SkipList<K extends Comparable<K>,V> extends AbstractMap<K,V> {
    private int n = 0, levels;
    private Element current;
 
    class Element implements Entry<K, V>{
        private K key;
        private V value;
        private Element[] next;
 
        private Element(K key, V value){
            this.key = key;
            this.value = value;
            this.next = new SkipList.Element[levels];
        }
 
        @Override
        public K getKey() { return this.key; }
        @Override
        public V getValue() { return this.value; }
        @Override
        public V setValue(V value) { return this.value = value; }
        public Element[] getNext() { return next; }
    }
    
    public SkipList(int levels){
        this.levels = levels;
        this.current = new Element(null, null);
    }
    
    @Override
    public void clear() {
        this.n = 0;
        this.current = new Element(null, null);
    }
 
    public V remove(K key){
        Element p[] = Skip(key);
        Element help = p[0].next[0];
        V buf = help.value;
        if ((help == null) || (help.key.equals(key) == false)) return null;
        for (int i = 0; i < levels && p[i].next[i] == help; i++)
            p[i].next[i] = help.next[i];
        n--;
        return buf;
    }
 
    @Override
    public Set<Entry<K, V>> entrySet() {
        return new MyEntrySet();
    }
 
    private class MyEntrySet extends AbstractSet{
        @Override
        public Iterator<SkipList<K, V>> iterator(){ return new MyIterator(); }
 
        private class MyIterator implements Iterator {
            private Element i = current;
            
            @Override
            public boolean hasNext() { return i.getNext()[0] != null; }
            @Override
            public Element next() { return i = i.getNext()[0]; }
            @Override
            public void remove(){ SkipList.this.remove(i.getKey()); }
        }
        @Override
        public int size() { return n; }
    }
 
    public Element[] Skip(K key){
        Element[] p = new SkipList.Element[levels];
        Element x = current;
        for(int i = levels - 1; i >= 0; i--){
            while((x.next[i] != null) && (x.next[i].key.compareTo(key) < 0))
                x = x.next[i]; 
            p[i] =  x;
        }
        return p;
    }
 
    @Override
    public V put(K key, V value){
        Element[] p =  Skip(key);
        Element x = new Element(key, value);
        Random random = new Random();
        int i, randdigit = random.nextInt() * 2;
        
        if (p[0].next[0] != null && p[0].next[0].key == key) {
            V help = p[0].next[0].value;
            p[0].next[0].setValue(value);
            return help;
        }
        
        for(i = 0; i < levels && randdigit % 2 == 0; i++, randdigit >>= 2){
            x.next[i] =  p[i].next[i];
            p[i].next[i] = x;
        }
        for (; i < levels; i++) x.next[i] = null;
        n++;
        return null;
    }
}
