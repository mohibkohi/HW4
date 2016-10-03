
/**
 * Mohib Kohi
 * Chris Kubec
 * Artem Davtyan
 *
 * Assignment 4 TCSS343 Summer 2016
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * tcss343 class used to create our solution for Assignment 4 for Design and
 * Analysis of Algorithms.
 */
public class tcss343 {
	public static Integer[][] stations;
	private static int size;
	private static String file = "sample_input.txt"; // Name of the File used

	/**
	 * Main Driver of class.
	 */
	public static void main(String[] args) {
		// Get input file used for tests
		BufferedReader input;

		try {
			input = new BufferedReader(new FileReader("src/" + file));
			while (input.readLine() != null) {
				size++;
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		stations = new Integer[size][size];
		readStation(file);

		// 3.1 Brute Force
		bruteForce();

		// 3.2 Divide And Conquer
		divideAndConquer();

		// 3.3 Dynamic Programming
		dynamicPrograming();
	}

	/**
	 * Brute Force Algorithm used for solution to 3.1 The asymptotic complexity
	 * of this algorithm is O(2^n).
	 */
	public static void bruteForce() {
		long time = System.currentTimeMillis();
		int minPath = -1;
		Set<Integer> minSet;
		Set<TreeSet<Integer>> setsOSets;
		HashSet<Integer> startingSet;

		// min tree set
		minSet = new TreeSet<>();

		startingSet = new HashSet<>();

		for (int i = 1; i <= stations.length; i++) {
			startingSet.add(i);
		}

		setsOSets = powerSet(startingSet);

		System.out.println("Brute Force");
		for (Set<Integer> currSet : setsOSets) {
			Integer pathSum = 0;
			ArrayList<Integer> setList = new ArrayList<>(currSet);

			if (currSet.contains(1) && currSet.contains(stations.length)) {
				for (int index = 0; index < currSet.size() - 1; index++) {
					int priceRow = setList.get(index) - 1;
					int priceCol = setList.get(index + 1) - 1;
					pathSum += stations[priceRow][priceCol];
				}
				if (minPath >= pathSum || minPath == -1) {
					minPath = pathSum;
					minSet = currSet;
				}
			}
		}
		time = System.currentTimeMillis() - time; // Get Run Time

		// Output Stream
		System.out.println("Minimum Path " + minSet.toString() + " and cost " + minPath + "\nBrute force time: " + time
				+ " milliseconds\n");
	}

	/**
	 * Divide and Conquer Algorithm used for 3.2.
	 */
	private static void divideAndConquer() {
		long timer = System.currentTimeMillis();
		int[] minimumCost = divideConquer(0);
		String minimumPath = buildDividePath(minimumCost);

		// Get Output
		System.out.println("Divide and Conquer\nMinimum Path " + minimumPath + " and cost " + minimumCost[0]);
		System.out.println("Divide and Conquer time: " + (System.currentTimeMillis() - timer) + " milliseconds\n");
	}

	/**
	 * Dynamic Programming Algorithm used for 3.3 The asymptotic complexity of
	 * this algorithm is O(n^2). *Code Explanation* This method starts with
	 * filling the tow row of the tempSolution, starting from the current cell
	 * looks left for all the minimum values, continuing with the same column of
	 * current cell looks above for all the if find one that is less than the
	 * one on the left update the minimum. If there is a better value obtained
	 * update the current cell.
	 */
	public static void dynamicPrograming() {
		long time = System.currentTimeMillis();
		int n = stations[0].length;

		Integer[][] tempSolution = new Integer[n][n];

		for (int col = 0; col < n; col++) {
			tempSolution[0][col] = stations[0][col];
		}

		for (int i = 1; i < n; i++) {
			for (int j = i; j < n; j++) {
				int minValue = -1;

				for (int k = i; k < j; k++) {
					if (tempSolution[i][k] + stations[i][j] < minValue || minValue == -1) {
						minValue = tempSolution[i][k] + stations[i][j];
					}
				}
				for (int k = 0; k < i; k++) {
					if (stations[k][j] != -1) {

						if (tempSolution[k][j] < minValue || minValue == -1) {
							minValue = tempSolution[k][j];
						}
					}
				}

				tempSolution[i][j] = minValue;
			}
		}
		time = System.currentTimeMillis() - time;

		System.out.println("Dynamic Programming\nMinimum Path " + recover(tempSolution).toString() + " and cost "
				+ tempSolution[n - 1][n - 1] + "\nDynamic Programing time: " + time + " milliseconds");
	}

	/**
	 * Reads each station in the array. 
	 * @param f_name
	 */
	private static void readStation(String f_name) {
		FileReader input;
		StringBuilder temp;
		try {
			stations = new Integer[size][size];
			input = new FileReader("src/" + f_name);
			int temp_char;
			temp = new StringBuilder();

			while ((temp_char = input.read()) != -1)
				temp.append((char) temp_char);
			int top = 0;
			int end = 0;
			int i = 0, j = 0;

			while (end < temp.length()) {
				while (isValidInput(temp.charAt(end)))
					end++;

				String nextVal = temp.substring(top, end);
				if (isNumb(nextVal))
					stations[i][j] = Integer.parseInt(nextVal);
				else
					stations[i][j] = -1;

				j++;

				if (j == size) {
					j = 0;
					i++;
				}
				while (end < temp.length() && !isValidInput(temp.charAt(end)))
					end++;
				top = end;

			}

			input.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		stations[size - 1][size - 1] = 0;
	}

	/**
	 * Recovery Method used by Dynamic Programing
	 * 
	 * @param solutionArr
	 * @return
	 */
	private static Set<Integer> recover(Integer[][] solutionArr) {

		int first_length = solutionArr[0].length;
		Set<Integer> winSet = new TreeSet<>();

		winSet.add(1);
		winSet.add(first_length);

		int row = first_length - 1, col = first_length - 1;
		while (row > 0) {

			int current = solutionArr[row][col];
			int above = solutionArr[row - 1][col];

			if (current == above) {
				row--;
			} else {
				int min = Integer.MAX_VALUE;
				int minIndex = Integer.MAX_VALUE;
				int i;
				for (i = row; i < col; i++) {

					if (solutionArr[row][i] < min) {
						min = solutionArr[row][i];
						minIndex = i;
					}
				}
				winSet.add(minIndex + 1);
				col = minIndex;
			}
		}
		return winSet;
	}

	/**
	 * Check for valid input. 
	 * @param c
	 * @return
	 */
	private static boolean isValidInput(char c) {
		return "NA".contains("" + c) || isNumb("" + c);
	}

	/**
	 * Check for valid number, and cast it.
	 * @param test
	 * @return
	 */
	public static boolean isNumb(String test) {
		boolean result = true;
		try {
			Integer.parseInt(test);
		} catch (NumberFormatException e) {
			result = false;
		}
		return result;
	}

	/**
	 * Given the Array builds the path. 
	 * @param minCost
	 * @return
	 */
	private static String buildDividePath(int[] minCost) {
		StringBuilder result = new StringBuilder();
		result.append("[1");
		for (int i = 1; i < minCost.length; i++) {
			if (minCost[i] > 0) {
				result.append(", ");
				result.append(minCost[i]);
			}
		}
		result.append("]");

		return result.toString();
	}

	/**
	 * Divide and conquer method. 
	 * The asymptotic complexity of this algorithm is O(n^2).
	 * @param i
	 * @return
	 */
	public static int[] divideConquer(int i) {
		int minVal = Integer.MAX_VALUE;
		int minJ = Integer.MAX_VALUE;
		int[] result = new int[size + 1];

		if (i == size - 1) {
			result[0] = 0;
			return result;
		} else {
			for (int j = i + 1; j < size; j++) {
				int[] curArr = divideConquer(j);
				int curVal = curArr[0] + stations[i][j];

				if (curVal < minVal) {
					minVal = curVal;
					minJ = j;

					System.arraycopy(curArr, 0, result, 0, result.length);
				}
			}
		}
		result[0] = minVal;

		result[i + 1] = minJ + 1;
		return result;
	}

	/**
	 * Helper method power set. 
	 * @param theSet
	 * @return
	 */
	public static Set<TreeSet<Integer>> powerSet(Set<Integer> theSet) {
		Set<TreeSet<Integer>> result = new HashSet<>();
		result.add(new TreeSet<>());

		for (Integer currentInt : theSet) {
			Set<TreeSet<Integer>> tempSet = new HashSet<>();

			for (TreeSet<Integer> subset : result) {
				if (subset.contains(1) || subset.contains(size - 1)) {

					tempSet.add(subset);
				}

				TreeSet<Integer> newSubset = new TreeSet<>(subset);
				newSubset.add(currentInt);
				tempSet.add(newSubset);
			}
			result = tempSet;
		}
		return result;
	}
}