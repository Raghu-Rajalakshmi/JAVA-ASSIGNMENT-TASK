import java.nio.file.*;
public class BenchmarkPassFind {
    public static void main(String[] args)throws Exception{
        for(int n:new int[]{1000,10000,100000}){
            Path p=Files.createTempFile("pass-bench",".txt"); PassStore s=new PassStore(p);
            for(int i=0;i<n;i++)s.add("P"+i,"REG"+i,"Student"+i,1,"Stop","2027-12-31",100);
            long t=System.nanoTime();s.find("P"+(n-1));
            System.out.printf("records=%d find_last_ms=%.3f%n",n,(System.nanoTime()-t)/1e6);
            Files.deleteIfExists(p);
        }
    }
}
