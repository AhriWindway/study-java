import java.util.*; 

public class Dividers {

    private static ArrayList<Long> der = new ArrayList<Long>();
    private static ArrayList<Long> used = new ArrayList<Long>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); 
        long n = sc.nextLong(); 
        System.out.print("graph{\n"); 
        createApex(n);
        createEdge();
        System.out.print("}\n"); 
    }

    public static void createApex(long n) {
        for (long i = (long) Math.sqrt(n); i > 0; i--) {
            if (n % i == 0) {
                der.add(i);
                if (!der.contains(n/i)) der.add(n/i);                
            }
        }
        Collections.sort(der);
        for (int i = 0; i < der.size(); i++) {
            System.out.print(der.get(i) + "\n");
        }   
    }
    
    public static void createEdge() {
        for (int i = der.size() - 1; i > 0; i--) {
            used.clear();
            for (int k = i - 1; k >= 0; k--) {
                if (der.get(i) % der.get(k) == 0) {
                    if (check(der.get(k))) {
                        used.add(der.get(k));
                        System.out.print(der.get(i) + "--" + der.get(k) + "\n");
                    } 
                }
            }
        }
    }
    
    public static boolean check(long n) {
        for (int i = 0; i < used.size(); i++) { 
            if (used.get(i) % n == 0) return false;   
        }
        return true;
    }
}
