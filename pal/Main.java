package pal;

import java.io.*;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
	public static int count = 0;
	public static int limit = 0;
	public static int[][] sites;
	public static HashSet<Integer> unused;
	public static int start;
	public static double[] bestWays;

	public static void main(String[] args) throws IOException {
//		System.setIn(new FileInputStream("./src/test/pub01.in"));
		read();
		bestWays = new double[count];
		for (int i = 0; i < count; i++) {
			start = i;
			work((HashSet<Integer>) unused.clone(), new int[count], i, 0, 0);
			unused.remove(i);
			if (bestWays[unused.size()] > 0) {
				System.out.println((int)Math.ceil(bestWays[unused.size()]));
				break;
			}
		}
	}

	public static void work(HashSet<Integer> array, int[] used, int site, double length, int depth) {
		array.remove(site);
		used[depth] = site;
//		printArray(used, depth + 1);
		int tStart;
		int tEnd;
		boolean crossed;
		if (!array.isEmpty()) {
			for (int s : array) {
//				System.out.print(site + " -> " + s);
				crossed = false;
				double toNext = getDistance(sites[site], sites[s]);
				double toHome = getDistance(sites[s], sites[start]);
				if (length + toNext + toHome > limit) {
//					System.out.println(" " + (length + toNext) + " too long to return home (" + (length + toNext + toHome) + ")");
					continue;
				}
//				System.out.println(" " + (length + toNext));
				tEnd = -1;
				for (int i = 0; i < depth + 1; i++) {
					tStart = tEnd;
					tEnd = used[i];
					if (tStart == -1) {
						continue;
					}
					if (sites[site][0] == sites[tEnd][0] && sites[site][1] == sites[tEnd][1]) {
						continue;
					}
					if (intersectionFounded(sites[site], sites[s], sites[tStart], sites[tEnd])) {
//						System.out.println("\tinterception founded: [" + site + "|" + s + "] vs [" + tStart + "|" + tEnd + "]");
						crossed = true;
						break;
					}
				}
				if (!crossed) {
					work((HashSet<Integer>)array.clone(), used.clone(), s, length + toNext, depth + 1);
				}
			}
		}
		tEnd = -1;
		crossed = false;
		for (int i = 0; i < depth + 1; i++) {
			tStart = tEnd;
			tEnd = used[i];
			if (tStart == -1) {
				continue;
			}
			if (sites[site][0] == sites[tEnd][0] && sites[site][1] == sites[tEnd][1] || sites[start][0] == sites[tStart][0] && sites[start][1] == sites[tStart][1]) {
				continue;
			}
			if (intersectionFounded(sites[site], sites[start], sites[tStart], sites[tEnd])) {
//				System.out.println("\tinterception founded: [" + site + "|" + start + "] vs [" + tStart + "|" + tEnd + "]");
				crossed = true;
				break;
			}
		}
		if (!crossed) {
			double temp = getDistance(sites[site], sites[start]) + length;
			if (bestWays[depth] == 0) {
				bestWays[depth] = temp;
			} else if (temp < bestWays[depth]) {
				bestWays[depth] = temp;
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
		// Return false if either of the lines have zero length
		if (x1 == x2 && y1 == y2 ||
			x3 == x4 && y3 == y4){
			return false;
		}
		// Fastest method, based on Franklin Antonio's "Faster Line Segment Intersection" topic "in Graphics Gems III" book (http://www.graphicsgems.org/)
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
			// This code wasn't in Franklin Antonio's method. It was added by Keith Woodward.
			// The lines are parallel.
			// Check if they're collinear.
			double y3LessY1 = y3-y1;
			double collinearityTestForP3 = x1*(y2-y3) + x2*(y3LessY1) + x3*(y1-y2);   // see http://mathworld.wolfram.com/Collinear.html
			// If p3 is collinear with p1 and p2 then p4 will also be collinear, since p1-p2 is parallel with p3-p4
			if (collinearityTestForP3 == 0){
				// The lines are collinear. Now check if they overlap.
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
