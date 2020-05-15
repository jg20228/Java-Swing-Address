package address.test.temp;

import org.junit.Test;

public class MyStringParserTest {
	
	@Test
	public void getId() {
	int memberId = Integer.parseInt("1.ȫ�浿".split("\\.")[0]);
	int memberId2 = Integer.parseInt("1.ȫ�浿".split("[.]")[0]);
	int memberId3 = Integer.parseInt("1.ȫ�浿".split(".")[0]);
	System.out.println(memberId);
	}
}
