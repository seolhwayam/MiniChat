package Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import DataBase.AccessDB;
import DataBase.NoticeDB;
import Server.AdminMain;
import Server.ServerGui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ChatGui extends JFrame {

	public static JPanel chatPanel;
	private JTextField textMsg;
	private TextArea chatLog;
	private BufferedReader reader;
	public PrintWriter writer;
	NoticeDB noticeDb  = new NoticeDB();
	AccessDB accessDb = new AccessDB();
	String notice;
	String id;

    //ChatGui 구현
    public ChatGui(String id) {
    	this.id = id;
        // EnterGui에서 보내는 닉네임 값을 매개변수로 받음
        String msg = "login/" + id;
        
       // notice
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(400, 600);
        setLocationRelativeTo(null); // 화면 중앙에 표시
        chatPanel = new JPanel();
        chatPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(chatPanel);
        chatPanel.setLayout(null);
        

        // chatLabel과 chatLog
        JLabel chatLabel = new JLabel("채팅방"); 
        chatLabel.setBounds(170, 5, 95, 30);
        chatPanel.add(chatLabel);
        //공지사항
		JLabel noticeLabel = new JLabel(noticeDb.printNoticeDB()); // chatLabel 생성
		
		noticeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        noticeLabel.setBounds(18, 35, 346, 20);
        noticeLabel.setOpaque(true); // 불투명하게 설정
        noticeLabel.setBackground(Color.WHITE); // 배경색 설정 
        chatPanel.add(noticeLabel);
        

        chatLog = new TextArea(); // chatLog 생성
        chatLog.setEditable(false);
        chatLog.setText("채팅 로그입니다.");
        chatLog.setBounds(18,65,346, 350);
        chatPanel.add(chatLog);

        textMsg = new JTextField();
        textMsg.setText("메세지를 입력하세요");
        textMsg.setBounds(18, 460, 346, 20);
        chatPanel.add(textMsg);
        textMsg.setColumns(10);
        
        JButton wisperButton = new JButton("귓속말");
        wisperButton.setBounds(18, 430, 100, 20);
        chatPanel.add(wisperButton);
        
        
        ImageIcon emojiIcon = new ImageIcon(EnterGui.path +"emoji.jpg");
	     Image imageEmoji = emojiIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        JButton emojiBtn = new JButton(new ImageIcon(imageEmoji));
        emojiBtn.setBounds(120, 430, 20, 20);
        chatPanel.add(emojiBtn);
        
        
        
        JButton logout = new JButton("돌아가기");
		 logout.setBounds(270,500, 100, 30);
		 chatPanel.add(logout);

	        
	     ImageIcon starIcon = new ImageIcon(EnterGui.path +"star.gif");
	     Image imageStar = starIcon.getImage().getScaledInstance(115, 104, Image.SCALE_DEFAULT);
	     JLabel star = new JLabel(new ImageIcon(imageStar));
	     star.setBounds(0, 500,115, 104); // 좌표와 크기 설정
	     chatPanel.add(star);
	        
	     ImageIcon blueStarIcon = new ImageIcon(EnterGui.path +"blueStar.gif");
	     Image imageblueStar = blueStarIcon.getImage().getScaledInstance(115, 104, Image.SCALE_DEFAULT);
	     JLabel blueStar = new JLabel(new ImageIcon(imageblueStar));
	     blueStar.setBounds(90, 520,115, 104); // 좌표와 크기 설정
	     chatPanel.add(blueStar);
	        
	        
	     JLabel Star2 = new JLabel(new ImageIcon(imageStar));
	     Star2.setBounds(180, 500,115, 104); // 좌표와 크기 설정
	     chatPanel.add(Star2);
	        
	     JLabel blueStar2 = new JLabel(new ImageIcon(imageblueStar));
	     blueStar2.setBounds(270, 520,115, 104); // 좌표와 크기 설정
	     chatPanel.add(blueStar2);
	      
	     JLabel Star3 = new JLabel(new ImageIcon(imageStar));
	     Star3.setBounds(320, 520,115, 104); // 좌표와 크기 설정
	     chatPanel.add(Star3);
	        
	     JLabel blueStar3 = new JLabel(new ImageIcon(imageblueStar));
	     blueStar3.setBounds(-50, 500,115, 104); // 좌표와 크기 설정
	     chatPanel.add(blueStar3);
	     
	     emojiBtn.addActionListener(event -> {
	    	 Object[] selections = {"토끼", "고양이", "헬리콥터","부엉이","찰칵찰칵","오징어"};
	        	String val = (String) JOptionPane.showInputDialog(null, "이모티콘을 선택해주세요",
	        	        "이모티콘", JOptionPane.INFORMATION_MESSAGE, null,
	        	        selections, selections[0]);

	        	if (val != null) {
	        	    switch (val) {
	        	        case ("토끼"):
	        	        	writer.println("^토끼^"+id);
	        	            break;
	        	        case ("고양이"):
	        	        	writer.println("^고양이^"+id);
	        	            break;
	        	        case ("헬리콥터"):
	        	        	writer.println("^헬리콥터^"+id);
	        	            break;
	        	        case ("부엉이"):
	        	        	writer.println("^부엉이^"+id);
	        	            break;
	        	        case ("찰칵찰칵"):
	        	        	writer.println("^찰칵찰칵^"+id);
	        	            break;
	        	        case ("오징어"):
	        	        	writer.println("^오징어^"+id);
	        	            break;
	        	    }
	        	}
	    	 
	        	
	      });
	     
	     logout.addActionListener(event -> {
	    	 	accessDb.removeAccessDB(id);
	    	 	writer.println("out/"+id);
	        	new UserMain(id);
	        	dispose();
	        	
	      });

	     
        
        wisperButton.addActionListener(event -> {
        	List<String> userList = accessDb.listAccessDB(id);
        	String[] userArray = userList.toArray(new String[0]); // 리스트를 배열로 변환
        	if (userArray != null && userArray.length > 0) { // Check if the array is not null and not empty
                String targetUser = (String) JOptionPane.showInputDialog(null, "귓속말을 보낼 상대를 골라주세요",
                        "귓속말", JOptionPane.INFORMATION_MESSAGE, null,
                        userArray, userArray[0]);
                String message = JOptionPane.showInputDialog("귓속말 내용을 입력해주세요. ");
                writer.println("whisper/" + targetUser + "/" + message);
            } else {
                JOptionPane.showMessageDialog(this, "접속중인 유저가 존재하지 않습니다", "오류", JOptionPane.ERROR_MESSAGE);
            }
        });



//        메세지 전송 엔터키 이벤트 처리
        textMsg.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    //엔터키 눌렸을때 실행될 코드
                    String text = textMsg.getText();
                    writer.println(text);// 텍스트를 서버로 전송
                    textMsg.setText(""); // 텍스트 필드의 값 지움 => 초기화
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        setVisible(true);
        try {
            // 소켓통신으로 서버로 메세지 전송하는 코드
            // 서버 정보
            String serverIP = "localhost";
//            String serverIP = "192.168.0.17";
            int serverPort = 8888; // 서버 포트 번호

            // 서버에 연결
            Socket socket = new Socket(serverIP, serverPort);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 서버로부터 메시지를 읽는 스레드 시작
            Thread readThread = new Thread(new ServerMessageReader());
            readThread.start();

            // 서버로 메세지 전송
            OutputStream outputStream = socket.getOutputStream();
            writer = new PrintWriter(outputStream, true);
            writer.println(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



	private void setDefaultCloseOperation(UserMain userMain) {
		// TODO Auto-generated method stub
		
	}



	//서버에서 주는 메세지 처리
    private class ServerMessageReader implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("서버로부터 메시지: " + message);
                    uploadText(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //받은 메세지 채팅창에 업로드
    public void uploadText(String message) {
        chatLog.append(message + "\n");
    }
}