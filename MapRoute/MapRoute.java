import java.util.*;

public class MapRoute {
    private static Graph g;
    private static int[][] lens;
    private static PriorityQueue q;
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        g = new Graph(n * n);
        lens = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) lens[i][k] = in.nextInt(); 
        }
        
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                int len = lens[i][k];
                int num = i * n + k;
                if (i > 0) g.add((i - 1) * n + k, num, len);
                if (k > 0) g.add(i * n + (k - 1), num, len);
                if (i + 1 < n) g.add((i + 1) * n + k, num, len);
                if (k + 1 < n) g.add(i * n + (k + 1), num, len);
            } 
        }
        Dijkstra();
    }
    
    private static void Dijkstra() {
        g.get(0).dist = 0;
        g.get(0).parent = null;
        q = new PriorityQueue();
        q.add(g.graph[0]);
        
        while (!q.isEmpty()) {
            Vertex v = (Vertex) q.poll();
            v.index = -1;
            for (Arc temp = v.next; temp != null; temp = temp.next) 
                if (g.get(temp.to).index != -1) relax(g.get(temp.to), v, temp.len);   
        }
        System.out.println(g.get(g.size() - 1).dist + lens[0][0]);
    }
    
    private static boolean relax(Vertex v, Vertex u, int w) {
        boolean changed = (!(u.dist + w < 0)) && (u.dist + w < v.dist);
        if (changed) {
            v.dist = u.dist + w;
            v.parent = u;
            q.add(v);
        }
        return changed;
    }
}

class Vertex implements Comparable<Vertex> {
    protected Arc next = null;
    protected final int value;
    protected int dist = Integer.MAX_VALUE;
    protected Vertex parent = null;
    protected int index = 0;
    public Vertex(int i) { value = i; }

    @Override
    public int compareTo(Vertex x) {
        if (this.dist == Integer.MAX_VALUE) return 1;
        if (x.dist == Integer.MIN_VALUE) return -1;
        return this.dist - x.dist;
    }
}

class Arc {
    protected Arc next = null;
    protected final int from, to, len;
    
    public Arc(int from, int to, int len) {
        this.from = from;
        this.to = to;
        this.len = len;
    }
}

class Graph {    
    protected Vertex[] graph;
    private int vertex;  
    protected ArrayList<String> order = new ArrayList<>();
    
    public Graph(int vert) {
        graph = new Vertex[vertex = vert];
        for (int i = 0; i < vertex; i++) graph[i] = new Vertex(i);
    }
    
    public Vertex get(int i) { return graph[i]; }
    public int size() { return vertex; }
 
    public void add(int from, int to, int len) {
        Arc temp = graph[from].next;
        Arc a = new Arc(from, to, len);
        if (graph[from].next != null) {
            
            while (temp.next != null) {
                if ((temp.to == to) && (temp.from == from)) return;
                temp = temp.next;
            }
            if ((temp.to == to) && (temp.from == from)) return;
            temp.next = a;
        } 
        else graph[from].next = a;
    }
}
