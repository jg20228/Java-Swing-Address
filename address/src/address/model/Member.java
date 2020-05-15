package address.model;

import lombok.Builder;
import lombok.Data;

//db 테이블이랑 똑같이 만듬
//회원 id는 username
@Data
public class Member {
	private int id; //PK 넘버링할때 쓴다, 시퀀스 쓰는게 제일 좋음
	private String name;
	private String phone;
	private String address;
	//그룹 : 친구, 회사, 학교, 가족
	//4개만 들어가야할때 도메인설정을 한다.
	//Enum을 생성 GroupType.java
	
	//private String gorup;
	private GroupType groupType;
	
	//사용할 생성자
	//Builder 패턴
	
//	public Member(String name, String phone, String address, GroupType group) {
//		this.name = name;
//		this.phone = phone;
//		this.address = address;
//		this.groupType = group;
//	}
	
	//더미데이터 생성자
	@Builder
	public Member(int id, String name, String phone, String address, GroupType groupType) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.groupType = groupType;
	}
	
	@Override
	public String toString() {
		return id+"."+name;
	}
}
