import java.util.*;

public class GraphBase {
    private static Graph g, gt, condencated;
    private static ArrayList<Integer> order = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(), m = in.nextInt(), component = 0;
        g = new Graph(n);
        gt = new Graph(n);
        
        for (int i = 0; i < m; i++) {
            int from = in.nextInt(), to = in.nextInt();
            g.add(from, to);
            gt.add(to, from);
        }
        
        for (int i = 0; i < n; i++) 
            if (g.get(i).index == -1) dfs1(i);
        
        for (int i = order.size() - 1; i >= 0; i--) {
            int temp = order.get(i);
            if (g.get(temp).component == -1) {
                dfs2(temp, component);
                component++;
            }
        }
        
        condencated = new Graph(component);
        createCond();
        order.clear();
        for (int i = 0; i < condencated.size(); i++) {
            if (condencated.get(i).index == -1) {
                for (int k = 0; k < g.size(); k++) {
                    if (g.get(k).component == i) {
                        order.add(k);
                        break;
                    }
                }
            }
        }
        
        Collections.sort(order);
        for (int i = 0; i < order.size(); i++) System.out.print(order.get(i) + " ");
        System.out.println();
    }
    
    private static void dfs1(int i) {
        g.get(i).index = 0;
        for (Arc x = g.get(i).next; x != null; x = x.next) 
            if (g.get(x.to).index == -1) dfs1(x.to);
        order.add(i);
    }
    
    private static void dfs2(int i, int component) {
        g.get(i).component = component;
        for (Arc x = gt.get(i).next; x != null; x = x.next) 
            if (g.get(x.to).component == -1) dfs2(x.to, component);        
    }
    
    private static void createCond() {
        for (int i = 0; i < g.size(); i++) {
            for (Arc x = g.get(i).next; x != null; x = x.next) {
                int from = g.get(i).component, to = g.get(x.to).component;
                if (from != to) {
                    condencated.add(from, to);
                    condencated.get(to).index = 0;
                }
            }
        }        
    }
}

class Vertex {
    protected Arc next = null;
    protected final int value;
    protected int index, component = -1;
    public Vertex(int i) { value = i; index = -1; }
}

class Arc {
    protected Arc next = null;
    protected final int from, to;
    
    public Arc(int from, int to) {
        this.from = from;
        this.to = to;
    }
}

class Graph {    
    private Vertex[] graph;
    private int vertex;  
    
    public Graph(int vert) {
        graph = new Vertex[vertex = vert];
        for (int i = 0; i < vertex; i++) graph[i] = new Vertex(i);
    }
    
    public Vertex get(int i) { return graph[i]; }
    public int size() { return vertex; }
 
    public void add(int from, int to) {
        Arc temp = graph[from].next;
        Arc a = new Arc(from, to);
        if (graph[from].next != null) {
            while (temp.next != null) temp = temp.next;
            temp.next = a;
        } 
        else graph[from].next = a;
    }
}
