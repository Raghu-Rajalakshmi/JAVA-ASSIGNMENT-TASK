import java.util.*;
public class ListStructureBenchmark {
 static long time(Runnable r){long s=System.nanoTime();r.run();return System.nanoTime()-s;}
 static void run(int n){System.out.println("N="+n);
  for(String type:List.of("ArrayList","LinkedList")){List<Integer> l=type.equals("ArrayList")?new ArrayList<>():new LinkedList<>();
   long add=time(()->{for(int i=0;i<n;i++)l.add(i);});
   long front=time(()->{for(int i=0;i<Math.min(n,1500);i++)l.add(0,-i);});
   long get=time(()->{long z=0;for(int i=0;i<Math.min(n,5000);i++)z+=l.get((i*17)%l.size());if(z==Long.MIN_VALUE)System.out.println(z);});
   long iterate=time(()->{long z=0;for(int v:l)z+=v;if(z==Long.MIN_VALUE)System.out.println(z);});
   System.out.printf("%s append=%d front-insert=%d indexed-get=%d iterate=%d ns%n",type,add,front,get,iterate);}
  ArrayDeque<Integer>d=new ArrayDeque<>();long ends=time(()->{for(int i=0;i<n;i++){d.addLast(i);d.addFirst(-i);}});long pops=time(()->{while(!d.isEmpty())d.removeFirst();});System.out.println("ArrayDeque both-ends="+ends+" remove-front="+pops+" ns");
 }
 public static void main(String[]a){run(1000);run(10000);run(50000);}
}
