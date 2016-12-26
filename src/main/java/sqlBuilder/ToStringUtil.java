package sqlBuilder;

public class ToStringUtil {

	private ToStringUtil() {

	}

	public static final String blankSpace = " ";
	public static final String blankSpace_4 = blankSpace + blankSpace + blankSpace + blankSpace;

	public static void printLine(String strToPrint) {
		System.out.println(strToPrint);
	}

	public static void printLine() {
		printLine("");
	}

}
