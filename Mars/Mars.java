import java.util.*;

public class Mars {
    private static Graph g;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(), mark = 1, component = 0;
        g = new Graph(n);
        ArrayList<Integer> free = new ArrayList<>();
        
        for (int i = 0; i < n; i++) 
            for (int k = 0; k < n; k++) if (in.next().equals("+")) g.add(i, k);
        for (int i = 0; i < n; i++) {
            Vertex v = g.get(i);
            if (v.mark == 0) {
                if (v.next != null) {
                    if (!g.bfs(i, mark, component)) {
                        System.out.println("No solution");
                        return;
                    } else {
                        mark+=2;
                        component++;
                    }
                }
                else free.add(i);
            }  
        }
        ArrayList<ArrayList<Integer>> first = new ArrayList<>();
        for (int k = 1; k < mark; k+=2) {
            ArrayList<Integer> buf1 = new ArrayList<>(), buf2 = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                Vertex v = g.get(i);
                if (v.mark == k) buf1.add(i);
                if (v.mark == k + 1) buf2.add(i);
            }
            first.add(buf1);
            first.add(buf2);
        }
        
        ArrayList<ArrayList<Integer>> results = new ArrayList<>();

        int p = 1 << mark;
        for (int i = 1; i < p; i++) {
        	Element a = new Element();
		int k = i;
                boolean good = true;
                for (int j = 0; j < mark - 1; j++) {
			if ((k & 1) == 1) {
                            for (int t: a.s) {
                                if (g.get(t).comp == g.get(first.get(j).get(0)).comp) {
                                    good = false;
                                    break;
                                }
                            }
                            if (!good) break;
                            for (int t: first.get(j)) a.s.add(t);
                            a.number++;
                        }
			k >>= 1;
		}
                Collections.sort(a.s);
                if ((a.number == mark/2) && (!results.contains(a.s))) { 
                    if (good) results.add(a.s);
                }
	}
        if (free.size() == n) {
            int count = 0;
            while (count < n/2) System.out.println(free.get(count++) + 1 + " ");
            System.out.println();
            return;
        } 
        for (ArrayList<Integer> t: results) {
            int count = 0;
            while ((t.size() < n/2) && (t.size() < n - t.size())) {
                t.add(free.get(count));
                count++;
            }
        }
        int best = Integer.MAX_VALUE;
        ArrayList<Integer> numbers = new ArrayList<>();
        for (ArrayList<Integer> t: results) {
            if (Math.abs(n - 2 * t.size()) < best) best = n - 2 * t.size();
            Collections.sort(t);          
        }

        for (int i = 0; i < results.size(); i++) if (Math.abs(n - 2 * results.get(i).size()) == best) numbers.add(i);
        if (numbers.size() == 1) for (int i: results.get(numbers.get(0))) System.out.print(i + 1 + " ");
        else {
            int min = numbers.get(0);
            for (int i = 0; i < numbers.size(); i++) {
                if (results.get(numbers.get(i)).size() == results.get(min).size()) {    
                    for (int j = 0; j < results.get(numbers.get(i)).size(); j++) {
                        ArrayList<Integer> a = results.get(numbers.get(i)), b = results.get(min);
                        if (a.get(j) == b.get(j)) continue;
                        if (a.get(j) > b.get(j)) break;
                        if (a.get(j) < b.get(j)) min = numbers.get(i);
                    }
                } 
                else if (results.get(numbers.get(i)).size() < results.get(min).size()) min = numbers.get(i);
            }    
            for (int i: results.get(min)) System.out.print(i + 1 + " ");
        }
        
        System.out.println();
    }
}

class Vertex {
    protected Edge next = null;
    protected final int value;
    protected int mark = 0, comp = -1;
    public Vertex(int i) { value = i; }
}

class Edge {
    protected Edge next = null;
    protected final int from, to;
    public Edge(int from, int to) {
        this.from = from;
        this.to = to;
    }
}

class Element{
    ArrayList<Integer> s = new ArrayList<>();
    protected int number = 0;
}

class Graph {    
    private Vertex[] graph;
    private static int vertex;  
    
    public Graph(int vert) {
        graph = new Vertex[vertex = vert];
        for (int i = 0; i < vertex; i++) graph[i] = new Vertex(i);
    }
    
    public Vertex get(int i) { return graph[i]; }
 
    public void add(int left, int right) {
        Edge temp = graph[left].next;
        Edge a = new Edge(left, right);
        if (graph[left].next != null) {
            while (temp.next != null) temp = temp.next;
            temp.next = a;
        } 
        else graph[left].next = a;
    }
    
    public boolean bfs(int v, int m, int c) {
        Deque<Integer> deque = new ArrayDeque<>();
        boolean[] used = new boolean[vertex];
        Arrays.fill(used, false);        
        deque.add(v);
        graph[v].mark = m;
        used[v] = true;
        
        while (!deque.isEmpty()) {
            int x = deque.poll();
            graph[x].comp = c;
            for (Edge u = graph[x].next; u != null; u = u.next) {
                if (!used[u.to]) {
                    used[u.to] = true;
                    if (graph[x].mark % 2 == 0) graph[u.to].mark = graph[x].mark - 1;
                    else graph[u.to].mark = graph[x].mark + 1;
                    deque.add(u.to);
                }
                if (graph[x].mark == graph[u.to].mark) return false;
            }
        } 
        return true;
    }
}
