package screen;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Client.EnterGui;
import Client.UserMain;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import DataBase.UserDB;

public class FortuneCookie extends JFrame{
	UserDB userDb = new UserDB();
	static Random random = new Random();
	Connection conn;
	PreparedStatement pstmt;
	String id;
	
	
	public FortuneCookie(String id) {
		this("jdbc:mysql://localhost:3306/mydb?severTimezone=UTC", "root", "qwe123!@#");
		this.id = id;
		fortune();
	}
	
	public FortuneCookie(String url, String user, String pw) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pw);
			fortune();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void fortune() {
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(400, 600);
    	setLocationRelativeTo(null);
	    JPanel Panel = new JPanel();
	    setContentPane(Panel);
	    Panel.setLayout(null);
	    Font font = new Font("마루 부리 굵은", Font.BOLD, 25);
	    JLabel msg = new JLabel("오늘의 포춘쿠키");
	    msg.setFont(font);
	    msg.setBounds(110,60, 300 ,30);
	    Panel.add(msg);
	    
	    ImageIcon starIcon = new ImageIcon(EnterGui.path +"star.gif");
	    Image imageStar = starIcon.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);

	    JButton fortunePick = new JButton(new ImageIcon(imageStar));
	    fortunePick.setBounds(50, 100, 300, 300); // 버튼 크기 수정
	    fortunePick.setBackground(null); // 배경색 없애기
	    fortunePick.setBorderPainted(false); // 테두리 없애기
	    Panel.add(fortunePick);
	    
//	    JLabel fortune = new JLabel("오늘의 운세");
//	   // fortune.setBounds(200, 200, 100, 30); // 라벨 위치 수정
//	    fortunePick.add(fortune); // fortunePick 버튼에 라벨 추가
//	    Panel.add(fortunePick); // 패널에 fortunePick 버튼 추가
//	    
	    
	    Font fortuneFont = new Font("마루 부리 굵은", Font.BOLD, 20);

  
	    JLabel fortune = new JLabel();
	    fortune.setBounds(100, 135, 100, 30); // fortunePick 버튼 내에서 라벨의 위치 설정
	    fortune.setHorizontalAlignment(SwingConstants.CENTER); // 가운데 정렬
	    fortune.setFont(fortuneFont);
	    fortunePick.setLayout(null); // fortunePick 버튼의 레이아웃을 null로 설정
	    fortunePick.add(fortune); // fortunePick 버튼에 라벨 추가

	    
	    JButton logout = new JButton("돌아가기");
	    logout.setBounds(270,500, 100, 30);
	    Panel.add(logout);
	    
	     ImageIcon starUnderIcon = new ImageIcon(EnterGui.path +"star.gif");
	     Image imageStarUnder =  starUnderIcon.getImage().getScaledInstance(115, 104, Image.SCALE_DEFAULT);
	     JLabel star = new JLabel(new ImageIcon(imageStarUnder));
	     star.setBounds(0, 500,115, 104); // 좌표와 크기 설정
	     Panel.add(star);
	        
	     ImageIcon blueStarIcon = new ImageIcon(EnterGui.path +"blueStar.gif");
	     Image imageblueStar = blueStarIcon.getImage().getScaledInstance(115, 104, Image.SCALE_DEFAULT);
	     JLabel blueStar = new JLabel(new ImageIcon(imageblueStar));
	     blueStar.setBounds(90, 520,115, 104); // 좌표와 크기 설정
	     Panel.add(blueStar);
	        
	        
	     JLabel Star2 = new JLabel(new ImageIcon(imageStarUnder));
	     Star2.setBounds(180, 500,115, 104); // 좌표와 크기 설정
	     Panel.add(Star2);
	        
	     JLabel blueStar2 = new JLabel(new ImageIcon(imageblueStar));
	     blueStar2.setBounds(270, 520,115, 104); // 좌표와 크기 설정
	     Panel.add(blueStar2);
	      
	     JLabel Star3 = new JLabel(new ImageIcon(imageStarUnder));
	     Star3.setBounds(320, 520,115, 104); // 좌표와 크기 설정
	     Panel.add(Star3);
	        
	     JLabel blueStar3 = new JLabel(new ImageIcon(imageblueStar));
	     blueStar3.setBounds(-50, 500,115, 104); // 좌표와 크기 설정
	     Panel.add(blueStar3);
	    
	    
	    
	    

	    fortunePick.addActionListener(event -> { // 포춘쿠키 버튼 이벤트
	        int randomNum = random.nextInt(3) + 1;
	        String fortuneMessage = null;
	        String sql = "SELECT cookie FROM fortune WHERE index1=?";
	        try {
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, randomNum);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	                fortuneMessage = rs.getString("cookie");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        fortune.setText(fortuneMessage);
	    });

	    logout.addActionListener(event -> { // 사용자 화면으로 돌아가기
	        new UserMain(id);
	        dispose();
	    });

	    setVisible(true);
	}

	
	
	
}
