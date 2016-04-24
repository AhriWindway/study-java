import java.util.Scanner;

public class Kth {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println(count.finder(in.nextLong() + 1));
    }
}

class count {
    static long finder(long k) {
        long i, j = 10, m = 9;
        for (i = 1; k - i * m > 0; j *= 10, m *= 10, i++) 
            k -= i * m;
        j /= 10;
        
        if (k % i == 0) return (j + k / i - 1) % 10;
        else return ((j + k / i) / (long) Math.pow(10, i - k % i)) % 10;
    }
}
