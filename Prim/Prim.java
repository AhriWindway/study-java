import java.util.*;

public class Prim {
    private static Graph g;
    private static Graph t;
    private static int vertex;
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        vertex = in.nextInt();
        g = new Graph(vertex);
        t = new Graph(vertex);
        
        int edge = in.nextInt();
        for (int i = 0; i < edge; i++) {
            int left = in.nextInt();
            int right = in.nextInt();
            int len = in.nextInt();
            g.add(left, right, len);
            if (left != right) g.add(right, left, len);  
        }
        System.out.println(MST_Prim());
    }
    
    private static int MST_Prim() {
        Queue<Vertex> q = new PriorityQueue<>(vertex);
        int result = 0;
        Vertex v = g.get(0);
        while (true) {
            v.index = -2;
            for (Edge u = v.next; u != null; u = u.next) {
                Vertex uv = g.get(u.to);
                if ((uv.index == -1) && (uv.key == 0)) {
                    uv.key = u.len;
                    uv.to = u.from;
                    if (!q.contains(uv)) q.add(uv);
                }
                else if ((uv.index != -2) && (u.len < uv.key)) {
                    q.remove(uv);
                    uv.to = u.from;
                    uv.key = u.len;
                    q.add(uv);
                }
            }
            if (q.isEmpty()) break;
            v = q.poll();
            result += v.key;
        }     
        return result;
    }    
}

class Vertex implements Comparable<Vertex> {
    protected Edge next = null;
    protected final int value;
    protected int index, key = 0, to;
    public Vertex(int i) { value = i; index = -1; }

    @Override
    public int compareTo(Vertex x) {
            if(this.key < x.key) return -1;
            if(this.key > x.key) return 1;
            return 0;
    }
}

class Edge {
    protected Edge next = null;
    protected final int from, to, len;
    public Edge(int from, int to, int len) {
        this.from = from;
        this.to = to;
        this.len = len;
    }
}

class Graph {    
    private Vertex[] graph;
    private static int vertex;  
    
    public Graph(int vert) {
        graph = new Vertex[vertex = vert];
        for (int i = 0; i < vertex; i++) graph[i] = new Vertex(i);
    }
    
    public Vertex get(int i) {
        return graph[i];
    }
 
    public void add(int left, int right, int len) {
        Edge temp = graph[left].next;
        Edge a = new Edge(left, right, len);
        if (graph[left].next != null) {
            while (temp.next != null) temp = temp.next;
            temp.next = a;
        } 
        else graph[left].next = a;
    }
}
