package pal;

import java.util.ArrayList;

/**
 * Created by krejcir on 29.11.14.
 */
public class Node {
	public ArrayList<int[]> edges;
	public int index;

	public Node(int i) {
		this.index = i;
		this.edges = new ArrayList<int[]>();
	}

	public void addEdge(int end, int cost) {
		this.edges.add(new int[]{end, cost});
	}
}
