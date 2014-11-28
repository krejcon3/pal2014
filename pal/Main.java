package pal;

import java.io.*;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {

	public static int L;
	public static int N;
	public static String[] words;
	public static Node[] nodes;

    public static void main(String[] args) throws IOException {
//        System.setIn(new FileInputStream("./src/test/6/pub05.in"));
		read();
    }

	public static void read() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		StringTokenizer st;
		if ((line = br.readLine()) != null) {
			st = new StringTokenizer(line);
			L = Integer.parseInt(st.nextToken());
			N = Integer.parseInt(st.nextToken());
		}
		words = new String[N];
		for (int i = 0; i < words.length; i++) {
			st = new StringTokenizer(br.readLine());
			words[i] = st.nextToken();
		}
		nodes = new Node[N];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(i);
		}
		for (int i = 0; i < words.length - 1; i++) {
			for (int j = i + 1; j < words.length; j++) {
				int levLng = getShortestLevenshteinDistance(words[i], words[j]);
				if (levLng <= L) {
					nodes[i].addEdge(j, 1);
					nodes[j].addEdge(i, 1);
				}
			}
		}
		int maybe = 0;
		int[] lngs;
		for (int i = 0; i < nodes.length; i++) {
			lngs = dijkstra(i);
			for (int l : lngs) {
				if (maybe < l && l < Integer.MAX_VALUE) {
					maybe = l;
				}
			}
		}
		System.out.println(maybe);
	}

	/*
	 * http://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java
	 */
	public static int getShortestLevenshteinDistance (String text, String pattern) {
		int textLength = text.length() + 1;
		int patternLength = pattern.length() + 1;

		int[] cost = new int[textLength];
		int[] newCost = new int[textLength];

		for (int i = 0; i < textLength; i++) {
			cost[i] = i;
		}

		for (int j = 1; j < patternLength; j++) {
			newCost[0] = j;

			for(int i = 1; i < textLength; i++) {
				int match = (text.charAt(i - 1) == pattern.charAt(j - 1)) ? 0 : 1;
				int costReplace = cost[i - 1] + match;
				int costInsert  = cost[i] + 1;
				int costDelete  = newCost[i - 1] + 1;
				newCost[i] = Math.min(Math.min(costInsert, costDelete), costReplace);
			}

			int[] swap = cost;
			cost = newCost;
			newCost = swap;
		}

		return cost[textLength - 1];
	}

	public static int[] dijkstra(int start) {
		int[] distances = new int[N];
		boolean[] closed = new boolean[N];
		for (int i = 0; i < distances.length; i++) {
			if (i != start) {
				distances[i] = Integer.MAX_VALUE;
			}
		}
		HashSet<Node> toVisit = new HashSet<Node>();
		toVisit.add(nodes[start]);
		int temp;
		Node actual = null;
		while (!toVisit.isEmpty()) {
			temp = Integer.MAX_VALUE;
			for (Node c : toVisit) {
				if (distances[c.index] < temp) {
					temp = distances[c.index];
					actual = c;
				}
			}
			toVisit.remove(actual);
			closed[actual.index] = true;

			for (int[] edge : actual.edges) {
				if (!closed[edge[0]]) {
					if (distances[edge[0]] > distances[actual.index] + edge[1]) {
						distances[edge[0]] = distances[actual.index] + edge[1];
						toVisit.add(nodes[edge[0]]);
					}
				}
			}
		}
		return distances;
	}
}
