import java.sql.*;
public class LoginDemo {
    public static boolean unsafe(Connection c,String id,String pwd)throws SQLException{
        String sql="SELECT 1 FROM users WHERE user_id='"+id+"' AND password='"+pwd+"'";
        try(Statement s=c.createStatement();ResultSet r=s.executeQuery(sql)){return r.next();}
    }
    public static boolean safe(Connection c,String id,String pwd)throws SQLException{
        String sql="SELECT 1 FROM users WHERE user_id=? AND password=?";
        try(PreparedStatement p=c.prepareStatement(sql)){
            p.setString(1,id);p.setString(2,pwd);
            try(ResultSet r=p.executeQuery()){return r.next();}
        }
    }
    public static void main(String[]a)throws Exception{
        String url=System.getenv().getOrDefault("DB_URL","jdbc:mysql://localhost:3306/assignment5");
        try(Connection c=DriverManager.getConnection(url,
                System.getenv().getOrDefault("DB_USER","root"),
                System.getenv().getOrDefault("DB_PASSWORD",""))){
            String attack="admin' -- ";
            System.out.println("Unsafe attack accepted: "+unsafe(c,attack,"wrong"));
            System.out.println("PreparedStatement attack accepted: "+safe(c,attack,"wrong"));
        }
    }
}
