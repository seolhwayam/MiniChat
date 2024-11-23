package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class UserDB {
    Connection conn;
    PreparedStatement pstmt;
    AccessDB accessDB = new AccessDB();

    public UserDB() {
        this("jdbc:mysql://localhost:3306/mydb?severTimezone=UTC", "root", "qwe123!@#");
    }

    public UserDB(String url, String user, String pw) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pw);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 회원 생성
    public void InputUser(String name, String id_number, String id, String pw) {
        try {
            String sql = "INSERT INTO user (name, id_number, id, pw, manage) VALUES (?, ?, ?, ?, false)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, id_number);
            pstmt.setString(3, id);
            pstmt.setString(4, pw);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 회원 삭제
    public void deleteUser(String id) {
        try {
            String sql = "UPDATE user SET deleteYN = 'Y' WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    //ID,PW 체크
    public boolean isExistingLogin(String id, String pw) {
        try {
            String sql = "SELECT * FROM user WHERE id=? and pw=? and deleteYN='N'";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, pw);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 아이디 유효성 검사
    public boolean isExistingID(String id) {
        try {
            String sql = "SELECT * FROM user WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> listUserDB() {
        List<String> userList = new ArrayList<>();
        try {
            String sql = "select id from user";
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("id");
                if (!userId.equals("admin")) {
                    userList.add(userId);
                }
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    
    public List<String> listALLUserDB() {
        List<String> userList = new ArrayList<>();
        try {
            String sql = "select id from user where deleteYN='N'";
            pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("id");
                if(!userId.equals("admin")) {
                	userList.add(userId);
                }
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    
    public void chageBackGround(String id, String color) {
        try {
            String sql = "UPDATE user SET background_color = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, color);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public void chageProfile(String id, int num) {
        try {
            String sql = "UPDATE user SET profile = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int selectProfile(String id) {
    	int num = 1;
        try {
            String sql = "SELECT profile FROM user WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            	num = rs.getInt("profile");
                return num;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return num;
    }
    
       
    
    
    
    
    
    
    public String selectColor(String id) {
    	String color = "pinkColor";
        try {
            String sql = "SELECT background_color FROM user WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                color = rs.getString("background_color");
                return color;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return color;
    }
    
    
    
    
   

}
