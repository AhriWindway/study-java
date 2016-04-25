import java.util.*;

public class EqDist {
    private static Graph g;
    private static int[][] dist;
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        g = new Graph(in.nextInt());
        
        int edge = in.nextInt();
        for (int i = 0; i < edge; i++) {
            int left = in.nextInt();
            int right = in.nextInt();
            g.add(left, right);
        }
        
        int k = in.nextInt();
        dist = new int[k][g.getVert()];
        for (int i = 0; i < k; i++) {
            Arrays.fill(dist[i], -1);
            int v = in.nextInt();
            g.bfs(dist[i], v);
        }
   
        List<Integer> result = new ArrayList<>();
        
        loop:
        for (int i = 0; i < g.getVert(); i++) {
            for (int j = 1; j < k; j++) {
                if (dist[j][i] == -1 || dist[j][i] != dist[j - 1][i]) continue loop;
            }
            result.add(i);
        }
        if (result.isEmpty()) System.out.println("-");
        else for (int x: result) System.out.print(x + " ");
        System.out.println();
    }
}

class Graph {
    class Element {
        private Element next;
        private final int value;
        public Element(int i) { value = i; next = null;}
    }
    private static Element[] graph;
    private final int vertex;  

    public Graph(int vert) {
        graph = new Element[vertex = vert];
        for (int i = 0; i < vertex; i++) graph[i] = new Element(i);
    }
    
    public int getVert() { return vertex; }
    
    public void bfs(int[] dist, int v) {
        Deque<Integer> deque = new ArrayDeque<>();
        boolean[] used = new boolean[vertex];
        Arrays.fill(used, false);        
        deque.add(v);
        used[v] = true;
        dist[v] = 0;
        
        while (!deque.isEmpty()) {
            int x = deque.poll();
            for (Element u = graph[x].next; u != null; u = u.next) {
                if (!used[u.value]) {
                    used[u.value] = true;
                    deque.add(u.value);
                    dist[u.value] = dist[x] + 1;
                }
            }
        }
    }
    
    public void add(int left, int right) {
        Element temp = graph[left];
        Element a = new Element(right);
        while (temp.next != null) temp = temp.next;
        temp.next = a;
        
        temp = graph[right];
        a = new Element(left);
        while (temp.next != null) temp = temp.next;
        temp.next = a;   
    }
}
