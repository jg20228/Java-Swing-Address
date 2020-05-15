package address.gui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import address.model.GroupType;

public class DetailFrame extends JFrame {

	private JFrame detailFrame = this;
	private MainFrame mainFrame;
	private int memberId; //mainFrame에서 넘어온 member의 id값
	private Container backgroundPanel;
	private JLabel laName, laPhone, laAddress, laGroup;
	private JTextField tfName, tfPhone, tfAddress;
	private JComboBox<GroupType> cbGorup;
	private JButton updateButton, deleteButton;
	
	//이 Frame이 꺼지기전에 MainFrame을 건드려야해서
	//MainFrame의 reference를 받아야함
	public DetailFrame(MainFrame mainFrame, int memberId) {
		this.mainFrame = mainFrame;
		this.memberId = memberId;
		initObject();
		initData();
		initDesign();
		initListener();
		setVisible(true);
	}
	private void initObject() {
		backgroundPanel = getContentPane();
		laName = new JLabel("이름");
		laPhone = new JLabel("전화번호");
		laAddress = new JLabel("주소");
		laGroup = new JLabel("그룹");
		
		tfName = new JTextField(20);
		tfPhone = new JTextField(20);
		tfAddress = new JTextField(20);
		
		//들어가서 생성자를 보면 배열로 넣어야함
		cbGorup = new JComboBox<>(GroupType.values());
		
		updateButton = new JButton("수정하기");
		deleteButton = new JButton("삭제하기");
	}
	
	private void initData() {
		
		
	}
	
	private void initDesign() {
		setTitle("주소록 상세보기");
		setSize(300, 300);
		setLocationRelativeTo(null);
		
		//Exit_ON_CLOSE를하게 되면 mainFrame도 같이 꺼져서
		//DISPOSE_ON_CLOSE로 해야한다.
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		backgroundPanel.setLayout(new GridLayout(5,2));
		
		backgroundPanel.add(laName);
		backgroundPanel.add(tfName);
		
		backgroundPanel.add(laPhone);
		backgroundPanel.add(tfPhone);
		
		backgroundPanel.add(laAddress);
		backgroundPanel.add(tfAddress);
		
		backgroundPanel.add(laGroup);
		backgroundPanel.add(cbGorup);
		
		backgroundPanel.add(updateButton);
		backgroundPanel.add(deleteButton);
		
	}
	private void initListener() {
		//(target)을 정하는곳
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				detailFrame.dispose();
				//this.dispose(); - 익명 클래스 내부라서 못찾음
			}
		});
		
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				detailFrame.dispose();
			}
		});
	}
}
