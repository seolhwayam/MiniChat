package DataBase;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class LoginlogDB {
	Connection conn;
	PreparedStatement pstmt;
	String id;
	public LoginlogDB() {
		this("jdbc:mysql://localhost:3306/mydb?severTimezone=UTC", "root", "qwe123!@#");
		this.id=id;
	}
	public LoginlogDB(String url, String user, String pw) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pw);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	


	public void loginUser(String id, String login) {
	    try {

	        // SQL 쿼리 작성 및 실행
	        String sql = "INSERT INTO loginlog  VALUES (sysdate(),?,?)";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, id);
	        pstmt.setString(2, login);
	        pstmt.executeUpdate();
	        pstmt.close(); // PreparedStatement 닫기
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	


	





}
