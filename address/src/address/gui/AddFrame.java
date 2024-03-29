package address.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import address.model.GroupType;
import address.model.Member;
import address.service.MemberService;

public class AddFrame extends JFrame {
	//test용
	private final static String TAG = "AddFrame :";
	
	private JFrame addFrame = this;
	private MainFrame mainFrame;
	private Container backgroundPanel;
	private JPanel addPanel;
	private JLabel laName, laPhone, laAddress, laGroup;
	private JTextField tfName, tfPhone, tfAddress;
	private JComboBox<GroupType> cbGorup;
	private JButton addButton;
	
	private MemberService memberService = MemberService.getInstance();
	
	//이 Frame이 꺼지기전에 MainFrame을 건드려야해서
	//MainFrame의 reference를 받아야함
	public AddFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		initObject();
		initDesign();
		initListener();
		setVisible(true);
	}
	private void initObject() {
		addPanel = new JPanel();
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
		
		addButton = new JButton("추가하기");
	}
	
	
	private void initDesign() {
		setTitle("주소록 추가하기");
		setSize(300, 300);
		setLocationRelativeTo(null);
		
		//Exit_ON_CLOSE를하게 되면 mainFrame도 같이 꺼져서
		//DISPOSE_ON_CLOSE로 해야한다.
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		backgroundPanel.setLayout(new BorderLayout());
		addPanel.setLayout(new GridLayout(4,2));
		
		addPanel.add(laName);
		addPanel.add(tfName);
		
		addPanel.add(laPhone);
		addPanel.add(tfPhone);
		
		addPanel.add(laAddress);
		addPanel.add(tfAddress);
		
		addPanel.add(laGroup);
		addPanel.add(cbGorup);
		
		backgroundPanel.add(addPanel,BorderLayout.CENTER);
		backgroundPanel.add(addButton,BorderLayout.SOUTH);
		
	}
	private void initListener() {
		//(target)을 정하는곳
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 1.TF에 있는 값을 가져옴
				// 2. 값을 Member에 담음
				System.out.println(TAG+"addButton 리스너:"+tfName.getText());
				Member member = Member.builder()
						//id는 시퀀스가 넣음
						.name(tfName.getText())
						.phone(tfPhone.getText())
						.address(tfAddress.getText())
						//콤보박스는 Object를 반환해줌
						.groupType(GroupType.valueOf(cbGorup.getSelectedItem().toString()))
						.build();
				
				int result = memberService.주소록추가(member);
				// 4. return값을 확인해서 로직을 직접짜야함(성공,실패)
				if(result==1) {
				// 5. 성공 = mainFrame에 값을 변경
					mainFrame.notifyUserList(); //ui를 동기화 해주는것
					addFrame.dispose();
					mainFrame.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "주소록 추가에 실패하였습니다.");
				}
				// 1이상 값이 있는것, 0 값 없음, -1 오류
				
				addFrame.dispose();
				mainFrame.setVisible(true);
				//this.dispose(); - 익명 클래스 내부라서 못찾음
			}
		});
		//WindowListener - 처음 씀 
		addFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				mainFrame.setVisible(true);
			}
		});
		
	}
}
