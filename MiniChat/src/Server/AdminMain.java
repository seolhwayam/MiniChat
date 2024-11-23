
package Server;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Client.EnterGui;
import Client.UserMain;
import DataBase.AccessDB;
import DataBase.NoticeDB;
import DataBase.UserDB;
import Server.Server.ChatThread;
import screen.CalendarGui;
import screen.ChatlogGUi;
import screen.UserDataGui;

//import Server.ChatThread;
public class AdminMain extends JFrame{
	AccessDB accessDb = new AccessDB();
	private JPanel enterPane2; // 관리자화면틀 생성
	private JPanel enterPane3; // 공지사항입력틀 생성
	private JButton noticeBtn; // 공지 버튼
	private JButton selectChatBtn ; // 채팅조회 버튼
	private JButton loginInfomationBtn; // 로그인정보보기 버튼
	private JButton deleteUserBtn; // 회원삭제 버튼
	private JButton deleteChatBtn; // 강퇴 버튼
	private JButton exitBtn; // 돌아가기 버튼
    private JFrame serverFrame;
    private TextArea chatTextArea;
    protected TextField chatTextField;
    private JList list;
    private DefaultListModel model;
    private ServerGui serverGui;
    NoticeDB noticeDb  = new NoticeDB();
    UserDB userDb = new UserDB();
    
	private BufferedReader reader;
	public PrintWriter writer;


	public AdminMain() {

		admin();
		setVisible(true); // Gui 켜기
	}
	
	public void admin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 종료시 프로세스 종료시키는 코드
        setSize(400, 600);
        setLocationRelativeTo(null); // 화면 중앙에 표시
        enterPane2 = new JPanel();
		setContentPane(enterPane2);
		enterPane2.setLayout(null);
		ImageIcon adminCorbyIcon = new ImageIcon(EnterGui.path + "admin.gif");
		// 이미지 크기 조정
		Image imageadminCorby = adminCorbyIcon.getImage().getScaledInstance(210, 160, Image.SCALE_DEFAULT);
		// 이미지 아이콘을 JLabel에 추가하여 JLabel 생성
		JLabel admincorby = new JLabel(new ImageIcon(imageadminCorby));
		// JLabel의 위치와 크기 설정
		admincorby.setBounds(90, 40, 210, 160); // 예시 위치, 적절한 위치로 수정 필요
		admincorby.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// JPanel에 JLabel 추가
		enterPane2.add(admincorby);

	
		JLabel adminLabel = new JLabel("관리자화면");
		adminLabel.setBounds(155, 200, 150, 50);
		
		
		noticeBtn = new JButton("공지사항");
		selectChatBtn = new JButton("채팅조회");
		loginInfomationBtn = new JButton("접속정보");
		deleteUserBtn = new JButton("회원삭제");
		deleteChatBtn = new JButton("회원강퇴");
		exitBtn = new JButton("로그아웃");
		
		exitBtn.setBounds(130, 490, 130, 30);
		deleteChatBtn.setBounds(130, 440, 130, 30);
		deleteUserBtn.setBounds(130, 390, 130, 30);
		loginInfomationBtn.setBounds(130, 340, 130, 30);
		selectChatBtn.setBounds(130, 290, 130, 30);
		noticeBtn.setBounds(130, 240, 130, 30);
		
		enterPane2.add(noticeBtn);		
		enterPane2.add(selectChatBtn);		
		enterPane2.add(loginInfomationBtn);		
		enterPane2.add(deleteUserBtn);		
		enterPane2.add(deleteChatBtn);		
		enterPane2.add(exitBtn);		
		enterPane2.add(adminLabel);
		
		noticeBtn.addActionListener(event -> {   // 공지사항 버튼
			
			String[] noticeSelect = {"실시간 공지", "영구 공지"};
			String select = (String) JOptionPane.showInputDialog(null, "원하는 공지를 선택해주세요.", "공지사항",
			        JOptionPane.QUESTION_MESSAGE, null, noticeSelect, "선택해주세요");

			// 사용자가 취소 버튼을 클릭했을 때 null을 반환하므로 null 체크를 통해 취소 처리를 수행합니다.
			if (select == null) {
			    // 아무 작업도 수행하지 않음
			    return;
			}

			String notice = JOptionPane.showInputDialog("공지사항 내용을 입력해주세요");
			if (select.equals("실시간 공지")) {
			    writer.println("공지사항/" + notice);
			} else if (select.equals("영구 공지")) {
			    noticeDb.inputNoticeDB(notice);
			}
			
		});
		selectChatBtn.addActionListener(event -> {   // 채팅 조회 버튼
			new ChatlogGUi();
			dispose();
		});
		loginInfomationBtn.addActionListener(event -> {   // 로그인 정보 조희 버튼
			List<String> userList = accessDb.listAccessDB("admin");
        	String[] userArray = userList.toArray(new String[0]); // 리스트를 배열로 변환
        	if (userArray != null && userArray.length > 0) { // Check if the array is not null and not empty
                 JOptionPane.showInputDialog(null, "접속 중인 유저 로그인 현황 입니다.",
                        "로그인 현황", JOptionPane.INFORMATION_MESSAGE, null,
                        userArray, userArray[0]);
          
            } else {
                JOptionPane.showMessageDialog(this, "접속중인 유저가 존재하지 않습니다", "오류", JOptionPane.ERROR_MESSAGE);
            }
   
		});
		deleteUserBtn.addActionListener(event -> {   // 회원 삭제 버튼
			String user = null;
			List<String> userList = userDb.listALLUserDB();
        	String[] userArray = userList.toArray(new String[0]); // 리스트를 배열로 변환
        	if (userArray != null && userArray.length > 0) { // Check if the array is not null and not empty
        		user = (String) JOptionPane.showInputDialog(null, "회원 삭제할 ID를 선택해주세요.",
                        "회원 삭제", JOptionPane.INFORMATION_MESSAGE, null,
                        userArray, userArray[0]);
          
            } else {
                JOptionPane.showMessageDialog(this, "회원이 존재하지 않습니다", "오류", JOptionPane.ERROR_MESSAGE);
            }
			
			userDb.deleteUser(user);		
		});
		deleteChatBtn.addActionListener(event -> {   // 강퇴 버튼
			List<String> userList = accessDb.listAccessDB("admin");
        	String[] userArray = userList.toArray(new String[0]); // 리스트를 배열로 변환
        	if (userArray != null && userArray.length > 0) { // Check if the array is not null and not empty
        		String targetUser = (String) JOptionPane.showInputDialog(null, "강퇴할 회원를 선택해주세요",
                        "회원 강퇴", JOptionPane.INFORMATION_MESSAGE, null,
                        userArray, userArray[0]);
        		accessDb.removeAccessDB(targetUser);
        		writer.println("del/"+targetUser);// 텍스트를 서버로 전송
        		          
            } else {
                JOptionPane.showMessageDialog(this, "접속중인 유저가 존재하지 않습니다", "오류", JOptionPane.ERROR_MESSAGE);
            }
        	
        	

		});
		
		exitBtn.addActionListener(event -> {   // 나가기 버튼
		    new EnterGui();
		    dispose();
		});
		
		 try {
	            // 소켓통신으로 서버로 메세지 전송하는 코드
	            // 서버 정보
	            String serverIP = "localhost";
//	            String serverIP = "192.168.0.17";
	            int serverPort = 8888; // 서버 포트 번호

	            // 서버에 연결
	            Socket socket = new Socket(serverIP, serverPort);
	            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


	            // 서버로 메세지 전송
	            OutputStream outputStream = socket.getOutputStream();
	            writer = new PrintWriter(outputStream, true);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	    }

		
		
		

		
		
	}

	

	
	










