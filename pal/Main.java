package pal;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
	public static int N;
	public static int M;
	public static int[][] edges;
	public static int[] ways;
	public static int[] costs;
	public static int longest = 0;

    public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		StringTokenizer st;
		if ((line = br.readLine()) != null) {
			st = new StringTokenizer(line);
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
		}
		edges = new int[M][3];
		ways = new int[N];
		costs = new int[N];
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			edges[i] = new int[]{Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
		}
		Arrays.sort(edges, new Comparator<int[]>(){
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[2] == o2[2] ? 0 : o1[2] < o2[2] ? 1 : -1;
			}
		});
		for (int[] edge : edges) {
			int tempCost1 = costs[edge[0]];
			int tempWay1 = ways[edge[0]];
			int tempCost2 = costs[edge[1]];
			int tempWay2 = ways[edge[1]];

			if (tempCost1 == 0 && tempCost2 == 0) {
				costs[edge[0]] = edge[2];
				costs[edge[1]] = edge[2];
				ways[edge[0]] = edge[2];
				ways[edge[1]] = edge[2];
				longest = Math.max(longest, Math.max(ways[edge[0]], ways[edge[1]]));
			} else {
				if (edge[2] < tempCost2 && tempWay1 < tempWay2 + edge[2]) {
					ways[edge[0]] = tempWay2 + edge[2];
					costs[edge[0]] = edge[2];
					longest = Math.max(longest, ways[edge[0]]);
				}
				if (edge[2] < tempCost1 && tempWay2 < tempWay1 + edge[2]) {
					ways[edge[1]] = tempWay1 + edge[2];
					costs[edge[1]] = edge[2];
					longest = Math.max(longest, ways[edge[1]]);
				}
				if (edge[2] == tempCost2 && tempWay1 < edge[2]) {
					ways[edge[0]] = edge[2];
					costs[edge[0]] = edge[2];
					longest = Math.max(longest, ways[edge[0]]);
				}
				if (edge[2] == tempCost1 && tempWay2 < edge[2]) {
					ways[edge[1]] = edge[2];
					costs[edge[1]] = edge[2];
					longest = Math.max(longest, ways[edge[1]]);
				}
			}
		}
		System.out.println(longest);
	}
}
