import java.util.*;

public class MaxComponent {
    private static Graph g;
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        g = new Graph(n);
        
        int edge = in.nextInt();
        for (int i = 0; i < edge; i++) {
            int left = in.nextInt();
            int right = in.nextInt();
            g.add(left, right);
            if (left != right) g.add(right, left);
            
        }
        
        g.DFS();
        g.generateDot();
    } 
}

class Graph {
    class Element {
        private Element next = null;
        private int comp = -1, mark = 0;
        private final int value;
        public Element(int i) { value = i; }
    }
    class Comp { private int vertNum = 0, edgeNum = 0, numberOfComp = 0; }
    
    private static Element[] graph;
    private static Comp max;
    private static int vertex;  
    
    public Graph(int vert) {
        graph = new Element[vertex = vert];
        max = new Comp(); 
        for (int i = 0; i < vertex; i++) graph[i] = new Element(i);
    }
    
    public void add(int left, int right) {
        Element temp = graph[left];
        Element a = new Element(right);
        while (temp.next != null) temp = temp.next;
        temp.next = a;
    }
    
    public void DFS() {
        for (int i = 0, component = 0; i < vertex; i++) {
            if (graph[i].comp == -1) {
                Comp temp = new Comp();
                temp.numberOfComp = component;
                VisitVertex(graph[i], component, temp);
                component++;
                
                if ((temp.vertNum > max.vertNum) | ((temp.vertNum == max.vertNum) && (temp.edgeNum > max.edgeNum))) max = temp;
            }
        }
    }
    
    private void VisitVertex(Element v, int component, Comp temp) {
        v.comp = component;
        v.mark = 1;
        temp.vertNum++;
        for (Element u = v.next; u != null; u = u.next, temp.edgeNum++) 
            if (graph[u.value].mark == 0) VisitVertex(graph[u.value], component, temp);   
        v.mark = 2;
    }
        
    public void generateDot() {
        System.out.println("graph {");
        for (int i = 0; i < vertex; i++) {
            if (graph[i].comp == max.numberOfComp) System.out.println(i + " [color = red]");
            else System.out.println(i);
            
            for (Element k = graph[i].next; k != null; k = k.next) { 
                if (k.value <= i) {
                    if (graph[i].comp == max.numberOfComp) System.out.println(i + " -- " + k.value + " [color = red]");
                    else System.out.println(i + " -- " + k.value);
                } 
            }
        }
        System.out.println("}");
    }  
}
