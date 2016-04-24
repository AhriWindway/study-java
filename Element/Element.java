public class Element<T> { 
    private Element<T> parent;
    private T x;
    private int depth;

    public Element(T x) { 
        this.x = x;
        depth = 0;
        parent = this;
    } 

    public T x() { return x; } 

    public boolean equivalent(Element<T> elem) { 
        return (find(this).parent == find(elem).parent);
    } 

    private Element<T> find(Element<T> elem) {
        if (elem.parent == elem) return elem;
        else return find(elem.parent);
    }

    public void union(Element<T> elem) { 
         Element<T> rootx = find(this);
         Element<T> rooty = find(elem);

         if (rootx.depth < rooty.depth) rootx.parent = rooty;
         else {
             rooty.parent = rootx;
             if ((rootx.depth == rooty.depth) && (rootx != rooty)) 
                 rootx.depth++;
         }
    }
}
