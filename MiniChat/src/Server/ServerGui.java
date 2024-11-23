package Server;



import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ServerGui {
    	private JFrame serverFrame;
    	private TextArea chatTextArea;
    	protected TextField chatTextField;
    	private JList list;
    	public static DefaultListModel<String> model;
    	public ServerGui(){

        serverFrame = new JFrame();
        serverFrame.setDefaultCloseOperation(serverFrame.EXIT_ON_CLOSE);
        serverFrame.setBounds(100, 100, 825, 475);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        serverFrame.setContentPane(contentPane);
        contentPane.setLayout(null);

        chatTextArea = new TextArea();
        chatTextArea.setEditable(false);
        chatTextArea.setBounds(18,12,567,384);
        chatTextArea.setBackground(Color.WHITE);
        contentPane.add(chatTextArea);

        chatTextField = new TextField();
        chatTextField.setColumns(30);
        chatTextField.setBounds(17, 403, 572, 22);
        contentPane.add(chatTextField);


        Label userListLabel = new Label("CHAT USER ");
        userListLabel.setBounds(667, 16, 100, 16);
        contentPane.add(userListLabel);
        
        model = new DefaultListModel<String>();

        list = new JList(model);
        list.setBounds(602, 41, 199, 373);
        list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        contentPane.add(list);
        
       

        serverFrame.setResizable(false);
        serverFrame.setTitle("채팅프로그램 서버");
        serverFrame.setVisible(true);


    }

    public void setFrameVisible(){
        serverFrame.setVisible(true);
    }

    public void setTextFieldBlank(){
        chatTextField.setText(null);
    }

    public void appendMessage(String message){
        chatTextArea.append(message + "\n");
    }
    public void appendUserList(String user){
        model.addElement(user);
    }
    public void removeUserList(String user){
        model.removeElement(user);
    }
    public String getChatMessage(){ return chatTextField.getText(); }
}