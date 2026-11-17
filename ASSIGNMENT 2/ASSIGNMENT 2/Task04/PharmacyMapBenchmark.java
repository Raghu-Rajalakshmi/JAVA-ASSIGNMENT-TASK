import java.util.*;
public class PharmacyMapBenchmark {
 static long time(Runnable r){long s=System.nanoTime();r.run();return System.nanoTime()-s;}
 static void run(int n){Map<String,Integer>[] ms=new Map[]{new HashMap<>(),new TreeMap<>(),new LinkedHashMap<>()};String[] names={"HashMap","TreeMap","LinkedHashMap"};
 for(int j=0;j<3;j++){Map<String,Integer>m=ms[j];long put=time(()->{for(int i=0;i<n;i++)m.put(String.format("MED%07d",i),i);});
 long get=time(()->{long z=0;for(int i=0;i<100000;i++)z+=m.getOrDefault(String.format("MED%07d",i%n),-1);if(z==Long.MIN_VALUE)System.out.println(z);});
 long iter=time(()->{long z=0;for(var e:m.entrySet())z+=e.getValue();if(z==Long.MIN_VALUE)System.out.println(z);});
 System.out.printf("%s n=%d put=%d lookup100k=%d iteration=%d ns%n",names[j],n,put,get,iter);
 if(j==1)System.out.println("Sorted first keys="+m.keySet().stream().limit(4).toList());if(j==2)System.out.println("Insertion-order first keys="+m.keySet().stream().limit(4).toList());}}
 public static void main(String[]a){run(1000);run(10000);run(50000);}
}
