import java.util.*;
class Patient {final String name;final int severity;final long arrival;Patient(String n,int s,long a){if(s<1||s>5)throw new IllegalArgumentException("severity 1..5");name=n;severity=s;arrival=a;}public String toString(){return name+"(S"+severity+")";}}
public class Triage {private long counter=0;private final PriorityQueue<Patient>queue=new PriorityQueue<>(Comparator.comparingInt((Patient p)->p.severity).thenComparingLong(p->p.arrival));
 void admit(String n,int s){queue.add(new Patient(n,s,counter++));}Patient next(){return queue.poll();}
 public static void main(String[]a){Triage er=new Triage();er.admit("Ravi",3);er.admit("Meena",1);er.admit("John",3);er.admit("Asha",5);er.admit("Kumar",1);er.admit("Lata",2);StringBuilder out=new StringBuilder();Patient p;while((p=er.next())!=null)out.append(p).append(' ');System.out.println(out.toString().trim());}}
