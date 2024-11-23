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


public class MenuRecomend extends JFrame{
	UserDB userDb = new UserDB();
	static Random random = new Random();
	Connection conn;
	PreparedStatement pstmt;
	String id;
	
	
	public MenuRecomend(String id) {
		this("jdbc:mysql://localhost:3306/mydb?severTimezone=UTC", "root", "qwe123!@#");
		this.id = id;
		menu();
	}
	
	public  MenuRecomend(String url, String user, String pw) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pw);
			menu();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void menu() {
		 	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    setSize(400, 600);
	    	setLocationRelativeTo(null);
		    JPanel Panel = new JPanel();
		    setContentPane(Panel);
		    Panel.setLayout(null);
		    Font font = new Font("마루 부리 굵은", Font.BOLD, 25);
		    JLabel msg = new JLabel("오늘의 메뉴추천");
		    msg.setFont(font);
		    msg.setBounds(110,60, 300 ,30);
		    Panel.add(msg);
		    
		    ImageIcon starIcon = new ImageIcon(EnterGui.path +"bluestar.gif");
		    Image imageStar = starIcon.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);

		    JButton menuPick = new JButton(new ImageIcon(imageStar));
		    menuPick.setBounds(50, 100, 300, 300); // 버튼 크기 수정
		    menuPick.setBackground(null); // 배경색 없애기
		    menuPick.setBorderPainted(false); // 테두리 없애기
		    Panel.add(menuPick);
		    
//		    JLabel fortune = new JLabel("오늘의 운세");
//		   // fortune.setBounds(200, 200, 100, 30); // 라벨 위치 수정
//		    fortunePick.add(fortune); // fortunePick 버튼에 라벨 추가
//		    Panel.add(fortunePick); // 패널에 fortunePick 버튼 추가
//		    
		    
		    Font menuFont = new Font("마루 부리 굵은", Font.BOLD, 20);

	  
		    JLabel menu = new JLabel();
		    menu.setHorizontalAlignment(SwingConstants.CENTER); // 가운데 정렬
		    menu.setFont(menuFont);
		    menu.setBounds(100, 135, 100, 30); // 크기를 충분히 확보하여 텍스트가 잘리지 않도록 설정
		    menuPick.setLayout(null); // fortunePick 버튼의 레이아웃을 null로 설정
		    menuPick.add(menu); // fortunePick 버튼에 라벨 추가
		    
		    JButton logout = new JButton("돌아가기");
		    logout.setBounds(270,500, 100, 30);
		    Panel.add(logout);
		    
		    
		    
		    ImageIcon starUnderIcon = new ImageIcon(EnterGui.path +"star.gif");
		     Image imageStarUnder =  starUnderIcon.getImage().getScaledInstance(115, 104, Image.SCALE_DEFAULT);
		     JLabel star = new JLabel(new ImageIcon(imageStarUnder));
		     star.setBounds(0, 500,115, 104); // 좌표와 크기 설정
		     Panel.add(star);
		        
		     ImageIcon blueUnderStarIcon = new ImageIcon(EnterGui.path +"blueStar.gif");
		     Image imageblueUnderStar = blueUnderStarIcon.getImage().getScaledInstance(115, 104, Image.SCALE_DEFAULT);
		     JLabel blueStar = new JLabel(new ImageIcon(imageblueUnderStar));
		     blueStar.setBounds(90, 520,115, 104); // 좌표와 크기 설정
		     Panel.add(blueStar);
		        
		        
		     JLabel Star2 = new JLabel(new ImageIcon(imageStarUnder));
		     Star2.setBounds(180, 500,115, 104); // 좌표와 크기 설정
		     Panel.add(Star2);
		        
		     JLabel blueStar2 = new JLabel(new ImageIcon(imageblueUnderStar));
		     blueStar2.setBounds(270, 520,115, 104); // 좌표와 크기 설정
		     Panel.add(blueStar2);
		      
		     JLabel Star3 = new JLabel(new ImageIcon(imageStarUnder));
		     Star3.setBounds(320, 520,115, 104); // 좌표와 크기 설정
		     Panel.add(Star3);
		        
		     JLabel blueStar3 = new JLabel(new ImageIcon(imageblueUnderStar));
		     blueStar3.setBounds(-50, 500,115, 104); // 좌표와 크기 설정
		     Panel.add(blueStar3);
		    
		    
		    menuPick.addActionListener(event -> { // 메뉴추첨 버튼
	        int randomNum = random.nextInt(9) + 1;
	        String  menuMessage = null;
	        String sql = "SELECT lunch FROM menu WHERE index2=?";
	        try {
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, randomNum);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	                menuMessage = rs.getString("lunch");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        menu.setText(menuMessage);
	    });


	    logout.addActionListener(event -> { // 사용자 화면으로 돌아가기
	        new UserMain(id);
	        dispose();
	    });

	    setVisible(true);
	}

	
	
	
}
