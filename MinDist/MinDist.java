import java.util.Scanner;

public class MinDist {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        char x = in.next().charAt(0), y = in.next().charAt(0);
        
        System.out.println(getDist.finder(s, x, y));
    }
}

class getDist {
    static int finder(String s, char x, char y){
        int lastx = -1, lasty = -1, dist = s.length(), i, cur = dist;
        char temp;
        for (i = 0; i < s.length(); i++) {
            temp = s.charAt(i); 
            if (temp == x) lastx = i;
            if (temp == y) lasty = i;
            
            if ((lastx != -1) && (lasty != -1)) cur = Math.abs(lastx - lasty) - 1;
            if (cur < dist) dist = cur;
            
            if (dist == 0) break;
        }  
        return dist;
    }
}
