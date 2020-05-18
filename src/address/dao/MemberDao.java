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

//싱글톤
public class MemberDao {
	
	private MemberDao() {}
	
	private static MemberDao instance = new MemberDao();
	
	public static MemberDao getInstance() {
		//필요한곳에서 getInstance()하면 됨
		return instance;
	}
	//synchronized static
	
	//select 3, insert 1, delete 1, update 1
	
	//DML은 return값이 무조건 int
	//-1을 return 하는것은 오류가 발생했다는 뜻이다.
	
	//parameter를 model로 받는다.
	public int 추가(Member member) {
		final String SQL = "INSERT INTO member(id,name,phone,address,groupType) VALUES(member_seq.nextval,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//1.스트림 연결
			conn = DBConnection.getConnection();
			//2.버퍼달기(?를 쓸 수 있는 버퍼)
			pstmt = conn.prepareStatement(SQL);
			//3.?완성
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getPhone());
			pstmt.setString(3, member.getAddress());
			pstmt.setString(4, member.getGroupType().toString());
			//4. 쿼리 전송(flush+commit)
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			System.out.println("추가 오류 : "+e.getMessage());
			//오류를 추적해서 수정이 필요할때
			//System.out.println("추가 오류 : "+e.getStackTrace());
		} finally {//무조건 실행
			DBUtils.close(conn, pstmt);
		}
		return -1; 
	}
	
	//PK만 받음
	public int 삭제(int id) {
		final String SQL = "DELETE FROM member WHERE id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//1.스트림 연결
			conn = DBConnection.getConnection();
			//2.버퍼달기(?를 쓸 수 있는 버퍼)
			pstmt = conn.prepareStatement(SQL);
			//3.?완성
			pstmt.setInt(1, id);
			//4. 쿼리 전송(flush+commit)
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			System.out.println("삭제하기 오류 : "+e.getMessage());
			//오류를 추적해서 수정이 필요할때
			//System.out.println("추가 오류 : "+e.getStackTrace());
		} finally {//무조건 실행
			DBUtils.close(conn, pstmt);
		}
		return -1;
	}
	
	//모든 정보를 수정 할 수 있으면 model로 받음
	public int 수정(Member member) {
		final String SQL = "UPDATE member SET NAME = ? ,PHONE = ? ,ADDRESS = ? ,GROUPTYPE = ? WHERE id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			//1.스트림 연결
			conn = DBConnection.getConnection();
			//2.버퍼달기(?를 쓸 수 있는 버퍼)
			pstmt = conn.prepareStatement(SQL);
			//3.?완성
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getPhone());
			pstmt.setString(3, member.getAddress());
			pstmt.setString(4, member.getGroupType().toString());
			pstmt.setInt(5, member.getId());
			//4. 쿼리 전송(flush+rs받기)
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			System.out.println("수정 오류 : "+e.getMessage());
			//오류를 추적해서 수정이 필요할때
			//System.out.println("수정 오류 : "+e.getStackTrace());
		} finally {//무조건 실행
			DBUtils.close(conn, pstmt);
		}
		return -1;
	}
	
	//DQL 은 return값이 ResultSet == Cursor
	//null은 못찾음
	
	//PK값인 id
	public Member 상세보기(int id) {
		final String SQL = "SELECT * FROM member WHERE id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member member = null;
		try {
			//1.스트림 연결
			conn = DBConnection.getConnection();
			//2.버퍼달기(?를 쓸 수 있는 버퍼)
			pstmt = conn.prepareStatement(SQL);
			//3.?완성
			pstmt.setInt(1, id);
			//4. 쿼리 전송(flush+rs받기)
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
			System.out.println("상세보기 오류 : "+e.getMessage());
			//오류를 추적해서 수정이 필요할때
			//System.out.println("추가 오류 : "+e.getStackTrace());
		} finally {//무조건 실행
			DBUtils.close(conn, pstmt,rs);
		}
		return null; 
	}
	
	public List<Member> 전체목록() {
		final String SQL = "SELECT * FROM member ORDER BY id";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> members = new ArrayList<>();
		try {
			//1.스트림 연결
			conn = DBConnection.getConnection();
			//2.버퍼달기(?를 쓸 수 있는 버퍼)
			pstmt = conn.prepareStatement(SQL);
			//3.?완성
			//4. 쿼리 전송(flush+rs받기)
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
			System.out.println("전체목록 오류"+e.getMessage());
			//오류를 추적해서 수정이 필요할때
			//System.out.println("전체목록 오류 : "+e.getStackTrace());
		} finally {//무조건 실행
			DBUtils.close(conn, pstmt,rs);
		}
		return null; 
	}
	
	//GorupType
	public List<Member> 그룹목록(GroupType groupType){
		final String SQL = "SELECT * FROM member WHERE groupType = ? ORDER BY id";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> members = new ArrayList<>();
		try {
			//1.스트림 연결
			conn = DBConnection.getConnection();
			//2.버퍼달기(?를 쓸 수 있는 버퍼)
			pstmt = conn.prepareStatement(SQL);
			//3.?완성
			pstmt.setString(1, groupType.toString());
			//4. 쿼리 전송(flush+rs받기)
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
			System.out.println("전체목록 오류"+e.getMessage());
			//오류를 추적해서 수정이 필요할때
			//System.out.println("전체목록 오류 : "+e.getStackTrace());
		} finally {//무조건 실행
			DBUtils.close(conn, pstmt,rs);
		}
		return null; 
	}
	
}

