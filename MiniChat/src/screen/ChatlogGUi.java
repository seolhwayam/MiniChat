package screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import Client.EnterGui;
import Server.AdminMain;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
public class ChatlogGUi extends JFrame {
	private JPanel enterPaneChat;
	private JButton selectDateBtn; 
	private JButton selectUserBtn;
	private JButton exitBtn; 

	public ChatlogGUi() {
		searchChoice();// 회원가입창라벨
		setVisible(true); // Gui 켜기
	}

	public void searchChoice() {
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(400, 600);
	        setLocationRelativeTo(null);
	        
	        Font font = new Font("마루 부리 굵은", Font.BOLD, 20);

	        // JPanel 생성 및 초기화
	        enterPaneChat = new JPanel();
	        enterPaneChat.setLayout(null); // Layout 설정

	        // JLabel 추가
	        JLabel selectLabel = new JLabel("채팅조회 화면");
	        selectLabel.setBounds(140, 70, 150, 50);
	        selectLabel.setFont(font);
	        enterPaneChat.add(selectLabel);

	        // JButton 추가
	        JButton selectDateBtn = new JButton("날짜별 조회");
	        JButton selectUserBtn = new JButton("회원별 조회");

	        selectDateBtn.setBounds(100, 290, 200, 30);
	        selectUserBtn.setBounds(100, 330, 200, 30);
	       
	        
	        JButton logout = new JButton("돌아가기");
			logout.setBounds(270,500, 100, 30);
			enterPaneChat.add(logout);

	        
	        
	        enterPaneChat.add(selectDateBtn);
	        enterPaneChat.add(selectUserBtn);
	        
	        ImageIcon sususungCorbyIcon = new ImageIcon(EnterGui.path +"sususungCorby.gif");
	        Image imageSususungCorby = sususungCorbyIcon.getImage().getScaledInstance(220, 150, Image.SCALE_DEFAULT);
	        JLabel sususungCorby = new JLabel(new ImageIcon(imageSususungCorby));
	        sususungCorby.setBounds(50, 100, 300, 200); // 좌표와 크기 설정
	        enterPaneChat.add(sususungCorby);
	        
	        ImageIcon starIcon = new ImageIcon(EnterGui.path +"star.gif");
	        Image imageStar = starIcon.getImage().getScaledInstance(115, 104, Image.SCALE_DEFAULT);
	        JLabel star = new JLabel(new ImageIcon(imageStar));
	        star.setBounds(0, 500,115, 104); // 좌표와 크기 설정
	        enterPaneChat.add(star);
	        
	        ImageIcon blueStarIcon = new ImageIcon(EnterGui.path +"blueStar.gif");
	        Image imageblueStar = blueStarIcon.getImage().getScaledInstance(115, 104, Image.SCALE_DEFAULT);
	        JLabel blueStar = new JLabel(new ImageIcon(imageblueStar));
	        blueStar.setBounds(90, 520,115, 104); // 좌표와 크기 설정
	        enterPaneChat.add(blueStar);
	        
	        
	        JLabel Star2 = new JLabel(new ImageIcon(imageStar));
	        Star2.setBounds(180, 500,115, 104); // 좌표와 크기 설정
	        enterPaneChat.add(Star2);
	        
	        JLabel blueStar2 = new JLabel(new ImageIcon(imageblueStar));
	        blueStar2.setBounds(270, 520,115, 104); // 좌표와 크기 설정
	        enterPaneChat.add(blueStar2);
	        
	        JLabel Star3 = new JLabel(new ImageIcon(imageStar));
	        Star3.setBounds(320, 520,115, 104); // 좌표와 크기 설정
	        enterPaneChat.add(Star3);
	        
	        JLabel blueStar3 = new JLabel(new ImageIcon(imageblueStar));
	        blueStar3.setBounds(-50, 500,115, 104); // 좌표와 크기 설정
	        enterPaneChat.add(blueStar3);
	  

	        // ContentPane 설정
	        setContentPane(enterPaneChat);
	        setVisible(true);
		
		
		
		selectDateBtn.addActionListener(new ActionListener() {
			// 날짜별 조회
			public void actionPerformed(ActionEvent e) {
				new CalendarGui();
				dispose();
			}
		});
		selectUserBtn.addActionListener(new ActionListener() {
			// 회원별 조회
			public void actionPerformed(ActionEvent e) {
				new UserDataGui();
				dispose();
			}
		});
		logout.addActionListener(new ActionListener() {
			// 입장 버튼 클릭시 이벤트 처리
			public void actionPerformed(ActionEvent e) {
				new AdminMain();
				dispose();
			}
		});
	}
	}