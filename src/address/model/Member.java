package address.model;

import lombok.Builder;
import lombok.Data;

//db ���̺��̶� �Ȱ��� ����
//ȸ�� id�� username
@Data
public class Member {
	private int id; //PK �ѹ����Ҷ� ����, ������ ���°� ���� ����
	private String name;
	private String phone;
	private String address;
	//�׷� : ģ��, ȸ��, �б�, ����
	//4���� �����Ҷ� �����μ����� �Ѵ�.
	//Enum�� ���� GroupType.java
	
	//private String gorup;
	private GroupType groupType;
	
	//����� ������
	//Builder ����
	
//	public Member(String name, String phone, String address, GroupType group) {
//		this.name = name;
//		this.phone = phone;
//		this.address = address;
//		this.groupType = group;
//	}
	
	//���̵����� ������
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
