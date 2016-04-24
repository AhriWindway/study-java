import java.math.BigInteger;
import java.util.*;

public class FastFib {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        System.out.println(getFib(n));
    }
    
    public static BigInteger getFib(int n) {
        BigInteger a = BigInteger.ONE, ta, 
                   b = BigInteger.ONE, tb, 
                   c = BigInteger.ONE, rc = BigInteger.ZERO, tc, 
                   d = BigInteger.ZERO, rd = BigInteger.ONE;
        
        while (n > 0) {
            if ((n & 1) == 1) {
                tc = rc;
                rc = (rc.multiply(a)).add(rd.multiply(c));
                rd = (tc.multiply(b)).add(rd.multiply(d));
            }
            ta = a; tb = b; tc = c;
            a = (a.multiply(a)).add(b.multiply(c));
            b = (ta.multiply(b)).add(b.multiply(d));
            c = (c.multiply(ta)).add(d.multiply(c));
            d = (tc.multiply(tb)).add(d.multiply(d));
            n >>= 1;
        }           
        return rc;
    }
}
