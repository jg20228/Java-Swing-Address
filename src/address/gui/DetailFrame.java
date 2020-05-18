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
	private int memberId; // mainFrame에서 넘어온 member의 id값
	private Container backgroundPanel;
	private JLabel laName, laPhone, laAddress, laGroup;
	private JTextField tfName, tfPhone, tfAddress;
	private JComboBox<GroupType> cbGroup;
	private JButton updateButton, deleteButton;
	// 5.18
	private MemberService memberService = MemberService.getInstance();

	// 이 Frame이 꺼지기전에 MainFrame을 건드려야해서
	// MainFrame의 reference를 받아야함
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

		// 들어가서 생성자를 보면 배열로 넣어야함
		cbGroup = new JComboBox<>(GroupType.values());

		updateButton = new JButton("수정하기");
		deleteButton = new JButton("삭제하기");
	}

	private void initData() {
		// 이렇게 설계해야 협업하기가 좋다.
		// DetailFrame -> MemberService -> MemberDao의 상세보기() -> DB
		Member member = memberService.상세보기(memberId);

		// setText()는 내부적으로 repaint()를 들고 있다.
		tfName.setText(member.getName());
		tfPhone.setText(member.getPhone());
		tfAddress.setText(member.getAddress());
		// 콤보박스 4개중에 선택되게 해줌
		cbGroup.setSelectedItem(member.getGroupType());
	}

	private void initDesign() {
		setTitle("주소록 상세보기");
		setSize(300, 300);
		setLocationRelativeTo(null);

		// Exit_ON_CLOSE를하게 되면 mainFrame도 같이 꺼져서
		// DISPOSE_ON_CLOSE로 해야한다.
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
		// (target)을 정하는곳
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
				// 수정
				int result = memberService.수정하기(member);
				if (result == 1) {
					mainFrame.notifyUserList();
					detailFrame.dispose();
					mainFrame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "수정실패");
				}
				// this.dispose(); - 익명 클래스 내부라서 못찾음
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 삭제
				int result = memberService.삭제하기(memberId);
				// result == 1 이면 아래 로직 실행, 1이 아니면 다이어로그 박스(삭제실패)
				if (result == 1) {
					mainFrame.notifyUserList();
					detailFrame.dispose();
					mainFrame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "삭제실패");
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
