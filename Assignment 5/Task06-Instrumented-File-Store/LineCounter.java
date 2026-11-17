import java.io.*;
import java.nio.file.*;
public class LineCounter {
    public static long unbuffered(Path p) throws IOException {
        long lines=0;
        try(InputStream in=Files.newInputStream(p)){int b;while((b=in.read())!=-1)if(b=='\n')lines++;}
        return lines;
    }
    public static long buffered(Path p) throws IOException {
        long lines=0;
        try(BufferedInputStream in=new BufferedInputStream(Files.newInputStream(p),64*1024)){
            byte[] buf=new byte[64*1024]; int n;
            while((n=in.read(buf))!=-1)for(int i=0;i<n;i++)if(buf[i]=='\n')lines++;
        }
        return lines;
    }
    private static long time(IoLong task) throws Exception {
        long start=System.nanoTime(); long result=task.run();
        System.out.printf("lines=%d elapsed_ms=%.3f%n",result,(System.nanoTime()-start)/1e6);
        return result;
    }
    interface IoLong { long run() throws Exception; }
    public static void main(String[] args)throws Exception{
        if(args.length!=1){System.out.println("Usage: java LineCounter FILE");return;}
        Path p=Path.of(args[0]);
        System.out.println("Unbuffered");time(()->unbuffered(p));
        System.out.println("Buffered");time(()->buffered(p));
    }
}
