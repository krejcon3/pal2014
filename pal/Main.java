package pal;

import java.io.*;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

	public static int L;
	public static int N;
	public static String[] words;
	public static Node[] nodes;

    public static void main(String[] args) throws IOException {
//        System.setIn(new FileInputStream("./src/test/6/pub07.in"));
		read();
    }

	public static void read() throws IOException {
//		long startTime = System.currentTimeMillis();
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
//		long reading = System.currentTimeMillis();
		nodes = new Node[N];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new Node(i);
		}
//		long setupNodes = System.currentTimeMillis();
		for (int i = 0; i < words.length - 1; i++) {
			for (int j = i + 1; j < words.length; j++) {
				int levLng = getShortestLevenshteinDistance(words[i], words[j]);
				if (levLng <= L) {
					nodes[i].addChild(nodes[j]);
					nodes[j].addChild(nodes[i]);
				}
			}
		}
//		long generate = System.currentTimeMillis();
		int maybe = 0;
		for (int i = 0; i < nodes.length; i++) {
			dijkstra(i);
			for (Node n : nodes) {
				if (maybe < n.distance && n.distance < Integer.MAX_VALUE) {
					maybe = n.distance;
				}
			}
		}
//		long dijkstra = System.currentTimeMillis();
		System.out.println(maybe);
//		System.out.println("Reading: " + (reading - startTime));
//		System.out.println("Setup nodes: " + (setupNodes - reading));
//		System.out.println("Generate graph: " + (generate - setupNodes));
//		System.out.println("Dijkstra: " + (dijkstra - generate));
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

	public static void dijkstra(int start) {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].distance = Integer.MAX_VALUE;
		}
		nodes[start].distance = 0;

		PriorityQueue<Node> queve = new PriorityQueue<Node>();
		queve.add(nodes[start]);

		while(!queve.isEmpty()) {
			Node node = queve.poll();

			for (Node child : node.childs) {
				if (node.distance + 1 < child.distance) {
					queve.remove(child);
					child.distance = node.distance + 1;
					queve.add(child);
				}
			}
		}
	}
}
