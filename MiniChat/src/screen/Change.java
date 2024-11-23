package screen;

import javax.swing.*;
import java.awt.*;

import Client.ChatGui;
import Client.EnterGui;
import Client.UserMain;
import DataBase.UserDB;

public class Change extends JFrame {
    String id;
    UserDB userDb = new UserDB();


    public Change(String id) {
        this.id = id;
        change();
    }

    public void change() {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(400, 600);
    	setLocationRelativeTo(null);
        JPanel Panel = new JPanel();
        setContentPane(Panel);
        Panel.setLayout(null);
        
        
        ImageIcon attackCorbyIcon = new ImageIcon(EnterGui.path +"attackCorby.gif");
	    Image imageattackCorby = attackCorbyIcon.getImage().getScaledInstance(200, 180, Image.SCALE_DEFAULT);

	    JLabel fortunePick = new JLabel(new ImageIcon(imageattackCorby));
	    fortunePick.setBounds(100, 100, 200, 200); // 버튼 크기 수정
	    fortunePick.setBackground(null); // 배경색 없애기
	    Panel.add(fortunePick);

        JButton changeBackground = new JButton("배경색깔 변경");
        changeBackground.setBounds(120, 300, 150, 30);
        Panel.add(changeBackground);
        
        JButton changeProfile = new JButton("프로필 변경");
        changeProfile.setBounds(120, 350, 150, 30);
        Panel.add(changeProfile);
        
        
        JButton logout = new JButton("돌아가기");
	    logout.setBounds(270,500, 100, 30);
	    Panel.add(logout);
	    
	    ImageIcon starIcon = new ImageIcon(EnterGui.path +"star.gif");
	     Image imageStar = starIcon.getImage().getScaledInstance(115, 104, Image.SCALE_DEFAULT);
	     JLabel star = new JLabel(new ImageIcon(imageStar));
	     star.setBounds(0, 500,115, 104); // 좌표와 크기 설정
	     Panel.add(star);
	        
	     ImageIcon blueStarIcon = new ImageIcon(EnterGui.path +"blueStar.gif");
	     Image imageblueStar = blueStarIcon.getImage().getScaledInstance(115, 104, Image.SCALE_DEFAULT);
	     JLabel blueStar = new JLabel(new ImageIcon(imageblueStar));
	     blueStar.setBounds(90, 520,115, 104); // 좌표와 크기 설정
	     Panel.add(blueStar);
	        
	        
	     JLabel Star2 = new JLabel(new ImageIcon(imageStar));
	     Star2.setBounds(180, 500,115, 104); // 좌표와 크기 설정
	     Panel.add(Star2);
	        
	     JLabel blueStar2 = new JLabel(new ImageIcon(imageblueStar));
	     blueStar2.setBounds(270, 520,115, 104); // 좌표와 크기 설정
	     Panel.add(blueStar2);
	      
	     JLabel Star3 = new JLabel(new ImageIcon(imageStar));
	     Star3.setBounds(320, 520,115, 104); // 좌표와 크기 설정
	     Panel.add(Star3);
	        
	     JLabel blueStar3 = new JLabel(new ImageIcon(imageblueStar));
	     blueStar3.setBounds(-50, 500,115, 104); // 좌표와 크기 설정
	     Panel.add(blueStar3);


        changeBackground.addActionListener(event -> {
        	Object[] selections = {"보라색", "노란색", "분홍색"};
        	String val = (String) JOptionPane.showInputDialog(null, "원하는 배경색깔을 선택하세요",
        	        "chage", JOptionPane.INFORMATION_MESSAGE, null,
        	        selections, selections[0]);

        	if (val != null) {
        	    switch (val) {
        	        case ("보라색"):
        	            Color purpleColor = new Color(221, 160, 221);
        	            userDb.chageBackGround(id, "purpleColor");
        	            UIManager.put("Panel.background", purpleColor);
        	            break;
        	        case ("노란색"):
        	            Color yellowColor = new Color(255, 248, 220);
        	            userDb.chageBackGround(id, "yellowColor");
        	            UIManager.put("Panel.background", yellowColor);
        	            break;
        	        case ("분홍색"):
        	            Color pinkColor = new Color(255, 204, 204); // 분홍색
        	            userDb.chageBackGround(id, "pinkColor");
        	            UIManager.put("Panel.background", pinkColor);
        	            break;
        	    }
        	}

        });
        
        logout.addActionListener(event -> {
            new UserMain(id);
            dispose();
        });

        changeProfile.addActionListener(event -> {
            new changeProfile(id);
            dispose();
        });

        setVisible(true);
    
    }
}
