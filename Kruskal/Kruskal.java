import java.util.*;
import java.text.DecimalFormat;

public class Kruskal {
    private static Element[] forest;
    private static Graph t;
    private static int number;
    private static ArrayList<Edge> e = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        forest = new Element[number = n];
        t = new Graph(n);
        for (int i = 0; i < n; i++) {
            int x = in.nextInt(), y = in.nextInt();
            forest[i] = new Element(i);
            t.setCoord(i, x, y);
        }
        
        for (int i = 0; i < n; i++)
            for (int k = 0; k < n; k++) if (i <= k) e.add(new Edge(i, k));

        MST_Kruskal();
        System.out.println(new DecimalFormat("#0.00").format(t.generateDist()));
    }
  
    private static void MST_Kruskal() {
        Collections.sort(e);
        SpanningTree();
    }
    
    private static void SpanningTree() {
        int i = 0;
        int counter = 0;
        while ((i < number) && (counter < e.size())) {
            Edge temp = e.get(counter);
            counter++;
             
            if (forest[temp.a].find(forest[temp.a]) != (forest[temp.b].find(forest[temp.b]))) {
                t.add(temp.a, temp.b);
                t.add(temp.b, temp.a);
                forest[temp.a].union(forest[temp.b]);
                i++;
            }
        }
    }
}

class Graph {
    class Coordinate {
        int x, y;
        public Coordinate(int a, int b) {
            x = a;
            y = b;
        }
    }
    class Element {
        private Element next = null;
        private Coordinate coord;
        private final int value;
        public Element(int i) { value = i; }
    }
    
    private static Element[] graph;
    private static int vertex;  
    
    public Graph(int vert) {
        graph = new Element[vertex = vert];
        for (int i = 0; i < vertex; i++) graph[i] = new Element(i);
    }
    
    public void setCoord(int i, int a, int b) {
        graph[i].coord = new Coordinate(a, b);
    }
    
    public static double dist(int a, int b) {
        return (Math.sqrt((graph[a].coord.x - graph[b].coord.x)*(graph[a].coord.x - graph[b].coord.x) +
                          (graph[a].coord.y - graph[b].coord.y)*(graph[a].coord.y - graph[b].coord.y)));
    }
    
    public void add(int left, int right) {
        Element temp = graph[left];
        Element a = new Element(right);
        while (temp.next != null) temp = temp.next;
        temp.next = a;
    }
    
    public double generateDist() {
        double dist = 0.0;
        for (int i = 0; i < vertex; i++) {
            for (Element k = graph[i].next; k != null; k = k.next) { 
                if (k.value <= i) dist += dist(i, k.value);
                
            }
        }
        return dist;
    }  
}

class Element { 
    private Element parent;
    private int x;
    private int depth;

    public Element(int a) { 
        this.x = a;
        depth = 0;
        parent = this;
    } 

    public Element find(Element elem) {
        if (elem.parent == elem) return elem;
        else return find(elem.parent);
    }


    public void union(Element elem) { 
         Element rootx = find(this);
         Element rooty = find(elem);

         if (rootx.depth < rooty.depth) rootx.parent = rooty;
         else {
             rooty.parent = rootx;
             if ((rootx.depth == rooty.depth) && (rootx != rooty)) 
                 rootx.depth++;
         }
    }  
}

class Edge implements Comparable<Edge> {
    int a, b; 
    double dist;
    public Edge(int a, int b) {
        this.a = a;
        this.b = b;
        dist = Graph.dist(a, b);
    }

    @Override
    public int compareTo(Edge x) {
        if (this.dist - x.dist > 0) return 1;
        if (this.dist - x.dist < 0) return -1;
        return 0;
    }
} 
