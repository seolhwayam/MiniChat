package Server;

import Model.User;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;

import DataBase.ChatDB;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ServerGui serverGui;
    private ArrayList<ChatThread> chatlist = new ArrayList<>();
    private ExecutorService executor;
    ChatDB chatDb = new ChatDB();
    public Server() {
        this.executor = Executors.newFixedThreadPool(10); // 최대 10개의 스레드를 가지는 스레드 풀 생성
        this.start();
        
    }
    public static void main(String[] args) {
		new Server();
	}

    public void start() {
        try {
            //GUI 켜기
            serverGui = new ServerGui();
            //GUI에 이벤트 추가
            serverGui.chatTextField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        String s = serverGui.getChatMessage().trim();
                        if (!s.isEmpty()) {
                            serverGui.appendMessage("SERVER: " + s);
                            sendToAll("공지사항: " + s);
                        }
                        serverGui.setTextFieldBlank();
                    }
                }
            });

            // 서버 소켓 생성
            serverSocket = new ServerSocket(8888);
            serverGui.appendMessage("서버가 시작 되었습니다");

            // 여러 클라이언트 연결 대기
            while (true) {
                clientSocket = serverSocket.accept();
                ChatThread chatthread = new ChatThread(); // 스레드 생성
                chatlist.add(chatthread);  // 어레이 리스트에 스레드 추가
                serverGui.appendMessage("클라이언트가 연결됐습니다." + "(IP : " + clientSocket.getInetAddress().toString().substring(1) + ")");

                // 스레드 풀을 사용하여 스레드 실행
                executor.execute(chatthread);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendToAll(String s) {
        for (ChatThread thread : chatlist) {
            thread.writerToClient.println(s);
        }
    }

    private void sendWhisper(String sender, String targetUser, String message) {
        if (targetUser == null) {
            serverGui.appendMessage("대상 사용자를 지정하지 않았습니다.");
            return;
        }
        
        for (ChatThread thread : chatlist) {
            if (thread.user != null && targetUser.equals(thread.user.getId())) {
                thread.writerToClient.println(">>>" + sender + "의 귓속말 : " + message);
                return;
            }
        }
        
        serverGui.appendMessage("귓속말 대상(" + targetUser + ")을 찾을 수 없습니다.");
    }

    class ChatThread extends Thread {
        String msg;
        String[] rmsg;
        private BufferedReader reader = null;
        PrintWriter writerToClient = null;
        private User user;

        public void run() {
            boolean loginBool = true;
            user = new User(); // user 객체 생성(스레드 접속 클라이언트)
            user.setThreadName(this.getName()); // user 스레드 이름 입력
            try {
                // 클라이언트와 입출력 스트림 생성
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writerToClient = new PrintWriter(clientSocket.getOutputStream(), true);
                writerToClient.println("서버에 연결 됐습니다(serverIP : " + serverSocket.getInetAddress().toString().substring(1) + ")"); //클라이언트에 연결 사실 전송
                
                //클라이언트로부터 메세지를 반복해서 읽어옴
                while (loginBool) {
                    msg = reader.readLine();
                    rmsg = msg.split("/");  // 구분자를 기준으로 나누기(login/id)

                    if(new BlacklistedWord().containBlacklistedWord(msg)){ // 욕설 처리
                        sendToAll("server : 부적절한 단어 입니다 바른말을 써주세요.");
                        continue;
                    }

                    serverGui.appendMessage(user.getId() + " : " + msg);
                    
                    
                    
                    if (rmsg[0].equals("login")) { //로그인 처리
                        user.setId(rmsg[1]);
                        sendToAll("server : " + user.getId() + "님이 입장했습니다");
                        serverGui.appendUserList(user.getId());
                    } else if (msg.equals(".quit")) { //로그아웃 처리
                        sendToAll("server : " + user.getId() + "님이 나갔습니다");
                        serverGui.appendMessage(user.getId() + "님이 나갔습니다");
                        loginBool = false; // 클라이언트가 .quit을 치면 나감
                        break; // 루프를 종료하여 스레드를 종료합니다.
                    } else if (msg.startsWith("whisper/")) {
                            String[] parts = msg.split("/");
                            String targetUser = parts[1];
                            String whisperMessage = parts[2];
                            sendWhisper(user.getId(), targetUser, whisperMessage);
                    } else if(msg.startsWith("del/")) {
                        String[] parts2 = msg.split("/");
                        if (parts2.length >= 2) {
                            String targetUser = parts2[1];
                            if (targetUser != null) { // 추가된 null 체크
                                ChatThread targetThread = null;
                                for (ChatThread thread : chatlist) {
                                    if (thread.user != null && targetUser.equals(thread.user.getId())) { // 사용자 ID와 null 체크
                                        targetThread = thread;
                                        break;
                                    }
                                }
                                
                                if (targetThread != null) {
                                    sendToAll("server : " + targetUser + "님이 강퇴되었습니다.");
                                    serverGui.appendMessage(targetUser + "님이 강퇴되었습니다.");
                                    chatlist.remove(targetThread);
                                    serverGui.removeUserList(targetUser);
                                    serverGui.appendMessage(targetThread.getName() + " stopped!");
                                    try {
                                        targetThread.writerToClient.close();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    targetThread.interrupt();
                                } else {
                                    // 대상 사용자를 찾을 수 없음을 처리하는 코드 추가
                                    serverGui.appendMessage("강퇴 대상(" + targetUser + ")을 찾을 수 없습니다.");
                                }
                            }
                       }
                   }else if(msg.startsWith("out/")) {
                         String[] parts3 = msg.split("/");
                         if (parts3.length >= 2) {
                             String logout = parts3[1];
                             if (logout != null) { // 추가된 null 체크
                                 ChatThread targetThread = null;
                                 for (ChatThread thread : chatlist) {
                                     if (thread.user != null && logout.equals(thread.user.getId())) { // 사용자 ID와 null 체크
                                         targetThread = thread;
                                          break;
                                     }
                                 }
                                 if (targetThread != null) {
                                    sendToAll("server : " + logout + "님이 나갔습니다.");
                                    serverGui.appendMessage(logout + "님이 나갔습니다.");
                                    chatlist.remove(targetThread);
                                    serverGui.removeUserList(logout);
                                    serverGui.appendMessage(targetThread.getName() + " stopped!");
                                    try {
                                        targetThread.writerToClient.close();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                      targetThread.interrupt();
                                    } else {
                                            // 대상 사용자를 찾을 수 없음을 처리하는 코드 추가
                                       serverGui.appendMessage("강퇴 대상(" + logout + ")을 찾을 수 없습니다.");
                                    }
                              } else {
                                // 대상 사용자가 null임을 처리하는 코드 추가
                                serverGui.appendMessage("강퇴 대상이 null입니다.");
                              }
                         }
                        
                    }else if(msg.startsWith("공지사항/")) {
                    	 String[] parts4 = msg.split("/");
                         String noticeMsg = parts4[1];
                         sendToAll(" 공지사항 : " + noticeMsg); 
                     }else if(msg.startsWith("^토끼^")) {
                    	 sendToAll(user.getId() + " : " + "(\\(\\");
                    	 sendToAll(user.getId() + " : " + "( -.-)");
                    	 sendToAll(user.getId() + " : " + "O_(\")(\")");
                     }
                     else if(msg.startsWith("^고양이^")) {
                    	 sendToAll(user.getId() + " : " + "/\\_/\\\r\n"
                    	 		+ "      ( o.o )\r\n"
                    	 		+ "       > ^ <");
                     }
                     else if(msg.startsWith("^헬리콥터^")) {
                    	 sendToAll(user.getId() + " : " + "▬▬▬.◙.▬▬▬\r\n"
                    	 		+ "═▂▄▄▓▄▄▂\r\n"
                    	 		+ "◢◤ █▀▀████▄▄▄▄◢◤\r\n"
                    	 		+ "█▄ █ █▄ ███▀▀▀▀▀▀▀╬\r\n"
                    	 		+ "◥█████◤ 사랑하는 사람 이름\r\n"
                    	 		+ "══╩══╩═ 부르고 하강!\r\n"
                    	 		+ "  ╬═╬\r\n"
                    	 		+ "  ╬═╬ ＼○ノ\r\n"
                    	 		+ "  ╬═╬    /\r\n"
                    	 		+ "  ╬═╬ ノ)\r\n"
                    	 		+ "  ╬═╬\r\n"
                    	 		+ "  ╬═╬\r\n"
                    	 		+ "  ╬═╬");
                     }
                 
                     else if(msg.startsWith("^부엉이^")) {
                    	 sendToAll(user.getId() + " : " + "＜￣｀ヽ、　　　　  ／￣＞\r\n"
                    	 		+ "　ゝ、　　＼　／⌒ヽ,ノ 　/´\r\n"
                    	 		+ "　　　ゝ、　`（ ´･ω･)／\r\n"
                    	 		+ "　　 　　>　 　 　,ノ 　\r\n"
                    	 		+ "　　　　　∠_,,,/´””");
                     }
                     else if(msg.startsWith("^찰칵찰칵^")) {
                    	 sendToAll(user.getId() + " : " + " 전 세상에서 젤 예쁩니다  \r\n"
                    	 		+ "￣￣￣￣∨￣￣￣￣\r\n"
                    	 		+ "　 ∧_,,∧\r\n"
                    	 		+ "　( ㅠ ㅠ )\r\n"
                    	 		+ "　 Ｕ P Ｕ\r\n"
                    	 		+ "／￣￣｜￣￣＼\r\n"
                    	 		+ "|二二二二二二二|\r\n"
                    	 		+ "｜　　 　　　 ｜\r\n"
                    	 		+ "찰칵       찰칵  찰칵\r\n"
                    	 		+ "　 ∧∧　    ∧∧  \r\n"
                    	 		+ "　(　　)】 (　　)】\r\n"
                    	 		+ "　/　/┘　/　/┘\r\n"
                    	 		+ "ノ￣ヽ　ノ￣ヽ 양심은 있나요?!");
                     }
                     else if(msg.startsWith("^오징어^")) {
                    	 sendToAll(user.getId() + " : " + "\n 　　／⌒ヽ\r\n"
                    	 		+ "　／　　　＼\r\n"
                    	 		+ "　/　　　　丶＼\r\n"
                    	 		+ "( /　　　　 丶　)\r\n"
                    	 		+ "/　　　　　丶”\r\n"
                    	 		+ "f　　　　　　i\r\n"
                    	 		+ "| ●　　●　 ｜\r\n"
                    	 		+ "|　 ▽　　　｜\r\n"
                    	 		+ "ヽ＿＿ 　 　ノ\r\n"
                    	 		+ "丿ﾉﾉ丁丁￣l＼\r\n"
                    	 		+ "く(_(_(＿L＿)ノ");
                     }
                    else {
                            sendToAll(user.getId() + " : " + msg); // 모든 클라이언트에 클라이언트의 메세지 전송
                            chatDb.inputChatDB(user.getId(), msg);
                        }
                    }
                

                //클라이언트 챗스레드 종료
                chatlist.remove(this);
                serverGui.removeUserList(user.getId());
                serverGui.appendMessage(this.getName() + " stopped!");
                this.interrupt();
            } catch (IOException e) {
                //클라이언트와 연결이 끊어짐
                serverGui.appendMessage("클라이언트 " + user.getId() + "의 연결이 끊어졌습니다");
                serverGui.appendMessage(this.getName() + " stopped!");
                serverGui.removeUserList(user.getId());
                chatlist.remove(this);
            }
        
    } // ChatThread 클래스
}
}

