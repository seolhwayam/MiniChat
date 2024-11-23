package screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Client.ChatJoin;
import DataBase.ChatDB;
public class UserDataGui extends JFrame {
	private JPanel Panel; 
	private JButton selectBtn;
	private JButton outBtn; 
	private JTextField chatTextField; 
	private TextArea chatTextArea;
	ChatDB chatDb = new ChatDB();
	public UserDataGui() {

		UserChat(); // 입장 버튼 생성 및 처리
		setVisible(true); // Gui 켜기
	}
	public void UserChat() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        Panel = new JPanel();
        setLocationRelativeTo(null); // 화면 중앙에 표시
        setContentPane(Panel);
		Panel.setLayout(null);
		
		JLabel ad = new JLabel("회원별 조회");
		Font font = new Font("마루 부리 굵은", Font.BOLD, 20);
		ad.setFont(font);
		ad.setBounds(140, 20,300, 30);
		Panel.add(ad);
		
		selectBtn = new JButton("조회");
		selectBtn.setBounds(290, 430, 80, 20);
		Panel.add(selectBtn);
		
		JButton logout = new JButton("돌아가기");
	    logout.setBounds(270,500, 100, 30);
	    Panel.add(logout);

		chatTextArea = new TextArea();
		chatTextArea.setEditable(false);
		chatTextArea.setBounds(18,65,346, 350);
		Panel.add(chatTextArea);
		
		chatTextField = new JTextField();
		chatTextField.setBounds(18, 430, 270, 20);
		Panel.add(chatTextField);
		chatTextField.setColumns(10); // 글자수 제한
		
		
		selectBtn.addActionListener(event -> { //조회 버튼 누를 시
		    ArrayList<String> chatData =  chatDb.selctUserChat(chatTextField.getText());
		    StringBuilder showChat = new StringBuilder();
		    for (int i = 0; i < chatData.size(); i++) {
		        showChat.append(chatData.get(i) + "\n");
		    }
		    chatTextArea.setText(showChat.toString());
		    
		    // 조회 버튼을 클릭할 때만 텍스트 필드를 초기화
		    chatTextField.setText("");
		});
		
		chatTextField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            ArrayList<String> chatData =  chatDb.selctUserChat(chatTextField.getText());
		            StringBuilder showChat = new StringBuilder();
		            for (int i = 0; i < chatData.size(); i++) {
		                showChat.append(chatData.get(i) + "\n");
		            }
		            chatTextArea.setText(showChat.toString());
		            
		            // Enter 키를 눌렀을 때만 텍스트 필드를 초기화
		            chatTextField.setText("");
		        }
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
					
		logout.addActionListener(event -> {//나가기 버튼 누를시 
			new ChatlogGUi();
			dispose();
	    });
	}

}