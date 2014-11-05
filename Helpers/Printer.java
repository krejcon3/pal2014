package Helpers;

/**
 * Created by krejcir on 2.11.14.
 */
public class Printer {

	/*
	 * One dimension array
	 */
	public static void printArray(int[] array) {
		for (int i : array) {
			System.out.print(i + " ");
		}
		System.out.println();
	}

	/*
	 * Two dimension array
	 */
	public static void printArray(int[][] array) {
		for (int[] i : array) {
			printArray(i);
		}
	}
}
