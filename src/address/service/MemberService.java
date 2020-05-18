package address.service;

import java.util.List;

import address.dao.MemberDao;
import address.model.GroupType;
import address.model.Member;

public class MemberService {
	private MemberService() {}
	private static MemberService instance = new MemberService();
	public static MemberService getInstance() {
		return instance;
	}
	private MemberDao memberDao = MemberDao.getInstance();
	
	//���� �̸�
	public int �ּҷ��߰�(Member member) {
		// 3. DAO�� �����ؼ� �߰� �Լ� ȣ��(Member)
		// 1�̻� ���� �ִ°�, 0 �� ����, -1 ����
		return memberDao.�߰�(member);
		//Ʈ������� ���������� ���⼭ -1���� ����ó���� �����ؾ��Ѵ�.
	}
	
	
	//���߿� ���⼭ commit�� rollback�� ���� �� �� �ִ�.
	//�Լ��� try~catch�� ��� rollback�� �ϸ� Ʈ����� ������ �����ϴ�.
	public List<Member> ��ü���() {
		return memberDao.��ü���();
		
	}
	
	public Member �󼼺���(int memberId) {
		return memberDao.�󼼺���(memberId);
	}
	
	public int �����ϱ�(int memberId) {
		return memberDao.����(memberId);
	}
	
	public int �����ϱ�(Member member) {
		return memberDao.����(member);
	}
	
	public List<Member> �׷���(GroupType groupType){
		return memberDao.�׷���(groupType);
	}
	
}
