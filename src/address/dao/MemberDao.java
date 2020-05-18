package address.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import address.db.DBConnection;
import address.db.DBUtils;
import address.model.GroupType;
import address.model.Member;

//�̱���
public class MemberDao {
	
	private MemberDao() {}
	
	private static MemberDao instance = new MemberDao();
	
	public static MemberDao getInstance() {
		//�ʿ��Ѱ����� getInstance()�ϸ� ��
		return instance;
	}
	//synchronized static
	
	//select 3, insert 1, delete 1, update 1
	
	//DML�� return���� ������ int
	//-1�� return �ϴ°��� ������ �߻��ߴٴ� ���̴�.
	
	//parameter�� model�� �޴´�.
	public int �߰�(Member member) {
		final String SQL = "INSERT INTO member(id,name,phone,address,groupType) VALUES(member_seq.nextval,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//1.��Ʈ�� ����
			conn = DBConnection.getConnection();
			//2.���۴ޱ�(?�� �� �� �ִ� ����)
			pstmt = conn.prepareStatement(SQL);
			//3.?�ϼ�
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getPhone());
			pstmt.setString(3, member.getAddress());
			pstmt.setString(4, member.getGroupType().toString());
			//4. ���� ����(flush+commit)
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			System.out.println("�߰� ���� : "+e.getMessage());
			//������ �����ؼ� ������ �ʿ��Ҷ�
			//System.out.println("�߰� ���� : "+e.getStackTrace());
		} finally {//������ ����
			DBUtils.close(conn, pstmt);
		}
		return -1; 
	}
	
	//PK�� ����
	public int ����(int id) {
		final String SQL = "DELETE FROM member WHERE id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//1.��Ʈ�� ����
			conn = DBConnection.getConnection();
			//2.���۴ޱ�(?�� �� �� �ִ� ����)
			pstmt = conn.prepareStatement(SQL);
			//3.?�ϼ�
			pstmt.setInt(1, id);
			//4. ���� ����(flush+commit)
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			System.out.println("�����ϱ� ���� : "+e.getMessage());
			//������ �����ؼ� ������ �ʿ��Ҷ�
			//System.out.println("�߰� ���� : "+e.getStackTrace());
		} finally {//������ ����
			DBUtils.close(conn, pstmt);
		}
		return -1;
	}
	
	//��� ������ ���� �� �� ������ model�� ����
	public int ����(Member member) {
		final String SQL = "UPDATE member SET NAME = ? ,PHONE = ? ,ADDRESS = ? ,GROUPTYPE = ? WHERE id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//1.��Ʈ�� ����
			conn = DBConnection.getConnection();
			//2.���۴ޱ�(?�� �� �� �ִ� ����)
			pstmt = conn.prepareStatement(SQL);
			//3.?�ϼ�
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getPhone());
			pstmt.setString(3, member.getAddress());
			pstmt.setString(4, member.getGroupType().toString());
			pstmt.setInt(5, member.getId());
			//4. ���� ����(flush+rs�ޱ�)
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			System.out.println("���� ���� : "+e.getMessage());
			//������ �����ؼ� ������ �ʿ��Ҷ�
			//System.out.println("���� ���� : "+e.getStackTrace());
		} finally {//������ ����
			DBUtils.close(conn, pstmt);
		}
		return -1;
	}
	
	//DQL �� return���� ResultSet == Cursor
	//null�� ��ã��
	
	//PK���� id
	public Member �󼼺���(int id) {
		final String SQL = "SELECT * FROM member WHERE id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member member = null;
		try {
			//1.��Ʈ�� ����
			conn = DBConnection.getConnection();
			//2.���۴ޱ�(?�� �� �� �ִ� ����)
			pstmt = conn.prepareStatement(SQL);
			//3.?�ϼ�
			pstmt.setInt(1, id);
			//4. ���� ����(flush+rs�ޱ�)
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				member = Member.builder()
				.id(rs.getInt("id"))
				.name(rs.getString("name"))
				.phone(rs.getString("phone"))
				.address(rs.getString("address"))
				.groupType(GroupType.valueOf(rs.getString("groupType")))
				.build();
			}
			return member;
		} catch (Exception e) {
			System.out.println("�󼼺��� ���� : "+e.getMessage());
			//������ �����ؼ� ������ �ʿ��Ҷ�
			//System.out.println("�߰� ���� : "+e.getStackTrace());
		} finally {//������ ����
			DBUtils.close(conn, pstmt,rs);
		}
		return null; 
	}
	
	public List<Member> ��ü���() {
		final String SQL = "SELECT * FROM member ORDER BY id";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> members = new ArrayList<>();
		try {
			//1.��Ʈ�� ����
			conn = DBConnection.getConnection();
			//2.���۴ޱ�(?�� �� �� �ִ� ����)
			pstmt = conn.prepareStatement(SQL);
			//3.?�ϼ�
			//4. ���� ����(flush+rs�ޱ�)
			rs = pstmt.executeQuery();
			while(rs.next()) {
				members.add(Member.builder()
						.id(rs.getInt("id"))
						.name(rs.getString("name"))
						.phone(rs.getString("phone"))
						.address(rs.getString("address"))
						.groupType(GroupType.valueOf(rs.getString("groupType")))
						.build());
			}
			return members;
		} catch (Exception e) {
			System.out.println("��ü��� ����"+e.getMessage());
			//������ �����ؼ� ������ �ʿ��Ҷ�
			//System.out.println("��ü��� ���� : "+e.getStackTrace());
		} finally {//������ ����
			DBUtils.close(conn, pstmt,rs);
		}
		return null; 
	}
	
	//GorupType
	public List<Member> �׷���(GroupType groupType){
		final String SQL = "SELECT * FROM member WHERE groupType = ? ORDER BY id";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> members = new ArrayList<>();
		try {
			//1.��Ʈ�� ����
			conn = DBConnection.getConnection();
			//2.���۴ޱ�(?�� �� �� �ִ� ����)
			pstmt = conn.prepareStatement(SQL);
			//3.?�ϼ�
			pstmt.setString(1, groupType.toString());
			//4. ���� ����(flush+rs�ޱ�)
			rs = pstmt.executeQuery();
			while(rs.next()) {
				members.add(Member.builder()
						.id(rs.getInt("id"))
						.name(rs.getString("name"))
						.phone(rs.getString("phone"))
						.address(rs.getString("address"))
						.groupType(GroupType.valueOf(rs.getString("groupType")))
						.build());
			}
			return members;
		} catch (Exception e) {
			System.out.println("��ü��� ����"+e.getMessage());
			//������ �����ؼ� ������ �ʿ��Ҷ�
			//System.out.println("��ü��� ���� : "+e.getStackTrace());
		} finally {//������ ����
			DBUtils.close(conn, pstmt,rs);
		}
		return null; 
	}
	
}

