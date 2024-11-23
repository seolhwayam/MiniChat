package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatDB {
	Connection conn;
    PreparedStatement pstmt;
    
    public ChatDB(){
        this("jdbc:mysql://localhost:3306/mydb?severTimezone=UTC", "root", "qwe123!@#");
    }
    
    public ChatDB(String url, String user, String pw) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pw);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void inputChatDB(String id,String msg) {
        try {
            // SQL 쿼리 작성 및 실행
            String sql = "INSERT INTO chat  VALUES (sysdate(),?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, msg);
            pstmt.executeUpdate();
            pstmt.close(); // PreparedStatement 닫기
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public ArrayList<String> selctUserChat(String id) {
    	ArrayList<String> chatData = new ArrayList<>();
        try {
            String sql = "SELECT datestamp,id,contents FROM chat WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            	String chatAll = rs.getString("datestamp")+" "+rs.getString("id")+" "+rs.getString("contents");
                chatData.add(chatAll);
            }
            return chatData;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return chatData;
    }
    
    public ArrayList<String> selectDateChat(String date) {
		ArrayList<String> chatData = new ArrayList<>();
	    try {
	        String sql = "SELECT * FROM chat where date(datestamp)=?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, date);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	        	String chatAll = rs.getString("datestamp")+" "+rs.getString("id")+" "+rs.getString("contents");
                chatData.add(chatAll);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	   
	    return chatData;
	}
    
    
    
    

}
