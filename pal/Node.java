package pal;

import java.util.ArrayList;

/**
 * Created by krejcir on 29.11.14.
 */
public class Node implements Comparable<Node> {
	public ArrayList<Node> childs;
	public int index;
	public int distance;

	public Node(int i) {
		this.index = i;
		this.childs = new ArrayList<Node>();
		this.distance = Integer.MAX_VALUE;
	}

	public void addChild(Node node) {
		this.childs.add(node);
	}

	@Override
	public int compareTo(Node node) {
		return Integer.compare(this.distance, node.distance);
	}
}
