package screen;

import java.awt.Font;
import java.awt.Panel;
import java.awt.TextArea;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DataBase.ChatDB;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
public class CalendarGui extends JFrame {
	private TextArea textArea;
	ChatDB chatDb = new ChatDB();
	
	public CalendarGui() {
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);

		JPanel panel = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 600);
		setLocationRelativeTo(null); // 화면 중앙에 표시
		setContentPane(panel);
		panel.setLayout(null);

		// DatePicker의 크기와 위치 설정
		datePicker.setBounds(18,60,346,330); // 임의의 위치와 크기로 설정

		JLabel ad = new JLabel("날짜별 조회");
		Font font = new Font("마루 부리 굵은", Font.BOLD, 20);
		ad.setFont(font);
		ad.setBounds(140,20,300, 30);
		panel.add(ad);

		JButton logout = new JButton("돌아가기");
		logout.setBounds(270,500, 100, 30);
		panel.add(logout);

		textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setBounds(18,95,346, 350);
		panel.add(textArea);

		// DatePicker를 panel에 추가
		panel.add(datePicker);

		setVisible(true);



	
		logout.addActionListener(event -> {//나가기 버튼 누를시 
			new ChatlogGUi();
			dispose();
	    });
		

		model.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				StringBuilder showData = new StringBuilder();
				if ("value".equals(evt.getPropertyName())) {// 선택한 날짜를 출력
					if (model.getValue() != null) {
						SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
						String selectDate = date.format(model.getValue());
						ArrayList<String> chatData = chatDb.selectDateChat(selectDate);
						for (int i = 0; i < chatData.size(); i++) {
							showData.append(chatData.get(i)+"\n");
						}
						textArea.setText(showData.toString());
						
					}
				}
			}
		});

	}
	
}