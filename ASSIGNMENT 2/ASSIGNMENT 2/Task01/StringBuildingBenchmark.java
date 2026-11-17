import java.util.*;
public class StringBuildingBenchmark {
 interface B { String make(List<String> x); }
 static String plus(List<String>x){String s="";for(String a:x)s=s+a;return s;}
 static String sb(List<String>x){StringBuilder s=new StringBuilder();for(String a:x)s.append(a);return s.toString();}
 static String buf(List<String>x){StringBuffer s=new StringBuffer();for(String a:x)s.append(a);return s.toString();}
 static long test(B b,List<String>x,int reps){long t=System.nanoTime();long sum=0;for(int i=0;i<reps;i++)sum+=b.make(x).length();if(sum<0)System.out.println(sum);return (System.nanoTime()-t)/reps;}
 static void run(String label,int fields,int reps){List<String>x=new ArrayList<>();for(int i=0;i<fields;i++)x.add("field"+i+"=value;");B[] bs={StringBuildingBenchmark::plus,StringBuildingBenchmark::sb,StringBuildingBenchmark::buf};String[] ns={"String +","StringBuilder","StringBuffer"};System.out.println(label+" fields="+fields);for(int i=0;i<3;i++){for(int w=0;w<3;w++)test(bs[i],x,reps);System.out.println(ns[i]+" avg ns/ticket="+test(bs[i],x,reps));}}
 public static void main(String[]a){run("SHORT",5,2000);run("NORMAL",50,1000);run("LARGE",500,100);}
}
