package address.test;

import java.sql.Connection;
import java.sql.DriverManager;
import org.junit.Test;

public class DBConnectionTest {
	
	@Test
	public void getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//C:\app\admin\product\18.0.0\dbhomeXE\network\admin ���� listener.ora �̸� xe�� Ȯ��
			//C:\app\admin\product\18.0.0\dbhomeXE\jdbc\lib���� ojdbc8�� build path
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","cos","bitc5600");
		} catch (Exception e) {
			System.out.println("DB ���� ���� : " + e.getMessage());
		}
	}
}
