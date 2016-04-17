import java.util.Arrays;
import java.util.Scanner;

class Element implements Comparable<Element> {
    final String num;
    public Element(String x){
        this.num = x;
    }
    public String get() {
        return num;
    }
    @Override
    public int compareTo(Element x) {
        return (int) (Long.parseLong(x.num + num) - Long.parseLong(num + x.num));
    }
}

public class MaxNum {    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Element[] xs = new Element[n];
        
        for (int i = 0; i < n; i++) xs[i] = new Element(in.next());
        Arrays.sort(xs);
        for (int i = 0; i < xs.length; i++) System.out.print(xs[i].get());
        System.out.println();
    }
}
