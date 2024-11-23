package Client;

import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import DataBase.AccessDB;
import DataBase.LoginlogDB;
import DataBase.UserDB;
import screen.Change;
import screen.FortuneCookie;
import screen.MenuRecomend;
import screen.changeProfile;

public class UserMain extends JFrame {
	UserDB userDb = new UserDB();
	LoginlogDB loginlogDb= new LoginlogDB();
	AccessDB accessDB= new AccessDB();
	public UserMain(String id) {
		userMain(id);
    }
    public void userMain(String id) {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setSize(400, 600);
    	setLocationRelativeTo(null);
    	

    	JPanel panel = new JPanel();
    	
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // 수직으로 정렬
    	
    	
        ImageIcon Icon1 = new ImageIcon(EnterGui.path+"musicCorby.gif");
    	ImageIcon Icon2 = new ImageIcon(EnterGui.path+"puhaCorby.gif");
    	ImageIcon Icon3 = new ImageIcon(EnterGui.path+"zzCorby.gif");
    	ImageIcon Icon4 = new ImageIcon(EnterGui.path+"yamCorby.gif");
    	ImageIcon Icon5 = new ImageIcon( EnterGui.path+"trunCorby.gif");
    	ImageIcon Icon6 = new ImageIcon(EnterGui.path+"corbyKick.gif");
    	
    	
    	int num = userDb.selectProfile(id);
    	ImageIcon profileIcon = new ImageIcon(EnterGui.path+"musicCorby.gif");
    	switch(num) {
    		case 1:
    			profileIcon = new ImageIcon(EnterGui.path+"musicCorby.gif");
    			break;
    		case 2:
    			profileIcon = new ImageIcon(EnterGui.path+"puhaCorby.gif");
    			break;
    		case 3:
    			profileIcon = new ImageIcon(EnterGui.path+"zzCorby.gif");
    			break;
    		case 4:
    			profileIcon = new ImageIcon(EnterGui.path+"yamCorby.gif");
    			break;
    		case 5:
    			profileIcon = new ImageIcon( EnterGui.path+"trunCorby.gif");
    			break;
    		case 6:
    			profileIcon = new ImageIcon(EnterGui.path+"corbyKick.gif");
    			break;
    			
    	}

    	Image imageprofile = profileIcon.getImage().getScaledInstance(200, 150, Image.SCALE_DEFAULT);
    	JLabel profile = new JLabel(new ImageIcon(imageprofile));
    	profile.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬

    	panel.add(profile);

    	JLabel idLabel = new JLabel(id);
    	idLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬
    	panel.add(idLabel);

    	Dimension buttonSize = new Dimension(130, 30); // 버튼 크기 설정

    	panel.add(Box.createVerticalStrut(20)); // 수직 공간 추가

    	JButton changeBtn = new JButton("변경");
    	changeBtn.setMaximumSize(buttonSize);
    	changeBtn.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬
    	panel.add(changeBtn);

    	panel.add(Box.createVerticalStrut(20)); // 수직 공간 추가

    	JButton fortuneCookie = new JButton("포춘쿠키");
    	fortuneCookie.setMaximumSize(buttonSize);
    	fortuneCookie.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬
    	panel.add(fortuneCookie);

    	panel.add(Box.createVerticalStrut(20)); // 수직 공간 추가

    	JButton menuRecommendations = new JButton("메뉴추천");
    	menuRecommendations.setMaximumSize(buttonSize);
    	menuRecommendations.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬
    	panel.add(menuRecommendations);

    	panel.add(Box.createVerticalStrut(20)); // 수직 공간 추가

    	JButton chatEnter = new JButton("채팅방 입장");
    	chatEnter.setMaximumSize(buttonSize);
    	chatEnter.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬
    	panel.add(chatEnter);

    	panel.add(Box.createVerticalStrut(20)); // 수직 공간 추가

    	JButton logout = new JButton("로그아웃");
    	logout.setMaximumSize(buttonSize);
    	logout.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬
    	panel.add(logout);
 
    	setContentPane(panel);
    	setVisible(true);

		changeBtn.addActionListener(event -> {  // 변경 버튼 선택시
			new Change(id);
			dispose();
        });
		fortuneCookie.addActionListener(event -> {  //포춘쿠키 선택시
			new FortuneCookie(id);
			dispose();
        });
		menuRecommendations.addActionListener(event -> { //메뉴추천 선택시
			new MenuRecomend(id);
			dispose();
		});
		

		
		
	
		logout.addActionListener(event -> { // 로그아웃시
			loginlogDb.loginUser(id,"logout");
			new EnterGui();
			dispose();
		});
		
		chatEnter.addActionListener(event -> { //채팅방 입장시
		    accessDB.inputAccessDB(id);
		    new ChatGui(id); // EnterGui로 이동
		    dispose(); // 현재 창 닫기
		});
		

    }
	
}
