package address.gui;

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
import javax.swing.JTextField;

import address.model.GroupType;
import address.model.Member;
import address.service.MemberService;

public class DetailFrame extends JFrame {
	
	
	
	private JFrame detailFrame = this;
	private MainFrame mainFrame;
	private int memberId; // mainFrame���� �Ѿ�� member�� id��
	private Container backgroundPanel;
	private JLabel laName, laPhone, laAddress, laGroup;
	private JTextField tfName, tfPhone, tfAddress;
	private JComboBox<GroupType> cbGroup;
	private JButton updateButton, deleteButton;
	// 5.18
	private MemberService memberService = MemberService.getInstance();

	// �� Frame�� ���������� MainFrame�� �ǵ�����ؼ�
	// MainFrame�� reference�� �޾ƾ���
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
		laName = new JLabel("�̸�");
		laPhone = new JLabel("��ȭ��ȣ");
		laAddress = new JLabel("�ּ�");
		laGroup = new JLabel("�׷�");

		tfName = new JTextField(20);
		tfPhone = new JTextField(20);
		tfAddress = new JTextField(20);

		// ���� �����ڸ� ���� �迭�� �־����
		cbGroup = new JComboBox<>(GroupType.values());

		updateButton = new JButton("�����ϱ�");
		deleteButton = new JButton("�����ϱ�");
	}

	private void initData() {
		// �̷��� �����ؾ� �����ϱⰡ ����.
		// DetailFrame -> MemberService -> MemberDao�� �󼼺���() -> DB
		Member member = memberService.�󼼺���(memberId);

		// setText()�� ���������� repaint()�� ��� �ִ�.
		tfName.setText(member.getName());
		tfPhone.setText(member.getPhone());
		tfAddress.setText(member.getAddress());
		// �޺��ڽ� 4���߿� ���õǰ� ����
		cbGroup.setSelectedItem(member.getGroupType());
	}

	private void initDesign() {
		setTitle("�ּҷ� �󼼺���");
		setSize(300, 300);
		setLocationRelativeTo(null);

		// Exit_ON_CLOSE���ϰ� �Ǹ� mainFrame�� ���� ������
		// DISPOSE_ON_CLOSE�� �ؾ��Ѵ�.
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		backgroundPanel.setLayout(new GridLayout(5, 2));

		backgroundPanel.add(laName);
		backgroundPanel.add(tfName);

		backgroundPanel.add(laPhone);
		backgroundPanel.add(tfPhone);

		backgroundPanel.add(laAddress);
		backgroundPanel.add(tfAddress);

		backgroundPanel.add(laGroup);
		backgroundPanel.add(cbGroup);

		backgroundPanel.add(updateButton);
		backgroundPanel.add(deleteButton);

	}

	private void initListener() {
		// (target)�� ���ϴ°�
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Member member = Member.builder()
						.id(memberId)
						.name(tfName.getText())
						.phone(tfPhone.getText())
						.address(tfAddress.getText())
						.groupType(GroupType.valueOf(cbGroup.getSelectedItem().toString()))
						.build();
				// ����
				int result = memberService.�����ϱ�(member);
				if (result == 1) {
					mainFrame.notifyUserList();
					detailFrame.dispose();
					mainFrame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "��������");
				}
				// this.dispose(); - �͸� Ŭ���� ���ζ� ��ã��
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ����
				int result = memberService.�����ϱ�(memberId);
				// result == 1 �̸� �Ʒ� ���� ����, 1�� �ƴϸ� ���̾�α� �ڽ�(��������)
				if (result == 1) {
					mainFrame.notifyUserList();
					detailFrame.dispose();
					mainFrame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "��������");
				}
			}
		});

		detailFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				mainFrame.setVisible(true);
			}
		});
	}
}
