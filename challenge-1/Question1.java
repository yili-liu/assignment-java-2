import java.util.*;

public class Question1 {

	static void findPairs(int[] testArray, int targetSum) {
		// initialize hashmap
		HashMap<Integer, Integer> occur = new HashMap<Integer, Integer>();

		// use hashmap to keep track of how many times each number occurs in the array
		for (int i = 0; i < testArray.length; i++) {
			int current = testArray[i];

			// if the current number has not shown up yet, add an entry of 1 occurrence
			if (occur.get(current) == null) {
				occur.put(current, 1);

				// if it has shown up, add 1 to its occurrence count
			} else {
				occur.put(current, occur.get(current) + 1);
			}
		}

		// loop through array again
		for (int i = 0; i < testArray.length; i++) {
			// keep track of current number and its complementary number needed to sum to target
			int current = testArray[i];
			int complement = targetSum - current;

			// if the complementary number is in the array
			if (occur.get(complement) != null) {
				// keep track of number of occurrences of each number
				int ocCurrent = occur.get(current);
				int ocComplement = occur.get(complement);

				boolean isPair = false;
				// it is a valid pair if...
				// the two numbers are not equal and they occur at least once
				if (current != complement && ocCurrent > 0 && ocComplement > 0) {
					isPair = true;

					// or if they are equal and they occur at least twice
				} else if (current == complement && ocCurrent > 1) {
					isPair = true;
				}

				// if they make a valid pair...
				// decrement their occurrence count and print the pair to console
				if (isPair) {
					occur.put(current, ocCurrent - 1);
					occur.put(complement, ocComplement - 1);
					System.out.println("(" + current + ", " + complement + ")");
				}
			}
		}
		
		// Time complexity: O(n)
		// Space complexity: O(n)
	}

	public static void main(String[] args) {
		// test cases
		int[] test1 = {2, 4, 5, 1, 3, 5, 4};
		int target1 = 6;
		int[] test2 = {};
		int target2 = 1;
		int[] test3 = {-1, 99, 7, 425, 13451, 3, 5, 6, -146, 545, -134, 2, 2, 4, 5, 1, 3, 5, 4};
		int target3 = 6;

		System.out.println("test1:");
		findPairs(test1, target1);
		System.out.println("test2:");
		findPairs(test2, target2);
		System.out.println("test3:");
		findPairs(test3, target3);
	}
}
