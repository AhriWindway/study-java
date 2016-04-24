import java.util.Scanner;

class Fraction {
    private int num = 0, denom = 1;
    
    public Fraction(int num, int denom) {
        this.num = num; this.denom = denom;
        normalize();
    }
    
    @Override
    public String toString() {
        return "" + num + "/" + denom + " ";
    }
    
    public static Fraction multiply(Fraction x, Fraction y) {
        return new Fraction(x.num * y.num, x.denom * y.denom);
    }
    
    public static Fraction divide(Fraction x, Fraction y) {
        return new Fraction(x.num * y.denom, x.denom * y.num);
    }
    
    public static Fraction minus(Fraction x, Fraction y) {
        return new Fraction(x.num * y.denom - y.num * x.denom, x.denom * y.denom);
    }
    
    public boolean zero() {
        return num == 0;
    }
    
    private int gcd(int a, int b) { return b==0 ? Math.abs(a) : gcd(b, a%b); }
    
    public final void normalize() {
        int temp = gcd(num, denom);
        if (temp != 0) {
            num /= temp;
            denom /= temp;
        }
        if (denom < 0) {
            denom *= -1; num *= -1;
        }
        if (num == 0) denom = 1;
    }
}

public class Gauss {

    private static Fraction[][] matrix;
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        matrix = new Fraction[n][n + 1];
        
        
        for (int i = 0; i < n; i++) 
            for (int k = 0; k < n + 1; k++) matrix[i][k] = new Fraction(in.nextInt(), 1); 
        
        Fraction m, t;
        Fraction[] buf;
        for (int k = 0; k < n; k++) {
            if (matrix[k][k].zero()) {
                for (int i = k + 1; i < n; i++) {
                    if (!(matrix[i][k].zero())) {
                        buf = matrix[k];
                        matrix[k] = matrix[i];
                        matrix[i] = buf;
                        break;
                    }
                }
            }
            
            t = matrix[k][k];
            for (int i = 0; i < n + 1; i++) {
                matrix[k][i] = Fraction.divide(matrix[k][i], t);
            }
            
            for (int j = k + 1; j < n; j++) {
                m = Fraction.divide(matrix[j][k], matrix[k][k]);
                for (int i = 0; i < n + 1; i++) {
                    matrix[j][i] = Fraction.minus(matrix[j][i], Fraction.multiply(m, matrix[k][i]));
                }
            }
        }
        
        boolean flag = false;
        for (int i = 0; i < n; i++)
            if (matrix[i][i].zero()) flag = true;
        if (flag) { 
            System.out.println("No solution");
            return;
        }
        
        for (int i = 0; i < n; i++) {
            boolean rows = true, cols = true;
            for (int k = 0; k < n; k++) {
                if (!matrix[i][k].zero()) rows = false;
                if (!matrix[k][i].zero()) cols = false;
            }
            if (rows | cols) {
                System.out.println("No solution");
                return;     
            }
        }
        
        Fraction x[] = new Fraction[n];
        for (int i = n - 1; i >= 0; i--) {
            x[i] = matrix[i][n];
            for (int j = i + 1; j < n; j++) {
                x[i] = Fraction.minus(x[i], Fraction.multiply(matrix[i][j], x[j]));
            }
        }            
        
        for (int i = 0; i < n; i++) {
            System.out.println(x[i]);
        } 
    } 
}
