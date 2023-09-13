package mini;

/**
 * Utility class with static methods for loop practice.
 */
public class LoopsInfinityAndBeyond {
	/**
	 * Private constructor to disable instantiation.
	 */
	private LoopsInfinityAndBeyond() {
	}

	/**
	 * Define a flying saucer as the following string pattern: one , followed by
	 * zero to many , followed by one . Write a Java method that, given a string
	 * find the first instance of a flying saucer (starting from the left) and
	 * return its length. If no flying saucer exists return 0.
	 * <p>
	 * For example: Given: "(==)" Return: 4
	 * <p>
	 * Given: "***()**(===)" Return: 2
	 * <p>
	 * Given: "****(***)" Return: 0
	 *
	 * @param source input string
	 * @return the length
	 */

	/**
	 * Returns the length of the first saucer, returns 0 if none found
	 * 
	 * @param str
	 * @return
	 */

	public static int flyingSaucerLength(String str) {
		int length = 0;
		boolean foundOpenBracket = false;

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);

			if (c == '(') {
				foundOpenBracket = true;
				length++;
			} else if (c == ')' && foundOpenBracket) {
				length++;
				break;
			} else if (c == '=' && foundOpenBracket) {
				length++;
			} else {
				length = 0;
				foundOpenBracket = false;
			}
		}

		return length;
	}

	/**
	 * Write a Java method that, given a string which many contain a flying saucer
	 * broken into two parts with characters in between, return a string where the
	 * flying is fixed by removing the in between characters. Look for the two parts
	 * of the flying saucer from left to right and fix the saucer with the first
	 * available parts.
	 * <p>
	 * For example: Given: ***(==****===)*** Return: ***(=====)***
	 * <p>
	 * Given: ***(==****)**=)* Return: ***(==)**=)*
	 * <p>
	 * Given: ***(==)** Return: ***(==)**
	 *
	 * @param s
	 * @return
	 */

	/**
	 * Removes *'s when in the middle of one
	 * 
	 * @param s
	 * @return
	 */

	public static String fixFlyingSaucer(String str) {
		int leftIndex = -1;
		int rightIndex = -1;

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);

			if (c == '(' && leftIndex == -1) {
				leftIndex = i;
			} else if (c == ')' && leftIndex != -1 && rightIndex == -1) {
				rightIndex = i;
				break;
			}
		}

		if (leftIndex != -1 && rightIndex != -1) {
			return str.substring(0, leftIndex + 1) + str.substring(leftIndex + 1, rightIndex).replaceAll("[^=]", "")
					+ str.substring(rightIndex);
		}

		return str;
	}

	/**
	 * Write a Java method that, given a string which many contain many flying
	 * saucers, return the number of flying saucers. For this problem a flying
	 * saucer may wrap around from the right side of the string to the left.
	 * <p>
	 * For example: Given: ***(===)*** Return: 1
	 * <p>
	 * Given: =)**(==)**( Return: 2
	 * <p>
	 * Given: ***(=*=)** Return: 0
	 *
	 * @param s
	 * @return
	 */

	/**
	 * returns the number of saucers
	 * 
	 * @param s
	 * @return
	 */

	public static int countFlyingSaucers(String str) {
		String temp = "";
		int count = 0;
		int i = 0;
		int firstEnd = 0;
		while (i < str.length()) {
			int start = str.indexOf("(", i);
			firstEnd = str.indexOf(")", i);
			if (firstEnd < start) {
				temp = str.substring(firstEnd, str.length());
				str = temp.concat(str.substring(0, firstEnd + 1));
			}
			if (start == -1) {
				break;
			}
			int end = str.indexOf(")", start);
			if (end == -1) {
				break;
			}
			int equalsCount = 0;
			for (int j = start + 1; j < end; j++) {
				if (str.charAt(j) == '=') {
					equalsCount++;
				}
			}
			if (equalsCount == end - start - 1) {
				count++;
			}
			i = end + 1;
		}
		return count;
	}

	/**
	 * Write a Java method that, given a string which many contain many flying
	 * saucers, shifts all of the saucers one character to the right. For this
	 * problem a flying saucer may wrap around from the right side of the string to
	 * the left. The returned string should have the same number of characters as
	 * the given string. This is achieved by moving the character to the right of a
	 * saucer to its left. It can be assumed that saucers will never be touching
	 * each other (i.e., there will always be at least one character between any two
	 * saucers). Also, a saucer will not touch itself (e.g., "=) (=").
	 * <p>
	 * For example: Given: ***(===)*** Return: ****(===)**
	 * <p>
	 * Given: =)**(==)**( Return: (=)***(==)*
	 * <p>
	 * Given: a()bcde(=*=)fg Return: ab()cde(=*=)fg
	 *
	 * @param s
	 * @return
	 */

	/**
	 * moves element one pot to the right
	 * 
	 * @param s
	 * @return
	 */

	public static String flyingSaucersFly(String str) {
		String temp = "";
		String tempSaucer = "";
		String tempString = "";
		String stringResult = str;
		char tempChar = 'a';
		int firstEnd = 0;
		int start = 0;
		int len = str.length();
		boolean isSaucer = false;
		int equalsCount = 0;
		start = str.indexOf("(");
		int end = str.indexOf(")", start);
		firstEnd = str.indexOf(")");

		if (firstEnd >= 0 && firstEnd < start) {
			// Format
			temp = str.substring(firstEnd + 1, len);
			temp = temp.concat(str.substring(0, firstEnd + 1));

			// Check For Saucers
			start = temp.indexOf("(");
			end = temp.indexOf(")");

			for (int j = start + 1; j < end; j++) {
				if (temp.charAt(j) == '=') {
					equalsCount++;
				}
			}
			if (equalsCount == end - start - 1) {
				stringResult = str.substring(str.length() - 1, str.length());
				stringResult = stringResult.concat(str.substring(0, firstEnd + 1));
				stringResult = stringResult.concat(str.substring(firstEnd + 1, str.length() - 1));
			}
			return stringResult;
		} else {
			// Format

			// Check For Saucers
			start = str.indexOf("(");
			end = str.indexOf(")");

			for (int j = start + 1; j < end; j++) {
				if (str.charAt(j) == '=') {
					equalsCount++;
				}
			}
			if (equalsCount == end - start - 1) {
				if (start != 0) {
					stringResult = str.substring(0, start);
					stringResult = stringResult.concat(str.substring(end + 1, end + 2));
					stringResult = stringResult.concat(str.substring(start, end + 1));
					stringResult = stringResult.concat(str.substring(end + 2, str.length()));
				}
			}
			equalsCount = 0;
			start = str.lastIndexOf("(");
			end = str.lastIndexOf(")");
			if (start != str.indexOf("(") && end != str.indexOf(")"))
				for (int j = start + 1; j < end; j++) {
					if (str.charAt(j) == '=') {
						equalsCount++;
					}
				}
			if (equalsCount == end - start - 1) {
				if (start != 0) {
					if (end < str.length()) {
						tempChar = stringResult.charAt(0);
						stringResult = "";
						stringResult = stringResult.concat(str.substring(end, end + 1));
						stringResult = stringResult.concat(str.substring(0, end));
					}

				}
			}

		}
		return stringResult;
	}

}
