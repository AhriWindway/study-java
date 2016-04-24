import java.util.HashSet;
import java.util.Scanner;

public class Econom {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String expr = in.nextLine();
        
        System.out.println(Notation.count(expr, -1, expr.length()));
    } 
}

class Notation {
    private static HashSet<String> xs = new HashSet<>();
    
    public static int count(String expr, int i, int n) {
        int num = 1;
        for (int k = i + 1; k < expr.length(); k++) {
            if (expr.charAt(k) == '(') {
                num++;
                count(expr, k, n);
            }
            
            if (expr.charAt(k) == ')') {
                num--;
                if (num == 0) {
                    if (!(xs.contains(expr.substring(i + 1, k)))) 
                        xs.add(expr.substring(i + 1, k));
                    break;
                }
            }
        }
        return xs.size();
    }
}
