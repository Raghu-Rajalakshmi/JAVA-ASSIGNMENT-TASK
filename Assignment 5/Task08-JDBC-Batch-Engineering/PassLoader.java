import java.sql.*;
import java.util.List;
public class PassLoader {
    public static int loadAll(Connection con,List<String[]> passes)throws SQLException{
        String sql="INSERT INTO bus_pass(pass_id,reg_no,name,route_no,boarding_stop,valid_until,amount) VALUES(?,?,?,?,?,?,?)";
        boolean old=con.getAutoCommit();con.setAutoCommit(false);
        int n=0;
        try(PreparedStatement ps=con.prepareStatement(sql)){
            for(String[] p:passes){
                if(p==null||p.length!=7)throw new IllegalArgumentException("Pass must have 7 fields");
                ps.setString(1,p[0]);ps.setString(2,p[1]);ps.setString(3,p[2]);
                ps.setInt(4,Integer.parseInt(p[3]));ps.setString(5,p[4]);
                ps.setDate(6,Date.valueOf(p[5]));
                double amount=Double.parseDouble(p[6]);
                if(!Double.isFinite(amount)||amount<0)throw new IllegalArgumentException("Invalid amount for "+p[0]);
                ps.setDouble(7,amount);ps.addBatch();n++;
                if(n%1000==0)ps.executeBatch();
            }
            ps.executeBatch();con.commit();return n;
        }catch(SQLException|RuntimeException ex){
            try{con.rollback();}catch(SQLException rollback){ex.addSuppressed(rollback);}
            if(ex instanceof SQLException)throw (SQLException)ex;
            throw ex;
        }finally{con.setAutoCommit(old);}
    }
}
