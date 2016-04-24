import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Sync {
    private static ArrayList<String> copy = new ArrayList<>(),
                                     delete = new ArrayList<>();
    
    public static void main(String[] args) throws IOException {
        File s = new File(args[0]), d = new File(args[1]);
        compareDirs(s, d);
        delete.stream().map(x -> x.substring(args[1].length() + 1)).sorted().forEach((x) -> {
            System.out.println("DELETE " + x); 
        });
        copy.stream().map(x -> x.substring(args[0].length() + 1)).sorted().forEach((x) -> {
            System.out.println("COPY " + x);
        });
        if ((copy.isEmpty()) && (delete.isEmpty())) System.out.println("IDENTICAL");
    }
    
    private static void compareDirs(File s, File d) throws IOException {
        if (s.exists()) {
            for (String x: s.list()) {
                File tempS = new File(s, x);
                File tempD = new File(d, x);
                if (tempS.isDirectory()) compareDirs(tempS, tempD);
                else compareFiles(tempS, tempD);
            }
        }
        
        if (d.exists()) {
            for (String x: d.list()) {
                File tempS = new File(s, x);
                File tempD = new File(d, x);
                if (!tempS.exists()) {
                        if (tempD.isDirectory()) compareDirs(tempS, tempD);
                        else delete.add(tempD.getPath());
                }                
            }
        }
    }
    
    private static void compareFiles(File s, File d) throws IOException {
        if (!d.exists()) copy.add(d.getPath());
        else if (s.lastModified() != d.lastModified()) {
            if (!Arrays.equals(Files.readAllBytes(Paths.get(s.getAbsolutePath())),
                               Files.readAllBytes(Paths.get(d.getAbsolutePath())))) {
                delete.add(s.getPath());
                copy.add(d.getPath());
            }
        }
    }
}
