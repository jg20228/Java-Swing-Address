package address.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import address.model.GroupType;
import address.model.Member;
import address.service.MemberService;
import address.utils.MyStringParser;

public class MainFrame extends JFrame {
	// Grid�� 2,1 Panel North
	// Grid�� 1,4 Panel�� ��ư4�� North
	// JList Center
	// �߰���ư South
	
	private MemberService memberService = MemberService.getInstance();
	
	private MainFrame mainFrame = this;
	
	private Container backGroundPanel;
	private JPanel topPanel, menuPanel, listPanel;
	private JButton homeButton, frButton, coButton, scButton, faButton, addButton;
	private JList<Member> userList;
	private DefaultListModel<Member> listModel;
	private JScrollPane jspane;

	public MainFrame() {
		initObject();
		initData();
		initDesign();
		initListener();
		setVisible(true);
	}

	// ��ü ����
	private void initObject() {
		backGroundPanel = getContentPane();
		topPanel = new JPanel();
		menuPanel = new JPanel();
		listPanel = new JPanel();

		homeButton = new JButton("�ּҷ� ��ü");
		frButton = new JButton("ģ��");
		coButton = new JButton("ȸ��");
		scButton = new JButton("�б�");
		faButton = new JButton("����");
		addButton = new JButton("�߰�");

		// ���� JList�� ���� ��ƾ���
		listModel = new DefaultListModel<Member>();
		userList = new JList<Member>(listModel);

		jspane = new JScrollPane(userList);
	}

	// ������ �ʱ�ȭ
	private void initData() {
		
		List<Member> members = memberService.��ü���();
		for (Member member : members) {
			listModel.addElement(member);
		}
		
//---2	for (int i = 1; i < 31; i++) {
//			//Builder ����
//			//id~address��� �Լ��� �ڱ��ڽ��� return�Ѵ�.
//			//Singleton���� �ٸ��� new�� ��
//			//new Member�� bulider�� ������
//			Member member = Member.builder()
//					.id(i)
//					.phone("0102222")
//					.name("ȫ�浿")
//					.address("�λ��")
//					.groupType(GroupType.ģ��)
//					.build();
//			listModel.addElement(member);
			//���� ���� ����.
//---1		listModel.addElement(Member.builder()
//					.id(i)
//					.phone("0102222")
//					.name("ȫ�浿")
//					.address("�λ��")
//					.group(GroupType.ģ��)
//					.build());
			
			//listModel.addElement(new Member(i, "ȫ�浿", "0102222", "�λ��", GroupType.ģ��));
	}

	// ������
	private void initDesign() {
		// 1.�⺻ ����
		setTitle("�ּҷ� ����");
		setSize(400, 500);
		setLocationRelativeTo(null);// ����� �߾ӿ� ��ġ
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 2. �г� ����
		backGroundPanel.setLayout(new BorderLayout());
		topPanel.setLayout(new GridLayout(2, 1));
		menuPanel.setLayout(new GridLayout(1, 4));
		listPanel.setLayout(new BorderLayout());// BorderLayout�����ϸ� center�� ������ ��

		// 3. ������
		userList.setFixedCellHeight(50); // ����Ʈ ������ ����
		topPanel.setPreferredSize(new Dimension(0, 100));// �׸���� ���δ� ������ �۵��� �̹��ؼ� �۵����� 0�� �൵ ��

		// 4. �г�(������Ʈ)�� ������Ʈ �߰�
		menuPanel.add(frButton);
		menuPanel.add(coButton);
		menuPanel.add(scButton);
		menuPanel.add(faButton);

		topPanel.add(homeButton);
		topPanel.add(menuPanel);
		listPanel.add(jspane);

		backGroundPanel.add(topPanel, BorderLayout.NORTH);
		backGroundPanel.add(listPanel, BorderLayout.CENTER);
		backGroundPanel.add(addButton, BorderLayout.SOUTH);

	}

	// ������ ���
	private void initListener() {
		userList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//index ��ȣ�� �������� ����ִ� ������ �ؾ��Ѵ�.
				//���� - ������ �Ͼ ��� ��ȣ�� �����Ǳ� �����̴�.
				//System.out.println(userList.getSelectedIndex());
				//System.out.println(userList.getSelectedValue());
				int memberId = MyStringParser.getId(userList.getSelectedValue().toString());
				new DetailFrame(mainFrame,memberId);
				//5.18
				mainFrame.setVisible(false);
			}
			
		});
		
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddFrame(mainFrame);
				mainFrame.setVisible(false);
			}
		});
		
		homeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyUserList();
			}
		});
		
		frButton.addActionListener(/*os�� �̺κ����� �ݹ�����*/new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyUserList(GroupType.ģ��);
			}
		});
		coButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyUserList(GroupType.ȸ��);
			}
		});
		scButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyUserList(GroupType.�б�);
			}
		});
		faButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyUserList(GroupType.����);
			}
		});
		
//		addButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				new AddFrame(mainFrame);
//			}
//		});
		
	}
	//�׷� ������ ���� -> �����ε�
	public void notifyUserList() {
		// 1. listModel ����!!
		listModel.clear();
		// 2. select �ؼ� ��ü��� ��������!! List<Member>�� ���
		// 3. listModel ä���ְ� (userList �ڵ�����)
		initData();
	}	
	public void notifyUserList(GroupType groupType) {
		// 1. listModel ����!!
		listModel.clear();
		// 2. select �ؼ� ��ü��� ��������!! List<Member>�� ���
		// 3. listModel ä���ְ� (userList �ڵ�����)
		List<Member> members = memberService.�׷���(groupType);
		for (Member member : members) {
			listModel.addElement(member);
		}
	}	
}
