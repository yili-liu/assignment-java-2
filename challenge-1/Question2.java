public class Question2 {
	
	static boolean isPalindrome(String testString) {
		// get string length
		int len = testString.length();

		// loop from the first character to the middle character
		for (int i = 0; i < len / 2; i++) {
			// if any mirror character does not equal, the string is not a palindrome
			if (testString.charAt(i) != testString.charAt(len - i - 1)) {
				return false;
			}
		}

		// if false is never returned for all the characters, the string is a palindrome
		return true;

		// Time complexity: O(n)
		// Space complexity: O(1)
	}

	public static void main(String[] args) {
		// test cases
		String test1 = "radar";
		String test2 = "asdfdsa";
		String test3 = "daad";
		String test4 = "qowihfao98a;sjkva";

		System.out.println("test1: " + isPalindrome(test1));
		System.out.println("test2: " + isPalindrome(test2));
		System.out.println("test3: " + isPalindrome(test3));
		System.out.println("test4: " + isPalindrome(test4));
	}
}
