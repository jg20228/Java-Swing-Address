package address.db;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class DBConnection {
	
	@Test
	public static Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//C:\app\admin\product\18.0.0\dbhomeXE\network\admin 에서 listener.ora 이름 xe를 확인
			//C:\app\admin\product\18.0.0\dbhomeXE\jdbc\lib에서 ojdbc8를 build path
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","cos","bitc5600");
			return conn;
		} catch (Exception e) {
			System.out.println("DB 연결 실패 : " + e.getMessage());
		}
		return null;
	}
	
}
