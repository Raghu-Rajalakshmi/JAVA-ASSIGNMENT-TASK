import java.util.*;
class BadStudent {String regNo,name;BadStudent(String r,String n){regNo=r;name=n;}public boolean equals(Object o){return o instanceof BadStudent s&&regNo.equals(s.regNo);}}
final class Student {private final String regNo,name;Student(String r,String n){regNo=Objects.requireNonNull(r);name=Objects.requireNonNull(n);}
 public boolean equals(Object o){return this==o||o instanceof Student s&&regNo.equals(s.regNo);}public int hashCode(){return Objects.hash(regNo);}public String toString(){return regNo+" "+name;}}
public class StudentHashSetDemo {public static void main(String[]a){
 Set<BadStudent>b=new HashSet<>();b.add(new BadStudent("192311001","Arun"));b.add(new BadStudent("192311002","Divya"));b.add(new BadStudent("192311001","Arun"));System.out.println("Broken set size (may vary): "+b.size());
 Set<Student>s=new HashSet<>();s.add(new Student("192311001","Arun"));s.add(new Student("192311002","Divya"));s.add(new Student("192311001","Arun"));System.out.println("Unique students: "+s.size());
}}
