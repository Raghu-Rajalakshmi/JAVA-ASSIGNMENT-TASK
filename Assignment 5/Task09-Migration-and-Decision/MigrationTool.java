import java.io.*;import java.nio.charset.StandardCharsets;import java.nio.file.*;import java.sql.*;import java.util.*;
public class MigrationTool{
 static String url=System.getenv().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/assignment5");
 static String user=System.getenv().getOrDefault("DB_USER","root"),pwd=System.getenv().getOrDefault("DB_PASSWORD","");
 static String[] parse(String line){return line.split("\\|",-1);}
 static boolean valid(String[]p){try{return p.length==7&&!p[0].isBlank()&&!p[1].isBlank()&&!p[2].isBlank()&&Integer.parseInt(p[3])>0&&!p[4].isBlank()&&java.time.LocalDate.parse(p[5])!=null&&Double.isFinite(Double.parseDouble(p[6]))&&Double.parseDouble(p[6])>=0;}catch(Exception e){return false;}}
 public static void main(String[]a)throws Exception{
  if(a.length!=1){System.out.println("Usage: java MigrationTool BUS_PASS_FILE");return;}
  List<String[]> good=new ArrayList<>();List<String> bad=new ArrayList<>();int lineNo=0;
  try(BufferedReader r=Files.newBufferedReader(Path.of(a[0]),StandardCharsets.UTF_8)){String line;while((line=r.readLine())!=null){lineNo++;String[]p=parse(line);if(valid(p))good.add(p);else bad.add("line "+lineNo+": "+line);}}
  try(Connection c=DriverManager.getConnection(url,user,pwd)){
   int loaded=PassLoader.loadAll(c,good);System.out.println("Loaded: "+loaded);
   System.out.println("Rejected: "+bad.size());for(String b:bad)System.out.println("REJECT "+b);
   DatabaseMetaData md=c.getMetaData();System.out.println("Database: "+md.getDatabaseProductName()+" "+md.getDatabaseProductVersion());System.out.println("Driver: "+md.getDriverName()+" "+md.getDriverVersion());
   try(ResultSet rs=md.getTables(c.getCatalog(),null,"%",new String[]{"TABLE"})){System.out.println("Tables:");while(rs.next())System.out.println(" - "+rs.getString("TABLE_NAME"));}
  }
 }
}
