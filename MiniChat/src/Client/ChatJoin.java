package Client;

import javax.swing.*;
import java.awt.*;
import DataBase.UserDB;

import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ChatJoin extends JFrame{
		UserDB userDb = new UserDB();
		JTextField textFieldName;
		JTextField textFieldNum;
		JTextField textFieldId;
		JTextField textFieldPw;
		JTextField textFieldCheckPw;
		boolean id_check = false;
		public ChatJoin() {
	    	try {
				join();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    public void join() throws SQLException {
	    	 setSize(400, 600);
	         setLocationRelativeTo(null); // 화면 중앙에 표시
	         JPanel panel = new JPanel();
	         panel.setLayout(null); // 레이아웃 매니저를 null로 설정하여 자유로운 위치 조정 가능

	        
			setContentPane(panel);
			panel.setLayout(null);
		
			JLabel name = new JLabel("이름");
			name.setBounds(80, 60, 80, 15);
			panel.add(name);
			JLabel id_num = new JLabel("주민번호");
			id_num.setBounds(80, 100, 80, 15);
			panel.add(id_num);
			JLabel id = new JLabel("ID");
			id.setBounds(80, 140, 80, 15);
			panel.add(id);
			JLabel pw = new JLabel("PW");
			pw.setBounds(80, 180, 80, 15);
			panel.add(pw);
			JLabel checkpw = new JLabel("PW 확인");
			checkpw.setBounds(80, 220, 80, 15);
			panel.add(checkpw);
			
			JButton checkIdButton = new JButton("중복");
			checkIdButton.setBounds(270, 140, 70, 20);
			panel.add(checkIdButton);
			
			textFieldName = new JTextField(); //이름
			textFieldName.setBounds(145, 60, 115, 20);
			panel.add(textFieldName);
			textFieldName.setColumns(10); 
			
			textFieldNum = new JTextField(); //주민번호
			textFieldNum.setBounds(145, 100, 115, 20);
			panel.add(textFieldNum);
			textFieldNum.setColumns(13);
			
			textFieldId = new JTextField(); //아이디
			textFieldId.setBounds(145, 140, 115, 20);
			panel.add(textFieldId);
			textFieldId.setColumns(10);
			
			textFieldPw = new JTextField(); //비밀번호
			textFieldPw.setBounds(145, 180, 115, 20);
			panel.add(textFieldPw);
			textFieldPw.setColumns(10); 
			
			textFieldCheckPw = new JTextField(); //비밀번호
			textFieldCheckPw.setBounds(145, 220, 115, 20);
			panel.add(textFieldCheckPw);
			textFieldCheckPw.setColumns(10); 
			
			JButton enterButton = new JButton("가입");
			enterButton.setBounds(145, 260, 97, 23);
			panel.add(enterButton);
			
			 JButton logout = new JButton("돌아가기");
			 logout.setBounds(270,500, 100, 30);
			 panel.add(logout);
			 
			 ImageIcon todackCorbyIcon = new ImageIcon(EnterGui.path +"todackCorby.gif");
			 Image imageTodackCorby = todackCorbyIcon.getImage().getScaledInstance(300, 180, Image.SCALE_DEFAULT);

			 JLabel todackCorby = new JLabel(new ImageIcon( imageTodackCorby));
			 todackCorby.setBounds(50, 300, 300, 200); // 버튼 크기 수정
			 todackCorby.setBackground(null); // 배경색 없애기
			 panel.add(todackCorby);
			 
		        
		     ImageIcon starIcon = new ImageIcon(EnterGui.path +"star.gif");
		     Image imageStar = starIcon.getImage().getScaledInstance(115, 104, Image.SCALE_DEFAULT);
		     JLabel star = new JLabel(new ImageIcon(imageStar));
		     star.setBounds(0, 500,115, 104); // 좌표와 크기 설정
		     panel.add(star);
		        
		     ImageIcon blueStarIcon = new ImageIcon(EnterGui.path +"blueStar.gif");
		     Image imageblueStar = blueStarIcon.getImage().getScaledInstance(115, 104, Image.SCALE_DEFAULT);
		     JLabel blueStar = new JLabel(new ImageIcon(imageblueStar));
		     blueStar.setBounds(90, 520,115, 104); // 좌표와 크기 설정
		     panel.add(blueStar);
		        
		        
		     JLabel Star2 = new JLabel(new ImageIcon(imageStar));
		     Star2.setBounds(180, 500,115, 104); // 좌표와 크기 설정
		     panel.add(Star2);
		        
		     JLabel blueStar2 = new JLabel(new ImageIcon(imageblueStar));
		     blueStar2.setBounds(270, 520,115, 104); // 좌표와 크기 설정
		     panel.add(blueStar2);
		      
		     JLabel Star3 = new JLabel(new ImageIcon(imageStar));
		     Star3.setBounds(320, 520,115, 104); // 좌표와 크기 설정
		     panel.add(Star3);
		        
		     JLabel blueStar3 = new JLabel(new ImageIcon(imageblueStar));
		     blueStar3.setBounds(-50, 500,115, 104); // 좌표와 크기 설정
		     panel.add(blueStar3);
			
			
			enterButton.addActionListener(event -> {
				
				if(checkJoin()&&enterCheck()) {
					userDb.InputUser(textFieldName.getText(), textFieldNum.getText(), textFieldId.getText(), textFieldPw.getText());
					new EnterGui();
					dispose();
					id_check = false;
				}
				
	        });
			
			checkIdButton.addActionListener(event -> {   
			    // 아이디 유효성 검사
				if (isValidID(textFieldId.getText())) {
				    if (userDb.isExistingID(textFieldId.getText())) {
				        JOptionPane.showMessageDialog(this, "이미 있는 ID 입니다. 다른 ID를 입력해주세요.", "입력오류", JOptionPane.ERROR_MESSAGE);
				    } else {
				        JOptionPane.showMessageDialog(this, "사용가능한 ID 입니다.", "ID유효성 체크", JOptionPane.INFORMATION_MESSAGE);
				        id_check = true;
				    }
				} else {
				    JOptionPane.showMessageDialog(this, "유효하지 않은 ID입니다.", "입력오류", JOptionPane.ERROR_MESSAGE);
				}
			});
			
			logout.addActionListener(event -> {   
			    new EnterGui();
			    dispose();
			});

			// 아이디 유효성 검사 메서드

			setVisible(true);
	    }
	    public boolean isValidID(String id) {
		    return id != null && !id.isEmpty();
		}
	
		public boolean checkJoin() {
	        if (id_check) {
	            if (textFieldPw.getText().equals(textFieldCheckPw.getText())) {
	                return true;
	            } else {
	                JOptionPane.showMessageDialog(this, "비밀번호 확인이 올바르지 않습니다.", "입력오류", JOptionPane.ERROR_MESSAGE);
	                return false;
	            }
	        } else {
	            JOptionPane.showMessageDialog(this, "ID 유효성 검사가 완료되지 않았습니다.", "입력오류", JOptionPane.ERROR_MESSAGE);
	            return false;
	        }
	    }
		
		public boolean enterCheck() {
			boolean result = false;
			if(!textFieldId.getText().isEmpty()) {
				if(!textFieldName.getText().isEmpty()) {
					if(!textFieldNum.getText().isEmpty()) {
						if(!textFieldPw.getText().isEmpty()) {
							result = true;
						}else {
							JOptionPane.showMessageDialog(this, "비밀번호을 입력하지 않았습니다.", "입력오류", JOptionPane.ERROR_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(this, "주민번호를 입력하지 않았습니다.", "입력오류", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(this, "이름을 입력하지 않았습니다.", "입력오류", JOptionPane.ERROR_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(this, "ID를 입력하지 않았습니다.", "입력오류", JOptionPane.ERROR_MESSAGE);
			}

	       return result;  
	    }
	 
	       
	    	
	        
}





