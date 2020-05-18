package address.utils;

public class MyStringParser {
	public static int getId(String selectedList) {
		return Integer.parseInt(selectedList.split("\\.")[0]);
	}
}
