package pal;

import java.io.*;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
	public static int count = 0;
	public static int limit = 0;
	public static int[][] sites;
	public static HashSet<Integer> unused;
	public static double[] bestWays;
	public static int[] way;

	/**
	 * co třeba řadit podle vzdálenosti ty kandidáty?
	 */

	public static void main(String[] args) throws IOException {
//		System.setIn(new FileInputStream("./src/test/pub01.in"));
		read();
		bestWays = new double[count];
		way = new int[count];
		int indexOfLastInserted = 0;
		for (int i = 0; i < count - 1; i++) {
			unused.remove(i);
			way[indexOfLastInserted] = i;
			work((HashSet<Integer>) unused.clone(), 0, indexOfLastInserted);
			if (bestWays[unused.size()] > 0) {
				System.out.println((int)Math.ceil(bestWays[unused.size()]));
				System.exit(0);
			}
		}
	}

	public static void work(HashSet<Integer> array, double length, int indexOfLastInserted) {
		HashSet<Integer> candidates = new HashSet<Integer>(array.size() * 2);
		int size = unused.size();

		// vybrání možných bodů dále
		if (!array.isEmpty()) {
			for (int candidate : array) {
				double toNext = getDistance(sites[way[indexOfLastInserted]], sites[candidate]);
				double toHome = getDistance(sites[candidate], sites[way[0]]);

				// není delší než povolený limit?
				if (length + toNext + toHome > limit) {
					continue;
				}

				// je delší než nejdelší s maximálním počtem vrcholů?
				if (bestWays[size] > 0 && bestWays[size] < length + toNext + toHome) {
					continue;
				}
				candidates.add(candidate);
			}
		}

		// testování vybraných bodů
		// pokud je nastaven maximální možný počet vrcholů (> 0) a zároveň je počet použitelných vrcholů + počet použitých menší
		// než počet maximálního možné počtu vrcholů
		if (!candidates.isEmpty()) {
			if (bestWays[size] > 0) {
				if (size != candidates.size() + indexOfLastInserted) {
					return;
				}
			}
			boolean crossed;
			HashSet<Integer> temp;
			for (int choosen : candidates) {
				double toNext = getDistance(sites[way[indexOfLastInserted]], sites[choosen]);
				double toHome = getDistance(sites[choosen], sites[way[0]]);

				// kříží se to s něčím?
				// kontroluji až když mám cestu aspon o 1 hraně
				crossed = false;
				if (indexOfLastInserted > 0) {
					for (int i = 1; i < indexOfLastInserted; i++) {
						if (intersectionFounded(sites[choosen], sites[way[indexOfLastInserted]], sites[way[i]], sites[way[i - 1]])) {
							crossed = true;
							break;
						}
					}
				}
				if (crossed) {
					continue;
				}

				// je lepší než zatím nejlepší řešení na dané délce?
				if (bestWays[indexOfLastInserted + 1] == 0) {
					bestWays[indexOfLastInserted + 1] = length + toNext + toHome;
				} else if (bestWays[indexOfLastInserted + 1] > length + toNext + toHome) {
					bestWays[indexOfLastInserted + 1] = length + toNext + toHome;
				}

				temp = (HashSet<Integer>)candidates.clone();
				temp.remove(choosen);
				way[indexOfLastInserted + 1] = choosen;
				work(temp, length + toNext, indexOfLastInserted + 1);
			}
		}
	}

	public static double getDistance(int[] from, int[] to) {
		return Math.sqrt(Math.pow(to[0] - from[0], 2) + Math.pow(to[1] - from[1], 2));
	}

	public static void read() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		StringTokenizer st;
		if ((line = br.readLine()) != null) {
			st = new StringTokenizer(line);
			count = Integer.parseInt(st.nextToken());
			limit = Integer.parseInt(st.nextToken());
		}
		sites = new int[count][2];
		unused = new HashSet<Integer>(2 * count);
		int c = 0;
		while ((line = br.readLine()) != null) {
			st = new StringTokenizer(line);
			sites[c][0] = Integer.parseInt(st.nextToken());
			sites[c][1] = Integer.parseInt(st.nextToken());
			unused.add(c++);
		}
	}

	public static boolean intersectionFounded(int[] a1, int[] a2, int[] b1, int[] b2) {
		return linesIntersect(a1[0], a1[1], a2[0], a2[1], b1[0], b1[1], b2[0], b2[1]);
	}

	// http://www.java-gaming.org/index.php?topic=22590.0
	public static boolean linesIntersect(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4){
		if (x1 == x2 && y1 == y2 ||
			x3 == x4 && y3 == y4){
			return false;
		}
		double ax = x2-x1;
		double ay = y2-y1;
		double bx = x3-x4;
		double by = y3-y4;
		double cx = x1-x3;
		double cy = y1-y3;

		double alphaNumerator = by*cx - bx*cy;
		double commonDenominator = ay*bx - ax*by;
		if (commonDenominator > 0){
			if (alphaNumerator < 0 || alphaNumerator > commonDenominator){
				return false;
			}
		}else if (commonDenominator < 0){
			if (alphaNumerator > 0 || alphaNumerator < commonDenominator){
				return false;
			}
		}
		double betaNumerator = ax*cy - ay*cx;
		if (commonDenominator > 0){
			if (betaNumerator < 0 || betaNumerator > commonDenominator){
				return false;
			}
		}else if (commonDenominator < 0){
			if (betaNumerator > 0 || betaNumerator < commonDenominator){
				return false;
			}
		}
		if (commonDenominator == 0){
			double y3LessY1 = y3-y1;
			double collinearityTestForP3 = x1*(y2-y3) + x2*(y3LessY1) + x3*(y1-y2);   // see http://mathworld.wolfram.com/Collinear.html
			if (collinearityTestForP3 == 0){
				if (x1 >= x3 && x1 <= x4 || x1 <= x3 && x1 >= x4 ||
					x2 >= x3 && x2 <= x4 || x2 <= x3 && x2 >= x4 ||
					x3 >= x1 && x3 <= x2 || x3 <= x1 && x3 >= x2){
					if (y1 >= y3 && y1 <= y4 || y1 <= y3 && y1 >= y4 ||
						y2 >= y3 && y2 <= y4 || y2 <= y3 && y2 >= y4 ||
						y3 >= y1 && y3 <= y2 || y3 <= y1 && y3 >= y2){
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}

	public static void printArray(int[] array, int limit) {
		for (int i = 0; i < limit; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}
}
