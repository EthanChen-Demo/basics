package temps.string;

public class PointCodeTest {
	public static void main(String[] args) {
		String one = "³ÂµÆÁÁ3ÁÁ";
		System.out.println("one.length():" + one.length());
		System.out.println("one.codePointCount(0, 1):" + one.codePointCount(0, 1));
		System.out.println("one.codePointCount(0, one.length()):" + one.codePointCount(0, one.length()));
	}
}
