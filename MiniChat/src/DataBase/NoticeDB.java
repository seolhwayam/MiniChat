package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NoticeDB {
	Connection conn;
	PreparedStatement pstmt;
	public NoticeDB() {
		this("jdbc:mysql://localhost:3306/mydb?severTimezone=UTC", "root", "qwe123!@#");
	}
	public NoticeDB(String url, String user, String pw) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pw);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void inputNoticeDB(String notice) {
	    try {
	        // SQL 쿼리 작성 및 실행
	        String sql = "INSERT INTO notice  VALUES (?,sysdate())";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, notice);
	        pstmt.executeUpdate();
	        pstmt.close(); // PreparedStatement 닫기
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	}
	
	public String printNoticeDB() {
		String notice = "공지사항";
	    try {
	        // SQL 쿼리 작성 및 실행
	        String sql = "select notice from notice order by date desc limit 1 ";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				notice = rs.getString("notice");
			}
	        pstmt.close(); // PreparedStatement 닫기
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return notice;

	}

}
