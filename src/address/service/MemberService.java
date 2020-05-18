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
	
	//서비스 이름
	public int 주소록추가(Member member) {
		// 3. DAO에 접근해서 추가 함수 호출(Member)
		// 1이상 값이 있는것, 0 값 없음, -1 오류
		return memberDao.추가(member);
		//트랜잭션이 복잡해지면 여기서 -1같은 오류처리를 생각해야한다.
	}
	
	
	//나중에 여기서 commit과 rollback을 관리 할 수 있다.
	//함수를 try~catch로 묶어서 rollback을 하면 트랜잭션 관리가 가능하다.
	public List<Member> 전체목록() {
		return memberDao.전체목록();
		
	}
	
	public Member 상세보기(int memberId) {
		return memberDao.상세보기(memberId);
	}
	
	public int 삭제하기(int memberId) {
		return memberDao.삭제(memberId);
	}
	
	public int 수정하기(Member member) {
		return memberDao.수정(member);
	}
	
	public List<Member> 그룹목록(GroupType groupType){
		return memberDao.그룹목록(groupType);
	}
	
}
