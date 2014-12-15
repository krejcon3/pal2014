package pal;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

	public static int N;
	public static int M;

	public static ArrayList<Node> nodes;
	public static int maximum = 0;
	public static int[] edge;
	public static ArrayList<int[]> edges;

    public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("./src/test/zk1/pub04.in"));
		read();
		work();
    }

	public static void work() {
		Tarjan tarjan = new Tarjan(N, nodes);
		for (int[] tryEdge : edges) {
			tarjan.run(tryEdge);
			if (tarjan.maxTotal > maximum) {
				maximum = tarjan.maxTotal;
				edge = tryEdge;
			}
		}
		System.out.println(edge[0] + " " + edge[1] + " " + maximum);
	}

	public static void read() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		StringTokenizer st;
		if ((line = br.readLine()) != null) {
			st = new StringTokenizer(line);
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
		}

		nodes = new ArrayList<Node>(N);

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			nodes.add(i, new Node(i, Integer.parseInt(st.nextToken())));
		}

		edges = new ArrayList<int[]>();
		int start;
		int end;
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			start = Integer.parseInt(st.nextToken());
			end = Integer.parseInt(st.nextToken());
			nodes.get(start).addChild(nodes.get(end));
			edges.add(new int[]{start, end});
		}
	}
}
