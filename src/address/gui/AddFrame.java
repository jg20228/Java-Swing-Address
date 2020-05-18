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
	//test��
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
	
	//�� Frame�� ���������� MainFrame�� �ǵ�����ؼ�
	//MainFrame�� reference�� �޾ƾ���
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
		laName = new JLabel("�̸�");
		laPhone = new JLabel("��ȭ��ȣ");
		laAddress = new JLabel("�ּ�");
		laGroup = new JLabel("�׷�");
		
		tfName = new JTextField(20);
		tfPhone = new JTextField(20);
		tfAddress = new JTextField(20);
		
		
		//���� �����ڸ� ���� �迭�� �־����
		cbGorup = new JComboBox<>(GroupType.values());
		
		addButton = new JButton("�߰��ϱ�");
	}
	
	
	private void initDesign() {
		setTitle("�ּҷ� �߰��ϱ�");
		setSize(300, 300);
		setLocationRelativeTo(null);
		
		//Exit_ON_CLOSE���ϰ� �Ǹ� mainFrame�� ���� ������
		//DISPOSE_ON_CLOSE�� �ؾ��Ѵ�.
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
		//(target)�� ���ϴ°�
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 1.TF�� �ִ� ���� ������
				// 2. ���� Member�� ����
				System.out.println(TAG+"addButton ������:"+tfName.getText());
				Member member = Member.builder()
						//id�� �������� ����
						.name(tfName.getText())
						.phone(tfPhone.getText())
						.address(tfAddress.getText())
						//�޺��ڽ��� Object�� ��ȯ����
						.groupType(GroupType.valueOf(cbGorup.getSelectedItem().toString()))
						.build();
				
				int result = memberService.�ּҷ��߰�(member);
				// 4. return���� Ȯ���ؼ� ������ ����¥����(����,����)
				if(result==1) {
				// 5. ���� = mainFrame�� ���� ����
					mainFrame.notifyUserList(); //ui�� ����ȭ ���ִ°�
					addFrame.dispose();
					mainFrame.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "�ּҷ� �߰��� �����Ͽ����ϴ�.");
				}
				// 1�̻� ���� �ִ°�, 0 �� ����, -1 ����
				
				addFrame.dispose();
				mainFrame.setVisible(true);
				//this.dispose(); - �͸� Ŭ���� ���ζ� ��ã��
			}
		});
		//WindowListener - ó�� �� 
		addFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				mainFrame.setVisible(true);
			}
		});
		
	}
}
