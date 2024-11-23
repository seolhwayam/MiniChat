package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccessDB {
    Connection conn;
    PreparedStatement pstmt;
    
    public AccessDB() {
        this("jdbc:mysql://localhost:3306/mydb?severTimezone=UTC", "root", "qwe123!@#");
    }
    
    public AccessDB(String url, String user, String pw) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pw);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void inputAccessDB(String id) {
        try {
            // SQL 쿼리 작성 및 실행
            String sql = "INSERT INTO access VALUES (?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            pstmt.close(); // PreparedStatement 닫기
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void removeAccessDB(String id) {
        try {
            // SQL 쿼리 작성 및 실행
            String sql = "DELETE FROM access WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            pstmt.close(); // PreparedStatement 닫기
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<String> listAccessDB(String id) {
        List<String> userList = new ArrayList<>();
        try {
            String sql = "select id from access";
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                String userId = rs.getString("id");
                if(!userId.equals(id)) {
                    userList.add(userId);
                }
            }
            pstmt.close(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
