package address.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUtils {
	//INSERT,UPDATE,DELETE
	public static void close(Connection conn, PreparedStatement pstmt) {
		try {
			conn.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println("DB종료시 오류가 발생 : "+e.getMessage());
		}
	}
	//SELECT
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			conn.close();
			pstmt.close();
			rs.close();
		} catch (Exception e) {
			System.out.println("DB종료시 오류가 발생 : "+e.getMessage());
		}
	}
}
