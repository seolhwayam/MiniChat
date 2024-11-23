package screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import Client.EnterGui;
import Client.UserMain;
import DataBase.UserDB;

public class changeProfile extends JFrame {
	String id;
    UserDB userDb = new UserDB();


    public changeProfile(String id) {
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
        
        JLabel selectLabel = new JLabel("프로필 변경");
        Font font = new Font("마루 부리 굵은", Font.BOLD, 20);
        selectLabel.setBounds(150, 10, 150, 50);
        selectLabel.setFont(font);
        Panel.add(selectLabel);
        
        ImageIcon Icon1 = new ImageIcon(EnterGui.path+"musicCorby.gif");
        Image image1 = Icon1.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        
        ImageIcon Icon2 = new ImageIcon(EnterGui.path+"puhaCorby.gif");
        Image image2 = Icon2.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        
        ImageIcon Icon3 = new ImageIcon(EnterGui.path+"zzCorby.gif");
        Image image3 = Icon3.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        
        ImageIcon Icon4 = new ImageIcon(EnterGui.path+"yamCorby.gif");
        Image image4 = Icon4.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
       
        ImageIcon Icon5 = new ImageIcon( EnterGui.path+"trunCorby.gif");
        Image image5= Icon5.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        
        ImageIcon Icon6 = new ImageIcon(EnterGui.path+"corbyKick.gif");
        Image image6 = Icon6.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
  

        JButton button1 = new JButton(new ImageIcon(image1));
        button1.setBounds(70,70,120,120);
        
        JButton button2 = new JButton(new ImageIcon(image2));
        button2.setBounds(210,70,120,120);
        
        JButton button3 = new JButton(new ImageIcon(image3));
        button3.setBounds(70,200,120,120);
        
        JButton button4 = new JButton(new ImageIcon(image4));
        button4.setBounds(210,200,120,120);
        
        JButton button5 = new JButton(new ImageIcon(image5));
        button5.setBounds(70,330,120,120);
        
        JButton button6 = new JButton(new ImageIcon(image6));
        button6.setBounds(210,330,120,120);

        Panel.add(button1);
        Panel.add(button2);
        Panel.add(button3);
        Panel.add(button4);
        Panel.add(button5);
        Panel.add(button6);

        
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


	     button1.addActionListener(event -> {
	    	 userDb.chageProfile(id, 1);
	    	 JOptionPane.showMessageDialog(null, "프로필 사진이 변경되었습니다!");
	     	});
	     button2.addActionListener(event -> {
	    	 userDb.chageProfile(id, 2);
	    	 JOptionPane.showMessageDialog(null, "프로필 사진이 변경되었습니다!");
	    	 
	        });
	     button3.addActionListener(event -> {
	    	 userDb.chageProfile(id, 3);
	    	 JOptionPane.showMessageDialog(null, "프로필 사진이 변경되었습니다!");
	        });
	     button4.addActionListener(event -> {
	    	 userDb.chageProfile(id, 4);
	    	 JOptionPane.showMessageDialog(null, "프로필 사진이 변경되었습니다!");
	        });
	     button5.addActionListener(event -> {
	    	 userDb.chageProfile(id, 5);
	    	 JOptionPane.showMessageDialog(null, "프로필 사진이 변경되었습니다!");
	        });
	     button6.addActionListener(event -> {
	    	 userDb.chageProfile(id, 6);
	    	 JOptionPane.showMessageDialog(null, "프로필 사진이 변경되었습니다!");
	        });

        logout.addActionListener(event -> {
            new UserMain(id);
            dispose();
        });

        setVisible(true);
    
    }
}
