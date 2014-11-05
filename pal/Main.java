package pal;

import java.io.*;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {
	public static int M, N, H, F, S, T;
	public static City[] cities;
	public static City[] oystered;
	public static City[] reducated;

    public static void main(String[] args) throws IOException {
//        System.setIn(new FileInputStream("./src/test/3/pub01.in"));
		read();
		reducto();
		find();
    }

	public static void find() {
		int[] distances = lastDijkstra(S);
		System.out.println(distances[T]);
	}

	public static int[] lastDijkstra(int start) {
		int[] distances = new int[M + N];
		boolean[] closed = new boolean[M + N];
		for (int i = 0; i < distances.length; i++) {
			if (i != start) {
				distances[i] = Integer.MAX_VALUE;
			}
		}
		HashSet<City> toVisit = new HashSet<City>();
		toVisit.add(reducated[start]);
		int temp;
		City actual = null;
		while (!toVisit.isEmpty()) {
			temp = Integer.MAX_VALUE;
			for (City c : toVisit) {
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
						toVisit.add(reducated[edge[0]]);
					}
				}
			}
		}
		return distances;
	}

	public static void reducto() {
		int[] distances;
		reducated = new City[M + N];
		for (int i = 0; i < oystered.length; i++) {
			distances = findOysteredInDistance(oystered[i].index);
			reducated[oystered[i].index] = new City(oystered[i].index);
			for (int j = 0; j < distances.length; j++) {
				if (distances[j] < Integer.MAX_VALUE && distances[j] > 0 && cities[j].hasOyster) {
					reducated[oystered[i].index].edges.add(new int[]{j, distances[j]});
				}
			}
		}
	}

	public static int[] findOysteredInDistance(int start) {
		int[] distances = new int[M + N];
		boolean[] closed = new boolean[M + N];
		for (int i = 0; i < distances.length; i++) {
			if (i != start) {
				distances[i] = Integer.MAX_VALUE;
			}
		}
		HashSet<City> toVisit = new HashSet<City>();
		toVisit.add(cities[start]);
		int temp;
		City actual = null;
		while (!toVisit.isEmpty()) {
			temp = Integer.MAX_VALUE;
			for (City c : toVisit) {
				if (distances[c.index] < temp) {
					temp = distances[c.index];
					actual = c;
				}
			}
			toVisit.remove(actual);
			closed[actual.index] = true;

			for (int[] edge : actual.edges) {
				if (!closed[edge[0]]) {
					if (F >= distances[actual.index] + edge[1] && distances[edge[0]] > distances[actual.index] + edge[1]) {
						distances[edge[0]] = distances[actual.index] + edge[1];
						toVisit.add(cities[edge[0]]);
					}
				}
			}
		}
		return distances;
	}

	public static void read() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		StringTokenizer st;
		if ((line = br.readLine()) != null) {
			st = new StringTokenizer(line);
			M = Integer.parseInt(st.nextToken()); // number of cities with Oyster
			N = Integer.parseInt(st.nextToken()); // number of cities without Oyster
			H = Integer.parseInt(st.nextToken()); // number of highways
			F = Integer.parseInt(st.nextToken()); // maximal travel distance
			S = Integer.parseInt(st.nextToken()); // label of hometown
			T = Integer.parseInt(st.nextToken()); // label of capital city (target)
		}
		cities = new City[M + N];
		oystered = new City[M];
		int index;
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			index = Integer.parseInt(st.nextToken());
			oystered[i] = new City(index);
			oystered[i].hasOyster = true;
			cities[index] = oystered[i];
		}
		int a, b, d;
		for (int i = 0; i < H; i++) {
			st = new StringTokenizer(br.readLine());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			d = Integer.parseInt(st.nextToken());

			if (cities[a] == null) {
				cities[a] = new City(a);
			}
			if (cities[b] == null) {
				cities[b] = new City(b);
			}

			cities[a].edges.add(new int[]{b, d});
			cities[b].edges.add(new int[]{a, d});
		}
	}
}
