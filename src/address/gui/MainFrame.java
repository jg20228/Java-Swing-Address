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
	// Grid로 2,1 Panel North
	// Grid로 1,4 Panel에 버튼4개 North
	// JList Center
	// 추가버튼 South
	
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

	// 객체 생성
	private void initObject() {
		backGroundPanel = getContentPane();
		topPanel = new JPanel();
		menuPanel = new JPanel();
		listPanel = new JPanel();

		homeButton = new JButton("주소록 전체");
		frButton = new JButton("친구");
		coButton = new JButton("회사");
		scButton = new JButton("학교");
		faButton = new JButton("가족");
		addButton = new JButton("추가");

		// 순서 JList에 모델을 담아야함
		listModel = new DefaultListModel<Member>();
		userList = new JList<Member>(listModel);

		jspane = new JScrollPane(userList);
	}

	// 데이터 초기화
	private void initData() {
		
		List<Member> members = memberService.전체목록();
		for (Member member : members) {
			listModel.addElement(member);
		}
		
//---2	for (int i = 1; i < 31; i++) {
//			//Builder 패턴
//			//id~address라는 함수가 자기자신을 return한다.
//			//Singleton과는 다르게 new를 함
//			//new Member와 bulider의 차이점
//			Member member = Member.builder()
//					.id(i)
//					.phone("0102222")
//					.name("홍길동")
//					.address("부산시")
//					.groupType(GroupType.친구)
//					.build();
//			listModel.addElement(member);
			//위와 밑은 같다.
//---1		listModel.addElement(Member.builder()
//					.id(i)
//					.phone("0102222")
//					.name("홍길동")
//					.address("부산시")
//					.group(GroupType.친구)
//					.build());
			
			//listModel.addElement(new Member(i, "홍길동", "0102222", "부산시", GroupType.친구));
	}

	// 디자인
	private void initDesign() {
		// 1.기본 세팅
		setTitle("주소록 메인");
		setSize(400, 500);
		setLocationRelativeTo(null);// 모니터 중앙에 배치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 2. 패널 세팅
		backGroundPanel.setLayout(new BorderLayout());
		topPanel.setLayout(new GridLayout(2, 1));
		menuPanel.setLayout(new GridLayout(1, 4));
		listPanel.setLayout(new BorderLayout());// BorderLayout으로하면 center로 꽉차게 들어감

		// 3. 디자인
		userList.setFixedCellHeight(50); // 리스트 각각의 높이
		topPanel.setPreferredSize(new Dimension(0, 100));// 그리드라서 가로는 꽉차게 작동을 이미해서 작동안함 0을 줘도 됨

		// 4. 패널(컴포넌트)에 컴포넌트 추가
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

	// 리스너 등록
	private void initListener() {
		userList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//index 번호로 하지말고 들고있는 값으로 해야한다.
				//이유 - 삭제가 일어날 경우 번호가 변동되기 때문이다.
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
		
		frButton.addActionListener(/*os가 이부분으로 콜백해줌*/new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyUserList(GroupType.친구);
			}
		});
		coButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyUserList(GroupType.회사);
			}
		});
		scButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyUserList(GroupType.학교);
			}
		});
		faButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyUserList(GroupType.가족);
			}
		});
		
//		addButton.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				new AddFrame(mainFrame);
//			}
//		});
		
	}
	//그룹 데이터 갱신 -> 오버로딩
	public void notifyUserList() {
		// 1. listModel 비우고!!
		listModel.clear();
		// 2. select 해서 전체목록 가져오고!! List<Member>에 담기
		// 3. listModel 채워주고 (userList 자동갱신)
		initData();
	}	
	public void notifyUserList(GroupType groupType) {
		// 1. listModel 비우고!!
		listModel.clear();
		// 2. select 해서 전체목록 가져오고!! List<Member>에 담기
		// 3. listModel 채워주고 (userList 자동갱신)
		List<Member> members = memberService.그룹목록(groupType);
		for (Member member : members) {
			listModel.addElement(member);
		}
	}	
}
