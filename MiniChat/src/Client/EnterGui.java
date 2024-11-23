package Client;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.sql.SQLException;

import DataBase.LoginlogDB;
import DataBase.AccessDB;
import DataBase.UserDB;
import Server.AdminMain;

public class EnterGui extends JFrame {
    private JTextField idTxt; // 아이디 입력 필드
    private JPasswordField pwTxt; // 비밀번호 입력 필드
    UserDB userDb = new UserDB();
    LoginlogDB loginlogDb= new LoginlogDB();
    AccessDB accessDB= new AccessDB();
    String id;
    Color purpleColor = new Color(221,160,221);
    Color yellowColor = new Color(255,248,220);
    Color pinkColor = new Color(255, 204, 204); 
    
    /*여기에 절대주소 경로를 입력해주세요^^*/
    public static String path = "C:/Users/bit/eclipse-workspace/MiniProject/src/image/";

    public EnterGui() {
        try {
            login();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void login() throws SQLException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Font font = new Font("마루 부리 굵은", Font.BOLD, 15);
        UIManager.put("Button.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("TextArea.font", font);
       
        UIManager.put("Panel.background", pinkColor);
        Color whiteColor = new Color(255, 255, 255); 
        UIManager.put("Button.background", whiteColor);
//        UIManager.put("Label.background", pinkColor);
//        UIManager.put("TextField.background", pinkColor);
//        UIManager.put("TextArea.background", pinkColor);
//        
        
        setSize(400, 600);
        setLocationRelativeTo(null); // 화면 중앙에 표시
        JPanel panel = new JPanel();
        panel.setLayout(null); // 레이아웃 매니저를 null로 설정하여 자유로운 위치 조정 가능
        
        
        ImageIcon corbyIcon = new ImageIcon(path +"corbyMain.gif");
        Image imageCorby = corbyIcon.getImage().getScaledInstance(200, 150, Image.SCALE_DEFAULT);
        JLabel corby = new JLabel(new ImageIcon(imageCorby));
        corby.setBounds(40, 50, 300, 200); // 좌표와 크기 설정
        panel.add(corby);
        
        ImageIcon starIcon = new ImageIcon(path +"star.gif");
        Image imageStar = starIcon.getImage().getScaledInstance(115, 104, Image.SCALE_DEFAULT);
        JLabel star = new JLabel(new ImageIcon(imageStar));
        star.setBounds(0, 500,115, 104); // 좌표와 크기 설정
        panel.add(star);
        
        ImageIcon blueStarIcon = new ImageIcon(path +"blueStar.gif");
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
        
        
        

        JLabel idLabel = new JLabel("아이디");
        idTxt = new JTextField(10);
        idLabel.setBounds(50, 270, 100, 30); // 좌표와 크기 설정
        idTxt.setBounds(150, 270, 200, 30); // 좌표와 크기 설정
        panel.add(idLabel);
        panel.add(idTxt);

        JLabel pwLabel = new JLabel("비밀번호");
        pwTxt = new JPasswordField(10);
        pwLabel.setBounds(50, 320, 100, 30); // 좌표와 크기 설정
        pwTxt.setBounds(150, 320, 200, 30); // 좌표와 크기 설정
        panel.add(pwLabel);
        panel.add(pwTxt);

        JButton loginBtn = new JButton("로그인");
        JButton chatJoinBtn = new JButton("회원가입");
        JButton exitBtn = new JButton("종료");

        loginBtn.setBounds(50, 420, 100, 30); // 좌표와 크기 설정
        chatJoinBtn.setBounds(150, 420, 100, 30); // 좌표와 크기 설정
        exitBtn.setBounds(250, 420, 100, 30); // 좌표와 크기 설정

        loginBtn.addActionListener(event -> {
            id = idTxt.getText();
            String pw = new String(pwTxt.getPassword());
            if (id.isEmpty() || pw.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID,PW가 제대로 입력되지 않았습니다.", "입력오류", JOptionPane.ERROR_MESSAGE);
            } else {
                if (userDb.isExistingLogin(id, pw)) {
                    if (id.equals("admin") && pw.equals("1234")) {
                        new AdminMain();
                        dispose();
                    } else {
                        loginlogDb.loginUser(id, "login");

                        switch(userDb.selectColor(id)) {
                        	case "purpleColor":
                        		UIManager.put("Panel.background",purpleColor );
                        		break;
                        	case "yellowColor":
                        		UIManager.put("Panel.background",yellowColor );
                        		break;
                        	case "pinkColor":
                        		UIManager.put("Panel.background",pinkColor);
                        		break;
                        }

                        new UserMain(id);
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "ID,PW가 올바르지 않습니다.", "입력오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        chatJoinBtn.addActionListener(event -> {
            new ChatJoin();
            dispose();
        });

        exitBtn.addActionListener(event -> {
            dispose();
        });

        panel.add(loginBtn);
        panel.add(chatJoinBtn);
        panel.add(exitBtn);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new EnterGui();
    }
}
