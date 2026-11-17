import java.util.*;
public class RedesignStringBuilder {
 static String original(List<String>x){String s="";for(String f:x)s=s+f;return s;}
 static String redesigned(List<String>x){StringBuilder b=new StringBuilder();for(String f:x)b.append(f);return b.toString();}
 static long measure(java.util.function.Function<List<String>,String> f,List<String>x,int n){long t=System.nanoTime();long z=0;for(int i=0;i<n;i++)z+=f.apply(x).length();if(z<0)System.out.println(z);return (System.nanoTime()-t)/n;}
 public static void main(String[]a){for(int size:new int[]{5,50,500,2000}){List<String>x=new ArrayList<>();for(int i=0;i<size;i++)x.add("field"+i+";");int reps=size<100?1000:100;for(int w=0;w<3;w++){measure(RedesignStringBuilder::original,x,reps);measure(RedesignStringBuilder::redesigned,x,reps);}System.out.printf("fields=%d original_ns=%d redesigned_ns=%d%n",size,measure(RedesignStringBuilder::original,x,reps),measure(RedesignStringBuilder::redesigned,x,reps));}}
}
